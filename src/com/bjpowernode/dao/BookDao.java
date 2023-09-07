package com.bjpowernode.dao;

import com.bjpowernode.bean.Book;

import java.util.List;

public interface BookDao {
    List<Book> initBook();
    List<Book> getBook(Book book);
    void addBook(Book book);
    void updateBook(Book book);
    void deleteBook(int id);
}
