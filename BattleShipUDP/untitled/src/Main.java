import java.util.Scanner;

public class Main {
    // Global variable (static)
    static Scanner in = new Scanner(System.in);

    // Method
    public static void changeValue(int row, int column, int val, int[][] array)
    {
        System.out.println("Type something: ");
        String userLine = in.nextLine();
        array[row][column] = val;
    }

    // Main function (code executes here)
    public static void main(String[] args) {
        int[][] array = new int[5][5]; // Make the array (only accessible in main)

        System.out.println("Enter row: ");
        int row = in.nextInt();
        System.out.println("Enter column: ");
        int column = in.nextInt();
        System.out.println("Enter value: ");
        int val = in.nextInt();

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = 0;
                System.out.print(array[i][j] + "|");
            }
            System.out.println("\n\n");
        }

        changeValue(row, column, val, array); // Call method from above

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j]);
            }
            System.out.println();
        }
    }
}
