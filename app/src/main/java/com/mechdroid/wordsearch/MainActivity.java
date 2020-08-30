package com.mechdroid.wordsearch;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import androidx.gridlayout.widget.GridLayout;

import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private final String DEBUG_TAG = "DEBUG";
    public Button restartGameButton;
    public String rowCol;
    public int rowNum;
    public int colNum;

    public int cellIndex;
    public double cellSize = 90;
    public String word = "";

    ArrayList<String> winner = new ArrayList<>();
    ArrayList<String> wordList = new ArrayList<>();
    ArrayList<Integer> wordListIndex = new ArrayList<>();
    ArrayList<Integer> wordListIndexAll = new ArrayList<>();
    ArrayList<String> validateWords = new ArrayList<>();

    public GridLayout gl;
    public GridLayout glWords;
    public TextView tvMessage;

    public String[][] puzzles;
    public String[] searchWords = {"SWIFT", "KOTLIN", "OBJECTIVEC", "VARIABLE", "JAVA", "MOBILE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        randomizeGame();

        restartGameButton = (Button) findViewById(R.id.restartButton);
        restartGameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // create a new game
                randomizeGame();

                restartGameButton.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        restartGameButton.setEnabled(true);
                    }
                }, 3000);
            }
        });
    }

    private void randomizeGame() {
        String[][] newPuzzle = new String[10][10];
        GenerateGame newGame = new GenerateGame(newPuzzle);
        puzzles = newGame.createGame(searchWords);
        displayGame(puzzles);
    }

    private void displayGame(String[][] letters) {
        tvMessage = (TextView) findViewById(R.id.tvMessage);
        tvMessage.setText("Find the words by swiping over the words");
        tvMessage.setWidth(600);

        // reset
        winner.clear();
        validateWords.clear();
        wordListIndexAll.clear();

        for (int i = 0; i < searchWords.length; i++) {
            winner.add(searchWords[i]);
        }

        //   ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        //    params.setMargins(13, 13, 13, 13);

        //generate GridLayout for the words list
        glWords = (GridLayout) findViewById(R.id.glwords);
        glWords.removeAllViews();
        glWords.setColumnCount(3);
        glWords.setRowCount(2);
        glWords.setBackgroundColor(Color.parseColor("#F4F5FA"));

        for (int i = 0; i < searchWords.length; i++) {
            TextView tViewWords = new TextView(this);
            tViewWords.setText(searchWords[i]);
            tViewWords.setGravity(Gravity.CENTER);
            tViewWords.setWidth(300);
            // tViewWords.setHeight(50);
            // tViewWords.setPadding(14, 5, 14, 10);
            tViewWords.setBackgroundColor(Color.parseColor("#F4F5FA"));
            tViewWords.setId(i);
            //  tViewWords.setLayoutParams(params);
            glWords.addView(tViewWords);
        }

        // create a GridLayout for word search 10 X 10 grid
        gl = (GridLayout) findViewById(R.id.glgame);
        gl.removeAllViews();
        int total = 100;
        int column = 10;
        int row = 10;
        gl.setMinimumWidth(900);
        gl.setMinimumHeight(900);

        gl.setColumnCount(column);
        gl.setRowCount(row);
        gl.setBackgroundColor(Color.parseColor("#F4F5FA"));  //#F4F5FA


        for (int i = 0, c = 0, r = 0; i < total; i++, c++) {
            if (c == column) {
                c = 0;
                r++;
            }

            TextView tView = new TextView(this);
            tView.setText(letters[r][c]);
            tView.setWidth(90);
            tView.setHeight(90);
            tView.setTextSize(19f);
            tView.setBackgroundColor(Color.parseColor("#F4F5FA"));
            tView.setGravity(Gravity.CENTER);
            tView.setId(i);
            gl.addView(tView);
        }

        gl.setOnTouchListener(handleTouch);
    }

    private View.OnTouchListener handleTouch = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            double x = (double) event.getX();
            double y = (double) event.getY();

            // detect swipes
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Log.i("TAG", "touched down");
                    //    Log.i("TAG", "starting at: (" + x + ", " + y + ")");

                    word = "";
                    wordList.clear();
                    wordListIndex.clear();
                    //  wordListIndexAll.clear();
                    break;
                case MotionEvent.ACTION_MOVE:
                    //    Log.i("TAG", "moving: (" + x + ", " + y + ")");

                    // calculate relative position to each cell
                    double xPos = x / cellSize - Math.floor(x / cellSize);
                    double yPos = y / cellSize - Math.floor(y / cellSize);

                    // check if within bounds of grid, or within cell (not on cell edge)
                    // if on cell edges, don't register the swipe. This is so that letter next to it isn't accidentally detected
                    // when swiping diagonally
                    if (x > 0 && x < 900 && y > 0 && y < 900 && ((xPos >= 0.144 && xPos <= 0.855) && (yPos >= 0.144 && yPos <= 0.855))) {
                        rowNum = (int) Math.floor(y / cellSize);
                        colNum = (int) Math.floor(x / cellSize);
                    } else {
                        rowNum = -1;
                        colNum = -1;
                    }

                    cellIndex = rowNum * 10 + colNum;
                    if (rowNum >= 0 && rowNum <= 10 && colNum >= 0 && colNum <= 10) {
                        rowCol = String.valueOf(rowNum) + String.valueOf(colNum);
                    }

                    // return the cell letter and index only once. Highlight the selected cell
                    if (!wordList.contains(rowCol) && rowNum != -1) {
                        wordList.add(rowCol);
                        wordListIndex.add(cellIndex);

                        word += puzzles[rowNum][colNum];
                        gl.getChildAt(cellIndex).setBackgroundColor(Color.parseColor("#B3BCF5"));
                    }

                    break;
                case MotionEvent.ACTION_UP:
                    //  Log.i("TAG", "touched up");

                    // if the selected word matches one of the words on the word list, change the color
                    if (winner.contains(word)) {
                        TextView tvWords = (TextView) glWords.getChildAt(winner.indexOf(word));
                        tvWords.setBackgroundColor(Color.parseColor("#5C6AC4"));
                        tvWords.setTextColor(Color.parseColor("#FFFFFF"));

                        //add word onto validateWord
                        if (!validateWords.contains(word)) {
                            validateWords.add(word);
                        }


                        for (int i = 0; i < wordListIndex.size(); i++) {
                            gl.getChildAt(wordListIndex.get(i)).setBackgroundColor(Color.parseColor("#5C6AC4"));
                            TextView tv = (TextView) gl.getChildAt(wordListIndex.get(i));
                            tv.setTextColor(Color.parseColor("#FFFFFF"));
                            wordListIndexAll.add(wordListIndex.get(i));
                        }


                    } else {
                        //remove cell color not on the list
                        for (int i = 0; i < wordListIndex.size(); i++) {
                            if (wordListIndexAll.contains(wordListIndex.get(i))) {
                                gl.getChildAt(wordListIndex.get(i)).setBackgroundColor(Color.parseColor("#5C6AC4"));
                            } else {
                                gl.getChildAt(wordListIndex.get(i)).setBackgroundColor(Color.parseColor("#F4F5FA"));
                            }
                        }
                    }

                    //check if all words are selected
                    if (validateWords.size() == winner.size()) {
                        tvMessage.setText("Congratulations! Play again?");
                    }

                    break;
            }

            return true;
        }
    };


}