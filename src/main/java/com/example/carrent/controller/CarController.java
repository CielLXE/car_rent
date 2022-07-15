package com.example.carrent.controller;

import com.example.carrent.bean.Car;
import com.example.carrent.bean.Result;
import com.example.carrent.services.CarService;
import com.example.carrent.utils.ResultGenerator;
import com.example.carrent.utils.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/car", produces = { MediaType.APPLICATION_JSON_VALUE })
@Api
public class CarController {

    @Autowired
    private CarService carService;


    @GetMapping(value = "")
    @ApiOperation(value = "get all cars in the database")
    public Result getAllCars() {
        List<Car> cars = carService.getAllCars();
        return ResultGenerator.genSuccessResult(cars);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "get car by primary key - id")
    public Result getCarById(@PathVariable Integer id) {
        Car car = carService.getCarById(id);
        if (car != null) {
            return ResultGenerator.genSuccessResult(car);
        } else {
            return ResultGenerator.genFailResult("car not exist");
        }
    }

    @GetMapping(value = "/available")
    @ApiOperation(value = "get all available cars in the database which can be rented")
    public Result getAllAvailableCars() {
        List<Car> cars = carService.getAvailableCars();
        return ResultGenerator.genSuccessResult(cars);
    }

    @PostMapping(value = "")
    @ApiOperation(value = "add a new car to the database")
    public Result addCar(@RequestBody Car car) {
        if (carService.addCar(car)) {
            return ResultGenerator.genSuccessResult(car.getId());
        } else {
            return ResultGenerator.genFailResult("add car fail");
        }
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "update a car's information by primary key - id")
    public Result updateCar(@RequestBody Car car) {
        try {
            if (carService.updateCar(car)) {
                return ResultGenerator.genSuccessResult();
            } else {
                return ResultGenerator.genFailResult("update car fail");
            }
        } catch (ServiceException e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }
}
