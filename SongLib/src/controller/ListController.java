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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.SongInputDialog;


public class ListController implements Initializable {
	@FXML         
	   ListView<String> listView; 
	@FXML
       private ImageView imageView;
	@FXML
	   private Text name;

	   private ObservableList<String> obsList;              
	  
	   public void start(Stage mainStage) {                
	      // create an ObservableList 
	      // from an ArrayList              
	      obsList = FXCollections.observableArrayList(                               
	                 "99 Problems",                               
	                 "Ambitionz Az A Ridah",                               
	                 "Blank Space",
	                 "Bitches Ain't Shit");               
	      listView.setItems(obsList); 
	      
	      // select the first item
	      listView.getSelectionModel().select(0);

	      // set listener for the items
	      listView
	      .getSelectionModel()
	      .selectedItemProperty()
	      .addListener(
	    		  (obs, oldVal, newVal) -> 
	    		  showItemInputDialog(mainStage));
	   }
	   
	   public void addSong(ActionEvent e) {
		   SongInputDialog dlg = new SongInputDialog("Add Song");
		   dlg.setContentText("Song name: ");
		   
		   dlg.showAndWait();
		   String song = dlg.getSongText(), artist = dlg.getArtistText(), album = dlg.getAlbumText(), year = dlg.getYearText();
		   
		   if (song.isEmpty() || artist.isEmpty()) {
			   Alert error = new Alert(AlertType.INFORMATION);
			   error.setHeaderText("Error!");
			   error.setContentText("Either artist or song name is missing!");
			   error.show();
		   }
		   else {
			   if (!year.isEmpty()) {
				   try {
					   int intyear = Integer.parseInt(year);
				   } catch (Exception ex) {
					   Alert error = new Alert(AlertType.INFORMATION);
					   error.setHeaderText("Error!");
					   error.setContentText("Year's invalid!");
					   error.show();
				   }
			   }
		   }
		   
		   //Make song and artist required while album and year are optional
		   
		   System.out.println(song + " " + artist + " " + album + " " + year);
		   /*
		   TextInputDialog songname = new TextInputDialog();
		   songname.setHeaderText("Add New Song");
		   songname.setContentText("Enter song name: ");
		   */
		   /*
		   TextInputDialog artistname = new TextInputDialog();
		   artistname.setHeaderText("Add New Artist");
		   artistname.setContentText("Enter artist name: ");
		   */
		   
		   //Optional<String> result = songname.showAndWait();
		   //Optional<String> result2 = artistname.showAndWait();
		   
	   }
	   
	   private void showItem(Stage mainStage) {                
		   Alert alert = new Alert(AlertType.INFORMATION);
		   alert.initOwner(mainStage);
		   alert.setTitle("List Item");
		   alert.setHeaderText(
				   "Selected list item properties");

		   String content = "Index: " + 
				   listView.getSelectionModel()
		   .getSelectedIndex() + 
		   "\nValue: " + 
		   listView.getSelectionModel()
		   .getSelectedItem();

		   alert.setContentText(content);
		   alert.showAndWait();
	   }
	   
	   private void showItemInputDialog(Stage mainStage) {                
		   String item = listView.getSelectionModel().getSelectedItem();
		   int index = listView.getSelectionModel().getSelectedIndex();

		   TextInputDialog dialog = new TextInputDialog(item);
		   dialog.initOwner(mainStage); dialog.setTitle("List Item");
		   dialog.setHeaderText("Selected Item (Index: " + index + ")");
		   dialog.setContentText("Enter name: ");

		   Optional<String> result = dialog.showAndWait();
		   if (result.isPresent()) { obsList.set(index, result.get()); }
	   }

	    @Override
	    public void initialize(URL location, ResourceBundle resources) {
	        File file = new File("src/album.png");
	        Image image = new Image(file.toURI().toString());
	        imageView.setImage(image);
	    }
}
