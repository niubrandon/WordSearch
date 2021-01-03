package com.mechdroid.wordsearch.model;

public abstract class WordInsertion {
    String[][] game;
    //  int randomRowIndex;
    //  int randomColIndex;
    //  int isTaken;
    //  int randomCounter;
    // int insertOption;

    public static int randomNum(int scale) {
        double randomNumber = Math.random() * scale;
        int randomNumberInteger = (int) (Math.floor(randomNumber));
        return randomNumberInteger;
    }

    abstract boolean randomFillWords(String word);
}