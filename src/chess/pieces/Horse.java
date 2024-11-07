package chess.pieces;

import chess.ChessBoard;
import chess.ChessPiece;

public class Horse extends ChessPiece {
    public Horse(ChessBoard.PlayerColor color) {
        super(color);
    }

    @Override
    public boolean canMoveToPosition(int startRow, int startColumn, int endRow, int endColumn) {
        if (!super.canMoveToPosition(startRow, startColumn, endRow, endColumn)) {
            return false;
        }

        int rowDiff = Math.abs(startColumn - endColumn);
        int columnDiff = Math.abs(startRow - endRow);
        return rowDiff == 1 && columnDiff == 2 || rowDiff == 2 && columnDiff == 1;
    }

    @Override
    public String getSymbol() {
        return "H";
    }
}
