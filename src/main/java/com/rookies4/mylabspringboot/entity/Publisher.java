package com.rookies4.mylabspringboot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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

@Column(nullable=false)
private String name;

@Column(nullable=false)
private LocalDate establishedDate;

@Column(nullable=false)
private String address;

@OneToMany(mappedBy="publisher", cascade=CascadeType.ALL, fetch= FetchType.LAZY)
private List<Book> books;
}