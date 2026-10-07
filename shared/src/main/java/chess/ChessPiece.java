package chess;

import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private final ChessGame.TeamColor teamColor;
    private final ChessPiece.PieceType pieceType;
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
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition){
        ChessPiece myPiece = board.getPiece(myPosition);
        int[][] myOffsets = myPiece.pieceTypeOffsets();
        PieceMovesCalculator pieceCalc = myPiece.pieceTypeMoves();
        return pieceCalc.possibleMoves(board, myPosition, myOffsets);
    }

    public PieceAttacksCalculator pieceTypeAttacks(){
        PieceType theType = this.getPieceType();
        return switch (theType) {
            case PieceType.BISHOP, PieceType.ROOK, PieceType.QUEEN -> new SlidingMovesCalculator();
            case PieceType.KING, PieceType.KNIGHT -> new HoppingMovesCalculator();
            case PieceType.PAWN -> new PawnMovesCalculator();
        };
    }
    public PieceMovesCalculator pieceTypeMoves(){
        PieceType theType = this.getPieceType();
        return switch (theType) {
            case PieceType.BISHOP, PieceType.ROOK, PieceType.QUEEN -> new SlidingMovesCalculator();
            case PieceType.KING, PieceType.KNIGHT -> new HoppingMovesCalculator();
            case PieceType.PAWN -> new PawnMovesCalculator();
        };
    }

    public int[][] pieceTypeOffsets(){
        return switch (this.getPieceType()) {
            case PieceType.BISHOP -> //Starting the sliding pieces
                    new int[][]{{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
            case PieceType.ROOK -> new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            case PieceType.QUEEN,
                 PieceType.KING -> //merging these two since they have the same offsets, just used differently.
                //combining the rook and bishop offsets
                    new int[][]{{1, -1}, {1, 0}, {1, 1}, {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {0, -1}};
            case PieceType.KNIGHT ->
                    new int[][]{{1, 2}, {1, -2}, {-1, 2}, {-1, -2}, {2, 1}, {2, -1}, {-2, 1}, {-2, -1}};
            case PieceType.PAWN ->
                //Only 3 offsets because the pawn can only go in 3 directions.
                    new int[][]{{1, 1}, {1, -1}, {1, 0}};
        };
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
