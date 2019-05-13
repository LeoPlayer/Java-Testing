# Java-Testing
java testing for software

## Addition

#### Main.java
```java
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Adding Two Numbers");
        primaryStage.setScene(new Scene(root, 860, 600));
        primaryStage.show();

        primaryStage.setOnCloseRequest(event ->
        {
            System.out.println("User Closed Program");
            //some way for logging
            Platform.exit();
        });
    }
```

Initialises the program.
When the user exits the program, 'User Closed Program' shown in console

#### Controller.java
```java
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
```

Adding logic
uses try/catch arround it so if input is not numeric, error displayed

```java
    private void inputError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText("Please put in a number");

        alert.showAndWait();
        System.out.println("inputError code run");
    }
```
error message code

#### sample.fxml

uses scenebuilder
very messy and long so not on README
