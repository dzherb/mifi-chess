package chess;

import chess.pieces.*;

public class ChessBoard {
    private final ChessPiece[][] board = new ChessPiece[8][8];
    PlayerColor currentPlayerColor;

    public enum PlayerColor {
        WHITE, BLACK;

        public String getSymbol() {
            return switch (this) {
                case WHITE -> "w";
                case BLACK -> "b";
            };
        }
    }

    public ChessBoard(PlayerColor currentPlayerColor) {
        this.currentPlayerColor = currentPlayerColor;
    }

    public PlayerColor getCurrentPlayerColor() {
        return this.currentPlayerColor;
    }

    public boolean moveToPosition(int startRow, int startColumn, int endRow, int endColumn) {
        if (!(isPositionWithin(startRow, startColumn) && isPositionWithin(endRow, endColumn))) {
            return false;
        }
        if (isCellEmpty(startRow, startColumn)) {
            return false;
        }

        ChessPiece chessPiece = getPiece(startRow, startColumn);

        if (!currentPlayerColor.equals(chessPiece.getColor())) {
            return false;
        }
        if (!chessPiece.canMoveToPosition(startRow, startColumn, endRow, endColumn)) {
            return false;
        }

        movePiece(startRow, startColumn, endRow, endColumn);
        togglePlayerColor();
        return true;
    }

    private void togglePlayerColor() {
        this.currentPlayerColor = this.getCurrentPlayerColor().equals(PlayerColor.WHITE) ? PlayerColor.BLACK : PlayerColor.WHITE;
    }

    private void printPlayer(int number, PlayerColor color) {
        System.out.println("Player " + number + " (" + color + ")\n");
    }

    public void printBoard() {  //print board in console
        System.out.println();
        printPlayer(2, PlayerColor.BLACK);
        System.out.println("\t0\t1\t2\t3\t4\t5\t6\t7");
        for (int i = 7; i > -1; i--) {
            System.out.print(i + "\t");
            for (int j = 0; j < 8; j++) {
                if (isCellEmpty(i, j)) {
                    System.out.print(".." + "\t");
                    continue;
                }
                ChessPiece chessPiece = getPiece(i, j);
                System.out.print(chessPiece.getSymbol() + chessPiece.getColor().getSymbol() + "\t");
            }
            System.out.println("\n");
        }
        printPlayer(1, PlayerColor.WHITE);
    }

    private boolean isPositionWithin(int row, int column) {
        return row >= 0 && row <= 7 && column >= 0 && column <= 7;
    }

    private void placePiece(int row, int column, ChessPiece chessPiece) {
        board[row][column] = chessPiece;
        if (chessPiece.getBoard() == null) {
            chessPiece.setBoard(this);
        }
    }

    private void removePiece(int row, int column) {
        board[row][column] = null;
    }

    private void movePiece(int startRow, int startColumn, int endRow, int endColumn) {
        ChessPiece chessPiece = getPiece(startRow, startColumn);
        placePiece(endRow, endColumn, chessPiece);
        removePiece(startRow, startColumn);
        chessPiece.moveHappened();
    }

    public ChessPiece getPiece(int row, int column) {
        if (isPositionWithin(row, column)) {
            return board[row][column];
        }
        return null;
    }

    public boolean isCellEmpty(int row, int column) {
        return getPiece(row, column) == null;
    }

    public int height() {
        return board.length;
    }

    public int width() {
        return board[0].length;
    }

    public boolean isRowSectionEmpty(int row, int startColumn, int endColumn) {
        if (endColumn < startColumn) {
            int temp = endColumn;
            endColumn = startColumn;
            startColumn = temp;
        }
        for (int i = startColumn + 1; i < endColumn; i++) {
            if (!isCellEmpty(row, i)) {
                return false;
            }
        }
        return true;
    }

    public boolean isColumnSectionEmpty(int column, int startRow, int endRow) {
        if (endRow < startRow) {
            int temp = endRow;
            endRow = startRow;
            startRow = temp;
        }
        for (int i = startRow + 1; i < endRow; i++) {
            if (!isCellEmpty(i, column)) {
                return false;
            }
        }
        return true;
    }

    public boolean isDiagonalEmpty(int startRow, int startColumn, int endRow, int endColumn) {
        int rowDiff = Math.abs(endRow - startRow);
        int columnDiff = Math.abs(endColumn - startColumn);
        if (rowDiff != columnDiff) {
            // invalid diagonal
            return false;
        }
        if (endRow < startRow) {
            startRow = endRow;
            startColumn = endColumn;
        }
        for (int i = 1; i < rowDiff; i++) {
            if (!isCellEmpty(startRow + i, startColumn + i)) {
                return false;
            }
        }
        return true;
    }

    private boolean castling(int rookColumn) {
        final int row = getCurrentPlayerColor().equals(PlayerColor.WHITE) ? 0 : 7;
        final int kingColumn = 4;

        Rook rook;
        King king;
        try {
            rook = (Rook) getPiece(row, rookColumn);
            king = (King) getPiece(row, kingColumn);
        } catch (ClassCastException e) {
            return false;
        }

        if (rook == null || king == null) {
            return false;
        }
        if (!(rook.isAtInitialPosition() && king.isAtInitialPosition())) {
            return false;
        }

        final int kingFinalColumn = rookColumn > kingColumn ? 6 : 2;
        final int rookFinalColumn = rookColumn > kingColumn ? 5 : 3;

        if (king.isUnderAttack(row, kingFinalColumn)) {
            return false;
        }

        if (!isRowSectionEmpty(row, rookColumn, kingColumn)) {
            return false;
        }

        movePiece(row, rookColumn, row, rookFinalColumn);
        movePiece(row, kingColumn, row, kingFinalColumn);
        togglePlayerColor();
        return true;
    }

    public boolean castling0() {
        return castling(0);
    }

    public boolean castling7() {
        return castling(7);
    }

    public static ChessBoard buildBoard() {
        ChessBoard board = new ChessBoard(PlayerColor.WHITE);

        for (PlayerColor player: PlayerColor.values()) {
            int row = player.equals(PlayerColor.WHITE) ? 0 : 7;
            board.placePiece(row, 0, new Rook(player));
            board.placePiece(row, 1, new Horse(player));
            board.placePiece(row, 2, new Bishop(player));
            board.placePiece(row, 3, new Queen(player));
            board.placePiece(row, 4, new King(player));
            board.placePiece(row, 5, new Bishop(player));
            board.placePiece(row, 6, new Horse(player));
            board.placePiece(row, 7, new Rook(player));
            row = player.equals(PlayerColor.WHITE) ? 1 : 6;
            for (int i = 0; i < board.width(); i++) {
                board.placePiece(row, i, new Pawn(player));
            }
        }

        return board;
    }
}