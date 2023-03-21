module com.gameminesweeper {
    requires java.sql;


    opens ms to javafx.fxml;
    exports ms;
}