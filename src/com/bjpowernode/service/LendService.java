package com.bjpowernode.service;

import com.bjpowernode.bean.Book;
import com.bjpowernode.bean.Lend;

import java.util.List;

public interface LendService {
    List<Lend> initLend();
    List<Lend> getLend(Book book);
    void returnBook(Lend lend);
    void renew(Lend lend);
    void updateLend(Lend lend);
}
