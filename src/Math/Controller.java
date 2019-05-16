/*
 * Copyright (©) 2019. Leo Player - Manly Selective Campus
 * made using JavaFX in IntelliJ
 */

package Math;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * fxml made using Scene Builder 2
 * Controller class used to initialise floats, then update with field when submit pressed
 * output also created after adding two numbers together
 */
public class Controller {
    private int tempSign = 0;
    private int sign = 0;
    private int stage = 0;
    private float firstValue;
    private float secondValue;
    private float mathSum;
    private int stageFix;
    @FXML private TextArea sumResult;
    @FXML private TextArea userInput;
    @FXML private Button plus;
    @FXML private Button minus;
    @FXML private Button multiply;
    @FXML private Button divide;
    @FXML protected void plusButton() {
        tempSign = 1;
        signPressed();
        plus();
    }
    @FXML protected void minusButton() {
        tempSign = 2;
        signPressed();
        minus();
    }
    @FXML protected void multiplyButton() {
        tempSign = 3;
        signPressed();
        multiply();
    }
    @FXML protected void divideButton() {
        tempSign = 4;
        signPressed();
        divide();
    }
    private void signPressed() {
        if (stage == 0 ) { //no value
            if (userInput.getText().equals("")) {
                if (sumResult.getText().equals("")) {
                    inputError();
                    resetSigns();
                }
                else {
                    firstValue = Float.parseFloat(sumResult.getText());
                    sign = tempSign;
                    stage = 1;
                }
            }
            else {
                try {
                    firstValue = Float.parseFloat(userInput.getText());
                    if (firstValue == (int)firstValue) {
                        sumResult.setText(String.valueOf(Math.round(firstValue)));
                    }
                    else {
                        sumResult.setText(String.valueOf(firstValue));
                    }
                    userInput.clear();
                    stage = 1;
                    sign = tempSign;
                    resetSigns();
                }
                catch (NumberFormatException firstNotNum) {
                    inputError();
                }
            }
        }
        else if (stage == 1) { //firstValue has value
            if (userInput.getText().equals("")) {
                inputError();
            }
            else {
                math();
                sign = tempSign;
                firstValue = mathSum;
                resetSigns();
            }
        }
    }
    @FXML protected void equalsButton() {
        resetSigns();
        if (stage == 0) {
            if (userInput.getText().equals(null)) {
                inputError();
            }
            else {
                try {
                    firstValue = Float.parseFloat(userInput.getText());
                    if (firstValue == (int)firstValue) {
                        sumResult.setText(String.valueOf(Math.round(firstValue)));
                    }
                    else {
                        sumResult.setText(String.valueOf(firstValue));
                    }
                    userInput.clear();
                    stage = 0;
                }
                catch (NumberFormatException notNumberFirstEquals) {
                    inputError();
                }
            }
        }
        else if (stage == 1) {
            math();
            sign = 0;
            tempSign = 0;
            if (stageFix == 0) {
                stage = 0;
            }
        }
    }
    private void math() {
        try {
            secondValue = Float.parseFloat(userInput.getText());
            if (sign == 1) {
                mathSum = firstValue + secondValue;
                userInput.clear();
                mathOutput();
            }
            if (sign == 2) {
                mathSum = firstValue - secondValue;
                userInput.clear();
                mathOutput();
            }
            if (sign == 3) {
                mathSum = firstValue * secondValue;
                userInput.clear();
                mathOutput();
            }
            if (sign == 4) {
                mathSum = firstValue / secondValue;
                userInput.clear();
                mathOutput();
            }
        }
        catch (NumberFormatException secondNotNum) {
            inputError();
            stageFix = 1;
        }
    }
    @FXML protected void clear() {

    }
    @FXML protected void allClear() {

    }
    private void plus() {
        plus.setStyle("-fx-background-color: #adadad");
        minus.setStyle("-fx-background-color: #eaeaea");
        multiply.setStyle("-fx-background-color: #eaeaea");
        divide.setStyle("-fx-background-color: #eaeaea");
    }
    private void minus() {
        plus.setStyle("-fx-background-color: #eaeaea");
        minus.setStyle("-fx-background-color: #adadad");
        multiply.setStyle("-fx-background-color: #eaeaea");
        divide.setStyle("-fx-background-color: #eaeaea");
    }
    private void multiply() {
        plus.setStyle("-fx-background-color: #eaeaea");
        minus.setStyle("-fx-background-color: #eaeaea");
        multiply.setStyle("-fx-background-color: #adadad");
        divide.setStyle("-fx-background-color: #eaeaea");
    }
    private void divide() {
        plus.setStyle("-fx-background-color: #eaeaea");
        minus.setStyle("-fx-background-color: #eaeaea");
        multiply.setStyle("-fx-background-color: #eaeaea");
        divide.setStyle("-fx-background-color: #adadad");
    }
    private void resetSigns() {
        plus.setStyle("-fx-background-color: #eaeaea");
        minus.setStyle("-fx-background-color: #eaeaea");
        multiply.setStyle("-fx-background-color: #eaeaea");
        divide.setStyle("-fx-background-color: #eaeaea");
    }
    private void inputError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText("Not a number");
        alert.setContentText("Please put in a number");

        alert.showAndWait();
        System.out.println("inputError code ran");
    }
    private void mathOutput() {
        if (mathSum == (int)mathSum) {
            sumResult.setText(String.valueOf(Math.round(mathSum)));
        }
        else {
            sumResult.setText(String.valueOf(mathSum));
        }
    }
}