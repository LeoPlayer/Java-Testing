# Java-Testing
java testing for software

## Addition

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
