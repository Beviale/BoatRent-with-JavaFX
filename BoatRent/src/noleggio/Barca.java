/**
 * @author AlessandroBevilacqua
 * @version 1.0.
 */

package noleggio;
import java.util.Objects;


/**
 * Classe astratta che definisce una Barca generica (senza specificarne il tipo).
 * @author AlessandroBevilacqua.
 */
public abstract class Barca 
{
	private String numeroSerie;
	private static String Marca="Marca";
	private String modello;
	private TipologiaBarca tipo;
	
	
	/**
	 * Metodo costruttore.
	 * @param numeroSerie della barca da creare (stringa di 12 caratteri numerici).
	 * @param modello della barca da creare.
	 * @param tipo della barca da creare.
	 */
	public Barca(String numeroSerie, String modello, TipologiaBarca tipo)
	{
		this.numeroSerie=numeroSerie;
		this.modello=modello;
		this.tipo=tipo;
	}
	
	/**
	 * Restituisce il numeroSerie della barca (stringa di 12 caratteri numerici).
	 * @return numeroSerie.
	 */
	public String getNumeroSerie() 
	{
		return numeroSerie;
	}
	
	/**
	 * Restituisce la marca della barca.
	 * @return marca.
	 */
	public static String getMarca() 
	{
		return Marca;
	}
	
	/**
	 * Permette di impostare la marca della barca.
	 * @param marca da impostare.
	 */
	public static void setMarca(String marca) 
	{
		Marca = marca;
	}
	
	/**
	 * Permette di impostare il modello della barca.
	 * @param modello da impostare.
	 */
	public void setModello(String modello) 
	{
		this.modello = modello;
	}
	
	/**
	 * Restituisce il modello della barca.
	 * @return modello della barca.
	 */
	public String getModello()
	{
		return this.modello;
	}
	@Override
	public int hashCode() 
	{
		return Objects.hash(numeroSerie);
	}
	
	/**
	 * Due oggetti Barca sono uguali se e soltanto se hanno lo stesso numeroSerie.
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
		Barca other = (Barca) obj;
		return Objects.equals(numeroSerie, other.numeroSerie);
	}
	
	/**
	 * Restituisce il tipo di barca.
	 * @return barca.
	 */
	public TipologiaBarca getTipo() 
	{
		return tipo;
	}
}
