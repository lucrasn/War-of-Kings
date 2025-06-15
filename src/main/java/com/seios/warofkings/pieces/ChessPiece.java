package com.seios.warofkings.pieces;

import com.seios.warofkings.board.Board;
import com.seios.warofkings.board.enums.Turn;
import com.seios.warofkings.pieces.enums.Type;
import com.seios.warofkings.utils.BoardUtils;
import com.seios.warofkings.utils.PieceUtils;

import com.seios.warofkings.pieces.enums.Type;
import com.seios.warofkings.board.enums.Turn;

import java.util.List;

/**
 * Classe abstrata base para todas as peças de xadrez.
 * <p>
 * Fornece a estrutura comum e a lógica compartilhada entre as peças, incluindo
 * movimentação, posição, número de movimentos e verificação de regras como xeque.
 * </p>
 *
 * <p><b>Nota:</b> Todas as peças concretas (Peão, Torre, Rei etc.) devem estender esta classe.</p>
 *
 * @author Lucas
 * @version 1.3
 * @since 2025-05-14
 */
public abstract class ChessPiece implements Movable, Positionable {
    protected int position;
    protected Type type;
    protected int n_moves;

    /**
     * Construtor padrão, inicia com zero movimentos.
     */
    public ChessPiece() {
        this.n_moves = 0;
    }

    /**
     * Construtor alternativo que define o número de movimentos.
     *
     * @param n_moves quantidade de movimentos já realizados.
     */
    public ChessPiece(int n_moves) {
        this.n_moves = n_moves;
    }

    /**
     * Verifica se um movimento deixaria o rei em xeque.
     * Simula o movimento e avalia se o rei ainda estaria seguro.
     *
     * @param to         posição de destino simulada.
     * @param piecesMap  matriz de peças do tabuleiro.
     * @return true se o movimento coloca o rei em xeque.
     */
    protected boolean kingCheck(int to, ChessPiece[][] piecesMap) {
        ChessPiece[][] simulatedBoard = BoardUtils.copyBoard(piecesMap);
        simulatedBoard[this.getX()][this.getY()] = null;
        simulatedBoard[PieceUtils.getX(to)][PieceUtils.getY(to)] = this;

        boolean isWhite = this.isWhite();
        List<ChessPiece> kings = BoardUtils.findPieces(simulatedBoard ,isWhite ? Type.KING_WHITE : Type.KING_BLACK);
        if (kings.isEmpty()) return false;
        ChessPiece king = kings.getFirst();

        if (PieceUtils.isSamePiece(this, king)) {
            int from = this.getPosition();
            this.setPosition(to);

            boolean isUnderAttack = PieceUtils.isPieceUnderAttack(this, simulatedBoard);
            this.setPosition(from);

            return isUnderAttack;
        }

        return PieceUtils.isPieceUnderAttack(king, simulatedBoard);
    }

    /**
     * Verifica se uma peça é adversária.
     *
     * @param other peça a ser comparada.
     * @return true se for de cor oposta.
     */
    protected boolean isOpponent(ChessPiece other) {
        if (other == null) return false;
        boolean isWhite = this.isWhite();
        boolean isWhiteTo = other.isWhite();
        return (isWhite != isWhiteTo);
    }

    /**
     * Verifica se a peça é branca.
     *
     * @return true se o tipo for branco.
     */
    public boolean isWhite() {
        return this.type.getValor() <= 5;
    }

    /**
     * Retorna a coordenada X (linha) da posição.
     *
     * @return valor da linha (0 a 7).
     */
    @Override
    public int getX() {
        return this.position / 10; // Dezena
    }

    /**
     * Retorna a coordenada Y (coluna) da posição.
     *
     * @return valor da coluna (0 a 7).
     */
    @Override
    public int getY() {
        return this.position % 10; // Unidade
    }

    /**
     * Representação em string da peça com nome e posição atual.
     *
     * @return descrição textual da peça.
     */
    @Override
    public String toString() {
        return "Tipo da peça: " + type.displayName() + ". " +
                "Na posição : (" + this.getX() +  ", " + this.getY() + ")";
    }

    /**
     * Retorna a posição inteira da peça.
     *
     * @return posição no formato XY (dezena = linha, unidade = coluna).
     */
    public int getPosition() {
        return this.position;
    }

    /**
     * Define a posição atual da peça.
     *
     * @param position nova posição.
     */
    @Override
    public void setPosition(int position) { // muda a posição
        this.position = position;
    }

    /**
     * Realiza o movimento da peça para uma nova posição, desde que válida e segura.
     *
     * @param position   nova posição de destino.
     * @param listMoves  lista de movimentos válidos.
     * @param board      estado atual do tabuleiro.
     * @return true se o movimento foi realizado com sucesso.
     */
    @Override
    public boolean moveTo(int position, List<Integer> listMoves, Board board) {
        if (position == getPosition()) return false;

        if (!listMoves.contains(position)) {
            return false;
        }

        ChessPiece[][] pieces = board.getPieces();

        if (kingCheck(position, pieces)) {
            System.out.println("Movimento deixaria o rei em xeque!");
            return false;
        }

        pieces[getX()][getY()] = null; // onde estava a peça
        pieces[PieceUtils.getX(position)][PieceUtils.getY(position)] = this; // onde a peça foi
        board.setPieces(pieces); // altera de fato o movimento

        setPosition(position);
        this.n_moves++;

        return true;
    }

    /**
     * Retorna o tipo da peça.
     *
     * @return {@link Type} da peça.
     */
    public Type getType() {
        return this.type;
    }

    /**
     * Define o tipo da peça.
     *
     * @param type novo tipo.
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Retorna o número de movimentos realizados pela peça.
     *
     * @return número de movimentos.
     */
    public int getN_moves() {
        return this.n_moves;
    }

    /**
     * Define o número de movimentos realizados.
     *
     * @param n_moves novo número de movimentos.
     */
    public void setN_moves(int n_moves) {
        this.n_moves = n_moves;
    }

    /**
     * Retorna o nome da imagem associada à peça, para renderização no front-end.
     *
     * @return nome do arquivo da imagem correspondente à peça.
     */
    public String getImgName() {
        return switch (type) {
            case PAWN_BLACK -> "Pawn_Black.png";
            case ROOK_BLACK -> "Rook_Black.png";
            case KNIGHT_BLACK -> "Knight_Black.png";
            case BISHOP_BLACK -> "Bishop_Black.png";
            case QUEEN_BLACK -> "Queen_Black.png";
            case KING_BLACK -> "King_Black.png";
            case PAWN_WHITE -> "Pawn_White.png";
            case ROOK_WHITE -> "Rook_White.png";
            case KNIGHT_WHITE -> "Knight_White.png";
            case BISHOP_WHITE -> "Bishop_White.png";
            case QUEEN_WHITE -> "Queen_White.png";
            case KING_WHITE -> "King_White.png";
            default -> null;
        };
    }
}
