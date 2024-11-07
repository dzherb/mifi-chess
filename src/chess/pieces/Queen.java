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

        return false;
        // todo проверять фигуры на пути, если есть (но не на финальной точке), то false
    }

    @Override
    public String getSymbol() {
        return "Q";
    }
}
