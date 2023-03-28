package com.dongnebook.domain.book.application.port.in.request;

import java.io.Serializable;
import java.util.Optional;

import com.dongnebook.domain.book.adapter.in.request.BookSearchCondition;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookSearchCommand implements MapSearchable, Serializable {
	private String bookTitle;
	private SearchCommand searchCommand;

	public BookSearchCommand(String bookTitle, Double longitude, Double latitude, Integer width, Integer height,
		Integer sector, Integer level) {
		this.bookTitle = Optional.ofNullable(bookTitle).orElse("");
		this.searchCommand = new SearchCommand(longitude, latitude, width, height, sector, level);
	}

	public static BookSearchCommand of(BookSearchCondition condition){
		return new BookSearchCommand(condition.getBookTitle(), condition.getLongitude(), condition.getLatitude(),
			condition.getWidth(), condition.getHeight(), condition.getSector(), condition.getLevel());
	}

	public Integer getLevel(){
		return searchCommand.getLevel();
	}

	public Double getLongitude() {
		return searchCommand.getLongitude();
	}

	public Double getLatitude() {
		return searchCommand.getLatitude();
	}

	public Integer getWidth() {
		return searchCommand.getWidth();
	}

	public Integer getHeight() {
		return searchCommand.getHeight();
	}

	public Integer getSector() {return searchCommand.getSector();
	}
}
