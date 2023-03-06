/**
 * @author AlessandroBevilacqua.
 * @version 1.0.
 */

package noleggio;
import java.time.LocalDate;


/**
 * Classe che riporta alcune informazioni su una barca noleggiata.
 * Può essere utilizzata con il database per tutte quelle operazioni di join tra la tabella Barca e la tabella Noleggio.
 * @author AlessandroBevilacqua.
 *
 */
public class BarcaNoleggio 
{
	private String numeroSerie;
	private String marca;
	private String modello;
	private TipologiaBarca tipo;
	private LocalDate dataInizioNoleggio;
	/**
	 * Metodo costruttore per le barche noleggiate (di qualunque tipo).
	 * @param numeroSerie della barca noleggiata (stringa numerica di 12 caratteri).
	 * @param marca della barca noleggiata.
	 * @param modello della barca noleggiata.
	 * @param tipo della barca noleggiata.
	 * @param dataInizioNoleggio della barca noleggiata.
	 */
	public BarcaNoleggio(String numeroSerie, String marca, String modello, TipologiaBarca tipo, LocalDate dataInizioNoleggio)
	{
		this.numeroSerie=numeroSerie;
		this.marca=marca;
		this.modello=modello;
		this.tipo=tipo;
		this.dataInizioNoleggio=dataInizioNoleggio;
	}
	
	/**
	 * Restituisce il numeroSerie della barca noleggiata.
	 * @return numeroSerie.
	 */
	public String getNumeroSerie()
	{
		return numeroSerie;
	}
	
	/**
	 * Restituisce il tipo della barca noleggiata.
	 * @return tipo.
	 */
	public TipologiaBarca getTipo() 
	{
		return tipo;
	}
	
	/**
	 * Restituisce la data di inizio noleggio della barca noleggiata.
	 * @return dataInizioNoleggio.
	 */
	public LocalDate getDataInizioNoleggio() 
	{
		return dataInizioNoleggio;
	}
	
	/**
	 * Restituisce la marca della barca noleggiata.
	 * @return marca.
	 */
	public String getMarca() 
	{
		return marca;
	}
	
	
	/**
	 * Restituisce il modello della barca noleggiata.
	 * @return modello.
	 */
	public String getModello() 
	{
		return modello;
	}
}
