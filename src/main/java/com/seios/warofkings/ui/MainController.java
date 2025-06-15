package com.seios.warofkings.ui;

import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import java.io.InputStream;
import java.util.List;
import com.seios.warofkings.board.Board;
import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.board.enums.Turn;


public class MainController {
    private ChessPiece selectedPiece;
    private ImageView selectedImage;
    private List<Integer> possibleMoves;

    Board board = new Board();

    private Turn turn = Turn.WHITE;

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
        tabuleiro.getChildren().clear(); // limpa caso reinitialize

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Region square = new Region();
                square.setPrefSize(60, 60);
                String color = (i + j) % 2 == 0 ? "#eeeed2" : "#769656";
                square.setStyle("-fx-background-color: " + color + ";");

                GridPane.setRowIndex(square, i);
                GridPane.setColumnIndex(square, j);
                tabuleiro.getChildren().add(square);
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
                else{
                    InputStream transparent = getClass().getResourceAsStream("/imagens/Transparent.png");

                    if (transparent == null) {
                        System.err.println("Imagem transparente nao encontrada: ");
                        continue;
                    }

                    Image trans_png = new Image(transparent);
                    ImageView trans = new ImageView(trans_png);
                    trans.setFitHeight(55);
                    trans.setFitWidth(55);

                    GridPane.setHalignment(trans, javafx.geometry.HPos.CENTER);
                    GridPane.setValignment(trans, javafx.geometry.VPos.CENTER);


                    pecas.add(trans, coluna, linha);
                }
            }
        }
    }

    @FXML
    public void movingPieces() {
        // 1. Eventos de clique nas peças (ImageView)
        for (Node node : pecas.getChildren()) {
            if (node instanceof ImageView imageView) {
                imageView.setOnMouseClicked(event -> {
                    Integer colY = GridPane.getColumnIndex(imageView);
                    Integer rowX = GridPane.getRowIndex(imageView);
                    ChessPiece piece = board.getPieces()[rowX][colY];
                    if (piece != null && piece.getColor() == turn) {
                        selectedPiece = piece;
                        selectedImage = imageView;
                        possibleMoves = piece.getPossibleMoves(board.getPieces());
                        System.out.println("Peça selecionada: " + piece);
                        System.out.println("Movimentos possíveis: " + possibleMoves);
                    }
                });
            }
        }

        // 2. Eventos de clique nas casas (Region)
        for (Node node : tabuleiro.getChildren()) {
            if (node instanceof Region square) {
                square.setOnMouseClicked(event -> {
                    Integer colY = GridPane.getColumnIndex(square);
                    Integer rowX = GridPane.getRowIndex(square);

                    if (colY == null || rowX == null) {
                        System.out.println("Erro: coluna ou linha null.");
                        return;
                    }

                    int positionTo = rowX * 10 + colY;
                    System.out.println("Clique na posição: " + positionTo);

                    if (selectedPiece != null) {
                        boolean moved = selectedPiece.moveTo(
                                positionTo,
                                selectedPiece.getPossibleMoves(board.getPieces()),
                                board
                        );

                        if (moved) {
                            pecas.getChildren().remove(selectedImage);

                            pecas.getChildren().removeIf(n ->
                                    GridPane.getColumnIndex(n) == colY &&
                                            GridPane.getRowIndex(n) == rowX
                            );

                            pecas.add(selectedImage, colY, rowX);

                            System.out.println("Peca movida!");

                            turn = turn.next();
                            System.out.println(turn);
                            selectedPiece = null;
                            selectedImage = null;
                            possibleMoves = null;

                            movingPieces(); // Reassocia listeners
                        } else {
                            System.out.println("Coordenada Invalida!");
                        }
                    } else {
                        System.out.println("Nenhuma peca selecionada!");
                    }
                });
            }
        }
    }
}