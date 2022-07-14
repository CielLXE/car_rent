package com.example.carrent.controller;

import com.example.carrent.bean.Rent;
import com.example.carrent.services.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/rent", produces = { MediaType.APPLICATION_JSON_VALUE })
public class RentController {
    @Autowired
    private RentService rentService;

    @GetMapping(value = "")
    public List<Rent> getAllRents() {
        return rentService.getAllRents();
    }

    @GetMapping(value = "/{id}")
    public Rent getRentById(@PathVariable Integer id) {
        Rent rent = rentService.getRentById(id);
        if (rent != null) {
            return rent;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rent not exist");
        }
    }

    @PostMapping(value = "")
    public int addRent(@RequestBody Rent rent) {
        if (rentService.addRent(rent)) {
            return rent.getId();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fail to add a new rent");
        }
    }

    @PutMapping(value = "/{id}/return")
    public void returnRent(@PathVariable Integer id) {
        if (rentService.returnRent(id)) {
            throw new ResponseStatusException(HttpStatus.OK, "Rent returned");
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rent not exist or already returned");
        }
    }

}
