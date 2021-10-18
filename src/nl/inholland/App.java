package nl.inholland;

import javafx.application.Application;
import javafx.stage.Stage;
import nl.inholland.data.Database;
import nl.inholland.ui.windows.LoginWindow;

public class App extends Application {

    public static void main(String[] args) { launch(args); }
    @Override
    public void start(Stage stage) {
        // rendering the text more crisply
        System.setProperty("prism.lcdtext", "false");
        // create database
        Database db = new Database();

        // open LoginWindow
        new LoginWindow(db).getStage().show();
    }
}
