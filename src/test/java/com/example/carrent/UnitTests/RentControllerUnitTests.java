package com.example.carrent.UnitTests;


import com.example.carrent.bean.Rent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.IsNull;
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

import java.text.SimpleDateFormat;
import java.util.Date;

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
        "INSERT INTO car (id,brand,model,available) VALUES (2,\"BMW\", \"650\",0);",
        "INSERT INTO user (id,name,password,user_role) VALUES (1,\"jason\", \"12345\",1);",
        "INSERT INTO user (id,name,password,user_role) VALUES (2,\"sue\", \"54321\",1);",
        "INSERT INTO user (id,name,password,user_role) VALUES (3,\"admin\", \"admin\",2);",
        "INSERT INTO rent (id,car_id,user_id,rent_date,supposed_return_date,actual_return_date) VALUES (1, 1, 2,\"2022-07-10\",\"2022-07-12\",\"2022-07-11\");",
        "INSERT INTO rent (id,car_id,user_id,rent_date,supposed_return_date) VALUES (2, 2, 1,\"2022-07-11\",\"2022-07-15\");"})
public class RentControllerUnitTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Test
    public void getAllRents() throws Exception{
        mvc.perform(get("/rent")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(10000)))
                .andExpect(jsonPath("$.message", is("SUCCESS")))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[0].carId", is(1)))
                .andExpect(jsonPath("$.data[0].userId", is(2)))
                .andExpect(jsonPath("$.data[0].rentDate", is("2022-07-10")))
                .andExpect(jsonPath("$.data[0].supposedReturnDate", is("2022-07-12")))
                .andExpect(jsonPath("$.data[0].actualReturnDate", is("2022-07-11")))
                .andExpect(jsonPath("$.data[1].id", is(2)))
                .andExpect(jsonPath("$.data[1].carId", is(2)))
                .andExpect(jsonPath("$.data[1].userId", is(1)))
                .andExpect(jsonPath("$.data[1].rentDate", is("2022-07-11")))
                .andExpect(jsonPath("$.data[1].supposedReturnDate", is("2022-07-15")))
                .andExpect(jsonPath("$.data[1].actualReturnDate", is(IsNull.nullValue())));

    }

    @Test
    public void getRentByIdExists() throws Exception {
        mvc.perform(get("/rent/1"))
                .andExpect(jsonPath("$.code", is(10000)))
                .andExpect(jsonPath("$.message", is("SUCCESS")))
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.carId", is(1)))
                .andExpect(jsonPath("$.data.userId", is(2)))
                .andExpect(jsonPath("$.data.rentDate", is("2022-07-10")))
                .andExpect(jsonPath("$.data.supposedReturnDate", is("2022-07-12")))
                .andExpect(jsonPath("$.data.actualReturnDate", is("2022-07-11")));
    }

    @Test
    public void getRentByIdNotExist() throws Exception {
        mvc.perform(get("/rent/3"))
                .andExpect(jsonPath("$.code", is(10001)))
                .andExpect(jsonPath("$.message", is("rent not exist")));
    }

    @Test
    public void addRent() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateStart = simpleDateFormat.parse("2022-07-16");
        Date dateEnd = simpleDateFormat.parse("2022-07-18");
        Rent rent = new Rent(3, 1, 1, dateStart, dateEnd, null);
        String request = om.writeValueAsString(rent);

        mvc.perform(post("/rent")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(10000)))
                .andExpect(jsonPath("$.message", is("SUCCESS")))
                .andExpect(jsonPath("$.data", is(3)));

        mvc.perform(get("/rent/3"))
                .andExpect(jsonPath("$.code", is(10000)))
                .andExpect(jsonPath("$.message", is("SUCCESS")))
                .andExpect(jsonPath("$.data.id", is(3)))
                .andExpect(jsonPath("$.data.carId", is(1)))
                .andExpect(jsonPath("$.data.userId", is(1)))
                .andExpect(jsonPath("$.data.rentDate", is("2022-07-16")))
                .andExpect(jsonPath("$.data.supposedReturnDate", is("2022-07-18")))
                .andExpect(jsonPath("$.data.actualReturnDate", is(IsNull.nullValue())));
    }

    @Test
    public void addRentCarUnavailable() throws Exception {
        Rent rent = new Rent(null,2,1,new Date(),new Date(),null);
        String request = om.writeValueAsString(rent);

        mvc.perform(post("/rent")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(10001)))
                .andExpect(jsonPath("$.message", is("car not available")));
    }

    @Test
    public void addRentCarNotExist() throws Exception {
        Rent rent = new Rent(null,3,1,new Date(),new Date(),null);
        String request = om.writeValueAsString(rent);

        mvc.perform(post("/rent")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(10001)))
                .andExpect(jsonPath("$.message", is("car not exist")));
    }

    @Test
    public void addRentCarWithAdmin() throws Exception {
        Rent rent = new Rent(null,1,3,new Date(),new Date(),null);
        String request = om.writeValueAsString(rent);

        mvc.perform(post("/rent")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(10001)))
                .andExpect(jsonPath("$.message", is("user is an admin")));
    }

    @Test
    public void addRentCarWithNotExistUser() throws Exception {
        Rent rent = new Rent(null,1,4,new Date(),new Date(),null);
        String request = om.writeValueAsString(rent);

        mvc.perform(post("/rent")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(10001)))
                .andExpect(jsonPath("$.message", is("user not exist")));
    }

    @Test
    public void returnRent() throws Exception {
        mvc.perform(put("/rent/2/return")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(10000)))
                .andExpect(jsonPath("$.message", is("SUCCESS")));
        mvc.perform(get("/car/2"))
                .andExpect(jsonPath("$.code", is(10000)))
                .andExpect(jsonPath("$.message", is("SUCCESS")))
                .andExpect(jsonPath("$.data.id", is(2)))
                .andExpect(jsonPath("$.data.available", is(1)));
    }

    @Test
    public void returnRentAlreadyReturned() throws Exception {
        mvc.perform(put("/rent/1/return")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(10001)))
                .andExpect(jsonPath("$.message", is("rent already returned")));
    }

    @Test
    public void returnRentNotExists() throws Exception {
        mvc.perform(put("/rent/3/return")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(10001)))
                .andExpect(jsonPath("$.message", is("rent not exist")));
    }

}
