package com.example.carrent.services;

import com.example.carrent.bean.Car;
import com.example.carrent.bean.CarExample;
import com.example.carrent.dao.CarDao;
import com.example.carrent.enums.CarAvailableStatusEnum;
import com.example.carrent.utils.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    private CarDao carDao;

    /**
     * get car by primary key - id
     *
     * @param id
     * @return car
     */
    public Car getCarById(Integer id) {
        return carDao.selectByPrimaryKey(id);
    }

    /**
     * get all cars in the database
     *
     * @return list of cars
     */
    public List<Car> getAllCars() {
        return carDao.selectAllCars();
    }

    /**
     * get all available cars in the database which can be rented
     *
     * @return list of available cars
     */
    public List<Car> getAvailableCars() {
        CarExample example = new CarExample();
        CarExample.Criteria criteria = example.createCriteria();
        criteria.andAvailableEqualTo((byte) CarAvailableStatusEnum.available.getCode());
        return carDao.selectByExample(example);
    }

    /**
     * add a new car to the database
     *
     * @param car
     * @return true:success false:fail
     */
    public boolean addCar(Car car) {
        return carDao.insert(car) == 1;
    }

    /**
     * update a car's information by primary key - id
     *
     * @param car
     * @return true:success false:fail
     */
    public boolean updateCar(Car car) {
        Car now = carDao.selectByPrimaryKey(car.getId());
        //not exist
        if (now == null) {
            throw new ServiceException("car not exist");
        }
        //don't allow to update a car's available
        if (car.getAvailable() != null && !now.getAvailable().equals(car.getAvailable())) {
            throw new ServiceException("don't allow to update a car's available");
        }
        return carDao.updateByPrimaryKeySelective(car) == 1;
    }

}
