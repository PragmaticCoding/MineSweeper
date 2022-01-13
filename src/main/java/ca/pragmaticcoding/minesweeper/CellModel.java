package ca.pragmaticcoding.minesweeper;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

class CellModel {

    private final BooleanProperty clicked = new SimpleBooleanProperty(false);
    private final BooleanProperty isMine = new SimpleBooleanProperty(false);
    private final BooleanProperty isFlag = new SimpleBooleanProperty(false);
    private final StringProperty mineCount = new SimpleStringProperty("");

    public BooleanProperty clickedProperty() {
        return clicked;
    }

    public BooleanProperty isMineProperty() {
        return isMine;
    }

    public BooleanProperty isFlagProperty() {
        return isFlag;
    }

    public StringProperty mineCountProperty() {
        return mineCount;
    }
}
