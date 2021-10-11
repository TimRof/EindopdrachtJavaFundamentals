package nl.inholland.ui.panes;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.inholland.data.Database;
import nl.inholland.model.Movie;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RoomListViews extends BorderPane{
    private Database db;
    private TableView<Movie> roomOneTableView;
    private TableView<Movie> roomTwoTableView;
    public RoomListViews(Database db) {
        this.db = db;
        roomOneTableView = new TableView<>();
        roomTwoTableView = new TableView<>();

        Label purchaseLabel = new Label("Purchase Tickets");
        purchaseLabel.setTextFill(Color.ROYALBLUE);
        purchaseLabel.setStyle("-fx-font: 24 arial");
        VBox roomOne = new VBox();
        VBox roomTwo = new VBox();

        // format for price
        DecimalFormat df = new DecimalFormat("#,00");

        // make columns
        TableColumn<Movie, LocalDateTime> startOneColumn = new TableColumn<>("Start");
        startOneColumn.setMinWidth(100);
        startOneColumn.setCellValueFactory(m -> new SimpleObjectProperty(m.getValue().getStartTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))));

        TableColumn<Movie, LocalDateTime> endOneColumn = new TableColumn<>("End");
        endOneColumn.setMinWidth(100);
        endOneColumn.setCellValueFactory(m -> new SimpleObjectProperty(m.getValue().getEndTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))));

        TableColumn<Movie, String> titleOneColumn = new TableColumn<>("Title");
        titleOneColumn.setMinWidth(130);
        titleOneColumn.setCellValueFactory(new PropertyValueFactory<>("movieTitle"));

        TableColumn<Movie, Integer> ticketsOneColumn = new TableColumn<>("Seats");
        ticketsOneColumn.setMinWidth(50);
        ticketsOneColumn.setCellValueFactory(m -> new SimpleObjectProperty<>(m.getValue().getAvailableSeats()));

        TableColumn<Movie, Double> priceOneColumn = new TableColumn<>("Price");
        priceOneColumn.setMinWidth(50);
        priceOneColumn.setCellValueFactory(m -> new SimpleObjectProperty(df.format(m.getValue().getPrice())));

        TableColumn<Movie, LocalDateTime> startTwoColumn = new TableColumn<>("Start");
        startTwoColumn.setMinWidth(100);
        startTwoColumn.setCellValueFactory(m -> new SimpleObjectProperty(m.getValue().getStartTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))));

        TableColumn<Movie, LocalDateTime> endTwoColumn = new TableColumn<>("End");
        endTwoColumn.setMinWidth(100);
        endTwoColumn.setCellValueFactory(m -> new SimpleObjectProperty(m.getValue().getEndTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))));

        TableColumn<Movie, String> titleTwoColumn = new TableColumn<>("Title");
        titleTwoColumn.setMinWidth(130);
        titleTwoColumn.setCellValueFactory(new PropertyValueFactory<>("movieTitle"));

        TableColumn<Movie, Integer> ticketsTwoColumn = new TableColumn<>("Seats");
        ticketsTwoColumn.setMinWidth(50);
        ticketsTwoColumn.setCellValueFactory(m -> new SimpleObjectProperty<>(m.getValue().getAvailableSeats()));

        TableColumn<Movie, Double> priceTwoColumn = new TableColumn<>("Price");
        priceTwoColumn.setMinWidth(50);
        priceTwoColumn.setCellValueFactory(m -> new SimpleObjectProperty(df.format(m.getValue().getPrice())));

        roomOneTableView.getColumns().addAll(startOneColumn,endOneColumn,titleOneColumn,ticketsOneColumn, priceOneColumn);
        roomTwoTableView.getColumns().addAll(startTwoColumn,endTwoColumn,titleTwoColumn,ticketsTwoColumn, priceTwoColumn);

        // fill table views
        refreshLists();

        roomOne.getChildren().add(roomOneTableView);
        roomTwo.getChildren().add(roomTwoTableView);

        roomOne.setPrefSize(432,400);
        roomTwo.setPrefSize(432,400);

        Label roomOneLabel = new Label("Room 1");
        roomOneLabel.setStyle("-fx-font-weight: bold");
        Label roomTwoLabel = new Label("Room 2");
        roomTwoLabel.setStyle("-fx-font-weight: bold");

        GridPane gridPaneRooms = new GridPane();

        gridPaneRooms.setStyle("-fx-border-color: ROYALBLUE");
        this.setPadding(new Insets(5,5,5,5));
        gridPaneRooms.setPadding(new Insets(0,0,0,0));

        gridPaneRooms.add(roomOneLabel, 0,0,1,1);
        gridPaneRooms.add(roomOne, 0,1,1,1);
        gridPaneRooms.add(roomTwoLabel, 1,0,1,1);
        gridPaneRooms.add(roomTwo, 1,1,1,1);

        GridPane.setMargin(roomOne, new Insets(0,0,0,5));
        GridPane.setMargin(roomTwo, new Insets(0,5,5,5));

        GridPane.setMargin(roomOneLabel, new Insets(5,0,0,5));
        GridPane.setMargin(roomTwoLabel, new Insets(5,0,0,5));

        this.setTop(purchaseLabel);
        this.setCenter(gridPaneRooms);
    }
    public void refreshLists(){
        roomOneTableView.getItems().clear();
        roomTwoTableView.getItems().clear();
        roomOneTableView.setItems(db.getRoomMovies(1));
        roomTwoTableView.setItems(db.getRoomMovies(2));
    }
}
