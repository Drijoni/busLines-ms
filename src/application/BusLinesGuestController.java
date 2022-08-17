package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BusLinesGuestController implements Initializable{
	
	
	@FXML
	private ComboBox<String> locationDropdown;
	
	@FXML
	private ComboBox<String> destinationDropdown;
	
	@FXML
	private ComboBox<String> daysDropdown;
	
	@FXML
	private Label XLabel;
	
	
	@FXML
	private TableView <Lines> busLinesTable;
	
	@FXML
	private TableColumn<Lines,String> locationColumn;
	
	@FXML
	private TableColumn<Lines,String> destinationColumn;
	
	@FXML
	private TableColumn<Lines,String> timeColumn;
	
	@FXML
	private TableColumn<Lines,String> daysColumn;
	
	@FXML
	private TableColumn<Lines,String> companyColumn;
	
	
	@FXML
	private Label goBackLabel;
	
	
	Connection conn = null;
	


	
	
	
	// Close
	public void XLabelClick(MouseEvent event) {
		Stage stage = (Stage) XLabel.getScene().getWindow();
		stage.close();
	}
	
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		
		locationDropdown.setItems(FXCollections.observableArrayList("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"));
		destinationDropdown.setItems(FXCollections.observableArrayList("0","05","10","15","20","25","30","35","40","45","50","55"));
		daysDropdown.setItems(FXCollections.observableArrayList("Monday","Tuesday","Wednsday","Thursday","Friday","Saturday","Sunday","Monday to Friday","Monday to Saturday","Everyday"));
		
		/// "FROM and TO" Dropbox
		try {
			conn = DatabaseConnection.ConnectDB();
			ResultSet query;
			query = conn.createStatement().executeQuery("select location from locations");
			ObservableList<String> data = FXCollections.observableArrayList();
			
			while(query.next()) {

				data.add(new String(query.getString(1)));
			}
			
			locationDropdown.setItems(data);
			destinationDropdown.setItems(data);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	
	
	
	public ObservableList<Lines> getLines() {
		
		
		
		String fromDropdownValue = locationDropdown.getSelectionModel().getSelectedItem().toString();	
		String toDropdownValue = destinationDropdown.getSelectionModel().getSelectedItem().toString();
		String daysDropdownValue = daysDropdown.getSelectionModel().getSelectedItem().toString();
			
	
			ObservableList<Lines> listofLines = FXCollections.observableArrayList();
			conn = DatabaseConnection.ConnectDB();
			
			String query = "select * from bus.bus_lines where fromLocation =" + "'" + fromDropdownValue + "' AND toLocation =" + "'" + toDropdownValue + "' AND days =" + "'" + daysDropdownValue + "'";
			Statement statement;
			ResultSet queryResult;
			
			try {
				statement = conn.createStatement();
				queryResult = statement.executeQuery(query);
				Lines lines;
				while(queryResult.next()) {
					lines = new Lines(queryResult.getInt("id"),queryResult.getString("fromLocation"),queryResult.getString("toLocation"),queryResult.getString("time"),queryResult.getString("days"), queryResult.getString("company"));
						
					listofLines.addAll(lines);
				}	
				
			

				
			}catch(Exception ex) {
			ex.printStackTrace();
			}
			

			return listofLines;
			
			
			
	}
	
	
	public void showLines() {
		
		ObservableList<Lines> lines = getLines();
		
		locationColumn.setCellValueFactory(new PropertyValueFactory<Lines, String>("location"));
		destinationColumn.setCellValueFactory(new PropertyValueFactory<Lines, String>("destination"));
		daysColumn.setCellValueFactory(new PropertyValueFactory<Lines, String>("days"));
		timeColumn.setCellValueFactory(new PropertyValueFactory<Lines, String>("time"));
		
		companyColumn.setCellValueFactory(new PropertyValueFactory<Lines, String>("company"));
		
		
		busLinesTable.setItems(lines);

	}
	

	// GO back
	
	public void goBack(MouseEvent event) {
		try {
			Stage stage = (Stage) goBackLabel.getScene().getWindow();
			stage.close();
			Parent root = FXMLLoader.load(getClass().getResource("welcome.fxml"));
			Stage welcome = new Stage();
			welcome.initStyle(StageStyle.UNDECORATED);
			welcome.setScene(new Scene(root, 600, 400));
			welcome.show();
			welcome.setResizable(false);
		} catch(Exception e) {
			e.printStackTrace();
			e.getCause();
		}
	}
	

}
