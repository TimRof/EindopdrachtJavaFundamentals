package nl.inholland.ui.windows;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import nl.inholland.data.Database;
import nl.inholland.model.AccessLevel;
import nl.inholland.model.User;
import nl.inholland.ui.panes.LoginMenu;

public class LoginWindow {
    private Database db;
    private Stage stage;
    public Stage getStage(){ return stage; }

    public LoginWindow(Database db){
        this.db = db;

        stage = new Stage();
        stage.setTitle("Login");

        Scene loginScene = new Scene(new LoginMenu(db, stage));

        stage.setScene(loginScene);
    }

}
