package com.bjpowernode.dao;

import com.bjpowernode.bean.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserDao {
    List<User> getUserInfo();
    List<User> getLendUser();
    void addUserInfo(User user);
    void updateUserInfo(User user);
    void deleteUserInfo(int id);
    void frozenUser(int id);
}
