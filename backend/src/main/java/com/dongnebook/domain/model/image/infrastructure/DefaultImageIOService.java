package com.dongnebook.domain.model.image.infrastructure;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.stereotype.Component;

@Component
public class DefaultImageIOService implements ImageIOService {
	@Override
	public BufferedImage read(InputStream inputStream) throws IOException {
		return ImageIO.read(inputStream);
	}

	@Override
	public void write(BufferedImage resizedImage, String jpg, ByteArrayOutputStream baos) throws IOException {
		ImageIO.write(resizedImage, jpg, baos);
	}
}
