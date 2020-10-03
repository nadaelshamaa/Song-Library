/*
 * NADA ELSHAMAA -> NHE12
 * AAMNA FAROOQ -> AF704
*/

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
import java.util.Comparator;
import java.util.Optional;
import javafx.scene.control.Label;
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

	@FXML Label DSong;
	@FXML Label DArtist;
	@FXML Label DAlbum;
	@FXML Label DYear;

	@FXML TextField ESong;
	@FXML TextField EArtist;
	@FXML TextField EAlbum;
	@FXML TextField EYear;

	@FXML ListView<SongDetail> listView;
	private ObservableList<SongDetail> obsList;

	public ArrayList <SongDetail> songDetails = new ArrayList<SongDetail>();

	public void start(Stage mainStage) throws IOException {
		createData();

		obsList = FXCollections.observableArrayList(songDetails);		
		obsList.sort((a,b) -> a.getName().compareToIgnoreCase(b.getName())==0 ? a.getArtist().compareToIgnoreCase(b.getArtist()) : a.getName().compareToIgnoreCase(b.getName()));
		listView.setItems(obsList);
		
		// select the first item
		if (!obsList.isEmpty()) {
			listView.getSelectionModel().select(0);
			//Update the details diplay bar
			updateSelection(listView.getSelectionModel().getSelectedItem());

		}

		 //set listener for the items
		listView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> showItem(mainStage));
	}

	public void add() throws IOException {
		//Makes sure user has at least entered Song and Artist name
		if((ASong.getText().equals("")||ASong.getText().isEmpty()) ||  (AArtist.getText().equals("")||AArtist.getText().isEmpty())) {
			Alert alert2 = new Alert(AlertType.ERROR);
			alert2.setTitle("Error!");
			alert2.setContentText("You must at least enter a song and artist name");
			alert2.showAndWait();
			
			return;
		}
		
		//Makes sure the user enter a number for Year
		if (!AYear.getText().equals("") && !AYear.getText().isEmpty()) {
			try {
			    Integer.parseInt(AYear.getText());
			} catch(Exception e) {
				Alert alert3 = new Alert(AlertType.ERROR);
				alert3.setTitle("Error!");
				alert3.setContentText("Year must be a number");
				alert3.showAndWait();
				
				AYear.setText("");
				return;
			}
		}
		
		//Confirmation to add song
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm");
		alert.setHeaderText("You have chosen to add a song to your playlist");
		alert.setContentText("Are you sure?");
		Optional<ButtonType> res = alert.showAndWait();
		
		String album=AAlbum.getText();
		String year=AYear.getText();
		if (res.get() == ButtonType.OK) {
			if (AAlbum.getText().isBlank()) {
				album="no-entry";
			}
			
			if (AYear.getText().isBlank()) {
				year="no-entry";
			}
			
			//Checking for duplicates
			SongDetail song = new SongDetail(ASong.getText(), AArtist.getText(), album, year);
			for (int i=0; i<songDetails.size(); i++) {
				if (songDetails.get(i).getName().equalsIgnoreCase(ASong.getText()) && songDetails.get(i).getArtist().equalsIgnoreCase(AArtist.getText())) {
					Alert alert1 = new Alert(AlertType.ERROR);
					alert1.setTitle("Error!");
					alert1.setContentText("The song you are trying to add already exists");
					alert1.showAndWait();
					
					ASong.setText("");
					AArtist.setText("");
					AAlbum.setText("");
					AYear.setText("");
					return;
				}
			}

			//Adding the song
			songDetails.add(song);
			obsList.add(song);

			//Sorting the observable list
			obsList.sort((a,b) -> a.getName().compareToIgnoreCase(b.getName())==0 ? a.getArtist().compareToIgnoreCase(b.getArtist()) : a.getName().compareToIgnoreCase(b.getName()));
			listView.getSelectionModel().select(obsList.indexOf(song));

			//Reseting text fields
			ASong.setText("");
			AArtist.setText("");
			AAlbum.setText("");
			AYear.setText("");

			//Changing the details in the display bar
			updateSelection(song);

			//Updating the file
			updateFile();			
		}
		
		//Reset text fields if user selects cancel
		ASong.setText("");
		AArtist.setText("");
		AAlbum.setText("");
		AYear.setText("");
	}
	
	public void delete() throws IOException {
		//Checking if list is empty
		if (obsList.isEmpty()) {
			Alert alert2 = new Alert(AlertType.ERROR);
			alert2.setTitle("Error!");
			alert2.setContentText("The are no songs in your playlist");
			alert2.showAndWait();
			return;
		}
		
		//Confirmation message
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm");
		alert.setHeaderText("You have chosen to delete a song from your playlist");
		alert.setContentText("Are you sure?");
		Optional<ButtonType> res = alert.showAndWait();
		if (res.get() == ButtonType.OK) {
			//Finding song in library
			int i=0;
			int index=0;
			for (i=0; i<songDetails.size(); i++) {
				if (songDetails.get(i).getName().equalsIgnoreCase(DSong.getText()) && songDetails.get(i).getArtist().equalsIgnoreCase(DArtist.getText()) && songDetails.get(i).getAlbum().equalsIgnoreCase(DAlbum.getText()) && songDetails.get(i).getYear().equalsIgnoreCase(DYear.getText())) {
					//Removing the song
					index=obsList.indexOf(songDetails.get(i));
					obsList.remove(songDetails.get(i));
					songDetails.remove(songDetails.get(i));
					break;
				}
			}
			
			if (obsList.isEmpty()) {
				//Reseting the text fields
				DSong.setText("");
				DArtist.setText("");
				DAlbum.setText("");
				DYear.setText("");
				
				//Update the file
				updateFile();
				return;
			}
			
			//Change details displayed and selection
			
			if (index==obsList.size()) {
				System.out.print("Last element"+'\t');
				System.out.println("i-> "+index);
				updateSelection(songDetails.get(index-1));
				listView.getSelectionModel().select(obsList.indexOf(songDetails.get(index-1)));
			}
			else {
				System.out.print("Other"+'\t');
				System.out.println("i-> "+index);
				updateSelection(songDetails.get(index));
				listView.getSelectionModel().select(obsList.indexOf(songDetails.get(index)));
			}
			
			//Update the file
			updateFile();
		}
	}

	public void edit() throws IOException {
		//Checking if list is empty
		if (obsList.isEmpty()) {
			Alert alert4 = new Alert(AlertType.ERROR);
			alert4.setTitle("Error!");
			alert4.setContentText("You cannot edit an empty playlist");
			alert4.showAndWait();
			
			//Reseting the text fields
			ESong.setText("");
			EArtist.setText("");
			EAlbum.setText("");
			EYear.setText("");
			return;
		}
		
		//Making sure user has entered at least a Song and Artist name
		if((ESong.getText().equals("")||ESong.getText().isEmpty()) ||  (EArtist.getText().equals("")||EArtist.getText().isEmpty())) {
			Alert alert2 = new Alert(AlertType.ERROR);
			alert2.setTitle("Error!");
			alert2.setContentText("You must at least enter a song name and artist");
			alert2.showAndWait();
			
			//Reseting the text fields
			ESong.setText("");
			EArtist.setText("");
			EAlbum.setText("");
			EYear.setText("");
			return;
		}
		
		//Making sure that user entered a number for Year
		if (!EYear.getText().equals("") && !EYear.getText().isEmpty()) {
			try {
			    Integer.parseInt(EYear.getText());
			} catch(Exception e) {
				Alert alert3 = new Alert(AlertType.ERROR);
				alert3.setTitle("Error!");
				alert3.setContentText("Year must be a number");
				alert3.showAndWait();
				
				
				//Reseting the text fields
				ESong.setText("");
				EArtist.setText("");
				EAlbum.setText("");
				EYear.setText("");
				return;
			}
		}
		
		//Confirmation Message
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm");
		alert.setHeaderText("You have chosen to edit a song in your playlist");
		alert.setContentText("Are you sure?");
		Optional<ButtonType> res = alert.showAndWait();
		if (res.get() == ButtonType.OK) {
			int i=0;
			//Checking if the for duplicates
			for (i=0; i<songDetails.size(); i++) {
				if (songDetails.get(i).getName().equalsIgnoreCase(ESong.getText()) && songDetails.get(i).getArtist().equalsIgnoreCase(EArtist.getText())) {
					Alert alert1 = new Alert(AlertType.ERROR);
					alert1.setTitle("Error!");
					alert1.setContentText("Song already exists");
					alert1.showAndWait();
					
					//Reseting the text fields
					ESong.setText("");
					EArtist.setText("");
					EAlbum.setText("");
					EYear.setText("");
					return;
				}
			}
			
			//Finding the song that user wants to edit
			for (i=0; i<songDetails.size(); i++) {
				if (songDetails.get(i).getName().equalsIgnoreCase(DSong.getText()) && songDetails.get(i).getArtist().equalsIgnoreCase(DArtist.getText()) && songDetails.get(i).getAlbum().equalsIgnoreCase(DAlbum.getText()) && songDetails.get(i).getYear().equalsIgnoreCase(DYear.getText())) {
					//Editing the song
					String album = EAlbum.getText();
					if (album.isBlank()) {
						album="no-entry";
					}
					String year = EYear.getText();
					if (year.isBlank()) {
						year="no-entry";
					}
					songDetails.get(i).setName(ESong.getText());
					songDetails.get(i).setArtist(EArtist.getText());
					songDetails.get(i).setAlbum(album);
					songDetails.get(i).setYear(year);
					obsList.set(i, new SongDetail(ESong.getText(), EArtist.getText(), album, year));
					
					//Sort the observable list
					obsList.sort((a,b) -> a.getName().compareToIgnoreCase(b.getName())==0 ? a.getArtist().compareToIgnoreCase(b.getArtist()) : a.getName().compareToIgnoreCase(b.getName()));
					break;
				}
			}
			
			//Reseting the text fields
			ESong.setText("");
			EArtist.setText("");
			EAlbum.setText("");
			EYear.setText("");
			
			//Change the displayed details and selection
			updateSelection(songDetails.get(i));
			listView.getSelectionModel().select(obsList.indexOf(songDetails.get(i)));
			
			//Update the file
			updateFile();
		}
		
		//Reseting the text fields if the user selects cancel
		ESong.setText("");
		EArtist.setText("");
		EAlbum.setText("");
		EYear.setText("");
	}

	public void updateSelection(SongDetail song) {
		DSong.setText(song.getName());
		DArtist.setText(song.getArtist());
		if (song.getAlbum().isEmpty()) {
			DAlbum.setText("");
		}
		else {
			DAlbum.setText(song.getAlbum());
		}
		if (song.getYear().isEmpty()) {
			DYear.setText("");
		}
		else {
			DYear.setText(song.getYear());
		}
		
	}

	public void updateFile() throws IOException {
		FileWriter f = new FileWriter("songs.txt");
		for (int i=0; i<songDetails.size(); i++) {
			String s = songDetails.get(i).getName() + '\t' + songDetails.get(i).getArtist() + '\t' + songDetails.get(i).getAlbum() + '\t' + songDetails.get(i).getYear()+'\n'; 
			f.write(s); 
		}
		f.close();
	}
	
	public void showItem(Stage mainStage) {
		SongDetail item = listView.getSelectionModel().getSelectedItem();
		if (item==null) {
			return;
		}
		
		DSong.setText(item.getName());
		DArtist.setText(item.getArtist());
		DAlbum.setText(item.getAlbum());
		DYear.setText(item.getYear());
	}

	public void createData() throws IOException{
		File f = new File("songs.txt");
		Scanner readSongs = new Scanner(f).useDelimiter(",\\s*");

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

