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
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Math.Main.csvLanguageInitialise;

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
    @FXML private Button memUnSet;
    @FXML private Button memUnDelete;
    @FXML private Button memDeux;
    @FXML private Button memDeuxSet;
    @FXML private Button memDeuxDelete;
    @FXML private Button c2mms;
    @FXML private Button mms2c;
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
                    LOGGER.log(Level.INFO, "signPressed, s=0, no uI mO");
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
        else {
            LOGGER.log(Level.SEVERE, "sign not 1~4 at math()");
            LOGGER.severe(String.valueOf(sign));
            System.exit(10);
        }
    }
    private void numericTest() {
        try {
            Double.parseDouble(userInput.getText());
            numericTestResult = 1;
        }
        catch (NumberFormatException inputNotNum) {
            numericTestResult = 0;
            LOGGER.log(Level.WARNING, "input not numeral: " + userInput.getText());
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
            catch (NumberFormatException memOne) {
                LOGGER.log(Level.INFO, "memOneSet failed, mathResult: " + mathResult + " userInput: " + userInput.getText());
                LOGGER.info(String.valueOf(memOne));
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
            catch (NumberFormatException memTwo) {
                LOGGER.log(Level.INFO, "memTwoSet failed, mathResult: " + mathResult + " userInput: " + userInput.getText());
                LOGGER.info(String.valueOf(memTwo));
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

    @FXML protected void controllerOneButton() {
        switch (moneySystemState) {
            case 0:
                setOne();
                break;
            case 1:
                if (adminLevel == 0) setTwo();
                else setThree();
                break;
            case 3:
                setFour();
                break;
            case 4:
                setLang1();
                csvLanguageInitialise(new File("src/Math/CSV/english.csv"));
                setLang2();
                break;
            default:
                LOGGER.log(Level.INFO, "I am bad at coding 1: " + moneySystemState);
        }
        focus();
    }
    @FXML protected void controllerTwoButton() {
        switch (moneySystemState) {
            case 10:
                setTwenty();
                break;
            default:
                LOGGER.log(Level.INFO, "I am bad at coding 2: " + moneySystemState);
        }
        focus();
    }
    @FXML protected void controllerThreeButton() {
        switch (moneySystemState) {
            case 4:
                setLang1();
                csvLanguageInitialise(new File("src/Math/CSV/japanese.csv"));
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
    @FXML protected void controllerFourButton() {
        switch (moneySystemState) {
            case 1:
                setTen();
                break;
            case 2:
                if (!controllerThree.getText().equals("")&&controllerThree.getText().equals(
                        superSecurePasswordDecoder(Main.superSecurePassword))) {
                    LOGGER.info("admin pass success: " + controllerThree.getText());
                    setThree();
                }
                adminLevel = 1;
                break;
            case 3:
                adminLevel = 0;
                setTwo();
                break;
            case 10:
                setForty();
                break;
            default:
                LOGGER.log(Level.INFO, "I am bad at coding 4: " + moneySystemState);
        }
        focus();
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
            case 4:
                setThree();
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
                LOGGER.log(Level.INFO, "I am bad at coding 5: " + moneySystemState);
        }
        focus();
    }
    private String superSecurePasswordDecoder(String hex){

        StringBuilder sb = new StringBuilder();

        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for( int i=0; i<hex.length()-1; i+=2 ){

            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char)decimal);
        }
        return sb.toString();
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
        on = 1;
        controllerTitle.setText(Main.languageString[0]);
        controllerOne.setStyle("-fx-alignment: CENTER");
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
                System.exit(11);
        }
        controllerFour.setText(Main.languageString[11]);
        controllerFive.setText(Main.languageString[12]);
        moneySystemState = 1;
    }
    private void setTwo() { //admin login
        clearControllers();
        controllerTitle.setText(Main.languageString[13]);
        controllerOne.setStyle("-fx-alignment: CENTER");
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
    private void setFour() {
        clearControllers();
        controllerTitle.setText(Main.languageString[38]);
        controllerOne.setText("English");
        controllerTwo.setText("Français");
        controllerThree.setText("日本語");
        controllerFour.setText("Italiano");
        controllerFive.setText(Main.languageString[18]);
        moneySystemState = 4;
    }
    private void setTen() { //main screen
        clearControllers();
        if (booted == 0) {
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            controllerOne.setStyle("-fx-alignment: CENTER");
                            controllerOne.setText(Main.languageString[0]);
                            new java.util.Timer().schedule(
                                    new java.util.TimerTask() {
                                        @Override
                                        public void run() {
                                            clearControllers();
                                            controllerTwo.setStyle("-fx-alignment: CENTER");
                                            controllerTwo.setText(Main.languageString[0]);
                                            new java.util.Timer().schedule(
                                                    new java.util.TimerTask() {
                                                        @Override
                                                        public void run() {
                                                            clearControllers();
                                                            controllerThree.setStyle("-fx-alignment: CENTER");
                                                            controllerThree.setText(Main.languageString[0]);
                                                            new java.util.Timer().schedule(
                                                                    new java.util.TimerTask() {
                                                                        @Override
                                                                        public void run() {
                                                                            clearControllers();
                                                                            controllerFour.setStyle("-fx-alignment: CENTER");
                                                                            controllerFour.setText(Main.languageString[0]);
                                                                            new java.util.Timer().schedule(
                                                                                    new java.util.TimerTask() {
                                                                                        @Override
                                                                                        public void run() {
                                                                                            clearControllers();
                                                                                            controllerFive.setStyle("-fx-alignment: CENTER");
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
        else {
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
        booted = 1;
    }
    private void setTwenty() { //check balance
        clearControllers();
        controllerTitle.setText(Main.languageString[26]);
        controllerOne.setStyle("-fx-alignment: CENTER");
        controllerOne.setText(Main.languageString[30]);
        //balance
        controllerThree.setStyle("-fx-alignment: CENTER");
        controllerThree.setText(Main.languageString[31]);
        //users balance
        controllerFive.setText(Main.languageString[18]);
        moneySystemState = 20;
    }
    private void setThirty() { //add
        clearControllers();
        controllerTitle.setText(Main.languageString[27]);
        controllerOne.setStyle("-fx-alignment: CENTER");
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
        controllerOne.setStyle("-fx-alignment: CENTER");
        controllerOne.setText(Main.languageString[32]);
        //balance
        controllerThree.setPromptText(Main.languageString[34]);
        controllerThree.setEditable(true);
        controllerFour.setText(Main.languageString[17]);
        controllerFive.setText(Main.languageString[18]);
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
            textField.setPromptText(null);
        }
    }
    private void setLang1() {
        on = 0;
        moneySystemState = 0;
        clearControllers();
    }
    private void setLang2() {
        userInput.setPromptText(Main.languageString[39]);
        mathOutput.setPromptText(Main.languageString[40]);
        memUn.setText(Main.languageString[41]);
        memUnSet.setText(Main.languageString[42]);
        memUnDelete.setText(Main.languageString[43]);
        memDeux.setText(Main.languageString[44]);
        memDeuxSet.setText(Main.languageString[42]);
        memDeuxDelete.setText(Main.languageString[43]);
        c2mms.setText(Main.languageString[45]);
        mms2c.setText(Main.languageString[46]);
        LOGGER.config("Language Initialised");
        setOne();
    }

    //communication between systems

    @FXML protected void c2mmsButton() {
        switch (moneySystemState) {
            case 30:
            case 40:
                if (userInput.getText().equals("")) {
                    controllerThree.setText(mathOutput.getText());
                    focus();
                }
                else {
                    numericTest();
                    if (numericTestResult == 0) {
                        inputError();
                    }
                    else {
                        controllerThree.setText(userInput.getText());
                        focus();
                    }
                }
            default:
                focus();
        }
    }
    @FXML protected void mms2cButton() {
        //mathOutput.setText(String.valueOf(Double.parseDouble(userInput.getText()))); to mess up
        sign = 5;
    }
}
