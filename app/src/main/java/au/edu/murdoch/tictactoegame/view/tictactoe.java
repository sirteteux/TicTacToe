package au.edu.murdoch.tictactoegame.view;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import au.edu.murdoch.tictactoegame.R;
import au.edu.murdoch.tictactoegame.controller.tictactoegame;

public class tictactoe extends AppCompatActivity {

    private tictactoegame matrix = null;
    private static final String GRID_BOARD_KEY = "GridKey";

    // Number of buttons in the grid
    private int noOfRows;
    private int noOfCols;

    String intentType;

    // Widgets
    Button[] buttons;
    TextView playerTurn;

    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tictactoe);
        //depending on the orientation set the buttons grid number

        intentType = getIntent().getStringExtra("computer");

        switch (getResources().getConfiguration().orientation) {

            // Potrait Mode
            case Configuration.ORIENTATION_PORTRAIT:
                noOfCols = 3;
                noOfRows = 3;
                break;

            // Landscape Mode
            case Configuration.ORIENTATION_LANDSCAPE:
                noOfCols = 5;
                noOfRows = 5;
                break;
        }
        buttons = new Button[noOfRows * noOfCols];

        //Creates a grid of noOfRows by noOfCols on the grid layout
        createButtonGrid(R.id.grid);

        //Reset previous state
        if (savedInstanceState != null) {
            //Restore game through serializable
            restoreGame((tictactoegame) savedInstanceState.getSerializable(GRID_BOARD_KEY));
        } else {
            //Reset game
            initGame();
        }
    }

    /**
     * creates a button grid to be used with the tictactoe grid
     */
    public void createButtonGrid(int id) {
        GridLayout layout = (GridLayout) findViewById(id);
        layout.setColumnCount(noOfCols);

        //Add buttons depending on the noOfRows and noOfCols
        int count = 0;
        for (int i = 0; i < noOfRows; i++) {
            for (int j = 0; j < noOfCols; j++) {
                int n = i * noOfRows + j;

                //dynamically create buttons
                Button button = new Button (this);

                //set the tag to be referenced when clicking the button
                button.setTag(count);
                button.setOnClickListener(view -> onPlayerClick(view));

                //Styling
                button.setBackgroundColor(Color.rgb(0, 255, 0));
                button.setMinHeight(0);
                button.setMinimumHeight(0);
                layout.addView(button);
                buttons[n] = button;
                count++;
            }
        }
    }

    /**
     * Initialize the grid of buttons when the user clicks in the button StartNew
     */
    public void startNew(View view){
        initGame();
    }

    /**
     * Sets the button to the corresponding turns value
     */
    public void onPlayerClick(View view) {
        //If the gave is already over
        if (matrix.isGameFinish()) {
            Toast.makeText(tictactoe.this, "Start Over", Toast.LENGTH_SHORT).show();
            return;
        }

        // find the index of the button to update
        int id = getClickedButtonIndex(view);

        // Update text and check whether there is a winner
        if (id >= 0) {
            boolean isCellsUpdated = updateCell(id);

            // Now let's check whether there is a winner
            if (isCellsUpdated) {
                if (intentType.equalsIgnoreCase("computer") && !matrix.getFilled()){
                    matrix.setPlayingTurn(tictactoegame.circle);
                    isCellsUpdated = false;
                    while (!isCellsUpdated){
                        int i = (int)(computerChoose(noOfRows) / noOfCols);
                        int j = computerChoose(noOfRows) % noOfCols;
                        if (noOfRows == 3){
                            if (i == 1){
                                j += 2;
                            }
                            else if (i == 2){
                                j += 4;
                            }
                        }
                        else{
                            if (i == 1){
                                j += 4;
                            }
                            else if (i == 2){
                                j += 8;
                            }
                            else if (i == 3){
                                j += 12;
                            }
                            else if (i == 4){
                                j += 16;
                            }
                        }
                        isCellsUpdated = updateCell(i+j);
                    }
                }
                int whoIsWinning = checkWinner();
                if (whoIsWinning == tictactoegame.cross) {
                    playerTurn.setText("X is the winner");
                    Toast.makeText(tictactoe.this, "X is the winner", Toast.LENGTH_SHORT).show();

                } else {
                    if (whoIsWinning == tictactoegame.circle) {
                        playerTurn.setText("O is the winner");
                        Toast.makeText(tictactoe.this, "O is the winner", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            if (!matrix.isGameFinish()) {
                setWhoIsPlayingTextView();
            }
        }
    }

    /**
     * Set the cell to the corresponding player code
     */
    public boolean updateCell(int index){

        boolean isCellsUpdated;
        int i = (int)(index / noOfCols);
        int j = index % noOfCols;

        isCellsUpdated = matrix.setCell(i, j);

        if (isCellsUpdated){

            int textResId;

            if (playerTurn() == tictactoegame.cross) {
                textResId = R.string.x_text;
                matrix.setPlayingTurn(tictactoegame.circle);
            }else{
                textResId = R.string.o_text;
                matrix.setPlayingTurn(tictactoegame.cross);
            }
            buttons[index].setText(textResId);
        }
        return isCellsUpdated;
    }

    /**
     * Computer turn
     */
    public int computerChoose(int rows){
        Random random = new Random();
        int chooseRow = random.nextInt(noOfRows);
        int chooseCol = random.nextInt(noOfCols);

        if (rows == 3){
            if (chooseRow == 1){
                chooseCol += 2;
            }
            else if(chooseRow == 2){
                chooseCol += 4;
            }
        }
        else{
            if (chooseRow == 1){
                chooseCol += 4;
            }
            else if(chooseRow == 2){
                chooseCol += 8;
            }
            else if(chooseRow == 3){
                chooseCol += 12;
            }
            else if(chooseRow == 4){
                chooseCol += 16;
            }
        }
        return chooseRow + chooseCol;
    }

    /**
     * Check the winner
     */
    public int checkWinner(){
        int i = matrix.checkWinner();
        return i;
    }

    /**
     * Sets the winning text view
     */
    protected void setWhoIsPlayingTextView(){

        if (playerTurn() == tictactoegame.cross)
            playerTurn.setText(R.string.TextXturn);
        else
            playerTurn.setText(R.string.TextOturn);
    }

    /**
     * Return who is playing
     */
    public int playerTurn(){
        return matrix.playingTurn();
    }

    public int getClickedButtonIndex(View view) {

        return Integer.parseInt(view.getTag().toString());
    }
    @Override
    protected void onSaveInstanceState (Bundle outState) {
        //Serialise the object
        super.onSaveInstanceState(outState);
        outState.putSerializable(GRID_BOARD_KEY, matrix);
    }

    /**
     *Initialise game
     */
    private void initGame(){
        //Create new board if does not exist
        if (matrix==null){
            matrix = new tictactoegame(noOfRows, noOfCols);
        }

        for (int i=0; i < noOfRows; i++) {
            for (int j=0; j < noOfCols; j++) {
                index = i * noOfCols  + j;
                buttons[index].setText(R.string.empty_text);
            }
        }
        //Clear board data
        matrix.clear();

        //Set first player
        playerTurn = (TextView)findViewById(R.id.playerTurn);
        setWhoIsPlayingTextView();

        //Game is not over yet
        matrix.setGameFinish(false);
        Toast.makeText(tictactoe.this, "Game Reset!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Restores the game previous state
     */
    private void restoreGame(tictactoegame restore) {

        //Create a new 5x5 grid
        switch (getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                tictactoegame n = null;
                //if game is over
                if(restore.isGameFinish()) {

                    n = new tictactoegame(5,5);
                    Toast.makeText(tictactoe.this, "Continue!", Toast.LENGTH_SHORT).show();
                }else {

                    //Create a new game
                    n = new tictactoegame(5,5);
                    for(int i = 0; i < restore.getWidth(); i++) {
                        for(int j = 0; j < restore.getHeight(); j++) {
                            n.set(i,j,restore.get(i,j));
                        }
                    }
                    Toast.makeText(tictactoe.this, "Continue!", Toast.LENGTH_SHORT).show();
                    n.setPlayingTurn(restore.playingTurn());
                }
                //set the board to the passed object
                matrix = n;
                int playerID;
                for (int i = 0; i < noOfRows; i++) {

                    for (int j = 0; j < noOfCols; j++) {

                        index = i * noOfCols  + j;

                        switch (matrix.get(i,j)) {

                            case tictactoegame.circle:
                                playerID = R.string.o_text;
                                break;
                            case tictactoegame.cross:
                                playerID = R.string.x_text;
                                break;
                            default:
                                playerID = R.string.empty_text;
                        }
                        buttons[index].setText(playerID);
                    }
                }
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                //Reset the game
                initGame();
                return;
        }

        playerTurn = (TextView)findViewById(R.id.playerTurn);
        setWhoIsPlayingTextView();
    }
}
