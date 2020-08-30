package com.mechdroid.wordsearch;

import java.util.Arrays;
import java.util.Random;

//create a random game
public class GenerateGame {
    private String[][] game;
    private int randomRowIndex;
    private int randomColIndex;
    private int isTaken;
    private int randomCounter;
    private int insertOption;


    public GenerateGame(String[][] game) {
        this.game = game;
    }

    public int randomNum(int scale) {
        double randomNumber = Math.random() * scale;
        int randomNumberInteger = (int) (Math.floor(randomNumber));
        return randomNumberInteger;
    }

    public boolean randomFillWords(String word) {
        if (word.length() > 6) {
            insertOption = randomNum(2);
        } else {
            insertOption = randomNum(3);
        }
        isTaken = 1;

        switch (insertOption) {
            case 0:
                randomCounter = 0;

                while (isTaken != 0 && randomCounter <= 200) {
                    isTaken = 0;
                    randomRowIndex = randomNum(10);
                    randomColIndex = randomNum(10 - word.length());

                    for (int j = 0; j < word.length(); j++) {
                        if (game[randomRowIndex][randomColIndex] != null) {
                            isTaken += 1;
                        }
                        randomColIndex += 1;
                    }

                    if (isTaken > 0) {
                        randomCounter += 1;
                    } else {
                        System.out.println("successfully inserted word horizontally: " + word);
                    }
                }

                if (randomCounter > 200) {
                    System.out.println("failed insertion horizontally at " + word);
                    return false;
                } else {
                    randomColIndex -= word.length();

                    for (int j = 0; j < word.length(); j++) {
                        String letter = String.valueOf(word.charAt(j));
                        game[randomRowIndex][randomColIndex] = letter;
                        randomColIndex += 1;
                    }
                }
                break;
            case 1:
                randomCounter = 0;

                while (isTaken != 0 && randomCounter <= 200) {
                    isTaken = 0;
                    randomRowIndex = randomNum((10 - word.length()));
                    randomColIndex = randomNum(10);

                    for (int j = 0; j < word.length(); j++) {
                        if (game[randomRowIndex][randomColIndex] != null) {
                            isTaken += 1;
                        }
                        randomRowIndex += 1;
                    }

                    if (isTaken > 0) {
                        randomCounter += 1;
                    } else {
                        System.out.println("successfully inserted word vertically: " + word);
                    }
                }

                if (randomCounter > 200) {
                    System.out.println("failed insertion vertically at words " + word);
                    return false;
                } else {
                    randomRowIndex -= word.length();

                    for (int j = 0; j < word.length(); j++) {
                        String letter = String.valueOf(word.charAt(j));
                        game[randomRowIndex][randomColIndex] = letter;
                        randomRowIndex += 1;
                    }
                }
                break;
            case 2:
                randomCounter = 0;

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

                break;
        }


      /*  // option 0: insert word horizontally
        if (insertOption == 0) {
            randomCounter = 0;

            while (isTaken != 0 && randomCounter <= 200) {
                isTaken = 0;
                randomRowIndex = randomNum(10);
                randomColIndex = randomNum(10 - word.length());

                for (int j = 0; j < word.length(); j++) {
                    if (game[randomRowIndex][randomColIndex] != null) {
                        isTaken += 1;
                    }
                    randomColIndex += 1;
                }

                if (isTaken > 0) {
                    randomCounter += 1;
                } else {
                    System.out.println("successfully inserted word horizontally: " + word);
                }
            }

            if (randomCounter > 200) {
                System.out.println("failed insertion horizontally at " + word);
                return false;
            } else {
                randomColIndex -= word.length();

                for (int j = 0; j < word.length(); j++) {
                    String letter = String.valueOf(word.charAt(j));
                    game[randomRowIndex][randomColIndex] = letter;
                    randomColIndex += 1;
                }
            }
        }*/

/*        // option 1: insert word vertically
        if (insertOption == 1) {
            randomCounter = 0;

            while (isTaken != 0 && randomCounter <= 200) {
                isTaken = 0;
                randomRowIndex = randomNum((10 - word.length()));
                randomColIndex = randomNum(10);

                for (int j = 0; j < word.length(); j++) {
                    if (game[randomRowIndex][randomColIndex] != null) {
                        isTaken += 1;
                    }
                    randomRowIndex += 1;
                }

                if (isTaken > 0) {
                    randomCounter += 1;
                } else {
                    System.out.println("successfully inserted word vertically: " + word);
                }
            }

            if (randomCounter > 200) {
                System.out.println("failed insertion vertically at words " + word);
                return false;
            } else {
                randomRowIndex -= word.length();

                for (int j = 0; j < word.length(); j++) {
                    String letter = String.valueOf(word.charAt(j));
                    game[randomRowIndex][randomColIndex] = letter;
                    randomRowIndex += 1;
                }
            }
        }*/

       /* // option 2: insert word diagonally
        if (insertOption == 2) {
            randomCounter = 0;

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
        }*/


        return true;
    }

    public String[][] createGame(String[] searchWords) {
        randomCounter = 0;

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
