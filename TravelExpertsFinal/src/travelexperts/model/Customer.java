package travelexperts.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Customer {
	private StringProperty id;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty address;
    private StringProperty city;
    private StringProperty postal;
    private StringProperty province;
    private StringProperty homePhone;
    private StringProperty busPhone;
    private StringProperty email;
    private StringProperty agentId;
    
    public Customer() {
        this(null, null, null, null, null, null, null, null, null, null, null);
    }

    /**
     * Constructor with some initial data.
     * 
     * @param firstName
     * @param lastName
     */
    public Customer(String id, String firstName, String lastName, String address, String city, String province, String postal, String homePhone, String busPhone, String email, String agentId) {
        this.id = new SimpleStringProperty(id);
    	this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);

        // Some initial dummy data, just for convenient testing.
        this.address = new SimpleStringProperty(address);
        this.city = new SimpleStringProperty(city);
        this.postal = new SimpleStringProperty(postal);
        this.province = new SimpleStringProperty(province);
        this.homePhone = new SimpleStringProperty(homePhone);
        this.busPhone = new SimpleStringProperty(busPhone);
        this.email = new SimpleStringProperty(email);
        this.agentId = new SimpleStringProperty(agentId);
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
    
    public String getHomePhone() {
        return homePhone.get();
    }

    public void setHomePhone(String homePhone) {
        this.homePhone.set(homePhone);
    }

    public StringProperty homePhoneProperty() {
        return homePhone;
    }
    public String getBusPhone() {
        return busPhone.get();
    }

    public void setBusPhone(String busPhone) {
        this.busPhone.set(busPhone);
    }

    public StringProperty busPhoneProperty() {
        return busPhone;
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
    
    public String getId(){
    	return id.get();
    }
    
    public void setId(String id){
    	this.id.set(id);
    }
    
    public StringProperty idProperty(){
    	return id;
    }
    
    public String getAgentId(){
    	return agentId.get();
    }
    
    public void setAgentId(String agentId){
    	this.agentId.set(agentId);
    }
    
    public StringProperty agentIdProperty(){
    	return agentId;
    }
    
    public String getAddress(){
    	return address.get();
    }
    
    public void setAddres(String address){
    	this.address.set(address);
    }
    
    public StringProperty addressProperty(){
    	return address;
    }
    
    public String getCity(){
    	return city.get();
    }
    
    public void setCity(String city){
    	this.city.set(city);
    }
    
    public StringProperty cityProperty(){
    	return city;
    }
    
    public String getProvince(){
    	return province.get();
    }
    
    public void setProvince(String province){
    	this.province.set(province);
    }
    
    public StringProperty provinceProperty(){
    	return province;
    }
    
    public String getPostal(){
    	return postal.get();
    }
    
    public void setPostal(String postal){
    	this.postal.set(postal);
    }
    
    public StringProperty postalProperty(){
    	return postal;
    }
}
