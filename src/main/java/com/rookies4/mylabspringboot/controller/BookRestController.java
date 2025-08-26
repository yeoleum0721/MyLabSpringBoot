package com.rookies4.mylabspringboot.controller;

import com.rookies4.mylabspringboot.entity.Book;
import com.rookies4.mylabspringboot.exception.BusinessException;
import com.rookies4.mylabspringboot.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/books")
public class BookRestController {
    private final BookRepository bookRepository;

    //책 등록
    @PostMapping
    public ResponseEntity <Book> createBook(@RequestBody Book book){
        bookRepository.save(book);
        return ResponseEntity.ok(book);
    }

    //전체 조회
    @GetMapping
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    //Id로 조회
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        Book bookId = bookRepository.findById(id).orElseThrow(() -> new BusinessException("Book not Founded", HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(bookId);
    }

    //Isbn으로 조회
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn){
        Book bookId = bookRepository.findByIsbn(isbn).orElseThrow(() -> new BusinessException("Book not Founded", HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(bookId);
    }

//    //작가가 쓴 책
//    @GetMapping("author/{author}")
//    public List <Book> getBookByAuthor(@PathVariable String author){
//        List<Book> byAuthor = bookRepository.findByAuthor(author);
//        return byAuthor;
//    }

    /// 수정
    @PatchMapping("/{id}")
    public ResponseEntity <Book> updateBook(@PathVariable Long id, @RequestBody Book book){
        Book bookFound = bookRepository.findById(id).orElseThrow(() -> new BusinessException("User Not Found", HttpStatus.NOT_FOUND));

        bookFound.setPrice(book.getPrice());
        Book updateBook = bookRepository.save(bookFound);
        return ResponseEntity.ok(updateBook);
    }
    //삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id){
        Book bookFound = bookRepository.findById(id).orElseThrow(() -> new BusinessException("User Not Found", HttpStatus.NOT_FOUND));
        bookRepository.delete(bookFound);
        return ResponseEntity.ok("book이 삭제되었습니다");

    }
}
