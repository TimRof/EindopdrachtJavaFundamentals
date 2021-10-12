package nl.inholland.ui.panes;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import nl.inholland.data.Database;
import nl.inholland.model.Show;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PurchaseBorderPane extends BorderPane{
    private Show selectedShow;
    public PurchaseBorderPane(Database db) {
        this.setPadding(new Insets(5,5,5,5));

        Label purchaseLabel = new Label("Purchase Tickets");
        purchaseLabel.setPadding(new Insets(0, 0, 5, 0));

        purchaseLabel.getStyleClass().add("header");

        Label roomLabel = new Label("Room");
        roomLabel.setPadding(new Insets(10,50,10,0));
        Label roomNumberLabel = new Label("");
        roomNumberLabel.setPadding(new Insets(10,50,10,0));
        Label startLabel = new Label("Start");
        startLabel.setPadding(new Insets(10,50,10,0));
        Label startTimeLabel = new Label("");
        startTimeLabel.setPadding(new Insets(10,50,10,0));
        Label endLabel = new Label("End");
        endLabel.setPadding(new Insets(10,50,10,0));
        Label endTimeLabel = new Label("");
        endTimeLabel.setPadding(new Insets(10,50,10,0));
        Label movieLabel = new Label("Movie title");
        movieLabel.setPadding(new Insets(10,50,10,0));
        Label movieTitleLabel = new Label("");
        movieTitleLabel.setPadding(new Insets(10,50,10,0));
        Label seatsLabel = new Label("No. of seats");
        seatsLabel.setPadding(new Insets(10,50,10,0));
        ObservableList<Integer> seatsAmount = FXCollections.observableArrayList(0, 1, 2, 3,4,5,6,7,8);
        ComboBox seatsComboBox = new ComboBox(seatsAmount);
        seatsComboBox.getSelectionModel().selectFirst();
        Label nameLabel = new Label("Name");
        nameLabel.setPadding(new Insets(10,50,10,0));
        TextField nameTextField = new TextField();

        GridPane gridPanePurchase = new GridPane();
        gridPanePurchase.setPadding(new Insets(10,10,10,10));
        gridPanePurchase.add(roomLabel, 0, 0, 1, 1);
        gridPanePurchase.add(startLabel, 0,1,1,1);
        gridPanePurchase.add(endLabel, 0,2,1,1);

        gridPanePurchase.add(roomNumberLabel, 1, 0, 1, 1);
        gridPanePurchase.add(startTimeLabel, 1,1,1,1);
        gridPanePurchase.add(endTimeLabel, 1,2,1,1);

        gridPanePurchase.add(movieLabel, 2,0,1,1);
        gridPanePurchase.add(seatsLabel, 2,1,1,1);
        gridPanePurchase.add(nameLabel, 2,2,1,1);

        gridPanePurchase.add(movieTitleLabel, 3,0,1,1);
        gridPanePurchase.add(seatsComboBox, 3,1,1,1);
        gridPanePurchase.add(nameTextField, 3,2,1,1);
        gridPanePurchase.getStyleClass().add("purchasePane");

        RoomListGridPane roomListGridPane = new RoomListGridPane(db);
        roomListGridPane.getRoomOneTableView().setRowFactory(tv -> {
            TableRow<Show> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                selectedShow = row.getItem();
                    roomNumberLabel.setText("Room " + selectedShow.getRoomNumber());
                    startTimeLabel.setText(selectedShow.getStartTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
                    endTimeLabel.setText(selectedShow.getStartTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
                    movieTitleLabel.setText(selectedShow.getMovie().getTitle());
            });
            return row;
        });
        roomListGridPane.getRoomTwoTableView().setRowFactory(tv -> {
            TableRow<Show> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                selectedShow = row.getItem();
                roomNumberLabel.setText("Room " + selectedShow.getRoomNumber());
                startTimeLabel.setText(selectedShow.getStartTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
                endTimeLabel.setText(selectedShow.getStartTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
                movieTitleLabel.setText(selectedShow.getMovie().getTitle());
            });
            return row;
        });
        this.setTop(purchaseLabel);
        this.setCenter(roomListGridPane);
        this.setBottom(gridPanePurchase);
    }
}
