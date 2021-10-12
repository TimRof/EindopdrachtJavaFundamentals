package nl.inholland.ui.windows;

import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.inholland.data.Database;
import nl.inholland.ui.StyledScene;
import nl.inholland.ui.panes.LoginGridPane;

public class LoginWindow {
    private Database db;
    private Stage stage;
    public Stage getStage(){ return stage; }

    public LoginWindow(Database db){
        this.db = db;

        stage = new Stage();
        stage.setTitle("Login");

        StyledScene loginScene = new StyledScene(new LoginGridPane(db, stage));

        stage.setScene(loginScene);
    }

}
