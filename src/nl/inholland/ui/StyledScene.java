package nl.inholland.ui;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class StyledScene extends Scene {
    public StyledScene(Parent parent) {
        super(parent);
        getStylesheets().add("resources/style.css");
    }

}
