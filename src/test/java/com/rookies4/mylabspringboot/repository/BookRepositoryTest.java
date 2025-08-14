package com.rookies4.mylabspringboot.repository;

import com.rookies4.mylabspringboot.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BookRepositoryTest{
    @Autowired
    private BookRepository bookRepository;

    @Test
    void tessCreateBook(){
        // 준비단계
        Book book1 = new Book();
        book1.setTitle("스프링 부트 입문");
        book1.setAuthor("홍길동");
        book1.setIsbn("9788956746425");
        book1.setPrice(30000);
        book1.setPublishDate(LocalDate.of(2025,5,7));

        Book book2 = new Book();
        book2.setTitle("JPA 프로그래밍");
        book2.setAuthor("박둘리");
        book2.setIsbn("9788956746432");

        book2.setPrice(305000);
        book2.setPublishDate(LocalDate.of(2025,4,30));

        // 실행단계
        Book savedBook1 = bookRepository.save(book1);
        Book savedBook2 = bookRepository.save(book2);

        // 검증단계
        assertThat(savedBook1).isNotNull();
        assertThat(savedBook1.getTitle()).isEqualTo("스프링 부트 입문");
        assertThat(savedBook2).isNotNull();
        assertThat(savedBook2.getTitle()).isEqualTo("JPA 프로그래밍");
    }

    @Test
    void testFindByIsbn(){
        //findByIsbn()호출
        Book bookByIsbn = bookRepository.findByIsbn("9788956746425").orElseGet(() -> new Book());
        assertThat(bookByIsbn.getTitle()).isEqualTo("스프링 부트 입문");
    }
    @Test
    void testFindByAuthor(){
        List<Book> bookByAuthor = bookRepository.findByAuthor("홍길동");
        assertThat(bookByAuthor).isNotNull();
    }
    @Test
    void testUpdate(){
        Book book = bookRepository.findByIsbn("9788956746432").orElseThrow(() -> new RuntimeException("Book Not Found"));
        book.setPrice(35000);
        bookRepository.save(book);
    }
    @Test
    void testDeleteBook(){
        Book book = bookRepository.findByIsbn("9788956746425").orElseThrow(() -> new RuntimeException("Book Not Found"));
        bookRepository.delete(book);
        Optional<Book> deletedBook = bookRepository.findByIsbn("9788956746425");
        assertThat(deletedBook).isEmpty();
    }
}