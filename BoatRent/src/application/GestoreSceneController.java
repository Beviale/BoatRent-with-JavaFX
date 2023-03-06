/**
 * @author AlessandroBevilacqua.
 * @version 1.0.
 */
package application;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import utility.Grafica;
import noleggio.*;
/**
 * Controller di GestoreScene.fxml.
 * Tale schermata serve per ottenere gli elenchi ordinati delle barche noleggiate.
 */
public class GestoreSceneController implements Initializable
{
	/**
	 * Button che permette di tornare alla schermata precedente ovvero MainScene.fxml.
	 */
	@FXML
	private Button indieroFromGestoreToMain;
	/**
	 * Button che permette di accedere alla schermata successiva ovvero GestoreInserimentoScene.fxml.
	 */
	@FXML
	private Button nextGestoreInserimento;
	/**
	 * TextField contenente il percorso di salvataggio del file.
	 */
	@FXML
	private TextField percorsoDiSalvataggio;
	/**
	 * ComboBox contenente tutti i codici fiscali di clienti memorizzati nel database.
	 */
	@FXML
	private ComboBox<String> codiceFiscaleStampa;
	/**
	 * ComboBox contenente tutti i codici fiscali di clienti memorizzati nel database.
	 */
	@FXML
	private ComboBox<String> codiceFiscale;
	/**
	 * ComboBox che permette all'utente di selezionare l'intervallo di partenza per la ricerca delle barche noleggiate da un cliente.
	 */
	@FXML
	private DatePicker dataInizio;
	/**
	 *  ComboBox che permette all'utente di selezionare l'intervallo di arrivo per la ricerca delle barche noleggiate da un cliente.
	 */
	@FXML
	private DatePicker dataFine;
	
	
	
	/**
	 * Si attiva con il Button indietroFromGestoreToMain e permette di tornare alla schermata iniziale MainScene.fxml.
	 */
	@FXML
	public void accediToMain()
	{
		Main.setTitoloScena("Noleggio");
		Main.changeScene("MainScene.fxml");
	}
	
	/**
	 * Si attiva con il Button nextGestoreInserimento e permette di acccederea alla schermata di GestoreInserimentoScene.fxml.
	 */
	@FXML
	public void accediGestoreInserimento()
	{
		Main.setTitoloScena("GestoreInserimento");
		Main.changeScene("GestoreInserimentoScene.fxml");
	}
	
	/**
	 * Permette di aprire un dialog attraverso il quale l'utente può decidere il percorso di salvataggio di un file.
	 */
	@FXML
	public void accediSelezionaPercorso()
	{
		DirectoryChooser directoryChooser = new DirectoryChooser();
		Stage dialogStage = new Stage();
		File selectedDirectory = directoryChooser.showDialog(dialogStage);
		if(selectedDirectory!=null)
		{
			this.percorsoDiSalvataggio.setText(selectedDirectory.getAbsolutePath());
		}
     }

