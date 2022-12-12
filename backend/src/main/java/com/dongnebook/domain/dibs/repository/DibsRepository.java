package com.dongnebook.domain.dibs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.dibs.domain.Dibs;
import com.dongnebook.domain.member.domain.Member;

public interface DibsRepository extends JpaRepository<Dibs,Long> {
	Optional<Dibs> findByBookAndMemberOrderByIdDesc(Book book, Member member);
}
