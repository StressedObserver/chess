package chess;

import java.util.Collection;

public interface PieceAttacksCalculator {
    Colllection<ChessMove> attackVision(ChessBoard myBoard, ChessPosition myPos);
}