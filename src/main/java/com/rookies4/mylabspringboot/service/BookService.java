package com.rookies4.mylabspringboot.service;

import com.rookies4.mylabspringboot.controller.dto.BookDTO;
import com.rookies4.mylabspringboot.controller.dto.BookPatchDTO;
import com.rookies4.mylabspringboot.entity.Book;
import com.rookies4.mylabspringboot.entity.BookDetail;
import com.rookies4.mylabspringboot.exception.BusinessException;
import com.rookies4.mylabspringboot.repository.BookDetailRepository;
import com.rookies4.mylabspringboot.repository.BookRepository;
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
                .orElseThrow(() -> new BusinessException("Book not Found"));
        return BookDTO.Response.fromEntity(bookById);
    }

    //Isbn으로 조회
    public BookDTO.Response getBookByIsbn(String isbn){
        Book bookByIsbn = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BusinessException("Book not Found"));
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
            throw new BusinessException("Book Already Exists with isbn : " + request.getIsbn());
        }
        //새 BookEntity 생성
        Book bookEntity = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .price(request.getPrice())
                .publishDate(request.getPublishDate())
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
                .orElseThrow(() -> new BusinessException("Book Not Found", HttpStatus.NOT_FOUND));

        if(!bookRepository.existsByIsbn(request.getIsbn())){
            throw new BusinessException("Book Already exists with isbn : " + request.getIsbn());
        }
        existBook.setTitle(request.getTitle());
        existBook.setAuthor(request.getAuthor());
        existBook.setPrice(request.getPrice());

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
            throw new BusinessException("Book not Found");
        }
        bookRepository.deleteById(id);
    }
    //부분 update
    public Book updateBookPartially(Long id, BookPatchDTO.PatchRequest patchRequest) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));

        // 제목 업데이트
        patchRequest.getTitle().ifPresent(book::setTitle);

        // 저자 업데이트
        patchRequest.getAuthor().ifPresent(book::setAuthor);

        // ISBN 업데이트 로직
        patchRequest.getIsbn().ifPresent(newIsbn -> {
            if (!book.getIsbn().equals(newIsbn) && bookRepository.existsByIsbn(newIsbn)) {
                throw new BusinessException("Book Already Exists with ISBN");
            }
            book.setIsbn(newIsbn);
        });

        // 가격 업데이트
        patchRequest.getPrice().ifPresent(book::setPrice);

        // 출판일 업데이트
        patchRequest.getPublishDate().ifPresent(book::setPublishDate);

        // BookDetail 업데이트 (nested object)
        patchRequest.getDetailRequest().ifPresent(detailPatchRequest -> {
            BookDetail bookDetail = book.getBookDetail();
            if (bookDetail != null) {
                detailPatchRequest.getDescription().ifPresent(bookDetail::setDescription);
                detailPatchRequest.getLanguage().ifPresent(bookDetail::setLanguage);
                detailPatchRequest.getPageCount().ifPresent(bookDetail::setPageCount);
                detailPatchRequest.getPublisher().ifPresent(bookDetail::setPublisher);
                detailPatchRequest.getCoverImageUrl().ifPresent(bookDetail::setCoverImageUrl);
                detailPatchRequest.getEdition().ifPresent(bookDetail::setEdition);
            }
        });

        return bookRepository.save(book);
    }
    public Book updateBookDetailPartially(Long id, BookPatchDTO.BookDetailPatchRequest patchRequest) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));

        BookDetail bookDetail = book.getBookDetail();
        if (bookDetail == null) {
            // BookDetail이 없으면 새로 생성하거나, 비즈니스 예외를 발생시킬 수 있습니다.
            // 여기서는 예외를 발생시키는 것으로 가정합니다.
            throw new EntityNotFoundException("Book detail not found for book id: " + id);
        }

        // 각 필드에 대해 Optional을 활용하여 값이 존재할 경우에만 업데이트합니다.
        patchRequest.getDescription().ifPresent(bookDetail::setDescription);
        patchRequest.getLanguage().ifPresent(bookDetail::setLanguage);
        patchRequest.getPageCount().ifPresent(bookDetail::setPageCount);
        patchRequest.getPublisher().ifPresent(bookDetail::setPublisher);
        patchRequest.getCoverImageUrl().ifPresent(bookDetail::setCoverImageUrl);
        patchRequest.getEdition().ifPresent(bookDetail::setEdition);

        // 부모 엔티티인 Book을 저장하면 BookDetail도 함께 저장됩니다.
        // JPA의 영속성 전이(Cascade) 설정에 따라 달라질 수 있으나, 일반적으로는 이렇게 처리합니다.
        return bookRepository.save(book);
    }

}