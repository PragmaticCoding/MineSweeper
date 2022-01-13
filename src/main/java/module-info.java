module ca.pragmaticcoding.minesweeper {
    requires javafx.controls;
    requires javafx.fxml;


    opens ca.pragmaticcoding.minesweeper to javafx.fxml;
    exports ca.pragmaticcoding.minesweeper;
}