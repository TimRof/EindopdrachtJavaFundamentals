package nl.inholland.ui.panes;

import nl.inholland.data.Database;
import nl.inholland.model.Movie;
import nl.inholland.model.Room;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import javafx.util.converter.DateTimeStringConverter;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ManageShowsGridPane extends GridPane {
    private Database db;
    private ComboBox<Movie> titleCombo;
    private ComboBox<Room> roomCombo;
    private Label seatsAmountLabel;
    private DatePicker startDatePicker;
    private TextField timeText;
    private Label endTimeLabel;
    private Label priceAmountLabel;
    Button addButton;
    Button clearButton;
    public ManageShowsGridPane(Database db, InfoPane infoPane) {
        this.db = db;
        createNodes();
        fillCombos();

        // set price & end time
        titleCombo.setOnAction(actionEvent -> {
            if (!titleCombo.getSelectionModel().isEmpty()) {
                DecimalFormat df = new DecimalFormat("#.00");
                priceAmountLabel.setText(df.format(titleCombo.getValue().getPrice()));
                setEndTime();
            }
        });

        // set seats for room
        roomCombo.setOnAction(actionEvent -> {
            if (!roomCombo.getSelectionModel().isEmpty()) {
                seatsAmountLabel.setText(String.valueOf(roomCombo.getValue().getSeatsAmount()));
            }
        });

        // set end time
        startDatePicker.setOnAction(actionEvent -> setEndTime());
        timeText.textProperty().addListener(t -> {
            try {
                LocalTime.parse(timeText.getText());
                setEndTime();
            }
            catch (Exception ignored){}
        });

        // add show
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // add show
            }
        });

        // clear fields
        clearButton.setOnAction(actionEvent -> clearFields());
    }
    private void setEndTime(){
        LocalDateTime startTime = LocalDateTime.of(startDatePicker.getValue(), LocalTime.parse(timeText.getText()));
        if (!titleCombo.getSelectionModel().isEmpty())
            startTime = startTime.plus(titleCombo.getValue().getMovieDuration());
        endTimeLabel.setText(startTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
    }
    private void clearFields(){
        titleCombo.setValue(null);
        roomCombo.setValue(null);
        startDatePicker.setValue(LocalDate.now());
        seatsAmountLabel.setText("");
        priceAmountLabel.setText("");
        timeText.setText("00:00");
        setEndTime();
    }
    private void createNodes() {
        // creates Labels, ComboBoxes, DatePicker, TextField and Buttons

        Label titleLabel = new Label("Movie title");
        titleCombo = new ComboBox<>();

        Label roomLabel = new Label("Room");
        roomCombo = new ComboBox<>();

        Label seatsLabel = new Label("No. of seats");
        seatsAmountLabel = new Label("");

        Label startLabel = new Label("Start");
        HBox timeHBox = makeDateTimePicker();


        Label endLabel = new Label("End");
        endTimeLabel = new Label("");
        setEndTime();

        Label priceLabel = new Label("Price");
        priceAmountLabel = new Label("");

        addButton = new Button("Add showing");
        clearButton = new Button("Clear");
        addButton.setPrefSize(120, 20);
        clearButton.setPrefSize(120, 20);

        setPadding(new Insets(10));
        setHgap(50);
        setVgap(5);

        add(titleLabel, 0, 0, 1, 1);
        add(roomLabel, 0, 1, 1, 1);
        add(seatsLabel, 0, 2, 1, 1);

        add(titleCombo, 1, 0, 1, 1);
        add(roomCombo, 1, 1, 1, 1);
        add(seatsAmountLabel, 1, 2, 1, 1);

        add(startLabel, 2, 0, 1, 1);
        add(endLabel, 2, 1, 1, 1);
        add(priceLabel, 2, 2, 1, 1);

        add(timeHBox, 3, 0, 1, 1);
        add(endTimeLabel, 3, 1, 1, 1);
        add(priceAmountLabel, 3, 2, 1, 1);

        add(addButton, 4, 1, 1, 1);
        add(clearButton, 4, 2, 1, 1);

        getStyleClass().add("contentPane");
    }
    private HBox makeDateTimePicker(){
        // make date picker
        makeDatePicker();

        //make time picker
        makeTimePicker();

        // make HBox
        HBox timeHBox = new HBox(startDatePicker, timeText);
        timeHBox.setSpacing(5);

        return timeHBox;
    }
    private void makeDatePicker(){
        // make datepicker
        startDatePicker = new DatePicker();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        StringConverter<LocalDate> dateConverter = new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateTimeFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateTimeFormatter);
                } else {
                    return null;
                }
            }
        };
        startDatePicker.setConverter(dateConverter);
        startDatePicker.setValue(LocalDate.now());
    }
    private void makeTimePicker(){
        // make time textField
        timeText = new TextField();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        try {
            timeText.setTextFormatter(new TextFormatter<>(new DateTimeStringConverter(format), format.parse("00:00")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public void fillCombos(){
        fillMovieCombo();
        fillRoomCombo();
    }
    private void fillMovieCombo(){
        for (Movie m:db.getMovies()){
            titleCombo.getItems().add(m);
        }
    }
    private void fillRoomCombo(){
        for (Room r:db.getRooms()){
            roomCombo.getItems().add(r);
        }
    }
}
