package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HoppingMovesCalculator implements PieceMovesCalculator, PieceAttacksCalculator{ //For Kings and Knights.
    @Override
    public Collection<ChessMove> possibleMoves(ChessBoard board, ChessPosition myPos, int[][] offsets) {
        Collection<ChessMove> possibleMoves = new ArrayList<>();
        for(int offset[] : offsets){
            int offsetY = offset[0];
            int offsetX = offset[1];
            int spottedY = myPos.getRow() + offsetY;
            int spottedX = myPos.getColumn() + offsetX;
            ChessPosition potentialPos = new ChessPosition(spottedY, spottedX);
            if(board.isInBounds(potentialPos)){
                if(board.getPiece(potentialPos) == null){ //The spot is blank
                    possibleMoves.add(new ChessMove(myPos, potentialPos, null));
                } else if(board.getPiece(myPos).getTeamColor() != board.getPiece(potentialPos).getTeamColor()){ //The occupied piece belongs to a different team
                    possibleMoves.add(new ChessMove(myPos, potentialPos, null));
                } //We are blocked by our own pieces otherwise, but since this is not a loop we don't need an extra condition to break out.
            }
        }

        return possibleMoves;
    }

    @Override
    public Collection<ChessMove> attackVision(ChessBoard myBoard, ChessPosition myPos, int[][] offsets) {
        Collection<ChessMove> attacks = new ArrayList<>();
        ChessPiece attacker = myBoard.getPiece(myPos);
        for(int[] offset: offsets){
            int potentialRow = myPos.getRow() + offset[0];
            int potentialColumn = myPos.getColumn() + offset[1];
            ChessPosition potentialPos = new ChessPosition(potentialRow, potentialColumn);
            if(myBoard.isInBounds(potentialPos)){
                ChessPiece viewingPiece = myBoard.getPiece(potentialPos);
                if (viewingPiece == null){ //The square we are adding is empty.
                    attacks.add(new ChessMove(myPos, potentialPos, null));
                } else if(viewingPiece.getTeamColor() != attacker.getTeamColor()){
                    attacks.add(new ChessMove(myPos, potentialPos, null));
                }
            }
        }
        return attacks;
    }
}
