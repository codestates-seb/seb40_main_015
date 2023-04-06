package com.dongnebook.domain.model.image.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.dongnebook.domain.model.image.application.ImageUploadService;
import com.dongnebook.domain.model.image.dto.ImageUploadRequest;

class ImageUploadControllerTest {
	private ImageUploadController imageUploadController;
	private ImageUploadService mockAwsS3Service;

	@BeforeEach
	void setUp() {
		mockAwsS3Service = mock(ImageUploadService.class);
		imageUploadController = new ImageUploadController(mockAwsS3Service);
	}

	@Test
	void saveImage_returnsS3Url() throws IOException, IOException {
		// given
		MockMultipartFile mockMultipartFile = new MockMultipartFile("img", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "test".getBytes());
		ImageUploadRequest imageUploadRequest = new ImageUploadRequest(mockMultipartFile);


		String s3Url = "https://example.com/test.jpg";
		when(mockAwsS3Service.storeImage(mockMultipartFile)).thenReturn(s3Url);

		// when
		String actual = imageUploadController.saveImage(imageUploadRequest);

		// then
		assertEquals(s3Url, actual);
		verify(mockAwsS3Service).storeImage(mockMultipartFile);
	}
}