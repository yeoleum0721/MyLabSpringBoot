package com.rookies4.mylabspringboot.controller;

import com.rookies4.mylabspringboot.controller.dto.PublisherDTO;
import com.rookies4.mylabspringboot.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/publishers")
public class PublishController {
    private final PublisherService publisherService;

    //전체 조회
    @GetMapping
    public ResponseEntity<List<PublisherDTO.SimpleResponse>> getAllPublisher(){
        return ResponseEntity.ok(publisherService.getAllPublishers());
    }
    //ID로 조회
    @GetMapping("/{id}")
    public ResponseEntity<PublisherDTO.Response> getPublisherById(@PathVariable Long id){
        return ResponseEntity.ok(publisherService.getPublisherById(id));
    }
    //name으로 조회
    @GetMapping("/name/{name}")
    public ResponseEntity<PublisherDTO.Response> getPublisherByName(@PathVariable String name){
        return ResponseEntity.ok(publisherService.getPublisherByName(name));
    }
    //create publisher
    @PostMapping
    public ResponseEntity<PublisherDTO.Response> createPublisher(
            @RequestBody PublisherDTO.Request request){
        return ResponseEntity.ok(publisherService.createPublisher(request));
    }
    //update publisher
    @PutMapping("/{id}")
    public ResponseEntity<PublisherDTO.Response> updatePublisher(
            @PathVariable Long id, @RequestBody PublisherDTO.Request request){
        return ResponseEntity.ok(publisherService.updatePublisher(id,request));
    }
    //delete publisher
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublisher(@PathVariable Long id){
        return ResponseEntity.noContent().build();
    }
}
