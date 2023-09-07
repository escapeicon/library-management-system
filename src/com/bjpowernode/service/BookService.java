package com.bjpowernode.service;

import com.bjpowernode.bean.Book;
import com.bjpowernode.bean.User;

import java.util.List;

public interface BookService {
    List<Book> initBook();
    List<Book> getBook(Book book);
    void addBook(Book book);
    void updateBook(Book book);
    void deleteBook(int id);
    void lendBook(Book book, User user);
}
