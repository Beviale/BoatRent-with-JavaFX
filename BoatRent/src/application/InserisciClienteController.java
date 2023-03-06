/**
 * @author AlessandroBevilacqua.
 * @version 1.0.
 */

package application;


import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import noleggio.Cliente;
import utility.Grafica;
/**
 * Controller di InserisciCliente.fxml.
 * Questa schermata permette l'aggiunta di un nuovo cliente al database.
 * @author AlessandroBevilacqua.
 */
public class InserisciClienteController implements Initializable
{
	/**
	 * Button che permette di tornare alla schermata precedente ovvero ClienteScene.fxml.
	 */
	@FXML
	private Button indietroFromInserisciClienteToCliente;
	/**
	 * Button che permette l'inserimento di un nuovo cliente al database sfruttando i dati forniti in input dall'utilizzatore mediante interfaccia grafica.
	 */
	@FXML
	private Button inserisciCliente;
	/**
	 * TextField contenente il numero massimo giornaliero di barche noleggiabili dal cliente da aggiungere. Si tratta di un parametro non modificabile in quanto unico per tutti.
	 */
	@FXML
	private TextField numeroMassimoNoleggi;
	/**
	 * TextField che permette all'utente di scrivere il nome del cliente da aggiungere.
	 */
	@FXML
	private TextField nomeCliente;
	/**
	 * TextField che permette all'utente di scrivere il cognome del cliente da aggiungere.
	 */
	@FXML
	private TextField cognomeCliente;
	/**
	 * TextField che permette all'utente di scrivere il codice fiscale del cliente da aggiungere.
	 */
	@FXML
	private TextField codiceFiscaleCliente;
	/**
	 * DatePicker utile alla selezione della data di nascita del cliente.
	 */
	@FXML
	private DatePicker dataNascitaCliente;
	/**
	 * ChoiceBox utile alla selezione del sesso del cliente.
	 */
	@FXML
	private ChoiceBox<Character> sesso;
	private PreparedStatement p;
	
	
	
	/**
	 * Si attiva con il Button indietroFromInserisciClienteToCliente.
	 * Permette di accedere alla schermata precedente ovvero ClienteScene.fxml.
	 */
	@FXML
	public void accediCliente()
	{
		Main.setTitoloScena("Cliente");
		Main.changeScene("ClienteScene.fxml");
	}
	
