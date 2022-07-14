package com.example.carrent.controller;

import com.example.carrent.bean.Car;
import com.example.carrent.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/car", produces = { MediaType.APPLICATION_JSON_VALUE })
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping(value = "")
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping(value = "/{id}")
    public Car getCarById(@PathVariable Integer id) {
        Car car = carService.getCarById(id);
        if (car != null) {
            return car;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Car did not exist");
        }
    }

    @GetMapping(value = "/available")
    public List<Car> getAllAvailableCars() {
        return carService.getAvailableCars();
    }

    @PostMapping(value = "")
    public int addCar(@RequestBody Car car) {
        if (carService.addCar(car)) {
            return car.getId();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Add car fail");
        }
    }

    @PutMapping(value = "/{id}")
    public void updateCar(@RequestBody Car car) {
        if (carService.updateCar(car)) {
            throw new ResponseStatusException(HttpStatus.OK, "Car updated");
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Car not exist or available modification prohibited");
        }
    }
}
