package nl.inholland.ui.dialogs;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ExitDialog extends Stage
{
    private boolean okPressed;
    private Button okButton;
    private Button cancelButton;
    public ExitDialog(String message)
    {
        okPressed = false;
        makeNodes();
        resizableProperty().setValue(Boolean.FALSE);
        initStyle(StageStyle.UTILITY);
        setTitle(message);

        okButton.setOnAction(a -> {
            okPressed = true;
            close();
        });
        cancelButton.setOnAction(a -> close());

    }
    private void makeNodes(){
        Label closeLabel = new Label("Close the window?");
        okButton = new Button("Ok");
        cancelButton = new Button("Cancel");
        HBox buttonBox = new HBox();
        VBox exitVBox = new VBox();
        buttonBox.getChildren().addAll(okButton, cancelButton);
        buttonBox.setSpacing(5);
        exitVBox.getChildren().addAll(closeLabel, buttonBox);
        exitVBox.setPadding(new Insets(10));

        Scene scene = new Scene(exitVBox);

        this.setScene(scene);
    }

    public boolean isOkPressed()
    {
        return okPressed;
    }
}
