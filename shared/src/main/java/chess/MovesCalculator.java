package chess;

import java.util.Collection;

public interface MovesCalculator {
    Collection<ChessMove> attackVision(ChessBoard board, ChessPosition startPos, int[][] offsets);

    Collection<ChessMove> possibleMoves(ChessBoard board, ChessPosition myPosition, int[][] myOffsets);
}
