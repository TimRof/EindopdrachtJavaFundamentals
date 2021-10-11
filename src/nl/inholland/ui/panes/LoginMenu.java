package nl.inholland.ui.panes;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import nl.inholland.data.Database;
import nl.inholland.model.User;
import nl.inholland.ui.windows.MainWindow;

public class LoginMenu extends GridPane {
    public LoginMenu(Database db, Stage stage) {
        this.setPadding(new Insets(10,10,10,10));
        this.setVgap(5);

        // labels, textfields and button
        Label welcomeLabel = new Label("Welcome!\nPlease sign in to continue.");
        Label badLogin = new Label("Bad credentials!");

        Label userLabel = new Label("Username: ");
        Label passLabel = new Label("Password: ");
        TextField userInput = new TextField();
        PasswordField passInput = new PasswordField();
        Button loginButton = new Button("Log in");
        loginButton.setPrefSize(100,20);
        badLogin.setTextFill(Color.color(1,0,0));

        // place nodes on gridpane
        GridPane.setConstraints(welcomeLabel, 0, 0);
        GridPane.setConstraints(badLogin, 0, 1);
        GridPane.setConstraints(userLabel, 0, 2);
        GridPane.setConstraints(passLabel, 0, 3);
        GridPane.setConstraints(userInput, 1, 2);
        GridPane.setConstraints(passInput, 1, 3);
        GridPane.setConstraints(loginButton, 0, 4);

        // hide badLogin label
        badLogin.setVisible(false);

        this.getChildren().addAll(userLabel, passLabel, userInput, passInput, loginButton, badLogin, welcomeLabel);

        // login functionality
        loginButton.setOnAction(actionEvent -> {
            checkLogin(db, userInput, passInput, badLogin, stage);
        });

        userInput.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER))
                checkLogin(db, userInput, passInput, badLogin, stage);
        });
        passInput.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER))
                checkLogin(db, userInput, passInput, badLogin, stage);
        });
    }
    private void checkLogin(Database db, TextField userInput, TextField passInput, Label badLogin, Stage stage){
        for (User user:db.getUsers()) {
            if(!user.getUsername().equals(userInput.getText()))
            {
                badLogin.setVisible(true);
                continue;
            }
            if (user.getPassword().equals(passInput.getText()))
            {
                badLogin.setVisible(false);
                MainWindow mw = new MainWindow(db, user, stage);
                mw.getStage().show();
                passInput.clear();
                passInput.requestFocus();
                stage.hide();
                return;
            }
        }
    }
}
