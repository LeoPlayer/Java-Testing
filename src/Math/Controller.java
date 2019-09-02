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

import java.io.File;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Math.Main.*;

/**
 * fxml made using Scene Builder 2
 * Controller used to run whole program
 * everything initialised in controller, then inputs processed here
 * main.java initialises language etc. and starts application
 */

public class Controller {
    private int stage = 0; //0 when no number, 1 when 1 number
    private int tempSign = 0; //used as temp storage of current sign
    private int sign = 0; //used as sign used in calculation
    private int numericTestResult = 0; //1 is number 0 is not number
    private int rootActive = 0; //whether root is active or not
    private BigDecimal firstNumber = new BigDecimal(0); //first number to calculate
    private BigDecimal secondNumber = new BigDecimal(0); //second number to calculate
    private BigDecimal mathResult = new BigDecimal(0); //result of calculation
    private BigDecimal memOne; //first memory number
    private BigDecimal memTwo; //second memory number
    private int moneySystemState = 0; //in the tens, 0 is initialisation
    private int adminLevel = 0; //0 not logged in, 1 logged in
    private int booted = 0; //1 booted, 0 not booted
    private int on = 0; //0 is off, 1 is on
    private String adminColor = "#ff0000"; //colour when admin logged in
    private String textColor = "#5a5a5a"; //colour of all text displayed
    private int user = 0; //what user is logged in
    private int numericTestMoneyResult = 0; //1 is number 0 is not number
    private int UserNumber = 0; //which user admin is accessing
    private boolean buttonPressable = true;
    @FXML
    private TextArea output;
    @FXML
    private TextArea input;
    @FXML
    private Button plus;
    @FXML
    private Button minus;
    @FXML
    private Button multiply;
    @FXML
    private Button divide;
    @FXML
    private Button root;
    @FXML
    private Button memUn;
    @FXML
    private Button memUnSet;
    @FXML
    private Button memUnDelete;
    @FXML
    private Button memDeux;
    @FXML
    private Button memDeuxSet;
    @FXML
    private Button memDeuxDelete;
    @FXML
    private Button c2mms;
    @FXML
    private Button mms2c;
    @FXML
    private Button adminButton;
    @FXML
    private Button zero;
    @FXML
    private Button one;
    @FXML
    private Button two;
    @FXML
    private Button three;
    @FXML
    private Button four;
    @FXML
    private Button five;
    @FXML
    private Button six;
    @FXML
    private Button seven;
    @FXML
    private Button eight;
    @FXML
    private Button nine;
    @FXML
    private Button allClear;
    @FXML
    private Button clear;
    @FXML
    private Button decimal;
    @FXML
    private Button plusMinus;
    @FXML
    private Button equals;
    @FXML
    private Button pi;
    @FXML
    private Button euler;
    @FXML
    public TextField controllerTitle;
    @FXML
    public TextField controllerOne;
    @FXML
    public TextField controllerTwo;
    @FXML
    public TextField controllerThree;
    @FXML
    public TextField controllerFour;
    @FXML
    public TextField controllerFive;


    private final Logger LOGGER = Logger.getLogger(Main.class.getName());

    @FXML
    protected void plusButton() { //sets colour of button and runs signPressed
        resetSignColor();
        plusColor();
        signPressed();
    }

    @FXML
    protected void minusButton() {
        resetSignColor();
        minusColor();
        signPressed();
    }

    @FXML
    protected void multiplyButton() {
        resetSignColor();
        multiplyColor();
        signPressed();
    }

    @FXML
    protected void divideButton() {
        resetSignColor();
        divideColor();
        signPressed();
    }

    @FXML
    protected void rootButton() {
        if (rootActive == 0) {
            rootColor();
            focus();
        } else {
            resetRootColor();
            focus();
        }
    }

    private void signPressed() { //code ran when sign button has been pressed
        if (stage == 0) {
            if (input.getText().equals("")) {
                if (output.getText().equals("")) {
                    LOGGER.log(Level.INFO, "signPressed, s=0, no uI mO");
                    inputError();
                    resetSignColor();
                } else {
                    if (rootActive == 0) {
                        firstNumber = new BigDecimal(output.getText());
                    } else {
                        firstNumber = new BigDecimal(output.getText()).sqrt(new MathContext(1000));
                        resetRootColor();
                    }
                    sign = tempSign;
                    stage = 1;
                    focus();
                }
            } else {
                numericTest();
                if (numericTestResult == 0) {
                    inputError();
                    resetSignColor();
                } else if (numericTestResult == 1) {
                    if (rootActive == 0) {
                        firstNumber = new BigDecimal(input.getText());
                    } else {
                        firstNumber = new BigDecimal(input.getText()).sqrt(new MathContext(1000));
                        resetRootColor();
                    }
                    sign = tempSign;
                    stage = 1;
                    input.clear();
                    focus();
                }
            }
        } else if (stage == 1) {
            if (input.getText().equals("")) {
                sign = tempSign;
                focus();
            } else {
                numericTest();
                if (numericTestResult == 0) {
                    inputError();
                    resetSignColor();
                } else if (numericTestResult == 1) {
                    if (rootActive == 0) {
                        secondNumber = new BigDecimal(input.getText());
                    } else {
                        secondNumber = new BigDecimal(input.getText()).sqrt(new MathContext(1000));
                        resetRootColor();
                    }
                    math();
                    displayAnswer();
                    sign = tempSign;
                    firstNumber = mathResult;
                    input.clear();
                    focus();
                }
            }
        }
    }

