package com.bambz.chessengine;


/**
 * Created by bambo on 11.06.2017.
 */
public class Game {


    enum FieldType {WHITE, BLACK, WHITE_QUEEN, BLACK_QUEEN, EMPTY}
    enum Player {WHITE, BLACK}

    private FieldType[][] board; // [col][row]

    private Player currentPlayer;

    private int whiteChequers;
    private int blackChequers;

    private Field lastTakenField;

    public static Game createTestingGame(FieldType[][] board, Player currentPlayer) {
        return new Game(board, currentPlayer);
    }

    private Game(FieldType[][] board, Player currentPlayer) {
        this.board = board;
        this.currentPlayer = currentPlayer;
    }

    public Game() {
        currentPlayer = Player.BLACK.WHITE;
        board = new FieldType[8][8];
        whiteChequers = blackChequers = 12;

        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                if (row == 0 || row == 2) {
                    if (col % 2 == 0) {
                        board[col][row] = FieldType.WHITE;
                    } else {
                        board[col][row] = FieldType.EMPTY;
                    }
                } else if (row == 1) {
                    if (col % 2 == 0) {
                        board[col][row] = FieldType.EMPTY;
                    } else {
                        board[col][row] = FieldType.WHITE;
                    }
                } else if (row == 5 || row == 7) {
                    if (col % 2 == 0) {
                        board[col][row] = FieldType.EMPTY;
                    } else {
                        board[col][row] = FieldType.BLACK;
                    }
                } else if (row == 6) {
                    if (col % 2 == 0) {
                        board[col][row] = FieldType.BLACK;
                    } else {
                        board[col][row] = FieldType.EMPTY;
                    }
                } else {
                    board[col][row] = FieldType.EMPTY;
                }
            }
        }

    }

    public boolean move(String command) {
        Move move = Move.createFromCommand(command);
        if(move.isWrongMove(board, currentPlayer, lastTakenField)) return false;

        board[move.getFinishCol()][move.getFinishRow()] = board[move.getStartCol()][move.getStartRow()];
        board[move.getStartCol()][move.getStartRow()] = FieldType.EMPTY;

        lastTakenField = new Field(move.getFinishCol(), move.getFinishRow());

        checkQueen(move);

        if(move.isTakenChequer()) {
            if(currentPlayer == Player.WHITE) {
                blackChequers--;
            } else {
                whiteChequers--;
            }
            int takenChequerColumn = Math.abs(move.getFinishCol() + move.getStartCol()) / 2;
            int takenChequerRow = Math.abs(move.getFinishRow() + move.getStartRow()) / 2;
            board[takenChequerColumn][takenChequerRow] = FieldType.EMPTY;

            if(move.isMoreChequerToTaken(board, currentPlayer)) return true;
        }

        currentPlayer = currentPlayer == Player.WHITE ? Player.BLACK : Player.WHITE;
        return true;
    }

    public void drawBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[col][row] == FieldType.EMPTY) {
                    System.out.print("_");
                } else if (board[col][row] == FieldType.WHITE) {
                    System.out.print("W");
                } else {
                    System.out.print("B");
                }
            }
            System.out.println();
        }
    }

    public FieldType[][] getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }


    public int getWhiteChequers() {
        return whiteChequers;
    }

    public int getBlackChequers() {
        return blackChequers;
    }

    private void checkQueen(Move move) {
        if(currentPlayer == Player.BLACK && move.getFinishRow() == 0 ) {
            board[move.getFinishCol()][move.getFinishRow()] = FieldType.BLACK_QUEEN;
        } else if(currentPlayer == Player.WHITE && move.getFinishRow() == 7 ) {
            board[move.getFinishCol()][move.getFinishRow()] = FieldType.WHITE_QUEEN;
        }
    }

    public static void main(String[] args) {

        Game game = new Game();


    }


}
