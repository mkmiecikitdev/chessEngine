package com.bambz.chessengine;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by bambo on 11.06.2017.
 */
public class GameTest {

    private Game game;
    private Game.FieldType board[][];    //[col][row]


    @Before
    public void setup() {
        game = new Game();
        board = game.getBoard();
    }

    @Test
    public void startGame_allChequersInStartPosition() {
        assertTrue(board[0][0] == Game.FieldType.WHITE);
        assertTrue(board[1][0] == Game.FieldType.EMPTY);
        assertTrue(board[2][0] == Game.FieldType.WHITE);
        assertTrue(board[3][0] == Game.FieldType.EMPTY);
        assertTrue(board[4][0] == Game.FieldType.WHITE);
        assertTrue(board[5][0] == Game.FieldType.EMPTY);
        assertTrue(board[6][0] == Game.FieldType.WHITE);
        assertTrue(board[7][0] == Game.FieldType.EMPTY);

        assertTrue(board[0][1] == Game.FieldType.EMPTY);
        assertTrue(board[1][1] == Game.FieldType.WHITE);
        assertTrue(board[2][1] == Game.FieldType.EMPTY);
        assertTrue(board[3][1] == Game.FieldType.WHITE);
        assertTrue(board[4][1] == Game.FieldType.EMPTY);
        assertTrue(board[5][1] == Game.FieldType.WHITE);
        assertTrue(board[6][1] == Game.FieldType.EMPTY);
        assertTrue(board[7][1] == Game.FieldType.WHITE);

        assertTrue(board[0][2] == Game.FieldType.WHITE);
        assertTrue(board[1][2] == Game.FieldType.EMPTY);
        assertTrue(board[2][2] == Game.FieldType.WHITE);
        assertTrue(board[3][2] == Game.FieldType.EMPTY);
        assertTrue(board[4][2] == Game.FieldType.WHITE);
        assertTrue(board[5][2] == Game.FieldType.EMPTY);
        assertTrue(board[6][2] == Game.FieldType.WHITE);
        assertTrue(board[7][2] == Game.FieldType.EMPTY);

        for (int row = 3; row < 5; ++row) {
            for (int col = 0; col < 8; col++) {
                assertTrue(board[col][row] == Game.FieldType.EMPTY);
            }
        }

        assertTrue(board[0][5] == Game.FieldType.EMPTY);
        assertTrue(board[1][5] == Game.FieldType.BLACK);
        assertTrue(board[2][5] == Game.FieldType.EMPTY);
        assertTrue(board[3][5] == Game.FieldType.BLACK);
        assertTrue(board[4][5] == Game.FieldType.EMPTY);
        assertTrue(board[5][5] == Game.FieldType.BLACK);
        assertTrue(board[6][5] == Game.FieldType.EMPTY);
        assertTrue(board[7][5] == Game.FieldType.BLACK);

        assertTrue(board[0][6] == Game.FieldType.BLACK);
        assertTrue(board[1][6] == Game.FieldType.EMPTY);
        assertTrue(board[2][6] == Game.FieldType.BLACK);
        assertTrue(board[3][6] == Game.FieldType.EMPTY);
        assertTrue(board[4][6] == Game.FieldType.BLACK);
        assertTrue(board[5][6] == Game.FieldType.EMPTY);
        assertTrue(board[6][6] == Game.FieldType.BLACK);
        assertTrue(board[7][6] == Game.FieldType.EMPTY);

        assertTrue(board[0][7] == Game.FieldType.EMPTY);
        assertTrue(board[1][7] == Game.FieldType.BLACK);
        assertTrue(board[2][7] == Game.FieldType.EMPTY);
        assertTrue(board[3][7] == Game.FieldType.BLACK);
        assertTrue(board[4][7] == Game.FieldType.EMPTY);
        assertTrue(board[5][7] == Game.FieldType.BLACK);
        assertTrue(board[6][7] == Game.FieldType.EMPTY);
        assertTrue(board[7][7] == Game.FieldType.BLACK);
    }

    @Test
    public void startGame_ThereAre12Chequers() {
        assertTrue(game.getWhiteChequers() == 12);
        assertTrue(game.getBlackChequers() == 12);
    }

    @Test
    public void startGame_firstIsPlayerWhite() {
        assertTrue(game.getCurrentPlayer() == Game.Player.WHITE);
    }

    @Test
    public void moveChequerWithoutTaken_changePlayer() {
        game.move("a3:b4");
        assertTrue(game.getCurrentPlayer() == Game.Player.BLACK);
    }

    @Test
    public void moveCorrect_returnTrue() {
        assertTrue(game.move("a3:b4"));
    }

    @Test
    public void moveWhiteChequer_changeWhiteChequerPosition() {
        game.move("a3:b4");
        assertTrue(board[0][2] == Game.FieldType.EMPTY);
        assertTrue(board[1][3] == Game.FieldType.WHITE);
    }

    @Test
    public void moveBlackChequer_changeBlackChequerPosition() {
        game.move("a3:b4");
        game.move("d6:e5");
        assertTrue(board[3][5] == Game.FieldType.EMPTY);
        assertTrue(board[4][4] == Game.FieldType.BLACK);
    }

