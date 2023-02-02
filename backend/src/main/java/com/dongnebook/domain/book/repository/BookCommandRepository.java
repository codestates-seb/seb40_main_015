package com.dongnebook.domain.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dongnebook.domain.book.domain.Book;

@Repository
public interface BookCommandRepository extends JpaRepository<Book, Long> {
}
