package ca.pragmaticcoding.minesweeper;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableBooleanValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.function.Consumer;

class CellView extends StackPane {
    private static final Image mineImage = new Image("/images/MineSmall.png");
    private static final Image flagImage = new Image("/images/Flag.png");

    CellView(CellModel model, Consumer<MouseButton> clickConsumer) {
        Text mineCountText = new Text("");
        ImageView mineImageView = new ImageView(mineImage);
        ImageView flagImageView = new ImageView(flagImage);
        flagImageView.setFitHeight(24);
        flagImageView.setFitWidth(24);
        getChildren().addAll(createRectangle(model.clickedProperty()), mineCountText, mineImageView, flagImageView);
        mineCountText.textProperty().bind(model.mineCountProperty());
        mineCountText.visibleProperty().bind(model.clickedProperty().and(model.isMineProperty().not()));
        mineImageView.visibleProperty().bind(model.clickedProperty().and(model.isMineProperty()));
        flagImageView.visibleProperty().bind(model.isFlagProperty().and(model.clickedProperty().not()));
        setOnMouseClicked(evt -> clickConsumer.accept(evt.getButton()));
    }

    private Rectangle createRectangle(ObservableBooleanValue clickedProperty) {
        Rectangle rectangle = new Rectangle(30, 30);
        rectangle.fillProperty()
                 .bind(Bindings.createObjectBinding(() -> clickedProperty.get() ? Color.WHITE : Color.GRAY, clickedProperty));
        rectangle.setStroke(Color.BLACK);
        return rectangle;
    }
}
