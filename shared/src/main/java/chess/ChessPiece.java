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
        int[][] myOffsets = myPiece.pieceTypeOffsets();
        switch(myPiece.getPieceType()){
            case PieceType.BISHOP, PieceType.ROOK, PieceType.QUEEN:
                SlidingMovesCalculator slidingCalc = new SlidingMovesCalculator();
                return slidingCalc.possibleMoves(board, myPosition, myOffsets);
            case PieceType.KING, PieceType.KNIGHT:
                HoppingMovesCalculator hoppingCalc = new HoppingMovesCalculator();
                return hoppingCalc.possibleMoves(board, myPosition, myOffsets);
            case PieceType.PAWN:
                PawnMovesCalculator pawnCalc = new PawnMovesCalculator();
                return pawnCalc.possibleMoves(board, myPosition, myOffsets);
            default:
                return List.of();
        }
    }


    private int[][] pieceTypeOffsets(){
        switch(this.getPieceType()) {
            case PieceType.BISHOP: //Starting the sliding pieces
                int[][] bishopOffsets = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
                return bishopOffsets;
            case PieceType.ROOK:
                int[][] rookOffsets = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
                return rookOffsets;
            case PieceType.QUEEN,
                 PieceType.KING: //merging these two since they have the same offsets, just used differently.
                int[][] kingQueenOffsets = {{1, -1}, {1, 0}, {1, 1}, {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {0, -1}}; //combining the rook and bishop offsets
                return kingQueenOffsets;
            case PieceType.KNIGHT:
                int[][] knightOffsets = {{1, 2}, {1, -2}, {-1, 2}, {-1, -2}, {2, 1}, {2, -1}, {-2, 1}, {-2, -1}};
                return knightOffsets;
            case PieceType.PAWN:
                int[][] pawnOffsets = {{1, 1}, {1, -1}, {1, 0}}; //Only 3 offsets because the pawn can only go in 3 directions.
                return pawnOffsets;
            default:
                return null;
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
