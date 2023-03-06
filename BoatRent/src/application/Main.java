/**
 * @author AlessandroBevilacqua
 * @version 1.0
 */

package application;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


/**
 * Classe iniziale.
 * @author AlessandroBevilacqua
 *
 */
public class Main extends Application 
{
	/**
	 * URL utilizzato per effettuare la connessione al database.
	 */
	private static String dburl = "jdbc:mysql://127.0.0.1:3306";
	/**
	 * Nome utente utilizzato per effettuare la connessione al database.
	 */
	private static String user = "root";
	/**
	 * Password utilizzata per effettuare la connessione dl database.
	 */
	private static String password = "1234";
	/**
	 * Utilizzato per la connessione al database.
	 */
	public static Connection c;
	/**
	 * Stage primario utilizzato per la visualizzazione delle varie schermate.
	 */
	private static Stage primaryStage;
	
	
	
	/**
	 * Carica la schermata iniziale (MainScene.fxml) con titolo e icona.
	 * @param ps stage da utilzzare per il caricamento della schermata iniziale.
	 */
	@Override
	public void start(Stage ps) 
	{
		try 
		{
			primaryStage=ps;
			primaryStage.setResizable(false); // La schermata viene resa non modificabile nel layout.
			Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
			primaryStage.setScene(new Scene(root));
			Image image = new Image(Main.class.getResourceAsStream("/icon.png"));
			primaryStage.getIcons().add(image); // Aggiungo l'icona.
			primaryStage.setTitle("Noleggio"); // Imposto il titolo.
			primaryStage.show(); // Mostro la schermata iniziale
		} catch(Exception e) 
		{
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * Permette di modificare il titolo dell'attuale stage primario.
	 * @param titolo titolo da impostare alla schermata.
	 */
	public static void setTitoloScena(String titolo)
	{
		primaryStage.setTitle(titolo);
	}
	
	/**
	 * Permette di modificare l'attuale schermata collegata allo stage primario.
	 * @param path percorso del file fxml di riferimento.
	 */
	public static void changeScene(String path) 
	{
		Parent root;
		try 
		{
			root = FXMLLoader.load(Main.class.getResource(path));
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Se non esiste, crea il database 'Noleggi' con le varie tabelle.
	 */
	private static void CreaDatabase()
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver"); // Forzo la registrazione del driver presso il DriverMnagaer ovvero JDBC (Java DataBase Connectivity).
			c = DriverManager.getConnection(dburl, user, password); // Effettuo la connessione al database.
			/**
			 * Query che permette di creare il database 'Noleggi' e le varie tabelle (se non esistono).
			 */
			String queryIniziale="create database if not exists noleggi;";
			/**
			 * PreparedStatement che permette di eseguire tutte le query utili alla creazione del database e relative tabelle.
			 */
			PreparedStatement p = c.prepareStatement(queryIniziale);
			p.execute();
			queryIniziale="use noleggi";
			p = c.prepareStatement(queryIniziale);
			p.execute();
			queryIniziale="create table if not exists Cliente(CodiceFiscale char(16) primary key, Nome varchar(30) not null, Cognome varchar(30) not null, dataNascita date, sesso char(1), numMaxBarche int);";
			p = c.prepareStatement(queryIniziale);
			p.execute();
			queryIniziale="create table if not exists Bagno(codiceBagno int primary key, area float not null, modello varchar(30) not null);";
			p = c.prepareStatement(queryIniziale);
			p.execute();
			queryIniziale="create table if not exists Cabina(codiceCabina int primary key, area float not null, modello varchar(30) not null);";
			p = c.prepareStatement(queryIniziale);
			p.execute();
			queryIniziale="create table if not exists Barca(numeroSerie char(12) primary key, marca varchar(30) not null, modello varchar(30) not null, tipo varchar(30), codiceBagno1 int, codiceCabina int, codiceBagno2 int, foreign Key(codiceBagno1) references Bagno(codiceBagno), foreign Key(codiceBagno2) references Bagno(codiceBagno), foreign Key(codiceCabina) references Cabina(codiceCabina));";
			p = c.prepareStatement(queryIniziale);
			p.execute();
			queryIniziale="create table if not exists Noleggio(codiceNoleggio int primary key, penale float, costo float not null, dataInizio date not null, dataFine date, codiceBarca char(12), cfCliente char (16), Skipper boolean, foreign key (codiceBarca) references Barca(numeroSerie), foreign key(cfCliente) references Cliente(codiceFiscale));";
			p = c.prepareStatement(queryIniziale);
			p.execute();
			p.close(); // Chiudo la PreparedStatement.
			
		} catch(SQLException e)
		{
			e.printStackTrace();
		}catch(ClassNotFoundException e1)
		{
			e1.printStackTrace();
		}
	}
	
	
/**
 * Programma principale partente. Chiama CreaDatabase().
 * @param args argomenti passati in input all'avvio del programma.
 */

	public static void main(String[] args) 
	{
		CreaDatabase();
		launch(args);
		try 
		{
			c.close();
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
}
