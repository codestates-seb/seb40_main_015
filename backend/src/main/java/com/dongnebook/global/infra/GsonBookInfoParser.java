package com.dongnebook.global.infra;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

import com.dongnebook.domain.book.adapter.in.BookInfoParser;
import com.dongnebook.domain.book.application.port.in.response.ApiBookInfoResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Component
public class GsonBookInfoParser implements BookInfoParser {
	private static final String TITLE = "title";
	private static final String PUBLISHER = "publisher";
	private static final String DOCUMENTS = "documents";
	private static final String AUTHORS = "authors";


	@Override
	public List<ApiBookInfoResponse> toResponse(HttpEntity<String> httpEntity) {
		JsonElement jsonElement = JsonParser.parseString(Objects.requireNonNull(httpEntity.getBody()));
		JsonObject asJsonObject = jsonElement.getAsJsonObject();
		JsonArray documents = getArrayPropertyValue(asJsonObject,DOCUMENTS);

		return IntStream.range(0, documents.size())
			.mapToObj(i -> documents.get(i).getAsJsonObject())
			.map(this::toApiBookInfo)
			.collect(Collectors.toList());
	}

	private List<String> getAuthors(JsonObject jsonObject) {
		JsonArray authors = getArrayPropertyValue(jsonObject, AUTHORS);
		return IntStream.range(0, authors.size())
			.mapToObj(authors::get)
			.map(JsonElement::getAsString)
			.collect(Collectors.toList());
	}

	private ApiBookInfoResponse toApiBookInfo(JsonObject element) {
		return ApiBookInfoResponse.builder()
			.title(getStringPropertyValue(element,TITLE))
			.publisher(getStringPropertyValue(element,PUBLISHER))
			.authors(getAuthors(element))
			.build();
	}

	private String getStringPropertyValue(JsonObject jsonObject,String propertyName){
		return jsonObject.get(propertyName).getAsString();
	}

	private JsonArray getArrayPropertyValue(JsonObject jsonObject,String propertyName){
		return jsonObject.get(propertyName).getAsJsonArray();
	}
}
