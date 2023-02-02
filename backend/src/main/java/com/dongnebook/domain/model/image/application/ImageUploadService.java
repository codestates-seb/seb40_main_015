package com.dongnebook.domain.model.image.application;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {
	String storeImage(MultipartFile img) throws IOException;
}
