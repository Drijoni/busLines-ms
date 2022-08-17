package application;

import java.sql.Connection;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RegisterAdmin {

	

	@FXML
	private Label goBack;

	@FXML
	private Label XLabel;
	
	@FXML
	private TextField UsernameTextField;
	
	@FXML
	private PasswordField PasswordField;
	
	@FXML
	private PasswordField ConfirmPasswordField;
	
	@FXML
	private Label InformationLabel;
	

	Connection conn = null;
	
	
	
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
	
	///Register
	
	public void registerAdmin(ActionEvent event) {
		
		if(UsernameTextField.getText().isEmpty() == false
				&& PasswordField.getText().isEmpty() == false && ConfirmPasswordField.getText().isEmpty() == false
				) {
			
			if(PasswordField.getText().equals(ConfirmPasswordField.getText())) {
				
				conn = DatabaseConnection.ConnectDB();
				
				String sql = "INSERT INTO admin_account (username, password) VALUES ('" + UsernameTextField.getText() + "','" + PasswordField.getText() + "')";
				
				try {
					
					Statement statement = conn.createStatement();
					statement.executeUpdate(sql);
					
					System.out.println("The user is register");
					InformationLabel.setTextFill(Color.web("#000679"));
					InformationLabel.setText("An admin has been added successfully!");
					UsernameTextField.setText("");
					PasswordField.setText("");
					ConfirmPasswordField.setText("");
					
				}catch(Exception e) {
					System.out.println("Exception in LogIn Controller " + e);
				}
				
			}else {
				InformationLabel.setTextFill(Color.web("#ff0000"));
				InformationLabel.setText("Passwords does not match!");
			}
			
		}else {
			InformationLabel.setTextFill(Color.web("#ff0000"));
			InformationLabel.setText("Please fill all the blanks");
		}
		
		
	}
		
}
