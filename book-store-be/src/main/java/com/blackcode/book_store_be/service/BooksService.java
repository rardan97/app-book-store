package com.blackcode.book_store_be.service;

import com.blackcode.book_store_be.dto.ImageLoadDto;
import com.blackcode.book_store_be.dto.books.BooksReq;
import com.blackcode.book_store_be.dto.books.BooksRes;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface BooksService {

    List<BooksRes> getBooksListAll();

    List<BooksRes> getBooksPublicListAll();

    BooksRes getBooksFindById(Long bookId);

    BooksRes addBooks(BooksReq booksReq, MultipartFile bookImage);

    BooksRes updateBook(Long bookId, BooksReq booksReq, MultipartFile bookImage);

    String deleteBook(Long bookId);

    ImageLoadDto loadImage(String filename);



}
