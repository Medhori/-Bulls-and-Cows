package bullscows;


import java.util.*;

public class Game {
    private final Scanner scanner = new Scanner(System.in);
    private int bull = 0;
    private int cow = 0;
    private String secretCode;
    private int length;
    private int possibleSymbols;

    public void play() {
        System.out.println("Please, enter the secret code's length:");
        String inputLength = scanner.nextLine();

        if (!inputLength.matches("\\d+")) {
            System.out.printf("Error: \"%s\" isn't a valid number.", inputLength);
            return;
        }

        length = Integer.parseInt(inputLength);

        if (length > 36 || length == 0) {
            System.out.printf("Error: can't generate a secret number with a length of " +
                    "%d because there aren't enough unique digits.%n", length);
            return;
        }

        System.out.println("Input the number of possible symbols in the code:");
        String inputPossibleSymbols = scanner.nextLine();

        if (!inputLength.matches("\\d+")) {
            System.out.printf("Error: \"%s\" isn't a valid number.", inputPossibleSymbols);
            return;
        }
        possibleSymbols = Integer.parseInt(inputPossibleSymbols);


        if (length > possibleSymbols) {
            System.out.printf("Error: it's not possible to generate a code with a " +
                    "length of %d with %d unique symbols.%n", length, possibleSymbols);
            return;
        }

        if (possibleSymbols > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return;
        }


        startPlaying();

    }

    private void startPlaying() {
        secretCode = generateSecretNumber();
        System.out.println("Okay, let's start a game!");
        int turn = 1;

        while (true) {
            System.out.printf("Turn %d:%n", turn++);
            startGame();
            if (isGuessed()) {
                System.out.println("Congratulations! You guessed the secret code.");
                break;
            }
            reset();
        }
    }

    private boolean isGuessed() {
        return bull == length;
    }

    private void reset() {
        bull = 0;
        cow = 0;
    }

    private void startGame() {
        String guessed = scanner.nextLine();
        String result;

        for (int i = 0; i < secretCode.length(); i++) {
            if (guessed.charAt(i) == secretCode.charAt(i)) {
                bull++;
            } else if (secretCode.contains(String.valueOf(guessed.charAt(i)))) {
                cow++;
            }
        }

        if (bull == 0 && cow == 0) {
            result = "None";
        } else if (bull > 0 && cow == 0) {
            result = String.format("%d bull(s)", bull);
        } else if (cow > 0 && bull == 0) {
            result = String.format("%d cow(s)", cow);
        } else {
            result = String.format("%d bull(s) and %d cow(s)", bull, cow);
        }

        System.out.printf("Grade: %s.%n", result);

    }
    private String generateSecretNumber() {
        String secretNumber;
        String alpha = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        if (possibleSymbols > 10) {
            alpha = alpha.substring(0, possibleSymbols - 10);
            numbers = numbers.concat(alpha);
            secretNumber = shuffleString(numbers).substring(0, length);
            System.out.printf("The secret is prepared: %s (0-9, a-%s).%n"
                    , "*".repeat(length), alpha.charAt(alpha.length() - 1));
        } else {
            secretNumber = shuffleString(numbers).substring(0, length);
            System.out.printf("The secret is prepared: %s (0-9).%n", "*".repeat(length));
        }

        return secretNumber;
    }
    private static String shuffleString(String s) {
        List<Character> list = new ArrayList<>();
        for (char c : s.toCharArray()) {
            list.add(c);
        }

        Collections.shuffle(list);
        StringBuilder builder = new StringBuilder();
        for (char c : list) {
            builder.append(c);
        }
        return builder.toString();
    }
}
