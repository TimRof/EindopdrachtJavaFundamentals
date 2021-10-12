package nl.inholland.ui.windows;

import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Scene;

import nl.inholland.data.Database;
import nl.inholland.model.User;
import nl.inholland.ui.panes.PurchaseBorderPane;
import nl.inholland.ui.panes.TopMenuHBox;

public class MainWindow {
    private Database db;
    private Stage stage;
    private User loggedInUser;
    private Stage oldStage;
    public Stage getStage(){ return stage; }
    public MainWindow(Database db, User user, Stage lw) {
        this.db = db;
        this.loggedInUser = user;
        this.oldStage = lw;
        stage = new Stage();
        stage.setTitle("Cinema Dashboard -- Welcome " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName() + "! [" + loggedInUser.getAccessLevel() + "]");

        // make top menu + items
        TopMenuHBox TopMenuHBox = new TopMenuHBox(loggedInUser, oldStage, stage);
        // make listviews for rooms
        PurchaseBorderPane purchaseTickets = new PurchaseBorderPane(db);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(TopMenuHBox);
        borderPane.setCenter(purchaseTickets);

        Scene dashboardScene = new Scene(borderPane);

        stage.setScene(dashboardScene);
    }
}
