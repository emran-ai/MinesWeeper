package ms;

import java.sql.*;
import java.time.LocalDateTime;

public class Lib {
    /**
     *
     * @return
     */
    public static Connection initDatabase() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ascii007", "postgres", "Student_1234");
            System.out.println("Connected to Postgresql server");

            return connection;

        } catch (SQLException e) {
            System.out.println("Error in connecting to Postgresql server: ");
            //throw new RuntimeException(e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param playerName
     *
     * This function will search int_leardership table to look for
     * playerName provided by the user
     *
     * @return boolean
     *
     * This method appears to check it the playername already exists in db called int_leaderboard.
     */
    public static boolean checkPlayerName(Connection connection, String playerName){

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT player_name FROM int_leaderboard");
            ResultSet rs = ps.executeQuery();

            boolean playerNameFound = false; /* then initialize a boolean variable if the playername already exits in db */

            while (rs.next()) {
                String player_name = rs.getString(1);

                if (player_name.equals(playerName)){
                    playerNameFound = true;
                }
            }
            return playerNameFound;

        } catch (SQLException e) {
            System.out.println("Error in connecting to Postgresql server: ");
            //throw new RuntimeException(e);
            e.printStackTrace();

            return false;
        }
    }

    /**
     *
     * @param connection
     * @param playerName
     * @return
     */

    /* this one retrieves the result of the last game played by a player from postgresql db  */
    public static String checkLastGameResult(Connection connection, String playerName){

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT game_result FROM int_minesweeper " +
                    "where player_name = '" + playerName + "' " +
                    "order by game_id desc " +
                    " FETCH NEXT 1 ROWS ONLY;");
            ResultSet rs = ps.executeQuery();

            rs.next();
            String gameResult = rs.getString(1);

            return gameResult;

        } catch (SQLException e) {
            System.out.println("Error in connecting to Postgresql server : ");
            //throw new RuntimeException(e);
            e.printStackTrace();

            return "Error";
        }
    }

    /**
     * this function inserts a new data for a new player
     * to the table int_leaderboard
     * This method create a new player in postgresql db by taking in a player's name and connection. it uses
     * the prepared statement to insert the player's name into the int_leaderboard>
     * @param connection
     * @param playerName
     * @return
     */
    public static void setNewPlayer(Connection connection, String playerName) {
        try {

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO int_leaderboard (player_name ) VALUES (?);");
            statement.setString(1, playerName);

            int rows = statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error in connecting to Postgresql server : ");
            e.printStackTrace();
        }
    }

    /**
     * this function inserts a new data for a new player
     * to the table int_leaderboard
     *
     * @param connection
     * @param playerName
     * @param startTime
     * @return
     */
    /*
    It first uses a PreparedStatement to retrieve the maximum game_id from the int_minesweeper table and
    stores it in the variable max_id.

    Then it uses another PreparedStatement to insert a new record into the int_minesweeper table with the
    provided player name, the next game_id (which is max_id + 1), the start time and a default value of
     "in_progress" for the game_result.
     */
    public static int setNewGame(Connection connection, String playerName, Timestamp startTime) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT max(game_id) as max_id " +
                    "FROM int_minesweeper;");
            ResultSet rs = ps.executeQuery();
            rs.next();
            int max_id = rs.getInt(1);

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO int_minesweeper (player_name,  game_id, start_time, game_result ) " +
                            "VALUES (?,?,?,?);");
            statement.setString(1, playerName);
            statement.setInt(2, max_id +1);
            statement.setTimestamp(3, startTime);
            statement.setString(4, "in_progress");

            int rows = statement.executeUpdate();

            return max_id + 1;

        } catch (SQLException e) {
            System.out.println("Error in connecting to Postgresql server : ");
            //throw new RuntimeException(e);
            e.printStackTrace();

            return -1;
        }
    }

    /**
     * This function is used to save  the result of the minesWeeper  game to the db. The prepared statement is updating
     * the int_minesWepper table by setting teh end_time adn game_result columns of the record with a specific game_id.
     * @param connection
     * @param playerName
     * @param gameId
     * @param endTime
     * @param gameResult
     */
    public static void saveGameResults(Connection connection, String playerName,
                                       int gameId, LocalDateTime endTime, String gameResult) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE int_minesweeper " +
                "set end_time = '" + endTime + "'"+
                ",game_result = '" + gameResult + "' " +
                    "where game_id = " + gameId +";");

            int rows = statement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("*** Database error ***");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param connection
     * @param gameId
     *
     * This function is used to save the game data
     */
    public static void saveGameData(Connection connection, int gameId, int[][] fieldVisible, int[][] fieldHidden) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE From int_board where game_id = " + gameId + ";");
            int rows = statement.executeUpdate();

             statement = connection.prepareStatement(
                    "DELETE From int_tiles where game_id = " + gameId + ";");
            rows = statement.executeUpdate();

            /**
             * filling int_board with fieldVisible values
             */
            for(int i =0; i<10; i++){
                for(int j =0; j<10; j++){
                    statement = connection.prepareStatement(
                            "INSERT INTO int_board (game_id,  field_x, field_y, field_value ) " +
                                    "VALUES (?,?,?,?);");
                    statement.setInt(1, gameId);
                    statement.setInt(2, i);
                    statement.setInt(3, j);
                    statement.setInt(4, fieldVisible[i][j]);
                    rows = statement.executeUpdate();
                }
            }

            /**
             * filling int_tiles with fieldHidden values
             */
            for(int i =0; i<10; i++){
                for(int j =0; j<10; j++){
                    statement = connection.prepareStatement(
                            "INSERT INTO int_tiles (game_id,  field_x, field_y, field_value ) " +
                                    "VALUES (?,?,?,?);");
                    statement.setInt(1, gameId);
                    statement.setInt(2, i);
                    statement.setInt(3, j);
                    statement.setInt(4, fieldHidden[i][j]);
                    rows = statement.executeUpdate();
                }
            }

        } catch (SQLException e) {
            System.err.println("*** Database error ***");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to retrieve the last played game id of the player from the int_minesWeeper table, so that the
     * game can be resumed or the player can view the detail of the last played game.
     * @param connection
     * @param playerName
     */
    public static int getLastGameId(Connection connection, String playerName) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT game_id FROM int_minesweeper " +
                    "where player_name = '" + playerName + "' " +
                    "order by game_id desc " +
                    " FETCH NEXT 1 ROWS ONLY;");
            ResultSet rs = ps.executeQuery();

            rs.next();
            int gameId = rs.getInt(1);

            return gameId;

        } catch (SQLException e) {
            System.out.println("Error in connecting to Postgresql server : ");
            //throw new RuntimeException(e);
            e.printStackTrace();

            return 0;
        }

    }

    /**
     * this function load the visible fields from the database for the specific game
     * @param connection
     * @param gameId
     * @return
     */
    public static int[][] getLastGameFieldVisible(Connection connection, int gameId) {
        try {
             int[][] fieldVisible = new int[10][10];

            PreparedStatement ps = connection.prepareStatement("SELECT field_x, field_y, field_value " +
                    "FROM int_board " +
                    "where game_id = " + gameId + ";");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int fieldX  = rs.getInt(1);
                int fieldY  = rs.getInt(2);
                int fieldValue = rs.getInt(3);

                fieldVisible[fieldX][fieldY] = fieldValue;
            }

            return fieldVisible;

        } catch (SQLException e) {
            System.err.println("*** Database error ***");
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }


    /**
     * this function load the visible fields from the database for the specific game
     * @param connection
     * @param gameId
     * @return
     */
    public static int[][] getLastGameFieldHidden(Connection connection, int gameId) {
        try {
            int[][] fieldHidden = new int[10][10];

            PreparedStatement ps = connection.prepareStatement("SELECT field_x, field_y, field_value " +
                    "FROM int_tiles " +
                    "where game_id = " + gameId + ";");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int fieldX  = rs.getInt(1);
                int fieldY  = rs.getInt(2);
                int fieldValue = rs.getInt(3);

                fieldHidden[fieldX][fieldY] = fieldValue;
            }

            return fieldHidden;

        } catch (SQLException e) {
            System.err.println("*** Database error ***");
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }


}

