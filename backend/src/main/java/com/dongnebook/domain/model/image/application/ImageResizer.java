package com.dongnebook.domain.model.image.application;

import java.awt.image.BufferedImage;

public interface ImageResizer {
	Integer MAX_SIZE = 400;
	BufferedImage resizeWithOriginalAspectRatio(BufferedImage originalImage);
}
