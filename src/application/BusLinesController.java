package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JComboBox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BusLinesController implements Initializable{

	@FXML
	private AnchorPane LocationsScreen;
	
	@FXML
	private AnchorPane CompaniesScreen;
	
	@FXML
	private AnchorPane BusLinesScreen;
	
	
	@FXML
	private Button LocationsBtn;
	
	@FXML
	private Button CompaniesBtn;
	
	@FXML
	private Button BusLinesBtn;
	
	
	@FXML 
	private Label GoBackLabel;
	
	
	/* Location Screen*/
	
	@FXML
	private TextField LocationTextField;

	@FXML
	private Button LocationInsertBtn;
	
	@FXML
	private Button LocationUpdateBtn;
	
	@FXML
	private Button LocationDeleteBtn;
	
	@FXML
	private TableView <Locations> LocationTable;
	
	@FXML
	private TableColumn<Locations,String> locationColumn;
	
	@FXML
	private TableColumn<Locations,Integer> idLocationColumn;
	
	
	@FXML
	private Label InformationLabel;
	
	/*
	 * Companies Screen*/
	
	@FXML
	private TextField CompanyTextField;

	@FXML
	private Button CompanyInsertBtn;
	
	@FXML
	private Button CompanyUpdateBtn;
	
	@FXML
	private Button CompanyDeleteBtn;
	
	@FXML
	private TableView <Company> CompanyTable;
	
	@FXML
	private TableColumn<Company,String> companyColumn;
	
	@FXML
	private TableColumn<Company,Integer> idCompanyColumn;
	

	@FXML
	private Label ManageAddedLinesbtn;
	
	Connection conn = null;
	
	
	
	
	/* Go back */
	
	public void goBack(MouseEvent event) {
		try {
			Stage stage = (Stage) GoBackLabel.getScene().getWindow();
			stage.close();
			Parent root = FXMLLoader.load(getClass().getResource("adminDashboard.fxml"));
			Stage adminDashboard = new Stage();
			adminDashboard.initStyle(StageStyle.UNDECORATED);
			adminDashboard.setScene(new Scene(root, 600, 400));
			adminDashboard.show();
			adminDashboard.setResizable(false);
		} catch(Exception e) {
			e.printStackTrace();
			e.getCause();
		}
	}
	
	
	
	public void goToDelete(MouseEvent event) {
		try {
			Stage stage = (Stage) ManageAddedLinesbtn.getScene().getWindow();
			stage.close();
			Parent root = FXMLLoader.load(getClass().getResource("deleteBusLines.fxml"));
			Stage deleteDashboard = new Stage();
			deleteDashboard.initStyle(StageStyle.UNDECORATED);
			deleteDashboard.setScene(new Scene(root, 600, 400));
			deleteDashboard.show();
			deleteDashboard.setResizable(false);
		} catch(Exception e) {
			e.printStackTrace();
			e.getCause();
		}
	}
	
	
	
	/* SHOW / HIDE Screens */
	@FXML
	public void showLocationsScreen(ActionEvent event) {
		LocationsScreen.setVisible(true);
		CompaniesScreen.setVisible(false);
		BusLinesScreen.setVisible(false);
		showLocations();
	}
	
	
	@FXML
	public void showCompaniesScreen(ActionEvent event) {
		LocationsScreen.setVisible(false);
		CompaniesScreen.setVisible(true);
		BusLinesScreen.setVisible(false);
	}
	
	
	@FXML
	public void showBusLinesScreen(ActionEvent event) {
		LocationsScreen.setVisible(false);
		CompaniesScreen.setVisible(false);
		BusLinesScreen.setVisible(true);
	}
	
	
	/* Location Screen*/
	
	
	public void showLocations() {
		
		ObservableList<Locations> locations = getLocations();

		locationColumn.setCellValueFactory(new PropertyValueFactory<Locations, String>("location"));
		idLocationColumn.setCellValueFactory(new PropertyValueFactory<Locations, Integer>("id"));
		
		LocationTable.setItems(locations);

	}
	
	/// Add location
	public void addLocation(ActionEvent event) {

		if(LocationTextField.getText().isEmpty() == false) {
				
				conn = DatabaseConnection.ConnectDB();
				
				String sql = "INSERT INTO locations (location) VALUES ('" + LocationTextField.getText() + "')";
				
				try {
					
					Statement statement = conn.createStatement();
					statement.executeUpdate(sql);
					
					InformationLabel.setTextFill(Color.web("#000679"));
					InformationLabel.setText("A Location has been added successfully!");
					LocationTextField.setText("");
					
					
					
				}catch(Exception e) {
					System.out.println("Exception " + e);
				}
			
			
		}else {
			InformationLabel.setTextFill(Color.web("#ff0000"));
			InformationLabel.setText("Please fill all the blanks");
		}
		
		
	}
	
	
	
	
	/* Bus Lines screen*/
	
	@FXML
	private ComboBox<String> fromDropdown;
	
	@FXML
	private ComboBox<String> toDropdown;
	
	@FXML
	private ComboBox<String> companyDropdown;

	
	
	@FXML
	private ComboBox<String> hoursDropdown;
	
	@FXML
	private ComboBox<String> minsDropdown;
	
	@FXML
	private ComboBox<String> daysDropdown;
	
	/// Initialize, get the value of the column which user has clicked
	
	
	public void Select(ActionEvent event) {
		String fromDropdownValue = fromDropdown.getSelectionModel().getSelectedItem().toString();
		String toDropdownValue = toDropdown.getSelectionModel().getSelectedItem().toString();
		String companyDropdownValue = companyDropdown.getSelectionModel().getSelectedItem().toString();
		String hoursDropdownValue = hoursDropdown.getSelectionModel().getSelectedItem().toString();
		String minsDropdownValue = minsDropdown.getSelectionModel().getSelectedItem().toString();
		
		String timeValue = hoursDropdownValue +":" + minsDropdownValue;
		
		String daysDropdownValue = daysDropdown.getSelectionModel().getSelectedItem().toString();
		
		try {
			
		System.out.println(fromDropdownValue.getClass(). getSimpleName());
		System.out.println(toDropdownValue);
		System.out.println(companyDropdownValue);
		//System.out.println(hoursDropdownValue);
		//System.out.println(minsDropdownValue);
		System.out.println(daysDropdownValue);
		System.out.println(timeValue);
		
		}
	
		catch(Exception e) {
			System.out.println("Please fill the forms");
		}
		
		
		
		
		/// Send the Values on DB
		
		
		if(fromDropdownValue != "") {
			
			conn = DatabaseConnection.ConnectDB();
			
			String sql = "INSERT INTO bus_lines (fromLocation, toLocation,company,time,days) VALUES ('" + fromDropdownValue + "','" + toDropdownValue + "','" + companyDropdownValue + "','" + timeValue + "','" + daysDropdownValue + "')";
			
			try {
				
				Statement statement = conn.createStatement();
				statement.executeUpdate(sql);
	
				
			}catch(Exception e) {
				System.out.println("Exception " + e);
			}
		
		
	}else {
		System.out.println("Fill the blanks");
	}
		
		}

	
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		
		hoursDropdown.setItems(FXCollections.observableArrayList("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"));
		minsDropdown.setItems(FXCollections.observableArrayList("0","05","10","15","20","25","30","35","40","45","50","55"));
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
			
			fromDropdown.setItems(data);
			toDropdown.setItems(data);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

		
		/// ""Company" Dropbox
		try {
			conn = DatabaseConnection.ConnectDB();
			ResultSet query;
			query = conn.createStatement().executeQuery("select company from companies");
			ObservableList<String> data = FXCollections.observableArrayList();
			
			while(query.next()) {

				data.add(new String(query.getString(1)));
			}
			
			companyDropdown.setItems(data);

		} catch (SQLException e) {
			
			e.printStackTrace();
		}




		
		
		// For Locations
		showLocations();
		LocationTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		    	Locations selectedLocation = LocationTable.getSelectionModel().getSelectedItem();
		    	System.out.println(selectedLocation);
		    
		    	LocationTextField.setText(selectedLocation.getLocation());
	
		    

		    }
		});
		
		// For Companies
		showCompanies();
		CompanyTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		    	Company selectedCompany = CompanyTable.getSelectionModel().getSelectedItem();

		    
		    	CompanyTextField.setText(selectedCompany.getCompany());
	
	
		    }
		});
	
	}
	
	
	public ObservableList<Locations> getLocations(){
		
		ObservableList<Locations> listofLocations = FXCollections.observableArrayList();
		conn = DatabaseConnection.ConnectDB();	
		String query = "SELECT * FROM locations";
		Statement statement;
		ResultSet queryResult;
		
		try {
			statement = conn.createStatement();
			queryResult = statement.executeQuery(query);
			Locations locations;
			while(queryResult.next()) {
				locations = new Locations(queryResult.getInt("id"),
						          queryResult.getString("location"));
					
				listofLocations.add(locations);
			}	
		}catch(Exception ex) {
		ex.printStackTrace();
		}
		
		return listofLocations;
	}
	

	
	
	/// Update Locations
	public void updateLocations() {
		conn = DatabaseConnection.ConnectDB();
		Locations selectedLocation = LocationTable.getSelectionModel().getSelectedItem();
		String query = "UPDATE locations SET location = ? WHERE id = ?";
		
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			
			
			String location = LocationTextField.getText();
		
			

			preparedStmt.setString(1, location);
			preparedStmt.setInt(2, selectedLocation.getId());
		

			
			preparedStmt.executeUpdate();
			
			
		    
			LocationTextField.setText("");
	    	showLocations();
		
		}catch(Exception ex) {
			ex.printStackTrace();
		}	
	}
	
	
	// Delete Location
	
	
	public void deleteLocation() {
		conn = DatabaseConnection.ConnectDB();
		Locations selectedLocation = LocationTable.getSelectionModel().getSelectedItem();
		String query = "DELETE FROM locations where id = ?";
		
		try {	
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			
			preparedStmt.setInt(1, selectedLocation.getId());
			
			preparedStmt.execute();
			
			showLocations();
			LocationTextField.setText("");
				
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	/*
	 * 
	 * COMPANIES SCREEN
	 */
	
	public void showCompanies() {
		
		ObservableList<Company> companies = getCompanies();

		companyColumn.setCellValueFactory(new PropertyValueFactory<Company, String>("company"));
		idCompanyColumn.setCellValueFactory(new PropertyValueFactory<Company, Integer>("id"));
		
		CompanyTable.setItems(companies);

	}

	
	
	/// Add Company
		public void addCompany(ActionEvent event) {
			
			if(CompanyTextField.getText().isEmpty() == false) {
					
					conn = DatabaseConnection.ConnectDB();
					
					String sql = "INSERT INTO companies (company) VALUES ('" + CompanyTextField.getText() + "')";
					
					try {
						
						Statement statement = conn.createStatement();
						statement.executeUpdate(sql);
						
						CompanyTextField.setText("");
						
						showCompanies();
						
					}catch(Exception e) {
						System.out.println("Exception " + e);
					}
				
				
			}else {
				//InformationLabel.setTextFill(Color.web("#ff0000"));
				//InformationLabel.setText("Please fill all the blanks");
			}
			
			
		}
		
	

		
		
		public ObservableList<Company> getCompanies(){
			
			ObservableList<Company> listofCompanies = FXCollections.observableArrayList();
			conn = DatabaseConnection.ConnectDB();	
			String query = "SELECT * FROM companies";
			Statement statement;
			ResultSet queryResult;
			
			try {
				statement = conn.createStatement();
				queryResult = statement.executeQuery(query);
				Company companies;
				while(queryResult.next()) {
					companies = new Company(queryResult.getInt("id"),
							          queryResult.getString("company"));
						
					listofCompanies.add(companies);
				}	
			}catch(Exception ex) {
			ex.printStackTrace();
			}
			
			return listofCompanies;
		}
		

		/// Update Companies
		public void updateCompanies() {
			conn = DatabaseConnection.ConnectDB();
			Company selectedCompany = CompanyTable.getSelectionModel().getSelectedItem();
			String query = "UPDATE companies SET company = ? WHERE id = ?";
			
			try {
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				
				
				String company = CompanyTextField.getText();
			
				

				preparedStmt.setString(1, company);
				preparedStmt.setInt(2, selectedCompany.getId());
			

				
				preparedStmt.executeUpdate();
				
				
			    
				CompanyTextField.setText("");
				showCompanies();
			
			}catch(Exception ex) {
				ex.printStackTrace();
			}	
		}
		
		
		public void deleteCompany() {
			conn = DatabaseConnection.ConnectDB();
			Company selectedCompany = CompanyTable.getSelectionModel().getSelectedItem();
			String query = "DELETE FROM companies where id = ?";
			
			try {	
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				
				preparedStmt.setInt(1, selectedCompany.getId());
				
				preparedStmt.execute();
				
				showCompanies();
				CompanyTextField.setText("");
					
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
		
		
		
		
		
		/*
		 * 
		 * ADD A LINE SCREEN
		 * 
		 * 
		 * */
		
		
		

	
	
	
	
}
