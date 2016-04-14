package travelexperts;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import travelexperts.DBConnect;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import travelexperts.model.Agent;
import travelexperts.model.Customer;
import travelexperts.view.DeactivateController;
import travelexperts.view.TravelController;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    
    private ObservableList<Agent> agentData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp() {
        // Add some sample data
        //agentData = getAgentsDB();
    	Splash splash = new Splash();
    	Connection conn = DBConnect.getConnection();
    	try{
    		Statement stmt = conn.createStatement();
    		ResultSet rs = stmt.executeQuery("SELECT * FROM agents");
    		while(rs.next()){
    			agentData.add(new Agent(rs.getString(1), rs.getString(2), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9)));
    			System.out.println(rs.getString(1));
    		}
    		
    	}
    	catch(SQLException e){
    		
    	}
    }

    public ObservableList<Agent> getAgentsDB(){
    	Connection conn = DBConnect.getConnection();
    	ObservableList<Agent> agents = FXCollections.observableArrayList();
    	try{
    		Statement stmt = conn.createStatement();
    		ResultSet rs = stmt.executeQuery("SELECT * FROM agents");
    		while(rs.next()){
    			agents.add(new Agent(rs.getString(1), rs.getString(2), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9)));
    			System.out.println(rs.getString(1));
    		}
    		
    	}
    	catch(SQLException e){
    		
    	}
    	return agents;
    }
    
    /**
     * Returns the data as an observable list of Persons. 
     * @return
     */
    public ObservableList<Agent> getAgentData() {
        return agentData;
    }
    
    public ObservableList<Agent> addAgent(Agent add){
    	agentData.add(add);
    	return agentData;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("TravelExperts");

        initRootLayout();

        showAgents();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showAgents() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainPage.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);
            
            TravelController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public boolean showAgentCustomersDialog(String agentId) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/AgentCustomers.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Deactivate Agent");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            DeactivateController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setAgentId(agentId);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.confirmation();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public ObservableList<Customer> getCustomersDB(){
		 ObservableList<Customer> custs = FXCollections.observableArrayList();
		 Connection conn = DBConnect.getConnection();
		 try{
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT * FROM customers");
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
				 agents.add(rs.getString(1));
			 }
		 }
		 catch(SQLException e){
			 return null;
		 }
		 return agents;
	 }

}
