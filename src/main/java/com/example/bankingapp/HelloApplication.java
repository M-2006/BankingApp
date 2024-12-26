package com.example.bankingapp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    private double balance = 0;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Banking System");

        // Header
        Label headerLabel = new Label("Banking System");
        headerLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #333; -fx-padding: 10px;");

        // Balance Section
        Label balanceLabel = new Label("Current Balance: €0.00");
        balanceLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #555; -fx-padding: 10px;");

        // Input Fields and Buttons
        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount");
        amountField.setMaxWidth(250);
        amountField.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        Button depositButton = new Button("Deposit");
        Button withdrawButton = new Button("Withdraw");
        Button exitButton = new Button("Exit");

        depositButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        withdrawButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        exitButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        HBox buttonBox = new HBox(15, depositButton, withdrawButton, exitButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Layout
        VBox mainLayout = new VBox(20, headerLabel, balanceLabel, amountField, buttonBox);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(30));
        mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #ffffff, #e6e6e6);");

        // Dynamic Resizing
        mainLayout.setPrefWidth(400);
        mainLayout.setPrefHeight(300);
        mainLayout.setMinWidth(300);
        mainLayout.setMinHeight(250);

        // Actions
        depositButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (amount > 0) {
                    balance += amount;
                    balanceLabel.setText(String.format("Current Balance: €%.2f", balance));
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Amount deposited successfully!");
                    amountField.clear();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Amount must be greater than zero.");
                }
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid amount. Please enter a numeric value.");
            }
        });

        withdrawButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (amount > 0 && amount <= balance) {
                    balance -= amount;
                    balanceLabel.setText(String.format("Current Balance: €%.2f", balance));
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Amount withdrawn successfully!");
                    amountField.clear();
                } else if (amount > balance) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Insufficient funds.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Amount must be greater than zero.");
                }
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid amount. Please enter a numeric value.");
            }
        });

        exitButton.setOnAction(e -> {
            showAlert(Alert.AlertType.INFORMATION, "Goodbye", "Thank you for using our banking system!");
            primaryStage.close();
        });

        // Scene and Stage
        Scene scene = new Scene(mainLayout, 450, 350);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(300);
        primaryStage.show();
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
