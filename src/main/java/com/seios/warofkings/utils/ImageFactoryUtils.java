package com.seios.warofkings.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.io.InputStream;

public class ImageFactoryUtils {
    public static ImageView createPieceImage(String imagePath) {
        InputStream stream = ImageFactoryUtils.class.getResourceAsStream(imagePath);

        if (stream == null) {
            System.err.println("Imagem nao encontrada: " + imagePath);
            return null;
        }

        Image png = new Image(stream);
        ImageView img = new ImageView(png);
        img.setFitWidth(55);
        img.setFitHeight(55);
        img.setPreserveRatio(true);

        // ESSENCIAL:
        img.setPickOnBounds(true); // capta o clique em toda a área
        img.setMouseTransparent(false); // garante que o clique não "atravesse"

        GridPane.setHalignment(img, javafx.geometry.HPos.CENTER);
        GridPane.setValignment(img, javafx.geometry.VPos.CENTER);

        return img;
    }

    public static ImageView createTransparentImage() {
        return createPieceImage("/imagens/Transparent.png");
    }

}
