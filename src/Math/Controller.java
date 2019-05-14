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
    private int sign = 0;
    private int signUsed = 0;
    private int step = 0;
    private float firstInput;
    @FXML private TextArea sumResult;
    @FXML private TextArea inputOne;
    @FXML private Button plus;
    @FXML private Button minus;
    @FXML private Button multiply;
    @FXML private Button divide;
    @FXML protected void allClear() {
        inputOne.clear();
        sumResult.clear();
        sign = 0;
        signUsed = 0;
        step = 0;
        System.out.println("User All Clear");
    }
    @FXML protected void clear() {
        inputOne.clear();
        System.out.println("User Clear");
    }
    @FXML protected void plusButton() {
        sign = 1;
        System.out.println("User changed sign to plus");
        signButton();
    }
    @FXML protected void minusButton() {
        sign = 2;
        System.out.println("User changed sign to minus");
        signButton();
    }
    @FXML protected void multiplyButton() {
        sign = 3;
        System.out.println("User changed sign to multiply");
        signButton();
    }
    @FXML protected void divideButton() {
        sign = 4;
        System.out.println("User changed sign to divide");
        signButton();
    }

    private void signButton() {
        if (step == 0) {
            try {
                firstInput = Float.parseFloat(inputOne.getText());
                System.out.println("User firstInput as " + firstInput + " which is a number");
                step = 1;
                System.out.println("step is " + step);
                signUsed = sign;
                inputOne.clear();
                if (signUsed == 1) {
                    plus.setStyle("-fx-background-color: #adadad");
                    minus.setStyle("-fx-background-color: #eaeaea");
                    multiply.setStyle("-fx-background-color: #eaeaea");
                    divide.setStyle("-fx-background-color: #eaeaea");
                }
                else if (signUsed == 2) {
                    plus.setStyle("-fx-background-color: #eaeaea");
                    minus.setStyle("-fx-background-color: #adadad");
                    multiply.setStyle("-fx-background-color: #eaeaea");
                    divide.setStyle("-fx-background-color: #eaeaea");
                }
                else if (signUsed == 3) {
                    plus.setStyle("-fx-background-color: #eaeaea");
                    minus.setStyle("-fx-background-color: #eaeaea");
                    multiply.setStyle("-fx-background-color: #adadad");
                    divide.setStyle("-fx-background-color: #eaeaea");
                }
                else if (signUsed == 4) {
                    plus.setStyle("-fx-background-color: #eaeaea");
                    minus.setStyle("-fx-background-color: #eaeaea");
                    multiply.setStyle("-fx-background-color: #eaeaea");
                    divide.setStyle("-fx-background-color: #adadad");
                }
                else {
                    signUsed = 0;
                    sign = 0;
                    step = 0;
                    System.out.println("APP ERROR: signUsed Error, not number 1~4");
                }
            }
            catch (NumberFormatException firstNotNum){
                System.out.println("User firstInput as " + inputOne.getText() + " which is not a number");
                inputError();
            }
        }
        else {
            System.out.println("User pressed sign when step = 1");
            signError();
        }
    }

    @FXML protected void equalsButton() {
        if (step == 1) {
            try {
                float secondInput = Float.parseFloat(inputOne.getText());
                System.out.println("User secondInput as " + secondInput + " which is a number");
                if (signUsed == 1) {
                    float sumTotal = firstInput + secondInput;
                    if (sumTotal == (int)sumTotal) {
                        sumResult.setText(String.valueOf(Math.round(sumTotal)));
                        System.out.println("added " + firstInput + " and " + secondInput + " to produce the integer " + Math.round(sumTotal));
                    }
                    else {
                        sumResult.setText(String.valueOf(sumTotal));
                        System.out.println("added " + firstInput + " and " + secondInput + " to produce the float " + sumTotal);
                    }
                }
                else if (signUsed == 2) {
                    float sumTotal = firstInput - secondInput;
                    if (sumTotal == (int)sumTotal) {
                        sumResult.setText(String.valueOf(Math.round(sumTotal)));
                        System.out.println("subtracted " + secondInput + " from " + firstInput + " to produce the integer " + Math.round(sumTotal));
                    }
                    else {
                        sumResult.setText(String.valueOf(sumTotal));
                        System.out.println("subtracted " + secondInput + " from " + firstInput + " to produce the float " + sumTotal);
                    }
                }
                else if (signUsed == 3) {
                    float sumTotal = firstInput * secondInput;
                    if (sumTotal == (int)sumTotal) {
                        sumResult.setText(String.valueOf(Math.round(sumTotal)));
                        System.out.println("multiplied " + firstInput + " and " + secondInput + " to produce the integer " + Math.round(sumTotal));
                    }
                    else {
                        sumResult.setText(String.valueOf(sumTotal));
                        System.out.println("multiplied " + firstInput + " and " + secondInput + " to produce the float " + sumTotal);
                    }
                }
                else if (signUsed == 4) {
                    float sumTotal = firstInput / secondInput;
                    if (sumTotal == (int)sumTotal) {
                        sumResult.setText(String.valueOf(Math.round(sumTotal)));
                        System.out.println("divided " + firstInput + " by " + secondInput + " to produce the integer " + Math.round(sumTotal));
                    }
                    else {
                        sumResult.setText(String.valueOf(sumTotal));
                        System.out.println("divided " + firstInput + " by " + secondInput + " to produce the float " + sumTotal);
                    }
                }
                else {
                    float sumTotal = secondInput;
                    if (sumTotal == (int)sumTotal) {
                        sumResult.setText(String.valueOf(Math.round(sumTotal)));
                        System.out.println("integer first input " + secondInput + " taken to sumResult");
                    }
                    else {
                        sumResult.setText(String.valueOf(sumTotal));
                        System.out.println("float first input " + secondInput + " taken to sumResult");
                    }
                }
                step = 0;
                System.out.println("step is " + step);
                inputOne.clear();
                sign = 0;
                signUsed = 0;
                plus.setStyle("-fx-background-color: #eaeaea");
                minus.setStyle("-fx-background-color: #eaeaea");
                multiply.setStyle("-fx-background-color: #eaeaea");
                divide.setStyle("-fx-background-color: #eaeaea");
            }
            catch (NumberFormatException secondNotNum){
                System.out.println("User secondInput as " + inputOne.getText() + " which is not a number");
                inputError();
            }
        }
        else {
            step = 1;
            signUsed = 0;
            equalsButton();
            System.out.println("User pressed equals when step = 0");
        }
    }
    /**
     * error code for when user doesn't input a numeral into the field
     * text: please put in a number
     * text in log: inputError code run
     */
    private void inputError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText("Input not a number");
        alert.setContentText("Please put in a number");

        alert.showAndWait();
        System.out.println("inputError code ran");
    }
    private void signError() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Button Error");
        alert.setHeaderText("Press equals for result");
        alert.setContentText(null);

        alert.showAndWait();
        System.out.println("buttonError code ran");
    }
}