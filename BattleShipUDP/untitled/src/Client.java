import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Client {

    private Socket socket;
    public static final int PORT = 3030;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private ObjectOutputStream objectOutputStream;

    private String username;
    private PlayerInfo playerInfo;
    public static char[][] playerBoard = new char[10][10];
    public static char[][] attackBoard = new char[10][10];

    public Client(Socket socket, String username, PlayerInfo playerInfo) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            this.username = username;
            this.playerInfo = playerInfo;
        } catch (IOException e) {
            System.out.println(4);
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendSpecMessage(String message) {
        try {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println(3);
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage() {
        try {
            //bufferedWriter.write(username);
            //bufferedWriter.newLine();
            //bufferedWriter.flush();

            objectOutputStream.writeObject(playerInfo);
            objectOutputStream.flush();
/*
            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String messageToSend = scanner.nextLine();
                bufferedWriter.write(username + ": " + messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
 */
        } catch (IOException e)
        {
            System.out.println(2);
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;

                while (socket.isConnected()) {
                    try {
                        msgFromGroupChat = bufferedReader.readLine();
                        if (msgFromGroupChat != null) {
                            HandleInput(msgFromGroupChat);
                        }
                    } catch (IOException e) {
                        System.out.println(1);
                        System.out.println(e.getMessage());
                        closeEverything(socket, bufferedReader, bufferedWriter);
                        break;
                    }
                }
            }
        }).start();
    }

    public void HandleInput(String input) {
        String[] inputArray = input.split(" ");
        System.out.println(input);

        if (input.equals("It is your turn. Enter Coordinates")) {
            int[] coords = board.collectXY();
            String move = String.valueOf(coords[0]) + String.valueOf(coords[1]);
            System.out.println(coords[0] + ", " + coords[1]);
            // send move to server
            sendSpecMessage(move);
        }
        else if (inputArray[1].equalsIgnoreCase("Hit.")) {
            // Successfully hit an opponents ship
            int x = Character.getNumericValue(inputArray[2].charAt(0));
            int y = Character.getNumericValue(inputArray[3].charAt(0));
            System.out.println("Coordinates: [" + x + ", " + y + "]");
            attackBoard[x][y] = 'X';
        }
        else if (inputArray[1].equalsIgnoreCase("Missed.")) {
            // Did not hit opponents ship
            int x = Character.getNumericValue(inputArray[2].charAt(0));
            int y = Character.getNumericValue(inputArray[3].charAt(0));
            attackBoard[x][y] = '*';
        }
        else if (inputArray[1].equalsIgnoreCase("Enemy")) {
            // Enemy missed your ship
            int x = Character.getNumericValue(inputArray[3].charAt(0));
            int y = Character.getNumericValue(inputArray[4].charAt(0));
            playerBoard[x][y] = '*';
        }
        else if (inputArray[1].equalsIgnoreCase("Got")) {
            // Enemy hit your ship
            int x = Character.getNumericValue(inputArray[3].charAt(0));
            int y = Character.getNumericValue(inputArray[4].charAt(0));
            playerBoard[x][y] = 'X';
        }
        else if (inputArray[1].equalsIgnoreCase("Win!")){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
        else if (inputArray[1].equalsIgnoreCase("Lost.")){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }

        System.out.println("Your Board:");
        board.viewMyBoard(playerBoard);
        System.out.println("________________________________");
        System.out.println("Enemy Board:");
        board.viewOpponentBoard(attackBoard);
    }

    public void checkReceived(String msg)
    {
        board.collectXY();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        System.out.println("Close Everything");
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException{

        // Set up user
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username for the server: ");
        String username = scanner.nextLine();

        //Create board
        char[][] tempBoard =  new char[10][10];

        for (int i = 0; i < tempBoard.length; i++) {
            Arrays.fill(tempBoard[i], '0');
        }

        for (int i = 0; i < attackBoard.length; i++) {
            Arrays.fill(attackBoard[i], '0');
        }

        board.viewMyBoard(tempBoard);

        playerBoard = board.setupBoard(tempBoard);


        PlayerInfo playerInfo = new PlayerInfo(username, tempBoard);

        // Join server
        Socket socket = new Socket("localhost", PORT);
        Client client = new Client(socket, username, playerInfo);
        client.listenForMessage();
        client.sendMessage();
    }
}