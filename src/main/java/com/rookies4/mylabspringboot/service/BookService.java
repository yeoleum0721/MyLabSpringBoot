package com.rookies4.mylabspringboot.service;

import com.rookies4.mylabspringboot.controller.dto.BookDTO;
import com.rookies4.mylabspringboot.entity.Book;
import com.rookies4.mylabspringboot.exception.BusinessException;
import com.rookies4.mylabspringboot.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {
    private BookRepository bookRepository;

    //전체 조회
    public List<BookDTO.BookResponse> getAllBooks(){
        return bookRepository.findAll()
                .stream()
                .map(BookDTO.BookResponse::from)
                .toList();
    }
    //ID로 조회
    public BookDTO.BookResponse getBookById(Long id){
        Book bookById = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Book not Found"));
        return BookDTO.BookResponse.from(bookById);
    }
    //Isbn으로 조회
    public BookDTO.BookResponse getBookByIsbn(String isbn){
        Book bookByIsbn = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BusinessException("Book not Found"));
        return BookDTO.BookResponse.from(bookByIsbn);
    }
    //작가가 쓴책 조회
    public List<BookDTO.BookResponse> getBooksByAuthor(String author){
        return bookRepository.findByAuthor(author)
                .stream()
                .map(BookDTO.BookResponse::from)
                .toList();
    }
    //책 등록
    public BookDTO.BookResponse createBook(BookDTO.BookCreateRequest request){
        bookRepository.findByIsbn(request.getIsbn())
                .ifPresent(entity -> {
            throw new BusinessException("Book with this ISBN already Exist", HttpStatus.CONFLICT);
        });
        Book entity = request.toEntity();
        Book savedEntity = bookRepository.save(entity);
        //Entity =>DTO로 변환 후 리턴됨
        return BookDTO.BookResponse.from(savedEntity);

    }
    //책 세부사항 변경
    @Transactional
    public BookDTO.BookResponse updateBook(Long id,
                                           BookDTO.BookUpdateRequest request) {
        Book existBook = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Book Not Found", HttpStatus.NOT_FOUND));
        // 변경이 필요한 필드만 업데이트
        if (request.getTitle() != null) {
            existBook.setTitle(request.getTitle());
        } else if (request.getAuthor() != null) {
            existBook.setAuthor(request.getAuthor());
        } else if (request.getPrice() != null) {
            existBook.setPrice(request.getPrice());
        } else if (request.getPublishDate() != null) {
            existBook.setPublishDate(request.getPublishDate());
            //dirty read(setter()만 호출하고, save() 생략가능)
        }
        return BookDTO.BookResponse.from(existBook);
    }
    //User 삭제
    @Transactional
    public void deleteBook(Long id){
            Book bookById = bookRepository.findById(id)
                    .orElseThrow(() -> new BusinessException("Book not Found"));
            bookRepository.delete(bookById);
    }


}
