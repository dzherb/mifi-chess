package chess.pieces;

import chess.ChessBoard;
import chess.ChessPiece;

public class Queen extends ChessPiece {
    public Queen(ChessBoard.PlayerColor color) {
        super(color);
    }

    @Override
    public boolean canMoveToPosition(int startRow, int startColumn, int endRow, int endColumn) {
        if (!super.canMoveToPosition(startRow, startColumn, endRow, endColumn)) {
            return false;
        }

        boolean canMoveHorizontally = startRow == endRow && board.isRowSectionEmpty(startRow, startColumn, endColumn);
        boolean canMoveVertically = startColumn == endColumn && board.isColumnSectionEmpty(startColumn, startRow, endRow);
        boolean canMoveDiagonally = board.isDiagonalEmpty(startRow, startColumn, endRow, endColumn);

        return canMoveHorizontally || canMoveVertically || canMoveDiagonally;
    }

    @Override
    public String getSymbol() {
        return "Q";
    }
}
