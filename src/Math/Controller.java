/*
 * Copyright (©) 2019. Leo Player - Manly Selective Campus
 * made using JavaFX in IntelliJ
 */

package Math;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * fxml made using Scene Builder 2
 * Controller class used to initialise floats, then update with field when submit pressed
 * output also created after adding two numbers together
 */

public class Controller {
    private int stage = 0; //0 when no number, 1 when 1 number
    private int tempSign = 0; //used as temp storage of current sign
    private int sign = 0; //used as sign used in calculation
    private int numericTestResult = 0; //1 is number 0 is not number
    private int rootActive = 0; //whether root is active or not
    private double firstNumber = 0; //first number to calculate
    private double secondNumber = 0; //second number to calculate
    private double mathResult = 0; //result of calculation
    private double memOne; //first memory number
    private double memTwo; //second memory number
    private int moneySystemState = 0; //in the tens, 0 is initialisation
    private int adminLevel = 0; //0 not logged in, 1 logged in
    private int booted = 0; //1 booted, 0 not booted
    private int on = 0; //0 is off, 1 is on
    @FXML private TextArea mathOutput;
    @FXML private TextArea userInput;
    @FXML private Button plus;
    @FXML private Button minus;
    @FXML private Button multiply;
    @FXML private Button divide;
    @FXML private Button root;
    @FXML private Button memUn;
    @FXML private Button memDeux;
    @FXML private TextField controllerTitle;
    @FXML private TextField controllerOne;
    @FXML private TextField controllerTwo;
    @FXML private TextField controllerThree;
    @FXML private TextField controllerFour;
    @FXML private TextField controllerFive;