    @FXML
    protected void equalsButton() { //code ran when equals button presesd
        if (stage == 0 && input.getText().equals("")) {
            resetRootColor();
        } else if (stage == 0 && !input.getText().equals("")) {
            numericTest();
            if (numericTestResult == 0) {
                inputError();
            } else if (numericTestResult == 1) {
                if (rootActive == 0) {
                    mathResult = new BigDecimal(input.getText());
                } else {
                    mathResult = new BigDecimal(input.getText()).sqrt(new MathContext(1000));
                    resetRootColor();
                }
                displayAnswer();
                input.clear();
                focus();
            }
        } else if (stage == 1) {
            if (input.getText().equals("")) {
                mathResult = firstNumber;
                displayAnswer();
                stage = 0;
                resetSignColor();
                resetRootColor();
                focus();
            } else {
                numericTest();
                if (numericTestResult == 0) {
                    inputError();
                } else if (numericTestResult == 1) {
                    if (rootActive == 0) {
                        secondNumber = new BigDecimal(input.getText());
                    } else {
                        secondNumber = new BigDecimal(input.getText()).sqrt(new MathContext(1000));
                        resetRootColor();
                    }
                    math();
                    displayAnswer();
                    stage = 0;
                    resetSignColor();
                    input.clear();
                    focus();
                }
            }
        }
    }

    private void math() { //math calculations all done here
        if (sign == 1) mathResult = firstNumber.add(secondNumber);
        else if (sign == 2) mathResult = firstNumber.subtract(secondNumber);
        else if (sign == 3) mathResult = firstNumber.multiply(secondNumber);
        else if (sign == 4) mathResult = firstNumber.divide(secondNumber, 100, RoundingMode.HALF_UP);
        else {
            LOGGER.log(Level.SEVERE, "sign not 1~4 at math()");
            LOGGER.severe("Sign is: " + sign);
            LOGGER.severe("exit 10");
            System.exit(10);
        }
    }

    private void numericTest() { //tests if input is numerical
        try {
            new BigDecimal(input.getText());
            numericTestResult = 1;
        } catch (RuntimeException inputNotNum) {
            numericTestResult = 0;
            LOGGER.log(Level.WARNING, "input not numeral: " + input.getText());
            LOGGER.log(Level.WARNING, "input not numeral: " + Arrays.toString(inputNotNum.getStackTrace()));
        }
    }

    private void displayAnswer() { //shows answer as int if int, else as float
        if (mathResult.equals(mathResult.setScale(0, RoundingMode.HALF_UP))) {
            output.setText(String.valueOf(mathResult.setScale(0, RoundingMode.HALF_UP)));
        } else output.setText(String.valueOf(mathResult));
    }

    @FXML
    protected void clear() { //clears input
        input.clear();
        resetRootColor();
        focus();
    }

    @FXML
    protected void allClear() { //clears calculator
        input.clear();
        output.clear();
        stage = 0;
        resetSignColor();
        resetRootColor();
        sign = 0;
        firstNumber = new BigDecimal(0);
        secondNumber = new BigDecimal(0);
        mathResult = new BigDecimal(0);
        focus();
    }

    @FXML
    protected void pi() {
        input.setText(String.valueOf(new BigDecimal(3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679)));
        focus();
    }

    @FXML
    protected void euler() {
        input.setText(String.valueOf(new BigDecimal(2.7182818284590452353602874713526624977572470936999595749669676277240766303535475945713821785251664274)));
        focus();
    }

    @FXML
    protected void memOneCall() { //sets input as value in memory 1
        if (memOne.equals(memOne.setScale(0, RoundingMode.HALF_UP))) {
            input.setText(String.valueOf(memOne.setScale(0, RoundingMode.HALF_UP)));
        } else input.setText(String.valueOf(memOne));
        focus();
    }

