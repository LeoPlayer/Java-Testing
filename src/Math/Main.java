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
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.*;

public class Main extends Application {
    /**
     * initialises program
     * PrintStream section changes system output to file on boot
     * setOnCloseRequest runs confirmation of closing application
     */
    static String[] languageString;
    static String superSecurePassword;
    private int lf = 0;
    private int csvfp = 0;
    private String lfMessage;
    private String csvfpMessage;

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

            LOGGER.config("Logger Configuration done.");

            //Console handler removed
            LOGGER.removeHandler(consoleHandler);

        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "FAILED TO CREATE LOG", exception);
            lf = 1;
            lfMessage = String.valueOf(exception);
        }
        csvLanguageInitialise(new File("src/Math/CSV/english.csv"));
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("src/Math/CSV/password.csv")));
            superSecurePassword = br.readLine().substring(1,17); //dunno why but readline adds space at start and end
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.log(Level.SEVERE, "Failed CSV password: " + Arrays.toString(e.getStackTrace()));
            csvfp = 1;
            csvfpMessage = Arrays.toString(e.getStackTrace());
        }
        LOGGER.config("Configuration complete.");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final Logger LOGGER = Logger.getLogger(Main.class.getName());
        Parent root = FXMLLoader.load(getClass().getResource("calculator.fxml"));
        primaryStage.setTitle("Adding Two Numbers");
        primaryStage.setScene(new Scene(root, 860, 600));
        primaryStage.show();
        if (lf == 1) {
            logFailAlert();
        } else if (csvfp == 1) {
            csvFailPasswordAlert();
        }

        primaryStage.setOnCloseRequest(event -> {
            Alert closing = new Alert(Alert.AlertType.CONFIRMATION);
            closing.setTitle("Quitting Application");
            closing.setHeaderText("Are you sure you want to close this incredible application?");
            closing.setContentText("You will regret it");

            Optional<ButtonType> result = closing.showAndWait();
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

    private void logFailAlert() {
        final Logger LOGGER = Logger.getLogger(Main.class.getName());
        Alert logFail = new Alert(Alert.AlertType.CONFIRMATION);
        logFail.setTitle("Log Creation Failed");
        logFail.setHeaderText("Do you want to continue program?");
        logFail.setContentText(lfMessage);

        Optional<ButtonType> result = logFail.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                LOGGER.log(Level.CONFIG, "User continued program, LogFail");
            } else {
                LOGGER.log(Level.CONFIG, "User terminated program, LogFail");
                System.exit(1);
            }
        }
    }
    private void csvFailPasswordAlert() {
        final Logger LOGGER = Logger.getLogger(Main.class.getName());
        Alert csvFailPass = new Alert(Alert.AlertType.CONFIRMATION);
        csvFailPass.setTitle("Password load failed");
        csvFailPass.setHeaderText("Do you want to continue program?");
        csvFailPass.setContentText(csvfpMessage);

        Optional<ButtonType> result = csvFailPass.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                LOGGER.log(Level.CONFIG, "User continued program, Pass fail");
            } else {
                LOGGER.log(Level.CONFIG, "User terminated program, Pass fail");
                System.exit(2);
            }
        }
    }
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
}