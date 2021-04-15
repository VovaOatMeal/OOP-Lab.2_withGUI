package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static sample.Main.*;

public class Controller {

    @FXML
    private Button editRowButton;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Subscriber> tableID;

    @FXML
    private TableColumn<Subscriber, Integer> idCol;

    @FXML
    private TableColumn<Subscriber, String> firstnameCol;

    @FXML
    private TableColumn<Subscriber, String> lastnameCol;

    @FXML
    private TableColumn<Subscriber, String> mobileCol;

    @FXML
    private TableColumn<Subscriber, Boolean> contractCol;

    @FXML
    private Button showButton;

    @FXML
    void initialize() {

        try {
            /*
            * Required service commands to run the program and DB.
            */
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            if (!connection.isClosed()) {
                System.out.println("Running the server...");
            } else {
                System.out.println("Cannot run the server: connection is closed.");
                return;
            }

            if (connection != null) {
                statement = connection.createStatement();
            } else {
                System.out.println("connection is null.");
                return;
            }

            // The end of service commands

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        idCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        firstnameCol.setCellValueFactory(new PropertyValueFactory<>("First_Name"));
        lastnameCol.setCellValueFactory(new PropertyValueFactory<>("Last_Name"));
        mobileCol.setCellValueFactory(new PropertyValueFactory<>("Mobile"));
        contractCol.setCellValueFactory(new PropertyValueFactory<>("Contract"));

        // the same code but based on lambda-expressions
        /*
        idCol.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        firstnameCol.setCellValueFactory(data -> data.getValue().first_NameProperty());
        lastnameCol.setCellValueFactory(data -> data.getValue().last_NameProperty());
        mobileCol.setCellValueFactory(data -> data.getValue().mobileProperty());
        contractCol.setCellValueFactory(data -> data.getValue().contractProperty());
*/

        // code for showing data on startup
        /*
        ObservableList<Subscriber> subscriberObservableList = FXCollections.observableArrayList(
                subscriberArrayList);

        tableID.setItems(subscriberObservableList);
        */


        //tableID.getItems().add(); // for adding Subscriber items to the table
        //tableID.remove(...); // for removing an item
    }

    //private ObservableList<Subscriber> subscriberObservableList = FXCollections.observableArrayList(subscriberArrayList);

    public void showData() throws SQLException {
        if (!subscriberArrayList.isEmpty()) subscriberArrayList.clear();
        DBQueries.selectQuery();

        ObservableList<Subscriber> subscriberObservableList = null;
        subscriberObservableList = FXCollections.observableArrayList(
                subscriberArrayList);

        tableID.setItems(subscriberObservableList);
    }

    public void editWindow(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/updateRowWindow.fxml"));
            Stage stage = new Stage();
            Stage primaryStage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow(); // accessing primaryStage from Main
            stage.initOwner(primaryStage); // setting which window will be locked
            stage.initModality(Modality.WINDOW_MODAL); // making new stage modal
            stage.setTitle("Edit row");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            showData();

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }
}
