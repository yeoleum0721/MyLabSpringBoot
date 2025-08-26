package com.rookies4.mylabspringboot.controller.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;

public class BookPatchDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PatchRequest {
        private Optional<String> title;
        private Optional<String> author;
        
        @Pattern(regexp = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$",
                message = "ISBN must be valid (10 or 13 digits, with or without hyphens)")
        private Optional<String> isbn;

        @PositiveOrZero(message = "Price must be positive or zero")
        private Optional<Integer> price;

        private Optional<LocalDate> publishDate;
        
        private Optional<BookDetailPatchRequest> detailRequest;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BookDetailPatchRequest {
        private Optional<String> description;
        private Optional<String> language;
        private Optional<Integer> pageCount;
        private Optional<String> publisher;
        private Optional<String> coverImageUrl;
        private Optional<String> edition;
    }
}