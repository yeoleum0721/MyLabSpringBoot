package com.rookies4.mylabspringboot.service;

import com.rookies4.mylabspringboot.controller.dto.BookDTO;
import com.rookies4.mylabspringboot.controller.dto.PublisherDTO;
import com.rookies4.mylabspringboot.entity.Book;
import com.rookies4.mylabspringboot.entity.Publisher;
import com.rookies4.mylabspringboot.exception.BusinessException;
import com.rookies4.mylabspringboot.exception.ErrorCode;
import com.rookies4.mylabspringboot.repository.BookDetailRepository;
import com.rookies4.mylabspringboot.repository.BookRepository;
import com.rookies4.mylabspringboot.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublisherService {
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    //전체 조회

    public List<PublisherDTO.SimpleResponse> getAllPublishers(){
        return publisherRepository.findAll()
                .stream()
                .map(PublisherDTO.SimpleResponse::fromEntityWithCount)
                .toList();
    }
    //ID조회
    public PublisherDTO.Response getPublisherById(Long id){
        Publisher publisherById = publisherRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Publisher", "id", id));
        return PublisherDTO.Response.fromEntity(publisherById);
    }
    //출판사에 속한 책 조회
    public List<BookDTO.Response> getPublisherByIdWithBooks(Long id){
        Publisher publisherById = publisherRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Publisher", "id", id));
        return publisherById.getBooks()
                .stream()
                .map(BookDTO.Response::fromEntity)
                .toList();
    }

    //name 조회
    public PublisherDTO.Response getPublisherByName(String name){
        Publisher publisherByName = publisherRepository.findByName(name)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Publisher", "name", name));
        return PublisherDTO.Response.fromEntity(publisherByName);
    }
    //create publisher
    @Transactional
    public PublisherDTO.Response createPublisher(PublisherDTO.Request request){
        if (publisherRepository.existsByName(request.getName())){
            throw new BusinessException(ErrorCode.RESOURCE_DUPLICATE,"Publisher","name",request.getName());
        }

        Publisher publisherEntity = Publisher.builder()
                .name(request.getName())
                .establishedDate(request.getEstablishedDate())
                .address(request.getAddress())
                .build();
        Publisher savedPublisher = publisherRepository.save(publisherEntity);
        return PublisherDTO.Response.fromEntity(savedPublisher);
    }
    //update publisher
    @Transactional
    public PublisherDTO.Response updatePublisher(Long id, PublisherDTO.Request request){
        Publisher publisher = publisherRepository.findById(id)
            .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Publisher", "id", id));

        // Check if another department already has the name
        if (!publisher.getName().equals(request.getName()) &&
                publisherRepository.existsByName(request.getName())) {
            throw new BusinessException(ErrorCode.RESOURCE_DUPLICATE,"Publisher","name", request.getName());
        }
        publisher.setAddress(request.getAddress());
        publisher.setName(request.getName());
        publisher.setEstablishedDate(request.getEstablishedDate());

        Publisher updatedPublisher = publisherRepository.save(publisher);
        return PublisherDTO.Response.fromEntity(updatedPublisher);
    }
    //delete publisher
    @Transactional
    public void deletePublisher(Long id){
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Publisher", "id", id));
        Long bookCount = bookRepository.countByPublisherId(id);
        if (bookCount>0){
            throw new BusinessException(ErrorCode.PUBLISHER_HAS_BOOKS,id,bookCount);
        }
        publisherRepository.deleteById(id);
    }
}