    @FXML
    protected void memOneSet() { //sets value of memory 1
        if (input.getText().equals("")) {
            try {
                memOne = new BigDecimal(output.getText());
                memUn.setStyle("-fx-background-color: #6ab873; -fx-border-color: #727272");
            } catch (NumberFormatException memOne) {
                LOGGER.log(Level.INFO, "memOneSet failed, mathResult: " + mathResult + " userInput: " + input.getText());
                LOGGER.info(String.valueOf(memOne));
            }
        } else {
            numericTest();
            if (numericTestResult == 0) {
                inputError();
            } else if (numericTestResult == 1) {
                if (rootActive == 0) {
                    mathResult = new BigDecimal(input.getText());
                } else {
                    mathResult = new BigDecimal(input.getText()).sqrt(new MathContext(1000));
                    resetRootColor();
                }
                displayAnswer();
                memOne = mathResult;
                memUn.setStyle("-fx-background-color: #6ab873; -fx-border-color: #727272");
            }
        }
        focus();
    }

    @FXML
    protected void memOneDelete() { //deletes value stored in memory 1
        memOne = new BigDecimal(0);
        memUn.setStyle("-fx-background-color: #eaeaea; -fx-border-color: #727272");
        focus();
    }

    @FXML
    protected void memTwoCall() {
        if (memTwo.equals(memTwo.setScale(0, RoundingMode.HALF_UP))) {
            input.setText(String.valueOf(memTwo.setScale(0, RoundingMode.HALF_UP)));
        } else input.setText(String.valueOf(memTwo));
        focus();
    }

    @FXML
    protected void memTwoSet() {
        if (!input.getText().equals("")) {
            numericTest();
            if (numericTestResult == 1) {
                if (rootActive == 1) {
                    mathResult = new BigDecimal(input.getText()).sqrt(new MathContext(1000));
                    resetRootColor();
                } else {
                    mathResult = new BigDecimal(input.getText());
                }
                displayAnswer();
                memTwo = mathResult;
                memDeux.setStyle("-fx-background-color: #6ab873; -fx-border-color: #727272");
            } else if (numericTestResult == 0) {
                inputError();
            }
        } else {
            try {
                memTwo = new BigDecimal(output.getText());
                memDeux.setStyle("-fx-background-color: #6ab873; -fx-border-color: #727272");
            } catch (NumberFormatException memTwo) {
                LOGGER.log(Level.INFO, "memTwoSet failed, mathResult: " + mathResult + " userInput: " + input.getText());
                LOGGER.info(String.valueOf(memTwo));
            }
        }
        focus();
    }

    @FXML
    protected void memTwoDelete() {
        memTwo = new BigDecimal(0);
        memDeux.setStyle("-fx-background-color: #eaeaea; -fx-border-color: #727272");
        focus();
    }

    @FXML
    protected void zero() { //when 0 on keypad is pressed
        input.setText(input.getText() + "0");
        focus();
    }

    @FXML
    protected void un() {
        input.setText(input.getText() + "1");
        focus();
    }

    @FXML
    protected void deux() {
        input.setText(input.getText() + "2");
        focus();
    }

    @FXML
    protected void trois() {
        input.setText(input.getText() + "3");
        focus();
    }

    @FXML
    protected void quatre() {
        input.setText(input.getText() + "4");
        focus();
    }

    @FXML
    protected void cinq() {
        input.setText(input.getText() + "5");
        focus();
    }

    @FXML
    protected void six() {
        input.setText(input.getText() + "6");
        focus();
    }

    @FXML
    protected void sept() {
        input.setText(input.getText() + "7");
        focus();
    }

    @FXML
    protected void huit() {
        input.setText(input.getText() + "8");
        focus();
    }

    @FXML
    protected void neuf() {
        input.setText(input.getText() + "9");
        focus();
    }

    @FXML
    protected void plusMinusButton() {
        if (input.getText().equals("")) {
            input.setText("-");
            focus();
        } else if (input.getText().equals("-")) {
            input.setText("");
            focus();
        } else {
            numericTest();
            if (numericTestResult == 0) {
                inputError();
            } else {
                if (Double.parseDouble(input.getText()) < 0) {
                    input.setText(input.getText().substring(1));
                } else if (Double.parseDouble(input.getText()) == 0) {
                    input.setText(String.valueOf(0));
                } else {
                    input.setText("-" + input.getText());
                }
                focus();
            }
        }
    }

    @FXML
    protected void decimalButton() {
        input.setText(input.getText() + ".");
        focus();
    }

    private void plusColor() {
        plus.setStyle("-fx-background-color: #adadad; -fx-border-color: #727272; -fx-text-fill: " + textColor + ";");
        tempSign = 1;
    }

    private void minusColor() {
        minus.setStyle("-fx-background-color: #adadad; -fx-border-color: #727272; -fx-text-fill: " + textColor + ";");
        tempSign = 2;
    }

    private void multiplyColor() {
        multiply.setStyle("-fx-background-color: #adadad; -fx-border-color: #727272; -fx-text-fill: " + textColor + ";");
        tempSign = 3;
    }

    private void divideColor() {
        divide.setStyle("-fx-background-color: #adadad; -fx-border-color: #727272; -fx-text-fill: " + textColor + ";");
        tempSign = 4;
    }

