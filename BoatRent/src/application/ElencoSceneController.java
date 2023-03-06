/**
 * @author AlessandroBevilacqua.
 * @version 1.0.
 */

package application;
import noleggio.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller di ElencoScene.fxml.
 * Questa schermata permette all'utente di visualizzare una tabella contenente le barche noleggiate da un determinato cliente e in un intervallo specificato di tempo.
 * La tabella è ordinata in base al tipo di barca (medio, base e poi avanzato) e, in caso di stesso tipo, in base alla data di noleggio (dalla meno recente).
 * @author AlessandroBevilacqua.
 */
public class ElencoSceneController 
{
	/**
	 * Tabella ordinata di BarcaNoleggio.
	 */
	@FXML
	private TableView<BarcaNoleggio> tabella;
	/**
	 * TabelColumn contenente i numeroSerie di BarcaNoleggio.
	 */
	@FXML 
	private TableColumn<BarcaNoleggio, String> numeroSerie;
	/**
	 * TableCokumn contenente le marche di BarcaNoleggio.
	 */
	@FXML 
	private TableColumn<BarcaNoleggio, String> marca;
	/**
	 * TableColumn contenente i modelli di BarcaNoleggio.
	 */
	@FXML 
	private TableColumn<BarcaNoleggio, String> modello;
	/**
	 * TableColumn contenente i tipi di BarcaNoleggio.
	 */
	@FXML 
	private TableColumn<BarcaNoleggio, TipologiaBarca> tipo;
	/**
	 * TableColumn contenente le date di inizio noleggio di BarcaNoleggio.
	 */
	@FXML 
	private TableColumn<BarcaNoleggio, LocalDate> dataNoleggio;
	/**
	 * Button che permette la visualizzazione dei dati della tabella.
	 */
	@FXML
	private Button showHide;
	/**
	 * Variabile di supporto al funzionamento del Button 'showHide'.
	 */
	private boolean buttonHide=false;
	
	
	
	private static LinkedList<BarcaNoleggio> elenco;
	
	/**
	 * Metodo statico che prende un elenco di BarcaNoleggio e lo ordina utilizzando CompareByTipoAndDate().
	 * @param elencoPassato elenco di BarcaNoleggio da ordinare.
	 */
	public static void setData(LinkedList<BarcaNoleggio> elencoPassato)
	{
		elenco=elencoPassato;
		Collections.sort(elenco, new CompareByTipoAndDate());
	}
	
	/**
	 * Inizializza gli elementi grafici della tableView 'tabella' e la riempie con gli oggetti BarcaNoleggio.
	 */
	@FXML
	public void costriuisciTabella()
	{
		if(buttonHide==true)
		{
			this.showHide.setText("Mostra");
			tabella.getItems().clear();
			buttonHide=false;
			return;
		}
		buttonHide=true;
		this.showHide.setText("Cancella");
		tabella.getItems().clear();
		this.numeroSerie.setCellValueFactory(new PropertyValueFactory<BarcaNoleggio, String>("numeroSerie"));
		this.marca.setCellValueFactory(new PropertyValueFactory<BarcaNoleggio, String>("marca"));
		this.modello.setCellValueFactory(new PropertyValueFactory<BarcaNoleggio, String>("modello"));
		this.tipo.setCellValueFactory(new PropertyValueFactory<BarcaNoleggio, TipologiaBarca>("tipo"));
		this.dataNoleggio.setCellValueFactory(new PropertyValueFactory<BarcaNoleggio, LocalDate>("dataInizioNoleggio"));
		for(BarcaNoleggio elemento: elenco)
		{
			tabella.getItems().add(elemento);
		}
	}
}
