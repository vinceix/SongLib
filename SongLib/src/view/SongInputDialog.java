//Edmond Wu & Vincent Xie

package view;

import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class SongInputDialog extends Dialog<ButtonType> {

    private ButtonType ok = new ButtonType("OK", ButtonData.OK_DONE);
    private ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
    private TextField songText;
    private TextField artistText;
    private TextField albumText;
    private TextField yearText;

    public SongInputDialog(String title) {
        setTitle(title);
        setHeaderText(null);

        GridPane dPane = new GridPane();
        Label song = new Label("Song: ");
        Label artist = new Label("Artist: ");
        Label album = new Label("Album: ");
        Label year = new Label("Year: ");
        songText = new TextField();
        artistText = new TextField();
        albumText = new TextField();
        yearText = new TextField();
        dPane.setHgap(7D);
        dPane.setVgap(8D);

        GridPane.setConstraints(song, 0, 0);
        GridPane.setConstraints(artist, 0, 1);
        GridPane.setConstraints(album, 0, 2);
        GridPane.setConstraints(year, 0, 3);
        GridPane.setConstraints(songText, 1, 0);
        GridPane.setConstraints(artistText, 1, 1);
        GridPane.setConstraints(albumText, 1, 2);
        GridPane.setConstraints(yearText, 1, 3);

        dPane.getChildren().addAll(song, artist, album, year, songText, artistText, albumText, yearText);
        getDialogPane().getButtonTypes().addAll(ok, cancel);
        getDialogPane().setContent(dPane);
    }
    
    public String getSongText() {
    	return songText.getText();
    }
    
    public String getArtistText() {
    	return artistText.getText();
    }
    
    public String getAlbumText() {
    	return albumText.getText();
    }
    
    public String getYearText() {
    	return yearText.getText();
    }
    
    public void setSongText(String name) {
    	songText.setText(name);
    }
    
    public void setArtistText(String name) {
    	artistText.setText(name);
    }
    
    public void setAlbumText(String name) {
    	albumText.setText(name);
    }
    
    public void setYearText(String year) {
    	yearText.setText(year);
    }
}