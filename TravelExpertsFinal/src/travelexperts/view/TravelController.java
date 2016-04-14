package travelexperts.view;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import travelexperts.DBConnect;
import travelexperts.MainApp;
import travelexperts.model.Agent;
import travelexperts.model.Customer;

public class TravelController {
	@FXML
    private TableView<Agent> agentList;
    @FXML
    private TableColumn<Agent, String> agtIdColumn;
    @FXML
    private TableColumn<Agent, String> agtFirstNameColumn;
    @FXML
    private TableColumn<Agent, String> agtLastNameColumn;
    
    @FXML
    private TableView<Customer> custList;
    @FXML
    private TableColumn<Customer, String> custIdColumn;
    @FXML
    private TableColumn<Customer, String> custFirstNameColumn;
    @FXML
    private TableColumn<Customer, String> custLastNameColumn;
    @FXML
    private TableColumn<Customer, String> custAgentIdColumn;
    
    @FXML
    private TabPane tpMenus;

    @FXML
    private TextField tfAgtFirstName;

    @FXML
    private TextField tfAgtPhone;

    @FXML
    private ComboBox<String> cbAgtPosition;

    @FXML
    private TextField tfAgtLastName;

    @FXML
    private TextField tfAgtEmail;

    @FXML
    private ComboBox<String> cbAgtAgency;

    @FXML
    private Button btnAddAgent;

    @FXML
    private Button btnActivate;
    
    @FXML
    private Button btnSave;
    
    @FXML 
    private Button btnSaveCust;

    @FXML
    private ComboBox<String> cbAgents;
    
    @FXML
    private Label lblFirstName;
    @FXML
    private Label lblLastName;
    
    @FXML
    private Label lblAddress;
    @FXML
    private Label lblCity;
    @FXML
    private Label lblProvince;
    @FXML
    private Label lblPostal;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblHomePhone;
    @FXML
    private Label lblBusPhone;
    
    private MainApp mainApp;
    private int selectedIndex = -1;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public TravelController() {
    	
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        agtIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
    	agtFirstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        agtLastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        
        custIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        custFirstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        custLastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        custAgentIdColumn.setCellValueFactory(cellData -> cellData.getValue().agentIdProperty());
        
        agentList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> displayAgent(newValue));
        
