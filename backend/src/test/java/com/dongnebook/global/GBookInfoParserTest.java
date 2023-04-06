package com.dongnebook.global;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;

import com.dongnebook.domain.book.application.port.in.response.ApiBookInfoResponse;
import com.dongnebook.global.infra.GsonBookInfoParser;

class GsonBookInfoParserTest {

	private GsonBookInfoParser parser;

	@Mock
	private HttpEntity<String> httpEntityMock;

	private final String testJson = "{\"documents\":[{\"title\":\"Book 1\",\"publisher\":\"Publisher 1\",\"authors\":[\"Author 1\",\"Author 2\"]}," +
		"{\"title\":\"Book 2\",\"publisher\":\"Publisher 2\",\"authors\":[\"Author 3\",\"Author 4\"]}]}";

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		parser = new GsonBookInfoParser();
	}

	@Test
	void toResponse_validJson_shouldReturnCorrectList() {
		when(httpEntityMock.getBody()).thenReturn(testJson);

		List<ApiBookInfoResponse> response = parser.toResponse(httpEntityMock);

		assertEquals(2, response.size());
		assertEquals("Book 1", response.get(0).getTitle());
		assertEquals("Publisher 1", response.get(0).getPublisher());
		assertEquals(2, response.get(0).getAuthors().size());
		assertEquals("Author 1", response.get(0).getAuthors().get(0));
		assertEquals("Author 2", response.get(0).getAuthors().get(1));
		assertEquals("Book 2", response.get(1).getTitle());
		assertEquals("Publisher 2", response.get(1).getPublisher());
		assertEquals(2, response.get(1).getAuthors().size());
		assertEquals("Author 3", response.get(1).getAuthors().get(0));
		assertEquals("Author 4", response.get(1).getAuthors().get(1));
	}
}