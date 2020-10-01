package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Optional;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class eventhandler {

	@FXML Button Add;
	@FXML Button Delete;
	@FXML Button Edit;
	
	@FXML TextField ASong;
	@FXML TextField AArtist;
	@FXML TextField AAlbum;
	@FXML TextField AYear;
	@FXML TextField DSong;
	@FXML TextField DArtist;
	@FXML TextField DAlbum;
	@FXML TextField DYear;
	
	@FXML ListView<SongDetail> listView;
	private ObservableList<SongDetail> obsList;
	
	public ArrayList <SongDetail> songDetails = new ArrayList<SongDetail>();
	
	public void start(Stage mainStage) throws IOException {
		System.out.println("Starting creation");
		createData();
		obsList = FXCollections.observableList(songDetails);
		listView.setItems(obsList);
		listView.getSelectionModel().getSelectedItems();
		
	}
	
	public void add() {
		//check for duplicates
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm");
		alert.setHeaderText("You have chosen to add a song to your playlist");
		alert.setContentText("Are you sure?");
		Optional<ButtonType> res = alert.showAndWait();
		if (res.get() == ButtonType.OK) {
			SongDetail song = new SongDetail(ASong.getText(), AArtist.getText(), AAlbum.getText(), AYear.getText());
			for (int i=0; i<songDetails.size(); i++) {
				if (songDetails.get(i).getName()==ASong.getText() && songDetails.get(i).getArtist()==AArtist.getText()) {
					Alert alert1 = new Alert(AlertType.ERROR);
					alert.setTitle("Error!");
					alert.setContentText("The song you are trying to add already exists");
					alert.showAndWait();
					return;
				}
			}
		
		//Add it
		songDetails.add(song);
		//Change the selection
		//Sort the list and display
		//Update the file
		}
	}
	
	public void delete() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm");
		alert.setHeaderText("You have chosen to delete a song from your playlist");
		alert.setContentText("Are you sure?");
		Optional<ButtonType> res = alert.showAndWait();
		if (res.get() == ButtonType.OK) {
			//Delete the selected song
			SongDetail song = new SongDetail(DSong.getText(), DArtist.getText(), DAlbum.getText(), DYear.getText());
			songDetails.remove(song);
			//Change selection
			//Sort the list and update display
			//Update the file
		}
	}
	
	public void edit() {
		
	}
	
	public void updateFile() throws IOException {
		FileWriter f = new FileWriter("songs.txt");
		for (int i=0; i<songDetails.size(); i++) {
			String s = songDetails.get(i).getName() + '\t' + songDetails.get(i).getArtist() + '\t' + songDetails.get(i).getAlbum() + '\t' + songDetails.get(i).getYear()+'\n'; 
			f.write(s); 
		}
		f.close();
	}
	
	public void createData() throws IOException{
		Scanner readSongs = new Scanner(new File("songs.txt")).useDelimiter(",\\s*");

		String readFile ="";
		String []readLine;

		while (readSongs.hasNext()) {
			// find next line
			readFile = readSongs.next();
			readLine=readFile.split("\\n");

			for (int i=0;i<readLine.length;i++){
				String[] readElement = readLine[i].split("\\s+");

				SongDetail songDetail = new SongDetail(readElement[0], readElement[1], readElement[2], readElement[3]);  
				songDetails.add(songDetail); 
			}
		}
		readSongs.close();
	}
	
	
}
