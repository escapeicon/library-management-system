package com.bjpowernode.util;

import com.bjpowernode.bean.Book;
import com.bjpowernode.bean.Constant;
import com.bjpowernode.bean.Lend;
import com.bjpowernode.bean.User;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InitDataUtil {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        initUser(null);
        initBook(null);
        initLend(null);

        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(Constant.LEND_PATH));

        List list = (List)inputStream.readObject();

    }

    /**
     * 初始化用户数据
     * @param list
     */
    public static void initUser(List<User> list){

        ObjectOutputStream output = null;
        //新建目录地址
        File directory = new File("user/");
        //创建文件地址
        File file = new File(Constant.USER_PATH);

        List<User> users = new ArrayList<>();

        //如果目录不存在新建目录
        if(!directory.exists()){
            directory.mkdir();
        }

        //给本地序列化对象
        try {
            output = new ObjectOutputStream(new FileOutputStream(file));

            if(list == null){
                users.add(new User(1001,"张三",Constant.USER_OK,BigDecimal.TEN,false));
                users.add(new User(1002,"李四",Constant.USER_OK,BigDecimal.TEN,false));
                users.add(new User(1003,"王五",Constant.USER_OK,BigDecimal.TEN,false));
                users.add(new User(1004,"赵六",Constant.USER_OK,BigDecimal.TEN,false));
                users.add(new User(1005,"老七",Constant.USER_OK,BigDecimal.TEN,false));
                users.add(new User(1006,"老八",Constant.USER_OK,BigDecimal.TEN,false));
            }else{
                users = list;
            }

            output.writeObject(users);

            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(output != null){
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    /**
     * 初始化图书数据
     * @param list
     */
    public static void initBook(List<Book> list){
        File directory = new File("book/");
        File file = new File(Constant.BOOK_PATH);

        if(!directory.exists()){
            directory.mkdir();
        }

        ObjectOutputStream outputStream = null;

        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(Constant.BOOK_PATH));

            List<Book> books = new ArrayList<>();

            if(list == null){
                list = new ArrayList<Book>();
                list.add(new Book(1, "java实战入门", "张三", Constant.TYPE_COMPUTER, "12-987", "XX出版社", Constant.STATUS_STORAGE));
                list.add(new Book(2, "java进阶", "李四", Constant.TYPE_COMPUTER, "12-97", "某某出版社", Constant.STATUS_STORAGE));
                list.add(new Book(3, "java实战", "xx", Constant.TYPE_COMPUTER, "12-9887", "某某出版社", Constant.STATUS_STORAGE));
                list.add(new Book(4, "金瓶梅", "xx", Constant.TYPE_LITERATURE, "12-9887", "某某出版社", Constant.STATUS_STORAGE));
                list.add(new Book(5, "时间管理", "罗祥", Constant.TYPE_MANAGEMENT, "12-9887", "某某出版社", Constant.STATUS_STORAGE));
                list.add(new Book(6, "母猪的产后护理", "我也不知道", Constant.TYPE_ECONOMY, "12-9887", "某某出版社", Constant.STATUS_STORAGE));
            }

            outputStream.writeObject(list);

            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 初始化借阅
     * @param list
     */
    public static void initLend(List<Lend> list){
        File directory = new File("lend/");
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputUserStream = null;
        ObjectInputStream inputBookStream = null;
        ObjectOutputStream outputUserStream = null;
        ObjectOutputStream outputBookStream = null;

        try {
            if(!directory.exists()){
                directory.mkdir();
            }

            inputUserStream = new ObjectInputStream(new FileInputStream(Constant.USER_PATH));
            inputBookStream = new ObjectInputStream(new FileInputStream(Constant.BOOK_PATH));

            //获取用户书籍
            List<User> userList = (List<User>)inputUserStream.readObject();
            //获取图书
            List<Book> bookList = (List<Book>)inputBookStream.readObject();

            outputStream = new ObjectOutputStream(new FileOutputStream(Constant.LEND_PATH));
            outputBookStream = new ObjectOutputStream(new FileOutputStream(Constant.BOOK_PATH));
            outputUserStream = new ObjectOutputStream(new FileOutputStream(Constant.USER_PATH));

            if(list == null){
                list = new ArrayList<Lend>();
                User user = userList.get(0);
                Book book = bookList.get(0);
                user.setLend(true);
                book.setStatus(Constant.STATUS_LEND);

                LocalDate now = LocalDate.now();
                Lend lend = new Lend(1,book,user,Constant.LEND_LEND,now,now.plusDays(30));
                list.add(lend);
            }

            outputStream.writeObject(list);
            outputBookStream.writeObject(bookList);
            outputUserStream.writeObject(userList);

            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
                try {
                    if(outputStream != null){
                        outputStream.close();
                    }
                    if(inputUserStream != null){
                        inputUserStream.close();
                    }
                    if(inputBookStream != null){
                        inputBookStream.close();
                    }
                    if(outputUserStream != null){
                        outputUserStream.close();
                    }
                    if(outputBookStream != null){
                        outputBookStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

}
