package com.dongnebook.domain.model.image.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;

@Getter
public class ImageUploadRequest {
	private final MultipartFile img;

	public ImageUploadRequest(MultipartFile img) {
		this.img = img;
	}
}
