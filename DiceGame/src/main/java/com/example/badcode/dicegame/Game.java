package com.example.badcode.dicegame;

import io.vavr.Tuple2;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Supplier;

public class Game {
    private class GameOverChecker {
        public GameResult isWinningThrow(final int score) {
            if (score == 7 || score == 11) {
                return GameResult.WIN;
            }
            else if (score == 2 || score == 3 || score == 12) {
                return GameResult.LOSE;
            }
            return GameResult.NO_RESULT;
        }
    }

    private final GameOverChecker gameOverChecker = new GameOverChecker();

    private class SequenceGenerator implements Supplier<Integer> {

        private final int[] array;
        private int counter;

        private SequenceGenerator(final int[] array) {
            this.array = array;
            this.counter = 0;
        }

        @Override
        public Integer get() {
            return array[counter++ % array.length];
        }
    }

    private class RandomGenerator implements Supplier<Integer> {
        private final Random r;

        private RandomGenerator(final Random r) {
            this.r=r;
        }

        @Override
        public Integer get() {
            return 1+r.nextInt(6);
        }
    }

    private final Scanner scan = new Scanner(System.in);
    private final Rollable firstDie = new GeneratorDie(new SequenceGenerator(new int[]{1,2,3,4,5,6,2,3,4,1}));
    private final Rollable secondDie = new GeneratorDie(new SequenceGenerator(new int[]{1,4,1,2,6,3,1,2,4,2}));

    public Tuple2<Integer,Integer> shoot() {
        return new Tuple2<>(firstDie.roll(), secondDie.roll());
    }

    public int calculateScore(final Tuple2<Integer,Integer> rolls) {
        return rolls._1 + rolls._2;
    }

    public void displayDetails(final Tuple2<Integer,Integer> rolls, final int score) {
        System.out.println("Dice 1: " + rolls._1);
        System.out.println("Dice 2: " + rolls._2);
        System.out.println("Score is: " + score);
    }

    public void decideOutcome(final int score) {
        try {
            final GameResult gameResult = gameOverChecker.isWinningThrow(score);
            if (gameResult==GameResult.WIN) {
                System.out.println("Congrats, this was a winning throw.");
            }
            else if (gameResult==GameResult.LOSE) {
                System.out.println("Sorry, this was a losing throw.");
            }
            else {
                System.out.print("\nDraw, you get another try. Type 1 to throw again or 2 to quit: ");
                int tryAgain = scan.nextInt();

                while (tryAgain != 1 && tryAgain !=2) {
                    System.out.print("Incorrect choice, choose 1 or 2: ");
                    tryAgain = scan.nextInt();
                }
                if (tryAgain == 1) {
                    System.out.println();
                    play();
                }
                else if (tryAgain == 2) {
                    System.out.println("\nThank you for playing.");
                    System.exit(0);
                }
            }
        }
        catch (final InputMismatchException ex) {
            System.err.println("Wrong data type. Try again");
        }
    }

    public void play() {
        final Tuple2<Integer, Integer> rolls = shoot();
        final int score = calculateScore(rolls);
        displayDetails(rolls, score);
        decideOutcome(score);
    }

    public static void main(final String[] args) {
        final Game craps = new Game();
        final Scanner scan = new Scanner(System.in);
        String quit;

        do {
            craps.play();
            System.out.print("\nPlay again? yes or no: ");
            quit = scan.nextLine();
            while (!quit.equalsIgnoreCase("no") && !quit.equalsIgnoreCase("yes")) {
                System.out.print("\nPlay again? yes or no: ");
                quit = scan.nextLine();
            }
            System.out.println();
        } while (!quit.equalsIgnoreCase("no"));
        scan.close();
        System.out.println("Thank you for playing.");
    }
}
