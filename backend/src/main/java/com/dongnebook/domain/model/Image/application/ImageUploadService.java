package com.dongnebook.domain.model.Image.application;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {

	String StoreImage(MultipartFile img);

}
