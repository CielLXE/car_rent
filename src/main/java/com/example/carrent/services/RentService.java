package com.example.carrent.services;

import com.example.carrent.bean.*;
import com.example.carrent.dao.CarDao;
import com.example.carrent.dao.RentDao;
import com.example.carrent.dao.UserDao;
import com.example.carrent.enums.CarAvailableStatusEnum;
import com.example.carrent.enums.UserRoleEnum;
import com.example.carrent.utils.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentService {

    @Autowired
    RentDao rentDao;

    @Autowired
    CarDao carDao;

    @Autowired
    UserDao userDao;


    /**
     * get rent by primary key - id
     *
     * @param id
     * @return rent
     */
    public Rent getRentById(Integer id) {
        return rentDao.selectByPrimaryKey(id);
    }

    /**
     * get all rents
     *
     * @return list of rents
     */
    public List<Rent> getAllRents() {
        return rentDao.selectAllRents();
    }

    /**
     * get rent by user id
     *
     * @param userId
     * @return list of rents
     */
    public List<Rent> getRentByUserId(Integer userId) {
        RentExample example = new RentExample();
        RentExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        return rentDao.selectByExample(example);
    }

    /**
     * get rent by car id
     *
     * @param carId
     * @return list of rents
     */
    public List<Rent> getRentByCarId(Integer carId) {
        RentExample example = new RentExample();
        RentExample.Criteria criteria = example.createCriteria();
        criteria.andCarIdEqualTo(carId);
        return rentDao.selectByExample(example);
    }

    /**
     * add a new rent, set this car to be unavailable
     *
     * @param rent
     * @return true:success false:fail
     */
    public boolean addRent(Rent rent) {
        Car car = carDao.selectByPrimaryKey(rent.getCarId());
        //car not exist or car not available
        if (car == null) {
            throw new ServiceException("car not exist");
        }
        if (car.getAvailable() == CarAvailableStatusEnum.unavailable.getCode()) {
            throw new ServiceException("car not available");
        }
        //user not exist or user is a admin
        User user = userDao.selectByPrimaryKey(rent.getUserId());
        if (user == null) {
            throw new ServiceException("user not exist");
        }
        if (user.getUserRole() == UserRoleEnum.admin.getCode()) {
            throw new ServiceException("user is an admin");
        }
        //set car to unavailable
        car.setAvailable((byte) CarAvailableStatusEnum.unavailable.getCode());
        carDao.updateByPrimaryKeySelective(car);
        //insert new rent record
        return rentDao.insertSelective(rent) == 1;
    }

    /**
     * return a rent by primary key - id, set this car to be available
     *
     * @param id
     * @return true:success false:fail
     */
    public boolean returnRent(Integer id) {
        Rent rent = rentDao.selectByPrimaryKey(id);
        //not exist
        if (rent == null) {
            throw new ServiceException("rent not exist");
        }
        Car car = carDao.selectByPrimaryKey(rent.getCarId());
        //already returned
        if (rent.getActualReturnDate() != null || car.getAvailable() == (byte) CarAvailableStatusEnum.available.getCode()) {
            throw new ServiceException("rent already returned");
        }
        rent.setActualReturnDate(new java.util.Date());
        car.setAvailable((byte) CarAvailableStatusEnum.available.getCode());
        return carDao.updateByPrimaryKeySelective(car) == 1 && rentDao.updateByPrimaryKeySelective(rent) == 1;
    }

}
