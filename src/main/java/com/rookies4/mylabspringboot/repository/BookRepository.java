package com.rookies4.mylabspringboot.repository;

import com.rookies4.mylabspringboot.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {
    //isbn을 이용해 책 찾기
    Optional<Book> findByIsbn(String isbn);

    //작가가 쓴 책찾기
    List<Book> findByAuthor(String author);
}
