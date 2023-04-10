package com.dongnebook.domain.model.image.infrastructure;

import java.awt.image.BufferedImage;

import com.dongnebook.domain.model.image.application.ImageResizer;

public class TestImageResizer implements ImageResizer {
	@Override
	public BufferedImage resizeWithOriginalAspectRatio(BufferedImage originalImage) {
		return new BufferedImage(100,100,BufferedImage.TYPE_INT_RGB);
	}
}
