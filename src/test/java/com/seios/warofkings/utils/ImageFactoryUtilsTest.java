package com.seios.warofkings.utils;

import javafx.scene.image.ImageView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes unitários para os métodos da utilitária {@link ImageFactoryUtils},
 * responsável pela criação de elementos visuais (imagens) utilizados na renderização do tabuleiro e das peças.
 *
 * <p>Os testes desta classe asseguram a correta geração de:</p>
 * <ul>
 *   <li>Imagens transparentes para marcação de casas vazias;</li>
 *   <li>Imagens de peças carregadas a partir de caminhos definidos.</li>
 * </ul>
 *
 * @author Allan
 * @version 1.0
 * @since 2025-06-16
 */
class ImageFactoryUtilsTest {
    /**
     * Testa o método {@link ImageFactoryUtils#createTransparentImage()} para garantir
     * que a imagem gerada para casas vazias (transparentes) não seja nula.
     *
     * <p>Esta imagem é utilizada para ocultar peças ou esvaziar visualmente casas do tabuleiro.</p>
     */
    @Test
    void testCreateTransparentImage() {
        ImageView image = ImageFactoryUtils.createTransparentImage();
        assertNotNull(image);
    }

    /**
     * Testa o método {@link ImageFactoryUtils#createPieceImage(String)} para verificar
     * se a imagem correspondente ao caminho fornecido é corretamente carregada e encapsulada
     * em um {@link ImageView}.
     *
     * <p>Este teste utiliza a imagem "/imagens/Transparent.png" como exemplo de recurso existente.</p>
     */
    @Test
    void testCreatePieceImage() {
        ImageView image = ImageFactoryUtils.createPieceImage("/imagens/Transparent.png");
        assertNotNull(image);
    }
}