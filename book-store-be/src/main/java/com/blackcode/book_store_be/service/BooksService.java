package com.blackcode.book_store_be.service;

import com.blackcode.book_store_be.dto.books.BooksReq;
import com.blackcode.book_store_be.dto.books.BooksRes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BooksService {

    List<BooksRes> getBooksListAll();

    List<BooksRes> getBooksPublicListAll();

    BooksRes getBooksFindById(Long bookId);

    BooksRes addBooks(BooksReq booksReq);

    BooksRes updateBook(Long bookId, BooksReq booksReq);

    String deleteBook(Long bookId);





}
