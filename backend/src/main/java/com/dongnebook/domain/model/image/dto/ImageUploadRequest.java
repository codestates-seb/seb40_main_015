package com.dongnebook.domain.model.image.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImageUploadRequest {
	private MultipartFile img;

	public ImageUploadRequest(MultipartFile img) {
		this.img = img;
	}
}
