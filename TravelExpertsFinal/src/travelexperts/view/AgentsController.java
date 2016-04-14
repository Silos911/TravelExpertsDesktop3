package travelexperts.view;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import travelexperts.DBConnect;

public class AgentsController {
	public static ObservableList<String> getAgencies(){
		Connection conn = DBConnect.getConnection();
		ObservableList<String> agencies = FXCollections.observableArrayList();
		try{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT AgencyId FROM agencies");
			while(rs.next()){
				agencies.add(rs.getString(1));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		return agencies;
	}
}
