package ca.pragmaticcoding.minesweeper;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.function.BiConsumer;

public class MineSweeper extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Minesweeper");
        BiConsumer<Integer, Integer> screenSizeHandler = (height, width) -> {
            primaryStage.setHeight(height);
            primaryStage.setWidth(width);
        };
        Scene value = new Scene(new MineSweeperController(screenSizeHandler).getView());
        primaryStage.setScene(value);
        primaryStage.show();
    }
}
