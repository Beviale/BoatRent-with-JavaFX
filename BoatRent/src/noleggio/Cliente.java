/**
 * @author AlessandroBevilacqua.
 * @version 1.0.
 */
package noleggio;
import java.time.LocalDate;
import java.util.Objects;


/**
 * Classe che definisce un oggetto Cliente.
 * Il cliente è la persona fisica che puo' noleggiare una o piu' barche.
 * @author AlessandroBevilacqua
 */
public class Cliente 
{
	private String codiceFiscale;
	private String nome;
	private String cognome;
	private LocalDate dataNascita;
	private String sesso;
	private static int numMaxBarche=10;
	
	/**
	 * Metodo costruttore.
	 * @param codiceFiscale del cliente da creare (stringa alfanumerica di 16 caratteri tutti scritti in maiuscolo).
	 * @param nome del cliente da creare.
	 * @param cognome del cliente da creare.
	 * @param dataNascita del cliente da creare.
	 * @param sesso del cliente da creare.
	 */
	public Cliente(String codiceFiscale,String nome, String cognome, LocalDate dataNascita, String sesso)
	{
		this.codiceFiscale=codiceFiscale;
		this.nome=nome;
		this.cognome=cognome;
		this.setDataNascita(dataNascita);
		this.sesso=sesso;
	}
	
	/**
	 * Restituisce il codice fiscale del cliente (stringa alfanumerica di 16 caratteri).
	 * @return codiceFiscale.
	 */
	public String getCodiceFiscale() 
	{
		return codiceFiscale;
	}
	
	/**
	 * Permette di impostare il codice fiscale del cliente (stringa alfanumerica di 16 caratteri tutti scritti in maiuscolo).
	 * @param codiceFiscale.
	 */
	public void setCodiceFiscale(String codiceFiscale) 
	{
		this.codiceFiscale = codiceFiscale;
	}
	
	/**
	 * Restituisce il nome del cliente.
	 * @return nome.
	 */
	public String getNome() 
	{
		return nome;
	}
	
	/**
	 * Permette di impostare il nome del cliente.
	 * @param nome.
	 */
	public void setNome(String nome) 
	{
		this.nome = nome;
	}
	
	/**
	 * Restituisce il cognome del cliente.
	 * @return cognome.
	 */
	public String getCognome() 
	{
		return cognome;
	}
	
	/**
	 * Permette di impostare il cognome del cliente.
	 * @param cognome.
	 */
	public void setCognome(String cognome) 
	{
		this.cognome = cognome;
	}
	
	
	@Override
	public int hashCode() 
	{
		return Objects.hash(codiceFiscale);
	}
	
	/**
	 * Due oggetti Cliente sono uguali se e soltanto se hanno lo stesso codiceFiscale.
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
		Cliente other = (Cliente) obj;
		return Objects.equals(codiceFiscale, other.codiceFiscale);
	}

	
	/**
	 * Restituisce il numero massimo di barche che possonoe essere noleggiate dal cliente.
	 * @return numMaxNoleggi.
	 */
	public static int getNumMaxBarche() 
	{
		return numMaxBarche;
	}

	/**
	 * Restituisce la data di nascita del cliente.
	 * @return dataNascita.
	 */
	public LocalDate getDataNascita() 
	{
		return dataNascita;
	}

	/**
	 * Permette di impostare la data di nascita del cliente.
	 * @param dataNascita.
	 */
	public void setDataNascita(LocalDate dataNascita) 
	{
		this.dataNascita = dataNascita;
	}

	/**
	 * Restituisce il sesso del cliente.
	 * @return sesso.
	 */
	public String getSesso() 
	{
		return sesso;
	}
}
