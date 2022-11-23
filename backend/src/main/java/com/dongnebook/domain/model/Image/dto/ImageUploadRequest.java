package com.dongnebook.domain.model.Image.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;

@Getter
public class ImageUploadRequest {
	private MultipartFile img;

	public ImageUploadRequest(MultipartFile img) {
		this.img = img;
	}
}
