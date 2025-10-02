import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    public static int currentPlayer = 0;
    private board board = new board();

    private Socket socket;

    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private ObjectInputStream objectInputStream;

    private String clientUsername;
    private PlayerInfo playerInfo;
    public char[][] clientBoard = new char[10][10];

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());
            this.playerInfo = (PlayerInfo) objectInputStream.readObject();
            this.clientUsername = this.playerInfo.username;
            this.clientBoard = this.playerInfo.playerBoard;

            clientHandlers.add(this);
            //broadcastMessage("SERVER: " + clientUsername + " has joined the server");
            if (clientHandlers.size() == 2) {
                ClientHandler firstPlayer = clientHandlers.get(currentPlayer);
                firstPlayer.bufferedWriter.write("It is your turn. Enter Coordinates");
                firstPlayer.bufferedWriter.newLine();
                firstPlayer.bufferedWriter.flush();
            }
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String messageFromClient;

        while (socket.isConnected()) {
            try {
                if (clientUsername.length() > 1) {
                    messageFromClient = bufferedReader.readLine();
                    if (messageFromClient != null) {
                        broadcastMessage(messageFromClient);
                    }
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    public void broadcastMessage(String messageToSend) {
        //If the message is sent from the current player
        if (currentPlayer >= clientHandlers.size()) {
            currentPlayer = clientHandlers.size() - 1;
        }

        if (clientUsername.equals(clientHandlers.get(currentPlayer).clientUsername)) {

            //Check if the input hits
            int[] coordinates = new int[2];
            coordinates[0] = (int)(messageToSend.charAt(0) - '0');
            coordinates[1] = (int)(messageToSend.charAt(1) - '0');

            boolean hit;
            boolean win = false;
            if(currentPlayer == 0) {
                if (board.checkHit(clientHandlers.get(1).clientBoard, coordinates[0], coordinates[1])) {
                    hit = true;
                    clientHandlers.get(1).clientBoard[coordinates[0]][coordinates[1]] = 'X';
                    if (board.checkWin(clientHandlers.get(1).clientBoard)) {
                        win = true;
                    }
                } else {
                    hit = false;
                    clientHandlers.get(1).clientBoard[coordinates[0]][coordinates[1]] = '*';
                }
            }
            else {
                if (board.checkHit(clientHandlers.get(0).clientBoard, coordinates[0], coordinates[1])) {
                    hit = true;
                    clientHandlers.get(0).clientBoard[coordinates[0]][coordinates[1]] = 'X';
                    if (board.checkWin(clientHandlers.get(0).clientBoard)) {
                        win = true;
                    }
                } else {
                    hit = false;
                    clientHandlers.get(0).clientBoard[coordinates[0]][coordinates[1]] = '*';
                }
            }

            // Send information to clients
            if (hit) {
                for (ClientHandler clientHandler : clientHandlers) {
                    try {
                        if (!(clientHandler.clientUsername.equals(clientHandlers.get(currentPlayer).clientUsername))){//!clientHandler.clientUsername.equals(clientUsername)) {
                            clientHandler.bufferedWriter.write("You Got Hit. " + coordinates[0] + " " + coordinates[1]);
                            clientHandler.bufferedWriter.newLine();
                            clientHandler.bufferedWriter.flush();
                        }
                        else {
                            clientHandler.bufferedWriter.write("You Hit. " + coordinates[0] + " " + coordinates[1]);
                            clientHandler.bufferedWriter.newLine();
                            clientHandler.bufferedWriter.flush();
                        }
                    } catch (IOException e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
            else {
                for (ClientHandler clientHandler : clientHandlers) {
                    try {
                        if (!clientHandler.clientUsername.equals(clientUsername)) {
                            clientHandler.bufferedWriter.write("Your Enemy Missed. " + coordinates[0] + " " + coordinates[1]);
                            clientHandler.bufferedWriter.newLine();
                            clientHandler.bufferedWriter.flush();
                        }
                        else {
                            clientHandler.bufferedWriter.write("You Missed. " + coordinates[0] + " " + coordinates[1]);
                            clientHandler.bufferedWriter.newLine();
                            clientHandler.bufferedWriter.flush();
                        }
                    } catch (IOException e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }

            //Check if player won
            if (win) {
                for (ClientHandler clientHandler : clientHandlers) {
                    try {
                        if (!clientHandler.clientUsername.equals(clientUsername)) {
                            clientHandler.bufferedWriter.write("You Lost.");
                            clientHandler.bufferedWriter.newLine();
                            clientHandler.bufferedWriter.flush();
                        }
                        else {
                            clientHandler.bufferedWriter.write("You Won!");
                            clientHandler.bufferedWriter.newLine();
                            clientHandler.bufferedWriter.flush();
                        }
                    } catch (IOException e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }


            //change current player
            //tell new current player it is their turn

            synchronized (clientHandlers) {
                currentPlayer = (currentPlayer == 0) ? 1 : 0;
                try {
                    ClientHandler nextPlayer = clientHandlers.get(currentPlayer);
                    nextPlayer.bufferedWriter.write("It is your turn. Enter Coordinates");
                    nextPlayer.bufferedWriter.newLine();
                    nextPlayer.bufferedWriter.flush();
                } catch (IOException e) {
                    closeEverything(socket, bufferedReader, bufferedWriter);
                }
            }
            /*
            try {
                ClientHandler testHandler = clientHandlers.get(currentPlayer);
                testHandler.bufferedWriter.write("It is your turn. Enter Coordinates");
                testHandler.bufferedWriter.newLine();
                testHandler.bufferedWriter.flush();
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }*/
        }
    }

    public void removeClientHandler() {
        clientHandlers.remove(this);
        broadcastMessage("SERVER: " + clientUsername + " has left the server");
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        System.out.println("Close Everything");
        removeClientHandler();
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
}