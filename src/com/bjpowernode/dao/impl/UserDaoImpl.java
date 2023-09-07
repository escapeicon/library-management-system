package com.bjpowernode.dao.impl;

import com.bjpowernode.bean.Constant;
import com.bjpowernode.bean.Lend;
import com.bjpowernode.bean.User;
import com.bjpowernode.dao.UserDao;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserDaoImpl implements UserDao {
    /**
     * 初始化用户
     * @return
     */
    @Override
    public List<User> getUserInfo() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Constant.USER_PATH))){
            List<User> list = (List<User>) ois.readObject();

            return list;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new ArrayList();
    }

    /**
     * 获取可借阅的用户信息
     * @return
     */
    @Override
    public List<User> getLendUser() {

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(Constant.USER_PATH))) {

            List<User> list = (List<User>)inputStream.readObject();

            Iterator<User> iterator = list.iterator();

            List<User> tempList = new ArrayList();

            while(iterator.hasNext()){
                //获取单个用户
                User user = iterator.next();
                //如果账户被冻结或账户已有借阅记录则不给显示，反之显示用户
                if(!user.isLend() && !Constant.USER_FROZEN.equals(user.getStatus())){
                    tempList.add(user);
                }
            }

            return tempList;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 添加用户信息
     */
    @Override
    public void addUserInfo(User user) {
        //初始化对象流
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;

        try {
            inputStream = new ObjectInputStream(new FileInputStream(Constant.USER_PATH));

            List<User> users = (List<User>)inputStream.readObject();

            if(users != null){
                User lastUser = users.get(users.size() - 1);
                user.setId(lastUser.getId() + 1);
                users.add(user);
            }else{
                users = new ArrayList<User>();
                user.setId(1001);
                users.add(user);
            }

            outputStream = new ObjectOutputStream(new FileOutputStream(Constant.USER_PATH));

            outputStream.writeObject(users);

            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 修改用户信息
     * @param user
     */
    @Override
    public void updateUserInfo(User user) {
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream(Constant.USER_PATH));

            List<User> userList = (List<User>) inputStream.readObject();

            //遍历寻找目标对象
            if(userList != null){

                User tempUser = null;
                for(User perUser : userList){
                    if(perUser.getId() == user.getId()){
                        tempUser = perUser;
                    }
                }
                userList.set(userList.indexOf(tempUser),user);

                //输出
                outputStream = new ObjectOutputStream(new FileOutputStream(Constant.USER_PATH));

                outputStream.writeObject(userList);

                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if(inputStream != null){
                    inputStream.close();
                }
                if(outputStream != null){
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除用户信息
     * @param id
     */
    @Override
    public void deleteUserInfo(int id) {
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream(Constant.USER_PATH));

            List<User> list = (List<User>)inputStream.readObject();

            if(list != null){
                User user = null;
                for(User perUser : list){
                    if(perUser.getId() == id){
                        user = perUser;
                    }
                }
                if(user != null){
                    list.remove(user);
                }

                outputStream = new ObjectOutputStream(new FileOutputStream(Constant.USER_PATH));

                outputStream.writeObject(list);

                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if(inputStream != null){
                    inputStream.close();
                }
                if(outputStream != null){
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * 冻结账户
     * @param id
     */
    @Override
    public void frozenUser(int id) {
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream(Constant.USER_PATH));

            List<User> list = (List<User>)inputStream.readObject();

            for(User perUser : list){
                if(perUser.getId() == id){
                    perUser.setStatus(Constant.USER_FROZEN);
                }
            }

            outputStream = new ObjectOutputStream(new FileOutputStream(Constant.USER_PATH));

            outputStream.writeObject(list);

            outputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if(inputStream != null){
                    inputStream.close();
                }
                if(outputStream != null){
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        UserDaoImpl userDao = new UserDaoImpl();

        userDao.addUserInfo(new User(0,"李四",Constant.USER_OK,BigDecimal.TEN,false));

        for(User user : userDao.getUserInfo()){
            System.out.println(user);
        }
    }
}
