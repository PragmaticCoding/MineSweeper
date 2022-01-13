package ca.pragmaticcoding.minesweeper;

import javafx.beans.property.BooleanProperty;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Region;

import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Cell {

    private static final Random RANDOM = new Random();
    private final int x;
    private final int y;
    private final CellModel model = new CellModel();
    private final Region cellView;


    public Cell(int x, int y, BiConsumer<Integer, Integer> clickConsumer) {
        this.x = x;
        this.y = y;
        Consumer<MouseButton> viewClickConsumer = mouseButton -> {
            if (mouseButton.equals(MouseButton.SECONDARY)) {
                model.isFlagProperty().set(!model.isFlagProperty().get());
            } else {
                model.clickedProperty().set(true);
                clickConsumer.accept(x, y);
            }
        };
        cellView = new CellView(model, viewClickConsumer);
    }

    public Region getView() {
        return cellView;
    }

    public void setNum(long num) {
        model.mineCountProperty().set(num > 0 ? Long.toString(num) : "");
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isMine() {
        return model.isMineProperty().get();
    }

    public boolean notMine() {
        return !isMine();
    }

    public boolean isNeighbour(int testX, int testY) {
        if ((Math.abs(getX() - testX) <= 1) && (Math.abs(getY() - testY) <= 1)) {
            return !((getX() == testX) && (getY() == testY));
        }
        return false;
    }

    public BooleanProperty clickedProperty() {
        return model.clickedProperty();
    }

    public void setMineRandomly() {
        model.isMineProperty().set(RANDOM.nextInt(10) <= 1);
    }
}
