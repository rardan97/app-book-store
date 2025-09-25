package com.blackcode.book_store_be.controller.staff_controller;

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

    @Value("${upload.dir}")
    private String uploadDir;

    private final BooksService booksService;

    private final FileStorageService storageService;

    public BookController(BooksService booksService, FileStorageService storageService) {
        this.booksService = booksService;
        this.storageService = storageService;
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

        BooksReq bookReq = new BooksReq();
        bookReq.setBookTitle(booksReq.getBookTitle());
        bookReq.setAuthor(booksReq.getAuthor());
        bookReq.setPrice(booksReq.getPrice());
        bookReq.setStock(booksReq.getStock());
        bookReq.setDescription(booksReq.getDescription());
        bookReq.setCategoryId(booksReq.getCategoryId());
        if (bookImage != null && !bookImage.isEmpty()) {
            String imagePath = storageService.store(bookImage);
            bookReq.setBookImage(imagePath);
        }

        BooksRes booksRes = booksService.addBooks(bookReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Category created", 201, booksRes));
    }

    @PutMapping("/updateBooks/{id}")
    public ResponseEntity<ApiResponse<BooksRes>> updateBooks(
            @PathVariable("id") Long id,
            @RequestPart("book") BooksReq booksReq,
            @RequestPart(value = "bookImage", required = false) MultipartFile bookImage){

        BooksReq bookReq = new BooksReq();
        bookReq.setBookTitle(booksReq.getBookTitle());
        bookReq.setAuthor(booksReq.getAuthor());
        bookReq.setPrice(booksReq.getPrice());
        bookReq.setStock(booksReq.getStock());
        bookReq.setDescription(booksReq.getDescription());
        bookReq.setCategoryId(booksReq.getCategoryId());

        if (bookImage != null && !bookImage.isEmpty()) {
            BooksRes dataBook = booksService.getBooksFindById(id);
            if(dataBook.getBookImage() != null && !dataBook.getBookImage().isEmpty()){
                String filename = dataBook.getBookImage();
                if(filename.equals(bookImage.getOriginalFilename())){
                    System.out.println("image user tidak di ganti");
                }else{
                    File file = new File(uploadDir + File.separator + filename);
                    if (file.exists()) {
                        storageService.delete(dataBook.getBookImage());
                    } else {
                        System.out.println("Gambar Tidak Tersedia di Storage");
                    }
                    String imagePath = storageService.store(bookImage);
                    bookReq.setBookImage(imagePath);
                }
            }else{
                String imagePath = storageService.store(bookImage);
                bookReq.setBookImage(imagePath);
            }
        }

        BooksRes booksRes = booksService.updateBook(id, bookReq);
        return ResponseEntity.ok(ApiResponse.success("Category Update", 200, booksRes));
    }

    @DeleteMapping("/deleteBooksById/{id}")
    public ResponseEntity<ApiResponse<String>> deleteBooksById(@PathVariable("id") Long id){
        String rtn = booksService.deleteBook(id);
        return ResponseEntity.ok(ApiResponse.success("Category success Delete", 200, rtn));
    }

    @GetMapping("/images/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
        try {
            byte[] image = storageService.load(filename);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
