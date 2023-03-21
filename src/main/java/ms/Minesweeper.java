package ms;

import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Minesweeper {
    private static int[][] fieldVisible;
    private static int[][] fieldHidden;

    private int gameId = 0; /* it is initialized ot 0 because it keeps track of current game session, with a new
                             game-id being generated for each new game*/

    Board board;
    Tile tile;

    /**
     * @param fieldVisible
     * @param fieldHidden
     */
    public Minesweeper(int[][] fieldVisible, int[][] fieldHidden) {
        Minesweeper.fieldVisible = fieldVisible;
        Minesweeper.fieldHidden = fieldHidden;
//        tile = new Tile(Minesweeper.fieldVisible, Minesweeper.fieldHidden);
//        board = new Board(Minesweeper.fieldVisible, Minesweeper.fieldHidden);
    }

    /**
     * initialize game
     */
    public void startGame() {
        Connection databaseConnection = Lib.initDatabase();
        System.out.println("\n\n================ Welcome to Minesweeper ! ================\n");

        Scanner sc = new Scanner(System.in);           /// user input
        System.out.print("\nEnter your name: ");
        String playerName = sc.nextLine();
        /* it uses a scanner object to get the player's name as input from the console */

        java.sql.Timestamp startTime = Timestamp.valueOf(LocalDateTime.now());
       /* Then it check if the player name already exits in a db using lib.checkPlayerName method. if it does
       it check the player if the player has unfinished game . it prompts the user either load the saved game or
       start a new game. if the user chooses to load the saved the game then the gameid variable is set the last
       game id
        */
        if (Lib.checkPlayerName(databaseConnection, playerName)) {
            // existing player
            System.out.println("Welcome: " + playerName);

            /**
             * user has one unfinished game
             */
            if (Lib.checkLastGameResult(databaseConnection, playerName).equals("in_progress") ) {
                System.out.println("Do you want to load last saved game (l), or start new game (N)?");
                System.out.println("Enter (l), or (N):");
                String userInput = sc.nextLine();
                if (userInput.equals("L") || userInput.equals("l")) {
                    gameId = Lib.getLastGameId(databaseConnection, playerName);
                    Minesweeper.fieldVisible = Lib.getLastGameFieldVisible(databaseConnection, gameId);
                    Minesweeper.fieldHidden = Lib.getLastGameFieldHidden(databaseConnection, gameId);
                    System.out.println("Saved game has been loaded. Press any key to continue.");

                    tile = new Tile(Minesweeper.fieldVisible, Minesweeper.fieldHidden);
                    board = new Board(Minesweeper.fieldVisible, Minesweeper.fieldHidden);

                } else {
                    /* else if the user choose to start a new game. then the game_id variable is set to new game ID. and the new tile and board are instantiated with the fh and fv */
                    gameId = Lib.setNewGame(databaseConnection, playerName, startTime);

                    tile = new Tile(Minesweeper.fieldVisible, Minesweeper.fieldHidden);
                    board = new Board(Minesweeper.fieldVisible, Minesweeper.fieldHidden);

                    tile.setupField(1);
                    System.out.println("Starting new game. Press any key to continue.");
                }
            }

            /**
             * if the player doesn't have an unfinished game, it checks the last game result and prompts the user to start a new game.
             */
            else {

                String lastGameResult = Lib.checkLastGameResult(databaseConnection, playerName);

                System.out.println("You "
                        + lastGameResult
                    + " your last game. ");
                System.out.println("Press any key to start new game.");
                String userInput = sc.nextLine();
                gameId = Lib.setNewGame(databaseConnection, playerName, startTime);

                tile = new Tile(Minesweeper.fieldVisible, Minesweeper.fieldHidden);
                board = new Board(Minesweeper.fieldVisible, Minesweeper.fieldHidden);

                tile.setupField(1);
            }

        } else {
            //new player
            System.out.println("Welcome: " + playerName);
            System.out.println("This is your first game");
            Lib.setNewPlayer(databaseConnection, playerName);
            gameId = Lib.setNewGame(databaseConnection, playerName, startTime);

            tile = new Tile(Minesweeper.fieldVisible, Minesweeper.fieldHidden);
            board = new Board(Minesweeper.fieldVisible, Minesweeper.fieldHidden);

            tile.setupField(1);
        }

        String userInput = sc.nextLine();

        /**
         * run main loop of the game
         */
        playGame(databaseConnection, playerName);
    }


    /**
     * main loop of the game
     */
    public void playGame(Connection databaseConnection, String playerName) {

        Scanner sc = new Scanner(System.in);
        String userInput ="";

        boolean gameRunning = true;

        //Main loop of the game
        /* then enters in while loop which will continue running as loong as  game is  running true; */
        while (gameRunning) {
            Board.displayVisible();

            System.out.println("Do you want to continue (Y/n):");
            String nextInput = sc.nextLine();
            /*
            It then prompts the user to either continue playing the game or exit and save the game's progress.
            If the user chooses to exit, the method saves the game data (visible and hidden fields) to the database using the Lib.saveGameData() method,
            and saves the game results (player name, game ID, end time, and result) to the database using the Lib.saveGameResults() method. It also sets gameRunning
            to false, which will exit the while loop and end the game.

             */

            /**
             * save results and exit unfinished game
             */
            if (nextInput.equals("n") || nextInput.equals("N") ) {
                LocalDateTime endTime = LocalDateTime.now();
                Lib.saveGameData(databaseConnection, gameId, Minesweeper.fieldVisible, Minesweeper.fieldHidden);
                Lib.saveGameResults(databaseConnection, playerName, gameId, endTime, "in_progress");
                System.out.print("Your game has been saved.");
                gameRunning = false;
                break;
            }

            /**
             * If the user chooses to continue playing, the method calls another method called playMove(). If playMove() returns false, it means the player has lost the game,
             * so it prints a message to the console, saves the game results to the database as "lost" and sets gameRunning to false, which will exit the while loop and end
             * the game.
             */
            else {
                gameRunning = playMove();

                Lib.saveGameData(databaseConnection, gameId, Minesweeper.fieldVisible, Minesweeper.fieldHidden);


                if (gameRunning == false) {
                    System.out.print("Oops! You stepped on a mine! \n============ GAME OVER ============");
                    LocalDateTime endTime = LocalDateTime.now();
                    assert databaseConnection != null;
                    Lib.saveGameResults(databaseConnection, playerName, gameId, endTime, "lost");
                }

                /**
                 * if the playMove returned true the method then called another method called checkWhin
                 *  if checkWin return tru it means the player has won the game - checking if the game is finished
                 * and player won the game
                 */
                else if (checkWin()) {
                    board.displayHidden();
                    System.out.println("\n================ You WON!!! ================");
                    LocalDateTime endTime = LocalDateTime.now();
                    assert databaseConnection != null;
                    Lib.saveGameResults(databaseConnection, playerName, gameId, endTime, "won");
                    gameRunning = false;
                }
            }
        }

        System.out.println("\n\nThe game has finished, press any key");
        userInput = sc.nextLine();
    }

    /**
     * get user input and record it in the main game table and database
     *
     * @return
     */
    public boolean playMove() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("\nEnter Row Number (0-9): ");
            int i = sc.nextInt();
            System.out.print("Enter Column Number (0-9): ");
            int j = sc.nextInt();

            /**
             * incorrect input check
             */
            //if (i < 0 || i > 9 || j < 0 || j > 9 || fieldVisible[i][j] != 0) {
            if (i < 0 || i > 9 || j < 0 || j > 9 ) {
                System.out.print("\nIncorrect Input!!");
                System.out.println();
                return true;
            }

            /**
             * it then checks the value of the hidden field at the inputted row and column number
             * .if the value is 100 it means the player stepped on the mine (value 100)
             */
            if (fieldHidden[i][j] == 100) {
                board.displayHidden();
                return false;
            }

            /**
             * open question mark, regular square
             */
            else if (fieldHidden[i][j] == 0) {
                tile.fixVisible(i, j);
            }

            /**
             * open position with the mine
             */
            else {
                tile.fixNeighbours(i, j);
            }

        } catch (InputMismatchException e) {
            System.out.print("\nIncorrect Input!!");
            System.out.println();
        }

        return true;
    }

    /**
     * check if the current user won the game
     *
     * @return
     */
    public boolean checkWin() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (fieldVisible[i][j] == 0) {
                    if (fieldHidden[i][j] != 100) {
                        return false;
                    }
                }
            }
        }
        return true;
    }



}


