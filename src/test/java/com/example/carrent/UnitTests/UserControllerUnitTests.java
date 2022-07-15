package com.example.carrent.UnitTests;

import com.example.carrent.bean.User;
import com.example.carrent.enums.UserRoleEnum;
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
        "INSERT INTO user (id,name,password,user_role) VALUES (1,\"jason\", \"12345\",1);",
        "INSERT INTO user (id,name,password,user_role) VALUES (2,\"admin\", \"admin\",2);"})
public class UserControllerUnitTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Test
    public void getAllUsers() throws Exception{
        mvc.perform(get("/user")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(10000)))
                .andExpect(jsonPath("$.message", is("SUCCESS")))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[0].name", is("jason")))
                .andExpect(jsonPath("$.data[0].password", is("12345")))
                .andExpect(jsonPath("$.data[0].userRole", is(1)))
                .andExpect(jsonPath("$.data[1].id", is(2)))
                .andExpect(jsonPath("$.data[1].name", is("admin")))
                .andExpect(jsonPath("$.data[1].password", is("admin")))
                .andExpect(jsonPath("$.data[1].userRole", is(2)));
    }

    @Test
    public void getUserByIdExists() throws Exception {
        mvc.perform(get("/user/1"))
                .andExpect(jsonPath("$.code", is(10000)))
                .andExpect(jsonPath("$.message", is("SUCCESS")))
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.name", is("jason")))
                .andExpect(jsonPath("$.data.password", is("12345")))
                .andExpect(jsonPath("$.data.userRole", is(1)));
    }

    @Test
    public void getUserByIdNotExist() throws Exception {
        mvc.perform(get("/user/3"))
                .andExpect(jsonPath("$.code", is(10001)))
                .andExpect(jsonPath("$.message", is("user not exist")));
    }

    @Test
    public void userLoginRight() throws Exception {
        User user = new User(null, "jason", "12345", null);
        String request = om.writeValueAsString(user);

        mvc.perform(post("/user/login")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(10000)))
                .andExpect(jsonPath("$.message", is("SUCCESS")))
                .andExpect(jsonPath("$.data.name", is("jason")))
                .andExpect(jsonPath("$.data.password", is("12345")))
                .andExpect(jsonPath("$.data.userRole", is(1)));
    }

    @Test
    public void userLoginNotExist() throws Exception {
        User user = new User(null, "sam", "12345", null);
        String request = om.writeValueAsString(user);

        mvc.perform(post("/user/login")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(10001)))
                .andExpect(jsonPath("$.message", is("user not exist")));
    }

    @Test
    public void userLoginWithTheWrongPassword() throws Exception {
        User user = new User(null, "jason", "54321", null);
        String request = om.writeValueAsString(user);

        mvc.perform(post("/user/login")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(10001)))
                .andExpect(jsonPath("$.message", is("username and password did not match")));
    }

    @Test
    public void addUserExist() throws Exception {
        User user = new User(3, "jason", "54321", (byte)UserRoleEnum.normal.getCode());
        String request = om.writeValueAsString(user);

        mvc.perform(post("/user")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(10001)))
                .andExpect(jsonPath("$.message", is("name exists")));
    }

    @Test
    public void addUserNotExists() throws Exception {
        User user = new User(3, "sue", "54321", (byte)UserRoleEnum.normal.getCode());
        String request = om.writeValueAsString(user);

        mvc.perform(post("/user")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(10000)))
                .andExpect(jsonPath("$.message", is("SUCCESS")))
                .andExpect(jsonPath("$.data", is(3)));
        mvc.perform(get("/user/3"))
                .andExpect(jsonPath("$.code", is(10000)))
                .andExpect(jsonPath("$.message", is("SUCCESS")))
                .andExpect(jsonPath("$.data.id", is(3)))
                .andExpect(jsonPath("$.data.name", is("sue")))
                .andExpect(jsonPath("$.data.password", is("54321")))
                .andExpect(jsonPath("$.data.userRole", is(1)));
    }

    @Test
    public void updateUserExists() throws Exception {
        User user = new User(1, "jason", "31245", (byte)UserRoleEnum.normal.getCode());
        String request = om.writeValueAsString(user);

        mvc.perform(put("/user/1")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(10000)))
                .andExpect(jsonPath("$.message", is("SUCCESS")));
        mvc.perform(get("/user/1"))
                .andExpect(jsonPath("$.code", is(10000)))
                .andExpect(jsonPath("$.message", is("SUCCESS")))
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.name", is("jason")))
                .andExpect(jsonPath("$.data.password", is("31245")))
                .andExpect(jsonPath("$.data.userRole", is(1)));
    }

    @Test
    public void updateUserWithOtherExistsName() throws Exception {
        User user = new User(1, "admin", "31245", (byte)UserRoleEnum.normal.getCode());
        String request = om.writeValueAsString(user);

        mvc.perform(put("/user/1")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(10001)))
                .andExpect(jsonPath("$.message", is("name exists")));
    }

    @Test
    public void updateUserNotExist() throws Exception {
        User user = new User(3, "sam", "31245", (byte)UserRoleEnum.normal.getCode());
        String request = om.writeValueAsString(user);

        mvc.perform(put("/user/3")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(10001)))
                .andExpect(jsonPath("$.message", is("user not exist")));
    }


}
