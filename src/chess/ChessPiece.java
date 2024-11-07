package chess;

public abstract class ChessPiece {
    private final ChessBoard.PlayerColor color;
    protected ChessBoard board;
    private boolean isAtInitialPosition = true;

    public ChessPiece(ChessBoard.PlayerColor color) {
        this.color = color;
    }

    public boolean canMoveToPosition(int startRow, int startColumn, int endRow, int endColumn) {
        return board.isCellEmpty(endRow, endColumn) || !isSameSideFigure(board.getPiece(endRow, endColumn));
    }

    public abstract String getSymbol();

    public ChessBoard.PlayerColor getColor() {
        return color;
    }

    public boolean getIsAtInitialPosition() {
        return isAtInitialPosition;
    }

    public void moveHappened() {
        isAtInitialPosition = false;
    }

    public boolean isSameSideFigure(ChessPiece chessPiece) {
        if (chessPiece != null) {
            return chessPiece.getColor().equals(getColor());
        }
        return false;
    }

    public ChessBoard getBoard() {
        return board;
    }

    public void setBoard(ChessBoard board) {
        this.board = board;
    }
}
