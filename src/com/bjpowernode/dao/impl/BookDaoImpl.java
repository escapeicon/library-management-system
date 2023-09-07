package com.bjpowernode.dao.impl;

import com.bjpowernode.bean.Book;
import com.bjpowernode.bean.Constant;
import com.bjpowernode.dao.BookDao;
import com.bjpowernode.util.InitDataUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {

    /**
     * ��ʼ��ͼ��
     * @return
     */
    @Override
    public List<Book> initBook() {
        ObjectInputStream inputStream = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream(Constant.BOOK_PATH));

            List<Book> list = (List<Book>) inputStream.readObject();

            if (list != null) {
                return list;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    /**
     * ��ѯ�鼮
     * @return
     */
    @Override
    public List<Book> getBook(Book book) {
        ObjectInputStream inputStream = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream(Constant.BOOK_PATH));

            List<Book> list = (List<Book>) inputStream.readObject();
            //������ֺ�isbn���ǿ��򶼷���
            if(book == null || "".equals(book.getBookName()) && "".equals(book.getIsbn())){
                return list;
            }else{
                List<Book> tempList = new ArrayList<Book>();
                for (Book perBook : list) {
                    //�������ֵ�����
                    if (perBook.getBookName().equals(book.getBookName()) && perBook.getIsbn().equals(book.getIsbn())) {
                        tempList.add(perBook);
                    }else{
                        if(perBook.getBookName().equals(book.getBookName())){
                            tempList.add(perBook);
                        }else if(perBook.getIsbn().equals(book.getIsbn())){
                            tempList.add(perBook);
                        }
                    }
                }
                return  tempList;
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * ����鼮
     * @param book
     */
    @Override
    public void addBook(Book book) {
        //��ʼ��������
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try {
            //�������������
            inputStream = new ObjectInputStream(new FileInputStream(Constant.BOOK_PATH));

            //��ȡ�����鼮
            List<Book> list = (List<Book>)inputStream.readObject();

            //���ñ��
            book.setId(list.get(list.size() - 1).getId() + 1);
            list.add(book);

            //���
            outputStream = new ObjectOutputStream(new FileOutputStream(Constant.BOOK_PATH));

            outputStream.writeObject(list);

            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            //�����ȡΪ��
            book.setId(1);
            List<Book> books = new ArrayList<Book>();
            InitDataUtil.initBook(books);
            e.printStackTrace();
        } finally {
            try {
                if(inputStream != null) {
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
     * �޸��鼮
     * @param book
     */
    @Override
    public void updateBook(Book book) {
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream(Constant.BOOK_PATH));

            //��ȡ�鼮����
            List<Book> list = (List<Book>)inputStream.readObject();

            int index = 0;
            for(Book book1 : list){
                if(book.getId() == book1.getId()){
                    index = list.indexOf(book1);
                }
            }
            list.set(index,book);

            //���
            outputStream = new ObjectOutputStream(new FileOutputStream(Constant.BOOK_PATH));

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

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ɾ���鼮
     * @param id
     */
    @Override
    public void deleteBook(int id) {
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream(Constant.BOOK_PATH));

            List<Book> list = (List<Book>) inputStream.readObject();

            Book temp = null;
            for(Book book : list){
                if(book.getId() == id){
                    temp = book;
                }
            }
            list.remove(temp);

            outputStream = new ObjectOutputStream(new FileOutputStream(Constant.BOOK_PATH));

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


    public static void main(String[] args) {
        BookDaoImpl bookDao = new BookDaoImpl();

        bookDao.initBook();
    }
}
