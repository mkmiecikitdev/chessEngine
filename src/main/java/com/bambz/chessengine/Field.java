package com.bambz.chessengine;

/**
 * Created by bambo on 11.06.2017.
 */
public class Field {

    private int col;
    private int row;


    public Field(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

}
