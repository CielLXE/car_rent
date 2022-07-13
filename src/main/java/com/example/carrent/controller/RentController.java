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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "rent not exist");
        }
    }

    @PostMapping(value = "")
    public void addRent(@RequestBody Rent Rent) {
        if (rentService.addRent(Rent)) {
            throw new ResponseStatusException(HttpStatus.CREATED, "Rent created");
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong");
        }
    }

    @PutMapping(value = "/{id}")
    public void putRent(@RequestBody Rent Rent) {
        if (rentService.updateRent(Rent)) {
            throw new ResponseStatusException(HttpStatus.OK, "Rent updated");
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong");
        }
    }

    @PutMapping(value = "/{id}/return")
    public void returnRent(@PathVariable Integer id) {
        if (rentService.returnRent(id)) {
            throw new ResponseStatusException(HttpStatus.OK, "Rent returned");
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong");
        }
    }

}
