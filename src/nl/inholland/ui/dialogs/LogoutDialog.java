package nl.inholland.ui.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class LogoutDialog extends Alert {
    public LogoutDialog(AlertType alertType, Stage oldStage, Stage stage) {
        super(alertType);
        this.setTitle("Confirm logout");
        this.setHeaderText("Logging out");
        this.setContentText("Are you sure?");
        Optional<ButtonType> result = this.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            oldStage.show();
            stage.close();
        }
        else
            this.close();

    }

}
