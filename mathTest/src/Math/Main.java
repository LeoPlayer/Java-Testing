/*
 * Copyright (©) 2019. Leo Player - Manly Selective Campus
 * made using JavaFX in IntelliJ
 */

package Math;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * initialises program
     */
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
}

