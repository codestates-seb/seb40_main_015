package com.dongnebook.domain.Image.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dongnebook.domain.Image.application.AwsS3Service;
import com.dongnebook.domain.Image.application.ImageUploadService;
import com.dongnebook.domain.Image.dto.ImageUploadRequest;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ImageUploadController {
	private final ImageUploadService awsS3Service;

	public ImageUploadController(AwsS3Service awsS3Service) {
		this.awsS3Service = awsS3Service;
	}

	@PostMapping("/upload")
	public String saveImage(ImageUploadRequest imageUploadRequest){

		log.info("upload Image = {} ", imageUploadRequest.getImg());
		String url = awsS3Service.StoreImage(imageUploadRequest.getImg());
		log.info("url = {}",url);
		return url;
	}

}
