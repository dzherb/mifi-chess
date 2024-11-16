package chess.pieces;

import chess.ChessBoard;
import chess.ChessPiece;

public class Pawn extends ChessPiece {
    public Pawn(ChessBoard.PlayerColor color) {
        super(color);
    }

    @Override
    public boolean canMoveToPosition(int startRow, int startColumn, int endRow, int endColumn) {
        if (!super.canMoveToPosition(startRow, startColumn, endRow, endColumn)) {
            return false;
        }

        if (endRow > startRow && getColor().equals(ChessBoard.PlayerColor.BLACK) || endRow < startRow && getColor().equals(ChessBoard.PlayerColor.WHITE)) {
            // don't allow to move backwards
            return false;
        }

        int rowDiff = Math.abs(startRow - endRow);
        int colDiff = Math.abs(startColumn - endColumn);

        if (colDiff != 0) {
            // can attack diagonally
            return colDiff == 1 && rowDiff == 1 && !board.isCellEmpty(endRow, endColumn);
        }

        int maxStep = isAtInitialPosition() ? 2 : 1;
        return rowDiff >= 1 && rowDiff <= maxStep;
    }

    @Override
    public String getSymbol() {
        return "P";
    }
}