    @Test
    public void moveToOccupiedField_noChangeBoardStateAndReturnFalse() {
        game.move("a3:b4");
        game.move("b6:a5");
        assertFalse(game.move("b4:a5"));
        assertTrue(board[1][3] == Game.FieldType.WHITE);
        assertTrue(board[0][4] == Game.FieldType.BLACK);
    }

    @Test
    public void wrongMove_notChangePlayer() {
        assertFalse(game.move("a1:b2"));
        assertTrue(game.getCurrentPlayer() == Game.Player.WHITE);
    }

    @Test
    public void moveHorizontalOrVertical_noChangeBoardStateAndReturnFalse() {
        assertFalse(game.move("a3:a4"));
        assertTrue(board[0][2] == Game.FieldType.WHITE);
        assertTrue(board[0][3] == Game.FieldType.EMPTY);

        assertFalse(game.move("a3:b3"));
        assertTrue(board[0][2] == Game.FieldType.WHITE);
        assertTrue(board[1][2] == Game.FieldType.EMPTY);
    }

    @Test
    public void moveNotForwardWithoutTakenChequer_noChangeBoardStateAndReturnFalse() {
        game.move("a3:b4");
        game.move("b6:c5");
        assertFalse(game.move("b4:a3"));
        assertTrue(board[0][2] == Game.FieldType.EMPTY);
        assertTrue(board[1][3] == Game.FieldType.WHITE);

    }

    @Test
    public void moveTwoOrMoreFieldsWithoutTakenChequer_noChangeBoardStateAndReturnFalse() {
        assertFalse(game.move("a3:c5"));
        assertTrue(board[0][2] == Game.FieldType.WHITE);
        assertTrue(board[2][4] == Game.FieldType.EMPTY);

        assertFalse(game.move("a3:d4"));
        assertTrue(board[0][2] == Game.FieldType.WHITE);
        assertTrue(board[3][3] == Game.FieldType.EMPTY);
    }

    @Test
    public void whenNotTakenOther_noChangeBoardStateAndReturnFalse() {
        goToFirstTaken();
        assertFalse(game.move("g5:h4"));
        assertTrue(board[6][4] == Game.FieldType.BLACK);
        assertTrue(board[7][3] == Game.FieldType.EMPTY);
    }

    @Test
    public void whenTakenChequer_decrementChequersAmount() {
        goToFirstTaken();
        assertTrue(game.move("d6:b4"));
        assertTrue(game.getWhiteChequers() == 11);
        assertTrue(board[2][4] == Game.FieldType.EMPTY);
    }

    @Test
    public void whenThereAreMoreChequersToTaken_notChangePlayer() {
        goToSecondTaken();
        assertTrue(game.getCurrentPlayer() == Game.Player.BLACK);
    }

    @Test
    public void takeLastRow_getTheQueen() {
        goToFirstBlackQueen();
        assertTrue(board[2][0] == Game.FieldType.BLACK_QUEEN);
    }

    @Test
    public void moveQueenManyField_changeQueenStateAndReturnTrue() {
        goToFirstBlackQueen();
        game.move("h2:g3"); //white
        assertTrue(game.move("c1:a3"));
        assertTrue(board[2][0] == Game.FieldType.EMPTY);
        assertTrue(board[0][2] == Game.FieldType.BLACK_QUEEN);
    }

    @Test
    public void takenChequerByQueenAndMoveManyFields_deleteTakenChequerAndReturnTrue() {
        goToFirstBlackQueen();
        game.move("h2:g3"); //white
        game.move("c1:a3");
        game.move("d2:c3"); // white
        game.move("b6:a5");
        game.move("c3:b4"); // white
        assertTrue(game.move("a3:d6"));
    }

    @Test
    public void queenDoesntTakenOther_noChangeBoardStateAndReturnFalse() {
        board = createEmptyBoard();
        board[4][0] = Game.FieldType.WHITE_QUEEN;
        board[1][3] = Game.FieldType.BLACK;

        game = Game.createTestingGame(board, Game.Player.WHITE);
        assertFalse(game.move("e1:d2"));
    }



    private void goToFirstBlackQueen() {
        goToSecondTaken();
        game.move("c3:e5");
        game.move("g3:h4"); // white
        game.move("d6:c5");
        game.move("b2:c3"); // white
        game.move("c7:b6");
        game.move("c3:b4"); // white
        game.move("c5:a3");
        game.move("c1:b2"); // white
        game.move("a3:c1");

    }


    private void goToFirstTaken() {
        game.move("a3:b4");
        game.move("h6:g5");
        game.move("b4:c5");
    }

    private void goToSecondTaken() {
        game.move("a3:b4");
        game.move("b6:a5");
        game.move("c3:d4");
        game.move("a5:c3");
    }

    private Game.FieldType[][] createEmptyBoard() {
        Game.FieldType[][] emptyBoard = new Game.FieldType[8][8];

        for(int i = 0 ; i < 8 ; ++i) {
            for(int j = 0 ; j < 8 ; ++j) {
                emptyBoard[i][j] = Game.FieldType.EMPTY;
            }
        }

        return emptyBoard;
    }


}
