package com.dongnebook.domain.model.image.infrastructure;

import java.awt.*;
import java.awt.image.BufferedImage;

import org.springframework.stereotype.Component;

import com.dongnebook.domain.model.image.application.ImageResizer;

@Component
public class JavaImageResizer implements ImageResizer {

	@Override
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
}
