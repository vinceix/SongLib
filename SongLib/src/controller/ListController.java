//Edmond Wu & Vincent Xie

package controller;

import java.io.*;
import java.net.URL;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Song;
import model.SongList;
import view.SongInputDialog;

public class ListController implements Initializable {
	@FXML         
	ListView<String> listView; 
	@FXML
	private ImageView imageView;
	@FXML
	private Text names;
	@FXML
	private Text artists;
	@FXML
	private Text albums;
	@FXML
	private Text years;

	private ObservableList<String> obsList;     
	private SongList songList;

	public void start(Stage mainStage) {                
		// create an ObservableList 
		// from an ArrayList          
		songList = new SongList();
		songList.getSongsFromFile("song-list.csv");
		obsList = FXCollections.observableArrayList();
		for (int i = 0; i < songList.getSongList().size(); i++) {
			obsList.add(i, songList.getSongList().get(i).getName());
		}
		listView.setItems(obsList); 
		listView.getSelectionModel().select(0);
		showInfo();
		listView.setOnMouseClicked((e) -> showInfo());
	}

	public void addSong(ActionEvent e) throws FileNotFoundException, UnsupportedEncodingException {
		SongInputDialog dlg = new SongInputDialog("Add Song");
		dlg.setHeaderText("Song must have at least name and artist");
		dlg.setContentText("Song name: ");
		
		Optional<ButtonType> result = dlg.showAndWait();
		
		String ok = ButtonType.OK.getText();
		String click = result.get().getText();
		
		if (click.equals(ok)) {
			String song = dlg.getSongText(), artist = dlg.getArtistText(), album = dlg.getAlbumText(), year = dlg.getYearText();
			Song newSong = new Song(song, album, artist, year);
			if (song.isEmpty() || artist.isEmpty()) {
				Alert error = new Alert(AlertType.INFORMATION);
				error.setHeaderText("Error!");
				error.setContentText("Either artist or song name is missing!");
				error.show();
				return;
			}
			else if (!year.isEmpty()) {
				try {
					int intyear = Integer.parseInt(year);
					if(intyear < 0){
						Alert error = new Alert(AlertType.INFORMATION);
						error.setHeaderText("Error!");
						error.setContentText("Year is invalid!");
						error.show();
						return;
					}
				} catch (Exception ex) {
					Alert error = new Alert(AlertType.INFORMATION);
					error.setHeaderText("Error!");
					error.setContentText("Year is invalid!");
					error.show();
					return;
				}
			}
			int index = songList.addSongToList(newSong);
			if(index < 0){
				Alert error = new Alert(AlertType.INFORMATION);
				error.setHeaderText("Error!");
				error.setContentText("Song with same name and artist already in list!");
				error.show();
				return;
			}
			obsList.add(index, song);
			listView.getSelectionModel().select(index);
			showInfo();
		}
		songList.writeFile(songList.getSongList());
	}

	public void removeSong(ActionEvent e) throws FileNotFoundException, UnsupportedEncodingException{
		int index = listView.getSelectionModel().getSelectedIndex();
		if(index >= 0){
			obsList.remove(index);
			songList.deleteSongFromList(index);
		}
		showInfo();
		songList.writeFile(songList.getSongList());
	}
	
	public void editSong(ActionEvent e) throws FileNotFoundException, UnsupportedEncodingException {
		int index = listView.getSelectionModel().getSelectedIndex();
		if (index < 0) {
			Alert error = new Alert(AlertType.INFORMATION);
			error.setHeaderText("Error!");
			error.setContentText("No song selected!");
			error.show();
			return;
		}
		SongInputDialog dlg = new SongInputDialog("Edit Song");
		dlg.setContentText("Edit song info: ");
		Song selected = songList.getSongFromListIndex(index);
		
		dlg.setSongText(selected.getName());
		dlg.setArtistText(selected.getArtist());
		dlg.setAlbumText(selected.getAlbum());
		dlg.setYearText(selected.getYear());
		Optional<ButtonType> result = dlg.showAndWait();
		
		String ok = ButtonType.OK.getText();
		String click = result.get().getText();
		
		if (click.equals(ok)) {
			Song newSong = new Song(dlg.getSongText(), dlg.getAlbumText(), dlg.getArtistText(), dlg.getYearText());
			int newIndex = songList.getSongIndex(newSong);
			if (newIndex >= 0) {
				Alert error = new Alert(AlertType.INFORMATION);
				error.setHeaderText("Error!");
				error.setContentText("Song with same name and artist already in list!");
				error.show();
				return;
			}
			else {
				obsList.remove(index);
				songList.deleteSongFromList(index);
				newIndex = songList.addSongToList(newSong);
				obsList.add(newIndex, newSong.getName());
				listView.getSelectionModel().select(newIndex);
				showInfo();
			}
		}
		songList.writeFile(songList.getSongList());
	}
	
	private void showInfo() {                
		int index = listView.getSelectionModel().getSelectedIndex();
		if(index >= 0){
			names.setText("Name: " + songList.getSongFromListIndex(index).getName());
			artists.setText("Artist: " + songList.getSongFromListIndex(index).getArtist());
			albums.setText("Album: " + songList.getSongFromListIndex(index).getAlbum());
			years.setText("Year: " + songList.getSongFromListIndex(index).getYear());
		} else {
			names.setText("Name: ");
			artists.setText("Artist: ");
			albums.setText("Album: ");
			years.setText("Year: ");
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		File file = new File("src/album.png");
		Image image = new Image(file.toURI().toString());
		imageView.setImage(image);
	}
}