    private void resetSignColor() {
        plus.setStyle("-fx-background-color: #eaeaea; -fx-border-color: #727272; -fx-text-fill: " + textColor + ";");
        minus.setStyle("-fx-background-color: #eaeaea; -fx-border-color: #727272; -fx-text-fill: " + textColor + ";");
        multiply.setStyle("-fx-background-color: #eaeaea; -fx-border-color: #727272; -fx-text-fill: " + textColor + ";");
        divide.setStyle("-fx-background-color: #eaeaea; -fx-border-color: #727272; -fx-text-fill: " + textColor + ";");
        tempSign = 0;
    }

    private void rootColor() {
        root.setStyle("-fx-background-color: #adadad; -fx-border-color: #727272; -fx-text-fill: " + textColor + ";");
        rootActive = 1;
    }

    private void resetRootColor() {
        root.setStyle("-fx-background-color: #eaeaea; -fx-border-color: #727272; -fx-text-fill: " + textColor + ";");
        rootActive = 0;
    }

    private void inputError() {
        Alert inputError = new Alert(Alert.AlertType.WARNING);
        inputError.setTitle(languageString[35]);
        inputError.setHeaderText(languageString[36]);
        inputError.setContentText(languageString[37]);
        LOGGER.log(Level.WARNING, "inputError");
        inputError.showAndWait();
        focus();
    }

    private void focus() { //moves cursor to input
        input.requestFocus();
        input.end();
    }

    //money management system below

    @FXML
    protected void controllerOneButton() { //when top button of MMS pressed
        if (buttonPressable) {
            switch (moneySystemState) {
                case 0:
                    setOne();
                    break;
                case 1:
                    if (adminLevel == 0) setTwo();
                    else setThree();
                    break;
                case 3:
                case 5:
                    setFour();
                    break;
                case 4:
                case 8:
                    setThree();
                    break;
                case 6:
                    setFive();
                    break;
                case 7:
                    adminColor = controllerOne.getText();
                    adminButton.setStyle("-fx-background-color: " + adminColor + ";");
                    break;
                case 9:
                    setEight();
                    break;
            }
            focus();
        }
    }

    @FXML
    protected void controllerTwoButton() {
        if (buttonPressable) {
            switch (moneySystemState) {
                case 3:
                    setSeven();
                    break;
                case 4:
                    setLang1();
                    csvLanguageInitialise(new File("src/Math/CSV/english.csv"));
                    setLang2();
                    break;
                case 7:
                    textColor = controllerTwo.getText();
                    setTextColor();
                    break;
                case 8:
                    UserNumber = 1;
                    setNinety();
                    break;
                case 9:
                    UserNumber = 4;
                    setNinety();
                    break;
                case 10:
                    setTwenty();
                    break;
            }
            focus();
        }
    }

    @FXML
    protected void controllerThreeButton() {
        if (buttonPressable) {
            switch (moneySystemState) {
                case 3:
                    setEight();
                    break;
                case 4:
                    setLang1();
                    csvLanguageInitialise(new File("src/Math/CSV/french.csv"));
                    setLang2();
                    break;
                case 5:
                    setLang1();
                    csvLanguageInitialise(new File("src/Math/CSV/korean.csv"));
                    setLang2();
                    break;
                case 8:
                    UserNumber = 2;
                    setNinety();
                    break;
                case 9:
                    UserNumber = 5;
                    setNinety();
                    break;
                case 10:
                    setThirty();
                    break;
            }
            focus();
        }
    }

