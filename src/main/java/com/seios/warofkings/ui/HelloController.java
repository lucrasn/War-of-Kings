package com.seios.warofkings.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.InputStream;
import java.util.Map;

public class HelloController {
    int[][] board = {
            { 7, 8, 9,10,11, 9, 8, 7},
            { 6, 6, 6, 6, 6, 6, 6, 6},
            {-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1},
            { 0, 0, 0, 0, 0, 0, 0, 0},
            { 1, 2, 3, 4, 5, 3, 2, 1}

    };

    Map<Integer, String> idPecas = Map.ofEntries(
            Map.entry(0, "Peao_Branco.png"),
            Map.entry(1, "Torre_Branco.png"),
            Map.entry(2, "Cavalo_Branco.png"),
            Map.entry(3, "Bispo_Branco.png"),
            Map.entry(4, "Rainha_Branco.png"),
            Map.entry(5, "Rei_Branco.png"),
            Map.entry(6, "Peao_Preto.png"),
            Map.entry(7, "Torre_Preto.png"),
            Map.entry(8, "Cavalo_Preto.png"),
            Map.entry(9, "Bispo_Preto.png"),
            Map.entry(10, "Rainha_Preto.png"),
            Map.entry(11, "Rei_Preto.png")
    );

    @FXML
    private GridPane pecas;

    @FXML
    private GridPane tabuleiro;


    @FXML
    public void initialize(){
        creatingBoard();
        creatingPieces();
        movingPieces();
    }

    @FXML
    public void creatingBoard() {
        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                Region casa = new Region();
                casa.setPrefSize(64,64);

                String color;

                if((linha + coluna) %2 == 0){
                    color = "#f0d9b5";
                }
                else {
                    color =  "#b58863";
                }

                casa.setStyle("-fx-background-color: " + color + ";");
                tabuleiro.add(casa,coluna,linha);
            }
        }
    }

    @FXML
    public void creatingPieces(){
        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                int id = board[linha][coluna];

                if (id != -1) {
                    String imageName = idPecas.get(id);
                    InputStream inputStream = getClass().getResourceAsStream("/imagens/" + imageName);


                    if (inputStream == null) {
                        System.err.println("Imagem não encontrada: " + imageName);
                        continue;
                    }

                    Image png = new Image(inputStream);
                    ImageView img = new ImageView(png);
                    img.setFitWidth(55);
                    img.setFitHeight(55);
                    img.setPreserveRatio(true);

                    GridPane.setHalignment(img, javafx.geometry.HPos.CENTER);
                    GridPane.setValignment(img, javafx.geometry.VPos.CENTER);

                    pecas.add(img, coluna, linha);
                }
            }
        }
    }

    private ImageView clickedPiece;

    @FXML
    public void movingPieces(){
        for(Node node : pecas.getChildren()){
            if(node instanceof ImageView){
                node.setOnMouseClicked(event -> {
                    ImageView clickedImg = (ImageView) node;

                    if(clickedPiece == null){
                        clickedPiece = clickedImg;
                        System.out.println("VOCE CLICOU");
                    }
                    else {
                        System.out.println("LUGAR SELECIONADO");
                        Integer column = GridPane.getColumnIndex(clickedImg);
                        Integer row = GridPane.getRowIndex(clickedImg);

                        pecas.getChildren().remove(clickedImg);
                        pecas.add(clickedImg, column, row);

                        clickedPiece = null;
                    }
                });
            }
        }

        //   public void turnPawn(){

        //    }
    }


}