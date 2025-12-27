package com.ntd.libraryappbe.dao;

import com.ntd.libraryappbe.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Fetch reviews for a book with pagination
    Page<Review> findByBookId(Long bookId, Pageable pageable);

    // Check if a user has already reviewed a book
    

    Optional<Review> findByUserEmailAndBookId(String email, Long bookId);


    // Delete all reviews for a book
    @Modifying
    @Query("delete from Review r where r.bookId = :bookId")
    void deleteAllByBookId(@Param("bookId") Long bookId);
}
