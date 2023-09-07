package com.bjpowernode.service;

import com.bjpowernode.bean.Book;
import com.bjpowernode.bean.Lend;
import com.bjpowernode.bean.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    List<User> getUserInfo();
    List<User> getLendUser();
    void addUserInfo(User user);
    void updateUserInfo(User user);
    void deleteUserInfo(int id);
    void frozenUser(int id);
    void charge(User user,BigDecimal money);
}
