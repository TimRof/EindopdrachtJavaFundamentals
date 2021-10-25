package nl.inholland.ui.panes;

import javafx.scene.layout.*;
import nl.inholland.data.Database;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import nl.inholland.model.Movie;
import nl.inholland.ui.dialogs.ConfirmDialog;
import nl.inholland.ui.exceptions.NoDurationException;
import nl.inholland.ui.exceptions.NoMovieException;
import nl.inholland.ui.exceptions.NoNameException;
import nl.inholland.ui.exceptions.NoPriceException;

import java.text.DecimalFormat;
import java.time.Duration;

public class ManageMoviesGridPane extends GridPane {
    private TextField titleTextField;
    private TextField priceTextField;
    private TextField durationTextField;
    private Button addButton;
    private Button clearButton;
    private Button removeButton;
    private ComboBox<Movie> movieCombo;
    public ManageMoviesGridPane(Database db, InfoPane infoPane) {
        createNodes();
        fillMovieCombo(db);

        // allow a maximum of 50 characters in TextField
        titleTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (titleTextField.getText().length() > 50){
                String s = titleTextField.getText().substring(0, 50);
                titleTextField.setText(s);
            }
        });

        // only allow numbers and dots, allow a maximum of 5 characters
        priceTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                priceTextField.setText(newValue.replaceAll("[^\\d.]", ""));
            }
            if (priceTextField.getText().length() > 5){
                String s = priceTextField.getText().substring(0, 5);
                priceTextField.setText(s);
            }
        });
        // only allow numbers, allow a maximum of 3 characters
        durationTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                durationTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (durationTextField.getText().length() > 3){
                String s = durationTextField.getText().substring(0, 3);
                durationTextField.setText(s);
            }
        });
        // add movie
        addButton.setOnAction(actionEvent -> addMovie(db, infoPane));
        // clear fields
        clearButton.setOnAction(actionEvent -> clearFields());
        // remove button
        removeButton.setOnAction(actionEvent -> removeMovie(db, infoPane));
    }
    private void createNodes() {
        // Create Labels, TextFields and Buttons
        Label titleLabel = new Label("Movie title");
        titleTextField = new TextField();

        Label priceLabel = new Label("Price");
        priceTextField = new TextField();
        priceTextField.setMaxSize(50,20);

        Label durationLabel = new Label("Duration (minutes)");
        durationTextField = new TextField();
        durationTextField.setMaxSize(50,20);

        addButton = new Button("Add movie");
        clearButton = new Button("Clear");
        addButton.setPrefSize(120, 20);
        clearButton.setPrefSize(120, 20);

        removeButton = new Button("Remove");
        removeButton.setPrefSize(120, 20);
        movieCombo = new ComboBox<>();

        setPadding(new Insets(10));
        setHgap(50);
        setVgap(5);

        add(titleLabel, 0, 0, 1, 1);
        add(priceLabel, 0, 1, 1, 1);
        add(durationLabel, 0, 2, 1, 1);

        add(titleTextField, 1, 0, 1, 1);
        add(priceTextField, 1, 1, 1, 1);
        add(durationTextField, 1, 2, 1, 1);

        add(addButton, 2, 1, 1, 1);
        add(clearButton, 2, 2, 1, 1);

        add(movieCombo, 3, 1, 1, 1);
        add(removeButton, 4, 1, 1, 1);

        getStyleClass().add("contentPane");
    }
    private double getPrice(){
        return Double.parseDouble(priceTextField.getText());
    }
    private void addMovie(Database db, InfoPane infoPane){
        try {
            if (titleTextField.getText().isEmpty())
                throw new NoNameException();
            else if (priceTextField.getText().isEmpty())
                throw new NoPriceException();
            else if (durationTextField.getText().isEmpty())
                throw new NoDurationException();
            String movieTitle = titleTextField.getText();
            double price = getPrice();
            Duration duration = Duration.ofMinutes(Integer.parseInt(durationTextField.getText()));
            Movie movie = new Movie(movieTitle, price, duration);

            String dialogTitle = "Adding movie '" + movieTitle + "'";
            DecimalFormat df = new DecimalFormat("#.00");
            String dialogHeader = "Title:\t\t" + movieTitle + "\nPrice:\t" + df.format(price) + "\nDuration:\t" + duration.toMinutes() + " minutes";
            ConfirmDialog confirmDialog = new ConfirmDialog(Alert.AlertType.CONFIRMATION, dialogTitle, dialogHeader);
            if (confirmDialog.getResult() == ButtonType.YES)
            {
                db.addMovie(movie);
                fillMovieCombo(db);
                infoPane.showInfoMessage("Successfully added the movie '" + movieTitle + "'!");
            }
            else
                infoPane.showInfoMessage("No movie added.");
        } catch (Exception e){
            infoPane.showInfoMessage("Movie not added. (" + e.getMessage() + ")");
        }
    }
    private void clearFields(){
        titleTextField.setText("");
        priceTextField.setText("");
        durationTextField.setText("");
        movieCombo.getSelectionModel().clearSelection();
    }
    private void removeMovie(Database db, InfoPane infoPane){
        try {
            if (movieCombo.getSelectionModel().isEmpty())
                throw new NoMovieException();
            String dialogTitle = "Removing movie '" + movieCombo.getValue().getTitle() + "'";
            String dialogHeader = "You are about to remove '" + movieCombo.getValue().getTitle() + "'" + " from the list of movies. \nThis action can not be undone.";
            ConfirmDialog confirmDialog = new ConfirmDialog(Alert.AlertType.CONFIRMATION, dialogTitle, dialogHeader);
            if (confirmDialog.getResult() == ButtonType.YES) {
                db.removeMovie(movieCombo.getValue());
                infoPane.showInfoMessage("Successfully removed the movie '" + movieCombo.getValue().getTitle() + "'!");
                fillMovieCombo(db);
            } else
                infoPane.showInfoMessage("No movie removed.");
        }
        catch(Exception e){
            infoPane.showInfoMessage("Movie not removed. (" + e.getMessage() + ")");
        }
    }
    private void fillMovieCombo(Database db){
        movieCombo.getItems().clear();
        for (Movie m:db.getMovies()){
            movieCombo.getItems().add(m);
        }
    }
}
