package com.rookies4.mylabspringboot.controller;

import com.rookies4.mylabspringboot.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/books")
public class BookRestController {
    private BookRepository bookRepository;

//    List<Book> getAllBooks(){}
//    ResponseEntity<Book> getBookById(Long id){}
//    ResponseEntity<Book> getBookByIsbn(String isbn){}
//    List <Book> getBookByAuthor(String author){}
//    ResponseEntity <Book> createBook(Book book){}
//    ResponseEntity <Book> updateBook(Long id, Book book){}
//    ResponseEntity<Void> deleteBook(Long id){}
}
