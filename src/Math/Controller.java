/*
 * Copyright (©) 2019. Leo Player - Manly Selective Campus
 * made using JavaFX in IntelliJ
 */

package Math;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * fxml made using Scene Builder 2
 * Controller class used to initialise floats, then update with field when submit pressed
 * output also created after adding two numbers together
 */
public class Controller {
    private int stage = 0;
    private int tempSign = 0;
    private int sign = 0;
    private float firstNumber = 0;
    private float secondNumber = 0;
    private float mathResult = 0;
    private int numericTestResult = 0; //1 is number 0 is not number
    @FXML private TextArea mathOutput;
    @FXML private TextArea userInput;
    @FXML private Button plus;
    @FXML private Button minus;
    @FXML private Button multiply;
    @FXML private Button divide;
    private final Logger LOGGER = Logger.getLogger(Main.class.getName());
    @FXML protected void plusButton() {
        tempSign = 1;
        plusColor();
        signPressed();
    }
    @FXML protected void minusButton() {
        tempSign = 2;
        minusColor();
        signPressed();
    }
    @FXML protected void multiplyButton() {
        tempSign = 3;
        multiplyColor();
        signPressed();
    }
    @FXML protected void divideButton() {
        tempSign = 4;
        divideColor();
        signPressed();
    }
    private void signPressed() {
        if (stage == 0) {
            if (userInput.getText().equals("")) {
                if (mathOutput.getText().equals("")) {
                    LOGGER.log(Level.WARNING, "signPressed, s=0, no uI mO");
                    inputError();
                    resetSignColor();
                }
                else {
                    firstNumber = Float.parseFloat(mathOutput.getText());
                    sign = tempSign;
                    stage = 1;
                }
            }
            else {
                numericTest();
                if (numericTestResult == 0) {
                    inputError();
                    resetSignColor();
                }
                else if (numericTestResult == 1) {
                    firstNumber = Float.parseFloat(userInput.getText());
                    sign = tempSign;
                    stage = 1;
                    userInput.clear();
                }
            }
        }
        else if (stage == 1) {
            if (userInput.getText().equals("")) {
                sign = tempSign;
            }
            else {
                numericTest();
                if (numericTestResult == 0) {
                    inputError();
                    resetSignColor();
                }
                else if (numericTestResult == 1){
                    secondNumber = Float.parseFloat(userInput.getText());
                    math();
                    displayAnswer();
                    sign = tempSign;
                    firstNumber = mathResult;
                    userInput.clear();
                }
            }
        }
    }
    @FXML protected void equalsButton() {
        if (stage == 0) {
            if (!userInput.getText().equals("")) {
                numericTest();
                if (numericTestResult == 0) {
                    inputError();
                }
                else if (numericTestResult == 1) {
                    mathResult = Float.parseFloat(userInput.getText());
                    displayAnswer();
                    userInput.clear();
                }
            }
        }
        else if (stage == 1) {
            if (userInput.getText().equals("")) {
                mathResult = firstNumber;
                displayAnswer();
                stage = 0;
                resetSignColor();
            }
            else {
                numericTest();
                if (numericTestResult == 0) {
                    inputError();
                }
                else if (numericTestResult == 1) {
                    secondNumber = Float.parseFloat(userInput.getText());
                    math();
                    displayAnswer();
                    stage = 0;
                    resetSignColor();
                    userInput.clear();
                }
            }
        }
    }
    private void math() {
        if (sign == 1) mathResult = firstNumber + secondNumber;
        else if (sign == 2) mathResult = firstNumber - secondNumber;
        else if (sign == 3) mathResult = firstNumber * secondNumber;
        else if (sign == 4) mathResult = firstNumber / secondNumber;
        else LOGGER.log(Level.SEVERE, "sign not 1~4 at math()");
    }
    private void numericTest() {
        try {
            Float.parseFloat(userInput.getText());
            numericTestResult = 1;
        }
        catch (NumberFormatException inputNotNum) {
            numericTestResult = 0;
            LOGGER.log(Level.WARNING, "input not numeral");
        }
    }
    private void displayAnswer() {
        if (mathResult == (int)mathResult) {
            mathOutput.setText(String.valueOf(Math.round(mathResult)));
        }
        else mathOutput.setText(String.valueOf(mathResult));
    }
    @FXML protected void clear() {
        userInput.clear();
    }
    @FXML protected void allClear() {
        userInput.clear();
        mathOutput.clear();
        stage = 0;
        resetSignColor();
        sign = 0;
        firstNumber = 0;
        secondNumber = 0;
    }
    private void plusColor() {
        plus.setStyle("-fx-background-color: #adadad");
        minus.setStyle("-fx-background-color: #eaeaea");
        multiply.setStyle("-fx-background-color: #eaeaea");
        divide.setStyle("-fx-background-color: #eaeaea");
    }
    private void minusColor() {
        plus.setStyle("-fx-background-color: #eaeaea");
        minus.setStyle("-fx-background-color: #adadad");
        multiply.setStyle("-fx-background-color: #eaeaea");
        divide.setStyle("-fx-background-color: #eaeaea");
    }
    private void multiplyColor() {
        plus.setStyle("-fx-background-color: #eaeaea");
        minus.setStyle("-fx-background-color: #eaeaea");
        multiply.setStyle("-fx-background-color: #adadad");
        divide.setStyle("-fx-background-color: #eaeaea");
    }
    private void divideColor() {
        plus.setStyle("-fx-background-color: #eaeaea");
        minus.setStyle("-fx-background-color: #eaeaea");
        multiply.setStyle("-fx-background-color: #eaeaea");
        divide.setStyle("-fx-background-color: #adadad");
    }
    private void resetSignColor() {
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
        LOGGER.log(Level.WARNING, "inputError");
    }
}
