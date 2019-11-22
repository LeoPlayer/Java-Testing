/*
 * Copyright (©) 2019. Leo Player - Manly Selective Campus
 * made using JavaFX in IntelliJ
 */

package math;

import com.opencsv.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.logging.*;

public class Main extends Application {
    /**
     * initialises program
     * PrintStream section changes system output to file on boot
     * setOnCloseRequest runs confirmation of closing application
     */
    static String[] languageString;
    static String[] user1;
    static String[] user2;
    static String[] user3;
    static String[] user4;
    static String[] user5;
    static String[] money;
    static final String[] UNameShown = new String[6];
    static String superSecurePassword;
    static String username;
    private static String[] moneyTemp;
    private boolean lf = false;
    private boolean csvfp = false;
    private boolean csvfu = false;
    private String lfMessage;
    private String csvfpMessage;
    private String csvfuMessage;
    private boolean tooManyUsers = false;
    static boolean usernameInitialisationFailed = false;
    static boolean passwordInitialisationFailed = false;

    public Main() {
    }

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
            lf = true;
            lfMessage = String.valueOf(exception);
        }
        csvLanguageInitialise(new File("src/Math/CSV/english.csv"));
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("src/Math/CSV/password.csv")));
            superSecurePassword = br.readLine().substring(1,17); //dunno why but readline adds space at start and end
            LOGGER.config("Password CSV Configuration done.");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.log(Level.SEVERE, "Failed CSV password: " + Arrays.toString(e.getStackTrace()));
            csvfp = true;
            csvfpMessage = Arrays.toString(e.getStackTrace());
            passwordInitialisationFailed = true;
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("src/Math/CSV/username.csv")));
            username = br.readLine().substring(1,37); //36 char
            LOGGER.config("Username CSV Configuration done.");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.log(Level.SEVERE, "Failed CSV username: " + Arrays.toString(e.getStackTrace()));
            csvfu = true;
            csvfuMessage = Arrays.toString(e.getStackTrace());
            usernameInitialisationFailed = true;
        }
        csvUserLoginInitialise(new File("src/Math/CSV/leo.csv"));
        csvUserLoginInitialise(new File("src/Math/CSV/jaden.csv"));
        csvUserLoginInitialise(new File("src/Math/CSV/wang.csv"));
        csvUserLoginInitialise(new File("src/Math/CSV/goyk.csv"));
        csvUserLoginInitialise(new File("src/Math/CSV/boeing.csv"));
        UNameShown[0]="spacer";
        UNameShown[1]=user1[3];
        UNameShown[2]=user2[3];
        UNameShown[3]=user3[3];
        UNameShown[4]=user4[3];
        UNameShown[5]=user5[3];
        csvMoneyReader();
        LOGGER.config("Configuration complete.");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final Logger LOGGER = Logger.getLogger(Main.class.getName());
        Parent root = FXMLLoader.load(getClass().getResource("calculator.fxml"));
        primaryStage.setTitle("Calculator and MMS");
        primaryStage.setScene(new Scene(root, 782, 538));
        primaryStage.show();
        if (lf) {
            logFailAlert();
        } else if (csvfp) {
            csvFailPasswordAlert();
        } else if (csvfu) {
            csvFailUsernameAlert();
        } else if (tooManyUsers) {
            tooManyUsersAlert();
        }

        primaryStage.setOnCloseRequest(event -> {
            Alert closing = new Alert(Alert.AlertType.CONFIRMATION);
            closing.setTitle(languageString[50]);
            closing.setHeaderText(languageString[51]);
            closing.setContentText(languageString[52]);

            Optional<ButtonType> result = closing.showAndWait();
            if (result.isPresent()) {
                if (result.get() == ButtonType.OK) {
                    LOGGER.log(Level.CONFIG, "User closed program exit 0");
                    Platform.exit();
                    System.exit(0);
                } else {
                    event.consume();
                }
            }
        });
    }
    static void csvLanguageInitialise(File csvl) {
        final Logger LOGGER = Logger.getLogger(Main.class.getName());
        File language = new File(String.valueOf(csvl));
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
            LOGGER.log(Level.SEVERE, "Failed CSV Language: " + Arrays.toString(e.getStackTrace()) + ", csv: " + csvl);
            LOGGER.severe("exit 1");
            System.exit(1);
        }
        LOGGER.config("Language CSV Configuration done. CSV: " + csvl);
    }
    private void csvUserLoginInitialise(File ucsv) {
        final Logger LOGGER = Logger.getLogger(Main.class.getName());
        File user = new File(String.valueOf(ucsv));
        BufferedReader br;
        String line;
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(user));
            while ((line = br.readLine()) != null) {
                // use comma as separator
                if (user1 == null) {
                    user1 = line.split(cvsSplitBy);
                } else if (user2 == null) {
                    user2 = line.split(cvsSplitBy);
                } else if (user3 == null) {
                    user3 = line.split(cvsSplitBy);
                } else if (user4 == null) {
                    user4 = line.split(cvsSplitBy);
                } else if (user5 == null) {
                    user5 = line.split(cvsSplitBy);
                } else {
                    LOGGER.severe("too many User names. csv: " + ucsv);
                    tooManyUsers = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.log(Level.SEVERE, "Failed CSV Username: " + Arrays.toString(e.getStackTrace()) + ", csv: " + ucsv);
            LOGGER.severe("exit 2");
            System.exit(2);
        }
        LOGGER.config("CSV Username Configuration done. csv: " + ucsv);
    }
    private static void csvMoneyReader() {
        final Logger LOGGER = Logger.getLogger(Main.class.getName());
        File moonee = new File("src/Math/CSV/moonee.csv");
        BufferedReader br;
        String line;
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(moonee));
            while ((line = br.readLine()) != null) {
                // use comma as separator
                moneyTemp = line.split(cvsSplitBy);
            }
            for (int i = 0; i < moneyTemp.length; i++) moneyTemp[i] = moneyTemp[i].replace("\"", "");
            if (moneyTemp.length-1>=5) money = Arrays.copyOf(moneyTemp, 6);
            LOGGER.config("Money Reader CSV. no. users in CSV: " + (moneyTemp.length - 1));
            moneyTemp = null;
            LOGGER.config("Money Reader CSV done. no. users loaded: " + (money.length - 1));
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            LOGGER.log(Level.SEVERE, "Failed CSV Money Reader: " + Arrays.toString(e.getStackTrace()));
            LOGGER.severe("exit 3");
            System.exit(3);
        }
    }
    static void csvMoneyWriter() {
        final Logger LOGGER = Logger.getLogger(Main.class.getName());
        File inputFile = new File("src/Math/CSV/moonee.csv");
        if (inputFile.delete()) {
            try {
                FileWriter outputFile = new FileWriter("src/Math/CSV/moonee.csv");
                CSVWriter writer = new CSVWriter(outputFile);
                String[] moonees = {"spacer",money[1],money[2],money[3],money[4],money[5]};
                writer.writeAll(Collections.singleton(moonees));
                writer.close();
                money = null;
            }
            catch (IOException e) {
                e.printStackTrace();
                LOGGER.log(Level.SEVERE, "Failed CSV Money Writer: " + Arrays.toString(e.getStackTrace()));
                LOGGER.severe("exit 4");
                System.exit(4);
            }
            csvMoneyReader();
        }
        else {
            LOGGER.severe("Failed to delete moonee.csv");
            LOGGER.severe("exit 4");
            System.exit(4);
        }
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
                LOGGER.severe("exit 5");
                System.exit(5);
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
                LOGGER.severe("exit 6");
                System.exit(6);
            }
        }
    }
    private void csvFailUsernameAlert() {
        final Logger LOGGER = Logger.getLogger(Main.class.getName());
        Alert csvFailPass = new Alert(Alert.AlertType.CONFIRMATION);
        csvFailPass.setTitle("Username load failed");
        csvFailPass.setHeaderText("Do you want to continue program?");
        csvFailPass.setContentText(csvfuMessage);

        Optional<ButtonType> result = csvFailPass.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                LOGGER.log(Level.CONFIG, "User continued program, Uname fail");
            } else {
                LOGGER.log(Level.CONFIG, "User terminated program, Uname fail");
                LOGGER.severe("exit 7");
                System.exit(7);
            }
        }
    }
    private void tooManyUsersAlert() {
        final Logger LOGGER = Logger.getLogger(Main.class.getName());
        Alert csvFailPass = new Alert(Alert.AlertType.CONFIRMATION);
        csvFailPass.setTitle("Too many user profiles");
        csvFailPass.setHeaderText("Too many user profiles - Do you want to continue program?");
        csvFailPass.setContentText("You may not be able to log in");

        Optional<ButtonType> result = csvFailPass.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                LOGGER.log(Level.CONFIG, "User continued program, too many users");
            } else {
                LOGGER.log(Level.CONFIG, "User terminated program, too many users");
                LOGGER.severe("exit 8");
                System.exit(8);
            }
        }
    }
}