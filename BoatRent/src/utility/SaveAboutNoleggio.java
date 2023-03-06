/**
 * @author AlessandroBevilacqua.
 * @version 1.0.
 */
package utility;
import java.time.LocalDate;

/**
 * Classe che può essere utilizzata per mantenere momentaneamente salvate le informazioni relative ad un noleggio (escluso il codiceBarca).
 * E' costituita esclusivamente da attributi e metodi statici, quindi non ha senso di essere istanziata.
 * @author AlessandroBevilacqua.
 */
public class SaveAboutNoleggio 
{
	private static String codiceFiscale;
	private static float costo;
	private static float penale;
	private static LocalDate dataInizio;
	private static LocalDate dataFine;
	private static boolean skipper;

	/**
	 * Metodo costruttore. Attenzione: non ci sono sono ne' metodi ne' attributi d'istanza.
	 */
	public SaveAboutNoleggio() 
	{
	}
	/**
	 * Restituisce il codice fiscale del cliente che ha effettuato il noleggio.
	 * @return codiceFiscale.
	 */
	public static String getCodiceFiscale() 
	{
		return codiceFiscale;
	}

	/**
	 * Permette di impostare il codice fiscale del cliente che ha effettuato il noleggio.
	 * @param codiceFiscale.
	 */
	public static void setCodiceFiscale(String codiceFiscale1) 
	{
		codiceFiscale = codiceFiscale1;
	}

	/**
	 * Restituisce il costo del noleggio.
	 * @return costo.
	 */
	public static float getCosto() 
	{
		return costo;
	}

	/**
	 * Permette di impostare il costo del noleggio.
	 * @param costo.
	 */
	public static void setCosto(float costo1) 
	{
		costo = costo1;
	}

	/**
	 * Restituisce il costo della penale del noleggio in caso di riconsegna avvenuta in ritardo.
	 * @return penale.
	 */
	public static float getPenale() 
	{
		return penale;
	}

	/**
	 * Permette di impostare il costo della penale del noleggio in caso di riconsegna avvenuta in ritardo.
	 * @param penale.
	 */
	public static void setPenale(float penale1) 
	{
		penale = penale1;
	}

	/**
	 * Restituisce la data di inizio noleggio.
	 * @return dataInizio.
	 */
	public 	static LocalDate getDataInizio() 
	{
		return dataInizio;
	}

	/**
	 * Permette di impostare la data di inizio noleggio.
	 * @param dataInizio.
	 */
	public static void setDataInizio(LocalDate dataInizio1) 
	{
		dataInizio = dataInizio1;
	}

	/**
	 * Restituisce la data di fine noleggio.
	 * @return dataFine.
	 */
	public static LocalDate getDataFine() 
	{
		return dataFine;
	}

	/**
	 * Permette di impostare la data di fine noleggio.
	 * @param dataFine.
	 */
	public static void setDataFine(LocalDate dataFine1) 
	{
		dataFine = dataFine1;
	}

	/**
	 * Restituisce un valore booleano che indica se il noleggio e' con oppure senza skipper.
	 * @return skipper.
	 */
	public static boolean isSkipper() 
	{
		return skipper;
	}

	/**
	 * Permette di impostare un valore booleano che indica se il noleggio e' con oppure senza skipper.
	 * @param skipper.
	 */
	public static void setSkipper(boolean skipper1) 
	{
		skipper = skipper1;
	}
}

