package com.dongnebook.domain.book.dto.response;

import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.domain.BookState;
import com.dongnebook.domain.model.Location;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class BookInfoResponse {

    private Long bookId;

    private String bookUrl;

    private String title;

    private String author;

    private String publisher;

    private Integer rentalFee;

    private String content;

    private Location location;

    private BookState bookState;

    private String merchantName;

    @QueryProjection
    public BookInfoResponse(Long bookId, String bookUrl, String title, String author,
                            String publisher, Integer rentalFee, String content,
                            Location location, BookState bookState, String merchantName){
        this.bookId = bookId;
        this.bookUrl = bookUrl;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.rentalFee = rentalFee;
        this.content = content;
        this.location = location;
        this.bookState = bookState;
        this.merchantName = merchantName;
    }

    public static BookInfoResponse of(Book book) {
        return new BookInfoResponse(book.getId(), book.getImgUrl(), book.getTitle(),
                book.getAuthor(), book.getPublisher(), book.getRentalFee().getValue(), book.getDescription(),
                book.getLocation(), book.getBookState(), book.getMember().getNickname());
    }

}

