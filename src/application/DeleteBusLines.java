package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DeleteBusLines implements Initializable {

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
	
	
	
	public void XLabelClick(MouseEvent event) {
		Stage stage = (Stage) XLabel.getScene().getWindow();
		stage.close();
}
	
	/* Go back */
	
	public void goBack(MouseEvent event) {
		try {
			Stage stage = (Stage) goBackLabel.getScene().getWindow();
			stage.close();
			Parent root = FXMLLoader.load(getClass().getResource("busLinesDashboard.fxml"));
			Stage busDashboard = new Stage();
			busDashboard.initStyle(StageStyle.UNDECORATED);
			busDashboard.setScene(new Scene(root, 600, 450));
			busDashboard.show();
			busDashboard.setResizable(false);
		} catch(Exception e) {
			e.printStackTrace();
			e.getCause();
		}
	}
	
	
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		showLines();
	}
	
	public ObservableList<Lines> getLines(){
		
		ObservableList<Lines> listofLines = FXCollections.observableArrayList();
		conn = DatabaseConnection.ConnectDB();	
		String query = "SELECT * FROM bus_lines";
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
	
	
	public void deleteLines() {
		conn = DatabaseConnection.ConnectDB();
		Lines selectedLine = busLinesTable.getSelectionModel().getSelectedItem();
		String query = "DELETE FROM bus_lines where id = ?";
		
		try {	
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			
			preparedStmt.setInt(1, selectedLine.getId());
			
			preparedStmt.execute();
			
			showLines();
				
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	
}
