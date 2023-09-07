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
     * 借阅书籍
     * @param book
     * @param user
     */
    @Override
    public void lendBook(Book book, User user) {

        Lend lend = new Lend();//创建借阅对象
        LocalDate now = LocalDate.now();//新建当前时间

        book.setStatus(Constant.STATUS_LEND);//设置图书为出借状态
        user.setLend(true);//设置用户为借用状态

        lend.setId(0);//设置临时编号
        lend.setLendDate(now);//设置借书日期
        lend.setReturnDate(now.plusDays(30));//设置还书日期
        lend.setBook(book);//添加图书
        lend.setUser(user);//添加用户
        lend.setStatus(Constant.LEND_LEND);//设置借阅为借出

        //更新图书
        bookDao.updateBook(book);

        System.out.println(user);

        //更新用户
        userDao.updateUserInfo(user);

        //添加出借记录
        lendDao.addLend(lend);
    }
}
