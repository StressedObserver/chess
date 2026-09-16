package chess;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private ChessGame.TeamColor teamColor;
    private ChessPiece.PieceType pieceType;
    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        teamColor = pieceColor;
        pieceType = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return this.teamColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return this.pieceType;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece myPiece = board.getPiece(myPosition);
        switch(myPiece.getPieceType()){
            case PieceType.BISHOP: //Starting the sliding pieces
                int[][] bishopOffsets = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
                SlidingMovesCalculator bishopCalc = new SlidingMovesCalculator();
                return bishopCalc.possibleMoves(board, myPosition, bishopOffsets);
            case PieceType.ROOK:
                int[][] rookOffsets = {{1,0}, {-1, 0}, {0, 1}, {0, -1}};
                SlidingMovesCalculator rookCalc = new SlidingMovesCalculator();
                return rookCalc.possibleMoves(board, myPosition, rookOffsets);
            case PieceType.QUEEN:
                int[][] queenOffsets = {{1,-1}, {1, 0}, {1, 1}, {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {0, -1}}; //combining the rook and bishop offsets
                SlidingMovesCalculator queenCalc = new SlidingMovesCalculator();
                return queenCalc.possibleMoves(board, myPosition, queenOffsets);
            case PieceType.KING: //Starting the hopping pieces
                int[][] kingOffsets = {{1,-1}, {1, 0}, {1, 1}, {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {0, -1}}; //Same as the queen's just gets used in different function.
                HoppingMovesCalculator kingCalc = new HoppingMovesCalculator();
                return kingCalc.possibleMoves(board, myPosition, kingOffsets);
            case PieceType.KNIGHT:
                int[][] knightOffsets = {{1, 2}, {1, -2}, {-1, 2}, {-1, -2}, {2, 1}, {2, -1}, {-2, 1}, {-2, -1}};
                HoppingMovesCalculator knightCalc = new HoppingMovesCalculator();
                return knightCalc.possibleMoves(board, myPosition, knightOffsets);
            case PieceType.PAWN:
                int[][] pawnOffsets = {{1, 1}, {1, -1}, {1, 0}}; //Only 3 offsets because the pawn can only go in 3 directions.
                PawnMovesCalculator pawnCalc = new PawnMovesCalculator();
                return pawnCalc.possibleMoves(board, myPosition, pawnOffsets);
            default:
                return List.of();

        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return teamColor == that.teamColor && pieceType == that.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamColor, pieceType);
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "teamColor=" + teamColor +
                ", pieceType=" + pieceType +
                '}';
    }
}
