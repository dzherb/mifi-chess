package chess.pieces;

import chess.ChessBoard;
import chess.ChessPiece;

public class Bishop extends ChessPiece {
    public Bishop(ChessBoard.PlayerColor color) {
        super(color);
    }

    @Override
    public boolean canMoveToPosition(int startRow, int startColumn, int endRow, int endColumn) {
        if (!super.canMoveToPosition(startRow, startColumn, endRow, endColumn)) {
            return false;
        }

        return board.isDiagonalEmpty(startRow, startColumn, endRow, endColumn);
    }

    @Override
    public String getSymbol() {
        return "B";
    }
}
