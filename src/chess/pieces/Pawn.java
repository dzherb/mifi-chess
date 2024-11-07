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

        if (startColumn != endColumn) {
            return false;
        }

        final int minStep = 1;
        final int maxStep = getIsAtInitialPosition() ? 2 : 1;

        int diff = startRow - endRow;
        if (getColor().equals(ChessBoard.PlayerColor.WHITE)) {
            diff = -diff;
        }
        // todo атака по-диагонали еще
        // todo проверять фигуры на пути, если есть (но не на финальной точке по-диагонали), то false
        return diff >= minStep && diff <= maxStep && board.isCellEmpty(endRow, endColumn);
    }

    @Override
    public String getSymbol() {
        return "P";
    }
}
