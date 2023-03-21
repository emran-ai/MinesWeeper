package ms;

public class GameManager {

    private static final int[][] fieldVisible = new int[10][10];
    private static final int[][] fieldHidden = new int[10][10];
    Minesweeper M = new Minesweeper(fieldVisible,fieldHidden);
    public GameManager() {

    }

    public void startGame() {
        M.startGame();
    }
}


/*
The game manager defines the 2D array, fieldVisible, and fieldHidden that are both 10*10
The game manager has a start gameGame() method that calls the startGame() of the minesWeeper > it seems
like this is a part of MinesWeeper game where the fieldVisible array represents teh visible start of the game
and where the fieldHidden represents the hidden start of the game board with the mines placed.

 */