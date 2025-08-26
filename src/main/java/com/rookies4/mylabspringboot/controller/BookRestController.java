package com.rookies4.mylabspringboot.controller;

import com.rookies4.mylabspringboot.controller.dto.BookDTO;
import com.rookies4.mylabspringboot.entity.Book;
import com.rookies4.mylabspringboot.exception.BusinessException;
import com.rookies4.mylabspringboot.repository.BookDetailRepository;
import com.rookies4.mylabspringboot.repository.BookRepository;
import com.rookies4.mylabspringboot.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/books")
public class BookRestController {
    private BookService bookService;

    //전체 조회
    @GetMapping
    public ResponseEntity<List<BookDTO.Response>> getAllBooks(){
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    //Id로 조회
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO.Response> getBookById(@PathVariable Long id){
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    //Isbn으로 조회
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDTO.Response> getBookByIsbn(@PathVariable String isbn){
        return ResponseEntity.ok(bookService.getBookByIsbn(isbn));
    }

    //작가가 쓴 책
    @GetMapping("author/{author}")
    public ResponseEntity<List<BookDTO.Response>> getBookByAuthor(
            @PathVariable String author){
        return ResponseEntity.ok(bookService.getBooksByAuthor(author));
    }
    //제목으로 조회
    @GetMapping("title/{title}")
    public ResponseEntity<List<BookDTO.Response>> getBookByTitle(
            @PathVariable String title){
        return ResponseEntity.ok(bookService.getBooksByAuthor(title));
    }

    //책 등록
    @PostMapping
    public ResponseEntity <BookDTO.Response> createBook(@RequestBody BookDTO.Request request){
        return ResponseEntity.ok(bookService.createBook(request));
    }

    /// 수정
    @PutMapping("/{id}")
    public ResponseEntity <BookDTO.Response> updateBook(@PathVariable Long id, @RequestBody BookDTO.Request request){
        return ResponseEntity.ok(bookService.updateBook(id, request));
    }
    //삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();

    }
}