    @FXML
    protected void controllerFourButton() {
        if (buttonPressable) {
            switch (moneySystemState) {
                case 1:
                    setHundred();
                    break;
                case 2:
                    try {
                        if (!Main.usernameInitialisationFailed && !Main.passwordInitialisationFailed) {
                            if (controllerTwo.getText().equals(superSecurePasswordDecoder(Main.username)) &&
                                    controllerThree.getText().equals(superSecurePasswordDecoder(Main.superSecurePassword))) {
                                LOGGER.info("admin uname success: " + controllerTwo.getText());
                                LOGGER.info("admin pword success: " + controllerThree.getText());
                                setThree();
                                adminLevel = 1;
                                adminButton.setStyle("-fx-background-color: " + adminColor + ";");
                            } else {
                                LOGGER.info("admin uname fail: " + controllerTwo.getText());
                                LOGGER.info("admin pword fail: " + controllerThree.getText());
                            }
                        }
                    } catch (RuntimeException e) {
                        LOGGER.info("admin uname fail: " + controllerTwo.getText());
                        LOGGER.info("admin pword fail: " + controllerThree.getText());
                    }
                    break;
                case 3:
                    adminLevel = 0;
                    adminButton.setStyle("-fx-background-color: #13b839");
                    setTwo();
                    break;
                case 4:
                    setLang1();
                    csvLanguageInitialise(new File("src/Math/CSV/japanese.csv"));
                    setLang2();
                    break;
                case 5:
                    setLang1();
                    csvLanguageInitialise(new File("src/Math/CSV/schinese.csv"));
                    setLang2();
                    break;
                case 8:
                    UserNumber = 3;
                    setNinety();
                    break;
                case 10:
                    setForty();
                    break;
                case 30:
                    numericTestMoney();
                    if (numericTestMoneyResult == 1) {
                        money[user] = String.valueOf(new BigDecimal(money[user]).add(new BigDecimal(controllerThree.getText())));
                        Main.csvMoneyWriter();
                        setFifty();
                    }
                    break;
                case 40:
                    numericTestMoney();
                    if (numericTestMoneyResult == 1) {
                        money[user] = String.valueOf(new BigDecimal(money[user]).subtract(new BigDecimal(controllerThree.getText())));
                        Main.csvMoneyWriter();
                        setFifty();
                    }
                    break;
                case 90:
                    numericTestMoney();
                    if (numericTestMoneyResult == 1) {
                        money[UserNumber] = controllerThree.getText();
                        Main.csvMoneyWriter();
                        setNinety();
                    }
                    break;
                case 100:
                    try {
                        if (controllerTwo.getText().equals(Main.user1[1]) &&
                                controllerThree.getText().equals(superSecurePasswordDecoder(Main.user1[2]))) {
                            user = 1;
                            LOGGER.info("user 1 logged in");
                            logIn();
                        } else if (controllerTwo.getText().equals(Main.user2[1]) &&
                                controllerThree.getText().equals(superSecurePasswordDecoder(Main.user2[2]))) {
                            user = 2;
                            LOGGER.info("user 2 logged in");
                            logIn();
                        } else if (controllerTwo.getText().equals(Main.user3[1]) &&
                                controllerThree.getText().equals(superSecurePasswordDecoder(Main.user3[2]))) {
                            user = 3;
                            LOGGER.info("user 3 logged in");
                            logIn();
                        } else if (controllerTwo.getText().equals(Main.user4[1]) &&
                                controllerThree.getText().equals(superSecurePasswordDecoder(Main.user4[2]))) {
                            user = 4;
                            LOGGER.info("user 4 logged in");
                            logIn();
                        } else if (controllerTwo.getText().equals(Main.user5[1]) &&
                                controllerThree.getText().equals(superSecurePasswordDecoder(Main.user5[2]))) {
                            user = 5;
                            LOGGER.info("user 5 logged in");
                            logIn();
                        } else {
                            LOGGER.info("user login failed, uname: " + controllerTwo.getText() + ", pword: " + controllerThree.getText());
                        }
                    } catch (RuntimeException e) {
                        LOGGER.info("user login failed, uname: " + controllerTwo.getText() + ", pword: " + controllerThree.getText());
                    }
                    break;
            }
            focus();
        }
    }

    @FXML
    protected void controllerFiveButton() {
        if (buttonPressable) {
            switch (moneySystemState) {
                case 1:
                    clearControllers();
                    moneySystemState = 0;
                    on = 0;
                    buttonPressable = true;
                    break;
                case 2:
                case 3:
                case 100:
                    setOne();
                    break;
                case 4:
                    setFive();
                    break;
                case 5:
                    setSix();
                    break;
                case 7:
                    setThree();
                    break;
                case 8:
                    setNine();
                    break;
                case 10:
                    setHundred();
                    booted = 0;
                    if (adminLevel == 0) {
                        adminButton.setStyle("-fx-background-color: #13b839");
                    } else {
                        adminButton.setStyle("-fx-background-color: " + adminColor + ";");
                    }
                    break;
                case 20:
                case 30:
                case 40:
                    setTen();
                    break;
                case 90:
                    if (UserNumber <= 3) {
                        setEight();
                    } else {
                        setNine();
                    }
                    break;
            }
            focus();
        }
    }

