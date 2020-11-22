package com.example.badcode.dicegame;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
    Scanner scan = new Scanner(System.in);
    Die firstDie = new Die();
    Die secondDie = new Die();
    int score;
    GameOutcomeDecider decider = new GameOutcomeDecider();

    public Game() {
        decider.addHandler(new ScoreHandler(Outcome.WIN, 7, 11));
        decider.addHandler(new ScoreHandler(Outcome.LOSE, 2, 3, 12));
    }

    public Game(Die firstDie, Die secondDie) {
        this();
        this.firstDie = firstDie;
        this.secondDie = secondDie;
    }

    public void shoot() {
        firstDie.roll();
        secondDie.roll();
    }

    public void calculateScore() {
        score = firstDie.getFaceValue() + secondDie.getFaceValue();
    }

    public void displayDetails() {
        System.out.println("Dice 1: " + firstDie.getFaceValue());
        System.out.println("Dice 2: " + secondDie.getFaceValue());
        System.out.println("Score is: " + score);
    }

    public void decideOutcome() {
        switch (decider.apply(this.score)) {
            case WIN:
                System.out.println("Congrats, this was a winning throw.");
                break;
            case LOSE:
                System.out.println("Sorry, this was a losing throw.");
                break;
            default:
                System.out.print("\nDraw, you get another try. Type 1 to throw again or 2 to quit: ");
                int tryAgain = scan.nextInt();

                while (tryAgain != 1 && tryAgain != 2) {
                    System.out.print("Incorrect choice, choose 1 or 2: ");
                    tryAgain = scan.nextInt();
                }
                if (tryAgain == 1) {
                    System.out.println();
                    playAgain();
                } else {
                    System.out.println("\nThank you for playing.");
//                    System.exit(0); // *** THIS IS PARTICULARLY BAD ***
                }
        }
    }

    public void playAgain() {
        shoot();
        calculateScore();
        displayDetails();
        decideOutcome();
    }


    public static void main(String[] args) {
        Game craps = new Game();
        Scanner scan = new Scanner(System.in);
        String quit;

        do {
            craps.shoot();
            craps.calculateScore();
            craps.displayDetails();
            craps.decideOutcome();
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
