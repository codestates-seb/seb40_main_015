package com.dongnebook.global.dto.request;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.domain.Sort;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public final class PageRequest implements Serializable {
	private Long index;
	private Long size = 6L;
	private Sort.Direction sort= Sort.Direction.DESC;

	public PageRequest(Long index) {
		this.index = index;
	}

	// getter
	public org.springframework.data.domain.PageRequest of() {
		return org.springframework.data.domain.PageRequest.of(0,size.intValue());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		PageRequest that = (PageRequest)o;
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
}
