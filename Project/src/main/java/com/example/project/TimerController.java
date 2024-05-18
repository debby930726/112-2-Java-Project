package com.example.project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class TimerController {

    @FXML
    private Button startButton;

    @FXML
    private ComboBox<String> timeComboBox;

    private int workTimeSeconds = 25 * 60; // Default work time: 25 minutes
    private int breakTimeSeconds = 5 * 60; // Default break time: 5 minutes
    private int currentTimeSeconds = workTimeSeconds;
    private boolean isWorking = true;
    private Timeline timeline;

    @FXML
    private Label timerText;

    @FXML
    protected void start() {
        startTimer();
    }

    @FXML
    private void updateTimeSettings() {
        String selectedTime = timeComboBox.getValue();
        if (selectedTime.equals("50 minutes")) {
            workTimeSeconds = 50 * 60;
            currentTimeSeconds = workTimeSeconds;
        } else {
            workTimeSeconds = 25 * 60; // Default work time: 25 minutes
            currentTimeSeconds = workTimeSeconds;
        }
        updateTimerLabel();
    }

    @FXML
    private void startTimer() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            currentTimeSeconds--;
            if (currentTimeSeconds <= 0) {
                switchTimer();
            }
            updateTimerLabel();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @FXML
    private void resetTimer() {
        if (timeline != null) {
            timeline.stop();
        }
        currentTimeSeconds = workTimeSeconds;
        isWorking = true;
        updateTimerLabel();
        startButton.setText("Start");
    }

    private void switchTimer() {
        if (isWorking) {
            currentTimeSeconds = breakTimeSeconds;
        } else {
            currentTimeSeconds = workTimeSeconds;
        }
        isWorking = !isWorking;
    }

    private void updateTimerLabel() {
        int minutes = currentTimeSeconds / 60;
        int remainingSeconds = currentTimeSeconds % 60;
        timerText.setText(String.format("%02d:%02d", minutes, remainingSeconds));
    }
}
