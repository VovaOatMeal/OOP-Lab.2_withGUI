package sample;

import javafx.beans.property.*;

public class Subscriber {
    private IntegerProperty id;
    private StringProperty First_Name;
    private StringProperty Last_Name;
    private StringProperty mobile;
    private BooleanProperty contract;

    public Subscriber(int id, String first_Name, String last_Name, String mobile, boolean contract) {
        this.id = new SimpleIntegerProperty(id);
        First_Name = new SimpleStringProperty(first_Name);
        Last_Name = new SimpleStringProperty(last_Name);
        this.mobile = new SimpleStringProperty(mobile);
        this.contract = new SimpleBooleanProperty(contract);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getFirst_Name() {
        return First_Name.get();
    }

    public StringProperty first_NameProperty() {
        return First_Name;
    }

    public String getLast_Name() {
        return Last_Name.get();
    }

    public StringProperty last_NameProperty() {
        return Last_Name;
    }

    public String getMobile() {
        return mobile.get();
    }

    public StringProperty mobileProperty() {
        return mobile;
    }

    public boolean isContract() {
        return contract.get();
    }

    public BooleanProperty contractProperty() {
        return contract;
    }
}
