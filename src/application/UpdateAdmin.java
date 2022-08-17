package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UpdateAdmin implements Initializable {
	
	@FXML
	private TextField usernameInput;
	
	@FXML
	private TextField passwordInput;
	
	
	@FXML
	private Button updateButton;
	@FXML
	private Button deleteButton;
	
	@FXML
	private TableView<Admins> AdminsTable;
	
	@FXML
	private TableColumn<Admins,String> usernameColumn;

	@FXML
	private TableColumn<Admins,String> passwordColumn;
	

	
	
	@FXML
	private Label goBack;

	@FXML
	private Label XLabel;
	
	Connection conn = null;
	
	
	
	
	// Get Admins
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		showAdmins();
		
		AdminsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		    	Admins selectedAdmin = AdminsTable.getSelectionModel().getSelectedItem();
		    
		    	usernameInput.setText(selectedAdmin.getUsername());
		    	passwordInput.setText(selectedAdmin.getPassword());
	
		    

		    }
		});
	}
	
	
	public ObservableList<Admins> getAdmins(){
		
		ObservableList<Admins> listofAdmins = FXCollections.observableArrayList();
		conn = DatabaseConnection.ConnectDB();	
		String query = "SELECT * FROM admin_account";
		Statement statement;
		ResultSet queryResult;
		
		try {
			statement = conn.createStatement();
			queryResult = statement.executeQuery(query);
			Admins admins;
			while(queryResult.next()) {
				admins = new Admins(queryResult.getInt("id"),
						          queryResult.getString("username"),queryResult.getString("password"));
					
				listofAdmins.add(admins);
			}	
		}catch(Exception ex) {
		ex.printStackTrace();
		}
		
		return listofAdmins;
	}
	

	
	
	/// Update Admins
	public void updateAdmins() {
		conn = DatabaseConnection.ConnectDB();
		Admins selectedAdmin = AdminsTable.getSelectionModel().getSelectedItem();
		String query = "UPDATE admin_account SET username = ?, password = ? WHERE id = ?";
		
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			
			
			String username = usernameInput.getText();
			String password = passwordInput.getText();
		
			

			preparedStmt.setString(1, username);
			preparedStmt.setString(2, password);
			preparedStmt.setInt(3, selectedAdmin.getId());
		

			
			preparedStmt.executeUpdate();
			
			
		    
	    	usernameInput.setText("");
	    	passwordInput.setText("");
			showAdmins();
		
		}catch(Exception ex) {
			ex.printStackTrace();
		}	
	}
	
	
	
	
	
	
	
	
	public void showAdmins() {
		ObservableList<Admins> admins = getAdmins();


		usernameColumn.setCellValueFactory(new PropertyValueFactory<Admins, String>("username"));
		passwordColumn.setCellValueFactory(new PropertyValueFactory<Admins, String>("password"));
		
		AdminsTable.setItems(admins);

	}
	
	
	
	/// Delete admins
	
	public void deleteAdmins() {
		conn = DatabaseConnection.ConnectDB();
		Admins selectedAdmin = AdminsTable.getSelectionModel().getSelectedItem();
		String query = "DELETE FROM admin_account where id = ?";
		
		try {	
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			
			preparedStmt.setInt(1, selectedAdmin.getId());
			
			preparedStmt.execute();

			showAdmins();
				
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	

	
	
	
	// Go back to Dashboard
	public void goBack(MouseEvent event) {
		try {
			Stage stage = (Stage) goBack.getScene().getWindow();
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
	
	// Close
	public void XLabelClick(MouseEvent event) {
		Stage stage = (Stage) XLabel.getScene().getWindow();
		stage.close();
	}
}
