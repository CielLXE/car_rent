package com.example.carrent.UnitTests;

import com.example.carrent.bean.Car;
import com.example.carrent.enums.CarAvailableStatusEnum;
import com.example.carrent.services.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "application.properties")
@AutoConfigureMockMvc
@Transactional
@Rollback(value = true)
@Sql(statements = {
        "INSERT INTO car (id,brand,model,available) VALUES (1,\"Toyota\", \"Camry\",1);",
        "INSERT INTO car (id,brand,model,available) VALUES (2,\"BMW\", \"650\",0);"})
public class CarControllerUnitTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CarService carService;


    @Test
    public void getAllCars() throws Exception{
        mvc.perform(get("/car")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].brand", is("Toyota")))
                .andExpect(jsonPath("$[0].model", is("Camry")))
                .andExpect(jsonPath("$[0].available", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].brand", is("BMW")))
                .andExpect(jsonPath("$[1].model", is("650")))
                .andExpect(jsonPath("$[1].available", is(0)));
    }

    @Test
    public void getAllAvailableCars() throws Exception{
        mvc.perform(get("/car/available")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].brand", is("Toyota")))
                .andExpect(jsonPath("$[0].model", is("Camry")))
                .andExpect(jsonPath("$[0].available", is(1)));
    }

    @Test
    public void getCarByIdExists() throws Exception {
        mvc.perform(get("/car/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.brand", is("Toyota")))
                .andExpect(jsonPath("$.model", is("Camry")))
                .andExpect(jsonPath("$.available", is(1)));
    }

    @Test
    public void getCarByIdNotExist() throws Exception {
        mvc.perform(get("/car/3"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addCar() throws Exception {
        Car car = new Car(3,"Toyota", "Camry", (byte)CarAvailableStatusEnum.available.getCode());
        String request =om.writeValueAsString(car);

        mvc.perform(post("/car")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateCarExists() throws Exception {
        Car car = new Car(1,"Toyota", "Century", (byte)CarAvailableStatusEnum.available.getCode());
        String request = om.writeValueAsString(car);

        mvc.perform(put("/car/1")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mvc.perform(get("/car/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.brand", is("Toyota")))
                .andExpect(jsonPath("$.model", is("Century")))
                .andExpect(jsonPath("$.available", is(1)));
    }

    @Test
    public void updateCarNotExist() throws Exception {
        Car car = new Car(3,"Toyota", "Century", (byte)CarAvailableStatusEnum.available.getCode());
        String request = om.writeValueAsString(car);

        mvc.perform(put("/car/3")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
