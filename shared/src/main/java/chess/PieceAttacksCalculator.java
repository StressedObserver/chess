package chess;

import java.util.Collection;

public interface PieceAttacksCalculator {
    Collection<ChessMove> attackVision(ChessBoard myBoard, ChessPosition myPos, int[][] offsets);
}