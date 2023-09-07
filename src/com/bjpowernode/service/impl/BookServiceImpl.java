package com.bjpowernode.service.impl;

import com.bjpowernode.bean.Book;
import com.bjpowernode.bean.Constant;
import com.bjpowernode.bean.Lend;
import com.bjpowernode.bean.User;
import com.bjpowernode.dao.BookDao;
import com.bjpowernode.dao.LendDao;
import com.bjpowernode.dao.UserDao;
import com.bjpowernode.dao.impl.BookDaoImpl;
import com.bjpowernode.dao.impl.LendDaoImpl;
import com.bjpowernode.dao.impl.UserDaoImpl;
import com.bjpowernode.service.BookService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.util.List;

public class BookServiceImpl implements BookService {
    private BookDao bookDao = new BookDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private LendDao lendDao = new LendDaoImpl();

    @Override
    public List<Book> initBook() {
        return bookDao.initBook();
    }

    @Override
    public List<Book> getBook(Book book) {
        return bookDao.getBook(book);
    }

    @Override
    public void addBook(Book book) {
        bookDao.addBook(book);
    }

    @Override
    public void updateBook(Book book) {
        bookDao.updateBook(book);
    }

    @Override
    public void deleteBook(int id) {
        bookDao.deleteBook(id);
    }

    /**
     * �����鼮
     * @param book
     * @param user
     */
    @Override
    public void lendBook(Book book, User user) {

        Lend lend = new Lend();//�������Ķ���
        LocalDate now = LocalDate.now();//�½���ǰʱ��

        book.setStatus(Constant.STATUS_LEND);//����ͼ��Ϊ����״̬
        user.setLend(true);//�����û�Ϊ����״̬

        lend.setId(0);//������ʱ���
        lend.setLendDate(now);//���ý�������
        lend.setReturnDate(now.plusDays(30));//���û�������
        lend.setBook(book);//���ͼ��
        lend.setUser(user);//����û�
        lend.setStatus(Constant.LEND_LEND);//���ý���Ϊ���

        //����ͼ��
        bookDao.updateBook(book);

        System.out.println(user);

        //�����û�
        userDao.updateUserInfo(user);

        //��ӳ����¼
        lendDao.addLend(lend);
    }
}
