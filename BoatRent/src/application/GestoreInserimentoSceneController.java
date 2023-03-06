/**
 * @author AlessandroBevilacqua.
 * @version 1.0.
 */

package application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import noleggio.Bagno;
import noleggio.Barca;
import noleggio.BarcaAvanzato;
import noleggio.BarcaBase;
import noleggio.BarcaMedio;
import noleggio.Cabina;
import utility.*;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;

/**
 * Controller di GestoreInserimentoScene.fxml.
 * Questa schermata permette l'inserimento di barche, cabine e bagni nel database.
 * @author AlessandroBevilacqua.
 */
public class GestoreInserimentoSceneController implements Initializable
{
	/**
	 * Button che permette di tornare indietro a GestoreScene.fxml.
	 */
	@FXML
	private Button backGestore;
	/**
	 * PreparedStatement utilizzata per tutte le operazioni di inserimento (bagni, cabine e barche).
	 */
	private PreparedStatement p; 
	/**
	 * Variabile di supporto utilizzata per evitare di mostrare la schermata di errore due volte.
	 */
	private boolean flagError=false;
	
	
	
	
	// Attributi relativi all'inserimento del bagno.
	/**
	 * Button che permette l'inserimento nel database di un nuovo bagno.
	 */
	@FXML
	private Button inserisciBagno;
	/**
	 * TextField contenente l'area (tipo float) del bagno che si vuole aggiungere al database.
	 */
	@FXML
	private TextField areaBagno;
	/**
	 * TextField contenente il modello (tipo String) del bagno che si vuole aggiungere al database.
	 */
	@FXML
	private TextField modelloBagno;
	/**
	 * Contiene l'area del bagno acquisita da TextField areaBagno.
	 */
	private float inserimentoAreaBagno;
	/**
	 * Contiene il modello del bagno acquisito da TextField modelloBagno.
	 */
	private String inserimentoModelloBagno;
	/**
	 * Query parametrizzata per l'inserimento di un nuovo bagno nel database.
	 */
	private static final String queryInserimentoBagno = "insert into Bagno values (?, ?, ?);";

	
	
	// Attributi relativi all'inserimento della Cabina.
	/**
	 * TextField contenente l'area (tipo float) della cabina che si vuole aggiungere al database.
	 */
	@FXML
	private TextField areaCabina;
	/**
	 * Button che permette l'inserimento di una nuova cabina nel database.
	 */
	@FXML
	private Button inserisciCabina;
	/**
	 * TextField contenente il modello (tipo String) della cabina che si vuole aggiungere al database.
	 */
	@FXML
	private TextField modelloCabina;
	/**
	 * Contiene l'area acquisita dalla TextField areaCabina.
	 */
	private float inserimentoAreaCabina;
	/**
	 * Contiene il modello acquisito dalla TextField modelloCabina.
	 */
	private String inserimentoModelloCabina;
	/**
	 * Query parametrizzata per l'inserimento di una nuova cabina nel database.
	 */
	private static final String queryInserimentoCabina = "insert into Cabina values (?, ?, ?);";
	
	
	
	// Attributi relativi all'inserimento della barca.
	/**
	 * ComboBox contenente il tipo di barca (base, medio, avanzato).
	 */
	@FXML
	private ComboBox<String> tipoBarca;
	/**
	 * ComboBox contenente il codice della cabina della barca che si vuole aggiungere al database.
	 */
	@FXML
	private ComboBox<Integer> codiceCabina;
	/**
	 * ComboBox contenente il codice del primo bagno della barca che si vuole aggiungere al database.
	 */
	@FXML
	private ComboBox<Integer> codiceBagno1;
	/**
	 * ComboBox contenente il codice del secondo bagno della barca che si vuole aggiungere al database.
	 */
	@FXML
	private ComboBox<Integer> codiceBagno2;
	/**
	 * TextField contenente il numero di serie della barca (stringa numerica di esattamente di 12 caratteri) che si vuole aggiungere al database.
	 */
	@FXML
	private TextField codiceBarca;
	/**
	 * TextField contenente la marca della barca che si vuole aggiungere al database.
	 */
	@FXML
	private TextField marcaBarca;
	/**
	 * TextField contenente il modello della barca che si vuole aggiungere al database.
	 */
	@FXML
	private TextField modelloBarca;
	/**
	 * Button che permette l'inserimento di una nuova barca al database.
	 */
	@FXML
	private Button inserisciBarca;
	
	
	
	
	/**
	 * Si attiva con il button backGestore e permette di tornare alla schermata precedente ovvero GestoreScene.fxml.
	 * @param event
	 */
	@FXML
	public void accediGestore(ActionEvent event) 
	{
		Main.setTitoloScena("Gestore");
		Main.changeScene("GestoreScene.fxml");
	}
	
	
	//--------------------Metodi per l'inserimento del bagno------------------//
	
