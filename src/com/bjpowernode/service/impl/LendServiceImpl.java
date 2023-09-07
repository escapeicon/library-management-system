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
import com.bjpowernode.service.LendService;

import java.util.List;

public class LendServiceImpl implements LendService {
    private LendDao lendDao = new LendDaoImpl();
    private BookDao bookDao = new BookDaoImpl();
    private UserDao userDao = new UserDaoImpl();

    /**
     * 初始化借阅记录
     * @return
     */
    @Override
    public List<Lend> initLend() {
        return lendDao.initLend();
    }

    /**
     * 模糊查询借阅记录
     * @param book
     * @return
     */
    @Override
    public List<Lend> getLend(Book book) {
        return lendDao.getLend(book);
    }

    /**
     * 归还书籍
     * @param lend
     */
    @Override
    public void returnBook(Lend lend) {

        Book lendBook = lend.getBook();//获取书籍
        User lendUser = lend.getUser();//获取用户

        lendBook.setStatus(Constant.STATUS_STORAGE);//设置书籍为入库
        lendUser.setLend(false);//设置为未借阅

        bookDao.updateBook(lendBook);//更新书籍
        userDao.updateUserInfo(lendUser);//更新用户

        lendDao.returnBook(lend);
    }

    /**
     * 续期
     * @param lend
     */
    @Override
    public void renew(Lend lend) {
        Book lendBook = lend.getBook();//获取书籍
        User user = lend.getUser();//获取用户

        lendBook.setStatus(Constant.STATUS_LEND);//书籍已出库
        user.setLend(true);//用户已借阅

        userDao.updateUserInfo(user);//更新本地用户数据
        bookDao.updateBook(lendBook);//更新本地书籍数据

        lendDao.renew(lend);//更新借阅记录
    }

    /**
     * 更新借阅记录
     * @param lend
     */
    @Override
    public void updateLend(Lend lend) {
        lendDao.updateLend(lend);
    }
}
