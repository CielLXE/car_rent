package com.example.carrent.services;

import com.example.carrent.bean.Car;
import com.example.carrent.bean.Rent;
import com.example.carrent.bean.User;
import com.example.carrent.dao.CarDao;
import com.example.carrent.dao.RentDao;
import com.example.carrent.dao.UserDao;
import com.example.carrent.enums.CarAvailableStatusEnum;
import com.example.carrent.enums.UserRoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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
        List<Rent> rents = rentDao.selectAllRents();
        return rents;
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
        if (car == null || car.getAvailable() == CarAvailableStatusEnum.unavailable.getCode()) {
            return false;
        }
        //user not exist or user is a admin
        User user = userDao.selectByPrimaryKey(rent.getUserId());
        if (user == null || user.getUserRole() == UserRoleEnum.admin.getCode()) {
            return false;
        }
        //set car to unavailable
        car.setAvailable((byte) CarAvailableStatusEnum.unavailable.getCode());
        carDao.updateByPrimaryKey(car);
        //insert new rent record
        return rentDao.insert(rent) == 1;
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
            return false;
        }
        Car car = carDao.selectByPrimaryKey(rent.getCarId());
        //already returned
        if (rent.getActualReturnDate() != null || car.getAvailable() == (byte) CarAvailableStatusEnum.available.getCode()) {
            return false;
        }
        rent.setActualReturnDate(new java.util.Date());
        car.setAvailable((byte) CarAvailableStatusEnum.available.getCode());
        return carDao.updateByPrimaryKey(car) == 1 && rentDao.updateByPrimaryKey(rent) == 1;
    }

    /**
     * update a rent's information
     *
     * @param rent
     * @return true:success false:fail
     */
    public boolean updateRent(Rent rent) {
        Rent now = rentDao.selectByPrimaryKey(rent.getId());
        //not exist
        if (now == null) {
            return false;
        }
        return rentDao.updateByPrimaryKey(rent) == 1;
    }
}
