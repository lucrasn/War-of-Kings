package com.seios.warofkings.ui;

import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import com.seios.warofkings.board.Board;
import com.seios.warofkings.pieces.ChessPiece;


public class MainController {
    Board board = new Board();

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
                ChessPiece peca = board.getPieces()[linha][coluna];

                if (peca != null) {
                    String imageName = peca.getImgName();
                    InputStream inputStream = getClass().getResourceAsStream("/imagens/" + imageName);


                    if (inputStream == null) {
                        System.err.println("Imagem nao encontrada: " + imageName);
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

    @FXML
    public void movingPieces() {
        AtomicReference<ChessPiece> selectedPiece = new AtomicReference<ChessPiece>();
        AtomicReference<ImageView> selectedImage = new AtomicReference<ImageView>();
        AtomicReference<List<Integer>> possibleMoves = new AtomicReference<List<Integer>>();

        for (Node node : pecas.getChildren()) {
            if (node instanceof ImageView imageView) {
                node.setOnMouseClicked(event -> {
                    Integer columnY = GridPane.getColumnIndex(imageView);
                    Integer rowX = GridPane.getRowIndex(imageView);
                    int positionTo = rowX * 10 + columnY;

                    if (selectedPiece.get() == null) {
                        ChessPiece piece = board.getPieces()[rowX][columnY];
                        if (piece != null) {
                            selectedPiece.set(piece);
                            selectedImage.set(imageView);
                            possibleMoves.set(piece.getPossibleMoves(board.getPieces()));
                            System.out.println("Peca selecionada: " + piece);
                            System.out.println("Movimentos possiveis: " + possibleMoves.get());
                        }
                    } else {
                        boolean moved = selectedPiece.get().moveTo(positionTo, possibleMoves.get(), board);
                        if (moved) {
                            pecas.getChildren().remove(selectedImage.get());
                            pecas.add(selectedImage.get(), columnY, rowX);
                            // atualizar?
                            System.out.println("Peça movida para " + positionTo);
                        } else {
                            System.out.println("Movimento inválido.");
                        }

                        selectedPiece.set(null);
                        selectedImage.set(null);
                        possibleMoves.set(null);
                    }
                });
            }
        }

        //   public void turnPawn(){

        //    }
    }
}