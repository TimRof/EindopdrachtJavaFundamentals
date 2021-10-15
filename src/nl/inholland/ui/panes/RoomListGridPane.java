package nl.inholland.ui.panes;

import nl.inholland.data.Database;
import nl.inholland.model.Show;

import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RoomListGridPane extends GridPane {
    private final Database db;
    private final TableView<Show> roomOneTableView;
    private final TableView<Show> roomTwoTableView;

    public TableView<Show> getRoomOneTableView() {
        return roomOneTableView;
    }

    public TableView<Show> getRoomTwoTableView() {
        return roomTwoTableView;
    }

    public RoomListGridPane(Database db) {
        this.db = db;
        roomOneTableView = new TableView<>();
        roomTwoTableView = new TableView<>();

        // make columns
        makeColumns(roomOneTableView);
        makeColumns(roomTwoTableView);

        // fill table views
        refreshLists();

        VBox roomOneVBox = new VBox();
        roomOneVBox.getChildren().add(roomOneTableView);
        VBox roomTwoVBox = new VBox();
        roomTwoVBox.getChildren().add(roomTwoTableView);

        roomOneVBox.setPrefSize(600,400);
        roomTwoVBox.setPrefSize(600,400);

        Label roomOneLabel = new Label("Room 1");
        roomOneLabel.setStyle("-fx-text-fill: #00325a");
        Label roomTwoLabel = new Label("Room 2");
        roomTwoLabel.setStyle("-fx-text-fill: #00325a");

        add(roomOneLabel, 0,0,1,1);
        add(roomOneVBox, 0,1,1,1);
        add(roomTwoLabel, 1,0,1,1);
        add(roomTwoVBox, 1,1,1,1);

        setStyle("-fx-border-color: #00325a");

        setMargin(roomOneVBox, new Insets(0,0,0,5));
        setMargin(roomTwoVBox, new Insets(0,5,5,5));

        setMargin(roomOneLabel, new Insets(5,0,0,5));
        setMargin(roomTwoLabel, new Insets(5,0,0,5));
    }

    private void makeColumns(TableView<Show> tableView){
        // format for price
        DecimalFormat df = new DecimalFormat("#.00");

        // make columns
        TableColumn<Show, LocalDateTime> startOneColumn = new TableColumn<>("Start");
        startOneColumn.setMinWidth(100);
        startOneColumn.setCellValueFactory(m -> new SimpleObjectProperty(m.getValue().getStartTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))));

        TableColumn<Show, LocalDateTime> endOneColumn = new TableColumn<>("End");
        endOneColumn.setMinWidth(100);
        endOneColumn.setCellValueFactory(m -> new SimpleObjectProperty(m.getValue().getEndTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))));

        TableColumn<Show, String> titleOneColumn = new TableColumn<>("Title");
        titleOneColumn.setMinWidth(280
        );
        titleOneColumn.setCellValueFactory(m -> new SimpleObjectProperty(m.getValue().getMovie().getTitle()));

        TableColumn<Show, Integer> ticketsOneColumn = new TableColumn<>("Seats");
        ticketsOneColumn.setMinWidth(59);
        ticketsOneColumn.setCellValueFactory(m -> new SimpleObjectProperty<>(m.getValue().getAvailableTickets()));

        TableColumn<Show, Double> priceOneColumn = new TableColumn<>("Price");
        priceOneColumn.setMinWidth(59);
        priceOneColumn.setCellValueFactory(m -> new SimpleObjectProperty(df.format(m.getValue().getMovie().getPrice())));

        tableView.getColumns().addAll(startOneColumn,endOneColumn,titleOneColumn,ticketsOneColumn, priceOneColumn);
    }
    public void refreshLists(){
        roomOneTableView.getItems().clear();
        roomTwoTableView.getItems().clear();
        roomOneTableView.setItems(db.getRoomShows(1).getShows());
        roomTwoTableView.setItems(db.getRoomShows(2).getShows());
    }
}
