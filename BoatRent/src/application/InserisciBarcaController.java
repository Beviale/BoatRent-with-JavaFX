/**
 * @author AlessandroBevilacqua.
 * @version 1.0.
 */
package application;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.HashSet;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import noleggio.Noleggio;
import utility.Grafica;
import utility.SaveAboutNoleggio;
/**
 * Controller di InserisciBarca.fxml.
 * Questa schermata permette di inserire le barche ad un noleggio già inizializzato.
 * @author AlessandroBevilacqua.
 */
public class InserisciBarcaController implements Initializable
{
	/**
	 * <p>Button che permette all'utente di terminare l'inserimento delle barche</p>
	 * <p>A livello grafico comporta il termine della creazione della pratica di noleggio</p>.
	 */
	@FXML
	private Button inserisciNoleggio;
	/**
	 * Permette la memorizzazione dei numeroSerie delle barche che sono state aggiunte alla pratica di noleggio corrente.
	 */
	private HashSet<String> codiciBarcaInseriti;
	/**
	 * ComboBox contenente tutti i numeroSerie delle barche memorizzate nel database ad eccezione di quelle che sono state già aggiunte alla pratica di noleggio corrente e visibili in 'elencoBarcheAggiunte'.
	 */
	@FXML
	private ComboBox<String> selezionaBarca;
	/**
	 * <p>Button che permette l'inserimento della barca seleziona in 'selezionaBarca' alla pratica di noleggio corrente.</p>
	 * <p>Praticamente inserisce una nuova riga alla tabella 'Noleggio' del database.
	 */
	@FXML
	private Button inserisciBarca;
	/**
	 * ComboBox contenente i numeroSerie delle barche sono state già aggiunte alla pratica di noleggio corrente.
	 */
	@FXML
	private ComboBox<String> elencoBarcheAggiunte;
	/**
	 * PreparedStatement utilizzata per l'inserimento di una nuova riga all'interno della tabella 'Noleggio' del database 'Noleggi.
	 */
	private PreparedStatement p;
	
	
	/**
	 * Si attiva con il Button inserisciNoleggio.
	 * Notifica all'utente il corretto inserimento del noleggio e ritorna alla schermata ClienteScene.fxml.
	 */
	@FXML
	public void accediInserisciNoleggio()
	{
		Main.setTitoloScena("Cliente");
		Main.changeScene("ClienteScene.fxml");
		try 
		{
			p.close();
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		Grafica.mostraSuccessoNoleggio();
	}
	
	/**
	 * Si attiva con il Button inserisciBarca e permette l'inserimento di una nuova barca alla pratica di noleggio corrente.
	 * Genera un errore se l'utente non ha selezionato la barca mediante la ComboBox 'selezionaBarca'.	
	 * Se non risulta neanche una barca memorizzata nel database genera un avviso per l'utente.
	 */
	@FXML
	public void accediInserisciBarca()
	{
		if(selezionaBarca.getItems().isEmpty() && elencoBarcheAggiunte.getItems().isEmpty())
		{
			Grafica.mostraMancanzaBarche();
		}
		if(this.selezionaBarca.getValue()==null)
		{
			System.out.println("Non hai selezionato alcuna barca!");
			return;
		}
		this.codiciBarcaInseriti.add(this.selezionaBarca.getValue());
		try 
		{
			inserisciNoleggio();
		}catch(SQLException e)
		{
			Main.setTitoloScena("Cliente");
			Main.changeScene("ClienteScene.fxml");
			Grafica.mostraInsuccessoInserimento();
			return;
		} catch (Exception e1) 
		{
			e1.printStackTrace();
			return;
		}
		try 
		{
			this.riempiSelezionaBarca();
		} catch (SQLException e) 
		{
			Main.changeScene("MainScene.fxml");
			Main.setTitoloScena("Noleggio");
			Grafica.mostraInsuccessoInserimento();
			e.printStackTrace();
			e.printStackTrace();
		}
		this.riempiElencoBarcheAggiunte();
		Grafica.mostraSuccessoInserimento();
		this.inserisciNoleggio.setVisible(true);
		return;
	}
	
	
	/**
	 * Inserisce una nuova riga nella tabella 'Noleggio' del database.
	 * Utilizza i metodi statici della classe SaveAboutNoleggio dove sono memorizzati i dati raccolti da ClienteSceneController.
	 * @throws Exception se il cliente ha raggiunto il numero massimo di barche noleggiabili nel giorno specificato.
	 * @throws SQLException se si verifica un errore nelle istruzioni di inserimento dei dati nel database.
	 */
	private void inserisciNoleggio() throws Exception, SQLException
	{
		if(verificaNumMaxBarche()==false)
		{
			Main.changeScene("ClienteScene.fxml");
			Main.setTitoloScena("Cliente");
			Grafica.mostraSuperamentoLimiteNoleggio();
			throw new Exception("Superamento Limite Noleggio");
		}
		Noleggio nuovo = new Noleggio(SaveAboutNoleggio.getCodiceFiscale(), this.selezionaBarca.getValue(), SaveAboutNoleggio.getCosto(), SaveAboutNoleggio.getDataInizio(), SaveAboutNoleggio.isSkipper());
		if(SaveAboutNoleggio.getPenale()!=0)
		{
			nuovo.setPenale(SaveAboutNoleggio.getPenale());
		}
		if(SaveAboutNoleggio.getDataFine()!=null)
		{
			nuovo.setDataFine(SaveAboutNoleggio.getDataFine());
		}

		p.setInt(1, nuovo.getCodiceNoleggio());
		p.setFloat(2, nuovo.getPenale());
		p.setFloat(3, nuovo.getCosto());
		Date dateInizio = java.sql.Date.valueOf(nuovo.getDataInizio());
		p.setDate(4, (java.sql.Date) dateInizio);
		if(nuovo.getDataFine()==null)
		{
			p.setNull(5, Types.DATE);
		}
		else
		{
			Date dateFine = java.sql.Date.valueOf(nuovo.getDataFine());
			p.setDate(5, (java.sql.Date) dateFine);
		}
		p.setString(6, nuovo.getCodiceBarca());
		p.setString(7, nuovo.getCodiceCliente());
		p.setBoolean(8, nuovo.isSkipper());
		p.execute();
		System.out.println("Noleggio inserito correttamente!" + "CodiceNoleggio: " + nuovo.getCodiceNoleggio() + ", CodiceFiscaleCliente: " + nuovo.getCodiceCliente() + ", CodiceBarca: " + nuovo.getCodiceBarca() + ", Costo: " + nuovo.getCosto() +  ", Penale: " + nuovo.getPenale() +  ", DataInizioNoleggio: " + nuovo.getDataInizio() +  ", DataFineNoleggio: " + nuovo.getDataFine() + ", Skipper: " + nuovo.isSkipper() + ".");
		return;
	}
	
	
	/**
	 * Verifica se il cliente associato al codice fiscale inserito in ClienteScene.fxml ha raggiunto il limite massimo giornaliero di barche noleggiabili. 
	 * @return true se il limite e' stato raggiunto, false altrimenti.
	 */
	private boolean verificaNumMaxBarche()
	{
		String queryVerifica = "select count(*) from cliente join noleggio on cliente.codiceFiscale=noleggio.cfCliente where cliente.codiceFiscale like '"+ SaveAboutNoleggio.getCodiceFiscale() + "' and noleggio.dataInizio='"+ SaveAboutNoleggio.getDataInizio() + "' group by cliente.codiceFiscale;";
		try 
		{
			PreparedStatement verifica = Main.c.prepareStatement(queryVerifica);
			ResultSet rVerifica = verifica.executeQuery();
			if(rVerifica.next()==true)
			{
				int noleggiFatti = rVerifica.getInt(1);
				queryVerifica = "select cliente.numMaxBarche from cliente where cliente.codiceFiscale like '" + SaveAboutNoleggio.getCodiceFiscale()+ "';";
				rVerifica = verifica.executeQuery(queryVerifica);
				rVerifica.next();
				int numMaxBarche = rVerifica.getInt(1);
				if(noleggiFatti==numMaxBarche)
				{
					return false;
				}
			}
			
			rVerifica.close();
		} catch (SQLException e) 
		{
			Grafica.mostraInsuccessoInserimento();
			Main.changeScene("ClienteScene.fxml");
			Main.setTitoloScena("Cliente");
			e.printStackTrace();
			return false;
		}
		return true;
	}


	/**
	 * Inizializza la visualizzazione grafica iniziale.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		preparaQueryInserimentoNoleggio();
		codiciBarcaInseriti = new HashSet<String>();
		this.inserisciNoleggio.setVisible(false);
		try 
		{
			riempiSelezionaBarca();
		} catch (SQLException e) 
		{
			Main.changeScene("MainScene.fxml");
			Main.setTitoloScena("Noleggio");
			Grafica.mostraInsuccessoInserimento();
			e.printStackTrace();
		}
	}
	
	/**
	 * Crea la query di inserimento noleggio parametrizzata.
	 */
	private void preparaQueryInserimentoNoleggio()
	{
		String queryInserimentoNoleggio = "insert into Noleggio values (?, ?, ?, ?, ?, ?, ?, ?);";
		try 
		{
			p = Main.c.prepareStatement(queryInserimentoNoleggio);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Aggiunge alla ComboBox 'selezionaBarca' i numeroSerie di tutte le barche memorizzate nel database ad eccezione di quelle che sono state gia' aggiunte al noleggio che si sta creando.
	 * Ciò viene fatto per evitare che l'utente possa noleggiare una stessa barca più volte con la stessa pratica di noleggio.
	 * @throws SQLException in caso di errori relativi alla query di reperimento dati dal database.
	 */
	private void riempiSelezionaBarca() throws SQLException
	{
		String query = "Select numeroSerie from Barca";
		selezionaBarca.getItems().clear();
		PreparedStatement p = null;
		p = Main.c.prepareStatement(query);
		ResultSet r = p.executeQuery();
		while(r.next())
		{
			if(codiciBarcaInseriti.contains(r.getString(1))==false)
			{
				selezionaBarca.getItems().add(r.getString(1));
			}
		}
		p.close();
	}
	
	
	
	
	/**
	 * Aggiunge alla ComboBox 'elencoBarcheAggiunte' i numeroSerie delle barche che sono state gia' aggiunte correttamente alla pratica di noleggio corrente.
	 */
	private void riempiElencoBarcheAggiunte()
	{
		this.elencoBarcheAggiunte.getItems().clear();
		for(String barca: codiciBarcaInseriti)
		{
			this.elencoBarcheAggiunte.getItems().add(barca);
		}
	}
}
