/**
 * @author AlessandroBevilacqua.
 * @version 1.0.
 */
package application;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import noleggio.Cliente;
import utility.Grafica;

/**
 * Controller di MostraClienteScene.fxml.
 * Questa schermatta è costituita da una TableView contenente tutti i dati di tutti i clienti memorizzati nel database.
 * Tali dati sono ricavati mediante l'esecuzione di una query.
 * @author AlessandroBevilacqua.
 */
public class MostraClientiSceneController implements Initializable
{
	/**
	 * TableView contenente tutti i dati di tutti i clienti memorizzati nel database.
	 */
	@FXML
	private TableView<Cliente> tableClienti;
	/**
	 * TableColumn contenente i codici fiscali dei clienti.
	 */
	@FXML
	private TableColumn<Cliente, String> codiceFiscale;
	/**
	 * TableColumn contenente i nomi dei clienti.
	 */
	@FXML
	private TableColumn<Cliente, String> nome;
	/**
	 * TableColumn contenente i cognomi dei clienti.
	 */
	@FXML
	private TableColumn<Cliente, String> cognome;
	/**
	 * TableColumn contenente le date di nascita dei clienti.
	 */
	@FXML
	private TableColumn<Cliente, LocalDate> dataNascita;
	/**
	 * TableColumn contenente i sessi dei clienti.
	 */
	@FXML
	private TableColumn<Cliente, String> sesso;
	
	
	
	/**
	 * Imposta tutte le colonne della tableView e aggiunge tutti i dati di tutti i clienti memorizzati nel database.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		this.codiceFiscale.setCellValueFactory(new PropertyValueFactory<Cliente, String>("codiceFiscale"));
		this.nome.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nome"));
		this.cognome.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cognome"));
		this.dataNascita.setCellValueFactory(new PropertyValueFactory<Cliente, LocalDate>("dataNascita"));
		this.sesso.setCellValueFactory(new PropertyValueFactory<Cliente, String>("sesso"));
		try
		{
			String query = "select * from cliente;";
			PreparedStatement p = Main.c.prepareStatement(query);
			ResultSet r = p.executeQuery();
			Cliente aggiungi;
			while(r.next())
			{
				aggiungi = new Cliente(r.getString(1), r.getString(2), r.getString(3), r.getDate(4).toLocalDate(), r.getString(5));
				tableClienti.getItems().add(aggiungi);
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
			Grafica.mostraInsuccessoInserimento();
			return;
		}
	}
}
