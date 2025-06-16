package com.seios.warofkings.pieces.types;

import com.seios.warofkings.board.Board;
import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;
import com.seios.warofkings.utils.BoardUtils;
import com.seios.warofkings.utils.PieceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa a peça Rei no xadrez, com implementação completa dos movimentos válidos
 * incluindo o movimento especial de roque.
 * <p>
 * O rei pode se mover uma casa em qualquer direção (vertical, horizontal ou diagonal)
 * e realizar o roque com uma torre se certas condições forem satisfeitas.
 * </p>
 *
 * @author Bia
 * @version 2.5
 * @since 2025-06-09
 */
public class King extends ChessPiece {
    /**
     * Construtor completo da peça Rei.
     *
     * @param position posição inicial da peça.
     * @param type     tipo da peça, deve ser {@code KING_WHITE (5)} ou {@code KING_BLACK (11)}.
     * @param n_moves  número de movimentos já realizados pela peça.
     * @throws IllegalArgumentException se o tipo informado não corresponder a um rei válido.
     */
    public King(int position, Type type, int n_moves) {
        if (!(type.getValor() == 5) && !(type.getValor() == 11)) {
            throw new IllegalArgumentException("Tipo inválido para rei. Esperado KING_WHITE (5) ou KING_BLACK (11).");
        }
        this.position = position;
        this.type = type;
        this.n_moves = n_moves;
    }

    /**
     * Construtor privado usado pelo método fábrica.
     *
     * @param position posição inicial da peça.
     * @param type     tipo da peça.
     */
    private King(int position, Type type) {
        super();
        this.position = position;
        this.type = type;
    }

    /**
     * Método fábrica para criar uma instância de {@code King}.
     *
     * @param position posição da peça no tabuleiro.
     * @param type     tipo da peça (branca ou preta).
     * @return nova instância de {@code King}.
     * @throws IllegalArgumentException se o tipo não for um rei válido.
     */
    public static King createKing(int position, Type type) {
        if (!(type.getValor() == 5) && !(type.getValor() == 11)) {
            throw new IllegalArgumentException("Tipo inválido para rei. Esperado KING_WHITE (5) ou KING_BLACK (11).");
        }
        return new King(position, type);
    }

    /**
     * Verifica se é possível realizar o roque com a torre especificada.
     * <p>
     * As condições para o roque são:
     * <ul>
     *     <li>O rei e a torre não devem ter se movido</li>
     *     <li>Não pode haver peças entre o rei e a torre</li>
     *     <li>O caminho entre eles não pode estar em xeque</li>
     * </ul>
     *
     * @param rook  torre envolvida no roque.
     * @param board estado atual do tabuleiro.
     * @return true se o roque é válido, false caso contrário.
     */
    private boolean isValidCastling(ChessPiece rook, ChessPiece[][] board) {
        if (this.getN_moves() > 0 || rook.getN_moves() > 0) return false;

        int kingPos = this.getPosition();
        int rookPos = rook.getPosition();

        return BoardUtils.isPathClearHorizontally(kingPos, rookPos, board) && PieceUtils.pathSafeForKing(rookPos, this, board);
    }

