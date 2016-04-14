package travelexperts.view;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import travelexperts.DBConnect;
import travelexperts.MainApp;
import travelexperts.model.Customer;

public class DeactivateController {
	@FXML
    private TableView<Customer> customerList;

    @FXML
    private TableColumn<Customer, String> firstNameColumn;

    @FXML
    private TableColumn<Customer, String> lastNameColumn;

    @FXML
    private Label lblFirstName;

    @FXML
    private Label lblLastName;

    @FXML
    private Label lblCity;

    @FXML
    private Label lblPostal;

    @FXML
    private Label lblHomePhone;

    @FXML
    private Label lblBusPhone;

    @FXML
    private Label lblEmail;

    @FXML
    private ComboBox<String> cbAgents;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblProvince;
    
    private MainApp mainApp;

	private Stage dialogStage;
	
	private String currentAgent;
	
	private ObservableList<Customer> check;
	
	private boolean deactivate = false;
	
	public DeactivateController(){
	}
	
	 @FXML
	 private void initialize() {
	    // Initialize the person table with the two columns.
		 
	    customerList.getSelectionModel().selectedItemProperty().addListener(
	        (observable, oldValue, newValue) -> displayCustomer(newValue));
	 }
	 
	 public void setMainApp(MainApp mainApp) {
	        this.mainApp = mainApp;

	        // Add observable list data to the table
	        
	        //cbAgtAgency.setItems(AgentsController.getAgencies());
	 }
	 
	 public void setDialogStage(Stage dialogStage) {
	        this.dialogStage = dialogStage;
	    }
	 
	 public void setAgentId(String agentId) {
	        currentAgent = agentId;
	        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
	        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
	        check = getAgentCustomers();
	        if(check.isEmpty()){
	        	btnSave.setText("Deactivate");
	        }
	        else{
	        	customerList.setItems(getAgentCustomers());
	        	customerList.getSelectionModel().selectFirst();
	        }
	        
	        cbAgents.setItems(getAvailableAgents());
	        cbAgents.getSelectionModel().selectFirst();
	    }
	 
	 public void displayCustomer(Customer cust){
		 if(cust != null){
			 lblFirstName.setText(cust.getFirstName());
			 lblLastName.setText(cust.getLastName());
			 lblAddress.setText(cust.getAddress());
			 lblCity.setText(cust.getCity());
			 lblProvince.setText(cust.getProvince());
			 lblPostal.setText(cust.getPostal());
			 lblEmail.setText(cust.getEmail());
			 lblHomePhone.setText(cust.getHomePhone());
			 lblBusPhone.setText(cust.getBusPhone());
		 }
		 
	 }
	 
	 public ObservableList<Customer> getAgentCustomers(){
		 ObservableList<Customer> custs = FXCollections.observableArrayList();
		 Connection conn = DBConnect.getConnection();
		 try{
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT * FROM customers WHERE AgentId=" + currentAgent);
			 while(rs.next()){
				 custs.add(new Customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12)));
			 }
		 }
		 catch(SQLException e){
			 return null;
		 }
		 return custs;
	 }
	 
	 public ObservableList<String> getAvailableAgents(){
		 ObservableList<String> agents = FXCollections.observableArrayList();
		 Connection conn = DBConnect.getConnection();
		 try{
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT AgentId FROM agents WHERE Active=1");
			 while(rs.next()){
				 if(!rs.getString(1).trim().equals(currentAgent.trim())){
					 agents.add(rs.getString(1));
				 }
			 }
		 }
		 catch(SQLException e){
			 return null;
		 }
		 return agents;
	 }
	 
	 @FXML
	 public boolean confirmation(){
		 return deactivate;
	 }
	 
	 @FXML
	 private void closeWindow(){
	     // get a handle to the stage
	     Stage stage = (Stage) btnCancel.getScene().getWindow();
	     // do what you have to do
	     stage.close();
	 }
	 
	 @FXML
	 private void checkDeactivate(){
		 if(btnSave.getText().equals("Deactivate")){
			 deactivate = true;
			 dialogStage.close();
		 }
		 else{
			 changeAgent();
		 }
	 }
	 
	 @FXML
	 private void changeAgent(){
		 String update = "UPDATE customers SET AgentId=" + cbAgents.getValue() + " WHERE CustomerId=" + customerList.getSelectionModel().getSelectedItem().getId();
		 Connection conn = DBConnect.getConnection();
		 try{
			 Statement stmt = conn.createStatement();
			 stmt.execute(update);
			 
			 check = getAgentCustomers();
			 customerList.setItems(getAgentCustomers());
			 if(check.isEmpty()){
				 btnSave.setText("Deactivate");
			 }
			 else{
				 customerList.getSelectionModel().selectFirst();
			 }
				 
			 
		 }
		 catch(SQLException e){
			 e.printStackTrace();
		}
	 }
		 
	
}
