import java.util.Scanner;

public class board { //create board

    public board() {

    }

    public static char[][] setupBoard(char[][] board){
        //int[] ships = {2};
        int[] ships = {2,3};
        viewMyBoard(board);

        for(int i=0; i < ships.length; i++){
            Scanner scanner = new Scanner(System.in);

            System.out.println("For ship length " + ships[i] + ", what is the starting x coordinate (A-J)?");
            //int x = scanner.nextInt();
            String x = scanner.nextLine();

            System.out.println("For ship length " + ships[i] + ", what is the starting y coordinate (1-10)? ");
            int y = scanner.nextInt();
            y = y - 1;

            System.out.println("Would you like your ship to be horizontal or vertical? Enter h for horizontal or v for vertical: ");
            String orientation = scanner.next();

            if (orientation.equals("h")){
                //addShipHorizontal(x, y, ships[i], board);
                addShipHorizontal((letterToInt(x)), y, ships[i], board);

            }
            else if (orientation.equals("v")){
                //addShipVertical(x, y, ships[i], board);
                addShipVertical((letterToInt(x)), y, ships[i], board);

            }

            viewMyBoard(board);
        }
        return board;
    }

    public static void addShipVertical(int x, int y, int length, char[][] grid) {
        for (int i = 0; i < length; i++) {
            grid[x + i][y] = '1';
            /*
            if (grid[x + i][y] == '0') {

            } */
        }
    }

    public static void addShipHorizontal(int x, int y, int length, char[][] grid) {
        for (int i = 0; i < length; i++) {
            grid[x][y + i] = '1';
            /*
            if (grid[x][y + i] == '0') {

            } */
        }
    }

    public static void viewMyBoard(char[][] board) {
//        for (int i = 0; i < board.length; i++) {
//            for (int j = 0; j < board[i].length; j++) {
//                System.out.print(board[i][j] + " ");
//            }
//            System.out.println();
//        }
        char c = 'A';
        System.out.print("   ");
        for(int i = 0; i < 10; i++){
            System.out.print((i+1) + " ");
        }
        System.out.println();

        System.out.print(c + "| ");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
            c += 1;
            if(c < 75){
                System.out.print((c) + "| ");
            }
        }
    }

    public static void viewOpponentBoard(char[][] board) {/*

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == '1') {
                    System.out.print("0 ");
                } else {
                    System.out.print(board[i][j] + " ");
                }
            }
            System.out.println();
        }
        */
        char c = 'A';
        System.out.print("   ");
        for(int i = 0; i < 10; i++){
            System.out.print((i+1) + " ");
        }
        System.out.println();

        System.out.print(c + "| ");
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                if (board[i][j] == '1'){
                    System.out.print("0 ");
                } else{
                    System.out.print(board[i][j] + " ");
                }
            }
            System.out.println();
            c += 1;
            if(c < 75){
                System.out.print((c) + "| ");
            }
        }
    }

    public void playerTurn(char[][] board, Client client) {
        /*
        System.out.println("Your Turn: ");

        while (true) {

            int[] attackWhat = collectXY();
            int x = attackWhat[0];
            int y = attackWhat[1];

            if ((x >= 0 && x < 10) && (y >= 0 && y < 10)) {
                if ((board[x][y] == 'X') || (board[x][y] == '*')) {
                    System.out.println("Coordinate has already been guessed!");
                } else {
                    if (board[x][y] == '1') {
                        System.out.println("Hit!");
                        board[x][y] = 'X';

                        System.out.println("Your Board");
                        viewMyBoard(board);

                        System.out.println("Opponent's Board");
                        viewOpponentBoard(opBoard);

                        checkWin(board);

                    } else if (board[x][y] == '0') {
                        System.out.println("Miss!");
                        board[x][y] = '*';

                        System.out.println("Your Board");
                        viewMyBoard(board);

                        System.out.println("Opponent's Board");
                        viewOpponentBoard(opBoard);
                        break;
                    }
                }
            }
         */
    }

    public boolean checkHit(char[][] board, int x, int y) {
        if (board[x][y] == '1') {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkWin(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == '1') {
                    return false;
                }
            }
        }
        return true;
    }

    public static int convertXY(char val)
    {
        if (Character.isLetter(val))
        {
            return Character.getNumericValue(val) - 1;
        }
        return 0;
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
                    System.out.println("CollectXY Yaxis: " + yAxis);
                } else {
                    System.out.println("Invalid Y axis");
                }
                if (yAxis >= 11) {
                    System.out.println("Invalid Y axis");
                }
            }
            // System.out.println(yAxis); // Variable checking

        }
        return new int[]{xAxis - 1, yAxis - 1};
    }

    public static int letterToInt(String x){
        char ch = Character.toUpperCase(x.charAt(0));
        return ch - 'A';
    }
}

