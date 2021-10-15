package nl.inholland.ui.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ConfirmDialog extends Alert {
    public ConfirmDialog(AlertType alertType, String title, String header) {
        super(alertType);
        getButtonTypes().clear();
        getButtonTypes().addAll(ButtonType.YES, ButtonType.CANCEL);
        setTitle(title);
        setHeaderText(header);
        setContentText("Are you sure?");
        showAndWait();
    }

}
