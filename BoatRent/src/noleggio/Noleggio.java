/**
 * @author AlessandroBevilacqua.
 * @version 1.0.
 */
package noleggio;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

import application.Main;

/**
 * Classe che definisce un oggetto Noleggio.
 * Tale classe si interfaccia con la tabella Noleggio del database.
 * @author AlessandroBevilacqua.
 */
public class Noleggio 
{
	private int codiceNoleggio;
	private float costo;
	private float penale;
	private LocalDate dataInizio;
	private LocalDate dataFine;
	private boolean skipper;
	private String codiceCliente;
	private String codiceBarca;
	
	/**
	 * Metodo costruttore.
	 * @param codiceCliente ovvero codice fiscale del cliente che effettua il noleggio.
	 * @param codiceBarca ovvero numero serie della barca noleggiata.
	 * @param costo del noleggio da creare.
	 * @param dataInizio del noleggio da creare.
	 * @param skipper valore booleano che specifica se il noleggio da creare prevede o meno uno skipper.
	 */
	public Noleggio(String codiceCliente, String codiceBarca, float costo, LocalDate dataInizio, boolean skipper)
	{
		this.codiceCliente=codiceCliente;
		this.codiceBarca=codiceBarca;
		this.setCosto(costo);
		this.setDataInizio(dataInizio);
		this.setSkipper(skipper);
		this.codiceNoleggio=calcolaCodiceNoleggio();
	}
	
	/**
	 * Calcola in automatico il codice del noleggio.
	 * Tale calcolo fa in modo che il codice sia esattamente il successivo del codiceNoleggio piu' grande presente nel database (parte da 1).
	 * In questo modo ogni volta che si crea un nuovo noleggio mediante l'applicativo si avrà un noleggio diverso da tutti gli altri.
	 * Lo scopo e' quello di avere memorizzati nel database dei noleggi che siano tutti distinti tra loro e i cui codici siano in ordine crescente.
	 * @return codice del noleggio.
	 */
	
	private int calcolaCodiceNoleggio()
	{
		String query = "Select MAX(codiceNoleggio) from Noleggio;";
		int codice=0;
		try 
		{
			PreparedStatement p = Main.c.prepareStatement(query);
			ResultSet r = p.executeQuery();
			r.next();
			codice = r.getInt(1);
			p.close();
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		codice++;
		return codice;
	}
	
	/**
	 * Restituisce il costo del noleggio.
	 * @return costo.
	 */
	public float getCosto() 
	{
		return costo;
	}
	
	/**
	 * Permette di impostare il costo del noleggio.
	 * @param costo
	 */
	public void setCosto(float costo) 
	{
		this.costo = costo;
	}
	
	/**
	 * Restituisce il costo della penale del noleggio in caso di ritardo.
	 * @return penale.
	 */
	public float getPenale() 
	{
		return penale;
	}
	
	/**
	 * Permette di impostare il costo della penale del noleggio in caso di ritardo.
	 * @param penale.
	 */
	public void setPenale(float penale) 
	{
		this.penale = penale;
	}
	
	/**
	 * Restituisce la data di inizio noleggio.
	 * @return dataInizio.
	 */
	public LocalDate getDataInizio() 
	{
		return dataInizio;
	}
	
	/**
	 * Permette di impostare la data di inizio noleggio.
	 * @param dataInizio.
	 */
	public void setDataInizio(LocalDate dataInizio) 
	{
		this.dataInizio = dataInizio;
	}
	
	/**
	 * Restituisce la data di fine noleggio.
	 * @return dataFine.
	 */
	public LocalDate getDataFine() 
	{
		return dataFine;
	}
	
	
	/**
	 * Permette di impostare la data di fine noleggio.
	 * @param dataFine.
	 */
	public void setDataFine(LocalDate dataFine) 
	{
		this.dataFine = dataFine;
	}
	
	/**
	 * Restituisce il codice del noleggio.
	 * @return codiceNoleggio.
	 */
	public int getCodiceNoleggio() 
	{
		return codiceNoleggio;
	}
	@Override
	public int hashCode() 
	{
		return Objects.hash(codiceNoleggio);
	}
	
	/**
	 * Due oggetti Noleggio sono uguali se e soltanto se hanno lo stesso codiceNoleggio.
	 */
	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Noleggio other = (Noleggio) obj;
		return codiceNoleggio == other.codiceNoleggio;
	}

	/**
	 * Restituisce il valore booleano che indica se il noleggio è con oppure senza skipper.
	 * @return skipper.
	 */
	public boolean isSkipper() 
	{
		return skipper;
	}

	/**
	 * Permette di impostare il valore booleano che indica se il noleggio è con oppure senza skipper.
	 * @param skipper.
	 */
	public void setSkipper(boolean skipper) 
	{
		this.skipper = skipper;
	}

	/**
	 * Restituisce il codice fiscale del cliente che ha effettuato il noleggio.
	 * @return codiceCliente.
	 */
	public String getCodiceCliente() 
	{
		return codiceCliente;
	}
	
	/**
	 * Restituisce il numero di serie della barca noleggiata.
	 * @return codiceBarca.
	 */
	public String getCodiceBarca() 
	{
		return codiceBarca;
	}
}
