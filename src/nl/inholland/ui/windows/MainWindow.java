package nl.inholland.ui.windows;

import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;
import nl.inholland.data.Database;
import nl.inholland.model.Show;
import nl.inholland.model.User;
import nl.inholland.ui.StyledScene;
import nl.inholland.ui.dialogs.ExitDialog;
import nl.inholland.ui.panes.*;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MainWindow {
    private final Stage stage;
    private final String titleString;
    private final String welcomeString;
    private final Database db;
    private final InfoPane infoPane;
    private static final String CSV_SEPARATOR = ","; // or use ;

    private final Label headerLabel;
    private final VBox mainVBox;
    private final PurchaseGridPane purchaseGridPane;
    private final ManageShowsGridPane manageShowsGridPane;
    private final ManageMoviesGridPane manageMoviesGridPane;
    public Stage getStage(){ return stage; }

    public MainWindow(Database db, User user, Stage lw) {
        titleString = "Fabulous Cinema";
        welcomeString = "Welcome " + user.getFirstName() + " " + user.getLastName() + "! [" + user.getAccessLevel() + "]";
        this.db = db;
        stage = new Stage();

        // make top menu + items
        TopMenuHBox topMenuHBox = new TopMenuHBox(this, user, lw, stage);

        // make header
        headerLabel = new Label("Purchase tickets");
        headerLabel.getStyleClass().add("header");

        // make listviews for rooms
        RoomListVBox roomListVBox = new RoomListVBox(db);

        // make infoPane
        infoPane = new InfoPane("Welcome " + user.getFirstName() + " " + user.getLastName() + "!");

        // make purchase pane
        purchaseGridPane = new PurchaseGridPane(db, roomListVBox, infoPane, this);

        // make manage shows pane
        manageShowsGridPane = new ManageShowsGridPane(db, infoPane, roomListVBox);

        // make manage movies pane
        manageMoviesGridPane = new ManageMoviesGridPane(db, infoPane);

        mainVBox = new VBox(topMenuHBox, headerLabel, roomListVBox, purchaseGridPane, infoPane);

        VBox.setMargin(roomListVBox, new Insets(10));
        VBox.setMargin(headerLabel, new Insets(10,10,0,10));
        VBox.setMargin(purchaseGridPane, new Insets(0,10,10,10));
        VBox.setMargin(manageShowsGridPane, new Insets(0,10,10,10));
        VBox.setMargin(manageMoviesGridPane, new Insets(0,10,10,10));
        VBox.setMargin(infoPane, new Insets(0,10,10,10));

        StyledScene dashboardScene = new StyledScene(mainVBox);

        stage.setOnCloseRequest(windowEvent ->
        {
            ExitDialog exitDialog = new ExitDialog("INFO");
            exitDialog.showAndWait();
            if (!exitDialog.isOkPressed())
                windowEvent.consume();
        });

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
    public void exportShowings()
    {
        try
        {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Comma Separated Value File", "*.csv"));
            fileChooser.setInitialFileName("shows.csv");
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                saveToFile(file);
            }

        }
        catch(Exception exception){
            infoPane.showInfoMessage("Something went wrong (" + exception.getMessage() + ")");
        }
    }
    private void saveToFile(File file){
        try
        {
            DecimalFormat df = new DecimalFormat("#.00");
            List<Show> shows = db.getShows();
            FileWriter csvWriter = new FileWriter(file);
            for (Show s : shows)
            {
                String startTime = s.getStartTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
                csvWriter.append(startTime);
                csvWriter.append(CSV_SEPARATOR);
                csvWriter.append(s.getEndTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
                csvWriter.append(CSV_SEPARATOR);
                csvWriter.append("Room " + s.getRoomNumber());
                csvWriter.append(CSV_SEPARATOR);
                csvWriter.append(s.getMovie().getTitle());
                csvWriter.append(CSV_SEPARATOR);
                csvWriter.append(String.valueOf(s.getAvailableTickets()));
                csvWriter.append(CSV_SEPARATOR);
                csvWriter.append(df.format(s.getMovie().getPrice()));
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
            infoPane.showInfoMessage("Successfully saved to file " + file.getName() + "!");

        }
        catch(Exception exception){
            infoPane.showInfoMessage("Something went wrong (" + exception.getMessage() + ")");
        }
    }
}
