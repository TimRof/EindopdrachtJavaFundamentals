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

public class RoomListVBox extends VBox {
    private final Database db;
    private final TableView<Show> roomOneTableView;
    private final TableView<Show> roomTwoTableView;

    public TableView<Show> getRoomOneTableView() {
        return roomOneTableView;
    }

    public TableView<Show> getRoomTwoTableView() {
        return roomTwoTableView;
    }

    public RoomListVBox(Database db) {
        this.db = db;
        roomOneTableView = new TableView<>();
        roomTwoTableView = new TableView<>();

        // make columns
        makeColumns(roomOneTableView);
        makeColumns(roomTwoTableView);

        // fill table views
        refreshLists();

        TextField searchTextField = new TextField();
        searchTextField.textProperty().addListener(t -> {
            try {
                if (searchTextField.textProperty().length().get() > 1)
                {
                    refreshSearchLists(searchTextField.getText());
                }
                else{
                    refreshLists();
                }
            }
            catch (Exception ignored){}
        });


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

        GridPane roomOverview = new GridPane();


        roomOverview.add(roomOneLabel, 2,0,1,1);
        roomOverview.add(roomOneVBox, 2,1,1,1);
        roomOverview.add(roomTwoLabel, 3,0,1,1);
        roomOverview.add(roomTwoVBox, 3,1,1,1);
        getChildren().addAll(searchTextField, roomOverview);

        setStyle("-fx-border-color: #00325a");

        setMargin(searchTextField,new Insets(10,0,0,0));

        setMargin(roomOneVBox, new Insets(0,0,0,10));
        setMargin(roomTwoVBox, new Insets(0,10,10,10));

        setMargin(roomOneLabel, new Insets(10,0,0,10));
        setMargin(roomTwoLabel, new Insets(10,0,0,10));
    }

    private void makeColumns(TableView<Show> tableView){
        // format for price
        DecimalFormat df = new DecimalFormat("#.00");

        // make columns
        TableColumn<Show, LocalDateTime> startColumn = new TableColumn<>("Start");
        startColumn.setMinWidth(100);
        startColumn.setCellValueFactory(m -> new SimpleObjectProperty(m.getValue().getStartTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))));

        TableColumn<Show, LocalDateTime> endColumn = new TableColumn<>("End");
        endColumn.setMinWidth(100);
        endColumn.setCellValueFactory(m -> new SimpleObjectProperty(m.getValue().getEndTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))));

        TableColumn<Show, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setMinWidth(280
        );
        titleColumn.setCellValueFactory(m -> new SimpleObjectProperty(m.getValue().getMovie().getTitle()));

        TableColumn<Show, Integer> ticketsColumn = new TableColumn<>("Seats");
        ticketsColumn.setMinWidth(59);
        ticketsColumn.setCellValueFactory(m -> new SimpleObjectProperty<>(m.getValue().getAvailableTickets()));

        TableColumn<Show, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setMinWidth(59);
        priceColumn.setCellValueFactory(m -> new SimpleObjectProperty(df.format(m.getValue().getMovie().getPrice())));

        tableView.getColumns().addAll(startColumn,endColumn,titleColumn,ticketsColumn, priceColumn);
    }
    public void refreshLists(){
        roomOneTableView.getItems().clear();
        roomTwoTableView.getItems().clear();
        roomOneTableView.setItems(db.getRoomShows(1).getShows());
        roomTwoTableView.setItems(db.getRoomShows(2).getShows());
    }
    public void refreshSearchLists(String filter){
        roomOneTableView.getItems().clear();
        roomTwoTableView.getItems().clear();
        roomOneTableView.setItems(db.getRoomShows(1).getFilteredShows(filter));
        roomTwoTableView.setItems(db.getRoomShows(2).getFilteredShows(filter));
    }
}
