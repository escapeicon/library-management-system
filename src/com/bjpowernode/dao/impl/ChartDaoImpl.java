package com.bjpowernode.dao.impl;

import com.bjpowernode.bean.Book;
import com.bjpowernode.bean.Constant;
import com.bjpowernode.dao.ChartDao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;

public class ChartDaoImpl implements ChartDao {

    /**
     * 统计图书类别总数
     * @return
     */
    @Override
    public Map<String, Integer> countPie() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(Constant.BOOK_PATH))){
            List<Book> list = (List<Book>)inputStream.readObject();
            Map<String,Integer> hashMap = new HashMap<>();

            if(list != null){
                Iterator<Book> iterator = list.iterator();
                while(iterator.hasNext()){
                    Book book = iterator.next();

                    if(hashMap.get(book.getType()) == null){
                        hashMap.put(book.getType(),1);
                    }else{
                        hashMap.put(book.getType(),hashMap.get(book.getType()) + 1);
                    }
                }
            }

            return hashMap;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new HashMap<>();
    }

    public static void main(String[] args){
        ChartDaoImpl chartDao = new ChartDaoImpl();
        System.out.println(chartDao.countPie());
    }
}
