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
     * ��ʼ���û�����
     * @param list
     */
    public static void initUser(List<User> list){

        ObjectOutputStream output = null;
        //�½�Ŀ¼��ַ
        File directory = new File("user/");
        //�����ļ���ַ
        File file = new File(Constant.USER_PATH);

        List<User> users = new ArrayList<>();

        //���Ŀ¼�������½�Ŀ¼
        if(!directory.exists()){
            directory.mkdir();
        }

        //���������л�����
        try {
            output = new ObjectOutputStream(new FileOutputStream(file));

            if(list == null){
                users.add(new User(1001,"����",Constant.USER_OK,BigDecimal.TEN,false));
                users.add(new User(1002,"����",Constant.USER_OK,BigDecimal.TEN,false));
                users.add(new User(1003,"����",Constant.USER_OK,BigDecimal.TEN,false));
                users.add(new User(1004,"����",Constant.USER_OK,BigDecimal.TEN,false));
                users.add(new User(1005,"����",Constant.USER_OK,BigDecimal.TEN,false));
                users.add(new User(1006,"�ϰ�",Constant.USER_OK,BigDecimal.TEN,false));
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
     * ��ʼ��ͼ������
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
                list.add(new Book(1, "javaʵս����", "����", Constant.TYPE_COMPUTER, "12-987", "XX������", Constant.STATUS_STORAGE));
                list.add(new Book(2, "java����", "����", Constant.TYPE_COMPUTER, "12-97", "ĳĳ������", Constant.STATUS_STORAGE));
                list.add(new Book(3, "javaʵս", "xx", Constant.TYPE_COMPUTER, "12-9887", "ĳĳ������", Constant.STATUS_STORAGE));
                list.add(new Book(4, "��ƿ÷", "xx", Constant.TYPE_LITERATURE, "12-9887", "ĳĳ������", Constant.STATUS_STORAGE));
                list.add(new Book(5, "ʱ�����", "����", Constant.TYPE_MANAGEMENT, "12-9887", "ĳĳ������", Constant.STATUS_STORAGE));
                list.add(new Book(6, "ĸ��Ĳ�����", "��Ҳ��֪��", Constant.TYPE_ECONOMY, "12-9887", "ĳĳ������", Constant.STATUS_STORAGE));
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
     * ��ʼ������
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

            //��ȡ�û��鼮
            List<User> userList = (List<User>)inputUserStream.readObject();
            //��ȡͼ��
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
