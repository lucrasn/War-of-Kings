package com.seios.warofkings.pieces.types;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;
import com.seios.warofkings.utils.PieceUtils;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Esta classe é responsável pela implementação dos movimentos da peça Cavalo
 *
 * @author lucas
 * @version 1.0
 * @since 2025-06-01
 */
public class Knight extends ChessPiece  {
    public Knight(int position, Type type, int n_moves)  {
        if (!(type.getValor() == 2) && !(type.getValor() == 8)) {
            throw new IllegalArgumentException("Tipo inválido para cavalo. Esperado KNIGHT_WHITE (2) ou KNIGHT_BLACK (8).");
        }
        this.position = position;
        this.type = type;
        this.n_moves = n_moves;
    }

    private Knight(int position, Type type) {
        super();
        this.position = position;
        this.type = type;
    }

    // Metodo de fábrica
    public static Knight createKnight(int position, Type type) {
        if (!(type.getValor() == 2) && !(type.getValor() == 8)) {
            throw new IllegalArgumentException("Tipo inválido para cavalo. Esperado KNIGHT_WHITE (2) ou KNIGHT_BLACK (8).");
        }
        return new Knight(position, type);
    }

    /**
     * Retorna as posições válidas para onde o peão pode se mover, considerando:
     * <ul>
     *   <li>Movimentos em 'L' do cavalo</li>
     * </ul>
     *
     * @param board matriz representando o estado atual do tabuleiro
     * @return lista de posições inteiras possíveis para o movimento
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
            if (PieceUtils.isWithinBounds(newPos)) {
                ChessPiece target = board[PieceUtils.getX(newPos)][PieceUtils.getY(newPos)];
                if (target == null || isOpponent(target)) {
                    if (!kingCheck(newPos, board)) {
                        possibleMoves.add(newPos);
                    }
                }
            }
        }

        return possibleMoves;
    }
}
