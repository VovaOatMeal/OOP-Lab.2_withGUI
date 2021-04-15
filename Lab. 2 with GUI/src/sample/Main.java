package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Telephone station");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static ArrayList<Subscriber> subscriberArrayList = new ArrayList<>();
    public static Connection connection = null;
    public static Statement statement;
    public static final String URL = "jdbc:mysql://localhost:3306/telephone_station";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "1234";


    public static void main(String[] args) {

        try {
            launch(args);

            if (!connection.isClosed()) {
                connection.close();
                System.out.println("The server has been successfully closed.");
            }
            connection = null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}
