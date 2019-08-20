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
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Math.Main.csvLanguageInitialise;

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
    private double firstNumber = 0; //first number to calculate
    private double secondNumber = 0; //second number to calculate
    private double mathResult = 0; //result of calculation
    private double memOne; //first memory number
    private double memTwo; //second memory number
    private int moneySystemState = 0; //in the tens, 0 is initialisation
    private int adminLevel = 0; //0 not logged in, 1 logged in
    private int booted = 0; //1 booted, 0 not booted
    private int on = 0; //0 is off, 1 is on
    private String adminColor = "#ff0000"; //colour when admin logged in
    private String textColor = "#5a5a5a"; //colour of all text displayed
    private int user = 0;
    @FXML private TextArea mathOutput;
    @FXML private TextArea userInput;
    @FXML private Button plus;
    @FXML private Button minus;
    @FXML private Button multiply;
    @FXML private Button divide;
    @FXML private Button root;
    @FXML private Button memUn;
    @FXML private Button memUnSet;
    @FXML private Button memUnDelete;
    @FXML private Button memDeux;
    @FXML private Button memDeuxSet;
    @FXML private Button memDeuxDelete;
    @FXML private Button c2mms;
    @FXML private Button mms2c;
    @FXML private Button adminButton;
    @FXML private Button zero;
    @FXML private Button one;
    @FXML private Button two;
    @FXML private Button three;
    @FXML private Button four;
    @FXML private Button five;
    @FXML private Button six;
    @FXML private Button seven;
    @FXML private Button eight;
    @FXML private Button nine;
    @FXML private Button allClear;
    @FXML private Button clear;
    @FXML private Button decimal;
    @FXML private Button plusMinus;
    @FXML private Button equals;
    @FXML private Button pi;
    @FXML private Button euler;
    @FXML public TextField controllerTitle;
    @FXML public TextField controllerOne;
    @FXML public TextField controllerTwo;
    @FXML public TextField controllerThree;
    @FXML public TextField controllerFour;
    @FXML public TextField controllerFive;


    private final Logger LOGGER = Logger.getLogger(Main.class.getName());
    @FXML
    protected void plusButton() {
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

    private void signPressed() {
        if (stage == 0) {
            if (userInput.getText().equals("")) {
                if (mathOutput.getText().equals("")) {
                    LOGGER.log(Level.INFO, "signPressed, s=0, no uI mO");
                    inputError();
                    resetSignColor();
                } else {
                    if (rootActive == 0) {
                        firstNumber = Double.parseDouble(mathOutput.getText());
                    } else {
                        firstNumber = Math.sqrt(Double.parseDouble(mathOutput.getText()));
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
                        firstNumber = Double.parseDouble(userInput.getText());
                    } else {
                        firstNumber = Math.sqrt(Double.parseDouble(userInput.getText()));
                        resetRootColor();
                    }
                    sign = tempSign;
                    stage = 1;
                    userInput.clear();
                    focus();
                }
            }
        } else if (stage == 1) {
            if (userInput.getText().equals("")) {
                sign = tempSign;
                focus();
            } else {
                numericTest();
                if (numericTestResult == 0) {
                    inputError();
                    resetSignColor();
                } else if (numericTestResult == 1) {
                    if (rootActive == 0) {
                        secondNumber = Double.parseDouble(userInput.getText());
                    } else {
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

    @FXML
    protected void equalsButton() {
        if (stage == 0 && userInput.getText().equals("")) {
            resetRootColor();
        } else if (stage == 0 && !userInput.getText().equals("")) {
            numericTest();
            if (numericTestResult == 0) {
                inputError();
            } else if (numericTestResult == 1) {
                if (rootActive == 0) {
                    mathResult = Double.parseDouble(userInput.getText());
                } else {
                    mathResult = Math.sqrt(Double.parseDouble(userInput.getText()));
                    resetRootColor();
                }
                displayAnswer();
                userInput.clear();
                focus();
            }
        } else if (stage == 1) {
            if (userInput.getText().equals("")) {
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
                        secondNumber = Double.parseDouble(userInput.getText());
                    } else {
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
        else {
            LOGGER.log(Level.SEVERE, "sign not 1~4 at math()");
            LOGGER.severe("Sign is: " + sign);
            LOGGER.severe("exit 10");
            System.exit(10);
        }
    }
    private void numericTest() {
        try {
            Double.parseDouble(userInput.getText());
            numericTestResult = 1;
        } catch (NumberFormatException inputNotNum) {
            numericTestResult = 0;
            LOGGER.log(Level.WARNING, "input not numeral: " + userInput.getText());
        }
    }
    private void displayAnswer() {
        if (mathResult == (int) mathResult) {
            mathOutput.setText(String.valueOf(Math.round(mathResult)));
        } else mathOutput.setText(String.valueOf(mathResult));
    }

    @FXML
    protected void clear() {
        userInput.clear();
        resetRootColor();
        focus();
    }
    @FXML
    protected void allClear() {
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

    @FXML
    protected void pi() {
        userInput.setText(String.valueOf(Math.PI));
        focus();
    }
    @FXML
    protected void euler() {
        userInput.setText(String.valueOf(Math.E));
        focus();
    }

    @FXML
    protected void memOneCall() {
        if (memOne == (int) memOne) {
            userInput.setText(String.valueOf(Math.round(memOne)));
        } else userInput.setText(String.valueOf(memOne));
        focus();
    }
    @FXML
    protected void memOneSet() {
        if (userInput.getText().equals("")) {
            try {
                memOne = Double.parseDouble(mathOutput.getText());
                memUn.setStyle("-fx-background-color: #6ab873; -fx-border-color: #727272");
            } catch (NumberFormatException memOne) {
                LOGGER.log(Level.INFO, "memOneSet failed, mathResult: " + mathResult + " userInput: " + userInput.getText());
                LOGGER.info(String.valueOf(memOne));
            }
        } else {
            numericTest();
            if (numericTestResult == 0) {
                inputError();
            } else if (numericTestResult == 1) {
                if (rootActive == 0) {
                    mathResult = Double.parseDouble(userInput.getText());
                } else {
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
    @FXML
    protected void memOneDelete() {
        memOne = 0;
        memUn.setStyle("-fx-background-color: #eaeaea; -fx-border-color: #727272");
        focus();
    }

    @FXML
    protected void memTwoCall() {
        if (memTwo == (int) memTwo) {
            userInput.setText(String.valueOf(Math.round(memTwo)));
        } else userInput.setText(String.valueOf(memTwo));
        focus();
    }
    @FXML
    protected void memTwoSet() {
        if (!userInput.getText().equals("")) {
            numericTest();
            if (numericTestResult == 1) {
                if (rootActive == 1) {
                    mathResult = Math.sqrt(Double.parseDouble(userInput.getText()));
                    resetRootColor();
                } else {
                    mathResult = Double.parseDouble(userInput.getText());
                }
                displayAnswer();
                memTwo = mathResult;
                memDeux.setStyle("-fx-background-color: #6ab873; -fx-border-color: #727272");
            } else if (numericTestResult == 0) {
                inputError();
            }
        } else {
            try {
                memTwo = Double.parseDouble(mathOutput.getText());
                memDeux.setStyle("-fx-background-color: #6ab873; -fx-border-color: #727272");
            } catch (NumberFormatException memTwo) {
                LOGGER.log(Level.INFO, "memTwoSet failed, mathResult: " + mathResult + " userInput: " + userInput.getText());
                LOGGER.info(String.valueOf(memTwo));
            }
        }
        focus();
    }
    @FXML
    protected void memTwoDelete() {
        memTwo = 0;
        memDeux.setStyle("-fx-background-color: #eaeaea; -fx-border-color: #727272");
        focus();
    }

    @FXML
    protected void zero() {
        userInput.setText(userInput.getText() + "0");
        focus();
    }
    @FXML
    protected void un() {
        userInput.setText(userInput.getText() + "1");
        focus();
    }
    @FXML
    protected void deux() {
        userInput.setText(userInput.getText() + "2");
        focus();
    }
    @FXML
    protected void trois() {
        userInput.setText(userInput.getText() + "3");
        focus();
    }
    @FXML
    protected void quatre() {
        userInput.setText(userInput.getText() + "4");
        focus();
    }
    @FXML
    protected void cinq() {
        userInput.setText(userInput.getText() + "5");
        focus();
    }
    @FXML
    protected void six() {
        userInput.setText(userInput.getText() + "6");
        focus();
    }
    @FXML
    protected void sept() {
        userInput.setText(userInput.getText() + "7");
        focus();
    }
    @FXML
    protected void huit() {
        userInput.setText(userInput.getText() + "8");
        focus();
    }
    @FXML
    protected void neuf() {
        userInput.setText(userInput.getText() + "9");
        focus();
    }

    @FXML
    protected void plusMinusButton() {
        if (userInput.getText().equals("")) {
            userInput.setText("-");
            focus();
        } else if (userInput.getText().equals("-")) {
            userInput.setText("");
            focus();
        } else {
            numericTest();
            if (numericTestResult == 0) {
                inputError();
            } else {
                if (Double.parseDouble(userInput.getText()) < 0) {
                    userInput.setText(userInput.getText().substring(1));
                } else if (Double.parseDouble(userInput.getText()) == 0) {
                    userInput.setText(String.valueOf(0));
                } else {
                    userInput.setText("-" + userInput.getText());
                }
                focus();
            }
        }
    }
    @FXML
    protected void decimalButton() {
        userInput.setText(userInput.getText() + ".");
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
        inputError.setTitle(Main.languageString[35]);
        inputError.setHeaderText(Main.languageString[36]);
        inputError.setContentText(Main.languageString[37]);
        LOGGER.log(Level.WARNING, "inputError");
        inputError.showAndWait();
        focus();
    }

    private void focus() {
        userInput.requestFocus();
        userInput.end();
    }

    //money management system below

    @FXML
    protected void controllerOneButton() {
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
                setThree();
                break;
            case 6:
                setFive();
                break;
            case 7:
                adminColor = controllerOne.getText();
                adminButton.setStyle("-fx-background-color: " + adminColor + ";");
                break;
            default:
                LOGGER.log(Level.INFO, "I am bad at coding 1: " + moneySystemState);
        }
        focus();
    }
    @FXML
    protected void controllerTwoButton() {
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
            case 10:
                setTwenty();
                break;
            default:
                LOGGER.log(Level.INFO, "I am bad at coding 2: " + moneySystemState);
        }
        focus();
    }
    @FXML
    protected void controllerThreeButton() {
        switch (moneySystemState) {
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
            case 10:
                setThirty();
                break;
            default:
                LOGGER.log(Level.INFO, "I am bad at coding 3: " + moneySystemState);
        }
        focus();
    }
    @FXML
    protected void controllerFourButton() {
        switch (moneySystemState) {
            case 1:
                setHundred();
                break;
            case 2:
                try {
                    if (!Main.usernameInitialisationFailed&&!Main.passwordInitialisationFailed) {
                        if (controllerTwo.getText().equals(Main.username) &&
                                controllerThree.getText().equals(superSecurePasswordDecoder(Main.superSecurePassword))) {
                            LOGGER.info("admin uname success: " + controllerTwo.getText());
                            LOGGER.info("admin pword success: " + controllerThree.getText());
                            setThree();
                            adminLevel = 1;
                            adminButton.setStyle("-fx-background-color: " + adminColor + ";");
                        }
                        else {
                            LOGGER.info("admin uname fail: " + controllerTwo.getText());
                            LOGGER.info("admin pword fail: " + controllerThree.getText());
                        }
                    }
                }
                catch (RuntimeException e) {
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
            case 10:
                setForty();
                break;
            case 100:
                try {
                    if (controllerTwo.getText().equals(Main.user1[1]) &&
                            controllerThree.getText().equals(superSecurePasswordDecoder(Main.user1[2]))) {
                        user = 1;
                        logIn();
                    } else if (controllerTwo.getText().equals(Main.user2[1]) &&
                            controllerThree.getText().equals(superSecurePasswordDecoder(Main.user2[2]))) {
                        user = 2;
                        logIn();
                    } else if (controllerTwo.getText().equals(Main.user3[1]) &&
                            controllerThree.getText().equals(superSecurePasswordDecoder(Main.user3[2]))) {
                        user = 3;
                        logIn();
                    } else if (controllerTwo.getText().equals(Main.user4[1]) &&
                            controllerThree.getText().equals(superSecurePasswordDecoder(Main.user4[2]))) {
                        user = 4;
                        logIn();
                    } else if (controllerTwo.getText().equals(Main.user5[1]) &&
                            controllerThree.getText().equals(superSecurePasswordDecoder(Main.user5[2]))) {
                        user = 5;
                        logIn();
                    } else {
                        LOGGER.info("user login failed, uname: " + controllerTwo.getText() + " pword: " + controllerThree.getText());
                    }
                }
                catch (RuntimeException e) {
                    LOGGER.info("user login failed, uname: " + controllerTwo.getText() + " pword: " + controllerThree.getText());
                }
                break;
            default:
                LOGGER.log(Level.INFO, "I am bad at coding 4: " + moneySystemState);
        }
        focus();
    }
    @FXML
    protected void controllerFiveButton() {
        switch (moneySystemState) {
            case 1:
                clearControllers();
                moneySystemState = 0;
                on = 0;
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
            default:
                LOGGER.log(Level.INFO, "I am bad at coding 5: " + moneySystemState);
        }
        focus();
    }

    private String superSecurePasswordDecoder(String hex) {

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
            }
            catch (ArrayIndexOutOfBoundsException e) {
                LOGGER.warning("setOne timer fail: " + Arrays.toString(e.getStackTrace()));
                actuallySetOne();
            }
        } else {
            actuallySetOne();
        }
    }
    private void actuallySetOne() {
        on = 1;
        controllerTitle.setText(Main.languageString[0]);
        controllerOne.setStyle("-fx-alignment: CENTER; -fx-text-fill: " + textColor + ";");
        Random rand = new Random();
        int randomMessage = rand.nextInt(10);
        switch (randomMessage) {
            case 0:
                controllerOne.setText(Main.languageString[1]);
                break;
            case 1:
                controllerOne.setText(Main.languageString[2]);
                break;
            case 2:
                controllerOne.setText(Main.languageString[3]);
                break;
            case 3:
                controllerOne.setText(Main.languageString[4]);
                break;
            case 4:
                controllerOne.setText(Main.languageString[5]);
                break;
            case 5:
                controllerOne.setText(Main.languageString[6]);
                break;
            case 6:
                controllerOne.setText(Main.languageString[7]);
                break;
            case 7:
                controllerOne.setText(Main.languageString[8]);
                break;
            case 8:
                controllerOne.setText(Main.languageString[9]);
                break;
            case 9:
                controllerOne.setText(Main.languageString[10]);
                break;
            default:
                LOGGER.log(Level.SEVERE, "error comment");
                LOGGER.severe("exit 11");
                System.exit(11);
        }
        controllerFour.setText(Main.languageString[11]);
        controllerFive.setText(Main.languageString[12]);
        moneySystemState = 1;
    }
    private void setTwo() { //admin login
        clearControllers();
        controllerTitle.setText(Main.languageString[13]);
        controllerOne.setStyle("-fx-alignment: CENTER; -fx-text-fill: " + textColor + ";");
        controllerOne.setText(Main.languageString[14]);
        controllerTwo.setPromptText(Main.languageString[15]);
        controllerTwo.setEditable(true);
        controllerThree.setPromptText(Main.languageString[16]);
        controllerThree.setEditable(true);
        controllerFour.setText(Main.languageString[17]);
        controllerFive.setText(Main.languageString[18]);
        moneySystemState = 2;
    }
    private void setThree() { //admin screen
        clearControllers();
        controllerTitle.setText(Main.languageString[19]);
        controllerOne.setText(Main.languageString[20]);
        controllerTwo.setText(Main.languageString[21]);
        controllerThree.setText(Main.languageString[22]);
        controllerFour.setText(Main.languageString[23]);
        controllerFive.setText(Main.languageString[24]);
        moneySystemState = 3;
    }
    private void setFour() { //language 1
        clearControllers();
        controllerTitle.setText(Main.languageString[20]);
        controllerOne.setText(Main.languageString[18]);
        controllerTwo.setText("English");
        controllerThree.setText("Français");
        controllerFour.setText("日本語");
        controllerFive.setText(Main.languageString[46]);
        moneySystemState = 4;
    }
    private void setFive() { //language 2
        clearControllers();
        controllerTitle.setText(Main.languageString[20]);
        controllerOne.setText(Main.languageString[47]);
        controllerTwo.setText("Italiano");
        controllerThree.setText("한국인");
        controllerFour.setText("中文");
        controllerFive.setText(Main.languageString[46]);
        moneySystemState = 5;
    }
    private void setSix() { //language 3
        clearControllers();
        controllerTitle.setText(Main.languageString[20]);
        controllerOne.setText(Main.languageString[47]);
        controllerTwo.setText("Deutsche");
        controllerThree.setText("русский");
        controllerFour.setText("हिंदी");
        moneySystemState = 6;
    }
    private void setSeven() { //appearance
        clearControllers();
        controllerTitle.setText(Main.languageString[21]);
        controllerOne.setPromptText(Main.languageString[48]);
        controllerOne.setEditable(true);
        controllerTwo.setPromptText(Main.languageString[49]);
        controllerTwo.setEditable(true);
        controllerFive.setText(Main.languageString[18]);
        moneySystemState = 7;
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
                                controllerOne.setText(Main.languageString[0]);
                                new java.util.Timer().schedule(
                                        new java.util.TimerTask() {
                                            @Override
                                            public void run() {
                                                clearControllers();
                                                controllerTwo.setStyle("-fx-alignment: CENTER; -fx-text-fill: " + textColor + ";");
                                                controllerTwo.setText(Main.languageString[0]);
                                                new java.util.Timer().schedule(
                                                        new java.util.TimerTask() {
                                                            @Override
                                                            public void run() {
                                                                clearControllers();
                                                                controllerThree.setStyle("-fx-alignment: CENTER; -fx-text-fill: " + textColor + ";");
                                                                controllerThree.setText(Main.languageString[0]);
                                                                new java.util.Timer().schedule(
                                                                        new java.util.TimerTask() {
                                                                            @Override
                                                                            public void run() {
                                                                                clearControllers();
                                                                                controllerFour.setStyle("-fx-alignment: CENTER; -fx-text-fill: " + textColor + ";");
                                                                                controllerFour.setText(Main.languageString[0]);
                                                                                new java.util.Timer().schedule(
                                                                                        new java.util.TimerTask() {
                                                                                            @Override
                                                                                            public void run() {
                                                                                                clearControllers();
                                                                                                controllerFive.setStyle("-fx-alignment: CENTER; -fx-text-fill: " + textColor + ";");
                                                                                                controllerFive.setText(Main.languageString[0]);
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
            catch (ArrayIndexOutOfBoundsException e) {
                LOGGER.warning("setTen timer fail: " + Arrays.toString(e.getStackTrace()));
                actuallySetTen();
            }
        } else {
            actuallySetTen();
        }
    }
    private void actuallySetTen() {
        clearControllers();
        controllerTitle.setText(Main.languageString[25]);
        controllerTwo.setText(Main.languageString[26]);
        controllerThree.setText(Main.languageString[27]);
        controllerFour.setText(Main.languageString[28]);
        controllerFive.setText(Main.languageString[29]);
        moneySystemState = 10;
    }
    private void setTwenty() { //check balance
        clearControllers();
        controllerTitle.setText(Main.languageString[26]);
        controllerOne.setStyle("-fx-alignment: CENTER; -fx-text-fill: " + textColor + ";");
        controllerOne.setText(Main.languageString[30]);
        //balance
        controllerThree.setStyle("-fx-alignment: CENTER; -fx-text-fill: " + textColor + ";");
        controllerThree.setText(Main.languageString[31]);
        //users balance
        controllerFive.setText(Main.languageString[18]);
        moneySystemState = 20;
    }
    private void setThirty() { //add
        clearControllers();
        controllerTitle.setText(Main.languageString[27]);
        controllerOne.setStyle("-fx-alignment: CENTER; -fx-text-fill: " + textColor + ";");
        controllerOne.setText(Main.languageString[32]);
        //balance
        controllerThree.setPromptText(Main.languageString[33]);
        controllerThree.setEditable(true);
        controllerFour.setText(Main.languageString[17]);
        controllerFive.setText(Main.languageString[18]);
        moneySystemState = 30;
    }
    private void setForty() { //subtract
        clearControllers();
        controllerTitle.setText(Main.languageString[28]);
        controllerOne.setStyle("-fx-alignment: CENTER; -fx-text-fill: " + textColor + ";");
        controllerOne.setText(Main.languageString[32]);
        //balance
        controllerThree.setPromptText(Main.languageString[34]);
        controllerThree.setEditable(true);
        controllerFour.setText(Main.languageString[17]);
        controllerFive.setText(Main.languageString[18]);
        moneySystemState = 40;
    }
    private void setHundred() { //regular login
        clearControllers();
        controllerTitle.setText("Login");
        controllerOne.setStyle("-fx-alignment: CENTER; -fx-text-fill: " + textColor + ";");
        controllerOne.setText(Main.languageString[14]);
        controllerTwo.setPromptText(Main.languageString[15]);
        controllerThree.setPromptText(Main.languageString[16]);
        controllerFour.setText(Main.languageString[17]);
        controllerFive.setText(Main.languageString[18]);
        controllerTwo.setEditable(true);
        controllerThree.setEditable(true);
        moneySystemState = 100;
        booted = 1;
    }
    private void logIn() {
        adminButton.setStyle("-fx-background-color: #005dff");
        setTen();
    }
    private void clearControllers() {
        controllerTitle.setText(null);
        clearControllers(controllerOne, controllerTwo, controllerThree, controllerFour, controllerFive);
    }
    private void clearControllers(TextField... testFields) {
        for (TextField textField : testFields) {
            textField.setText(null);
            textField.setStyle("-fx-alignment: CENTER-RIGHT; -fx-text-fill: " + textColor + ";");
            textField.setEditable(false);
            textField.setPromptText(null);
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
        setTextAreaTextColor(userInput,mathOutput);
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
        userInput.setPromptText(Main.languageString[38]);
        mathOutput.setPromptText(Main.languageString[39]);
        memUn.setText(Main.languageString[40]);
        memUnSet.setText(Main.languageString[41]);
        memUnDelete.setText(Main.languageString[42]);
        memDeux.setText(Main.languageString[43]);
        memDeuxSet.setText(Main.languageString[41]);
        memDeuxDelete.setText(Main.languageString[42]);
        c2mms.setText(Main.languageString[44]);
        mms2c.setText(Main.languageString[45]);
        LOGGER.config("Language Initialised");
        setOne();
    }

    //communication between systems

    @FXML
    protected void c2mmsButton() {
        switch (moneySystemState) {
            case 30:
            case 40:
                if (userInput.getText().equals("")) {
                    controllerThree.setText(mathOutput.getText());
                    focus();
                } else {
                    numericTest();
                    if (numericTestResult == 0) {
                        inputError();
                    } else {
                        controllerThree.setText(userInput.getText());
                        focus();
                    }
                }
            default:
                focus();
        }
    }
    @FXML
    protected void mms2cButton() {
        //mathOutput.setText(String.valueOf(Double.parseDouble(userInput.getText()))); to mess up
        sign = 5;
    }
}
