package com.dongnebook.domain.book.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dongnebook.domain.book.domain.Book;

public interface BookCommandRepository extends JpaRepository<Book,Long> {
}
