package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PawnMovesCalculator implements PieceMovesCalculator{ //For pawns specifically, since we need to add promotion logic
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPos, int[][] offsets) {
        Collection<ChessMove> possibleMoves = new ArrayList<>();
        ChessPiece myPiece = board.getPiece(myPos);
        if(myPiece.getTeamColor() == ChessGame.TeamColor.BLACK){ //Need to flip where the pieces can go depending on team color
            for(int offset[]: offsets){
                offset[0] *= -1;
            }
        }

        for(int offset[] : offsets){
            int offsetY = offset[0];
            int offsetX = offset[1];
            int spottedY = myPos.getRow() + offsetY;
            int spottedX = myPos.getColumn() + offsetX;
            ChessPosition potentialPos = new ChessPosition(spottedY, spottedX);
            if(board.isInBounds(potentialPos)){
                if(board.getPiece(potentialPos) == null  && offset[1] == 0){//The spot is blank
                    if(promotionEligible(potentialPos.getRow(), myPiece)){ //promo conditions fulfilled.
                        possibleMoves.add(new ChessMove(myPos, potentialPos, ChessPiece.PieceType.BISHOP));
                        possibleMoves.add(new ChessMove(myPos, potentialPos, ChessPiece.PieceType.ROOK));
                        possibleMoves.add(new ChessMove(myPos, potentialPos, ChessPiece.PieceType.QUEEN));
                        possibleMoves.add(new ChessMove(myPos, potentialPos, ChessPiece.PieceType.KNIGHT));
                    } else{ //promo conditions not fulfilled
                        ChessPosition lookAhead = new ChessPosition(spottedY + offsetY, spottedX);
                        if(atInitialPosition(myPos.getRow(), myPiece) && board.getPiece(lookAhead) == null){
                            //it hasn't moved yet and the place 2 spaces ahead is also empty
                            possibleMoves.add(new ChessMove(myPos, lookAhead, null));
                        }
                        possibleMoves.add(new ChessMove(myPos, potentialPos, null));
                    }
                } else if(board.getPiece(potentialPos) != null && offset[1] != 0 && board.getPiece(myPos).getTeamColor() != board.getPiece(potentialPos).getTeamColor()){ //The occupied piece belongs to a different team
                    if(promotionEligible(potentialPos.getRow(), myPiece)){ //promo conditions
                        possibleMoves.add(new ChessMove(myPos, potentialPos, ChessPiece.PieceType.BISHOP));
                        possibleMoves.add(new ChessMove(myPos, potentialPos, ChessPiece.PieceType.ROOK));
                        possibleMoves.add(new ChessMove(myPos, potentialPos, ChessPiece.PieceType.QUEEN));
                        possibleMoves.add(new ChessMove(myPos, potentialPos, ChessPiece.PieceType.KNIGHT));
                    } else{ //promo conditions not fulfilled.
                        possibleMoves.add(new ChessMove(myPos, potentialPos, null));
                    }
                } //We are blocked by our own pieces otherwise, but since this is not a loop we don't need an extra condition to break out.
            }
        }

        return possibleMoves;
    }

    private boolean promotionEligible(int row, ChessPiece myPiece){
        return (row == 8 && myPiece.getTeamColor() == ChessGame.TeamColor.WHITE) || (row == 1 && myPiece.getTeamColor() == ChessGame.TeamColor.BLACK);
    }

    private boolean atInitialPosition(int row, ChessPiece myPiece){
        return (row == 2 && myPiece.getTeamColor() == ChessGame.TeamColor.WHITE) || (row == 7 && myPiece.getTeamColor() == ChessGame.TeamColor.BLACK);
    }
}
