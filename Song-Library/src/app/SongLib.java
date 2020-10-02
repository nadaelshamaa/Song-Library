//NADA ELSHAMAA -> NHE12
//AAMNA FAROOQ -> AF704
package app;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.eventhandler;

public class SongLib extends Application {

	public void start (Stage primaryStage) throws Exception{
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				getClass().getResource("/view/design.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		eventhandler eh = 
				loader.getController();
		eh.start(primaryStage);

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Song Library");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
