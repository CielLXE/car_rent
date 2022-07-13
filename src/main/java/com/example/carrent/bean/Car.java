package com.example.carrent.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Car {
    private Integer id;

    private String brand;

    private String model;

    /**
     * 1: available to rent 0: not available to rent
     */
    private Byte available;

}