    private final Logger LOGGER = Logger.getLogger(Main.class.getName());
    @FXML protected void plusButton() {
        tempSign = 1;
        resetSignColor();
        plusColor();
        signPressed();
    }
    @FXML protected void minusButton() {
        tempSign = 2;
        resetSignColor();
        minusColor();
        signPressed();
    }
    @FXML protected void multiplyButton() {
        tempSign = 3;
        resetSignColor();
        multiplyColor();
        signPressed();
    }
    @FXML protected void divideButton() {
        tempSign = 4;
        resetSignColor();
        divideColor();
        signPressed();
    }
    @FXML protected void rootButton() {
        if (rootActive == 0) {
            rootColor();
            focus();
        }
        else {
            resetRootColor();
            focus();
        }
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
                    if (rootActive == 0) {
                        firstNumber = Double.parseDouble(mathOutput.getText());
                    }
                    else {
                        firstNumber = Math.sqrt(Double.parseDouble(mathOutput.getText()));
                        resetRootColor();
                    }
                    sign = tempSign;
                    stage = 1;
                    focus();
                }
            }
            else {
                numericTest();
                if (numericTestResult == 0) {
                    inputError();
                    resetSignColor();
                }
                else if (numericTestResult == 1) {
                    if (rootActive == 0) {
                        firstNumber = Double.parseDouble(userInput.getText());
                    }
                    else {
                        firstNumber = Math.sqrt(Double.parseDouble(userInput.getText()));
                        resetRootColor();
                    }
                    sign = tempSign;
                    stage = 1;
                    userInput.clear();
                    focus();
                }
            }
        }
        else if (stage == 1) {
            if (userInput.getText().equals("")) {
                sign = tempSign;
                focus();
            }
            else {
                numericTest();
                if (numericTestResult == 0) {
                    inputError();
                    resetSignColor();
                }
                else if (numericTestResult == 1){
                    if (rootActive == 0) {
                        secondNumber = Double.parseDouble(userInput.getText());
                    }
                    else {
                        secondNumber = Math.sqrt(Double.parseDouble(userInput.getText()));
                        resetRootColor();
                    }
                    math();
                    displayAnswer();
                    sign = tempSign;
                    firstNumber = mathResult;
                    userInput.clear();
                    focus();
                }
            }
        }
    }

    @FXML protected void equalsButton() {
        if (stage == 0 && userInput.getText().equals("")) {
            resetRootColor();
        }
        else if (stage == 0 && !userInput.getText().equals("")) {
            numericTest();
            if (numericTestResult == 0) {
                inputError();
            }
            else if (numericTestResult == 1) {
                if (rootActive == 0) {
                    mathResult = Double.parseDouble(userInput.getText());
                }
                else {
                    mathResult = Math.sqrt(Double.parseDouble(userInput.getText()));
                    resetRootColor();
                }
                displayAnswer();
                userInput.clear();
                focus();
            }
        }
        else if (stage == 1) {
            if (userInput.getText().equals("")) {
                mathResult = firstNumber;
                displayAnswer();
                stage = 0;
                resetSignColor();
                resetRootColor();
                focus();
            }
            else {
                numericTest();
                if (numericTestResult == 0) {
                    inputError();
                }
                else if (numericTestResult == 1) {
                    if (rootActive == 0) {
                        secondNumber = Double.parseDouble(userInput.getText());
                    }
                    else {
                        secondNumber = Math.sqrt(Double.parseDouble(userInput.getText()));
                        resetRootColor();
                    }
                    math();
                    displayAnswer();
                    stage = 0;
                    resetSignColor();
                    userInput.clear();
                    focus();
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
            Double.parseDouble(userInput.getText());
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
        resetRootColor();
        focus();
    }
    @FXML protected void allClear() {
        userInput.clear();
        mathOutput.clear();
        stage = 0;
        resetSignColor();
        resetRootColor();
        sign = 0;
        firstNumber = 0;
        secondNumber = 0;
        mathResult = 0;
        focus();
    }
    @FXML protected void pi() {
        userInput.setText(String.valueOf(Math.PI));
        focus();
    }
    @FXML protected void euler() {
        userInput.setText(String.valueOf(Math.E));
        focus();
    }
    @FXML protected void memOneCall() {
        if (memOne == (int)memOne) {
            userInput.setText(String.valueOf(Math.round(memOne)));
        }
        else userInput.setText(String.valueOf(memOne));
        focus();
    }
    @FXML protected void memOneSet() {
        if (userInput.getText().equals("")) {
            try {
                memOne = Double.parseDouble(mathOutput.getText());
                memUn.setStyle("-fx-background-color: #6ab873; -fx-border-color: #727272");
            }
            catch (NumberFormatException e) {
                LOGGER.log(Level.INFO, "memOneSet failed, mathResult: " + mathResult + " userInput: " + userInput.getText());
            }
        }
        else {
            numericTest();
            if (numericTestResult == 0) {
                inputError();
            }
            else if (numericTestResult == 1) {
                if (rootActive == 0) {
                    mathResult = Double.parseDouble(userInput.getText());
                }
                else {
                    mathResult = Math.sqrt(Double.parseDouble(userInput.getText()));
                    resetRootColor();
                }
                displayAnswer();
                memOne = mathResult;
                memUn.setStyle("-fx-background-color: #6ab873; -fx-border-color: #727272");
            }
        }
        focus();
    }
    @FXML protected void memOneDelete() {
        memOne = 0;
        memUn.setStyle("-fx-background-color: #eaeaea; -fx-border-color: #727272");
        focus();
    }
    @FXML protected void memTwoCall() {
        if (memTwo == (int)memTwo) {
            userInput.setText(String.valueOf(Math.round(memTwo)));
        }
        else userInput.setText(String.valueOf(memTwo));
        focus();
    }
    @FXML protected void memTwoSet() {
        if (!userInput.getText().equals("")) {
            numericTest();
            if (numericTestResult == 1) {
                if (rootActive == 1) {
                    mathResult = Math.sqrt(Double.parseDouble(userInput.getText()));
                    resetRootColor();
                }
                else {
                    mathResult = Double.parseDouble(userInput.getText());
                }
                displayAnswer();
                memTwo = mathResult;
                memDeux.setStyle("-fx-background-color: #6ab873; -fx-border-color: #727272");
            }
            else if (numericTestResult == 0) {
                inputError();
            }
        }
        else {
            try {
                memTwo = Double.parseDouble(mathOutput.getText());
                memDeux.setStyle("-fx-background-color: #6ab873; -fx-border-color: #727272");
            }
            catch (NumberFormatException e) {
                LOGGER.log(Level.INFO, "memTwoSet failed, mathResult: " + mathResult + " userInput: " + userInput.getText());
            }
        }
        focus();
    }
    @FXML protected void memTwoDelete() {
        memTwo = 0;
        memDeux.setStyle("-fx-background-color: #eaeaea; -fx-border-color: #727272");
        focus();
    }
    @FXML protected void zero() {
        userInput.setText(userInput.getText() + "0");
        focus();
    }
    @FXML protected void un() {
        userInput.setText(userInput.getText() + "1");
        focus();
    }
    @FXML protected void deux() {
        userInput.setText(userInput.getText() + "2");
        focus();
    }
    @FXML protected void trois() {
        userInput.setText(userInput.getText() + "3");
        focus();
    }
    @FXML protected void quatre() {
        userInput.setText(userInput.getText() + "4");
        focus();
    }
    @FXML protected void cinq() {
        userInput.setText(userInput.getText() + "5");
        focus();
    }
    @FXML protected void six() {
        userInput.setText(userInput.getText() + "6");
        focus();
    }
    @FXML protected void sept() {
        userInput.setText(userInput.getText() + "7");
        focus();
    }
    @FXML protected void huit() {
        userInput.setText(userInput.getText() + "8");
        focus();
    }
    @FXML protected void neuf() {
        userInput.setText(userInput.getText() + "9");
        focus();
    }
    @FXML protected void plusMinusButton() {
        if (userInput.getText().equals("")) {
            userInput.setText("-");
            focus();
        }
        else if (userInput.getText().equals("-")) {
            userInput.setText("");
            focus();
        }
        else {
            numericTest();
            if (numericTestResult == 0) {
                inputError();
            }
            else {
                if (Double.parseDouble(userInput.getText()) < 0) {
                    userInput.setText(userInput.getText().substring(1));
                }
                else if (Double.parseDouble(userInput.getText()) == 0) {
                    userInput.setText(String.valueOf(0));
                }
                else {
                    userInput.setText("-" + userInput.getText());
                }
                focus();
            }
        }
    }
    @FXML protected void decimalButton() {
        userInput.setText(userInput.getText() + ".");
        focus();
    }
    private void plusColor() {
        plus.setStyle("-fx-background-color: #adadad; -fx-border-color: #727272");
    }
    private void minusColor() {
        minus.setStyle("-fx-background-color: #adadad; -fx-border-color: #727272");
    }
    private void multiplyColor() {
        multiply.setStyle("-fx-background-color: #adadad; -fx-border-color: #727272");
    }
    private void divideColor() {
        divide.setStyle("-fx-background-color: #adadad; -fx-border-color: #727272");
    }
    private void resetSignColor() {
        plus.setStyle("-fx-background-color: #eaeaea; -fx-border-color: #727272");
        minus.setStyle("-fx-background-color: #eaeaea; -fx-border-color: #727272");
        multiply.setStyle("-fx-background-color: #eaeaea; -fx-border-color: #727272");
        divide.setStyle("-fx-background-color: #eaeaea; -fx-border-color: #727272");
    }
    private void rootColor() {
        root.setStyle("-fx-background-color: #adadad; -fx-border-color: #727272");
        rootActive = 1;
    }
    private void resetRootColor() {
        root.setStyle("-fx-background-color: #eaeaea; -fx-border-color: #727272");
        rootActive = 0;
    }
    private void inputError() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Input Error");
        alert.setHeaderText("Not a number");
        alert.setContentText("Please put in a number");
        LOGGER.log(Level.WARNING, "inputError");
        alert.showAndWait();
        focus();
    }
    private void focus() {
        userInput.requestFocus();
        userInput.end();
    }

    //money management system below

    @FXML protected void controllerOneButton() {
        switch (moneySystemState) {
            case 0:
                setOne();
                on = 1;
                break;
            case 1:
                if (adminLevel == 0) setTwo();
                else setThree();
                break;
            default:
                LOGGER.log(Level.SEVERE, "I am bad at coding 1");
        }
    }
    @FXML protected void controllerTwoButton() {
        switch (moneySystemState) {
            case 10:
                setTwenty();
                break;
            default:
                LOGGER.log(Level.SEVERE, "I am bad at coding 2");
        }
    }
    @FXML protected void controllerThreeButton() {
        switch (moneySystemState) {
            case 10:
                setThirty();
                break;
            default:
                LOGGER.log(Level.SEVERE, "I am bad at coding 3");
        }
    }
    @FXML protected void controllerFourButton() {
        switch (moneySystemState) {
            case 1:
                setTen();
                break;
            case 2:
                //check uname and password
                break;
            case 3:
                adminLevel = 0;
                setTwo();
                break;
            case 10:
                setForty();
                break;
            default:
                LOGGER.log(Level.SEVERE, "I am bad at coding 4");
        }
    }
    @FXML protected void controllerFiveButton() {
        switch (moneySystemState) {
            case 1:
                clearControllers();
                moneySystemState = 0;
                on = 0;
                break;
            case 2:
            case 3:
                setOne();
                break;
            case 10:
                setOne();
                booted = 0;
                break;
            case 20:
            case 30:
            case 40:
                setTen();
                break;
            default:
                LOGGER.log(Level.SEVERE, "I am bad at coding 5");
        }
    }
    private void setOne() { //boot screen
        clearControllers();
        if (on == 0) {
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            clearControllers();
                            new java.util.Timer().schedule(
                                    new java.util.TimerTask() {
                                        @Override
                                        public void run() {
                                            controllerThree.setStyle("-fx-alignment: CENTER");
                                            controllerThree.setText("Leo Player");
                                            new java.util.Timer().schedule(
                                                    new java.util.TimerTask() {
                                                        @Override
                                                        public void run() {
                                                            clearControllers();
                                                            new java.util.Timer().schedule(
                                                                    new java.util.TimerTask() {
                                                                        @Override
                                                                        public void run() {
                                                                            actuallySetOne();
                                                                        }
                                                                    },
                                                                    500
                                                            );
                                                        }
                                                    },
                                                    1000
                                            );
                                        }
                                    },
                                    300
                            );
                        }
                    },
                    300
            );
        }
        else {
            actuallySetOne();
        }
    }
    private void actuallySetOne() {
        controllerTitle.setText("Money Management System");
        controllerOne.setStyle("-fx-alignment: CENTER");
        Random rand = new Random();
        int randomMessage = rand.nextInt(10);
        switch (randomMessage) {
            case 0:
                controllerOne.setText("Approved by Jaden!");
                break;
            case 1:
                controllerOne.setText("\"France is just the northern part of Australia!\" - Fish, 2019");
                break;
            case 2:
                controllerOne.setText("something creative 2");
                break;
            case 3:
                controllerOne.setText("something creative 3");
                break;
            case 4:
                controllerOne.setText("something creative 4");
                break;
            case 5:
                controllerOne.setText("something creative 5");
                break;
            case 6:
                controllerOne.setText("something creative 6");
                break;
            case 7:
                controllerOne.setText("something creative 7");
                break;
            case 8:
                controllerOne.setText("something creative 8");
                break;
            case 9:
                controllerOne.setText("something creative 9");
                break;
        }
        controllerFour.setText("Boot");
        controllerFive.setText("Off");
        moneySystemState = 1;
    }
    private void setTwo() { //admin login
        clearControllers();
        controllerTitle.setText("Admin");
        controllerOne.setStyle("-fx-alignment: CENTER");
        controllerOne.setText("Input for access");
        controllerTwo.setText("(User Name)");
        controllerTwo.setEditable(true);
        controllerThree.setText("(Password)");
        controllerThree.setEditable(true);
        controllerFour.setText("Enter");
        controllerFive.setText("Back");
        moneySystemState = 2;
    }
    private void setThree() { //admin screen
        clearControllers();
        controllerTitle.setText("Admin Controls");
        controllerOne.setText("");
        controllerTwo.setText("");
        controllerThree.setText("");
        controllerFour.setText("Logout");
        controllerFive.setText("Back Without Logout");
        moneySystemState = 3;
    }
    private void setTen() { //main screen
        clearControllers();
        if (booted == 0) {
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            controllerOne.setStyle("-fx-alignment: CENTER");
                            controllerOne.setText("Money Management System");
                            new java.util.Timer().schedule(
                                    new java.util.TimerTask() {
                                        @Override
                                        public void run() {
                                            clearControllers();
                                            controllerTwo.setStyle("-fx-alignment: CENTER");
                                            controllerTwo.setText("Money Management System");
                                            new java.util.Timer().schedule(
                                                    new java.util.TimerTask() {
                                                        @Override
                                                        public void run() {
                                                            clearControllers();
                                                            controllerThree.setStyle("-fx-alignment: CENTER");
                                                            controllerThree.setText("Money Management System");
                                                            new java.util.Timer().schedule(
                                                                    new java.util.TimerTask() {
                                                                        @Override
                                                                        public void run() {
                                                                            clearControllers();
                                                                            controllerFour.setStyle("-fx-alignment: CENTER");
                                                                            controllerFour.setText("Money Management System");
                                                                            new java.util.Timer().schedule(
                                                                                    new java.util.TimerTask() {
                                                                                        @Override
                                                                                        public void run() {
                                                                                            clearControllers();
                                                                                            controllerFive.setStyle("-fx-alignment: CENTER");
                                                                                            controllerFive.setText("Money Management System");
                                                                                            new java.util.Timer().schedule(
                                                                                                    new java.util.TimerTask() {
                                                                                                        @Override
                                                                                                        public void run() {
                                                                                                            clearControllers();
                                                                                                            new java.util.Timer().schedule(
                                                                                                                    new java.util.TimerTask() {
                                                                                                                        @Override
                                                                                                                        public void run() {
                                                                                                                            actuallySetTen();
                                                                                                                        }
                                                                                                                    },
                                                                                                                    600
                                                                                                            );
                                                                                                        }
                                                                                                    },
                                                                                                    400
                                                                                            );
                                                                                        }
                                                                                    },
                                                                                    400
                                                                            );
                                                                        }
                                                                    },
                                                                    400
                                                            );
                                                        }
                                                    },
                                                    400
                                            );
                                        }
                                    },
                                    400
                            );
                        }
                    },
                    1000
            );
        }
        else {
            actuallySetTen();
        }
    }
    private void actuallySetTen() {
        controllerTitle.setText("Main Menu");
        //controllerOne.setText(""); to put something else in
        controllerTwo.setText("Check Balance");
        controllerThree.setText("Add to Balance");
        controllerFour.setText("Subtract from Balance");
        controllerFive.setText("Exit");
        moneySystemState = 10;
        booted = 1;
    }
    private void setTwenty() { //check balance
        clearControllers();
        controllerTitle.setText("Check Balance");
        controllerOne.setStyle("-fx-alignment: CENTER");
        controllerOne.setText("Balance");
        //balance
        controllerThree.setStyle("-fx-alignment: CENTER");
        controllerThree.setText("Your Balance");
        //users balance
        controllerFive.setText("Back");
        moneySystemState = 20;
    }
    private void setThirty() { //add
        clearControllers();
        controllerTitle.setText("Add to Balance");
        controllerOne.setStyle("-fx-alignment: CENTER");
        controllerOne.setText("Current Balance");
        //balance
        controllerThree.setText("(Amount to add)");
        controllerThree.setEditable(true);
        controllerFour.setText("Enter");
        controllerFive.setText("Back");
        moneySystemState = 30;
    }
    private void setForty() { //subtract
        clearControllers();
        controllerTitle.setText("Subtract from Balance");
        controllerOne.setStyle("-fx-alignment: CENTER");
        controllerOne.setText("Current Balance");
        //balance
        controllerThree.setText("(Amount to subtract)");
        controllerThree.setEditable(true);
        controllerFour.setText("Enter");
        controllerFive.setText("Back");
        moneySystemState = 40;
    }
    private void clearControllers() {
        controllerTitle.setText(null);
        clearControllers(controllerOne, controllerTwo, controllerThree, controllerFour, controllerFive);
    }
    private void clearControllers(TextField... testFields) {
        for (TextField textField : testFields) {
            textField.setText(null);
            textField.setStyle("-fx-alignment: CENTER-RIGHT");
            textField.setEditable(false);
        }
    }
}
