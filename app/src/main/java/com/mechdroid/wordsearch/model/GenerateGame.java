package com.mechdroid.wordsearch.model;

import java.util.Arrays;
import java.util.Random;

//create a random game
public class GenerateGame {
    private String[][] game;
    private int insertOption;


    public GenerateGame(String[][] game) {
        this.game = game;
    }

    //use static method from abstract class instead
  /*  public int randomNum(int scale) {
        double randomNumber = Math.random() * scale;
        int randomNumberInteger = (int) (Math.floor(randomNumber));
        return randomNumberInteger;
    }*/

    public boolean randomFillWords(String word) {


        if (word.length() > 6) {
            insertOption = WordInsertion.randomNum(2);
        } else {
            insertOption = WordInsertion.randomNum(3);
        }

        switch (insertOption) {
            case 0:
                WordInsertionHorizontal insertionHorizontal = new WordInsertionHorizontal(game);
                insertionHorizontal.randomFillWords(word);
                break;
            case 1:
                WordInsertionVertical insertionVertical = new WordInsertionVertical(game);
                insertionVertical.randomFillWords(word);
                break;
            case 2:
                WordInsertionDiagonal insertionDiagonal = new WordInsertionDiagonal(game);
                insertionDiagonal.randomFillWords(word);
                break;
        }


        return true;
    }

    public String[][] createGame(String[] searchWords) {
      //  randomCounter = 0;

        for (int i = 0; i < game.length; i++) {
            Arrays.fill(game[i], null);
        }

        for (int i = 0; i < searchWords.length; i++) {
            // randomize the insertion options: 0 - horizontal; 1 - vertical; 2 - diagonal


            boolean successfullyFilledWord = randomFillWords(searchWords[i]);


            while (!successfullyFilledWord) {
                successfullyFilledWord = randomFillWords(searchWords[i]);
                System.out.println("Re-roll insertion for word " + searchWords[i]);
            }
        }

        // fill the rest of grid with random letters
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (game[i][j] == null) {
                    Random rnd = new Random();
                    char c = (char) ('A' + rnd.nextInt(26));
                    game[i][j] = Character.toString(c);
                }

            }
        }

        return game;
    }
}
