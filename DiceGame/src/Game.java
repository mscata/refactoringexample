import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
    Scanner scan = new Scanner(System.in);
    Die firstDie = new Die();
    Die secondDie = new Die();
    int score;

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
        try {
            if (score == 7 || score == 11) {
                System.out.println("Congrats, this was a winning throw.");
            }
            else if (score == 2 || score == 3 || score == 12) {
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
                    playAgain();
                }
                else if (tryAgain == 2) {
                    System.out.println("\nThank you for playing.");
                    System.exit(0);
                }
            }
        }
        catch (InputMismatchException ex) {
            System.err.println("Wrong data type. Try again");
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
