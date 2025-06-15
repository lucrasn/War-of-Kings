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


/**
 * Controlador principal da interface gráfica do jogo War of Kings.
 * <p>
 * Responsável por inicializar o tabuleiro, renderizar peças e gerenciar a lógica de cliques
 * e movimentação das peças no tabuleiro. Também mantém o controle do turno atual.
 * </p>
 *
 * <p>Associado ao arquivo FXML principal carregado por {@link MainApplication}.</p>
 *
 * @author Lívia
 * @version 1.0
 * @since 2025-06-09
 */
public class MainController {
    private ChessPiece selectedPiece;
    private ImageView selectedImage;
    private List<Integer> possibleMoves;

    private final Board board = new Board();
    private final Region[][] boardSquares = new Region[8][8];
    private Turn turn = Turn.WHITE;

    @FXML
    private GridPane pecas;

    @FXML
    private GridPane tabuleiro;

    /**
     * Método chamado automaticamente pelo JavaFX após o carregamento do FXML.
     * Responsável por iniciar o tabuleiro, posicionar as peças e configurar os eventos.
     */
    @FXML
    public void initialize(){
        creatingBoard();
        creatingPieces();
        movingPieces();
    }

    /**
     * Cria o tabuleiro visualmente no {@code GridPane tabuleiro}, alternando cores das casas.
     */
    @FXML
    public void creatingBoard() {
        tabuleiro.getChildren().clear(); // limpa caso reinitialize

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Region square = new Region();
                square.setPrefSize(60, 60);
                String color = (i + j) % 2 == 0 ? "#eeeed2" : "#769656";
                square.setStyle("-fx-background-color: " + color + ";");

                boardSquares[i][j] = square;

                GridPane.setRowIndex(square, i);
                GridPane.setColumnIndex(square, j);
                tabuleiro.getChildren().add(square);
            }
        }
    }

    /**
     * Posiciona as imagens das peças no tabuleiro com base no estado atual do objeto {@link Board}.
     * Peças são desenhadas no {@code GridPane pecas}.
     */
    @FXML
    public void creatingPieces(){
        pecas.getChildren().clear();
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

    /**
     * Associa eventos de clique a cada peça do tabuleiro.
     * Permite seleção, visualização de movimentos válidos e execução de jogadas.
     * Também alterna o turno após um movimento válido.
     */
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

                    if (piece != null && piece.getType().getColor() == turn) {
                        selectedPiece = piece;
                        selectedImage = imageView;
                        possibleMoves = piece.getPossibleMoves(board.getPieces());

                        System.out.println("Peça selecionada: " + piece);
                        System.out.println("Movimentos possíveis: " + possibleMoves);

                        ToMark(possibleMoves);
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


                            boolean isPawn = selectedPiece.getType().name().startsWith("PAWN");
                            int rowFinal = GridPane.getRowIndex(selectedImage);

//                            if (isPawn && (rowFinal == 0 || rowFinal == 7)) {
//                                ChessPiece peao = selectedPiece.getType().name();
//                                turnPawn(peao);
//                            }


                            System.out.println("Peça movida!");
                            turn = turn.next();
                            System.out.println("Turno atual: " + turn);

                            selectedPiece = null;
                            selectedImage = null;
                            possibleMoves = null;

                            ToMark(List.of());
                            creatingPieces();
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

    private void ToMark(List<Integer> moves) {
        //Resetar tabuleiro que nem o original (branco e verde)
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String color = (i + j) % 2 == 0 ? "#eeeed2" : "#769656";
                boardSquares[i][j].setStyle("-fx-background-color: " + color + ";");
            }
        }

        //Aplicar destaque nas casas (region) de acordo com a tonalidade
        for (int pos : moves) {
            int x = pos / 10;
            int y = pos % 10;

            boolean isWhiteSquare = (x + y) % 2 == 0;
            String secondColor = isWhiteSquare ? "#FF8C00" : "#CCFF00";
            boardSquares[x][y].setStyle("-fx-background-color: " + secondColor + ";");
        }
    }

    // Método de promoção comentado e pendente de implementação
    // public ChessPiece turnPawn(Pawn peao) { ... }
}