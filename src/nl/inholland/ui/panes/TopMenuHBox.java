package nl.inholland.ui.panes;

import nl.inholland.model.AccessLevel;
import nl.inholland.model.User;
import nl.inholland.ui.dialogs.ConfirmDialog;
import nl.inholland.ui.windows.MainWindow;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TopMenuHBox extends HBox{
    public TopMenuHBox(MainWindow mainWindow, User loggedInUser, Stage oldStage, Stage stage) {
        setPadding(new Insets(5));

        Menu helpMenu = makeHelpMenu();
        Menu logoutMenu = makeLogoutMenu(oldStage, stage);

        MenuBar menuBar = new MenuBar();
        if (loggedInUser.getAccessLevel().equals(AccessLevel.Admin))
            menuBar.getMenus().add(makeAdminMenu(loggedInUser, mainWindow));

        menuBar.getMenus().addAll(helpMenu, logoutMenu);

        getStyleClass().add("topMenu");

        getChildren().add(menuBar);
    }
    private Menu makeAdminMenu(User loggedInUser, MainWindow mainWindow){
        // admin menu
        Menu adminMenu = new Menu(loggedInUser.getAccessLevel().toString());

        MenuItem manageShowingsItem = new MenuItem("Manage showings");
        MenuItem manageMoviesItem = new MenuItem("Manage movies");

        manageShowingsItem.setOnAction(e -> mainWindow.setManageShowsView());
        manageMoviesItem.setOnAction(e -> mainWindow.setManageMoviesView());

        adminMenu.getItems().addAll(manageShowingsItem, manageMoviesItem);

        /*MenuItem purchaseItem = new MenuItem("Purchase tickets");
        purchaseItem.setOnAction(e -> mainWindow.setPurchaseView()); // no purchase button allowed?
        adminMenu.getItems().add(purchaseItem);*/

        return adminMenu;
    }
    private Menu makeHelpMenu(){
        // about menu item
        Menu helpMenu = new Menu("Help");
        MenuItem aboutItem = new MenuItem("About");
        helpMenu.getItems().add(aboutItem);

        return helpMenu;
    }
    private Menu makeLogoutMenu(Stage oldStage, Stage stage){
        // log out menu item
        Menu logoutMenu = new Menu("Log out");
        MenuItem logoutItem = new MenuItem("Logout...");

        logoutMenu.getItems().add(logoutItem);

        logoutItem.setOnAction(e -> {
            ConfirmDialog confirmDialog = new ConfirmDialog(Alert.AlertType.CONFIRMATION, "Confirm logout", "Logging out");
            if (confirmDialog.getResult() != ButtonType.YES) {
                confirmDialog.close();
            } else {
                oldStage.show();
                stage.close();
            }
        });

        return logoutMenu;
    }
}
