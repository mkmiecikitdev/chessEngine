package com.bambz.chessengine;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by bambo on 11.06.2017.
 */
public class Move {

    private static class Offset {
        private int offsetCol, offsetRow;

        public Offset(int offsetCol, int offsetRow) {
            this.offsetCol = offsetCol;
            this.offsetRow = offsetRow;
        }
    }

    private static Offset[] offsets = {
            new Offset(-1, -1),
            new Offset(1, -1),
            new Offset(-1, 1),
            new Offset(1, 1),
    };

    private int startCol;
    private int startRow;
    private int finishCol;
    private int finishRow;

    private boolean takenChequer = false;

    private Move() {
    }

    public static Move createFromCommand(String command) {
        Move move = new Move();
        String[] moves = command.split(":");
        move.startCol = (int) (moves[0].charAt(0)) - 97;
        move.startRow = Character.getNumericValue(moves[0].charAt(1)) - 1;
        move.finishCol = (int) (moves[1].charAt(0)) - 97;
        move.finishRow = Character.getNumericValue(moves[1].charAt(1)) - 1;
        return move;
    }

    public int getStartCol() {
        return startCol;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getFinishCol() {
        return finishCol;
    }

    public int getFinishRow() {
        return finishRow;
    }

    public boolean isTakenChequer() {
        return takenChequer;
    }

    public boolean isWrongMove(Game.FieldType[][] board, Game.Player currentPlayer, Field lastTakenField) {

        if (isOccupiedField(board) || isIncorrectField() || isNotOwnChequer(board, currentPlayer)) return true;
        else {
            if (isMoveByQueen(board)) {
                int size = getJumpingChequersList(board).size();
                if (size > 1) return true;
                else if (size == 1) {
                    Game.FieldType currentChequer;
                    Game.FieldType currentQueen;

                    if (currentPlayer == Game.Player.WHITE) {
                        currentChequer = Game.FieldType.WHITE;
                        currentQueen = Game.FieldType.WHITE_QUEEN;
                    } else {
                        currentChequer = Game.FieldType.BLACK;
                        currentQueen = Game.FieldType.BLACK_QUEEN;
                    }

                    if (getJumpingChequersList(board).get(0) == currentChequer || getJumpingChequersList(board).get(0) == currentQueen)
                        return true;
                    else {
                        takenChequer = true;
                        return false;
                    }

                } else {
                    return canTakeChequer(board, currentPlayer, lastTakenField);

                }

            } else {
                if (isTakenChequer(board, currentPlayer)) {
                    takenChequer = true;
                    return false;
                }

                return canTakeChequer(board, currentPlayer, lastTakenField) ||
                        isMoveNotForwardWithoutTakenChequer(currentPlayer) ||
                        isTwoOrMoreFieldsMoveWithoutTakenChequer();

            }
        }

    }


    public boolean isMoreChequerToTaken(Game.FieldType[][] board, Game.Player currentPlayer) {
        Game.FieldType checkingChequer = currentPlayer == Game.Player.BLACK ? Game.FieldType.WHITE : Game.FieldType.BLACK;

        for (int i = 0; i < 4; ++i) {
            int checkingCol = finishCol + offsets[i].offsetCol;
            int checkingRow = finishRow + offsets[i].offsetRow;

            if (isOutsideBoard(checkingCol, checkingRow)) continue;

            if (board[checkingCol][checkingRow] == checkingChequer) {
                checkingCol += offsets[i].offsetCol;
                checkingRow += offsets[i].offsetRow;

                if (isOutsideBoard(checkingCol, checkingRow)) continue;

                if (board[checkingCol][checkingRow] == Game.FieldType.EMPTY) return true;
            }

        }

        return false;

    }

    private boolean isMoveByQueen(Game.FieldType[][] board) {
        return board[startCol][startRow] == Game.FieldType.WHITE_QUEEN || board[startCol][startRow] == Game.FieldType.BLACK_QUEEN;
    }

    private boolean isNotOwnChequer(Game.FieldType[][] board, Game.Player currentPlayer) {
        if (currentPlayer == Game.Player.WHITE) {
            return board[startCol][startRow] != Game.FieldType.WHITE && board[startCol][startRow] != Game.FieldType.WHITE_QUEEN;
        } else {
            return board[startCol][startRow] != Game.FieldType.BLACK && board[startCol][startRow] != Game.FieldType.BLACK_QUEEN;
        }
    }

    private boolean canTakeChequer(Game.FieldType[][] board, Game.Player currentPlayer, Field field) {

        if (field == null) return false;

        Game.FieldType checkingChequer = currentPlayer == Game.Player.BLACK ? Game.FieldType.BLACK : Game.FieldType.WHITE;

        for (int i = 0; i < 4; ++i) {
            int checkingCol = field.getCol() + offsets[i].offsetCol;
            int checkingRow = field.getRow() + offsets[i].offsetRow;

            if (isOutsideBoard(checkingCol, checkingRow)) continue;

            if (board[checkingCol][checkingRow] == checkingChequer) {
                checkingCol -= 2 * offsets[i].offsetCol;
                checkingRow -= 2 * offsets[i].offsetRow;

                if (isOutsideBoard(checkingCol, checkingRow)) continue;

                if (board[checkingCol][checkingRow] == Game.FieldType.EMPTY) return true;
            }

        }

        return false;
    }

    private boolean isOccupiedField(Game.FieldType[][] board) {
        return board[finishCol][finishRow] != Game.FieldType.EMPTY;
    }

    private boolean isIncorrectField() {
        return Math.abs(finishCol - startCol) != Math.abs(finishRow - startRow);
    }

    private boolean isMoveNotForwardWithoutTakenChequer(Game.Player currentPlayer) {
        return currentPlayer == Game.Player.WHITE ? finishRow <= startRow : finishRow >= startRow;
    }

    private boolean isTwoOrMoreFieldsMoveWithoutTakenChequer() {
        return Math.abs(finishRow - startRow) > 1 || Math.abs(finishCol - startCol) > 1;
    }


    private boolean isTakenChequer(Game.FieldType[][] board, Game.Player currentPlayer) {
        if (Math.abs(finishCol - startCol) == 2) {
            int checkingCol = Math.abs(finishCol + startCol) / 2;
            int checkingRow = Math.abs(finishRow + startRow) / 2;
            return board[checkingCol][checkingRow] == (currentPlayer == Game.Player.WHITE ? Game.FieldType.BLACK : Game.FieldType.WHITE);
        }
        return false;
    }

    private List<Game.FieldType> getJumpingChequersList(Game.FieldType[][] board) {

        List<Game.FieldType> chequersList = new ArrayList<Game.FieldType>();

        int distance = Math.abs(finishCol - startCol);
        int offsetCol = (finishCol - startCol) / distance;
        int offsetRow = (finishRow - startRow) / distance;

        int checkingCol = startCol + offsetCol;
        int checkingRow = startRow + offsetRow;


        while (checkingCol != finishCol) {
            if (board[checkingCol][checkingRow] != Game.FieldType.EMPTY) {
                chequersList.add(board[checkingCol][checkingRow]);
            }
            checkingCol += offsetCol;
            checkingRow += offsetRow;
        }

        return chequersList;
    }


    private boolean isOutsideBoard(int col, int row) {
        return col > 7 || col < 0 || row > 7 || row < 0;
    }

}
