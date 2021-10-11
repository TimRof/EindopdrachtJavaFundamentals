package nl.inholland.ui.windows;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;

import nl.inholland.data.Database;
import nl.inholland.model.Movie;
import nl.inholland.model.User;
import nl.inholland.ui.panes.RoomListViews;
import nl.inholland.ui.panes.TopMenuItems;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        TopMenuItems TopMenuItems = new TopMenuItems(loggedInUser, oldStage, stage);
        // make listviews for rooms
        RoomListViews purchaseTickets = new RoomListViews(db);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(TopMenuItems);
        borderPane.setCenter(purchaseTickets);

        Scene dashboardScene = new Scene(borderPane);

        stage.setScene(dashboardScene);
    }
}
