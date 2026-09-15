package chess;

import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {
    private final int chessRow;
    private final int chessColumn;
    public ChessPosition(int row, int col) {
        chessRow = row;
        chessColumn = col;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return chessRow;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left column
     */
    public int getColumn() {
        return chessColumn;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPosition that = (ChessPosition) o;
        return chessRow == that.chessRow && chessColumn == that.chessColumn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(chessRow, chessColumn);
    }

    @Override
    public String toString() {
        return "ChessPosition{" +
                "chessRow=" + chessRow +
                ", chessColumn=" + chessColumn +
                '}';
    }
}
