package com.rookies4.mylabspringboot.service;

import com.rookies4.mylabspringboot.controller.dto.BookDTO;
import com.rookies4.mylabspringboot.entity.Book;
import com.rookies4.mylabspringboot.entity.BookDetail;
import com.rookies4.mylabspringboot.entity.Publisher;
import com.rookies4.mylabspringboot.exception.BusinessException;
import com.rookies4.mylabspringboot.exception.ErrorCode;
import com.rookies4.mylabspringboot.repository.BookDetailRepository;
import com.rookies4.mylabspringboot.repository.BookRepository;
import com.rookies4.mylabspringboot.repository.PublisherRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final BookDetailRepository bookDetailRepository;
    private final PublisherRepository publisherRepository;

    //전체 조회
    public List<BookDTO.Response> getAllBooks(){
        return bookRepository.findAll()
                .stream()
                .map(BookDTO.Response::fromEntity)
                .toList();
    }
    //ID로 조회
    public BookDTO.Response getBookById(Long id){
        Book bookById = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Book", "id", id));
        return BookDTO.Response.fromEntity(bookById);
    }

    //Isbn으로 조회
    public BookDTO.Response getBookByIsbn(String isbn){
        Book bookByIsbn = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Book", "ISBN", isbn));
        return BookDTO.Response.fromEntity(bookByIsbn);
    }
    //작가가 쓴책 조회
    public List<BookDTO.Response> getBooksByAuthor(String author){
        return bookRepository.findByAuthorContainingIgnoreCase(author)
                .stream()
                .map(BookDTO.Response::fromEntity)
                .toList();
    }
    //제목으로 조회
    public List<BookDTO.Response>  getBookByTitle(String title){
        return bookRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(BookDTO.Response::fromEntity)
                .toList();
    }

    //책 등록
    @Transactional
    public BookDTO.Response createBook(BookDTO.Request request){
        //isbn이 존재할 경우
        if (bookRepository.existsByIsbn(request.getIsbn())){
            throw new BusinessException(ErrorCode.ISBN_DUPLICATE, request.getIsbn());
        }
        Publisher publisher = publisherRepository.findById(request.getPublisherId())
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Publisher", "id", request.getPublisherId()));

        //새 BookEntity 생성
        Book bookEntity = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .price(request.getPrice())
                .publishDate(request.getPublishDate())
                .publisher(publisher)
                .build();

        if(request.getDetailRequest() != null){
            BookDetail bookDetailEntity = BookDetail.builder()
                    .description(request.getDetailRequest().getDescription())
                    .language(request.getDetailRequest().getLanguage())
                    .pageCount(request.getDetailRequest().getPageCount())
                    .publisher(request.getDetailRequest().getPublisher())
                    .coverImageUrl(request.getDetailRequest().getCoverImageUrl())
                    .edition(request.getDetailRequest().getEdition())
                    .book(bookEntity)
                    .build();
            bookEntity.setBookDetail(bookDetailEntity);
        }
        Book savedBook = bookRepository.save(bookEntity);
        return BookDTO.Response.fromEntity(savedBook);
    }
    //책 세부사항 변경
    @Transactional
    public BookDTO.Response updateBook(Long id,
                                           BookDTO.Request request) {
        Book existBook = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Book", "id", id));

        // Check if another book already has the ISBN
        if (!existBook.getIsbn().equals(request.getIsbn()) &&
                bookRepository.existsByIsbn(request.getIsbn())) {
            throw new BusinessException(ErrorCode.ISBN_DUPLICATE, request.getIsbn());
        }
        existBook.setTitle(request.getTitle());
        existBook.setAuthor(request.getAuthor());
        existBook.setIsbn(request.getIsbn());
        existBook.setPrice(request.getPrice());
        existBook.setPublishDate(request.getPublishDate());

        // Update student detail if provided
        if (request.getDetailRequest() != null) {
            BookDetail bookDetail = existBook.getBookDetail();

            if(bookDetail==null){
                bookDetail = new BookDetail();
                bookDetail.setDescription(request.getDetailRequest().getDescription());
                bookDetail.setLanguage((request.getDetailRequest().getLanguage()));
                bookDetail.setPageCount(request.getDetailRequest().getPageCount());
                bookDetail.setCoverImageUrl(request.getDetailRequest().getCoverImageUrl());
                bookDetail.setEdition(request.getDetailRequest().getEdition());
                bookDetail.setBook(existBook);
                existBook.setBookDetail(bookDetail);
            }
            bookDetail.setDescription(request.getDetailRequest().getDescription());
            bookDetail.setLanguage((request.getDetailRequest().getLanguage()));
            bookDetail.setPageCount(request.getDetailRequest().getPageCount());
            bookDetail.setPublisher(request.getDetailRequest().getPublisher());
            bookDetail.setCoverImageUrl(request.getDetailRequest().getCoverImageUrl());
            bookDetail.setEdition(request.getDetailRequest().getEdition());
        }
        Book updatebook = bookRepository.save(existBook);
        return BookDTO.Response.fromEntity(updatebook);
    }
    //도서 삭제
    @Transactional
    public void deleteBook(Long id){
        if(!bookRepository.existsById(id)){
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Book", "id", id);
        }
        bookRepository.deleteById(id);
    }

}