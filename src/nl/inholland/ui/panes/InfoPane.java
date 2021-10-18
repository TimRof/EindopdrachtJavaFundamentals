package nl.inholland.ui.panes;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class InfoPane extends Pane {
    private final Label infoLabel;
    private Timeline timeline;
    private final int messageTime;
    private int seconds;

    public InfoPane(String string) {
        infoLabel = new Label();
        messageTime = 5; // duration of message in seconds
        infoLabel.setPadding(new Insets(10));
        getChildren().add(infoLabel);
        getStyleClass().add("contentPane");
        showInfoMessage(string);
    }
    public void showInfoMessage(String info){
        // show info messages for 'messageTime' amount of seconds
        if(timeline!=null)
            timeline.stop();
        infoLabel.setText(info);
        seconds = messageTime;
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame frame = new KeyFrame(Duration.seconds(1), actionEvent -> {
            seconds--;
            if (seconds<=0){
                timeline.stop();
                infoLabel.setText("");
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.playFromStart();
    }
}
