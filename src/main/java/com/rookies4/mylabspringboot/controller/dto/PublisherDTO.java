package com.rookies4.mylabspringboot.controller.dto;

import com.rookies4.mylabspringboot.entity.Book;
import com.rookies4.mylabspringboot.entity.Publisher;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PublisherDTO{

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SimpleResponse{
        private Long id;
        private String name;
        private String address;
        private LocalDate establishedDate;
        private Long bookCount;

        public static SimpleResponse fromEntityWithCount(Publisher publisher) {
            return SimpleResponse.builder()
                    .id(publisher.getId())
                    .name(publisher.getName())
                    .establishedDate(publisher.getEstablishedDate())
                    .address(publisher.getAddress())
                    .bookCount((long) publisher.getBooks().size())
                    .build();
        }
    }
    // 기본 출판사 정보만 필요한 경우 사용 (책 개수 없음)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SimpleResponseWOCount {
        private Long id;
        private String name;
        private String address;
        private LocalDate establishedDate;

        public static SimpleResponse fromEntity(Publisher publisher) {
            return SimpleResponse.builder()
                    .id(publisher.getId())
                    .name(publisher.getName())
                    .establishedDate(publisher.getEstablishedDate())
                    .address(publisher.getAddress())
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private String name;
        private Long bookCount;
        private String address;
        private LocalDate establishedDate;
        private List<BookDTO.SimpleResponse> books;

        public static Response fromEntity(Publisher publisher){
            return Response.builder()
                    .id(publisher.getId())
                    .name(publisher.getName())
                    .establishedDate(publisher.getEstablishedDate())
                    .address(publisher.getAddress())
                    .bookCount((long) publisher.getBooks().size())
                    .books(publisher.getBooks().stream()
                            .map(BookDTO.SimpleResponse::fromEntity)
                            .collect(Collectors.toList()))
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        @NotBlank(message = "Name is required")
        private String name;

        @Past(message = "Established date must be in the past")
        private  LocalDate establishedDate;

        private String address;
    }
}