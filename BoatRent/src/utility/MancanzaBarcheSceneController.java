/**
 * @author AlessandroBevilacqua.
 * @version 1.0.
 */

package utility;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import application.Main;
import javafx.event.ActionEvent;

/**
 * <p>Controller di MancanzaBarcheScene.fxml.</p>
 * <p>Questa schermata avvisa l'utente di non poter creare un noleggio in quanto non sono presenti delle barche nel database.</p>
 * <p>Consente di accedere a GestoreInserimentoScene.fxml in modo da creare delle barche da memorizzare.</p>
 * @author AlessandroBevilacqua.
 */
public class MancanzaBarcheSceneController 
{
	/**
	 * Button che permette di accedere a GestoreInserimentoScene.fxml.
	 */
	@FXML
	private Button inserisciBarche;
	
	/**
	 * Si attiva con il Button inserisciBarche. Permette all'utente di accedere a GestoreInserimentoScene.fxml.
	 * @param event.
	 */
	@FXML
	public void accediInserisciBarche(ActionEvent event) 
	{
		Main.changeScene("GestoreInserimentoScene.fxml");
		Stage stage = (Stage) inserisciBarche.getScene().getWindow();
	    stage.close();
	}
}
