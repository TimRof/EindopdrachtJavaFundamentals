package nl.inholland.ui.panes;

import nl.inholland.data.Database;

import javafx.scene.layout.GridPane;

public class ManageMoviesGridPane extends GridPane {
    public ManageMoviesGridPane(Database db, InfoPane infoPane) {
        createNodes();
    }
    private void createNodes() {
        // stuff
        getStyleClass().add("contentPane");
    }
}
