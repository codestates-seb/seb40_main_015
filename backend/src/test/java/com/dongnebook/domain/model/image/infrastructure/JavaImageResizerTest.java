package com.dongnebook.domain.model.image.infrastructure;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.awt.image.BufferedImage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dongnebook.domain.model.image.application.ImageResizer;

class JavaImageResizerTest {

	private static final int MAX_SIZE = 400;
	private ImageResizer imageResizer;

	@BeforeEach
	public void setUp() {
		imageResizer = new JavaImageResizer();
	}

	@Test
	void testResizeWithOriginalAspectRatio() {
		// given
		BufferedImage originalImage = TestImageUtil.createTestImage(600, 400);

		// when
		BufferedImage resizedImage = imageResizer.resizeWithOriginalAspectRatio(originalImage);

		// then
		assertEquals(MAX_SIZE, resizedImage.getWidth());
		assertEquals(266, resizedImage.getHeight());
	}

	public static class TestImageUtil {

		public static BufferedImage createTestImage(int width, int height) {
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics2D = image.createGraphics();
			graphics2D.setPaint(Color.WHITE);
			graphics2D.fillRect(0, 0, width, height);
			graphics2D.setPaint(Color.BLACK);
			graphics2D.drawRect(0, 0, width - 1, height - 1);
			graphics2D.dispose();
			return image;
		}
	}
}