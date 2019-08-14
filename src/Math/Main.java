/*
 * Copyright (©) 2019. Leo Player - Manly Selective Campus
 * made using JavaFX in IntelliJ
 */

package Math;

import com.opencsv.CSVReader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.logging.*;

public class Main extends Application {
    /**
     * initialises program
     * PrintStream section changes system output to file on boot
     * setOnCloseRequest runs confirmation of closing application
     */

    @Override
    public void init() {
        final Logger LOGGER = Logger.getLogger(Main.class.getName());
        Handler consoleHandler;
        Handler fileHandler;
        try {
            //Creating consoleHandler and fileHandler
            consoleHandler = new ConsoleHandler();
            fileHandler = new FileHandler("./Log.log");

            //Assigning handlers to LOGGER object
            LOGGER.addHandler(consoleHandler);
            LOGGER.addHandler(fileHandler);

            //Setting levels to handlers and LOGGER
            consoleHandler.setLevel(Level.ALL);
            fileHandler.setLevel(Level.ALL);
            LOGGER.setLevel(Level.ALL);

            LOGGER.config("Configuration done.");

            //Console handler removed
            LOGGER.removeHandler(consoleHandler);

        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "FAILED TO CREATE LOG", exception);
            System.exit(1);
        }
        File language = new File("src/Math/CSV/languages.csv");
        BufferedReader br;
        String line;
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(language));
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] languageString = line.split(cvsSplitBy);

                System.out.println(languageString[0]);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LOGGER.log(Level.SEVERE, "Failed CSV 1: " + e.getStackTrace() + " " + e.getCause());
            System.exit(2);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.log(Level.SEVERE, "Failed CSV 2: " + e.getStackTrace() + " " + e.getCause());
            System.exit(3);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final Logger LOGGER = Logger.getLogger(Main.class.getName());
        Parent root = FXMLLoader.load(getClass().getResource("calculator.fxml"));
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
                    System.exit(0);
                } else {
                    event.consume();
                }
            }
        });
    }
}