package nl.inholland.ui.windows;

import nl.inholland.data.Database;
import nl.inholland.model.User;
import nl.inholland.ui.StyledScene;
import nl.inholland.ui.panes.*;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainWindow {
    private final Stage stage;
    private final String titleString;
    private final String welcomeString;

    private final Label headerLabel;
    private final VBox mainVBox;
    private final PurchaseGridPane purchaseGridPane;
    private final ManageShowsGridPane manageShowsGridPane;
    private final ManageMoviesGridPane manageMoviesGridPane;
    public Stage getStage(){ return stage; }

    public MainWindow(Database db, User user, Stage lw) {
        titleString = "Fabulous Cinema";
        welcomeString = "Welcome " + user.getFirstName() + " " + user.getLastName() + "! [" + user.getAccessLevel() + "]";
        stage = new Stage();

        // make top menu + items
        TopMenuHBox topMenuHBox = new TopMenuHBox(this, user, lw, stage);

        // make header
        headerLabel = new Label("Purchase tickets");
        headerLabel.getStyleClass().add("header");

        // make listviews for rooms
        RoomListGridPane roomListGridPane = new RoomListGridPane(db);

        // make infoPane
        InfoPane infoPane = new InfoPane("Welcome " + user.getFirstName() + " " + user.getLastName() + "!");

        // make purchase pane
        purchaseGridPane = new PurchaseGridPane(db, roomListGridPane, infoPane, this);

        // make manage shows pane
        manageShowsGridPane = new ManageShowsGridPane(db, infoPane, roomListGridPane);

        // make manage movies pane
        manageMoviesGridPane = new ManageMoviesGridPane(db, infoPane);

        mainVBox = new VBox(topMenuHBox, headerLabel, roomListGridPane, purchaseGridPane, infoPane);

        VBox.setMargin(roomListGridPane, new Insets(10));
        VBox.setMargin(headerLabel, new Insets(10,10,0,10));
        VBox.setMargin(purchaseGridPane, new Insets(0,10,10,10));
        VBox.setMargin(manageShowsGridPane, new Insets(0,10,10,10));
        VBox.setMargin(manageMoviesGridPane, new Insets(0,10,10,10));
        VBox.setMargin(infoPane, new Insets(0,10,10,10));

        StyledScene dashboardScene = new StyledScene(mainVBox);

        stage.setScene(dashboardScene);
    }
    public void setPurchaseView(){
        stage.setTitle(titleString + " -- purchase tickets -- " + welcomeString);
        headerLabel.setText("Purchase tickets");
        setView(purchaseGridPane);
    }
    public void setManageShowsView(){
        stage.setTitle(titleString + " -- manage showings -- " + welcomeString);
        headerLabel.setText("Manage showings");
        manageShowsGridPane.refresh();
        setView(manageShowsGridPane);
    }
    public void setManageMoviesView(){
        stage.setTitle(titleString + " -- manage movies -- " + welcomeString);
        headerLabel.setText("Manage movies");
        setView(manageMoviesGridPane);
    }
    private void setView(GridPane pane){
        if (mainVBox.getChildren().get(mainVBox.getChildren().size()-2).equals(pane))
            return;
        else
        {
            int size = mainVBox.getChildren().size()-2;
            mainVBox.getChildren().set(size, pane);
        }
    }
}
