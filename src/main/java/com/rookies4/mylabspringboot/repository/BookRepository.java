package com.rookies4.mylabspringboot.repository;

import com.rookies4.mylabspringboot.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    //isbn을 이용해 책 찾기
    Optional<Book> findByIsbn(String isbn);

    //작가가 쓴 책찾기
    List<Book> findByAuthorContainingIgnoreCase(String author);

    List<Book> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.bookDetail WHERE b.id = :id")
    Optional<Book> findByIdWithBookDetail(@Param("id") Long id);

    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.bookDetail WHERE b.isbn = :isbn")
    Optional<Book> findByIsbnWithBookDetail(@Param("isbn") String isbn);

    boolean existsByIsbn(String isbn);

    List<Book> findByPublisherId(Long publisherId);

    Long countByPublisherId(Long publisherId);

    @Query("SELECT b FROM Book b JOIN FETCH b.bookDetail JOIN FETCH b.publisher WHERE b.id = :id")
    Optional<Book> findByIdWithAllDetail(@Param("id") Long id);
}
