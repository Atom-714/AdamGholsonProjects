package Junk;

import java.util.Scanner;

public class GameFunctions {
    public static void main(String[] args) {
        int[] attackCoordinates = collectXY(); // Capture the returned values
        int xAxisAttack = attackCoordinates[0]; // Get xAxis
        int yAxisAttack = attackCoordinates[1]; // Get yAxis
        char xAxisLetter = (char) attackCoordinates[2]; // just for the client to see what number they attack
        int xAxisAttackArray = attackCoordinates[3];
        int yAxisAttackArray = attackCoordinates[4];
        boardBuilder();

        System.out.println("You attacked " + xAxisLetter + yAxisAttack); // Print the values
    }

    public static int[] collectXY() {
        // Create a Scanner
        Scanner input = new Scanner(System.in);

        int xAxis = 0;
        int yAxis = 0;

        char stringBreaker = 'a';
        int intGrabber = 0;

        while (xAxis == 0 || yAxis >= 11 || yAxis <= 0) {

            // Resetting variables if invalid input
            xAxis = 0;
            yAxis = 0;

            // Read an initial data
            System.out.print("Enter your target (ex. E4): ");
            String userString = input.nextLine();

            stringBreaker = userString.charAt(0);
            if (Character.isLetter(stringBreaker) == true) {
                stringBreaker = Character.toUpperCase(stringBreaker);
                switch (stringBreaker) {
                    case 'A':
                        xAxis = 1;
                        break;
                    case 'B':
                        xAxis = 2;
                        break;
                    case 'C':
                        xAxis = 3;
                        break;
                    case 'D':
                        xAxis = 4;
                        break;
                    case 'E':
                        xAxis = 5;
                        break;
                    case 'F':
                        xAxis = 6;
                        break;
                    case 'G':
                        xAxis = 7;
                        break;
                    case 'H':
                        xAxis = 8;
                        break;
                    case 'I':
                        xAxis = 9;
                        break;
                    case 'J':
                        xAxis = 10;
                        break;
                    default:
                        System.out.println("Invalid X axis");
                        break;
                }
                // System.out.println(xAxis); // testing
            } else
                System.out.println("invalid X axis");
            // Get Y axis
            String numBroken = userString.substring(1);

            for (char intBreaker : numBroken.toCharArray()) {
                if (Character.isDigit(intBreaker)) {
                    yAxis = yAxis * 10 + Character.getNumericValue(intBreaker);
                } else {
                    System.out.println("Invalid Y axis");
                }
                if (yAxis >= 11) {
                    System.out.println("Invalid Y axis");
                }
            }
            // System.out.println(yAxis); // Variable checking

        }
        return new int[]{xAxis, yAxis, stringBreaker, xAxis - 1, yAxis - 1};
    }


    public static void boardBuilder() {
        char[][] board = new char[3][3];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = 'O';
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

}
