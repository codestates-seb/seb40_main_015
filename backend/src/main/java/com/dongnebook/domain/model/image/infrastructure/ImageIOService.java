package com.dongnebook.domain.model.image.infrastructure;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

public interface ImageIOService {
	BufferedImage read(InputStream inputStream) throws IOException;

	void write(BufferedImage resizedImage, String jpg, ByteArrayOutputStream baos) throws IOException;
}
