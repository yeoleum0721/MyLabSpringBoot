package com.rookies4.mylabspringboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;

import java.time.LocalDate;

@Entity
@Table(name="books")
@Getter @Setter
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(unique = true, nullable = false)
    private String isbn;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private LocalDate publishDate;
}