	/**
	 * Imposta la visualizzazione grafica iniziale.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		riempiCodiceFiscaleStampa();
		riempiCodiceFiscale();
	}
	
	/**
	 * Se tutti i campi sono stati avvalorati corretamente e non sono state sollevate eccezioni, crea una LinkedList di 
	 * BarcaNoleggio contenente tutte le barche che sono state noleggiate da un cliente e in un intervallo di tempo specificati come input da interfaccia grafica.
	 * Prende i dati necessari dal database.
	 */
	@FXML
	public void ottieniElenco()
	{
		if(verificaCampiElenco()==false)
		{
			Main.setTitoloScena("Gestore");
			Main.changeScene("GestoreScene.fxml");
			Grafica.mostraInsuccessoInserimento();
			return;
		}
		LinkedList<BarcaNoleggio> noleggiOrdinati = new LinkedList<BarcaNoleggio>();
		PreparedStatement inserisci=null;
		String query = "select b.numeroSerie, b.marca, b.modello, b.tipo, n.dataInizio from noleggio n join barca b on b.numeroSerie=n.codiceBarca"
				+ " where n.cfCliente=? and n.dataInizio between ? and ?";
		try
		{
			inserisci = Main.c.prepareStatement(query);
			inserisci.setString(1, this.codiceFiscale.getValue().toString());
			Date dateDataInizio = java.sql.Date.valueOf(this.dataInizio.getValue());
			inserisci.setDate(2, (java.sql.Date) dateDataInizio);
			Date dateDataFine = java.sql.Date.valueOf(this.dataFine.getValue());
			inserisci.setDate(3, (java.sql.Date) dateDataFine);
			ResultSet r = inserisci.executeQuery();
			while(r.next())
			{
				BarcaNoleggio aggiungi = new BarcaNoleggio(r.getString(1), r.getString(2), r.getString(3), TipologiaBarca.valueOf(r.getString(4)), r.getDate(5).toLocalDate());
				noleggiOrdinati.add(aggiungi);
			}
		}catch(SQLException e)
		{
			Main.setTitoloScena("Gestore");
			Main.changeScene("GestoreScene.fxml");
			e.printStackTrace();
			return;
		}
		mostraElencoScene(noleggiOrdinati);
	}
	
	
	/**
	 * Passa una LinkedList di BarcaNoleggio a ElencoSceneController.
	 * @param noleggiOrdinati LinkedList di BarcaNoleggio da passare.
	 */
	private void mostraElencoScene(LinkedList<BarcaNoleggio> noleggiOrdinati)
	{

		FXMLLoader loader = new FXMLLoader(getClass().getResource("ElencoScene.fxml"));
		Stage stageTable = new Stage();
		try 
		{
			Parent root3 = loader.load();
			stageTable.setScene(new Scene(root3));
		} catch (IOException e) 
		{
			Main.setTitoloScena("Gestore");
			Main.changeScene("GestoreScene.fxml");
			e.printStackTrace();
			return;
		}
		ElencoSceneController controller = loader.getController();
		ElencoSceneController.setData(noleggiOrdinati);
		Image image = new Image(Main.class.getResourceAsStream("/table.png"));
		stageTable.getIcons().add(image);
		stageTable.setTitle("Visualizzazione tabella");
		stageTable.setResizable(false);
		stageTable.show();
	}
	
	
	/**
	 * Verifica se tutti i campi utili alla generazione della tabella sono stati avvalorati corretamente dall'utente.
	 * I campi relativi al codice fiscale del cliente e all'intervallo di tempo sono obbligatori.
	 * @return true se tutti i campi sono stati avvalorati correttamente, false altrimenti.
	 */
	private boolean verificaCampiElenco()
	{
		if(this.codiceFiscale.getValue()==null)
		{
			System.out.println("Non hai inserito il codice fiscale!");
			return false;
		}
		if(this.dataInizio.getValue()==null)
		{
			System.out.println("Non hai inserito la data di partenza!");
			return false;
		}
		if(this.dataFine.getValue()==null)
		{
			System.out.println("Non hai inserito la data di arrivo!");
			return false;
		}
		if(this.dataInizio.getValue().compareTo(this.dataFine.getValue())>0)
		{
			System.out.println("La data di partenza e' maggiore di quella di arrivo!");
			return false;
		}
		return true;
	}
	
	
	
	
	/**
	 * Se tutti i campi sono stati avvalorati correttamente e non sono state sollevate eccezioni, crea una LinkedList
	 * di BarcaAllInformazioni contenente le barche noleggiate da un cliente il cui codice fiscale viene fornito in input dall'utente mediante interfaccia grafica.
	 */
	public void stampaElenco()
	{
		if(verificaCampiStampa()==false)
		{
			Main.setTitoloScena("Gestore");
			Main.changeScene("GestoreScene.fxml");
			Grafica.mostraInsuccessoInserimento();
			return;
		}
		LinkedList<BarcaAllInformazioni> noleggiOrdinatiStampa = new LinkedList<BarcaAllInformazioni>();
		PreparedStatement inserisci=null;
		String query = "select b.numeroSerie, b.marca, b.modello, b.tipo, b.codiceBagno1, b.codiceCabina, b.codiceBagno2, n.dataInizio from noleggio n join barca b on b.numeroSerie=n.codiceBarca"
				+ " where n.cfCliente=?";
		try
		{
			inserisci = Main.c.prepareStatement(query);
			inserisci.setString(1, this.codiceFiscaleStampa.getValue().toString());			
			ResultSet r = inserisci.executeQuery();
			while(r.next())
			{
				BarcaAllInformazioni aggiungi = new BarcaAllInformazioni(r.getString(1), r.getString(2), r.getString(3), TipologiaBarca.valueOf(r.getString(4)), r.getInt(5), r.getInt(6), r.getInt(7), r.getDate(8).toLocalDate());
				noleggiOrdinatiStampa.add(aggiungi);
			}
		}catch(SQLException e)
		{
			Main.setTitoloScena("Gestore");
			Main.changeScene("GestoreScene.fxml");
			e.printStackTrace();
			return;
		}
		scriviFile(noleggiOrdinatiStampa);
	}
	