    /**
     * Calcula e retorna a lista de posições possíveis para o movimento do rei.
     * <p>
     * Este método considera:
     * <ul>
     *   <li>Os 8 movimentos padrão do rei (horizontal, vertical e diagonais);</li>
     *   <li>As casas de destino devem estar dentro dos limites do tabuleiro e não podem conter uma peça aliada;</li>
     *   <li>Se o rei ainda não se moveu, verifica a possibilidade de realizar o roque com uma torre da mesma cor.</li>
     * </ul>
     *
     * @param board Matriz de peças que representa o estado atual do tabuleiro.
     * @return Uma lista de posições inteiras (no formato XY, ex: 43 para linha 4, coluna 3) indicando os movimentos válidos para o rei.
     *
     * @implNote A verificação de roque considera:
     * <ul>
     *   <li>Se o rei e a torre ainda não se moveram;</li>
     *   <li>Se não há peças entre eles;</li>
     *   <li>Se o movimento não colocará o rei em xeque (essa verificação completa geralmente é feita na execução);</li>
     * </ul>
     * Os movimentos de roque, se válidos, são adicionados à lista como destino: duas casas à esquerda ou direita do rei.
     *
     * @see BoardUtils#isWithinBounds(int)
     * @see BoardUtils#findPieces(ChessPiece[][], Type)
     * @see #isOpponent(ChessPiece)
     * @see #isValidCastling(ChessPiece, ChessPiece[][])
     */
    @Override
    public List<Integer> getPossibleMoves(ChessPiece[][] board) {
        List<Integer> possibleMoves = new ArrayList<Integer>();

        int forward = -10;
        int backward = 10;
        int right = 1;
        int left = -1;
        int northwest = -11;
        int southwest = 9;
        int northeast = -9;
        int southeast = 11;

        // movimentos normais do rei
        int[] directions = {forward, backward, left, right, northwest, northeast, southwest, southeast};

        for (int dir : directions) {
            int target = this.position + dir;

            if (BoardUtils.isWithinBounds(target)) {
                ChessPiece targetPiece = board[PieceUtils.getX(target)][PieceUtils.getY(target)];
                if (targetPiece == null || isOpponent(targetPiece)) {
                    possibleMoves.add(target);
                }
            }
        }

        // verificação de roque
        if (this.getN_moves() == 0) {
            List<ChessPiece> rooks = BoardUtils.findPieces(board, this.isWhite() ? Type.ROOK_WHITE : Type.ROOK_BLACK);

            for (ChessPiece rook : rooks) {
                // ignora qualquer torre que não esteja na mesma linha do rei:
                if (PieceUtils.getX(rook.getPosition()) != PieceUtils.getX(this.position))
                    continue;

                if (isValidCastling(rook, board)) {
                    int kingPos = this.getPosition();
                    int rookPos = rook.getPosition();
                    int direction = (kingPos > rookPos) ? -1 : 1;

                    int castlingTarget = kingPos + 2 * direction;
                    possibleMoves.add(castlingTarget);
                }
            }
        }

        return possibleMoves; // possibleMoves = {..., positionRoqueLeft, positionRoqueRight}
    }

    /**
     * Move o rei para uma nova posição, incluindo a possibilidade de realizar o movimento especial de roque.
     * <p>
     * Este método estende a lógica padrão de movimentação para incluir:
     * <ul>
     *   <li>Verificação se o movimento solicitado é válido com base na lista de movimentos possíveis ({@code listMoves});</li>
     *   <li>Tratamento do roque, se o rei ainda não se moveu e há uma torre elegível na mesma linha;</li>
     *   <li>Validação se o roque deixaria o rei em xeque, impedindo o movimento se necessário;</li>
     *   <li>Movimentação da torre correspondente ao lado do roque (curto ou longo);</li>
     *   <li>Execução do movimento normal do rei, caso não seja roque.</li>
     * </ul>
     *
     * @param position   A nova posição destino no formato XY (ex: 63 representa linha 6, coluna 3).
     * @param listMoves  Lista de posições válidas para o movimento do rei.
     * @param board      O tabuleiro atual de jogo contendo as peças e seu estado.
     * @return {@code true} se o movimento foi realizado com sucesso, {@code false} caso contrário.
     *
     * @implNote Este método manipula diretamente a matriz de peças do tabuleiro para simular o roque.
     * Caso o movimento não seja de roque, a lógica padrão definida na superclasse é utilizada.
     *
     * @see PieceUtils#getX(int)
     * @see PieceUtils#getY(int)
     * @see BoardUtils#findPieces(ChessPiece[][], Type)
     * @see #isValidCastling(ChessPiece, ChessPiece[][])
     * @see #kingCheck(int, ChessPiece[][])
     */
    @Override
    public boolean moveTo(int position, List<Integer> listMoves, Board board) {
        if (!listMoves.contains(position)) return false;

        int kingPos = this.getPosition();
        boolean isCastlingMove = false;
        int castlingDirection = 0;

        if (this.getN_moves() == 0) {
            List<ChessPiece> rooks = BoardUtils.findPieces(board.getPieces(), this.isWhite() ? Type.ROOK_WHITE : Type.ROOK_BLACK);

            for (ChessPiece rook : rooks) {
                if (isValidCastling(rook, board.getPieces())) {
                    int rookPos = rook.getPosition();
                    int direction = (rookPos > kingPos) ? 1 : -1;
                    int castlingTarget = kingPos + 2 * direction;

                    if (position == castlingTarget) {
                        isCastlingMove = true;
                        castlingDirection = direction;

                        // Valida se roque colocaria rei em xeque
                        if (this.kingCheck(position, board.getPieces())) {
                            System.out.println("Movimento de roque deixaria o rei em xeque!");
                            return false;
                        }

                        // Move a torre
                        int rookTargetPos = kingPos + (direction > 0 ? 1 : -1);
                        ChessPiece[][] pieces = board.getPieces();

                        pieces[PieceUtils.getX(rookPos)][PieceUtils.getY(rookPos)] = null;
                        pieces[PieceUtils.getX(rookTargetPos)][PieceUtils.getY(rookTargetPos)] = rook;
                        rook.setPosition(rookTargetPos);
                        rook.setN_moves(rook.getN_moves() + 1);

                        board.setPieces(pieces);
                        break;
                    }
                }
            }
        }

        // Executa movimento do rei normalmente
        return super.moveTo(position, listMoves, board);
    }

