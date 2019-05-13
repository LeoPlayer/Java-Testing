/*
 * Copyright (©) 2019. Leo Player - Manly Selective Campus
 * made using JavaFX in IntelliJ
 */

package Math;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * fxml made using Scene Builder 2
 * Controller class used to initialise floats, then update with field when submit pressed
 * output also created after adding two numbers together
 */
public class Controller {
    @FXML private Label sumResult;
    @FXML private TextArea inputOne;
    @FXML private TextArea inputTwo;
    @FXML protected void sumSubmitButtonPressed() {
        try {
            Float.parseFloat(inputOne.getText());
            System.out.println("User inputOne as " + inputOne.getText() + " which is a number");
            try {
                Float.parseFloat(inputTwo.getText());
                System.out.println("User inputTwo as " + inputTwo.getText() + " which is a number");
                float firstInput = Float.parseFloat(inputOne.getText());
                float secondInput = Float.parseFloat(inputTwo.getText());
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
            catch (NumberFormatException is1not2){
                System.out.println("User inputTwo as " + inputTwo.getText() + " which is not a number");
                inputError();
            }
        }
        catch (NumberFormatException not1){
            System.out.println("User inputOne as " + inputOne.getText() + " which is not a number");
            try {
                Float.parseFloat(inputTwo.getText());
                System.out.println("User inputTwo as " + inputTwo.getText() + " which is a number");
            }
            catch (NumberFormatException not1not2){
                System.out.println("User inputTwo as " + inputTwo.getText() + " which is not a number");
            }
            inputError();
        }
    }

    /**
     * error code for when user doesn't input a numeral into the field
     * text: please put in a number
     * text in system: inputError code run
     */
    private void inputError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText("Please put in a number");

        alert.showAndWait();
        System.out.println("inputError code run");
    }
}