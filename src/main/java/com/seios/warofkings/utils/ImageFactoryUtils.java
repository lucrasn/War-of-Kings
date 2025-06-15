package com.seios.warofkings.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.io.InputStream;

/**
 * Classe utilitária responsável pela criação e configuração de imagens {@link ImageView}
 * utilizadas na interface gráfica do jogo War of Kings.
 * <p>
 * Fornece métodos para carregar imagens de peças a partir dos recursos internos do projeto,
 * com ajustes de tamanho e comportamento de clique apropriados.
 * </p>
 *
 * @author Lucas
 * @version 1.0
 * @since 2025-06-15
 */
public class ImageFactoryUtils {
    /**
     * Cria um {@link ImageView} para a peça de xadrez correspondente ao caminho da imagem fornecido.
     * <p>
     * A imagem é ajustada com tamanho fixo, centralizada no {@link GridPane} e configurada para
     * responder corretamente a eventos de clique.
     * </p>
     *
     * @param imagePath caminho da imagem (relativo à raiz de recursos).
     * @return {@code ImageView} configurado com a imagem da peça ou {@code null} se a imagem não for encontrada.
     */
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

    /**
     * Cria uma imagem transparente que pode ser usada para ocupar posições vazias no tabuleiro.
     *
     * @return {@code ImageView} de uma imagem transparente.
     */
    public static ImageView createTransparentImage() {
        return createPieceImage("/imagens/Transparent.png");
    }

}
