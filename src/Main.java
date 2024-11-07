import chess.ChessBoard;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ChessBoard board = ChessBoard.buildBoard();
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                Чтобы проверить игру надо вводить команды:
                'exit' - для выхода
                'replay' - для перезапуска игры
                'castling0' или 'castling7' - для рокировки по соответствующей линии
                'move 1 1 2 3' - для передвижения фигуры с позиции 1 1 на 2 3(поле это двумерный массив от 0 до 7)
                Проверьте могут ли фигуры ходить друг сквозь друга, корректно ли съедают друг друга, можно ли поставить шах и сделать рокировку?
                """);

        board.printBoard();

        while (true) {
            System.out.print(">>> ");
            String command = scanner.nextLine();
            String firstWord = command.split(" ")[0];
            switch (firstWord) {
                case "exit":
                    System.exit(0);
                case "replay":
                    System.out.println("Заново");
                    board = ChessBoard.buildBoard();
                    break;
                case "castling0":
                    if (board.castling0()) {
                        System.out.println("Рокировка удалась");
                    } else {
                        System.out.println("Рокировка не удалась");
                    }
                    break;
                case "castling7":
                    if (board.castling7()) {
                        System.out.println("Рокировка удалась");
                    } else {
                        System.out.println("Рокировка не удалась");
                    }
                    break;
                case "move":
                    String[] a = command.split(" ");
                    try {
                        int row = Integer.parseInt(a[1]);
                        int column = Integer.parseInt(a[2]);
                        int toRow = Integer.parseInt(a[3]);
                        int toColumn = Integer.parseInt(a[4]);
                        if (board.moveToPosition(row, column, toRow, toColumn)) {
                            System.out.println("Успешно передвинулись");
                            break;
                        } else {
                            System.out.println("Передвижение не удалось");
                            continue;
                        }
                    }
                    catch (IndexOutOfBoundsException e) {
                        System.out.println("Неправильное количество аргументов, попробуйте еще раз");
                        continue;
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Вы что-то ввели не так, попробуйте ещё раз");
                        continue;
                    }
                default:
                    System.out.println("Неизвестная команда");
                    continue;
            }
            board.printBoard();
        }
    }
}