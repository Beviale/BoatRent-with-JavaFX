/**
 * @author AlessandroBevilacqua
 * @version 1.0.
 */
package noleggio;
import java.time.LocalDate;

/**
 * Classe che riporta tutte le informazioni su una barca noleggiata più la data di inizio noleggio.
 * Può essere utilizzata con il database per tutte quelle operazioni di join tra la tabella Barca e la tabella Noleggio.
 * @author AlessandroBevilacqua
 */
public class BarcaAllInformazioni 
{
	private String numeroSerie;
	private String marca;
	private String modello;
	private TipologiaBarca tipo;
	private Integer codiceBagno1=null;
	private Integer codiceCabina=null;
	private Integer codiceBagno2=null;
	private LocalDate dataInizioNoleggio;
	
	/**
	 * Metodo costruttore per le barche base noleggiate.
	 * @param numeroSerie della barca noleggiata.
	 * @param marca della barca noleggiata.
	 * @param modello della barca noleggiata.
	 * @param tipo della barca noleggiata.
	 * @param dataInizioNoleggio della barca noleggiata.
	 */
	public BarcaAllInformazioni(String numeroSerie, String marca, String modello, TipologiaBarca tipo, LocalDate dataInizioNoleggio)
	{
		this.numeroSerie=numeroSerie;
		this.marca=marca;
		this.modello=modello;
		this.tipo=tipo;
		this.dataInizioNoleggio=dataInizioNoleggio;
	}
	
	
	/**
	 * Metodo costruttore per le barche medie noleggiate.
	 * @param numeroSerie della barca noleggiata.
	 * @param marca della barca noleggiata.
	 * @param modello della barca noleggiata.
	 * @param tipo della barca noleggiata.
	 * @param codiceBagno1 della barca noleggiata.
	 * @param dataInizioNoleggio della barca noleggiata.
	 */
	public BarcaAllInformazioni(String numeroSerie, String marca, String modello, TipologiaBarca tipo, Integer codiceBagno1, LocalDate dataInizioNoleggio)
	{
		this.numeroSerie=numeroSerie;
		this.marca=marca;
		this.modello=modello;
		this.tipo=tipo;
		this.codiceBagno1=codiceBagno1;
		this.dataInizioNoleggio=dataInizioNoleggio;
	}
	
	/**
	 * Metodo costruttore per le barche avanzate noleggiate.
	 * @param numeroSerie della barca noleggiata.
	 * @param marca della barca noleggiata.
	 * @param modello della barca noleggiata.
	 * @param tipo della barca noleggiata.
	 * @param codiceBagno1 della barca noleggiata.
	 * @param codiceCabina della barca noleggiata.
	 * @param codiceBagno2 della barca noleggiata.
	 * @param dataInizioNoleggio della barca noleggiata.
	 */
	public BarcaAllInformazioni(String numeroSerie, String marca, String modello, TipologiaBarca tipo, Integer codiceBagno1, Integer codiceCabina, Integer codiceBagno2, LocalDate dataInizioNoleggio)
	{
		this.numeroSerie=numeroSerie;
		this.marca=marca;
		this.modello=modello;
		this.tipo=tipo;
		this.codiceBagno1=codiceBagno1;
		this.codiceCabina=codiceCabina;
		this.codiceBagno2=codiceBagno2;
		this.dataInizioNoleggio=dataInizioNoleggio;
	}
	
	
	/**
	 * Restituisce il numeroSerie della barca.
	 * @return numeroSerie.
	 */
	public String getNumeroSerie()
	{
		return numeroSerie;
	}
	
	/**
	 * Restituisce il tipo di barca.
	 * @return tipo di barca.
	 */
	public TipologiaBarca getTipo() 
	{
		return tipo;
	}
	
	/**
	 * Restituisce la data di inizio noleggio della barca.
	 * @return dataInizioNoleggio.
	 */
	public LocalDate getDataInizioNoleggio() 
	{
		return dataInizioNoleggio;
	}
	
	/**
	 * Restituisce la marca della barca.
	 * @return marca.
	 */
	public String getMarca() 
	{
		return marca;
	}
	
	/**
	 * Restituisce il modello della barca.
	 * @return modello.
	 */
	public String getModello() 
	{
		return modello;
	}
	
	/**
	 * Restituisce il codice del primo bagno.
	 * @return codiceBagno1.
	 */
	public Integer getCodiceBagno1() 
	{
		return codiceBagno1;
	}
	
	/**
	 * Restituisce il codice della cabina.
	 * @return codiceCabina.
	 */
	public Integer getCodiceCabina() 
	{
		return codiceCabina;
	}

	/**
	 * Restituisce il codice del secondo bagno.
	 * @return codiceBagno2.
	 */
	public Integer getCodiceBagno2() 
	{
		return codiceBagno2;
	}

	@Override
	public String toString() 
	{
		return "BarcaAllInformazioni [numeroSerie=" + numeroSerie + ", marca=" + marca + ", modello=" + modello
				+ ", tipo=" + tipo + ", codiceBagno1=" + codiceBagno1 + ", codiceCabina=" + codiceCabina
				+ ", codiceBagno2=" + codiceBagno2 + ", dataInizioNoleggio=" + dataInizioNoleggio + "]";
	}
}
