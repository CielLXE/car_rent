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
                .andExpect(jsonPath("$.code", is(10000)))
                .andExpect(jsonPath("$.message", is("SUCCESS")))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[0].brand", is("Toyota")))
                .andExpect(jsonPath("$.data[0].model", is("Camry")))
                .andExpect(jsonPath("$.data[0].available", is(1)))
                .andExpect(jsonPath("$.data[1].id", is(2)))
                .andExpect(jsonPath("$.data[1].brand", is("BMW")))
                .andExpect(jsonPath("$.data[1].model", is("650")))
                .andExpect(jsonPath("$.data[1].available", is(0)));
    }

    @Test
    public void getAllAvailableCars() throws Exception{
        mvc.perform(get("/car/available")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(10000)))
                .andExpect(jsonPath("$.message", is("SUCCESS")))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[0].brand", is("Toyota")))
                .andExpect(jsonPath("$.data[0].model", is("Camry")))
                .andExpect(jsonPath("$.data[0].available", is(1)));
    }

    @Test
    public void getCarByIdExists() throws Exception {
        mvc.perform(get("/car/1"))
                .andExpect(jsonPath("$.code", is(10000)))
                .andExpect(jsonPath("$.message", is("SUCCESS")))
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.brand", is("Toyota")))
                .andExpect(jsonPath("$.data.model", is("Camry")))
                .andExpect(jsonPath("$.data.available", is(1)));
    }

    @Test
    public void getCarByIdNotExist() throws Exception {
        mvc.perform(get("/car/3"))
                .andExpect(jsonPath("$.code", is(10001)))
                .andExpect(jsonPath("$.message", is("car not exist")));

    }

    @Test
    public void addCar() throws Exception {
        Car car = new Car(3,"Toyota", "Camry", (byte)CarAvailableStatusEnum.available.getCode());
        String request =om.writeValueAsString(car);

        mvc.perform(post("/car")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(10000)))
                .andExpect(jsonPath("$.message", is("SUCCESS")))
                .andExpect(jsonPath("$.data", is(3)));
        mvc.perform(get("/car/3"))
                .andExpect(jsonPath("$.code", is(10000)))
                .andExpect(jsonPath("$.message", is("SUCCESS")))
                .andExpect(jsonPath("$.data.id", is(3)))
                .andExpect(jsonPath("$.data.brand", is("Toyota")))
                .andExpect(jsonPath("$.data.model", is("Camry")))
                .andExpect(jsonPath("$.data.available", is(1)));
    }

    @Test
    public void updateCarExists() throws Exception {
        Car car = new Car(1,"Toyota", "Century", null);
        String request = om.writeValueAsString(car);

        mvc.perform(put("/car/1")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(10000)))
                .andExpect(jsonPath("$.message", is("SUCCESS")));
        mvc.perform(get("/car/1"))
                .andExpect(jsonPath("$.code", is(10000)))
                .andExpect(jsonPath("$.message", is("SUCCESS")))
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.brand", is("Toyota")))
                .andExpect(jsonPath("$.data.model", is("Century")))
                .andExpect(jsonPath("$.data.available", is(1)));
    }

    @Test
    public void updateCarExistsModifyAvailable() throws Exception {
        Car car = new Car(1,"Toyota", "Century", (byte)CarAvailableStatusEnum.unavailable.getCode());
        String request = om.writeValueAsString(car);

        mvc.perform(put("/car/1")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(10001)))
                .andExpect(jsonPath("$.message", is("don't allow to update a car's available")));
    }

    @Test
    public void updateCarNotExist() throws Exception {
        Car car = new Car(3,"Toyota", "Century", (byte)CarAvailableStatusEnum.available.getCode());
        String request = om.writeValueAsString(car);

        mvc.perform(put("/car/3")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(10001)))
                .andExpect(jsonPath("$.message", is("car not exist")));
    }

}
