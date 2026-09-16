package chess;

import java.util.Collection;

public interface PieceMovesCalculator {
    Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPos, int[][] offsets);
    //The offsets field means that we don't have to create a calculator for each piece type.
    //Instead, we can classify the pieces into "sliding" (bishops, rooks, and queens), "hopping" (kings, knights)
    //and "pawn" (because they need extra logic for promoting).
}
