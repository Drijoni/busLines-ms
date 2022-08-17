package application;



import java.sql.ResultSet;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AdminDashboardController {
		
	@FXML
	private Label XLabel;
	
	@FXML
	private Label OpenEditAdmin;
	
	@FXML
	private Label goBack;
	
	
	@FXML
	private Label openBusLineDashboard;
	
	@FXML
	private Label openAddAdmin;
	
	public void XLabelClick(MouseEvent event) {
		Stage stage = (Stage) XLabel.getScene().getWindow();
		stage.close();
}
	
	
	public void goBack(MouseEvent event) {
		try {
			Stage stage = (Stage) goBack.getScene().getWindow();
			stage.close();
			Parent root = FXMLLoader.load(getClass().getResource("welcome.fxml"));
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
	
	
	public void OpenAddAdmin(MouseEvent event) {
		try {
			Stage stage = (Stage) openAddAdmin.getScene().getWindow();
			stage.close();
			Parent root = FXMLLoader.load(getClass().getResource("addAdmin.fxml"));
			Stage addAdmin = new Stage();
			addAdmin.initStyle(StageStyle.UNDECORATED);
			addAdmin.setScene(new Scene(root, 420, 400));
			addAdmin.show();
			addAdmin.setResizable(false);
		} catch(Exception e) {
			e.printStackTrace();
			e.getCause();
		}
	}
	
	
	public void OpenEditAdmin(MouseEvent event) {
		try {
			Stage stage = (Stage) OpenEditAdmin.getScene().getWindow();
			stage.close();
			Parent root = FXMLLoader.load(getClass().getResource("updateAdmin.fxml"));
			Stage updateAdmin = new Stage();
			updateAdmin.initStyle(StageStyle.UNDECORATED);
			updateAdmin.setScene(new Scene(root, 600, 450));
			updateAdmin.show();
			updateAdmin.setResizable(false);
		} catch(Exception e) {
			e.printStackTrace();
			e.getCause();
		}
	}


	public void OpenEditLines(MouseEvent event) {
		try {
			Stage stage = (Stage) openBusLineDashboard.getScene().getWindow();
			stage.close();
			Parent root = FXMLLoader.load(getClass().getResource("busLinesDashboard.fxml"));
			Stage updateLines = new Stage();
			updateLines.initStyle(StageStyle.UNDECORATED);
			updateLines.setScene(new Scene(root, 600, 445));
			updateLines.show();
			updateLines.setResizable(false);
		} catch(Exception e) {
			e.printStackTrace();
			e.getCause();
		}
	}
		
	
	
	}
	
