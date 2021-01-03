package com.mechdroid.wordsearch.model;

public class WordInsertionDiagonal extends WordInsertion {

    String[][] game;
    int randomRowIndex;
    int randomColIndex;
    int isTaken;
    int randomCounter;

    public WordInsertionDiagonal(String[][] game) {
        this.game = game;
    }

    @Override
    boolean randomFillWords(String word) {
        randomCounter = 0;
        isTaken = 1;

        while (isTaken != 0 && randomCounter <= 200) {
            isTaken = 0;
            randomRowIndex = randomNum((10 - word.length()));
            randomColIndex = randomNum((10 - word.length()));

            for (int z = 0; z < word.length(); z++) {
                if (game[randomRowIndex][randomColIndex] != null) {
                    isTaken += 1;
                }
                randomRowIndex += 1;
                randomColIndex += 1;
            }
            if (isTaken > 0) {
                randomCounter += 1;
            } else {
                System.out.println("successfully inserted word diagonally: " + word);
            }
        }

        if (randomCounter > 200) {
            System.out.println("failed insertion diagonally at words " + word);
            return false;
        } else {
            randomRowIndex -= word.length();
            randomColIndex -= word.length();

            for (int j = 0; j < word.length(); j++) {
                String letter = String.valueOf(word.charAt(j));
                game[randomRowIndex][randomColIndex] = letter;
                randomRowIndex += 1;
                randomColIndex += 1;
            }
        }


        return true;
    }
}
