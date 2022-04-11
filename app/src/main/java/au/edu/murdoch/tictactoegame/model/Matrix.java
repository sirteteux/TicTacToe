package au.edu.murdoch.tictactoegame.model;

public class Matrix {

    private int[] matrix;
    int width;
    int height;

    public Matrix(int width, int height){

        this.width = width;
        this.height = height;

        matrix = new int[width * height];

        for (int i = 0; i < height; i++) {

            for (int j = 0; j < width; j++) {

                matrix[i * width + j] = 0;
            }
        }
    }

    public int get(int row, int col) {
        return matrix[row * width + col];
    }

    public void set(int row, int col, int val) {
        matrix[row * width + col] = val;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}