    /**
     * Verifica se o rei atual está em situação de xeque-mate.
     * <p>
     * O método segue os seguintes passos para determinar o xeque-mate:
     * <ol>
     *   <li>Verifica se o rei está sob ataque (em xeque);</li>
     *   <li>Se estiver, verifica se o rei tem algum movimento possível que o tire do xeque;</li>
     *   <li>Se o rei não pode escapar, verifica se alguma peça aliada pode bloquear ou capturar a ameaça ao rei;</li>
     *   <li>Se nenhuma dessas opções for possível, retorna {@code true}, indicando xeque-mate.</li>
     * </ol>
     *
     * @param board Instância do tabuleiro atual do jogo, contendo todas as peças em suas posições.
     * @return {@code true} se o rei estiver em xeque-mate, ou {@code false} caso contrário.
     *
     * @implNote Este método realiza simulações de movimentos para testar se o rei ou peças aliadas
     * conseguiriam impedir o xeque. A integridade das posições é restaurada após cada simulação.
     */
    public boolean xequeMate(Board board) {
        ChessPiece[][] pieces = board.getPieces();


        // verifica se o rei está em xeque
        if (!PieceUtils.isPieceUnderAttack(this, pieces)) {
            return false;
        }

        // verifica se o rei pode escapar
        List<Integer> kingMoves = this.getPossibleMoves(pieces);
        for (Integer move : kingMoves) {
            ChessPiece[][] simulation = BoardUtils.copyBoard(pieces);
            int from = this.getPosition();
            this.setPosition(move);

            simulation[PieceUtils.getX(from)][PieceUtils.getY(from)] = null;
            simulation[PieceUtils.getX(move)][PieceUtils.getY(move)] = this;

            boolean aindaEmXeque = PieceUtils.isPieceUnderAttack(this, simulation);
            this.setPosition(from);

            if (!aindaEmXeque) {
                return false; // Rei conseguiu escapar
            }
        }

        // verifica se alguma peça aliada pode ajudar
        List<ChessPiece> aliadas = BoardUtils.getAliadas(this, pieces);

        for (ChessPiece aliada : aliadas) {
            List<Integer> movimentos = aliada.getPossibleMoves(pieces);
            int from = aliada.getPosition();

            for (Integer destino : movimentos) {
                ChessPiece[][] simulation = BoardUtils.copyBoard(pieces);

                simulation[PieceUtils.getX(from)][PieceUtils.getY(from)] = null;
                simulation[PieceUtils.getX(destino)][PieceUtils.getY(destino)] = aliada;

                aliada.setPosition(destino);
                boolean reiAindaEmXeque = PieceUtils.isPieceUnderAttack(this, simulation);
                aliada.setPosition(from);

                if (!reiAindaEmXeque) {
                    return false; // Alguma peça consegue salvar o rei
                }
            }
        }

        // nenhum escape foi possível
        return true;
    }

    public boolean isRoqueRight() {
        return isRoqueRight;
    }

    public void setRoqueRight(boolean roqueRight) {
        isRoqueRight = roqueRight;
    }

    public boolean isRoqueLeft() {
        return isRoqueLeft;
    }

    public void setRoqueLeft(boolean roqueLeft) {
        isRoqueLeft = roqueLeft;
    }
}