        custList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> displayCustomer(newValue));
        
        tpMenus.getSelectionModel().selectedItemProperty().addListener(
        	    new ChangeListener<Tab>() {
        	        @Override
        	        public void changed(ObservableValue<? extends Tab> ov, Tab oldValue, Tab newValue) {
        	            if(newValue.getText().equals("Agents")){
        	            	setAgentDisplay();
        	            }
        	            else if(newValue.getText().equals("Customers")){
        	            	setCustomerDisplay();
        	            }
        	        }
        	    }
        	);
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        setAgentDisplay();
        //setCustomerDisplay();
    }
    
    public void setAgentDisplay(){
    	agentList.setItems(mainApp.getAgentsDB());
        ObservableList<String> options = 
        	    FXCollections.observableArrayList(
        	        "Junior Agent",
        	        "Intermediate Agent",
        	        "Senior Agent"
        	    );
        cbAgtPosition.setItems(options);
        cbAgtAgency.setItems(AgentsController.getAgencies());
        agentList.getSelectionModel().selectFirst();
    }
    
    public void setCustomerDisplay(){
    	custList.setItems(mainApp.getCustomersDB());
    	cbAgents.setItems(mainApp.getAvailableAgents());
    	custList.getSelectionModel().selectFirst();
    }
    
    public boolean executeCommand(String command){
    	Connection conn = DBConnect.getConnection();
    	try{
    		Statement stmt = conn.createStatement();
    		stmt.execute(command);
    	}
    	catch(SQLException e){
    		e.printStackTrace();
    		return false;
    	}
    	return true;
    }
    
    @FXML
    private void handleChangeCustomers() {
    	Agent selectedAgent = agentList.getSelectionModel().getSelectedItem();
        if (selectedAgent != null) {
            boolean confirmation = mainApp.showAgentCustomersDialog(selectedAgent.getId());
            if (confirmation) {
            	String command = "UPDATE agents SET Active=0 WHERE AgentId=" + agentList.getSelectionModel().getSelectedItem().getId();
            	if(!executeCommand(command)){
        			/*agentList.getSelectionModel().getSelectedItem().setActive(0);
        			btnActivate.setText("Activate");
        			Stage dialogStage = new Stage();
        			dialogStage.initModality(Modality.WINDOW_MODAL);
        			dialogStage.setScene(new Scene(VBoxBuilder.create().
        			    children(new Text("Deactivated"), new Button("Ok.")).
        			    alignment(Pos.CENTER).padding(new Insets(5)).build()));
        			dialogStage.show();*/
        			Alert alert = new Alert(AlertType.WARNING);
        	        alert.initOwner(mainApp.getPrimaryStage());
        	        alert.setTitle("Deactivation Failed");
        	        alert.setHeaderText("Couldn't deactivate, please try again.");
        	        alert.setContentText("Please try deactivating again.");

        	        alert.showAndWait();
        		}
            	else{
            		Alert alert = new Alert(AlertType.WARNING);
        	        alert.initOwner(mainApp.getPrimaryStage());
        	        alert.setTitle("Success");
        	        alert.setHeaderText("Deactivation saved.");
        	        alert.setContentText("Agent deactivated successfully");

        	        alert.showAndWait();
        	        btnActivate.setText("Activate");
        	        agentList.getSelectionModel().getSelectedItem().setActive(0);
            	}
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }
    
    /*********************************************************************************
     * Section for code related to the agents tab
     ********************************************************************************/
    
    public void displayAgent(Agent selection){
    	if(agentList.getSelectionModel().getSelectedItem() != null){
    		tfAgtFirstName.setText(selection.getFirstName());
        	tfAgtLastName.setText(selection.getLastName());
        	tfAgtPhone.setText(selection.getPhone());
        	cbAgtPosition.setValue(selection.getPosition());
        	tfAgtEmail.setText(selection.getEmail());
        	cbAgtAgency.setValue(selection.getAgency());
        	if(selection.getActive() == 1){
        		btnActivate.setText("Deactivate");
        	}
        	else{
        		btnActivate.setText("Activate");
        	}
        	selectedIndex = agentList.getSelectionModel().getSelectedIndex();
    	}
    	
    }
    
    @FXML
    public void saveAgent(){
    	String sqlStatement = "UPDATE agents SET AgtFirstName='" + tfAgtFirstName.getText()
    		+ "', AgtLastName='" + tfAgtLastName.getText()
    		+ "', AgtBusPhone='" + tfAgtPhone.getText()
    		+ "', AgtEmail='" + tfAgtEmail.getText()
    		+ "', AgtPosition='" + cbAgtPosition.getValue()
    		+ "	', AgencyId=" + cbAgtAgency.getValue()
    		+ " WHERE AgentId=" + agentList.getSelectionModel().getSelectedItem().getId();
    	boolean success = executeCommand(sqlStatement);
    	if(success == true){
    		Agent current = agentList.getSelectionModel().getSelectedItem();
    		current.setFirstName(tfAgtFirstName.getText());
    		current.setLastName(tfAgtLastName.getText());
    		current.setPhone(tfAgtPhone.getText());
    		current.setEmail(tfAgtEmail.getText());
    		current.setPosition(cbAgtPosition.getValue());
    		current.setAgency(cbAgtAgency.getValue());
    	}
    	else{
    		tfAgtFirstName.setText("Error");
    	}
    	
    }
    
    @FXML
    public void addAgent(){
    	if(btnAddAgent.getText().equals("Add Agent")){
    		btnAddAgent.setText("Add");
    		btnActivate.setText("Cancel");
    		btnSave.setVisible(false);
        	tfAgtFirstName.setText("");
        	tfAgtLastName.setText("");
        	tfAgtPhone.setText("");
        	tfAgtEmail.setText("");
        	cbAgtPosition.setValue(null);
        	cbAgtAgency.setValue(null);
        	agentList.getSelectionModel().clearSelection();
        	agentList.setDisable(true);
        	
    	}
    	else{
    		String addAgent = "INSERT INTO agents (AgtFirstName, AgtLastName, AgtBusPhone, AgtEmail, AgtPosition, AgencyId, Active) VALUES('" + tfAgtFirstName.getText()
    			+ "', '" + tfAgtLastName.getText()
    			+ "', '" + tfAgtPhone.getText()
    			+ "', '" + tfAgtEmail.getText()
    			+ "', '" + cbAgtPosition.getValue()
    			+ "', " + cbAgtAgency.getValue() + ","
    			+ " 1)";
    		String id = "";
    		if(executeCommand(addAgent)){
    			Connection conn = DBConnect.getConnection();
    			try{
    				Statement stmt = conn.createStatement();
    				ResultSet rs = stmt.executeQuery("SELECT max(AgentId) FROM agents");
    				rs.next();
    				id = rs.getString(1);
    			}
    			catch(SQLException e){
    				e.printStackTrace();
    			}
    			agentList.setItems(mainApp.addAgent(new Agent(id, tfAgtFirstName.getText(), tfAgtLastName.getText(), tfAgtPhone.getText(), tfAgtEmail.getText(), cbAgtPosition.getValue(), cbAgtAgency.getValue(), 1)));
    			btnSave.setVisible(true);
    			btnActivate.setText("Activate");
    			btnAddAgent.setText("Add Agent");
    			agentList.setDisable(false);
    		}
    	}
    	
    }
    
    @FXML
    public void activateButton(){
    	if(btnActivate.getText().equals("Cancel")){
			btnSave.setVisible(true);
			btnActivate.setText("Activate");
			btnAddAgent.setText("Add Agent");
			agentList.setDisable(false);
    	}
    	else if(btnActivate.getText().equals("Activate")){
    		String command = "UPDATE agents SET Active=1 WHERE AgentId=" + agentList.getSelectionModel().getSelectedItem().getId();
    		if(executeCommand(command)){
    			Alert alert = new Alert(AlertType.WARNING);
    	        alert.initOwner(mainApp.getPrimaryStage());
    	        alert.setTitle("Success");
    	        alert.setHeaderText("Agent activated.");
    	        alert.setContentText("Agent activated successfully.");

    	        alert.showAndWait();
    	        btnActivate.setText("Deactivate");
    	        agentList.getSelectionModel().getSelectedItem().setActive(1);
    		}
    	}
    	else if(btnActivate.getText().equals("Deactivate")){
    		/*String command = "UPDATE agents SET Active=0 WHERE AgentId=" + agentList.getSelectionModel().getSelectedItem().getId();
    		if(executeCommand(command)){
    			agentList.getSelectionModel().getSelectedItem().setActive(0);
    			btnActivate.setText("Activate");
    			Stage dialogStage = new Stage();
    			dialogStage.initModality(Modality.WINDOW_MODAL);
    			dialogStage.setScene(new Scene(VBoxBuilder.create().
    			    children(new Text("Deactivated"), new Button("Ok.")).
    			    alignment(Pos.CENTER).padding(new Insets(5)).build()));
    			dialogStage.show();
    		}*/
    		handleChangeCustomers();
    	}
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
			 cbAgents.setValue(cust.getAgentId());
		 }
		 
	 }
    
    @FXML
    public void saveCustomer(){
   		 String update = "UPDATE customers SET AgentId=" + cbAgents.getValue() + " WHERE CustomerId=" + custList.getSelectionModel().getSelectedItem().getId();
   		 Connection conn = DBConnect.getConnection();
   		 try{
   			 Statement stmt = conn.createStatement();
   			 stmt.execute(update);
   			 custList.getSelectionModel().getSelectedItem().setAgentId(cbAgents.getValue());
   		 }
   		 catch(SQLException e){
   			 e.printStackTrace();
   		}
   	 
    }
}
