package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HoppingMovesCalculator implements PieceMovesCalculator{ //For Kings and Knights.
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
}
