package com.seios.warofkings.ui;

import com.seios.warofkings.utils.ImageFactoryUtils;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

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
    private GridPane pawnTurn; //n é GridPane


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
                    String imageName = "/imagens/" + peca.getImgName();
                    ImageView img = ImageFactoryUtils.createPieceImage(imageName);
                    pecas.add(img, coluna, linha);
                }
                else{
                    ImageView trans = ImageFactoryUtils.createTransparentImage();
                    pecas.add(trans, coluna, linha);
                }
            }
        }
    }

    @FXML
    public void movingPieces() {
        for (Node node : pecas.getChildren()) {
            if (node instanceof ImageView imageView) {
                imageView.setOnMouseClicked(event -> {
                    Integer colY = GridPane.getColumnIndex(imageView);
                    Integer rowX = GridPane.getRowIndex(imageView);

                    if (colY == null || rowX == null) {
                        System.out.println("Erro: coluna ou linha null.");
                        return;
                    }

                    int positionTo = rowX * 10 + colY;
                    System.out.println("Clique na posição: " + positionTo);

                    ChessPiece piece = board.getPieces()[rowX][colY];

                    if (piece != null && piece.getColor() == turn) {
                        selectedPiece = piece;
                        selectedImage = imageView;
                        possibleMoves = piece.getPossibleMoves(board.getPieces());

                        System.out.println("Peça selecionada: " + piece);
                        System.out.println("Movimentos possíveis: " + possibleMoves);
                    } else if (selectedPiece != null) {
                        boolean moved = selectedPiece.moveTo(
                                positionTo,
                                possibleMoves,
                                board
                        );

                        if (moved) {
                            // Remove imagem da posição destino e da origem
                            pecas.getChildren().remove(imageView);
                            pecas.getChildren().remove(selectedImage);

                            // Adiciona a peça no destino
                            String imgName = selectedPiece.getImgName();
                            ImageView newPieceImage = ImageFactoryUtils.createPieceImage("/imagens/" + imgName);
                            pecas.add(newPieceImage, colY, rowX);

                            Integer origemColY = GridPane.getColumnIndex(selectedImage);
                            Integer origemRowX = GridPane.getRowIndex(selectedImage);

                            selectedImage = newPieceImage;

                            // adiciona transparente na origem
                            ImageView newTrans = ImageFactoryUtils.createTransparentImage();
                            pecas.add(newTrans, origemColY, origemRowX);

                            System.out.println("Peça movida!");
                            turn = turn.next();
                            System.out.println("Turno atual: " + turn);

                            selectedPiece = null;
                            selectedImage = null;
                            possibleMoves = null;

                            movingPieces(); // Reassocia
                        } else {
                            System.out.println("Coordenada Inválida!");
                        }
                    } else {
                        System.out.println("Nenhuma peça selecionada!");
                    }
                });
            }
        }
    }

    public void turnPawn(){
        for(int i = 0; i < 1; i++){
            for(int j = 0; j < 5; j++){

            }
        }
    }
}