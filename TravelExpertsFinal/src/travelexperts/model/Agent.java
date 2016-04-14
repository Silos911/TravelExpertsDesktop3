package travelexperts.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Agent {

	private StringProperty id;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty phone;
    private StringProperty email;
    private StringProperty position;
    private StringProperty agency;
    private IntegerProperty active;

    /**
     * Default constructor.
     */
    public Agent() {
        this(null, null, null, null, null, null, null, 0);
    }

    /**
     * Constructor with some initial data.
     * 
     * @param firstName
     * @param lastName
     */
    public Agent(String id, String firstName, String lastName, String phone, String email, String position, String agency, int active) {
        this.id = new SimpleStringProperty(id);
    	this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);

        // Some initial dummy data, just for convenient testing.
        this.phone = new SimpleStringProperty(phone);
        this.email = new SimpleStringProperty(email);
        this.position = new SimpleStringProperty(position);
        this.agency = new SimpleStringProperty(agency);
        this.active = new SimpleIntegerProperty(active);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getPosition() {
        return position.get();
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

    public StringProperty positionProperty() {
        return position;
    }

    public String getAgency() {
        return agency.get();
    }

    public void setAgency(String agency) {
        this.agency.set(agency);
    }

    public StringProperty agencyProperty() {
        return agency;
    }
    
    public String getId(){
    	return id.get();
    }
    
    public void setId(String id){
    	this.id.set(id);
    }
    
    public StringProperty idProperty(){
    	return id;
    }
    
    public int getActive(){
    	return active.get();
    }
    
    public void setActive(int active){
    	this.active.set(active);
    }
    
    public IntegerProperty activeProperty(){
    	return active;
    }
}
