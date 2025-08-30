package com.rookies4.mylabspringboot.repository;

import com.rookies4.mylabspringboot.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher,Long> {
	Optional<Publisher> findByName(String name);

    @Query("SELECT p FROM Publisher p JOIN FETCH p.books WHERE p.id = :id")
	Optional<Publisher> findByIdWithBooks(@Param("id") Long id);

	boolean existsByName(String name);
}