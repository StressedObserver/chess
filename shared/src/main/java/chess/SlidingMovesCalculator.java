package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SlidingMovesCalculator implements PieceMovesCalculator{

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPos, int[][] offsets) {
        Collection<ChessMove> possibleMoves = new ArrayList<>();
        for(int offset[] : offsets){
            int offsetY = offset[0];
            int offsetX = offset[1];
            int spottedY = myPos.getRow() + offsetY;
            int spottedX = myPos.getColumn() + offsetX;
            ChessPosition potentialPos = new ChessPosition(spottedY, spottedX);
            while(board.isInBounds(potentialPos)){
                if(board.getPiece(potentialPos) == null){ //The spot is blank
                    possibleMoves.add(new ChessMove(myPos, potentialPos, null));
                } else if(board.getPiece(myPos).getTeamColor() != board.getPiece(potentialPos).getTeamColor()){ //The occupied piece belongs to a different team
                    possibleMoves.add(new ChessMove(myPos, potentialPos, null));
                    break; //This piece is blocking the vision, so we don't continue along this line.
                } else{ //We are blocked by our own pieces.
                    break; //Don't want to keep searching when our vision is blocked.
                }
                spottedY = spottedY + offsetY; //If we haven't broken yet our vision is still unobstructed
                spottedX = spottedX + offsetX; //So we continue searching in this direction
                potentialPos = new ChessPosition(spottedY, spottedX); //Update potentialPos so that the loop isn't infinite.
            }
        }

        return possibleMoves;
    }
}
