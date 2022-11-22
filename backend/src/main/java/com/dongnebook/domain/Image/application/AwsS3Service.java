package com.dongnebook.domain.Image.application;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.dongnebook.domain.Image.exception.UploadFailed;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AwsS3Service implements ImageUploadService {

	private final AmazonS3 amazonS3;

	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	public String StoreImage(MultipartFile img) {
		validateFileExists(img);
		String originalFilename = img.getOriginalFilename();
		String storeFileName = createStoreFileName(originalFilename);

		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(img.getContentType());

		try (InputStream inputStream = img.getInputStream()) {
			byte[] bytes = IOUtils.toByteArray(inputStream);
			objectMetadata.setContentLength(bytes.length);
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
			amazonS3.putObject(new PutObjectRequest(bucketName, storeFileName,byteArrayInputStream, objectMetadata)
				.withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (IOException e) {
			throw new UploadFailed();
		}

		return amazonS3.getUrl(bucketName, storeFileName).toString();
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
