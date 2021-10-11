package nl.inholland.ui.panes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import nl.inholland.model.AccessLevel;
import nl.inholland.model.User;
import nl.inholland.ui.dialogs.LogoutDialog;

public class TopMenuItems extends HBox{
    public TopMenuItems(User loggedInUser, Stage oldStage, Stage stage) {

        this.setPadding(new Insets(13,13,13,13));
        this.setSpacing(10);
        this.setStyle("-fx-background-color: #464854");

        // user/admin menu items
        MenuItem manageShowingsMenu = new MenuItem("Manage showings");
        MenuItem manageMoviesMenu = new MenuItem("Manage movies");
        MenuItem profileMenuItem = new MenuItem("Profile");
        MenuButton adminMenuButton = new MenuButton(loggedInUser.getAccessLevel().toString(), null, profileMenuItem);
        if (loggedInUser.getAccessLevel().equals(AccessLevel.Admin))
            adminMenuButton.getItems().addAll(manageShowingsMenu, manageMoviesMenu);
        adminMenuButton.setPrefSize(100,20);

        // about menu item
        MenuItem aboutMenu = new MenuItem("About");
        MenuButton helpMenuButton = new MenuButton("Help", null, aboutMenu);
        helpMenuButton.setPrefSize(100,20);

        // log out menu item
        Button logoutButton = new Button("Log out");
        logoutButton.setPrefSize(100,20);

        // log out functionality
        logoutButton.setOnAction(actionEvent -> {
            new LogoutDialog(Alert.AlertType.CONFIRMATION, oldStage, stage);
        });

        this.getChildren().addAll(adminMenuButton, helpMenuButton,logoutButton);
    }
}
