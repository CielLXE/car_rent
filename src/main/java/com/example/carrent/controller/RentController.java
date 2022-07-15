package com.example.carrent.controller;

import com.example.carrent.bean.Car;
import com.example.carrent.bean.Rent;
import com.example.carrent.bean.Result;
import com.example.carrent.services.RentService;
import com.example.carrent.utils.ResultGenerator;
import com.example.carrent.utils.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/rent", produces = { MediaType.APPLICATION_JSON_VALUE })
@Api
public class RentController {
    @Autowired
    private RentService rentService;

    @GetMapping(value = "")
    @ApiOperation(value = "get all rents")
    public Result getAllRents() {
        List<Rent> rents = rentService.getAllRents();
        return ResultGenerator.genSuccessResult(rents);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "get rent by primary key - id")
    public Result getRentById(@PathVariable Integer id) {
        Rent rent = rentService.getRentById(id);
        if (rent != null) {
            return ResultGenerator.genSuccessResult(rent);
        } else {
            return ResultGenerator.genFailResult("rent not exist");
        }
    }

    @GetMapping(value = "/user/{id}")
    @ApiOperation(value = "get rent by user id")
    public Result getRentByUserId(@PathVariable Integer id) {
        List<Rent> rents = rentService.getRentByUserId(id);
        return ResultGenerator.genSuccessResult(rents);
    }

    @GetMapping(value = "/car/{id}")
    @ApiOperation(value = "get rent by car id")
    public Result getRentByCarId(@PathVariable Integer id) {
        List<Rent> rents = rentService.getRentByCarId(id);
        return ResultGenerator.genSuccessResult(rents);

    }

    @PostMapping(value = "")
    @ApiOperation(value = "add a new rent, set this car to be unavailable")
    public Result addRent(@RequestBody Rent rent) {
        try {
            if (rentService.addRent(rent)) {
                return ResultGenerator.genSuccessResult(rent.getId());
            } else {
                return ResultGenerator.genFailResult("Fail to add a new rent");
            }
        } catch (ServiceException e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}/return")
    @ApiOperation(value = "return a rent by primary key - id, set this car to be available")
    public Result returnRent(@PathVariable Integer id) {
        try {
            if (rentService.returnRent(id)) {
                return ResultGenerator.genSuccessResult();
            } else {
                return ResultGenerator.genFailResult("Fail to return a rent");
            }
        }catch (ServiceException e){
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

}
