package com.example.demo1;
// all the imports and stuff required for javafx
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application implements EventHandler<ActionEvent> {

    // declaring all the key UI elements
    CheckBox eggSandwichCheckBox;
    CheckBox bagelCheckBox;
    CheckBox potatoSaladCheckBox;
    CheckBox chickenSandwichCheckBox;

    ToggleGroup drinkGroup; // toggle group for radio buttons for drinks

    RadioButton coffeeRadioButton;
    RadioButton greenTeaRadioButton;
    RadioButton blackTeaRadioButton;
    RadioButton orangeJuiceRadioButton;

    Button orderButton;
    Button cancelButton;
    Button confirmButton;

    TextArea billTextArea; // to print out the receipt

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Joe's Deli Breakfast Ordering");

        // initializing the food checkboxes
        eggSandwichCheckBox = new CheckBox("Egg Sandwich");
        bagelCheckBox = new CheckBox("Bagel");
        potatoSaladCheckBox = new CheckBox("Potato Salad");
        chickenSandwichCheckBox = new CheckBox("Chicken Sandwich");

        // initializing the radio buttons
        drinkGroup = new ToggleGroup();
        coffeeRadioButton = new RadioButton("Coffee");
        greenTeaRadioButton = new RadioButton("Green Tea");
        blackTeaRadioButton = new RadioButton("Black Tea");
        orangeJuiceRadioButton = new RadioButton("Orange Juice");

        // setting toggle groups for the radio buttons so that only one can be active at a time
        coffeeRadioButton.setToggleGroup(drinkGroup);
        greenTeaRadioButton.setToggleGroup(drinkGroup);
        blackTeaRadioButton.setToggleGroup(drinkGroup);
        orangeJuiceRadioButton.setToggleGroup(drinkGroup);

        // initializing the buttons
        orderButton = new Button("Order");
        cancelButton = new Button("Cancel");
        confirmButton = new Button("Confirm");

        // Initialize the text area to display the bill
        billTextArea = new TextArea();
        billTextArea.setText("Receipt"); // show that it is the receipt as header
        billTextArea.setEditable(false);
        billTextArea.setWrapText(true);


        orderButton.setOnAction(this);
        cancelButton.setOnAction(this);
        confirmButton.setOnAction(this);

        // creating vbox to group food items
        VBox foodBox = new VBox(10, eggSandwichCheckBox, bagelCheckBox, potatoSaladCheckBox, chickenSandwichCheckBox);

        // creating vbox to group drink items
        VBox drinkBox = new VBox(10, coffeeRadioButton, greenTeaRadioButton, blackTeaRadioButton, orangeJuiceRadioButton);

        // arranging food and drink boxes horizontally
        HBox selectionBox = new HBox(20, foodBox, drinkBox);

        // arranging receipt and buttons horizontally with selection box
        HBox mainContent = new HBox(20, selectionBox, billTextArea);

        // arranging buttons side by side
        HBox buttonBox = new HBox(10, orderButton, cancelButton, confirmButton);

        // arranging everything vertically
        VBox mainLayout = new VBox(10, mainContent, buttonBox);

        // Creating the scene with the layout and set the size of the window
        Scene scene = new Scene(mainLayout, 800, 400); // Adjusted width to fit layout

        // Set the scene on the primary stage and show the stage
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public void handle(ActionEvent event) {
        // Determine which button was clicked and call the appropriate handler method
        if (event.getSource() == orderButton) {
            handleOrder();
        } else if (event.getSource() == cancelButton) {
            handleCancel();
        } else if (event.getSource() == confirmButton) {
            handleConfirm();
        }
    }

    private void handleOrder() {
        StringBuilder bill = new StringBuilder();  // StringBuilder to construct the bill text
        double totalCost = 0;  // Initialize total cost

        // Check which food items are selected and update the bill and total cost
        if (eggSandwichCheckBox.isSelected()) {
            bill.append("Egg Sandwich: $7.99\n");
            totalCost += 7.99;
        }
        if (bagelCheckBox.isSelected()) {
            bill.append("Bagel: $2.50\n");
            totalCost += 2.50;
        }
        if (potatoSaladCheckBox.isSelected()) {
            bill.append("Potato Salad: $4.49\n");
            totalCost += 4.49;
        }
        if (chickenSandwichCheckBox.isSelected()) {
            bill.append("Chicken Sandwich: $9.99\n");
            totalCost += 9.99;
        }

        // Check which drink is selected and update the bill and total cost
        RadioButton selectedDrink = (RadioButton) drinkGroup.getSelectedToggle();
        if (selectedDrink != null) {
            String drinkText = selectedDrink.getText();
            double drinkPrice = switch (drinkText) {  // Determine the drink price
                case "Coffee" -> 1.99;
                case "Green Tea" -> 0.99;
                case "Black Tea" -> 1.25;
                case "Orange Juice" -> 2.25;
                default -> 0;
            };
            bill.append(drinkText).append(": $").append(String.format("%.2f", drinkPrice)).append("\n");
            totalCost += drinkPrice;
        }

        // Calculate tax and total cost
        double tax = totalCost * 0.07;  // Tax rate is 7%
        double finalTotal = totalCost + tax;
        bill.append("Subtotal: $").append(String.format("%.2f", totalCost)).append("\n");
        bill.append("Tax: $").append(String.format("%.2f", tax)).append("\n");
        bill.append("Total: $").append(String.format("%.2f", finalTotal)).append("\n");

        // Update the text area with the bill
        billTextArea.setText(bill.toString());
    }

    private void handleCancel() {
        // Clear all selections and the bill text area
        eggSandwichCheckBox.setSelected(false);
        bagelCheckBox.setSelected(false);
        potatoSaladCheckBox.setSelected(false);
        chickenSandwichCheckBox.setSelected(false);
        drinkGroup.selectToggle(null);  // Deselect any selected radio button
        billTextArea.clear();  // Clear the text area
    }

    private void handleConfirm() {
        // Simply clear all selections but keep the bill displayed
        eggSandwichCheckBox.setSelected(false);
        bagelCheckBox.setSelected(false);
        potatoSaladCheckBox.setSelected(false);
        chickenSandwichCheckBox.setSelected(false);
        drinkGroup.selectToggle(null);  // Deselect any selected radio button
        // Bill remains displayed in the text area
    }

    public static void main(String[] args) {
        launch(args);  // Launch the JavaFX application
    }
}