	/**
	 * Prende in input una LinkedList di BarcaAllInformazioni e la ordina in ordine opposto a CompareByDate(), ovvero dalla data di inizio noleggio più recente alla meno recente.
	 * Stampa la LinkedList su un file di testo chiamato 'fileGeneratoBevilacqua.txt'.
	 * Il percorso di salvataggio del file viene prelevato dalla TextField 'percorsoDiSalvataggio'.
	 * In testa al file vine anche stampato un timestamp.
	 * @param noleggiOrdinatiStampa LinkedList di BarcaAllInformazioni da stampare.
	 */
	private void scriviFile(LinkedList<BarcaAllInformazioni> noleggiOrdinatiStampa)
	{
		Collections.sort(noleggiOrdinatiStampa, Collections.reverseOrder(new CompareByDate()));
		try 
		{
			FileWriter file = new FileWriter(new File(this.percorsoDiSalvataggio.getText(), "fileGeneratoBevilacqua.txt"));
			String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
			file.write("Data ultimo aggiornamento: " + timeStamp + "\n\n\n");
			for(BarcaAllInformazioni data: noleggiOrdinatiStampa)
			{
				file.write("NUMEROSERIE: " + data.getNumeroSerie());
				file.write(", MARCA: " + data.getMarca());
				file.write(", MODELLO: " + data.getModello());
				file.write(", TIPO: " + data.getTipo().toString());
				if(data.getCodiceBagno1()!=0)
				file.write(", CODICEBAGNO1: " + data.getCodiceBagno1());
				if(data.getCodiceCabina()!=0)
				file.write(", CODICECABINA: " + data.getCodiceCabina());
				if(data.getCodiceBagno2()!=0)
				file.write(", CODICEBAGNO2: " + data.getCodiceBagno2());
				file.write(", DATA NOLEGGIO: " + data.getDataInizioNoleggio().toString());
				file.write("\n");
			}
			file.close();
		} catch (IOException e) 
		{
			Grafica.mostraInsuccessoInserimento();
			System.out.println("Errore file!");
			e.printStackTrace();
			return;
		}
		Grafica.mostraGenerazioneFile();
		return;
	}
	
	/**
	 * Verifica se tutti i campi utili alla generazione del file sono stati avvalorati correttamente.
	 * In particolare i campi relativi al codice fiscale del cliente e al percorso di salvataggio sono obbligatori.
	 * @return true se tutti i campi sono stati avvalorati correttamente, false altrimenti.
	 */
	private boolean verificaCampiStampa()
	{
		if(this.codiceFiscaleStampa.getValue()==null)
		{
			System.out.println("Non hai inserito il codice fiscale!");
			return false;
		}
		if(this.percorsoDiSalvataggio.getText().length()<1)
		{
			System.out.println("Non hai inserito il percorso di salvataggio!");
			return false;
		}
		return true;
	}
	
	/**
	 * Riempie la ComboBox 'codiceFiscaleStampa' con tutti i codici fiscali memorizzati all'interno del database.
	 */
	private void riempiCodiceFiscaleStampa()
	{
		PreparedStatement riempi=null;
		String query = "Select codiceFiscale from Cliente;";
		codiceFiscaleStampa.getItems().clear();
		try 
		{
			riempi = Main.c.prepareStatement(query);
			ResultSet r = riempi.executeQuery();
			while(r.next())
			{
				codiceFiscaleStampa.getItems().add(r.getString(1));
			}
			riempi.close();
		} catch (SQLException e) 
		{
			e.printStackTrace();
			try 
			{
				riempi.close();
			} catch (SQLException e1) 
			{
				e1.printStackTrace();
				Main.setTitoloScena("Noleggio");
				Main.changeScene("MainScene.fxml");
				Grafica.mostraInsuccessoInserimento();
				return;
			}
		}
	}
	
	
	/**
	 * Riempie la ComboBox 'codiceFiscale' con tutti i codici fiscali memorizzati all'interno del database.
	 */
	private void riempiCodiceFiscale()
	{
		PreparedStatement riempi=null;
		String query = "Select codiceFiscale from Cliente;";
		codiceFiscale.getItems().clear();
		try 
		{
			riempi = Main.c.prepareStatement(query);
			ResultSet r = riempi.executeQuery();
			while(r.next())
			{
				codiceFiscale.getItems().add(r.getString(1));
			}
			riempi.close();
		} catch (SQLException e) 
		{
			e.printStackTrace();
			try 
			{
				riempi.close();
			} catch (SQLException e1) 
			{
				e1.printStackTrace();
				Main.setTitoloScena("Noleggio");
				Main.changeScene("MainScene.fxml");
				Grafica.mostraInsuccessoInserimento();
				return;
			}
		}
	}
}
