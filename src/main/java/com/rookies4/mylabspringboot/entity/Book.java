package com.rookies4.mylabspringboot.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;

import java.time.LocalDate;

@Entity
@Table(name="books")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="book_id")
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

    @OneToOne(fetch = FetchType.LAZY,
            mappedBy = "book",
            cascade = CascadeType.ALL)
    private BookDetail bookDetail;
}
