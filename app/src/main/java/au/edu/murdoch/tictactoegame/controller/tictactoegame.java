package au.edu.murdoch.tictactoegame.controller;

import java.io.Serializable;

import au.edu.murdoch.tictactoegame.model.Matrix;

public class tictactoegame extends Matrix implements Serializable {

    public static final int cross  = 1;
    public static final int circle = -1;

    private boolean isGameEnds = false;

    private int playerPlay = cross;

    public tictactoegame(int rows, int cols){
        super(rows, cols);
    }

    /**
     * Sets the cell
     */
    public boolean setCell(int i, int j) {
        int value = playingTurn();

        if (value == cross){
            return setToCross( i,  j);
        }

        if (value == circle)
            return setToCircle( i,  j);

        return false;
    }

    /**
     * Sets cell to cross
     */
    public boolean setToCross(int i, int j) {
        //Set empty cells to cross
        boolean isUpdated = false;
        if (get(i, j) == 0) {
            super.set(i, j, cross);
            isUpdated = true;

            //Check whether there is a winner
            if (isCrossWinning()) {
                setGameFinish(true);
            }
        }
        return isUpdated;
    }

    /**
     * Sets cell to circle
     */
    public boolean setToCircle(int i, int j){
        //Set empty cells to circle
        boolean isUpdated = false;
        if (get(i, j) == 0) {
            super.set(i, j, circle);
            isUpdated = true;

            // Check whether there is a winner
            if (isCircleWinning()) {
                setGameFinish(true);
            }
        }
        return isUpdated;
    }

    /**
     * Clear the cell by setting its value to 0
     */
    public void clear(int i, int j) {
        set(i, j, 0);
    }

    /**
     * Clear all cells by setting their vslues to 0
     */

    public void clear() {
        for (int i=0; i < getHeight(); i++) {

            for (int j=0; j < getWidth(); j++) {
                set(i, j, 0);
            }
        }
        setPlayingTurn(cross);
    }
    /**
     * Check winning moves and if there is a winner
     */
    public boolean isWinning(int playerCode) {
        return (checkColumns(playerCode) || checkDiagonals(playerCode) || checkRows(playerCode));
    }

    /**
     * Checks the rows for a win
     */
    public boolean checkRows(int playerCode) {
        boolean winResults = false;

        //Check the rows
        for (int row = 0; row < getHeight(); row++) {
            int total = 0;

            for (int col = 0; col < getWidth(); col++) {
                total += get(row,col);

                //if the addition adds up to the width, true
                if(total == playerCode * getWidth()){
                    winResults = true;
                    return winResults;
                }
            }
        }
        return winResults;
    }

    /**
     * Check the colums for a winning move
     */
    public boolean checkColumns(int playerCode){
        boolean winResults = false;

        for (int col = 0; col < getHeight(); col++) {
            int total = 0;

            for (int row = 0; row < getWidth(); row++) {
                //if the addition is equal to the widdth, true
                total += get(row,col);

                if(total == playerCode * getWidth()) {
                    winResults = true;
                    return winResults;
                }
            }
        }
        return winResults;
    }

    /**
     * Checks the diagonals for a win
     */
    public boolean checkDiagonals(int playerCode){
        int total = 0;
        boolean winResults = false;

        for (int lr = 0; lr < getWidth(); lr++)
        {
            total += get(lr,lr);
        }

        if (total == playerCode * getWidth())
        {
            winResults = true;
            return winResults;
        }

        total = 0;

        for (int rl = 0; rl < getWidth(); rl++)
        {
            total += get(rl,getWidth() - rl - 1);

        }

        if (total == playerCode * getWidth())
        {
            winResults = true;
            return winResults;
        }
        return winResults;
    }
    /**
     * Check whether cross is winning
     */
    public boolean isCrossWinning() {
        return isWinning(cross);
    }

    /**
     * Check whether circle is winning
     */
    public boolean isCircleWinning() {
        return isWinning(circle);
    }

    /**
     * Check winner
     * 0 - Draw, 1 - Cross, 2 - Circle
     */
    public int checkWinner() {

        if (isCrossWinning())
            return cross;
        else {

            if (isCircleWinning()) {
                return circle;

            }else {
                //Draw
                return 0;
            }
        }
    }

    public boolean isGameFinish() {
        return isGameEnds;
    }

    public void setGameFinish(boolean v) {
        isGameEnds = v;
    }

    public int playingTurn() {
        return playerPlay;
    }
    public void setPlayingTurn(int v) {
        playerPlay = v;
    }

    public boolean getFilled(){
        boolean filled = true;
        for (int i=0; i < getHeight(); i++) {

            for (int j=0; j < getWidth(); j++) {
                if (get(i, j) == 0){
                    filled = false;
                }
            }
        }
        return filled;
    }
}