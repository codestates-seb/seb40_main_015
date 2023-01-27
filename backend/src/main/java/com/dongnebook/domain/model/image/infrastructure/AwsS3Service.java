package com.dongnebook.domain.model.image.infrastructure;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dongnebook.domain.model.image.application.ImageUploadService;
import com.dongnebook.domain.model.image.exception.UploadFailed;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AwsS3Service implements ImageUploadService {
	private final AmazonS3 amazonS3;
	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	private static final Integer MAX_SIZE = 400;

	public String storeImage(MultipartFile beforeImage) throws IOException {

		validateFileExists(beforeImage);
		String originalFilename = beforeImage.getOriginalFilename();
		String storeFileName = createStoreFileName(originalFilename);
		BufferedImage bufferedImage = ImageIO.read(beforeImage.getInputStream());
		BufferedImage resizedImage = resizeWithOriginalAspectRatio(bufferedImage);

		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			ImageIO.write(resizedImage, "jpg", baos);
			baos.flush();
			byte[] bytes = baos.toByteArray();
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(beforeImage.getContentType());
			objectMetadata.setContentLength(bytes.length);

			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

			amazonS3.putObject(new PutObjectRequest(bucketName, storeFileName, byteArrayInputStream, objectMetadata)
				.withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (IOException e) {
			throw new UploadFailed();
		}

		return amazonS3.getUrl(bucketName, storeFileName).toString();
	}

	public BufferedImage resizeWithOriginalAspectRatio(BufferedImage originalImage) {
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();

		int newWidth = width;
		int newHeight = height;
		if (width > height && width > MAX_SIZE) {
			newHeight = (int)(height * (double)MAX_SIZE / width);
			newWidth = MAX_SIZE;
		}

		if (width <= height && height > MAX_SIZE) {
			newWidth = (int)(width * (double)MAX_SIZE / height);
			newHeight = MAX_SIZE;
		}

		BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
		Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		resizedImage.getGraphics().drawImage(scaledImage, 0, 0, null);
		return resizedImage;
	}

	private void validateFileExists(MultipartFile multipartFile) {
		if (multipartFile.isEmpty()) {
			throw new UploadFailed();
		}
	}

	private static String createStoreFileName(String originalFilename) {
		String ext = extractExt(originalFilename);
		String uuid = UUID.randomUUID().toString();
		return uuid + "." + ext;
	}

	private static String extractExt(String originalFilename) {
		int pos = originalFilename.lastIndexOf(".");
		return originalFilename.substring(pos + 1);
	}
}