    private String superSecurePasswordDecoder(String hex) { //converts hex to string

        StringBuilder sb = new StringBuilder();

        //split into two characters
        for (int i = 0; i < hex.length() - 1; i += 2) {
            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char) decimal);
        }
        return sb.toString();
    }

    private void setOne() { //boot screen
        clearControllers();
        if (on == 0) {
            try {
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
            } catch (ArrayIndexOutOfBoundsException e) {
                LOGGER.warning("setOne timer fail: " + Arrays.toString(e.getStackTrace()));
                actuallySetOne();
            }
        } else {
            actuallySetOne();
        }
    }

    private void actuallySetOne() {
        on = 1;
        controllerTitle.setText(languageString[0]);
        setCenterControllers(controllerOne);
        Random rand = new Random();
        int randomMessage = rand.nextInt(10);
        switch (randomMessage) {
            case 0:
                controllerOne.setText(languageString[1]);
                break;
            case 1:
                controllerOne.setText(languageString[2]);
                break;
            case 2:
                controllerOne.setText(languageString[3]);
                break;
            case 3:
                controllerOne.setText(languageString[4]);
                break;
            case 4:
                controllerOne.setText(languageString[5]);
                break;
            case 5:
                controllerOne.setText(languageString[6]);
                break;
            case 6:
                controllerOne.setText(languageString[7]);
                break;
            case 7:
                controllerOne.setText(languageString[8]);
                break;
            case 8:
                controllerOne.setText(languageString[9]);
                break;
            case 9:
                controllerOne.setText(languageString[10]);
                break;
            default:
                LOGGER.log(Level.SEVERE, "error comment");
                LOGGER.severe("exit 11");
                System.exit(11);
        }
        controllerFour.setText(languageString[11]);
        controllerFive.setText(languageString[12]);
        moneySystemState = 1;
        buttonPressable = true;
    }

    private void setTwo() { //admin login
        clearControllers();
        controllerTitle.setText(languageString[13]);
        setCenterControllers(controllerOne);
        setEditableControllers(controllerTwo, controllerThree);
        controllerOne.setText(languageString[14]);
        controllerTwo.setPromptText(languageString[15]);
        controllerThree.setPromptText(languageString[16]);
        controllerFour.setText(languageString[17]);
        controllerFive.setText(languageString[18]);
        moneySystemState = 2;
        buttonPressable = true;
    }

    private void setThree() { //admin screen
        clearControllers();
        controllerTitle.setText(languageString[19]);
        setCenterControllers(controllerOne,controllerTwo,controllerThree);
        controllerOne.setText(languageString[20]);
        controllerTwo.setText(languageString[21]);
        controllerThree.setText("Money Controls");
        controllerFour.setText(languageString[23]);
        controllerFive.setText(languageString[24]);
        moneySystemState = 3;
        buttonPressable = true;
    }

    private void setFour() { //language 1
        clearControllers();
        controllerTitle.setText(languageString[20]);
        controllerOne.setText(languageString[18]);
        controllerTwo.setText("English");
        controllerThree.setText("Français");
        controllerFour.setText("日本語");
        controllerFive.setText(languageString[46]);
        moneySystemState = 4;
        buttonPressable = true;
    }

    private void setFive() { //language 2
        clearControllers();
        controllerTitle.setText(languageString[20]);
        controllerOne.setText(languageString[47]);
        controllerTwo.setText("Italiano");
        controllerThree.setText("한국인");
        controllerFour.setText("中文");
        controllerFive.setText(languageString[46]);
        moneySystemState = 5;
        buttonPressable = true;
    }

    private void setSix() { //language 3
        clearControllers();
        controllerTitle.setText(languageString[20]);
        controllerOne.setText(languageString[47]);
        controllerTwo.setText("Deutsche");
        controllerThree.setText("русский");
        controllerFour.setText("हिंदी");
        moneySystemState = 6;
        buttonPressable = true;
    }

    private void setSeven() { //appearance
        clearControllers();
        controllerTitle.setText(languageString[21]);
        setEditableControllers(controllerOne, controllerTwo);
        controllerOne.setPromptText(languageString[48]);
        controllerTwo.setPromptText(languageString[49]);
        controllerFive.setText(languageString[18]);
        moneySystemState = 7;
        buttonPressable = true;
    }

    private void setEight() { //set money for people 1
        clearControllers();
        controllerTitle.setText("Money Controls");
        controllerOne.setText(languageString[18]);
        controllerTwo.setText(UNameShown[1]);
        controllerThree.setText(UNameShown[2]);
        controllerFour.setText(UNameShown[3]);
        controllerFive.setText(languageString[46]);
        moneySystemState = 8;
        buttonPressable = true;
    }
    private void setNine() { //set money for people 2
        clearControllers();
        controllerTitle.setText("Money Controls");
        controllerOne.setText(languageString[47]);
        controllerTwo.setText(UNameShown[4]);
        controllerThree.setText(UNameShown[5]);
        moneySystemState = 9;
        buttonPressable = true;
    }
    //admin money set
    private void setTen() { //main screen
        clearControllers();
        if (booted == 0) {
            try {
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                controllerOne.setStyle("-fx-alignment: CENTER; -fx-text-fill: " + textColor + ";");
                                controllerOne.setText(languageString[0]);
                                new java.util.Timer().schedule(
                                        new java.util.TimerTask() {
                                            @Override
                                            public void run() {
                                                clearControllers();
                                                controllerTwo.setStyle("-fx-alignment: CENTER; -fx-text-fill: " + textColor + ";");
                                                controllerTwo.setText(languageString[0]);
                                                new java.util.Timer().schedule(
                                                        new java.util.TimerTask() {
                                                            @Override
                                                            public void run() {
                                                                clearControllers();
                                                                controllerThree.setStyle("-fx-alignment: CENTER; -fx-text-fill: " + textColor + ";");
                                                                controllerThree.setText(languageString[0]);
                                                                new java.util.Timer().schedule(
                                                                        new java.util.TimerTask() {
                                                                            @Override
                                                                            public void run() {
                                                                                clearControllers();
                                                                                controllerFour.setStyle("-fx-alignment: CENTER; -fx-text-fill: " + textColor + ";");
                                                                                controllerFour.setText(languageString[0]);
                                                                                new java.util.Timer().schedule(
                                                                                        new java.util.TimerTask() {
                                                                                            @Override
                                                                                            public void run() {
                                                                                                clearControllers();
                                                                                                controllerFive.setStyle("-fx-alignment: CENTER; -fx-text-fill: " + textColor + ";");
                                                                                                controllerFive.setText(languageString[0]);
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
                                                                                                                                booted = 1;
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
            } catch (ArrayIndexOutOfBoundsException e) {
                LOGGER.warning("setTen timer fail: " + Arrays.toString(e.getStackTrace()));
                actuallySetTen();
            }
        } else {
            actuallySetTen();
        }
    }

    private void actuallySetTen() {
        clearControllers();
        controllerTitle.setText(languageString[25] + " : " + UNameShown[user]);
        controllerTwo.setText(languageString[26]);
        controllerThree.setText(languageString[27]);
        controllerFour.setText(languageString[28]);
        controllerFive.setText(languageString[29]);
        moneySystemState = 10;
        buttonPressable = true;
    }

    private void setTwenty() { //check balance
        clearControllers();
        controllerTitle.setText(languageString[26]);
        setCenterControllers(controllerOne, controllerThree);
        controllerOne.setText(languageString[30]);
        controllerTwo.setText(String.valueOf(countTotal()));
        controllerThree.setText(languageString[31]);
        controllerFour.setText(String.valueOf(new BigDecimal(money[user])));
        controllerFive.setText(languageString[18]);
        moneySystemState = 20;
        buttonPressable = true;
    }

    private void setThirty() { //add
        clearControllers();
        controllerTitle.setText(languageString[27]);
        setCenterControllers(controllerTwo, controllerOne);
        setEditableControllers(controllerThree);
        controllerOne.setText(languageString[32]);
        controllerTwo.setText(String.valueOf(new BigDecimal(money[user])));
        controllerThree.setPromptText(languageString[33]);
        controllerFour.setText(languageString[17]);
        controllerFive.setText(languageString[18]);
        moneySystemState = 30;
        buttonPressable = true;
    }

    private void setForty() { //subtract
        clearControllers();
        controllerTitle.setText(languageString[28]);
        setCenterControllers(controllerOne, controllerTwo);
        setEditableControllers(controllerThree);
        controllerOne.setText(languageString[32]);
        controllerTwo.setText(String.valueOf(new BigDecimal(money[user])));
        controllerThree.setPromptText(languageString[34]);
        controllerFour.setText(languageString[17]);
        controllerFive.setText(languageString[18]);
        moneySystemState = 40;
        buttonPressable = true;
    }

    private void setFifty() { //after add or subtract
        clearControllers();
        controllerTitle.setText(UNameShown[user]);
        setCenterControllers(controllerTwo);
        controllerTwo.setText("New Balance");
        controllerThree.setText(money[user]);
        controllerFive.setText(languageString[18]);
        buttonPressable = true;
    }

    private void setNinety() { //user money for admin
        clearControllers();
        controllerTitle.setText(UNameShown[UserNumber]);
        setEditableControllers(controllerThree);
        setCenterControllers(controllerOne);
        controllerOne.setText("Balance" + " : " + UNameShown[UserNumber]);
        controllerTwo.setText(money[UserNumber]);
        controllerThree.setPromptText("New Balance");
        controllerFour.setText(languageString[17]);
        controllerFive.setText(languageString[18]);
        moneySystemState = 90;
        buttonPressable = true;
    }

    private void setHundred() { //regular login
        clearControllers();
        controllerTitle.setText("Login");
        setCenterControllers(controllerOne);
        setEditableControllers(controllerTwo, controllerThree);
        controllerOne.setText(languageString[14]);
        controllerTwo.setPromptText(languageString[15]);
        controllerThree.setPromptText(languageString[16]);
        controllerFour.setText(languageString[17]);
        controllerFive.setText(languageString[18]);
        moneySystemState = 100;
        buttonPressable = true;
    }

    private BigDecimal countTotal() { //counts total balance
        BigDecimal total = new BigDecimal(0);
        for (int i = 1; i <= money.length - 1; i++) {
            total = total.add(new BigDecimal(money[i]));
        }
        return total;
    }

    private void numericTestMoney() { //sees if input of MMS is numeric
        try {
            new BigDecimal(controllerThree.getText());
            numericTestMoneyResult = 1;
        } catch (RuntimeException inputNotNum) {
            numericTestMoneyResult = 0;
            LOGGER.log(Level.WARNING, "input not numeral Money: " + controllerThree.getText());
            LOGGER.log(Level.WARNING, "input not numeral Money: " + Arrays.toString(inputNotNum.getStackTrace()));
        }
    }

    private void logIn() { //
        adminButton.setStyle("-fx-background-color: #005dff");
        LOGGER.info("User Logged in. uname: " + controllerTwo.getText() + ", pword: " + controllerThree.getText());
        setTen();
    }

    private void clearControllers() {
        controllerTitle.setText(null);
        clearControllers(controllerOne, controllerTwo, controllerThree, controllerFour, controllerFive);
        buttonPressable = false;
    }

    private void clearControllers(TextField... testFields) {
        for (TextField textField : testFields) {
            textField.setText(null);
            textField.setStyle("-fx-alignment: CENTER-RIGHT; -fx-text-fill: " + textColor + ";");
            textField.setEditable(false);
            textField.setPromptText(null);
        }
    }

    private void setCenterControllers(TextField... central) {
        for (TextField textField : central) {
            textField.setStyle("-fx-alignment: CENTER; -fx-text-fill: " + textColor + ";");
        }
    }

    private void setEditableControllers(TextField... edit) {
        for (TextField textField : edit) {
            textField.setEditable(true);
        }
    }

    private void setTextColor() {
        setButtonTextColor(zero, one, two, three, four, five, six, seven, eight,
                nine, allClear, clear, plus, minus, multiply, divide, decimal,
                plusMinus, equals, memUn, memUnSet, memUnDelete, memDeux, memDeuxSet,
                memDeuxDelete, root, pi, euler, c2mms, mms2c);
        setTextFieldTextColor(controllerTitle, controllerOne, controllerTwo, controllerThree, controllerFour, controllerFive);
        if (rootActive == 1) {
            root.setStyle("-fx-text-fill:" + textColor + "; -fx-background-color: #adadad; -fx-border-color: #727272");
        }
        switch (tempSign) {
            case 1:
                plus.setStyle("-fx-text-fill:" + textColor + "; -fx-background-color: #adadad; -fx-border-color: #727272");
            case 2:
                minus.setStyle("-fx-text-fill:" + textColor + "; -fx-background-color: #adadad; -fx-border-color: #727272");
            case 3:
                multiply.setStyle("-fx-text-fill:" + textColor + "; -fx-background-color: #adadad; -fx-border-color: #727272");
            case 4:
                divide.setStyle("-fx-text-fill:" + textColor + "; -fx-background-color: #adadad; -fx-border-color: #727272");
        }
        setTextAreaTextColor(input, output);
    }

    private void setButtonTextColor(Button... button) {
        for (Button button1 : button) {
            button1.setStyle("-fx-text-fill:" + textColor + "; -fx-background-color: #eaeaea; -fx-border-color: #727272");
        }
    }

    private void setTextFieldTextColor(TextField... textField) {
        for (TextField textField1 : textField) {
            textField1.setStyle("-fx-text-fill: " + textColor + ";");
        }
    }

    private void setTextAreaTextColor(TextArea... textArea) {
        for (TextArea textArea1 : textArea) {
            textArea1.setStyle("-fx-text-fill: " + textColor + ";");
        }
    }

    private void setLang1() {
        on = 0;
        moneySystemState = 0;
        clearControllers();
    }

    private void setLang2() {
        input.setPromptText(languageString[38]);
        output.setPromptText(languageString[39]);
        memUn.setText(languageString[40]);
        memUnSet.setText(languageString[41]);
        memUnDelete.setText(languageString[42]);
        memDeux.setText(languageString[43]);
        memDeuxSet.setText(languageString[41]);
        memDeuxDelete.setText(languageString[42]);
        c2mms.setText(languageString[44]);
        mms2c.setText(languageString[45]);
        LOGGER.config("Language Initialised.");
        setOne();
    }

    //communication between systems

    @FXML
    protected void c2mmsButton() {
        switch (moneySystemState) {
            case 30:
            case 40:
            case 90:
                if (input.getText().equals("")) {
                    controllerThree.setText(output.getText());
                } else {
                    numericTest();
                    if (numericTestResult == 0) {
                        inputError();
                    } else {
                        controllerThree.setText(input.getText());
                    }
                }
            default:
                focus();
        }
    }

    @FXML
    protected void mms2cButton() {
        switch (moneySystemState) {
            case 20:
                if (input.getText().equals(controllerFour.getText())) {
                    input.setText(controllerTwo.getText());
                } else {
                    input.setText(controllerFour.getText());
                }
                break;
            case 50:
                input.setText(controllerThree.getText());
                break;
            case 30:
            case 40:
            case 90:
                if (input.getText().equals(controllerTwo.getText())) {
                    input.setText(controllerThree.getText());
                } else {
                    input.setText(controllerTwo.getText());
                }
                break;
        }
    }
}