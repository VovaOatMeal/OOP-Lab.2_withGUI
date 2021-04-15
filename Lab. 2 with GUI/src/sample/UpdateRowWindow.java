package sample;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateRowWindow {

    @FXML
    public Button loadButton;

    @FXML
    public Button cancelButton;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField first_nameField;

    @FXML
    private TextField last_nameField;

    @FXML
    private TextField idField;

    @FXML
    private TextField mobileField;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private Button submitButton;

    private final ObservableList<String> availableChoices = FXCollections.observableArrayList("Yes", "No");

    @FXML
    void initialize() {
        choiceBox.setItems(availableChoices);
    }

    public void loadAction(ActionEvent actionEvent) {

        int id;
        try {
            id = Integer.parseInt(idField.getText());
            if (id <= 0) {
                Alert dialog = new Alert(AlertType.ERROR);
                dialog.setTitle("Wrong ID");
                dialog.setHeaderText("Entered value is zero or less.");
                dialog.setContentText("Please enter only integer numbers that bigger than 0.");
                dialog.showAndWait();

                idField.clear();
                return;
            }
        } catch (NumberFormatException formatException) {
            //formatException.printStackTrace();

            Alert dialog = new Alert(AlertType.ERROR);
            dialog.setTitle("Wrong ID");
            dialog.setHeaderText("You've entered a non-integer value.");
            dialog.setContentText("Please enter only integer numbers.");
            dialog.showAndWait();

            idField.clear();
            return;
        }

        first_nameField.setDisable(false);
        last_nameField.setDisable(false);
        mobileField.setDisable(false);
        choiceBox.setDisable(false);
        submitButton.setDisable(false);
        idField.setDisable(true);
        // fill in all the fields with object's values taken by the id from the database

        ResultSet resultSet = null;
        try {
            resultSet = DBQueries.loadQuery(id);
        } catch (SQLException exception) {
            System.out.println(exception.toString() +
                    "\nHappened in loadAction() when initializing resultSet");
            return;
        }

        if (resultSet != null) {
            try {
                resultSet.next();
                String First_Name = resultSet.getString("First_Name");
                String Last_Name = resultSet.getString("Last_Name");
                String mobile = resultSet.getString("Mobile");
                boolean contract = resultSet.getBoolean("Contract");

                first_nameField.setText(First_Name);
                last_nameField.setText(Last_Name);
                mobileField.setText(mobile);
                if (contract) {
                    choiceBox.setValue("Yes");
                } else {
                    choiceBox.setValue("No");
                }
            } catch (SQLException exception) {
                System.out.println(exception.toString() +
                        "\nHappened in loadAction() when getting values from resultSet");
                return;
            }
        } else {
            System.out.println("Error: resultSet is null.");
            return;
        }
    }

    public void submitAction(ActionEvent event) {
        boolean tempBool;
        tempBool = choiceBox.getValue().equals("Yes");

        DBQueries.updateQuery(Integer.parseInt(idField.getText()), first_nameField.getText(), last_nameField.getText(),
                mobileField.getText(), tempBool);
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }

    public void cancelAction(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
