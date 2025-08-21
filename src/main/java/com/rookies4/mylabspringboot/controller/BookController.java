package com.rookies4.mylabspringboot.controller;

import com.rookies4.mylabspringboot.controller.dto.BookDTO;
import com.rookies4.mylabspringboot.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDTO.BookResponse>> getAllBooks(){
        List<BookDTO.BookResponse> bookList = bookService.getAllBooks();
        return ResponseEntity.ok(bookList);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO.BookResponse>getBookById(@PathVariable Long id){
        BookDTO.BookResponse bookById = bookService.getBookById(id);
        return ResponseEntity.ok(bookById);
    }
    @GetMapping("/{isbn}/")
    public ResponseEntity<BookDTO.BookResponse>getBooksByAuthor(@PathVariable String isbn){
        BookDTO.BookResponse bookByIsbn = bookService.getBookByIsbn(isbn);
        return ResponseEntity.ok(bookByIsbn);
    }
    @GetMapping("/{author}/")
    public ResponseEntity<List<BookDTO.BookResponse>> getBookByIsbn(@PathVariable String author){
        bookService.getBooksByAuthor(author);
        return ResponseEntity.ok(bookService.getBooksByAuthor(author));
    }
    @PostMapping
    public ResponseEntity<BookDTO.BookResponse>createBook(@Valid @RequestBody
                                                              BookDTO.BookCreateRequest request){
        return ResponseEntity.ok(bookService.createBook(request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookDTO.BookResponse>updateBook(@PathVariable Long id, @Valid @RequestBody
                                                          BookDTO.BookUpdateRequest request) {
        return ResponseEntity.ok(bookService.updateBook(id, request));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
