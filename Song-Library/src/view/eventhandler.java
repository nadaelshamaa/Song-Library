//NADA ELSHAMAA -> NHE12
//AAMNA FAROOQ -> AF704

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
		//System.out.println("Starting creation");
		createData();

		obsList = FXCollections.observableArrayList(songDetails);
		Comparator<SongDetail> comparator = Comparator.comparing(SongDetail::getName); 
		FXCollections.sort(obsList, comparator);
		listView.setItems(obsList);
		// select the first item
		listView.getSelectionModel().select(0);
		updateSelection(listView.getSelectionModel().getSelectedItem());


		 //set listener for the items
		listView
		.getSelectionModel()
		.selectedIndexProperty()
		.addListener(
				(obs, oldVal, newVal) -> 
				showItem(mainStage));
		
//		FXCollections.sort(listView, new Comparator<SongDetail>() {
//			@Override
//			public int compare(SongDetail lhs, SongDetail rhs) {
//				return lhs.getName().compareTo(rhs.getName());
//			}
//		});


	}

	public void add() throws IOException {
		if((ASong.getText().equals("")||ASong.getText().isEmpty()) ||  (AArtist.getText().equals("")||AArtist.getText().isEmpty())) {
			Alert alert2 = new Alert(AlertType.ERROR);
			alert2.setTitle("Error!");
			alert2.setContentText("You must at least enter a song name and artist");
			alert2.showAndWait();
			
			ASong.setText("");
			AArtist.setText("");
			AAlbum.setText("");
			AYear.setText("");
			return;
		}
		
		if (!AYear.getText().equals("") && !AYear.getText().isEmpty()) {
			try {
			    Integer.parseInt(AYear.getText());
			} catch(Exception e) {
				Alert alert3 = new Alert(AlertType.ERROR);
				alert3.setTitle("Error!");
				alert3.setContentText("Year must be a number");
				alert3.showAndWait();
				
				ASong.setText("");
				AArtist.setText("");
				AAlbum.setText("");
				AYear.setText("");
				return;
			}
		}
		
		//check for duplicates
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm");
		alert.setHeaderText("You have chosen to add a song to your playlist");
		alert.setContentText("Are you sure?");
		Optional<ButtonType> res = alert.showAndWait();
		if (res.get() == ButtonType.OK) {
			SongDetail song = new SongDetail(ASong.getText(), AArtist.getText(), AAlbum.getText(), AYear.getText());
			for (int i=0; i<songDetails.size(); i++) {
				if (songDetails.get(i).getName().equals(ASong.getText()) && songDetails.get(i).getArtist().equals(AArtist.getText())) {
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

			ASong.setText("");
			AArtist.setText("");
			AAlbum.setText("");
			AYear.setText("");
			
			//Add it
			songDetails.add(song);
			obsList.add(song);
			//Change the selection
			updateSelection(song);
			//Sort the list and display
			//Update the file
			updateFile();			
			
//			Comparator<SongDetail> comparator = Comparator.comparing(SongDetail::getName); 
//			FXCollections.sort(obsList, comparator);
		}
		ASong.setText("");
		AArtist.setText("");
		AAlbum.setText("");
		AYear.setText("");
	}
	
	public void delete() throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm");
		alert.setHeaderText("You have chosen to delete a song from your playlist");
		alert.setContentText("Are you sure?");
		Optional<ButtonType> res = alert.showAndWait();
		if (res.get() == ButtonType.OK) {
			//Delete the selected song
			int i=0;
			for (i=0; i<songDetails.size(); i++) {
				if (songDetails.get(i).getName().equals(DSong.getText()) && songDetails.get(i).getArtist().equals(DArtist.getText()) && songDetails.get(i).getAlbum().equals(DAlbum.getText()) && songDetails.get(i).getYear().equals(DYear.getText())) {
					obsList.remove(songDetails.get(i));
					songDetails.remove(songDetails.get(i));
					break;
				}
			}
			//Change selection
			if (i==songDetails.size()) {
				updateSelection(songDetails.get(i-1));
			}
			else if(i==0) {
				updateSelection(songDetails.get(i+1));
			}
			else {
				updateSelection(songDetails.get(i-1));
			}
			//Update the file
			updateFile();
//			Comparator<SongDetail> comparator = Comparator.comparing(SongDetail::getName); 
//			FXCollections.sort(obsList, comparator);
		}
	}

	public void edit() throws IOException {
		if((ESong.getText().equals("")||ESong.getText().isEmpty()) ||  (EArtist.getText().equals("")||EArtist.getText().isEmpty())) {
			Alert alert2 = new Alert(AlertType.ERROR);
			alert2.setTitle("Error!");
			alert2.setContentText("You must at least enter a song name and artist");
			alert2.showAndWait();
			ESong.setText("");
			EArtist.setText("");
			EAlbum.setText("");
			EYear.setText("");
			return;
		}
		
		if (!EYear.getText().equals("") && !EYear.getText().isEmpty()) {
			try {
			    Integer.parseInt(EYear.getText());
			} catch(Exception e) {
				Alert alert3 = new Alert(AlertType.ERROR);
				alert3.setTitle("Error!");
				alert3.setContentText("Year must be a number");
				alert3.showAndWait();
				
				ESong.setText("");
				EArtist.setText("");
				EAlbum.setText("");
				EYear.setText("");
				return;
			}
		}
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm");
		alert.setHeaderText("You have chosen to edit a song in your playlist");
		alert.setContentText("Are you sure?");
		Optional<ButtonType> res = alert.showAndWait();
		if (res.get() == ButtonType.OK) {
			int i=0;
			for (i=0; i<songDetails.size(); i++) {
				if (songDetails.get(i).getName().equals(ESong.getText()) && songDetails.get(i).getArtist().equals(EArtist.getText())) {
					Alert alert1 = new Alert(AlertType.ERROR);
					alert1.setTitle("Error!");
					alert1.setContentText("Song already exists");
					alert1.showAndWait();
					ESong.setText("");
					EArtist.setText("");
					EAlbum.setText("");
					EYear.setText("");
					return;
				}
			}
			for (i=0; i<songDetails.size(); i++) {
				if (songDetails.get(i).getName().equals(DSong.getText()) && songDetails.get(i).getArtist().equals(DArtist.getText()) && songDetails.get(i).getAlbum().equals(DAlbum.getText()) && songDetails.get(i).getYear().equals(DYear.getText())) {
					songDetails.get(i).setName(ESong.getText());
					songDetails.get(i).setArtist(EArtist.getText());
					songDetails.get(i).setAlbum(EAlbum.getText());
					songDetails.get(i).setYear(EYear.getText());
					obsList.set(i, new SongDetail(ESong.getText(), EArtist.getText(), EAlbum.getText(), EYear.getText()));
					break;
				}
			}
			ESong.setText("");
			EArtist.setText("");
			EAlbum.setText("");
			EYear.setText("");
			//Change selection
			updateSelection(songDetails.get(i));
			//Update the file
			updateFile();
//			Comparator<SongDetail> comparator = Comparator.comparing(SongDetail::getName); 
//			FXCollections.sort(obsList, comparator);
		}
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

				if (readElement.length==2) {
					SongDetail songDetail = new SongDetail(readElement[0], readElement[1], "", "");  
					songDetails.add(songDetail); 
				}
				else if (readElement.length==3) {
					try {
						Integer.parseInt(readElement[2]);
						SongDetail songDetail = new SongDetail(readElement[0], readElement[1], "", readElement[2]);  
						songDetails.add(songDetail); 
					}catch(NumberFormatException e) {
						SongDetail songDetail = new SongDetail(readElement[0], readElement[1], readElement[2], "");  
						songDetails.add(songDetail); 
					}
					
				}
				else {
					SongDetail songDetail = new SongDetail(readElement[0], readElement[1], readElement[2], readElement[3]);  
					songDetails.add(songDetail); 
				}
			}
		}
		readSongs.close();
	}
}

