package com.seios.warofkings.ui.components;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Classe base para iniciar componentes JavaFX personalizados no contexto do jogo War of Kings.
 * <p>
 * Esta classe herda de {@link javafx.application.Application} e é utilizada
 * como ponto de entrada alternativo para testes de componentes visuais ou customizações específicas
 * da interface gráfica.
 * </p>
 *
 * <p><b>Nota:</b> Atualmente o método {@code start} está vazio e pode ser preenchido com
 * elementos da UI para testes isolados ou previews de componentes.</p>
 *
 * @author Lívia
 * @version 1.0
 * @since 2025-06-09
 */
public class CustomControls extends Application {

    /**
     * Ponto de entrada padrão do JavaFX.
     *
     * @param args argumentos de linha de comando.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Método principal chamado ao iniciar a aplicação JavaFX.
     * Deve ser sobrescrito para configurar e exibir os componentes visuais.
     *
     * @param primaryStage palco principal da aplicação.
     */
    @Override
    public void start(Stage primaryStage) {

    }
}
