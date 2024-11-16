package chess.pieces;

import chess.ChessBoard;
import chess.ChessPiece;

public class Rook extends ChessPiece {
    public Rook(ChessBoard.PlayerColor color) {
        super(color);
    }

    @Override
    public boolean canMoveToPosition(int startRow, int startColumn, int endRow, int endColumn) {
        if (!super.canMoveToPosition(startRow, startColumn, endRow, endColumn)) {
            return false;
        }
        boolean canMoveHorizontally = startRow == endRow && board.isRowSectionEmpty(startRow, startColumn, endColumn);
        boolean canMoveVertically = startColumn == endColumn && board.isColumnSectionEmpty(startColumn, startRow, endRow);
        return canMoveHorizontally || canMoveVertically;
    }

    @Override
    public String getSymbol() {
        return "R";
    }
}
