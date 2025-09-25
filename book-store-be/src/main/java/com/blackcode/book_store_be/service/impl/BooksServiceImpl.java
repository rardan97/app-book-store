package com.blackcode.book_store_be.service.impl;

import com.blackcode.book_store_be.dto.books.BooksReq;
import com.blackcode.book_store_be.dto.books.BooksRes;
import com.blackcode.book_store_be.dto.category.CategoryRes;
import com.blackcode.book_store_be.model.Books;
import com.blackcode.book_store_be.model.Category;
import com.blackcode.book_store_be.repository.BooksRepository;
import com.blackcode.book_store_be.repository.CategoryRepository;
import com.blackcode.book_store_be.service.BooksService;
import com.blackcode.book_store_be.service.CategoryService;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BooksServiceImpl implements BooksService {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<BooksRes> getBooksListAll() {
        List<Books> books = booksRepository.findAll();
        List<BooksRes> booksResList = new ArrayList<>();
        for (Books rowBooks : books){
            BooksRes booksRes = new BooksRes();
            booksRes.setBookId(rowBooks.getBookId());
            booksRes.setBookTitle(rowBooks.getBookTitle());
            booksRes.setAuthor(rowBooks.getAuthor());
            booksRes.setDescription(rowBooks.getDescription());
            booksRes.setPrice(rowBooks.getPrice());
            booksRes.setStock(rowBooks.getStock());

            CategoryRes categoryRes = new CategoryRes();
            categoryRes.setCategoryId(rowBooks.getCategory().getCategoryId());
            categoryRes.setCategoryName(rowBooks.getCategory().getCategoryName());
            booksRes.setCategory(categoryRes);

            booksResList.add(booksRes);
        }
        return booksResList;
    }

    @Override
    public List<BooksRes> getBooksPublicListAll() {
        List<Books> books = booksRepository.findAll();
        List<BooksRes> booksResList = new ArrayList<>();
        for (Books rowBooks : books){
            BooksRes booksRes = new BooksRes();
            booksRes.setBookId(rowBooks.getBookId());
            booksRes.setBookTitle(rowBooks.getBookTitle());
            booksRes.setAuthor(rowBooks.getAuthor());
            booksRes.setDescription(rowBooks.getDescription());
            booksRes.setPrice(rowBooks.getPrice());
            booksRes.setStock(rowBooks.getStock());
            booksRes.setBookImage(rowBooks.getBookImage());
            CategoryRes categoryRes = new CategoryRes();
            categoryRes.setCategoryId(rowBooks.getCategory().getCategoryId());
            categoryRes.setCategoryName(rowBooks.getCategory().getCategoryName());
            booksRes.setCategory(categoryRes);

            booksResList.add(booksRes);
        }
        return booksResList;
    }

    @Override
    public BooksRes getBooksFindById(Long bookId) {
        Optional<Books> books = booksRepository.findById(bookId);
        BooksRes booksRes = null;
        if(books.isPresent()){
            booksRes = new BooksRes();
            booksRes.setBookId(books.get().getBookId());
            booksRes.setBookTitle(books.get().getBookTitle());
            booksRes.setAuthor(books.get().getAuthor());
            booksRes.setDescription(books.get().getDescription());
            booksRes.setPrice(books.get().getPrice());
            booksRes.setStock(books.get().getStock());
            booksRes.setBookImage(books.get().getBookImage());

            CategoryRes categoryRes = new CategoryRes();
            categoryRes.setCategoryId(books.get().getCategory().getCategoryId());
            categoryRes.setCategoryName(books.get().getCategory().getCategoryName());
            booksRes.setCategory(categoryRes);

            return booksRes;
        }
        return null;
    }

    @Override
    public BooksRes addBooks(BooksReq booksReq) {
        BooksRes booksRes = null;
        Optional<Category> category = categoryRepository.findById(Long.valueOf(booksReq.getCategoryId()));
        if (category.isPresent()){
            booksRes = new BooksRes();
            Books book = new Books();
            book.setBookTitle(booksReq.getBookTitle());
            book.setAuthor(booksReq.getAuthor());
            book.setDescription(booksReq.getDescription());
            book.setPrice(booksReq.getPrice());
            book.setStock(booksReq.getStock());
            System.out.println("Book Image Service :"+booksReq.getBookImage());
            book.setBookImage(booksReq.getBookImage());
            book.setCategory(category.get());
            book = booksRepository.save(book);

            booksRes.setBookId(book.getBookId());
            booksRes.setBookTitle(book.getBookTitle());
            booksRes.setAuthor(book.getAuthor());
            booksRes.setDescription(book.getDescription());
            booksRes.setPrice(book.getPrice());
            booksRes.setStock(book.getStock());
            booksRes.setBookImage(book.getBookImage());

            CategoryRes categoryRes = new CategoryRes();
            System.out.println("categoryId "+book.getCategory().getCategoryId());
            categoryRes.setCategoryId(book.getCategory().getCategoryId());
            categoryRes.setCategoryName(book.getCategory().getCategoryName());
            booksRes.setCategory(categoryRes);

        }
        return booksRes;
    }

    @Override
    public BooksRes updateBook(Long bookId, BooksReq booksReq) {
        BooksRes booksRes = null;
        Optional<Books> books = booksRepository.findById(bookId);
        if (books.isPresent()){
            Optional<Category> category = categoryRepository.findById(Long.valueOf(booksReq.getCategoryId()));
            if(category.isPresent()){
                Books book = books.get();
                booksRes = new BooksRes();

                book.setBookTitle(booksReq.getBookTitle());
                book.setAuthor(booksReq.getAuthor());
                book.setDescription(booksReq.getDescription());
                book.setPrice(booksReq.getPrice());
                book.setStock(booksReq.getStock());
                book.setBookImage(booksReq.getBookImage());
                book.setCategory(category.get());
                book = booksRepository.save(book);
                booksRes.setBookId(book.getBookId());
                booksRes.setBookTitle(book.getBookTitle());
                booksRes.setAuthor(book.getAuthor());
                booksRes.setDescription(book.getDescription());
                booksRes.setPrice(book.getPrice());
                booksRes.setStock(book.getStock());
                booksRes.setBookImage(book.getBookImage());

                CategoryRes categoryRes = new CategoryRes();
                categoryRes.setCategoryId(book.getCategory().getCategoryId());
                categoryRes.setCategoryName(book.getCategory().getCategoryName());
                booksRes.setCategory(categoryRes);
            }
        }
        return booksRes;
    }

    @Override
    public String deleteBook(Long bookId) {

        Optional<Books> book = booksRepository.findById(bookId);
        System.out.println("check id :"+bookId);
        if(book.isPresent()){
            System.out.println("Proccess Delete Data Id : "+bookId);
            booksRepository.deleteById(bookId);
            return "success";
        }
        return null;
    }
}