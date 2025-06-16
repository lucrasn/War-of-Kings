package com.seios.warofkings.utils;

import javafx.scene.image.ImageView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImageFactoryUtilsTest {

    @Test
    void testCreateTransparentImage() {
        ImageView image = ImageFactoryUtils.createTransparentImage();
        assertNotNull(image);
    }

    @Test
    void testCreatePieceImage() {
        ImageView image = ImageFactoryUtils.createPieceImage("/imagens/Transparent.png");
        assertNotNull(image);
    }
}