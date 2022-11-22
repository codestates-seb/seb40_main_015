package com.dongnebook.domain.Image.application;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {

	String StoreImage(MultipartFile img);

}