	/**
	 * Si attiva con il Button inserisciCliente. 
	 * Se tutti i campi risultano avvalorati correttamente inserisce un nuovo cliente al database.
	 */
	@FXML
	public void accediInserisciCliente()
	{
		if(verificaCampi()==false)
		{
			Main.setTitoloScena("InserisciCliente");
			Main.changeScene("InserisciCliente.fxml");
			Grafica.mostraInsuccessoInserimento();
			return;
		}
		preparaQueryCliente();
		String stringNomeCliente = nomeCliente.getText();
		String stringCognomeCliente = cognomeCliente.getText();
		String stringCodiceFiscaleCliente = codiceFiscaleCliente.getText();
		LocalDate localDateDataNascitaCliente = dataNascitaCliente.getValue();
		String charSesso = sesso.getValue().toString();
		/**
		 * Cliente da aggiungere al database.
		 */
		Cliente nuovo = new Cliente(stringCodiceFiscaleCliente, stringNomeCliente, stringCognomeCliente, localDateDataNascitaCliente, charSesso);
		try
		{
			p.setString(1, nuovo.getCodiceFiscale());
			p.setString(2, nuovo.getNome());
			p.setString(3, nuovo.getCognome());
			Date dateDataNascita = java.sql.Date.valueOf(nuovo.getDataNascita());
			p.setDate(4, (java.sql.Date) dateDataNascita);
			p.setString(5, charSesso);
			p.setInt(6, Cliente.getNumMaxBarche());
			p.execute();
			p.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
			Main.setTitoloScena("InserisciCliente");
			Main.changeScene("InserisciCliente.fxml");
			Grafica.mostraInsuccessoInserimento();
			return;
		}
		System.out.println("Cliente inserito correttamente!" + " CodiceFiscale: " + nuovo.getCodiceFiscale() + ", Nome: " + nuovo.getNome() + ", Cognome: " + nuovo.getCognome() + ", DataNascita: " + nuovo.getDataNascita() +", Sesso: " + nuovo.getSesso() + ", NumMaxBarche: " + Cliente.getNumMaxBarche() + ".");
		Main.setTitoloScena("InserisciCliente");
		Main.changeScene("InserisciCliente.fxml");
		Grafica.mostraSuccessoInserimento();
		return;
	}
	
	
	/**
	 * Inizializza e parametrizza la PreparedStatement utile per l'inserimento del cliente al database.
	 */
	private void preparaQueryCliente()
	{
		/**
		 * Query parametrizzata per l'inserimento del cliente al database.
		 */
		String queryInserimentoCliente = "insert into Cliente values (?, ?, ?, ?, ?, ?);";
		try 
		{
			p = Main.c.prepareStatement(queryInserimentoCliente);
		} catch (SQLException e) 
		{
			Main.changeScene("MainScene.fxml");
			Main.setTitoloScena("Noleggio");
			Grafica.mostraInsuccessoInserimento();
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Verifica che tutti i campi compilati dall'utente mediante interfaccia grafica siano avvalorati e con i dati corretti.
	 * @return true se tutti i campi sono corretti, false altrimenti.
	 */
	private boolean verificaCampi()
	{
		if(nomeCliente.getText().length()<1 || nomeCliente.getText().length()>30)
		{
			System.out.println("nomeCliente nullo oppure troppo lungo (MAX 30 caratteri)!");
			return false;
		}
		if(cognomeCliente.getText().length()<1 || cognomeCliente.getText().length()>30)
		{
			System.out.println("conomeCliente nullo oppure troppo lungo (MAX 30 caratteri)!");
			return false;
		}
		if(dataNascitaCliente.getValue()==null)
		{
			System.out.println("dataNascitaCliente non inserita!");
			return false;
		}
		if(sesso.getValue()==null)
		{
			System.out.println("Sesso non inserito!");
			return false;
		}
		if(dataNascitaCliente.getValue().compareTo(LocalDate.now())>=0)
		{
			System.out.println("dataNascitaCliente non antecedente alla data attuale!");
			return false;
		}
		if(verificaCodiceFiscale()==false)
		{
			return false;
		}
		return true;
	}
	
	
	/**
	 * Verifica che il codice fiscale inserito dall'utente mediante interfaccia grafica per l'inserimento di un nuovo cliente al database sia corretto.
	 * Per corretto s'intende la giusta formattazione (stringa alfanumerica di 16 caratteri) e la non presenza all'interno del database dello stesso in modo tale da evitare duplicati.
	 * @return true se il codice fiscale e' valido, false altrimenti.
	 */
	private boolean verificaCodiceFiscale()
	{
		String stringCodiceFiscaleCliente = codiceFiscaleCliente.getText();
		if(checkFormatoCodiceFiscale(stringCodiceFiscaleCliente)==false)
		{
			System.out.println("codiceFiscale inserito non e' nel formato corretto! (Deve essere una stringa alfanumerica di 16 caratteri tutti scritti in maiuscolo)!");
			return false;
		}
		String getAllCodiciFiscali = "Select codiceFiscale from cliente;";
		/**
		 *  Set contenente tutti i codici fiscali presenti nel database.
		 */
		Set<String> allCodiciFiscali= new HashSet<String>();
		PreparedStatement verificaDuplicato = null;
		try 
		{
			verificaDuplicato = Main.c.prepareStatement(getAllCodiciFiscali);
			ResultSet r = verificaDuplicato.executeQuery();
			while(r.next())
			{
				allCodiciFiscali.add(r.getString(1));
			}
			
		} catch (SQLException e) 
		{
			e.printStackTrace();
			try 
			{
				verificaDuplicato.close();
			} catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
			return false;
		}
		if(allCodiciFiscali.contains(stringCodiceFiscaleCliente))
		{
			System.out.println("Il codice fiscale inserito risulta gia' presente nel database!");
			return false; 
		}
		return true;
	}
	
	
	
	
	/**
	 * Verifica che la stringa passata sia alfanumerica e costituita da esattamente 16 caratteri tutti scritti in maiuscolo.
	 * @param check stringa da verificare.
	 * @return true se la stringa e' valida, false altrimenti.
	 */
	private boolean checkFormatoCodiceFiscale(String check)
	{
		String regex = "[0-9A-Z]{16}";
		return Pattern.matches(regex, check);
	}
	
	/**
	 * Crea un nuovo stage e carica la schermata MostraCliente.fxml.
	 */
	@FXML 
	public void mostraClienti()
	{
		try 
		{
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MostraClientiScene.fxml"));
			Parent root2 = (Parent) fxmlLoader.load();
			Stage stage1 = new Stage();
			stage1.setScene(new Scene(root2));
			Image image = new Image(Main.class.getResourceAsStream("/table.png"));
			stage1.getIcons().add(image);
			stage1.setTitle("Visualizzazione tabella");
			stage1.setResizable(false);
			stage1.show();
		} catch(IOException e) 
		{
			e.printStackTrace();
			Main.setTitoloScena("InserisciCliente");
			Main.changeScene("InserisciCliente.fxml");
		}
	}

	
	/**
	 * Inizializza la schermata iniziale impostando la TextField 'numeroMassimoNoleggi' e la ChoiceBox 'sesso'.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		numeroMassimoNoleggi.setText(Integer.toString(Cliente.getNumMaxBarche()));
		numeroMassimoNoleggi.setEditable(false);
		sesso.getItems().add('M');
		sesso.getItems().add('F');
	}
}
