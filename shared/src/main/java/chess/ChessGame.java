package chess;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

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
        ChessPiece piece = myBoard.getPiece(startPosition);
        if(piece == null){
            return List.of();
        }
        TeamColor moverColor = piece.getTeamColor();
        Collection<ChessMove> possibleMoves = piece.pieceMoves(myBoard, startPosition);
        Collection<ChessMove> validMoves = new ArrayList<>();

        for(ChessMove possibleMove: possibleMoves){
            ChessBoard tempBoard = deepBoardCopy(myBoard);
            applyMove(tempBoard, possibleMove);
            if(!isInCheckHelper(tempBoard, moverColor)){
                validMoves.add(possibleMove);
            }
        }
        return validMoves;
    }

    private void applyMove(ChessBoard board, ChessMove move){
        ChessPosition startPos = move.getStartPosition();
        ChessPosition endPos = move.getEndPosition();
        ChessPiece movingPiece = board.getPiece(startPos);
        //remove piece at startPos by overwriting it.
        board.addPiece(startPos, null);
        //put it at the end position to pretend that it has moved.
        board.addPiece(endPos, movingPiece);
    }

    /**
     * Makes a move in the chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        //Time for a big one.
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        return isInCheckHelper(myBoard, teamColor);
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
        int[][] offsets = myPiece.pieceTypeOffsets();
        PieceAttacksCalculator pieceCalc = myPiece.pieceTypeAttacks();
        for(ChessMove move: pieceCalc.attackVision(board, startPos, offsets)){
            if(move.getEndPosition().equals(targetPos)){
                return true;
            }
        }
        return false; //Finish implementing this once the attack functions are up.
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

    private boolean squareNotEmpty(ChessPosition posToCheck){
        return myBoard.getPiece(posToCheck) != null;
    }

    public boolean noValidMoves(TeamColor color){
        Collection<ChessMove> availableMoves = new ArrayList<>();
        for(int i = 1; i<=8; i++){
            for(int j = 1; j <= 8; j++){
                ChessPosition myPos = new ChessPosition(i,j);
                if(squareNotEmpty(myPos) && color == myBoard.getPiece(myPos).getTeamColor()){
                    availableMoves.addAll(validMoves(myPos));
                }
            }
        }
        return availableMoves.isEmpty();
    }
    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        return (noValidMoves(teamColor) && isInCheck(teamColor));
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        return (noValidMoves(teamColor) && !isInCheck(teamColor));
    }

    private ChessBoard deepBoardCopy(ChessBoard original){
        ChessBoard copy = new ChessBoard();
        for(int row = 1; row <=8; row++){
            for(int col = 1; col <=8; col++){
                ChessPosition pos = new ChessPosition(row, col);
                ChessPiece piece = original.getPiece(pos);
                if(piece != null){
                    copy.addPiece(pos, new ChessPiece(piece.getTeamColor(), piece.getPieceType()));
                } else{
                    copy.addPiece(pos, null);
                }
            }
        }
        return copy;
    }

    /**
     * Sets this game's chessboard to a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.myBoard = deepBoardCopy(board);
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
