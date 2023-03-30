package tictactoe;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final int SIZE = 3; //size of rows/columns
    private static final char[][] gameField = new char[SIZE][SIZE];

    public static void main(String[] args) {
        generateGame();
        printGame();
        int countMove = 0;
        boolean isOver = false;
        while (!isOver) {
            if (countMove % 2 == 0) {
                play('X');
            } else play('O');
            countMove++;
            printGame();
            if (analyzeGame().equals("X wins")) {
                System.out.println(analyzeGame());
                isOver = true;
            } else if (analyzeGame().equals("O wins")) {
                System.out.println(analyzeGame());
                isOver = true;
            } else if (analyzeGame().equals("Draw")) {
                System.out.println(analyzeGame());
                isOver = true;
            }
        }
    }

    public static void generateGame() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                gameField[i][j] = '_';
            }
        }
    }

    public static void play(char c) {
        try {
            String x = scanner.next();
            String y = scanner.next();
            int xCoordinate = Integer.parseInt(x);
            int yCoordinate = Integer.parseInt(y);
            if (xCoordinate > 3 || yCoordinate > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
                play(c);
                return;
            }
            if (gameField[xCoordinate - 1][yCoordinate - 1] != '_') {
                System.out.println("This cell is occupied! Choose another one!");
                play(c);
                return;
            }
            gameField[xCoordinate - 1][yCoordinate - 1] = c;


        } catch (NumberFormatException e) {
            System.out.println("You should enter numbers!");
            play(c);
        }
    }

    public static void printGame() {
        StringBuilder sb = new StringBuilder();
        sb.append("---------\n");

        for (int i = 0; i < SIZE; i++) {
            sb.append("| ");
            for (int j = 0; j < SIZE; j++) {
                sb.append(gameField[i][j]).append(" ");
            }
            sb.append("|\n");
        }
        sb.append("---------");
        System.out.println(sb);
    }

    public static String analyzeGame() {
        String result = "error";
//        int numberOfXes = countSymbol('X');
//        int numberOfOs = countSymbol('O');
        int numberOfEmptyCells = countSymbol('_');

//        if (Math.abs(numberOfXes - numberOfOs) > 1) {
//            result = "Impossible";
//        } else if (countLinesOfType('X') > 0 && countLinesOfType('O') > 0) {
//            result = "Impossible";
//        } else if (countLinesOfType('X') == 0 && countLinesOfType('O') == 0 && numberOfEmptyCells > 0) {
//            result = "Game not finished";
        if (countLinesOfType('X') == 0 && countLinesOfType('O') == 0 && numberOfEmptyCells == 0) {
            result = "Draw";
        } else if (countLinesOfType('X') == 1 && countLinesOfType('O') == 0) {
            result = "X wins";

        } else if (countLinesOfType('O') == 1 && countLinesOfType('X') == 0) {
            result = "O wins";

        }

        return result;
    }

    public static boolean hasLineOfType(char[] line, char type) {
        for (char c : line) {
            if (c != type) {
                return false;
            }
        }
        return true;
    }

    public static int countLinesOfType(char type) {
        int count = 0;
        for (char[] line : gameField) {
            if (hasLineOfType(line, type)) {
                count++;
            }
        }
        for (char[] line : transposedGameGrid(gameField)) {
            if (hasLineOfType(line, type)) {
                count++;
            }
        }
        count += countDiagonalOfType(0, type);
        count += countDiagonalOfType(1, type);
        return count;
    }

    public static boolean hasDiagonalOfType(int diagonalType, char type) {
        //0 for primary, 1 for secondary diagonal
        if (diagonalType == 0) {
            return gameField[0][0] == type && gameField[1][1] == type && gameField[2][2] == type;
        } else if (diagonalType == 1) {
            return gameField[2][0] == type && gameField[1][1] == type && gameField[0][2] == type;
        }
        return false;
    }

    public static int countDiagonalOfType(int diagonalType, char type) {
        int count = 0;
        if (hasDiagonalOfType(diagonalType, type)) {
            count++;
        }

        return count;
    }

    public static int countSymbol(char symbol) {
        int ans = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (gameField[i][j] == symbol) {
                    ans++;
                }
            }
        }
        return ans;
    }

    public static char[][] transposedGameGrid(char[][] gameField) {

        char[][] transpose = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                transpose[j][i] = gameField[i][j];
            }
        }
        return transpose;
    }
}
