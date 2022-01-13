package ca.pragmaticcoding.minesweeper;


import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class MineSweeperController {

    private final BooleanProperty loseProperty = new SimpleBooleanProperty(false);
    private final BooleanProperty winProperty = new SimpleBooleanProperty(false);

    private final MineSweeperView view;

    private boolean initialized = false;
    private ArrayList<Cell> cells = new ArrayList<>();
    private final BiConsumer<Integer, Integer> screenSizeHandler;

    MineSweeperController(BiConsumer<Integer, Integer> screenSizeHandler) {
        this.screenSizeHandler = screenSizeHandler;
        view = new MineSweeperView(this::setBoardSize, loseProperty, winProperty);
    }

    private void setBoardSize(int newBoardSize) {
        screenSizeHandler.accept((newBoardSize * 31) + 100, (newBoardSize * 31) + 32);
        reset();
        loadBoard(newBoardSize);
    }

    public Region getView() {
        return view;
    }

    public void reset() {
        winProperty.unbind();
        winProperty.set(false);
        loseProperty.unbind();
        loseProperty.set(false);
        cells.clear();
        initialized = false;
    }

    public void loadBoard(int boardSize) {
        view.clearView();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Cell cell = new Cell(i, j, this::consumeBoardClick);
                view.addCell(i, j, cell.getView());
                cells.add(cell);
            }
        }
    }

    private void consumeBoardClick(int x, int y) {
        if (!initialized) {
            initializeBoard(x, y);
            initialized = true;
        }
        System.out.println("***** (" + x + ", " + y + ")");
        cells.stream()
             .filter(Cell::notMine)
             .filter(cell -> !cell.clickedProperty().get())
             .forEach(cell -> System.out.println("\t" + "(" + cell.getX() + ", " + cell.getY() + ")"));
    }

    public void initializeBoard(int pressedI, int pressedJ) {
        setMines(pressedI, pressedJ);
        cells.forEach(cell -> cell.setNum(getNeighbors(cell.getX(), cell.getY()).stream().filter(Cell::isMine).count()));
        winProperty.bind(createWinLoseBinding(Cell::notMine, Boolean::logicalAnd));
        loseProperty.bind(createWinLoseBinding(Cell::isMine, Boolean::logicalOr));
    }

    private BooleanBinding createWinLoseBinding(Predicate<Cell> predicate, BinaryOperator<Boolean> reductionOperator) {
        List<BooleanProperty> clickProperties = cells.stream().filter(predicate).map(Cell::clickedProperty).collect(Collectors.toList());
        return Bindings.createBooleanBinding(() -> clickProperties.stream()
                                                                  .map(BooleanProperty::get)
                                                                  .reduce(clickProperties.get(0).get(), reductionOperator),
                                             clickProperties.toArray(new BooleanProperty[0]));
    }

    private void setMines(int pressedI, int pressedJ) {
        cells.stream()
             .filter(cell -> !cell.isNeighbour(pressedI, pressedJ))
             .filter(cell -> !((cell.getX() == pressedI) && (cell.getY() == pressedJ)))
             .forEach(Cell::setMineRandomly);
    }

    public List<Cell> getNeighbors(int x, int y) {
        return cells.stream().filter(cell -> cell.isNeighbour(x, y)).collect(Collectors.toList());
    }
}