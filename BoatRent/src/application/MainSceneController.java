/**
 * @author AlessandroBevilacqua.
 * @version 1.0.
 */

package application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Controller della schermata iniziale (MainScene.fxml).
 * Tale schermata permette all'utente di accedere a:
 * 1. GestoreScene.fxml
 * 2. ClienteScene.fxml
 * 3. Info.fxml
 */
public class MainSceneController 
{
	/**
	 * Pane di layout.
	 */
	@FXML
	private Pane Pane;
	/**
	 * Button che permette di accedere a GestoreScene.fxml.
	 */
	@FXML
	private Button buttonGestore;
	/**
	 * Button che permette di accedere a ClienteScene.fxml.
	 */
	@FXML
	private Button buttonCliente;
	/**
	 * Button che permette di accedere a Info.fxml.
	 */
	private Hyperlink linkInfo;
	
	
	
	
	/**
	 * Si attiva quando viene premuto il Button buttonGestore e apre la schermata di GestoreScene.fxml.
	 */
	public void accediGestore()
	{
		Main.setTitoloScena("Gestore");
		Main.changeScene("GestoreScene.fxml");
	}
	
	
	/**
	 * Si attiva quando viene premuto il Button buttonCliente e apre la schermata di ClienteScene.fxml.
	 */
	public void accediCliente()
	{
		Main.setTitoloScena("Cliente");
		Main.changeScene("ClienteScene.fxml");
	}
	
	/**
	 * Si attiva quando viene premuto l'HyperLink linkInfo e apre la schermata di Info.fxml.
	 */
	public void accediInfo() 
	{              
			try 
			{
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Info.fxml"));
				Parent root1 = (Parent) fxmlLoader.load();
				Stage infoStage = new Stage();
				infoStage.setScene(new Scene(root1));
				Image image = new Image(Main.class.getResourceAsStream("/icon.png"));
				infoStage.getIcons().add(image);
				infoStage.setTitle("Info");
				infoStage.setResizable(false);
				infoStage.show();
			} catch(Exception e) 
			{
				e.printStackTrace();
			}
		}
}
