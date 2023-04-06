package com.dongnebook.domain.model.image.infrastructure;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.dongnebook.domain.model.image.application.ImageResizer;

@ExtendWith(MockitoExtension.class)
class AwsS3ServiceTest {

	private AwsS3Service awsS3Service;

	@Mock
	private AmazonS3 amazonS3;

	private final ImageResizer imageResizer= new TestImageResizer();
	private final ImageIOService imageIOService = new TestImageIOService();

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		awsS3Service = new AwsS3Service(amazonS3, imageResizer,imageIOService);
	}


	@Test
	void testStoreImage() throws IOException {
		// given
		byte[] fileBytes = {0x12, 0x34, 0x56};
		MultipartFile multipartFile = new MockMultipartFile("test.jpg", fileBytes);
		BufferedImage mockBufferedImage = mock(BufferedImage.class);
		when(amazonS3.getUrl(any(), any())).thenReturn( new URL("https://www.example.com"));
		// when
		awsS3Service.storeImage(multipartFile);

		// then
		assertThat(imageResizer.resizeWithOriginalAspectRatio(mockBufferedImage)).usingRecursiveComparison().isEqualTo(new BufferedImage(100,100,BufferedImage.TYPE_INT_RGB));
		verify(amazonS3, times(1)).getUrl(any(), any());

	}
}