package com.example.bankingapp;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HelloApplication extends Application {

    private double balance = 0;
    private boolean isDarkMode = false; // Track the current theme mode

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Banking System");
        primaryStage.setResizable(true);

        // Header
        Label headerLabel = new Label("Banking System");
        headerLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #333; -fx-padding: 20px;");

        // Balance Section
        Label balanceLabel = new Label("Current Balance: 0.00€");
        balanceLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #555; -fx-padding: 10px;");

        // Input Fields and Buttons
        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount");
        amountField.setMaxWidth(250);
        amountField.setStyle("-fx-font-size: 16px; -fx-padding: 10px; -fx-border-radius: 5px; -fx-border-color: #ccc;");

        Button depositButton = createButton("Deposit", "#4CAF50");
        Button withdrawButton = createButton("Withdraw", "#2196F3");
        Button exitButton = createButton("Exit", "#f44336");
        Button toggleButton = new Button("Toggle Dark Mode");
        toggleButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px; -fx-border-radius: 5px; -fx-color: #1E1E1E");

        HBox buttonBox = new HBox(15, depositButton, withdrawButton, exitButton, toggleButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Layout
        VBox mainLayout = new VBox(20, headerLabel, balanceLabel, amountField, buttonBox);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(30));
        mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #ffffff, #e6e6e6);");

        // Scene and Stage
        Scene scene = new Scene(mainLayout, 550, 450);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(300);
        primaryStage.show();

        // Actions
        depositButton.setOnAction(e -> handleDeposit(amountField, balanceLabel));
        withdrawButton.setOnAction(e -> handleWithdraw(amountField, balanceLabel));
        exitButton.setOnAction(e -> {
            showAlert(Alert.AlertType.INFORMATION, "Goodbye", "Thank you for using our banking system!");
            primaryStage.close();
        });

        // Dark Mode Toggle Action
        toggleButton.setOnAction(e -> toggleDarkMode(mainLayout, scene));
    }

    private Button createButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-border-radius: 5px; -fx-background-radius: 5px;", color));
        button.setEffect(new javafx.scene.effect.DropShadow(5, javafx.scene.paint.Color.GRAY));
        return button;
    }

    private void handleDeposit(TextField amountField, Label balanceLabel) {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount > 0) {
                balance += amount;
                balanceLabel.setText(String.format("Current Balance: %.2f€", balance));
                showAlertWithAnimation(Alert.AlertType.INFORMATION, "Success", "Amount deposited successfully!");
                amountField.clear();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Amount must be greater than zero.");
            }
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid amount. Please enter a numeric value.");
        }
    }

    private void handleWithdraw(TextField amountField, Label balanceLabel) {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount > 0 && amount <= balance) {
                balance -= amount;
                balanceLabel.setText(String.format("Current Balance: %.2f€", balance));
                showAlertWithAnimation(Alert.AlertType.INFORMATION, "Success", "Amount withdrawn successfully!");
                amountField.clear();
            } else if (amount > balance) {
                showAlert(Alert.AlertType.ERROR, "Error", "Insufficient funds.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Amount must be greater than zero.");
            }
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid amount. Please enter a numeric value.");
        }
    }

    private void showAlertWithAnimation(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Add fade transition to alert
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), alert.getDialogPane());
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();

        alert.show();
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> alert.close());
        pause.play();
    }

    private void toggleDarkMode(VBox mainLayout, Scene scene) {
        isDarkMode = !isDarkMode; // Toggle the theme mode

        if (isDarkMode) {
            // Dark mode styles
            mainLayout.setStyle("-fx-background-color: #333333;");
            for (Node node : mainLayout.getChildren()) {
                if (node instanceof Label) {
                    node.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-padding: 10px;");
                }
                if (node instanceof Button) {
                    Button button = (Button) node;
                    button.setStyle("-fx-background-color: #555555; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-border-radius: 5px;");
                }
                if (node instanceof TextField) {
                    node.setStyle("-fx-background-color: #444444; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px; -fx-border-radius: 5px; -fx-border-color: #888888;");
                }
            }
        } else {
            // Light mode styles
            mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #ffffff, #e6e6e6);");
            for (Node node : mainLayout.getChildren()) {
                if (node instanceof Label) {
                    node.setStyle("-fx-text-fill: #333; -fx-font-size: 24px; -fx-padding: 10px;");
                }
                if (node instanceof Button) {
                    Button button = (Button) node;
                    button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-border-radius: 5px;");
                }
                if (node instanceof TextField) {
                    node.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-font-size: 16px; -fx-padding: 10px; -fx-border-radius: 5px; -fx-border-color: #ccc;");
                }
            }
        }

        // Optional: Add a fade effect when toggling modes
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), mainLayout);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
