import java.io.Serializable;

public class PlayerInfo implements Serializable {
    public String username;
    public char[][] playerBoard;

    public PlayerInfo(String username, char[][] playerBoard) {
        this.username = username;
        this.playerBoard = playerBoard;
    }

    @Override
    public String toString(){
        return "Username: " + username + "\nBoard" + playerBoard.toString();
    }


}
