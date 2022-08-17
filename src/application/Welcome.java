package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Welcome {

	@FXML
	private Label XLabel;
	
	@FXML
	private TextField usernameTextField;
	
	@FXML
	private PasswordField passwordTextField;
	
	@FXML
	private Label loginMessageLabel;
	
	@FXML
	private Label busLinesBtn;
	
	
	
	Connection conn = null;
	
	
	
	
	public void goToLines(MouseEvent event) {
		try {
			Stage stage = (Stage) busLinesBtn.getScene().getWindow();
			stage.close();
			Parent root = FXMLLoader.load(getClass().getResource("busLinesGuest.fxml"));
			Stage Dashboard = new Stage();
			Dashboard.initStyle(StageStyle.UNDECORATED);
			Dashboard.setScene(new Scene(root, 600, 450));
			Dashboard.show();
			Dashboard.setResizable(false);
		} catch(Exception e) {
			e.printStackTrace();
			e.getCause();
		}
	}
	
	
	public void XLabelClick(MouseEvent event) {
			Stage stage = (Stage) XLabel.getScene().getWindow();
			stage.close();
	}
	
	
	@FXML
	private void SignIn(ActionEvent event) throws Exception{
		
		if(usernameTextField.getText().isEmpty() == false && passwordTextField.getText().isEmpty() == false) {
			conn = DatabaseConnection.ConnectDB();
			
			String sql = "SELECT count(1) FROM admin_account WHERE username = '" + usernameTextField.getText() + "' AND password = '" + passwordTextField.getText() + "'";
			
			try {
				
				Statement statement = conn.createStatement();
				ResultSet queryResult = statement.executeQuery(sql);
				
				while(queryResult.next()) {
					
					if(queryResult.getInt(1)==1) {
						loginMessageLabel.setTextFill(Color.web("#000679"));
						loginMessageLabel.setText("Connected!");
						System.out.println("You're connected");
						
						Stage stage = (Stage) XLabel.getScene().getWindow();
						stage.close();


						Parent root = FXMLLoader.load(getClass().getResource("adminDashboard.fxml"));
						Stage dashboardScene = new Stage();
						dashboardScene.initStyle(StageStyle.UNDECORATED);
						dashboardScene.setScene(new Scene(root, 600, 400));
						dashboardScene.show();
						dashboardScene.setResizable(false);
						
						
					}else {
						loginMessageLabel.setTextFill(Color.web("#ff0000"));
						loginMessageLabel.setText("Invalid SignIn");
						System.out.println("Invalid Sign in");
					}
					
				}
				
			}catch(Exception e) {
				System.out.println("Exception in LogIn Controller " + e);
			}
		}
		else {
			loginMessageLabel.setTextFill(Color.web("#ff0000"));
			loginMessageLabel.setText("Please type your username and password.");
		}
	
	}
	
	
	
	
	
}
