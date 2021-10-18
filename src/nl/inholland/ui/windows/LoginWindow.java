package nl.inholland.ui.windows;

import nl.inholland.data.Database;
import nl.inholland.ui.StyledScene;
import nl.inholland.ui.panes.LoginGridPane;

import javafx.stage.Stage;

public class LoginWindow {
    private final Stage stage;
    public Stage getStage(){ return stage; }

    public LoginWindow(Database db){
        stage = new Stage();
        stage.setTitle("Login");

        StyledScene loginScene = new StyledScene(new LoginGridPane(db, stage));

        stage.setScene(loginScene);
    }

}
