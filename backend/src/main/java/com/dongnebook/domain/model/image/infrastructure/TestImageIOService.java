package com.dongnebook.domain.model.image.infrastructure;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestImageIOService implements ImageIOService{
	@Override
	public BufferedImage read(InputStream inputStream) throws IOException {
		return new BufferedImage(200,200 ,BufferedImage.TYPE_INT_RGB );
	}

	@Override
	public void write(BufferedImage resizedImage, String jpg, ByteArrayOutputStream baos) throws IOException {
		log.info("do Nothing");
	}
}
