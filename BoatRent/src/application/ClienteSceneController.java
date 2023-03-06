/**
 * @author AlessandroBevilacqua.
 * @version 1.0.
 */
package application;
import java.net.URL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import utility.Grafica;
import utility.SaveAboutNoleggio;

/**
 * Controller di ClienteScene.fxml.
 * Questa schermata permette all'utente di inserire un nuovo noleggio.
 * @author AlessandroBevilacqua.
 */
public class ClienteSceneController implements Initializable 
{
	/**
	 * Button per tornare alla schermata precedente ovvero MainScene.fxml.
	 */
	@FXML
	private Button indieroFromClienteToMain;
	/**
	 * Button per andare alla schermata successiva ovvero InserisciCliente.fxml.
	 */
	@FXML
	private Button avantiInserimentoCliente;
	/**
	 * CombBox contenente i codici fiscali dei clienti memorizzati nel database.
	 */
	@FXML
	private ComboBox<String> codiceFiscaleCliente;
	/**
	 * Button che permette di aprire la schermata di inserimento barche (InserisciBarca.fxml).
	 */
	@FXML
	private Button inserisciBarca;
	/**
	 * DatePicker per la selezione della data di inizio noleggio.
	 */
	@FXML
	private DatePicker dataInizioNoleggio;
	/**
	 * DatePicker per la selezione della data di fine noleggio.
	 */
	@FXML
	private DatePicker dataFineNoleggio;
	/**
	 * TextField che permette all'utente l'inserimento del costo del noleggio.
	 */
	@FXML
	private TextField costoNoleggio;
	/**
	 * TextField che permette all'utente l'inserimento del costo della penale del noleggio.
	 */
	@FXML
	private TextField penaleNoleggio;
	/**
	 * CheckBox che permette all'utente di selezionare o meno la presenza di uno skipper a bordo.
	 */
	@FXML
	private CheckBox skipperNoleggio;
		
			
	
	/**
	 * Si attiva con il Button indietroFromClienteToMain. Serve per accedere alla schermata precedente ovvero MainScene.fxml.
	 */
	@FXML
	public void accediMain()
	{
		Main.setTitoloScena("Noleggio");
		Main.changeScene("MainScene.fxml");
	}
	
	/**
	 * Si attiva con il Button avantiInserimentoCliente. Serve per accedere alla schermata InserisciCliente.fxml.
	 */
	@FXML
	public void accediInserisciCliente()
	{
		Main.setTitoloScena("InserisciCliente");
		Main.changeScene("InserisciCliente.fxml");
	}


	
	/**
	 * Riempie la TextField 'codiceFiscaleCliente' con i codici fiscali memorizzati all'interno del database.
	 * In particolare la riempie esclusivamente con i codici fiscali dei clienti che non hanno superato il limite massimo di barche noleggiabili nel giorno specificato.
	 * Per questo motivo prima della selezione del codice fiscale è necessario specificare la data di inizio noleggio.
	 */
	private void riempiCodiceFiscale()
	{
		String query = "select CodiceFiscale from cliente where codiceFiscale not in (" 
				+ "select CodiceFiscale from cliente join noleggio on cliente.codiceFiscale=noleggio.cfCliente where noleggio.dataInizio='" + this.dataInizioNoleggio.getValue() +"' group by cliente.codiceFiscale having count(*)>=cliente.numMaxBarche);";
		codiceFiscaleCliente.getItems().clear();
		PreparedStatement p = null;
		try 
		{
			p = Main.c.prepareStatement(query);
			ResultSet r = p.executeQuery();
			while(r.next())
			{
				codiceFiscaleCliente.getItems().add(r.getString(1));
			}
			p.close();
		} catch (SQLException e) 
		{
			e.printStackTrace();
			try 
			{
				p.close();
			} catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Si attiva con il DataPicker dataInizioNoleggio e chiama il metodo riempiCodiceFiscale().
	 * In questo modo ogniqualvolta viene modificata la data viene anche aggiornata la lista dei clienti che possono effettuare un nuovo noleggio.
	 */
	@FXML
	public void accediDataInizioNoleggio()
	{
		riempiCodiceFiscale();
	}

	/**
	 * Imposta la visualizzazione grafica iniziale.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		this.codiceFiscaleCliente.getItems().add("Inserire prima la data");
		this.codiceFiscaleCliente.getSelectionModel().select(0);
		this.codiceFiscaleCliente.setEditable(false);
	}
	
	
	/**
	 * Si attiva con il Button inserisciBarca.
	 * Se i campi sono stati avvalorati correttamente e non sono state sollevate eccezioni, salva in saveAboutNoleggio le informazioni acquisite come input dall'utente e apre la schermata InserisciBarca.fxml.
	 */
	@FXML
	public void accediInserisciBarca()
	{
		if(verificaCampi()==false)
		{
			Main.setTitoloScena("Cliente");
			Main.changeScene("ClienteScene.fxml");
			Grafica.mostraInsuccessoInserimento();
			return;
		}
		SaveAboutNoleggio.setCodiceFiscale(this.codiceFiscaleCliente.getValue());
		SaveAboutNoleggio.setCosto(Float.parseFloat(this.costoNoleggio.getText()));
		SaveAboutNoleggio.setSkipper(this.skipperNoleggio.isSelected());
		SaveAboutNoleggio.setDataInizio(this.dataInizioNoleggio.getValue());
		if(this.penaleNoleggio.getText().length()!=0)
		{
			SaveAboutNoleggio.setPenale(Float.parseFloat(this.penaleNoleggio.getText()));
		}
		else
		{
			SaveAboutNoleggio.setPenale(0);
		}
		if(this.dataFineNoleggio.getValue()!=null)
		{
			SaveAboutNoleggio.setDataFine(this.dataFineNoleggio.getValue());
		}
		else
		{
			SaveAboutNoleggio.setDataFine(null);
		}
		Main.setTitoloScena("InserisciBarca");
		Main.changeScene("InserisciBarca.fxml");
		return;
	}
	
	/**
	 * Verifica che i campi siano stati avvalorati correttamente dall'utente.
	 * Nello specifico l'utente deve obbligatoriamente avvalorare i campi relativi
	 * al codice fiscale, al costo, alla penale e alla data di inizio noleggio, mentre quello riguardante la data di fine noleggio risulta opzionale.
	 * @return true se tutti i campi sono stati avvalorati correttamente, false altrimenti.
	 */
	private boolean verificaCampi()
	{
		if(this.codiceFiscaleCliente.getValue()==null)
		{
			System.out.println("Non hai selezionato il codice fiscale!");
			return false;
		}
		try
		{
			 Float.parseFloat(this.costoNoleggio.getText());
		}catch(Exception e)
		{
			System.out.println("Il costo del noleggio non e' corretto!");
			return false;
		}
		if(this.penaleNoleggio.getText().length()!=0)
		{
			try
			{
				Float.parseFloat(this.penaleNoleggio.getText());
				
			}catch(Exception e)
			{
				System.out.println("Il costo della penale non e' corretto!");
				return false;
			}
		}
		
		if(dataInizioNoleggio.getValue()==null)
		{
			System.out.println("Non hai inserito la data di inizio noleggio!");
			return false;
		}
		if(dataFineNoleggio.getValue()!=null)
		{
			if(dataInizioNoleggio.getValue().compareTo(dataFineNoleggio.getValue())>0)
			{
				System.out.println("La data di inizio noleggio non puo' essere maggiore della data di fine noleggio!");
				return false;
			}
		}
		return true;
	}
}
