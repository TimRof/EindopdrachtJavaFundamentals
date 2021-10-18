package nl.inholland.ui.panes;

import nl.inholland.data.Database;
import nl.inholland.model.Show;
import nl.inholland.ui.dialogs.ConfirmDialog;
import nl.inholland.ui.exceptions.NoNameException;
import nl.inholland.ui.exceptions.NoSeatsException;
import nl.inholland.ui.windows.MainWindow;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

public class PurchaseGridPane extends GridPane{
    private Show selectedShow;
    private Label roomNumberLabel;
    private Label startTimeLabel;
    private Label endTimeLabel;
    private Label movieTitleLabel;
    private ComboBox<Integer> seatsComboBox;
    private Button purchaseButton;
    private Button clearButton;
    private TextField nameTextField;
    private final int maxSeats = 8; // max seats combobox

    public PurchaseGridPane(Database db, RoomListGridPane roomListGridPane, InfoPane infoPane, MainWindow mainWindow) {
        createNodes();

        // row selection tableViewOne
        roomListGridPane.getRoomOneTableView().setRowFactory(tv -> {
            TableRow<Show> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(!row.isEmpty()) {
                    showSelected(mainWindow, row);
                }
            });
            return row;
        });

        // row selection tableViewTwo
        roomListGridPane.getRoomTwoTableView().setRowFactory(tv -> {
            TableRow<Show> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(!row.isEmpty()) {
                    showSelected(mainWindow, row);
                }
            });
            return row;
        });

        // purchase button click
        purchaseButton.setOnAction(actionEvent -> {
            if (selectedShow!=null) {
                purchaseTickets(db, roomListGridPane, infoPane);
            }
        });

        // clear button click
        clearButton.setOnAction(actionEvent -> clearFields(roomListGridPane, infoPane));

        // name max characters 25
        nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (nameTextField.getText().length() > 25){
                String s = nameTextField.getText().substring(0, 25);
                nameTextField.setText(s);
            }
        });
    }

    private void setSeatsComboBox(ComboBox<Integer> seatsComboBox){
        // fills combobox with available seats
        int availableSeats = maxSeats;
        seatsComboBox.getItems().clear();
        if (availableSeats > selectedShow.getAvailableTickets())
        {
            for (int i = 0; i < selectedShow.getAvailableTickets()+1; i++) {
                seatsComboBox.getItems().add(i);
            }
        }
        else{
            for (int i = 0; i < availableSeats +1; i++) {
                seatsComboBox.getItems().add(i);
            }
        }
        seatsComboBox.getSelectionModel().selectFirst();
    }
    private void createNodes() {
        // Create Labels, ComboBox, TextField and Buttons

        Label roomLabel = new Label("Room");
        roomLabel.setPadding(new Insets(5, 0,0,0));
        roomNumberLabel = new Label("");

        Label startLabel = new Label("Start");
        startTimeLabel = new Label("");

        Label endLabel = new Label("End");
        endTimeLabel = new Label("");

        Label movieLabel = new Label("Movie title");
        movieTitleLabel = new Label("");

        Label seatsLabel = new Label("No. of seats");
        seatsComboBox = new ComboBox<>();
        seatsComboBox.getSelectionModel().selectFirst();

        Label nameLabel = new Label("Name");
        nameTextField = new TextField();

        purchaseButton = new Button("Purchase");
        clearButton = new Button("Clear");
        purchaseButton.setPrefSize(120, 20);
        clearButton.setPrefSize(120, 20);

        setPadding(new Insets(10));
        setHgap(50);
        setVgap(5);
        add(roomLabel, 0, 0, 1, 1);
        add(startLabel, 0, 1, 1, 1);
        add(endLabel, 0, 2, 1, 1);

        add(roomNumberLabel, 1, 0, 1, 1);
        add(startTimeLabel, 1, 1, 1, 1);
        add(endTimeLabel, 1, 2, 1, 1);

        add(movieLabel, 2, 0, 1, 1);
        add(seatsLabel, 2, 1, 1, 1);
        add(nameLabel, 2, 2, 1, 1);

        add(movieTitleLabel, 3, 0, 1, 1);
        add(seatsComboBox, 3, 1, 1, 1);
        add(nameTextField, 3, 2, 1, 1);

        add(purchaseButton, 4, 1, 1, 1);
        add(clearButton, 4, 2, 1, 1);

        getStyleClass().add("contentPane");

        hidePane();
    }
    private void hidePane(){
        // hides purchasePane
        seatsComboBox.getSelectionModel().selectFirst();
        nameTextField.clear();
        selectedShow = null;
        setVisible(false);
    }
    private void fillLabels(){
        // shows and fills purchasePane
        setVisible(true);
        roomNumberLabel.setText("Room " + selectedShow.getRoomNumber());
        startTimeLabel.setText(selectedShow.getStartTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        endTimeLabel.setText(selectedShow.getEndTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        movieTitleLabel.setText(selectedShow.getMovie().getTitle());
    }
    private void purchaseTickets(Database db, RoomListGridPane roomListGridPane, InfoPane infoPane){
        try {
            int amount = Integer.parseInt(seatsComboBox.getValue().toString());
            if (amount < 1) // if seats = 0 throw exception
                throw new NoSeatsException();
            else if (nameTextField.getText().isEmpty()) // if name is empty throw exception
                throw new NoNameException();

            String ticket; // check amount for plural
            if (amount > 1)
                ticket = "tickets";
            else
                ticket = "ticket";

            String dialogTitle = "Buying tickets for '" + nameTextField.getText() + "'";
            DecimalFormat df = new DecimalFormat("#.00");
            String dialogHeader = "You are about to buy " + amount + " " + ticket + ", for a total of €" + df.format(amount*selectedShow.getMovie().getPrice());

            ConfirmDialog confirmDialog = new ConfirmDialog(Alert.AlertType.CONFIRMATION, dialogTitle, dialogHeader);
            if (confirmDialog.getResult() == ButtonType.YES)
            {
                selectedShow = db.buyTickets(selectedShow, amount, nameTextField.getText());
                roomListGridPane.refreshLists();
                infoPane.showInfoMessage("Successfully bought " + amount + " " + ticket + "!");
                hidePane();
            }
            else
                infoPane.showInfoMessage("No tickets bought.");
        } catch (Exception e){
            infoPane.showInfoMessage("No tickets bought. (" + e.getMessage() + ")");
        }
    }
    private void clearFields(RoomListGridPane roomListGridPane, InfoPane infoPane){
        hidePane();
        roomListGridPane.refreshLists();
        infoPane.showInfoMessage("Cleared!");
    }
    private void showSelected(MainWindow mainWindow, TableRow<Show> row){
        mainWindow.setPurchaseView();
        selectedShow = row.getItem();
        fillLabels();
        seatsComboBox.getItems().clear();
        setSeatsComboBox(seatsComboBox);
    }
}
