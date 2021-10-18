package nl.inholland.ui.panes;

import nl.inholland.data.Database;
import nl.inholland.model.User;
import nl.inholland.ui.windows.MainWindow;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginGridPane extends GridPane {
    public LoginGridPane(Database db, Stage stage) {
        setPadding(new Insets(10,10,10,10));
        setVgap(5);

        // labels, textFields and button
        Label welcomeLabel = new Label("Welcome!\nPlease sign in to continue.");
        Label badLogin = new Label("Bad credentials!");

        Label userLabel = new Label("Username");
        Label passLabel = new Label("Password");
        TextField userInput = new TextField();
        PasswordField passInput = new PasswordField();
        Button loginButton = new Button("Log in");
        loginButton.setPrefSize(100,20);
        getStyleClass().add("background");
        badLogin.setStyle("-fx-text-fill: red");

        // place nodes on gridPane
        GridPane.setConstraints(welcomeLabel, 0, 0);
        GridPane.setConstraints(badLogin, 0, 5);
        GridPane.setConstraints(userLabel, 0, 2);
        GridPane.setConstraints(passLabel, 0, 3);
        GridPane.setConstraints(userInput, 1, 2);
        GridPane.setConstraints(passInput, 1, 3);
        GridPane.setConstraints(loginButton, 0, 4);

        // hide badLogin label
        badLogin.setVisible(false);

        getChildren().addAll(userLabel, passLabel, userInput, passInput, loginButton, badLogin);

        // login functionality
        loginButton.setOnAction(actionEvent -> checkLogin(db, userInput, passInput, badLogin, stage));

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
                mw.getStage().setTitle("Fabulous Cinema  -- purchase tickets -- Welcome " + user.getFirstName() + " " + user.getLastName() + "! [" + user.getAccessLevel() + "]");
                mw.getStage().show();
                passInput.clear();
                passInput.requestFocus();
                stage.hide();
                return;
            }
        }
    }
}
