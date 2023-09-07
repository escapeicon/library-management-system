package com.bjpowernode.service.impl;

import com.bjpowernode.bean.Constant;
import com.bjpowernode.bean.User;
import com.bjpowernode.dao.UserDao;
import com.bjpowernode.dao.impl.UserDaoImpl;
import com.bjpowernode.service.UserService;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDAO = new UserDaoImpl();

    @Override
    public List<User> getUserInfo() {
        return userDAO.getUserInfo();
    }

    @Override
    public List<User> getLendUser() {
        return userDAO.getLendUser();
    }

    @Override
    public void addUserInfo(User user) {
        userDAO.addUserInfo(user);
    }

    @Override
    public void updateUserInfo(User user) {
        userDAO.updateUserInfo(user);
    }

    @Override
    public void deleteUserInfo(int id) {
        userDAO.deleteUserInfo(id);
    }

    @Override
    public void frozenUser(int id) {
        userDAO.frozenUser(id);
    }

    /**
     * ³äÖµ
     * @param money
     */
    @Override
    public void charge(User user,BigDecimal money) {

        user.setMoney(user.getMoney().add(money));
        if(user.getMoney().compareTo(BigDecimal.ZERO) > 0){
            user.setStatus(Constant.USER_OK);
        }

        userDAO.updateUserInfo(user);
    }
}
