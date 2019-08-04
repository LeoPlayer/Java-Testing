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
    private int stage = 0;
    private int tempSign = 0;
    private int sign = 0;
    private float firstNumber = 0;
    private float secondNumber = 0;
    private float mathResult = 0;
    private int numericTestResult = 0; //1 is number 0 is not number
    private float memOne;
    private float memTwo;
    private int rootActive = 0;
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
    @FXML private TextField controllerTitle;
    @FXML private TextField controllerOne;
    @FXML private TextField controllerTwo;
    @FXML private TextField controllerThree;
    @FXML private TextField controllerFour;
    @FXML private TextField controllerFive;


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
    @FXML protected void rootButton() {
        if (rootActive == 0) {
            rootColor();
            userInput.requestFocus();
        }
        else {
            resetRootColor();
            userInput.requestFocus();
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
                    firstNumber = Float.parseFloat(mathOutput.getText());
                    sign = tempSign;
                    stage = 1;
                    userInput.requestFocus();
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
                    userInput.requestFocus();
                }
            }
        }
        else if (stage == 1) {
            if (userInput.getText().equals("")) {
                sign = tempSign;
                userInput.requestFocus();
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
                    userInput.requestFocus();
                }
            }
        }
    }

    @FXML protected void equalsButton() {
        if (stage == 0 && !userInput.getText().equals("")) {
            numericTest();
            if (numericTestResult == 0) {
                inputError();
            }
            else if (numericTestResult == 1) {
                mathResult = Float.parseFloat(userInput.getText());
                displayAnswer();
                userInput.clear();
                userInput.requestFocus();
            }
        }
        else if (stage == 1) {
            if (userInput.getText().equals("")) {
                mathResult = firstNumber;
                displayAnswer();
                stage = 0;
                resetSignColor();
                userInput.requestFocus();
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
                    userInput.requestFocus();
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
        userInput.requestFocus();
    }
    @FXML protected void allClear() {
        userInput.clear();
        mathOutput.clear();
        stage = 0;
        resetSignColor();
        sign = 0;
        firstNumber = 0;
        secondNumber = 0;
        userInput.requestFocus();
    }
    @FXML protected void pi() {
        userInput.setText(String.valueOf(Math.PI));
        userInput.requestFocus();
    }
    @FXML protected void euler() {
        userInput.setText(String.valueOf(Math.E));
        userInput.requestFocus();
    }
    @FXML protected void memOneCall() {
        if (memOne == (int)memOne) {
            userInput.setText(String.valueOf(Math.round(memOne)));
        }
        else userInput.setText(String.valueOf(memOne));
        userInput.requestFocus();
    }
    @FXML protected void memOneSet() {
        if (userInput.getText().equals("")) {
            memOne = Float.parseFloat(mathOutput.getText());
        }
        else {
            numericTest();
            if (numericTestResult == 0) {
                inputError();
            }
            else if (numericTestResult == 1) {
                mathResult = Float.parseFloat(userInput.getText());
                displayAnswer();
                userInput.clear();
                stage = 0;
                memOne = mathResult;
                userInput.requestFocus();
            }
        }
        userInput.requestFocus();
    }
    @FXML protected void memOneDelete() {
        memOne = 0;
        userInput.requestFocus();
    }
    @FXML protected void memTwoCall() {
        if (memTwo == (int)memTwo) {
            userInput.setText(String.valueOf(Math.round(memTwo)));
        }
        else userInput.setText(String.valueOf(memTwo));
        userInput.requestFocus();
    }
    @FXML protected void memTwoSet() {
        if (userInput.getText().equals("")) {
            memTwo = Float.parseFloat(mathOutput.getText());
        }
        else {
            numericTest();
            if (numericTestResult == 0) {
                inputError();
            }
            else if (numericTestResult == 1) {
                stage = 0;
                mathResult = Float.parseFloat(userInput.getText());
                displayAnswer();
                userInput.clear();
                memTwo = mathResult;
                userInput.requestFocus();
            }
        }
        userInput.requestFocus();
    }
    @FXML protected void memTwoDelete() {
        memTwo = 0;
        userInput.requestFocus();
    }
    @FXML protected void memoryDeleteAll() {
        memOne = 0;
        memTwo = 0;
        userInput.requestFocus();
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
    private void rootColor() {
        root.setStyle("-fx-background-color: #adadad");
        rootActive = 1;
    }
    private void resetRootColor() {
        root.setStyle("-fx-background-color: #eaeaea");
        rootActive = 0;
    }
    private void inputError() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Input Error");
        alert.setHeaderText("Not a number");
        alert.setContentText("Please put in a number");
        LOGGER.log(Level.WARNING, "inputError");
        alert.showAndWait();
        userInput.requestFocus();
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
        controllerThree.setText("(Password)");
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
        controllerTwo.setStyle("-fx-alignment: CENTER");
        controllerTwo.setText("Balance");
        //balance
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
        controllerFour.setText("Enter");
        controllerFive.setText("Back");
        moneySystemState = 40;
    }
    private void clearControllers() {
        controllerTitle.setText(null);
        controllerOne.setText(null);
        controllerOne.setStyle("-fx-alignment: CENTER-RIGHT");
        controllerTwo.setText(null);
        controllerTwo.setStyle("-fx-alignment: CENTER-RIGHT");
        controllerThree.setText(null);
        controllerThree.setStyle("-fx-alignment: CENTER-RIGHT");
        controllerFour.setText(null);
        controllerFour.setStyle("-fx-alignment: CENTER-RIGHT");
        controllerFive.setText(null);
        controllerFive.setStyle("-fx-alignment: CENTER-RIGHT");
    }
}
