import java.util.Random;
import java.util.Scanner;

public class GuessingNo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int minRange = 1;
        int maxRange = 100;
        int maxAttempts = 5;
        int score = 0;

        System.out.println("Welcome to the Guessing Game!");

        boolean playAgain = true;
        while (playAgain) {
            int generatedNumber = random.nextInt(maxRange - minRange + 1) + minRange;
            int attempts = 0;
            boolean guessedCorrectly = false;

            System.out.println("\nI have generated a number between " + minRange + " and " + maxRange + ".");
            System.out.println("You have " + maxAttempts + " attempts to guess the correct number.");

            while (attempts < maxAttempts && !guessedCorrectly) {
                System.out.print("\nEnter your guess: ");
                int userGuess = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                attempts++;

                if (userGuess == generatedNumber) {
                    System.out.println("Congratulations! You guessed the correct number.");
                    score++;
                    guessedCorrectly = true;
                } else if (userGuess < generatedNumber) {
                    System.out.println("Too low! Try again.");
                } else {
                    System.out.println("Too high! Try again.");
                }
            }

            if (!guessedCorrectly) {
                System.out.println("\nSorry, you've run out of attempts. The correct number was " + generatedNumber + ".");
            }

            System.out.print("\nDo you want to play again? (yes/no): ");
            String playAgainInput = scanner.nextLine().toLowerCase();
            playAgain = playAgainInput.equals("yes") || playAgainInput.equals("y");
        }

        System.out.println("\nGame over! Your score is " + score + ".");
        scanner.close();
    }
}
