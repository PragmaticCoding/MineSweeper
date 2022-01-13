package ca.pragmaticcoding.minesweeper;


import javafx.beans.value.ObservableBooleanValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.function.Consumer;

class MineSweeperView extends VBox {

    private final GridPane grid = new GridPane();
    private final Consumer<Integer> boardInitializer;

    MineSweeperView(Consumer<Integer> boardInitializer, ObservableBooleanValue gameLostProperty, ObservableBooleanValue gameWonProperty) {
        this.boardInitializer = boardInitializer;
        initialize(gameLostProperty, gameWonProperty);
    }

    private void initialize(ObservableBooleanValue mineClickedProperty, ObservableBooleanValue unclickEmptyProperty) {
        setSpacing(10);
        getChildren().addAll(setUpMainGrid(mineClickedProperty, unclickEmptyProperty), setUpButtonBox());
        setPadding(new Insets(10));
    }

    private Region setUpMainGrid(ObservableBooleanValue gameLostProperty, ObservableBooleanValue gameWonProperty) {
        return new StackPane(grid,
                setUpWinLosePane(new ImageView(new Image("/images/MineBig.png")), Color.PINK, gameLostProperty),
                setUpWinLosePane(new ImageView(new Image("/images/Win.png")), Color.BLUEVIOLET, gameWonProperty));
    }

    private Region setUpButtonBox() {
        return new HBox(10, createTypeButton("Easy", 10), createTypeButton("Medium", 16), createTypeButton("Hard", 25));
    }

    private Pane setUpWinLosePane(ImageView imageView, Color colour, ObservableBooleanValue revealProperty) {
        Pane pane = new StackPane();
        pane.setBackground(new Background(new BackgroundFill(colour, CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setOpacity(0.6);
        pane.getChildren().add(imageView);
        pane.visibleProperty().bind(revealProperty);
        return pane;
    }

    private Node createTypeButton(String label, int newBoardSize) {
        Button button = new Button(label);
        button.setOnAction(evt -> boardInitializer.accept(newBoardSize));
        return button;
    }

    void addCell(int x, int y, Region cell) {
        grid.add(cell, x, y);
    }

    void clearView() {
        grid.getChildren().clear();
    }
}