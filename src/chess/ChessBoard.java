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
        if (!movePiece(startRow, startColumn, endRow, endColumn)) {
            return false;
        }
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
        System.out.println("\nTurn " + currentPlayerColor + "\n");
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

    private boolean movePiece(int startRow, int startColumn, int endRow, int endColumn) {
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

        placePiece(endRow, endColumn, chessPiece);
        removePiece(startRow, startColumn);

        chessPiece.moveHappened();
        return true;
    }

    private void removePiece(int row, int column) {
        board[row][column] = null;
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

    public boolean castling0() {
        int row = getCurrentPlayerColor().equals(PlayerColor.WHITE) ? 0 : 7;

        Pawn pawn;
        King king;
        try {
            pawn = (Pawn) getPiece(row, 0);
            king = (King) getPiece(row, 4);
        } catch (ClassCastException e) {
            return false;
        }
        if (pawn == null || king == null) {
            return false;
        }
        if (!(pawn.isAtInitialPosition() && king.isAtInitialPosition())) {
            return false;
        }

        if (king.isUnderAttack(row, 2)) {
            return false;
        }

        for (int i = 1; i < 4; i++) {
            if (!isCellEmpty(0, i)) {
                return false;
            }
        }
        movePiece(row, 0, row, 3);
        movePiece(row, 4, row, 2);
        return true;
    }

    public boolean castling7() {
        return false;
    }

    public static ChessBoard buildBoard() {
        ChessBoard board = new ChessBoard(PlayerColor.WHITE);

        board.placePiece(0, 0, new Rook(PlayerColor.WHITE));
        board.placePiece(0, 1, new Horse(PlayerColor.WHITE));
        board.placePiece(0, 2, new Bishop(PlayerColor.WHITE));
        board.placePiece(0, 3, new Queen(PlayerColor.WHITE));
        board.placePiece(0, 4, new King(PlayerColor.WHITE));
        board.placePiece(0, 5, new Bishop(PlayerColor.WHITE));
        board.placePiece(0, 6, new Horse(PlayerColor.WHITE));
        board.placePiece(0, 7, new Rook(PlayerColor.WHITE));
        for (int i = 0; i < board.width(); i++) {
            board.placePiece(1, i, new Rook(PlayerColor.WHITE));
        }

        board.placePiece(7, 0, new Rook(PlayerColor.BLACK));
        board.placePiece(7, 1, new Horse(PlayerColor.BLACK));
        board.placePiece(7, 2, new Bishop(PlayerColor.BLACK));
        board.placePiece(7, 3, new Queen(PlayerColor.BLACK));
        board.placePiece(7, 4, new King(PlayerColor.BLACK));
        board.placePiece(7, 5, new Bishop(PlayerColor.BLACK));
        board.placePiece(7, 6, new Horse(PlayerColor.BLACK));
        board.placePiece(7, 7, new Rook(PlayerColor.BLACK));
        for (int i = 0; i < board.width(); i++) {
            board.placePiece(6, i, new Rook(PlayerColor.BLACK));
        }

        return board;
    }
}