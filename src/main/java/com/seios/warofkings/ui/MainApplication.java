package com.seios.warofkings.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe principal da aplicação JavaFX para o jogo War of Kings.
 * <p>
 * Responsável por inicializar a interface gráfica, carregando o layout principal via FXML
 * e configurando a janela principal do jogo.
 * </p>
 *
 * <p>Utiliza o arquivo FXML localizado em {@code /com/seios/warofkings/layouts/hello-view.fxml}.</p>
 *
 * @author Lívia
 * @version 1.0
 * @since 2025-06-09
 */
public class MainApplication extends Application {
    /**
     * Método chamado automaticamente pelo JavaFX ao iniciar a aplicação.
     * Carrega o layout da interface principal e exibe a janela do jogo.
     *
     * @param stage palco principal da aplicação.
     * @throws IOException se ocorrer erro ao carregar o arquivo FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/seios/warofkings/layouts/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 340, 350);
        stage.setTitle("War of Kings");
        stage.setScene(scene);
        stage.setMinWidth(320);
        stage.setMinHeight(370);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    /**
     * Método principal da aplicação. Responsável por lançar a aplicação JavaFX.
     *
     * @param args argumentos de linha de comando.
     */
    public static void main(String[] args) {
        launch();
    }
}