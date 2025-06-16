package com.seios.warofkings.ui;

import com.seios.warofkings.pieces.enums.Type;
import com.seios.warofkings.pieces.types.*;
import com.seios.warofkings.utils.ImageFactoryUtils;
import javafx.fxml.FXML;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.InputStream;
import java.util.ArrayList;
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
    private boolean blockPromotion = false;//essa vai ser a flag usada

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
                    if (blockPromotion) {
                        System.out.println("Escolha a promoção antes de continuar.");
                        return;
                    }

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
                        boolean moved = selectedPiece.moveTo(positionTo, possibleMoves, board);
                        if (moved) {
                            pecas.getChildren().remove(imageView);
                            pecas.getChildren().remove(selectedImage);

                            String imgName = selectedPiece.getImgName();
                            ImageView newPieceImage = ImageFactoryUtils.createPieceImage("/imagens/" + imgName);
                            pecas.add(newPieceImage, colY, rowX);

                            Integer origemColY = GridPane.getColumnIndex(selectedImage);
                            Integer origemRowX = GridPane.getRowIndex(selectedImage);
                            selectedImage = newPieceImage;

                            ImageView newTrans = ImageFactoryUtils.createTransparentImage();
                            pecas.add(newTrans, origemColY, origemRowX);

                            boolean isPawn = selectedPiece.getType().name().startsWith("PAWN");
                            int rowFinal = GridPane.getRowIndex(selectedImage);

                            if (isPawn && (rowFinal == 0 || rowFinal == 7)) {
                                blockPromotion = true; //essa porra aqui vai travar
                                turnPawn(selectedPiece);
                            } else {
                                turn = turn.next();
                            }

                            System.out.println("Peça movida!");
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

    /**
     * Atualiza as cores do tabuleiro de xadrez para destacar as casas possíveis de movimento.
     * <p>
     * O método realiza duas operações principais:
     * <ol>
     *   <li>Reseta todas as casas do tabuleiro para suas cores originais padrão (branco e verde);</li>
     *   <li>Aplica uma cor de destaque nas casas cujas posições estão listadas como possíveis movimentos.</li>
     * </ol>
     *
     * @param moves Lista de posições inteiras representando as casas onde uma peça pode se mover.
     *              Cada posição está no formato XY, onde X representa a linha (0 a 7) e Y a coluna (0 a 7).
     *              Por exemplo, a posição 43 indica linha 4, coluna 3.
     *
     * @implNote As cores originais utilizadas são:
     * <ul>
     *   <li>Casa clara: {@code #eeeed2}</li>
     *   <li>Casa escura: {@code #769656}</li>
     * </ul>
     * E os destaques são aplicados com:
     * <ul>
     *   <li>Destaque em casa clara: {@code #c2c2ae}</li>
     *   <li>Destaque em casa escura: {@code #989885}</li>
     * </ul>
     */

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
            String secondColor = isWhiteSquare ? "#c2c2ae" : "#989885";
            boardSquares[x][y].setStyle("-fx-background-color: " + secondColor + ";");
        }
    }

    // Método de promoção comentado e pendente de implementação
    @FXML FlowPane piecesTurn;
    public void turnPawn(ChessPiece selectedPiece) {
        boolean isWhite = selectedPiece.getType().name().endsWith("WHITE");
        int row = GridPane.getRowIndex(selectedImage);
        int col = GridPane.getColumnIndex(selectedImage);

        int finalRow = row;
        int finalCol = col;

        piecesTurn.getChildren().clear();

        List<ChessPiece> pawnTurn = new ArrayList<>();
        if (isWhite) {
            pawnTurn.add(Queen.createQueen(finalRow * 10 + finalCol, Type.QUEEN_WHITE));
            pawnTurn.add(Bishop.createBishop(finalRow * 10 + finalCol, Type.BISHOP_WHITE));
            pawnTurn.add(Rook.createRook(finalRow * 10 + finalCol, Type.ROOK_WHITE));
            pawnTurn.add(Knight.createKnight(finalRow * 10 + finalCol, Type.KNIGHT_WHITE));
        } else {
            pawnTurn.add(Queen.createQueen(finalRow * 10 + finalCol, Type.QUEEN_BLACK));
            pawnTurn.add(Bishop.createBishop(finalRow * 10 + finalCol, Type.BISHOP_BLACK));
            pawnTurn.add(Rook.createRook(finalRow * 10 + finalCol, Type.ROOK_BLACK));
            pawnTurn.add(Knight.createKnight(finalRow * 10 + finalCol, Type.KNIGHT_BLACK));
        }

        for (ChessPiece newPiece : pawnTurn) {
            String imgName = newPiece.getImgName();
            ImageView imgView = ImageFactoryUtils.createPieceImage("/imagens/" + imgName);
            if (imgView != null) {
                imgView.setFitHeight(50);
                imgView.setFitWidth(50);
                imgView.setCursor(Cursor.HAND);

                imgView.setOnMouseClicked(event -> {
                    board.getPieces()[finalRow][finalCol] = newPiece;

                    creatingPieces();
                    movingPieces();
                    piecesTurn.getChildren().clear();

                    this.selectedPiece = null;
                    this.selectedImage = null;
                    this.possibleMoves = null;

                    System.out.println("Peão promovido para: " + newPiece.getType());

                    turn = turn.next();
                    System.out.println("Turno atual: " + turn);

                    blockPromotion = false; //esssa outra aqui é pra destravar
                });


                piecesTurn.getChildren().add(imgView);
            }
        }
    }
}