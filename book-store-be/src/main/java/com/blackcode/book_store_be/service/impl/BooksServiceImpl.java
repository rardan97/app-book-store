package com.blackcode.book_store_be.service.impl;

import com.blackcode.book_store_be.dto.ImageLoadDto;
import com.blackcode.book_store_be.dto.books.BooksReq;
import com.blackcode.book_store_be.dto.books.BooksRes;
import com.blackcode.book_store_be.dto.category.CategoryRes;
import com.blackcode.book_store_be.exception.DataNotFoundException;
import com.blackcode.book_store_be.model.Books;
import com.blackcode.book_store_be.model.Category;
import com.blackcode.book_store_be.repository.BooksRepository;
import com.blackcode.book_store_be.repository.CategoryRepository;
import com.blackcode.book_store_be.service.BooksService;
import com.blackcode.book_store_be.service.CategoryService;
import com.blackcode.book_store_be.service.FileStorageService;
import com.blackcode.book_store_be.utils.ApiResponse;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Book;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BooksServiceImpl implements BooksService {

    @Value("${upload.dir}")
    private String uploadDir;

    private final BooksRepository booksRepository;

    private final CategoryRepository categoryRepository;

    private final FileStorageService storageService;

    public BooksServiceImpl(BooksRepository booksRepository, CategoryRepository categoryRepository, FileStorageService storageService) {
        this.booksRepository = booksRepository;
        this.categoryRepository = categoryRepository;
        this.storageService = storageService;
    }


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
    public BooksRes addBooks(BooksReq booksReq, MultipartFile bookImage) {
        Category category = categoryRepository.findById(Long.valueOf(booksReq.getCategoryId()))
                .orElseThrow(() -> new DataNotFoundException("Category not found with id :"+ booksReq.getCategoryId()));

        Books book = new Books();
        book.setBookTitle(booksReq.getBookTitle());
        book.setAuthor(booksReq.getAuthor());
        book.setDescription(booksReq.getDescription());
        book.setPrice(booksReq.getPrice());
        book.setStock(booksReq.getStock());

        if(bookImage != null && !bookImage.isEmpty()){
            String imagePath = storageService.store(bookImage);
            booksReq.setBookImage(imagePath);
            book.setBookImage(booksReq.getBookImage());
        }
        book.setCategory(category);

        book = booksRepository.save(book);

        return mapToBooksRes(book);
    }

    @Override
    public BooksRes updateBook(Long bookId, BooksReq booksReq, MultipartFile bookImage) {
        Books book = booksRepository.findById(bookId)
                .orElseThrow(() -> new DataNotFoundException("Book not found with id: "+ bookId));

        Category category = categoryRepository.findById(Long.valueOf(booksReq.getCategoryId()))
                .orElseThrow(() -> new DataNotFoundException("Category not found with id:"+booksReq.getCategoryId()));

        book.setBookTitle(booksReq.getBookTitle());
        book.setAuthor(booksReq.getAuthor());
        book.setDescription(booksReq.getDescription());
        book.setPrice(booksReq.getPrice());
        book.setStock(booksReq.getStock());
        updateBookImage(book, bookImage);
        book.setCategory(category);
        book = booksRepository.save(book);

        return mapToBooksRes(book);
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

    @Override
    public ImageLoadDto loadImage(String filename) {
        byte[] image = storageService.load(filename);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ImageLoadDto(image, headers);
    }

    private void updateBookImage(Books books, MultipartFile booksImage) {
        if (booksImage == null || booksImage.isEmpty()) return;

        String existingImage = books.getBookImage();
        if (existingImage != null && !existingImage.isEmpty()) {
            if (!existingImage.equals(booksImage.getOriginalFilename())) {
                File file = new File(uploadDir + File.separator + existingImage);
                if (file.exists()) {
                    storageService.delete(existingImage);
                } else {
                    System.out.println("Gambar Tidak Tersedia di Storage");
                }

                String newImagePath = storageService.store(booksImage);
                books.setBookImage(newImagePath);
            } else {
                System.out.println("Image user tidak di ganti");
            }
        } else {
            String newImagePath = storageService.store(booksImage);
            books.setBookImage(newImagePath);
        }
    }

    private BooksRes mapToBooksRes(Books books) {
        BooksRes booksRes = new BooksRes();
        booksRes.setBookId(books.getBookId());
        booksRes.setBookTitle(books.getBookTitle());
        booksRes.setDescription(books.getDescription());
        booksRes.setPrice(books.getPrice());
        booksRes.setStock(books.getStock());
        booksRes.setAuthor(books.getAuthor());
        booksRes.setBookImage(books.getBookImage());

        CategoryRes categoryRes = new CategoryRes();
        categoryRes.setCategoryId(books.getCategory().getCategoryId());
        categoryRes.setCategoryName(books.getCategory().getCategoryName());
        booksRes.setCategory(categoryRes);

        return booksRes;
    }


}