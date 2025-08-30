package com.rookies4.mylabspringboot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="publishers")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Publisher{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="publisher_id")
    private Long id;

    @Column(nullable=false,unique = true)
    private String name;

    @Column(nullable=false)
    private LocalDate establishedDate;

    @Column(nullable=false)
    private String address;

    @OneToMany(mappedBy="publisher", cascade=CascadeType.ALL, fetch= FetchType.LAZY)
    @Builder.Default // Builder 패턴 사용 시 기본값 설정
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book){
        books.add(book);
    }
    public void remove(Book book){
        books.remove(book);
    }
}