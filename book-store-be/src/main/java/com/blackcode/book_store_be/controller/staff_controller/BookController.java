package com.blackcode.book_store_be.controller.staff_controller;

import com.blackcode.book_store_be.dto.ImageLoadDto;
import com.blackcode.book_store_be.dto.books.BooksReq;
import com.blackcode.book_store_be.dto.books.BooksRes;
import com.blackcode.book_store_be.service.BooksService;
import com.blackcode.book_store_be.service.FileStorageService;
import com.blackcode.book_store_be.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/staff/books")
public class BookController {

    private final BooksService booksService;

    public BookController(BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping("/getBooksListAll")
    public ResponseEntity<ApiResponse<List<BooksRes>>> getBooksListAll(){
        List<BooksRes>  booksResList = booksService.getBooksListAll();
        return ResponseEntity.ok(ApiResponse.success("Book retrieved successfully", 200, booksResList));
    }

    @GetMapping("/getBooksFindById/{id}")
    public ResponseEntity<ApiResponse<BooksRes>> getBooksFindById(@PathVariable("id") Long id){
        BooksRes booksRes = booksService.getBooksFindById(id);
        return ResponseEntity.ok(ApiResponse.success("Books found", 200, booksRes));
    }

    @PostMapping("/addBooks")
    public ResponseEntity<ApiResponse<BooksRes>> addBooks(
            @RequestPart("book") BooksReq booksReq,
            @RequestPart(value = "bookImage", required = false) MultipartFile bookImage){

        BooksRes booksRes = booksService.addBooks(booksReq, bookImage);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Book created", 201, booksRes));
    }

    @PutMapping("/updateBooks/{id}")
    public ResponseEntity<ApiResponse<BooksRes>> updateBooks(
            @PathVariable("id") Long id,
            @RequestPart("book") BooksReq booksReq,
            @RequestPart(value = "bookImage", required = false) MultipartFile bookImage){

        BooksRes booksRes = booksService.updateBook(id, booksReq, bookImage);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Book created", 201, booksRes));
    }

    @DeleteMapping("/deleteBooksById/{id}")
    public ResponseEntity<ApiResponse<String>> deleteBooksById(@PathVariable("id") Long id){
        String rtn = booksService.deleteBook(id);
        return ResponseEntity.ok(ApiResponse.success("Category success Delete", 200, rtn));
    }

    @GetMapping("/images/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
        ImageLoadDto imageLoadDto = booksService.loadImage(filename);
        return new ResponseEntity<>(imageLoadDto.getImage(), imageLoadDto.getHeaders(), HttpStatus.OK);
    }
}
