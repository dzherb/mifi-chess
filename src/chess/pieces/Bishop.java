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

        int rowDiff = Math.abs(endRow - startRow);
        int columnDiff = Math.abs(endColumn - startColumn);
        return rowDiff == columnDiff && rowDiff != 0;
        // todo проверять фигуры на пути, если есть (но не на финальной точке), то false
    }

    @Override
    public String getSymbol() {
        return "B";
    }
}
