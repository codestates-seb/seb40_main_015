package com.dongnebook.global.dto.request;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.domain.Sort;

import com.dongnebook.domain.book.application.port.in.request.PageRequest;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Getter
public class PageRequestImpl implements PageRequest,Serializable {
	private final long index;
	private final long size;
	private final Sort.Direction sort;

	public PageRequestImpl(long index) {
		this.size = 6L;
		this.index = index;
		this.sort = Sort.Direction.DESC;
	}

	public static PageRequestImpl of(Long index){
		return new PageRequestImpl(index);
	}

	// getter
	@Override
	public org.springframework.data.domain.PageRequest of() {
		return org.springframework.data.domain.PageRequest.of(0, (int)size);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		PageRequestImpl that = (PageRequestImpl)o;
		return Objects.equals(index, that.index) && Objects.equals(size, that.size)
			&& sort == that.sort;
	}

	@Override
	public int hashCode() {
		return Objects.hash(index, size, sort);
	}

	@Override
	public String toString() {
		return "PageRequest{" +
			"index=" + index +
			", size=" + size +
			", sort=" + sort +
			'}';
	}

	public Long getIndex() {
		return index;
	}

	public Long getSize() {
		return size;
	}
}
