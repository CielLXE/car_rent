package com.example.carrent.services;

import com.example.carrent.bean.CarExample;
import com.example.carrent.bean.User;
import com.example.carrent.bean.UserExample;
import com.example.carrent.dao.UserDao;
import com.example.carrent.utils.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    /**
     * get user by primary key - id
     *
     * @param id
     * @return user
     */
    public User getUserById(Integer id) {
        return userDao.selectByPrimaryKey(id);
    }

    /**
     * get all users in the database
     *
     * @return list of users
     */
    public List<User> getAllUsers() {
        return userDao.selectAllUsers();
    }

    /**
     * login and verify if user legal : user exists and password matches
     * @param user with name and password
     * @return user added id and user_role
     */
    public User login(User user) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(user.getName());
        List<User> store = userDao.selectByExample(example);
        if (store == null || store.size() == 0) {
            throw new ServiceException("user not exist");
        }
        if (!store.get(0).getPassword().equals(user.getPassword())) {
            throw new ServiceException("username and password did not match");
        }
        return store.get(0);
    }

    /**
     * add a new user with unique name
     *
     * @param user with name and password
     * @return true:success false:fail
     */
    public boolean addUser(User user) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(user.getName());
        List<User> store = userDao.selectByExample(example);
        if (store != null && store.size() != 0) {
            throw new ServiceException("name exists");
        }
        return userDao.insert(user) == 1;
    }

    /**
     * update a user's information by primary key - id
     *
     * @param user
     * @return true:success false:fail
     */
    public boolean updateUser(User user) {
        User now = userDao.selectByPrimaryKey(user.getId());
        //not exist
        if (now == null) {
            throw new ServiceException("user not exist");
        }
        //judge name
        if (user.getName() != null && !user.getName().equals(now.getName())) {
            UserExample example = new UserExample();
            UserExample.Criteria criteria = example.createCriteria();
            criteria.andNameEqualTo(user.getName());
            List<User> store = userDao.selectByExample(example);
            if (store != null && store.size() != 0) {
                throw new ServiceException("name exists");
            }
        }
        return userDao.updateByPrimaryKeySelective(user) == 1;
    }
}
