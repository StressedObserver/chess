package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalculator implements PieceMovesCalculator, PieceAttacksCalculator{ //For pawns specifically, since we need to add promotion logic
    @Override
    public Collection<ChessMove> possibleMoves(ChessBoard board, ChessPosition myPos, int[][] offsets) {
        Collection<ChessMove> possibleMoves = new ArrayList<>();
        ChessPiece myPiece = board.getPiece(myPos);
        if(myPiece.getTeamColor() == ChessGame.TeamColor.BLACK){ //Need to flip where the pieces can go depending on team color
            for(int[] offset : offsets){
                offset[0] *= -1;
            }
        }

        for(int[] offset : offsets){
            int offsetY = offset[0];
            int offsetX = offset[1];
            int spottedY = myPos.getRow() + offsetY;
            int spottedX = myPos.getColumn() + offsetX;
            ChessPosition potentialPos = new ChessPosition(spottedY, spottedX);
            if(board.isInBounds(potentialPos)){
                if(board.getPiece(potentialPos) == null  && offset[1] == 0){//The spot is blank
                    if(promotionEligible(potentialPos.getRow(), myPiece)){ //promo conditions fulfilled.
                        addPromoMoves(possibleMoves, myPos, potentialPos);
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
                        addPromoMoves(possibleMoves, myPos, potentialPos);
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

    private void addPromoMoves(Collection<ChessMove> moves, ChessPosition to, ChessPosition from){
        moves.add(new ChessMove(to, from, ChessPiece.PieceType.KNIGHT));
        moves.add(new ChessMove(to, from, ChessPiece.PieceType.BISHOP));
        moves.add(new ChessMove(to, from, ChessPiece.PieceType.ROOK));
        moves.add(new ChessMove(to, from, ChessPiece.PieceType.QUEEN));
    }

    @Override
    public Collection<ChessMove> attackVision(ChessBoard myBoard, ChessPosition myPos, int[][] offsets) {
        Collection<ChessMove> attacks = new ArrayList<>();
        ChessPiece attacker = myBoard.getPiece(myPos);
        if(attacker.getTeamColor() == ChessGame.TeamColor.BLACK){ //Need to flip where the pieces can go depending on team color
            for(int[] offset : offsets){
                offset[0] *= -1;
            }
        }
        for(int[] offset: offsets){
            int potentialRow = myPos.getRow() + offset[0];
            int potentialColumn = myPos.getColumn() + offset[1];
            ChessPosition potentialPos = new ChessPosition(potentialRow, potentialColumn);
            if(myBoard.isInBounds(potentialPos)){
                ChessPiece viewingPiece = myBoard.getPiece(potentialPos);
                if (viewingPiece == null || offset[1] == 0 ||
                        attacker.getTeamColor() == viewingPiece.getTeamColor()){ //The square we are adding is empty or part of the same team.
                    continue;
                }
                if(promotionEligible(potentialPos.getRow(), attacker)){
                    addPromoMoves(attacks, myPos, potentialPos);
                } else{
                    attacks.add(new ChessMove(myPos, potentialPos, null));
                }
            }
        }
        return attacks;
    }
}
