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
        if((myPos.getRow() == 2 && myPiece.getTeamColor() == ChessGame.TeamColor.WHITE) || (myPos.getRow() == 7 && myPiece.getTeamColor() == ChessGame.TeamColor.BLACK)){
            //The pawn hasn't moved yet, so we add an extra offset to check for double movement.
            offsets[3] = new int[]{2, 0}; //Hopefully this adds things correctly.
        }

        for(int offset[] : offsets){
            int offsetY = offset[0];
            int offsetX = offset[1];
            int spottedY = myPos.getRow() + offsetY;
            int spottedX = myPos.getColumn() + offsetX;
            ChessPosition potentialPos = new ChessPosition(spottedY, spottedX);
            if(board.isInBounds(potentialPos)){
                if(board.getPiece(potentialPos) == null){//The spot is blank
                    if(promotionEligible(myPos.getRow(), myPiece)){ //promo conditions fulfilled.
                        possibleMoves.add(new ChessMove(myPos, potentialPos, ChessPiece.PieceType.BISHOP));
                        possibleMoves.add(new ChessMove(myPos, potentialPos, ChessPiece.PieceType.ROOK));
                        possibleMoves.add(new ChessMove(myPos, potentialPos, ChessPiece.PieceType.QUEEN));
                        possibleMoves.add(new ChessMove(myPos, potentialPos, ChessPiece.PieceType.KNIGHT));
                    } else{ //promo conditions not fulfilled
                        possibleMoves.add(new ChessMove(myPos, potentialPos, null));
                    }
                } else if(board.getPiece(myPos).getTeamColor() != board.getPiece(potentialPos).getTeamColor()){ //The occupied piece belongs to a different team
                    if(promotionEligible(myPos.getRow(), myPiece)){ //promo conditions
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
}
