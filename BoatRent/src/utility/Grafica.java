/**
 * @author AlessandroBevilacqua
 * @version 1.0
 */

package utility;
import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Classe utilizzata per accedere a vari metodi statici utili per la visualizzazione di alcuni elementi grafici.
 * @author AlessandroBevilacqua.
 */
public class Grafica 
{

	public Grafica() 
	{
		
	}
	
	/**
	 * Mostra all'utente una schermata di avvenuto inserimento (SuccessoScene.fxml).
	 */
	public static void mostraSuccessoInserimento()
	{
		try 
		{
			FXMLLoader fxmlLoader = new FXMLLoader(Grafica.class.getResource("..\\application\\SuccessoScene.fxml"));
			Parent root2 = (Parent) fxmlLoader.load();
			Stage stage1 = new Stage();
			stage1.setScene(new Scene(root2));
			Image image = new Image(Main.class.getResourceAsStream("/conferm.png"));
			stage1.getIcons().add(image);
			stage1.setTitle("Info");
			stage1.setResizable(false);
			stage1.show();
		} catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Mostra all'utente una schermata di errore (InsuccessoScene.fxml).
	 */
	public static void mostraInsuccessoInserimento()
	{
		try 
		{
			FXMLLoader fxmlLoader = new FXMLLoader(Grafica.class.getResource("..\\application\\InsuccessoScene.fxml"));
			Parent root2 = (Parent) fxmlLoader.load();
			Stage stage1 = new Stage();
			stage1.setScene(new Scene(root2));
			Image image = new Image(Main.class.getResourceAsStream("/error.png"));
			stage1.getIcons().add(image);
			stage1.setTitle("Errore!");
			stage1.setResizable(false);
			stage1.show();
		} catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Mostra all'utente una schermata che notifica l'avvenuta corretta generazione di un file (SuccessoFile.fxml).
	 */
	public static void mostraGenerazioneFile()
	{
		try 
		{
			FXMLLoader fxmlLoader = new FXMLLoader(Grafica.class.getResource("SuccessoFile.fxml"));
			Parent root2 = (Parent) fxmlLoader.load();
			Stage stage1 = new Stage();
			stage1.setScene(new Scene(root2));
			Image image = new Image(Main.class.getResourceAsStream("/file.png"));
			stage1.getIcons().add(image);
			stage1.setTitle("ok!");
			stage1.setResizable(false);
			stage1.show();
		} catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Mostra all'utente una schermata di avvenuto corretto inserimento di un noleggio al database (NoleggioAggiuntoScene.fxml).
	 */
	public static void mostraSuccessoNoleggio()
	{
		try 
		{
			FXMLLoader fxmlLoader = new FXMLLoader(Grafica.class.getResource("NoleggioAggiuntoScene.fxml"));
			Parent root2 = (Parent) fxmlLoader.load();
			Stage stage1 = new Stage();
			stage1.setScene(new Scene(root2));
			Image image = new Image(Main.class.getResourceAsStream("/rent.png"));
			stage1.getIcons().add(image);
			stage1.setTitle("OK!");
			stage1.setResizable(false);
			stage1.show();
		} catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Mostra all'utente una schermata che lo avvisa di non poter aggiungere ulteriori noleggi (SuperamentoLimiteNoleggioScene.fxml).
	 */
	public static void mostraSuperamentoLimiteNoleggio()
	{
		try 
		{
			FXMLLoader fxmlLoader = new FXMLLoader(Grafica.class.getResource("SuperamentoLimiteNoleggioScene.fxml"));
			Parent root2 = (Parent) fxmlLoader.load();
			Stage stage1 = new Stage();
			stage1.setScene(new Scene(root2));
			Image image = new Image(Main.class.getResourceAsStream("/warning.png"));
			stage1.getIcons().add(image);
			stage1.setTitle("Attenzione!");
			stage1.setResizable(false);
			stage1.show();
		} catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Mostra all'utente una schermata che lo avvisa di non poter creare un noleggio in quanto non sono presenti delle barche nel database (MancanzaBarcheScene.fxml).
	 */
	public static void mostraMancanzaBarche()
	{
		try 
		{
			FXMLLoader fxmlLoader = new FXMLLoader(Grafica.class.getResource("MancanzaBarcheScene.fxml"));
			Parent root2 = (Parent) fxmlLoader.load();
			Stage stage1 = new Stage();
			stage1.setScene(new Scene(root2));
			Image image = new Image(Main.class.getResourceAsStream("/warning.png"));
			stage1.getIcons().add(image);
			stage1.setTitle("Attenzione!");
			stage1.setResizable(false);
			stage1.show();
		} catch(Exception e) 
		{
			e.printStackTrace();
		}	
	}
}
