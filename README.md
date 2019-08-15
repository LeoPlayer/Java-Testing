# Java-Testing
java testing for software

## Calculator

#### Main.java
```java
    @Override
    public void start(Stage primaryStage) throws Exception {
        final Logger LOGGER = Logger.getLogger(Main.class.getName());
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Adding Two Numbers");
        primaryStage.setScene(new Scene(root, 860, 600));
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Quitting Application");
            alert.setHeaderText("Are you sure you want to close this incredible application?");
            alert.setContentText("You will regret it");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()) {
                if (result.get() == ButtonType.OK) {
                    LOGGER.log(Level.CONFIG, "User closed program");
                    Platform.exit();
                } else {
                    event.consume();
                }
            }
        });
    }
```

Initialises the program.
When the user exits the program, 'User Closed Program' shown in console
Separate init() intialises LOGGER

#### Controller.java
```java
    @FXML protected void plusButton() {
        tempSign = 1;
        resetSignColor();
        plusColor();
        signPressed();
    }
```

a button's code
tempSign is changed to plus
signs color is changed to signify it is on
code ran to save input as firstNumber

```java
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
```

checks whether input is numeric or not

```java
private void displayAnswer() {
        if (mathResult == (int)mathResult) {
            mathOutput.setText(String.valueOf(Math.round(mathResult)));
        }
        else mathOutput.setText(String.valueOf(mathResult));
    }
```

displays answer, making sure integers shown as int

#### sample.fxml

uses scenebuilder
very messy and long so not on README

## MMS

#### Main.java

```java
csvLanguageInitialise(new File("src/Math/CSV/english.csv"));
```

```java
static void csvLanguageInitialise(File csvs) {
        final Logger LOGGER = Logger.getLogger(Main.class.getName());
        File language = new File(String.valueOf(csvs));
        BufferedReader br;
        String line;
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(language));
            while ((line = br.readLine()) != null) {
                // use comma as separator
                languageString = line.split(cvsSplitBy);
            }
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.log(Level.SEVERE, "Failed CSV: " + Arrays.toString(e.getStackTrace()) + " csv: " + csvs);
            System.exit(3);
        }
        LOGGER.config("CSV Configuration done.");
    }
```

configures language by changing string values from language-separate csv files

#### Controller.java

```java
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
```

an MMS button code. Depending on what is on the screen, what the button does is different, so this format was chosen. Since multiple buttons run same code, loading new screens were simplified, such as setOne();

```java
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
```

an MMS screen code. all properties reset, then set again. moneySystemState allows other functions to know what the screen is showing


## Calculator and MMS

#### Controller.java

```java
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
```

when button pressed, function activated depending on what the MMS shows.
