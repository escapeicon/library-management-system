package com.bjpowernode.dao.impl;

import com.bjpowernode.bean.Book;
import com.bjpowernode.bean.Constant;
import com.bjpowernode.bean.Lend;
import com.bjpowernode.dao.LendDao;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LendDaoImpl implements LendDao {

    /**
     * 初始化借阅信息
     * @return
     */
    @Override
    public List<Lend> initLend() {
        ObjectInputStream inputStream = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream(Constant.LEND_PATH));

            List<Lend> list = (List<Lend>)inputStream.readObject();

            return list;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally{
            try {
                if(inputStream != null){
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 条件查询
     * @param book
     * @return
     */
    @Override
    public List<Lend> getLend(Book book) {
        try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(Constant.LEND_PATH))){

            //获取本地借阅记录
            List<Lend> list = (List<Lend>) inputStream.readObject();

            //如果书籍是null
            if(book == null || "".equals(book.getBookName()) && "".equals(book.getIsbn())){
                return list;
            }else{
                List<Lend> tempLendList = new ArrayList<>();
                for(Lend lend : list){
                    Book lendBook = lend.getBook();
                    if(lendBook.getBookName().equals(book.getBookName()) && lendBook.getIsbn().equals(book.getIsbn())){
                        tempLendList.add(lend);
                    }else{
                        if(lendBook.getBookName().equals(book.getBookName())){
                            tempLendList.add(lend);
                        }else if(lendBook.getIsbn().equals(book.getIsbn())){
                            tempLendList.add(lend);
                        }
                    }
                }
                return tempLendList;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        return null;
    }

    /**
     * 添加出借记录
     * @param lend
     */
    @Override
    public void addLend(Lend lend) {
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream(Constant.LEND_PATH));

            List<Lend> lendList = (List<Lend>) inputStream.readObject();//获取借阅记录

            lend.setId(lendList.get(lendList.size() - 1).getId() + 1);//设置添加的借阅记录的编号

            outputStream = new ObjectOutputStream(new FileOutputStream(Constant.LEND_PATH));

            lendList.add(lend);

            outputStream.writeObject(lendList);//写入借阅记录

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

    /**
     * 归还书籍
     * @param lend
     */
    @Override
    public void returnBook(Lend lend) {
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream(Constant.LEND_PATH));

            //读取借阅记录
            List<Lend> lendList = (List<Lend>)inputStream.readObject();

            //查找相同的记录并删除
            Iterator<Lend> iterator = lendList.iterator();

            while(iterator.hasNext()){
                Lend localLend = iterator.next();
                if(localLend.getId() == lend.getId()){
                    localLend.setReturnDate(LocalDate.now());
                    localLend.setStatus(Constant.LEND_RETURN);
                }
            }

            outputStream = new ObjectOutputStream(new FileOutputStream(Constant.LEND_PATH));

            outputStream.writeObject(lendList);//写出

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

    /**
     * 续期借阅记录
     * @param lend
     */
    @Override
    public void renew(Lend lend) {
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream(Constant.LEND_PATH));

            List<Lend> lendList = (List<Lend>) inputStream.readObject();

            Iterator<Lend> iterator = lendList.iterator();
            while(iterator.hasNext()){
                Lend localLend = iterator.next();

                //匹配借阅记录
                if(localLend.getId() == lend.getId()){
                    localLend.setLendDate(LocalDate.now());
                    localLend.setStatus(Constant.LEND_LEND);
                    localLend.setReturnDate(LocalDate.now().plusDays(30));
                }

            }

            outputStream = new ObjectOutputStream(new FileOutputStream(Constant.LEND_PATH));

            outputStream.writeObject(lendList);

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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 更新借阅记录
     * @param lend
     */
    @Override
    public void updateLend(Lend lend) {
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream(Constant.LEND_PATH));

            List<Lend> lendList = (List<Lend>) inputStream.readObject();

            Lend tempLend = null;
            for(Lend perLend : lendList){
                if(perLend.getId() == lend.getId()){
                    tempLend = perLend;
                }
            }
            lendList.set(lendList.indexOf(tempLend),lend);

            outputStream = new ObjectOutputStream(new FileOutputStream(Constant.LEND_PATH));

            outputStream.writeObject(lendList);

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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
