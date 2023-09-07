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
     * ��ʼ�����ļ�¼
     * @return
     */
    @Override
    public List<Lend> initLend() {
        return lendDao.initLend();
    }

    /**
     * ģ����ѯ���ļ�¼
     * @param book
     * @return
     */
    @Override
    public List<Lend> getLend(Book book) {
        return lendDao.getLend(book);
    }

    /**
     * �黹�鼮
     * @param lend
     */
    @Override
    public void returnBook(Lend lend) {

        Book lendBook = lend.getBook();//��ȡ�鼮
        User lendUser = lend.getUser();//��ȡ�û�

        lendBook.setStatus(Constant.STATUS_STORAGE);//�����鼮Ϊ���
        lendUser.setLend(false);//����Ϊδ����

        bookDao.updateBook(lendBook);//�����鼮
        userDao.updateUserInfo(lendUser);//�����û�

        lendDao.returnBook(lend);
    }

    /**
     * ����
     * @param lend
     */
    @Override
    public void renew(Lend lend) {
        Book lendBook = lend.getBook();//��ȡ�鼮
        User user = lend.getUser();//��ȡ�û�

        lendBook.setStatus(Constant.STATUS_LEND);//�鼮�ѳ���
        user.setLend(true);//�û��ѽ���

        userDao.updateUserInfo(user);//���±����û�����
        bookDao.updateBook(lendBook);//���±����鼮����

        lendDao.renew(lend);//���½��ļ�¼
    }

    /**
     * ���½��ļ�¼
     * @param lend
     */
    @Override
    public void updateLend(Lend lend) {
        lendDao.updateLend(lend);
    }
}
