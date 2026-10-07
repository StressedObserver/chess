package chess;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    TeamColor teamTurn;
    private ChessBoard myBoard;
    public ChessGame() {
        myBoard = new ChessBoard();
        teamTurn = TeamColor.WHITE; //Games start with white moving first
        myBoard.resetBoard(); //This will set the chess board to its default.
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Sets which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets all valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Makes a move in the chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }
    private boolean isInCheckHelper(ChessBoard board, TeamColor teamTurn){
        ChessPosition teamKingLoc = teamKingLocatorOnBoard(board, teamTurn);
        for(int i =1; i<=8; i++){
            for(int j = 1; j<=8; j++){ //Iterating through all the chessboard.
                ChessPosition myPosition = new ChessPosition(i, j);
                ChessPiece myPiece = board.getPiece(myPosition);
                if(myPiece != null && teamTurn != myPiece.getTeamColor()){ //If there is a piece there,
                    //and it is not part of the team currently moving.
                    if(pieceAttacksSquare(board, myPosition, teamKingLoc)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean pieceAttacksSquare(ChessBoard board, ChessPosition startPos, ChessPosition targetPos){
        ChessPiece myPiece = board.getPiece(startPos);
        switch(myPiece.getPieceType()) {
            case ChessPiece.PieceType.BISHOP: //Starting the sliding pieces
                int[][] bishopOffsets = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
                SlidingMovesCalculator bishopCalc = new SlidingMovesCalculator();
                return bishopCalc.possibleMoves(board, myPos, bishopOffsets);
            case ChessPiece.PieceType.ROOK:
                int[][] rookOffsets = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
                SlidingMovesCalculator rookCalc = new SlidingMovesCalculator();
                return rookCalc.possibleMoves(board, myPos, rookOffsets);
            case ChessPiece.PieceType.QUEEN:
                int[][] queenOffsets = {{1, -1}, {1, 0}, {1, 1}, {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {0, -1}}; //combining the rook and bishop offsets
                SlidingMovesCalculator queenCalc = new SlidingMovesCalculator();
                return queenCalc.possibleMoves(board, myPos, queenOffsets);
            case ChessPiece.PieceType.KING: //Starting the hopping pieces
                int[][] kingOffsets = {{1, -1}, {1, 0}, {1, 1}, {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {0, -1}}; //Same as the queen's just gets used in different function.
                HoppingMovesCalculator kingCalc = new HoppingMovesCalculator();
                return kingCalc.possibleMoves(board, myPos, kingOffsets);
            case ChessPiece.PieceType.KNIGHT:
                int[][] knightOffsets = {{1, 2}, {1, -2}, {-1, 2}, {-1, -2}, {2, 1}, {2, -1}, {-2, 1}, {-2, -1}};
                HoppingMovesCalculator knightCalc = new HoppingMovesCalculator();
                return knightCalc.possibleMoves(board, myPos, knightOffsets);
            case ChessPiece.PieceType.PAWN:
                int[][] pawnOffsets = {{1, 1}, {1, -1}, {1, 0}}; //Only 3 offsets because the pawn can only go in 3 directions.
                PawnMovesCalculator pawnCalc = new PawnMovesCalculator();
                return pawnCalc.possibleMoves(board, myPos, pawnOffsets);
            default:
                return List.of();
        }
        return true; //Finish implementing this once the attack functions are up.
    }

    private ChessPosition teamKingLocatorOnBoard(ChessBoard board, TeamColor team){
        for(int i = 1; i <= 8; i++){
            for(int j = 1; j <= 8; j++){
                ChessPosition myPosition = new ChessPosition(i,j);
                ChessPiece piece = board.getPiece(myPosition);
                if(piece != null){
                    if(team == piece.getTeamColor() && piece.getPieceType() == ChessPiece.PieceType.KING){
                        return myPosition;
                    }
                }
            }
        }
        return null; //We should never reach this but if there's somehow no king this is a failsafe.
    }
    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard to a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return myBoard;
    }
}
