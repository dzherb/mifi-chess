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

        if (startRow == endRow && startColumn == endColumn) {
            return false;
        }

        else return startRow == endRow || startColumn == endColumn;
        // todo проверять фигуры на пути, если есть (но не на финальной точке), то false

    }

    @Override
    public String getSymbol() {
        return "R";
    }
}
