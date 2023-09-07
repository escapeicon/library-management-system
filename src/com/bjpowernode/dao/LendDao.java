package com.bjpowernode.dao;

import com.bjpowernode.bean.Book;
import com.bjpowernode.bean.Lend;

import java.util.List;

public interface LendDao {
    List<Lend> initLend();
    List<Lend> getLend(Book book);
    void addLend(Lend lend);
    void returnBook(Lend lend);
    void renew(Lend lend);
    void updateLend(Lend lend);
}
