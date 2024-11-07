package chess.pieces;

import chess.ChessBoard;
import chess.ChessPiece;

public class King extends ChessPiece {
    public King(ChessBoard.PlayerColor color) {
        super(color);
    }

    @Override
    public boolean canMoveToPosition(int startRow, int startColumn, int endRow, int endColumn) {
        if (!super.canMoveToPosition(startRow, startColumn, endRow, endColumn)) {
            return false;
        }
        int rowDiff = Math.abs(endRow - startRow);
        int colDiff = Math.abs(endColumn - startColumn);
        if (rowDiff == 0 && colDiff == 0) {
            return false;
        }
        return rowDiff == 1 || colDiff == 1;
    }

    @Override
    public String getSymbol() {
        return "K";
    }

    public boolean isUnderAttack(int row, int column) {
        for (int i = 0; i < board.height(); i++) {
            for (int j = 0; j < board.width(); j++) {
                if (board.isCellEmpty(i, j)) continue;
                ChessPiece chessPiece = board.getPiece(i, j);
                if (!isSameSideFigure(chessPiece) && chessPiece.canMoveToPosition(i, j, row, column)) {
                    return true;
                }
            }
        }
        return false;
    }
}
