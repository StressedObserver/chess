package chess;

import java.util.Arrays; //This is so we can create an array to represent our Chessboard.
import java.util.Objects; //Don't need this quite yet, but it could be helpful.

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    ChessPiece[][] chessSquares = new ChessPiece[8][8]; //This will be the representation of our chessboard.
    public ChessBoard() {

    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        //Remember that arrays are 0-indexed, but our row and column index aren't because that's more intuitive to me.
        chessSquares[position.getRow()-1][position.getColumn()-1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        //Same concept as addPiece, only we are returning what we find instead of adding a piece there.
        return chessSquares[position.getRow()-1][position.getColumn()-1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        //I remember this being such a pain last time, and I have no doubts that I'll still have trouble.
        chessSquares = new ChessPiece[8][8]; //Creating a new blank one.
        chessSquares[0] = new ChessPiece[]{new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK)};
        chessSquares[1] = new ChessPiece[]{new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN)};
        chessSquares[6] = new ChessPiece[]{new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN)};
        chessSquares[7] = new ChessPiece[]{new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK)};
        //Rest of the lines don't need to be set because they are blank at the start of a game.
    }

    //In order to determine what moves we can make, I'm making a function to check if a position is in bounds.
    public boolean isInBounds(ChessPosition position){
        //Since the .getRow() and .getColumn() functions are designed to have indexes from 1 to 8 instead of the array's
        //indexing of 0 to 7, the comparisons need to be adjusted accordingly.
        return ((position.getRow() > 0 && position.getRow() <= 8) && (position.getColumn() > 0 && position.getColumn() <= 8));
    }

    //I've forgotten how useful these generated functions are.
    //Except, now they seem to be the cause of my issues even though I haven't modified them or anything.
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(chessSquares, that.chessSquares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(chessSquares);
    }

    //If I remember correctly, this is more so for debugging, but it is a very useful tool while debugging.
    @Override
    public String toString() {
        return "ChessBoard{" +
                "chessSquares=" + Arrays.toString(chessSquares) +
                '}';
    }


}
