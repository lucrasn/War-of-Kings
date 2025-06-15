package com.seios.warofkings.pieces.types;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;
import com.seios.warofkings.utils.BoardUtils;
import com.seios.warofkings.utils.PieceUtils;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Representa a peça Cavalo no xadrez, com implementação completa de seus movimentos em "L".
 * <p>
 * O cavalo é a única peça que pode "saltar" sobre outras peças no tabuleiro, movendo-se
 * em padrões específicos de duas casas em uma direção e uma casa em perpendicular.
 * </p>
 *
 * <p><b>Nota:</b> Esta classe valida o {@link Type} para garantir que apenas cavalos válidos
 * (branco ou preto) sejam instanciados corretamente.</p>
 *
 * @author Lucas
 * @version 1.0
 * @since 2025-06-01
 */
public class Knight extends ChessPiece  {
    /**
     * Construtor completo da peça Cavalo.
     *
     * @param position posição no tabuleiro.
     * @param type     tipo da peça, deve ser {@code KNIGHT_WHITE (2)} ou {@code KNIGHT_BLACK (8)}.
     * @param n_moves  número de movimentos realizados pela peça.
     * @throws IllegalArgumentException se o tipo informado não for um cavalo válido.
     */
    public Knight(int position, Type type, int n_moves)  {
        if (!(type.getValor() == 2) && !(type.getValor() == 8)) {
            throw new IllegalArgumentException("Tipo inválido para cavalo. Esperado KNIGHT_WHITE (2) ou KNIGHT_BLACK (8).");
        }
        this.position = position;
        this.type = type;
        this.n_moves = n_moves;
    }

    /**
     * Construtor privado utilizado pelo método fábrica.
     *
     * @param position posição no tabuleiro.
     * @param type     tipo da peça.
     */
    private Knight(int position, Type type) {
        super();
        this.position = position;
        this.type = type;
    }

    /**
     * Método fábrica para criação de instâncias da peça Cavalo.
     *
     * @param position posição no tabuleiro.
     * @param type     tipo da peça.
     * @return instância de {@code Knight}.
     * @throws IllegalArgumentException se o tipo informado não for um cavalo válido.
     */
    public static Knight createKnight(int position, Type type) {
        if (!(type.getValor() == 2) && !(type.getValor() == 8)) {
            throw new IllegalArgumentException("Tipo inválido para cavalo. Esperado KNIGHT_WHITE (2) ou KNIGHT_BLACK (8).");
        }
        return new Knight(position, type);
    }

    /**
     * Retorna as posições possíveis para as quais o cavalo pode se mover.
     * O cavalo se move em forma de "L" — duas casas em uma direção e uma casa em perpendicular.
     * Pode capturar peças adversárias e ignorar obstáculos no caminho.
     *
     * @param board matriz representando o estado atual do tabuleiro.
     * @return lista de posições válidas como inteiros.
     */
    @Override
    public List<Integer> getPossibleMoves(ChessPiece[][] board) {
        List<Integer> possibleMoves = new ArrayList<Integer>();
        int pos = this.position;

        Map<String, Integer> moves = new HashMap<String, Integer>() {{
            put("northNortheast", -19); // NNE
            put("eastNortheast", -8); // ENE
            put("EastSoutheast", 12); // ESE
            put("southSoutheast", 21); // SSE
            put("southSouthwest", 19); // SSW
            put("westSouthwest", 8); // WSW
            put("westNorthwest", -12); // WNW
            put("northNorthwest", -21); // NNW
        }};

        for (Integer offset : moves.values()) {
            int newPos = pos + offset;
            if (BoardUtils.isWithinBounds(newPos)) {
                ChessPiece target = board[PieceUtils.getX(newPos)][PieceUtils.getY(newPos)];
                if (target == null || isOpponent(target)) {
                    possibleMoves.add(newPos);
                }
            }
        }

        return possibleMoves;
    }
}
