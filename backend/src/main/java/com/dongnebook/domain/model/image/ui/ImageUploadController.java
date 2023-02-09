package com.dongnebook.domain.model.image.ui;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dongnebook.domain.model.image.infrastructure.AwsS3Service;
import com.dongnebook.domain.model.image.application.ImageUploadService;
import com.dongnebook.domain.model.image.dto.ImageUploadRequest;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ImageUploadController {
	private final ImageUploadService awsS3Service;

	public ImageUploadController(AwsS3Service awsS3Service) {
		this.awsS3Service = awsS3Service;
	}

	@PostMapping("/upload")
	public String saveImage(ImageUploadRequest imageUploadRequest) throws IOException {
		return awsS3Service.storeImage(imageUploadRequest.getImg());
	}
}