	/**
	 * Si attiva con Button inserisciBagno e permette l'aggiunta di un nuovo bagno al database.
	 * @param event.
	 */
	@FXML
	public void accediInserisciBagno(ActionEvent event) 
	{
		preparaQueryBagno();
		/**
		 * Stringa contenente l'acquisizione dalla TextField modelloBagno.
		 */
		String stringModelloBagno = modelloBagno.getText();
		/**
		 * Stringa contenente l'acquisizione dalla TextField areaBagno.
		 */
		String stringAreaBagno = areaBagno.getText();
		if(stringModelloBagno.length()>30 || stringModelloBagno.length()<1) // Se la stringa inserita in modelloBagno è nulla oppure e' costituita da più di 30 caratteri.
		{
			flagError=true;
			Main.setTitoloScena("GestoreInserimento");
			Main.changeScene("GestoreInserimentoScene.fxml");
			Grafica.mostraInsuccessoInserimento();
			System.out.println("Stringa 'modelloBagno' inserita troppo lunga o nulla.");
			return;
		}
		
		try
		{
			/**
			 * Variabile float che contiene la parserizzazione di stringAreaBagno.
			 */
			Float floatAreaBagno = Float.parseFloat(stringAreaBagno);
			Main.setTitoloScena("GestoreInserimento");
			Main.changeScene("GestoreInserimentoScene.fxml");
			Grafica.mostraSuccessoInserimento();
			this.inserimentoAreaBagno=floatAreaBagno;
			this.inserimentoModelloBagno=stringModelloBagno;
			inserisciElementoBagno();
			modelloBagno.clear();
			areaBagno.clear();
		}catch(Exception e) // se Float.parseFloat() genera un'eccezione.
		{
			e.printStackTrace();
			System.out.println("Errore stringa 'areaBagno' inserita.");
			if(flagError!=true)
			{
				Main.setTitoloScena("GestoreInserimento");
				Main.changeScene("GestoreInserimentoScene.fxml");
				Grafica.mostraInsuccessoInserimento();
			}
			return;
		}
	}
	
	
	/**
	 * Metodo di supporto ad accediInserisciBagno():
	 * Crea l'oggetto Bagno e lo memorizza nel database utilizzando la PreparedStatement già parametrizzata da preparaQueryBagno().
	 */
	private void inserisciElementoBagno()
	{
		Bagno nuovoBagno = new Bagno(inserimentoAreaBagno, inserimentoModelloBagno);
		try 
		{
			p.setInt(1, nuovoBagno.getCodice());
			p.setFloat(2, nuovoBagno.getArea());
			p.setString(3, nuovoBagno.getModello());
			p.execute();
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
			return;
		}
		System.out.println("Bagno inserito con successo!" + "Codice: " + nuovoBagno.getCodice() + ", Area: " + nuovoBagno.getArea() +", Modello: " +nuovoBagno.getModello()+".");
	}
	
	
	/**
	 * Inizializza la preparedStatement con queryInserimentoBagno.
	 */
	private void preparaQueryBagno()
	{
		try 
		{
			p = Main.c.prepareStatement(queryInserimentoBagno);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	
	
	//--------------------Metodi per l'inserimento della cabina------------------//
	
	/**
	 * Si attiva con Button inserisciCabina.
	 * Permette l'inserimento di una nuova cabina al database.
	 * @param event
	 */
	public void accediInserisciCabina(ActionEvent event) 
	{
		preparaQueryCabina();
		/**
		 * String contenente l'area della cabina acquisita dalla TextField areaCabina.
		 */
		String stringAreaCabina = areaCabina.getText();
		/**
		 * String contenente il modello della cabina acquisito dalla TextField modelloCabina.
		 */
		String stringModelloCabina = modelloCabina.getText();
		if(stringModelloCabina.length()>30 || stringModelloCabina.length()<1) // Se la stringa inserita in modelloCabina è nulla oppure e' costituita da più di 30 caratteri.
		{
			flagError=true;
			Main.setTitoloScena("GestoreInserimento");
			Main.changeScene("GestoreInserimentoScene.fxml");
			Grafica.mostraInsuccessoInserimento();
			System.out.println("Stringa 'modelloCabina' inserita troppo lunga o nulla.");
			return;
		}
		try
		{
			/**
			 * Variabile che contiene la parserizzazione in float dell'areaCabina acquisita dall'utente.
			 */
			Float floatAreaCabina = Float.parseFloat(stringAreaCabina);
			Main.setTitoloScena("GestoreInserimento");
			Main.changeScene("GestoreInserimentoScene.fxml");
			Grafica.mostraSuccessoInserimento();
			this.inserimentoAreaCabina=floatAreaCabina;
			this.inserimentoModelloCabina=stringModelloCabina;
			inserisciElementoCabina();
			areaCabina.clear();
		}catch(Exception e) // Se Float.parseFloat() genera un'eccezione.
		{
			e.printStackTrace();
			System.out.println("Errore stringa 'areaCabina' inserita.");
			if(flagError!=true)
			{
				Main.setTitoloScena("GestoreInserimento");
				Main.changeScene("GestoreInserimentoScene.fxml");
				Grafica.mostraInsuccessoInserimento();
			}
			return;
		}
	}
	
	/**
	 * Inizializza la preparedStatement con queryInserimentoCabina.
	 */
	private void preparaQueryCabina()
	{
		try 
		{
			p = Main.c.prepareStatement(queryInserimentoCabina);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Metodo di supporto ad accediInserisciCabina.
	 * Crea una nuova cabina e la aggiunge al database.
	 * Sfrutta la preparedStatement gia' parametrizzata da preparaQueryCabina().
	 */
	private void inserisciElementoCabina()
	{
		Cabina nuovoCabina = new Cabina(inserimentoAreaCabina, inserimentoModelloCabina);
		try 
		{
			p.setInt(1, nuovoCabina.getCodiceCabina());
			p.setFloat(2, nuovoCabina.getArea());
			p.setString(3, nuovoCabina.getModello());
			p.execute();
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
			return;
		}
		System.out.println("Cabina inserita con successo!" + "Codice: " + nuovoCabina.getCodiceCabina() + ", Area: " + nuovoCabina.getArea()+ ", Modello: " + nuovoCabina.getModello() + ".");
	}
	
	
	//--------------------Metodi per l'inserimento della barca------------------//
	/**
	 * In base al tipo di barca selezionato (Base, Medio o Avanzato) chiama i rispettivi metodi dedicati.
	 */
	@FXML
	public void accediTipoBarca()
	{
		if(tipoBarca.getValue().toString()=="Base")
		{
			accediTipoBarcaBase();
		}
		else if(tipoBarca.getValue().toString()=="Medio")
		{
			accediTipoBarcaMedio();
		}
		else if(tipoBarca.getValue().toString()=="Avanzato")
		{
			accediTipoBarcaAvanzato();
		}
	}
	
	/**
	 * Metodo che imposta la visualizzazione grafica nel caso in cui l'utente dovesse scegliere di inserire una barca di tipo 'Base'.
	 */
	private void accediTipoBarcaBase()
	{
		Effect effect = new GaussianBlur();
		codiceBagno1.setDisable(true);
		codiceBagno1.setEffect(effect);
		codiceBagno2.setDisable(true);
		codiceBagno2.setEffect(effect);
		codiceCabina.setDisable(true);
		codiceCabina.setEffect(effect);
		
		
		codiceBarca.setDisable(false);
		codiceBarca.setEffect(null);
		modelloBarca.setDisable(false);
		modelloBarca.setEffect(null);
	}
	
	/**
	 * Metodo che imposta la visualizzazione grafica nel caso in cui l'utente dovesse scegliere di inserire una barca di tipo 'Medio'.
	 */
	private void accediTipoBarcaMedio()
	{
		Effect effect = new GaussianBlur();
		codiceBagno2.setDisable(true);
		codiceBagno2.setEffect(effect);
		codiceCabina.setDisable(true);
		codiceCabina.setEffect(effect);
		
		
		codiceBagno1.setDisable(false);
		codiceBagno1.setEffect(null);
		codiceBarca.setDisable(false);
		codiceBarca.setEffect(null);
		modelloBarca.setDisable(false);
		modelloBarca.setEffect(null);
		riempiCodiceBagno1();
		
	}
	
	/**
	 * Metodo che imposta la visualizzazione grafica nel caso in cui l'utente dovesse scegliere di inserire una barca di tipo 'Avanzato'.
	 */
	private void accediTipoBarcaAvanzato()
	{
		codiceCabina.setDisable(false);
		codiceCabina.setEffect(null);
		codiceBagno1.setDisable(false);
		codiceBagno1.setEffect(null);
		codiceBagno2.setDisable(false);
		codiceBagno2.setEffect(null);
		codiceBarca.setDisable(false);
		codiceBarca.setEffect(null);
		modelloBarca.setDisable(false);
		modelloBarca.setEffect(null);
		
		riempiCodiceBagno1();
		riempiCodiceBagno2();
		riempiCodiceCabina();
	}
	
	/**
	 * Inserisce nella ComboBox 'codiceBagno1' i codici di tutti i bagni attualmente memorizzati nel database.
	 */
	private void riempiCodiceBagno1()
	{
		String query = "Select codiceBagno from Bagno;";
		codiceBagno1.getItems().clear();
		try 
		{
			p = Main.c.prepareStatement(query);
			ResultSet r = p.executeQuery();
			while(r.next())
			{
				codiceBagno1.getItems().add(r.getInt(1));
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
	 * Inserisce nella ComboBox 'codiceBagno2' i codici di tutti i bagni attualmente memorizzati nel database.
	 */
	private void riempiCodiceBagno2()
	{
		String query = "Select codiceBagno from Bagno;";
		codiceBagno2.getItems().clear();
		try 
		{
			p = Main.c.prepareStatement(query);
			ResultSet r = p.executeQuery();
			while(r.next())
			{
				codiceBagno2.getItems().add(r.getInt(1));
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
	 * Inserisce nella ComboBox 'codiceCabina' i codici di tutte le cabine attualmente memorizzate nel database.
	 */
	private void riempiCodiceCabina()
	{
		String query = "Select codiceCabina from Cabina;";
		codiceCabina.getItems().clear();
		try 
		{
			p = Main.c.prepareStatement(query);
			ResultSet r = p.executeQuery();
			while(r.next())
			{
				codiceCabina.getItems().add(r.getInt(1));
			}
			p.close();
		} catch (SQLException e) 
		{
			e.printStackTrace();
			try {
				p.close();
			} catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * Si attiva con il Button inserisciBarca e permette l'inserimento di una nuova barca al database.
	 */
	public void accediInserisciBarca()
	{
		if(tipoBarca.getValue()==null) 
		{
			Main.setTitoloScena("GestoreInserimento");
			Main.changeScene("GestoreInserimentoScene.fxml");
			System.out.println("Seleziona il tipo di barca!");
			Grafica.mostraInsuccessoInserimento();
		}
		else if(tipoBarca.getValue().toString()=="Base")
		{
			preparaQueryInserimentoBarca();
			inserisciBarcaBase();
		}
		else if(tipoBarca.getValue().toString()=="Medio")
		{
			preparaQueryInserimentoBarca();
			inserisciBarcaMedio();
		}
		else if(tipoBarca.getValue().toString()=="Avanzato")
		{
			preparaQueryInserimentoBarca();
			inserisciBarcaAvanzato();
		}
	}
	
	
	/**
	 * Inizializza e parametrizza la preparedStatement per l'inserimento della barca al database.
	 */
	private void preparaQueryInserimentoBarca()
	{
		String preparedInserisciBarca = "Insert into Barca value(?,?,?,?,?,?,?);";
		try 
		{
			p = Main.c.prepareStatement(preparedInserisciBarca);
		} catch (SQLException e) 
		{
			Main.setTitoloScena("GestoreInserimento");
			Main.changeScene("GestoreInserimentoScene.fxml");
			Grafica.mostraInsuccessoInserimento();
			e.printStackTrace();
			return;
		}
	}
	
	
	/**
	 * Crea un oggetto BarcaBase e lo memorizza nel database. Effettua anche controlli sull'input dell'utente.
	 */
	private void inserisciBarcaBase()
	{
		if(verificaCodiceMarcaModello()==false)
		{
			return;
		}
		BarcaBase nuovo = new BarcaBase(codiceBarca.getText(), modelloBarca.getText()); 
		try 
		{
			p.setString(1, nuovo.getNumeroSerie());
			p.setString(2, Barca.getMarca());
			p.setString(3, nuovo.getModello());
			p.setString(4, nuovo.getTipo().toString());
			p.setNull(5, Types.INTEGER);
			p.setNull(6, Types.INTEGER);
			p.setNull(7, Types.INTEGER);
			p.execute();
			p.close();
			System.out.println("Barca inserita correttamente!");
			Main.setTitoloScena("GestoreInserimento");
			Main.changeScene("GestoreInserimentoScene.fxml");
			Grafica.mostraSuccessoInserimento();
		} catch (SQLException e) 
		{
			Main.setTitoloScena("GestoreInserimento");
			Main.changeScene("GestoreInserimentoScene.fxml");
			Grafica.mostraInsuccessoInserimento();
			e.printStackTrace();
			try {
				p.close();
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			return;
		}
	}
	
	
	/**
	 * Verifica se una stringa sia esattamente di 12 caratteri e contenga esclusivamente numeri.
	 * @param verifica stringa da verificare.
	 * @return true se la stringa e' nel formato corretto, false altrimenti.
	 */
	private boolean verificaNumSerie(String verifica)
	{
		String regex="[0-9]{12}";
		return Pattern.matches(regex, verifica);
		
	}
	
	/**
	 * Crea un oggetto BarcaMedio e lo memorizza nel database. Effettua anche controlli sull'input dell'utente.
	 */
	private void inserisciBarcaMedio()
	{
		if(verificaCodiceMarcaModello()==false)
		{
			return;
		}
		if(codiceBagno1.getSelectionModel().isEmpty())
		{
			Main.setTitoloScena("GestoreInserimento");
			Main.changeScene("GestoreInserimentoScene.fxml");
			System.out.println("Il codice del bagno risulta null!");
			Grafica.mostraInsuccessoInserimento();
			return;
		}
		String queryIstanzia = "select * from bagno where codiceBagno=?";
		PreparedStatement istanzia = null;
		// Creo il bagno che deve essere aggiunto alla barca di tipo Medio.
		Bagno primoBagno;
		try 
		{
			istanzia = Main.c.prepareStatement(queryIstanzia);
			istanzia.setInt(1, codiceBagno1.getValue().intValue());
			ResultSet r = istanzia.executeQuery();
			r.next();
			primoBagno = new Bagno(r.getFloat(2), r.getString(3));
			primoBagno.setCodiceBagno(r.getInt(1));
			istanzia.close();
		} catch (SQLException e1) 
		{
			Main.setTitoloScena("GestoreInserimento");
			Main.changeScene("GestoreInserimentoScene.fxml");
			Grafica.mostraInsuccessoInserimento();
			e1.printStackTrace();
			try 
			{
				istanzia.close();
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
			return;
		}
		BarcaMedio nuovo = new BarcaMedio(codiceBarca.getText(), modelloBarca.getText(), primoBagno); 
		try 
		{
			p.setString(1, nuovo.getNumeroSerie());
			p.setString(2, Barca.getMarca());
			p.setString(3, nuovo.getModello());
			p.setString(4, nuovo.getTipo().toString());
			p.setInt(5, nuovo.getBagno().getCodice());
			p.setNull(6, Types.INTEGER);
			p.setNull(7, Types.INTEGER);
			p.execute();
			p.close();
			System.out.println("Barca inserita correttamente!");
			Main.setTitoloScena("GestoreInserimento");
			Main.changeScene("GestoreInserimentoScene.fxml");
			Grafica.mostraSuccessoInserimento();
		} catch (SQLException e) 
		{
			Main.setTitoloScena("GestoreInserimento");
			Main.changeScene("GestoreInserimentoScene.fxml");
			Grafica.mostraInsuccessoInserimento();
			e.printStackTrace();
			try 
			{
				p.close();
			} catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
			return;
		}
	}
	
	
	
	/**
	 * Crea un oggetto BarcaAvanzato e lo memorizza nel database. Effettua anche controlli sull'input dell'utente.
	 */
	private void inserisciBarcaAvanzato()
	{
		if(verificaCodiceMarcaModello()==false)
		{
			return;
		}
		if(codiceBagno1.getSelectionModel().isEmpty() || codiceBagno2.getSelectionModel().isEmpty())
		{
			Main.setTitoloScena("GestoreInserimento");
			Main.changeScene("GestoreInserimentoScene.fxml");
			System.out.println("Il codice del bagno risulta null!");
			Grafica.mostraInsuccessoInserimento();
			return;
		}
		if(codiceCabina.getSelectionModel().isEmpty())
		{
			Main.setTitoloScena("GestoreInserimento");
			Main.changeScene("GestoreInserimentoScene.fxml");
			System.out.println("Il codice della cabina risulta null!");
			Grafica.mostraInsuccessoInserimento();
			return;
		}
		String queryIstanziaBagno1 = "select * from bagno where codiceBagno=?";
		String queryIstanziaBagno2 = "select * from bagno where codiceBagno=?";
		String queryIstanziaCabina = "select * from cabina where codiceCabina=?";
		PreparedStatement istanzia = null;
		// Creo il primo bagno che deve essere aggiunto alla barca di tipo Avanzato.
		Bagno primoBagno;
		// Creo il secondo bagno che deve essere aggiunto alla barca di tipo Avanzato.
		Bagno secondoBagno;
		// Creo la cabina che deve essere aggiunta alla barca di tipo Avanzato.
		Cabina cabina;
		ResultSet r;
		try 
		{
			// Inizializzo primoBagno con i dati memorizzati nel database.
			istanzia = Main.c.prepareStatement(queryIstanziaBagno1);
			istanzia.setInt(1, codiceBagno1.getValue().intValue());
			r = istanzia.executeQuery();
			r.next();
			primoBagno = new Bagno(r.getFloat(2), r.getString(3));
			primoBagno.setCodiceBagno(r.getInt(1));
			// Inizializzo secondoBagno con i dati memorizzati nel database.
			istanzia = Main.c.prepareStatement(queryIstanziaBagno2);
			istanzia.setInt(1, codiceBagno2.getValue().intValue());
			r = istanzia.executeQuery();
			r.next();
			secondoBagno = new Bagno(r.getFloat(2), r.getString(3));
			secondoBagno.setCodiceBagno(r.getInt(1));
			// Inizializzo cabina con i dati memorizzati nel database.
			istanzia = Main.c.prepareStatement(queryIstanziaCabina);
			istanzia.setInt(1, codiceCabina.getValue().intValue());
			r = istanzia.executeQuery();
			r.next();
			cabina = new Cabina(r.getFloat(2), r.getString(3));
			cabina.setCodiceCabina(r.getInt(1));
			istanzia.close();
		} catch (SQLException e1) 
		{
			Main.setTitoloScena("GestoreInserimento");
			Main.changeScene("GestoreInserimentoScene.fxml");
			Grafica.mostraInsuccessoInserimento();
			e1.printStackTrace();
			try 
			{
				istanzia.close();
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
			return;
		}
		BarcaAvanzato nuovo = new BarcaAvanzato(codiceBarca.getText(), modelloBarca.getText(), primoBagno, secondoBagno, cabina); 
		try 
		{
			p.setString(1, nuovo.getNumeroSerie());
			p.setString(2, Barca.getMarca());
			p.setString(3, nuovo.getModello());
			p.setString(4, nuovo.getTipo().toString());
			p.setInt(5, nuovo.getBagno1().getCodice());
			p.setInt(6, nuovo.getCabina().getCodiceCabina());
			p.setInt(7, nuovo.getBagno2().getCodice());
			p.execute();
			p.close();
			System.out.println("Barca inserita correttamente!");
			Main.setTitoloScena("GestoreInserimento");
			Main.changeScene("GestoreInserimentoScene.fxml");
			Grafica.mostraSuccessoInserimento();
		} catch (SQLException e) 
		{
			Main.setTitoloScena("GestoreInserimento");
			Main.changeScene("GestoreInserimentoScene.fxml");
			Grafica.mostraInsuccessoInserimento();
			e.printStackTrace();
			try 
			{
				p.close();
			} catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
			return;
		}
	}
	
	
	/**
	 * Verifica che il numeroSerie della barca inserito dall'utente sia nel formato corretto e che non risulti gia' presente all'interno del database.
	 * Verifica, inoltre, che la stringa inserita in modelloBarca non sia nulla oppure troppo lunga (max 30 caratteri).
	 * @return true se numeroSerie e modelloBarca sono corretti, false altrimenti.
	 */
	private boolean verificaCodiceMarcaModello()
	{
		if(verificaNumSerie(codiceBarca.getText())==false)
		{
			Main.setTitoloScena("GestoreInserimento");
			Main.changeScene("GestoreInserimentoScene.fxml");
			System.out.println("NumeroSerie nullo oppure formato non corretto!");
			Grafica.mostraInsuccessoInserimento();
			return false;
		}
		if(modelloBarca.getText().length()>30 || modelloBarca.getText().length()<1)
		{
			Main.setTitoloScena("GestoreInserimento");
			Main.changeScene("GestoreInserimentoScene.fxml");
			System.out.println("modelloBarca nullo oppure troppo lungo (MAX 30 CARATTERI)!");
			Grafica.mostraInsuccessoInserimento();
			return false;
		}
		String getAllNumeroSerie = "Select numeroSerie from barca;";
		/**
		 *  Set contenente tutti i numeroSerie presenti nel database.
		 */
		Set<String> allNumeroSerie = new HashSet<String>();
		PreparedStatement verificaNumSerie = null;
		try 
		{
			verificaNumSerie = Main.c.prepareStatement(getAllNumeroSerie);
			ResultSet r = verificaNumSerie.executeQuery();
			while(r.next())
			{
				allNumeroSerie.add(r.getString(1));
			}
			
		} catch (SQLException e) 
		{
			Main.setTitoloScena("GestoreInserimento");
			Main.changeScene("GestoreInserimentoScene.fxml");
			Grafica.mostraInsuccessoInserimento();
			e.printStackTrace();
			try 
			{
				verificaNumSerie.close();
			} catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
		}
		if(allNumeroSerie.contains(codiceBarca.getText()))
		{
			Main.setTitoloScena("GestoreInserimento");
			Main.changeScene("GestoreInserimentoScene.fxml");
			System.out.println("Hai Inserito un numeroSerie che e' già presente nel database!");
			Grafica.mostraInsuccessoInserimento();
			return false; 
		}
		return true;
	}
	
	
	
	
	/**
	 * Imposta la visualizzazione grafica iniziale della schermata GestoreInserimentoScene.fxml.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		tipoBarca.getItems().addAll(
			    "Base",
			    "Medio",
			    "Avanzato"
			);	
		marcaBarca.setText(Barca.getMarca());
		Effect effect = new GaussianBlur();
		codiceCabina.setDisable(true);
		codiceCabina.setEffect(effect);
		codiceBagno1.setDisable(true);
		codiceBagno1.setEffect(effect);
		codiceBagno2.setDisable(true);
		codiceBagno2.setEffect(effect);
		codiceBarca.setDisable(true);
		codiceBarca.setEffect(effect);
		modelloBarca.setDisable(true);
		modelloBarca.setEffect(effect);
	}
}
