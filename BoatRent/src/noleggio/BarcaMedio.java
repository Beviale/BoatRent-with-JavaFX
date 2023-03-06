/**
 * @author AlessandroBevilacqua
 * @version 1.0.
 */

package noleggio;

/**
 * Classe che definisce un oggetto BarcaMedio.
 * Eredita dalla classe astratta Barca e aggiunge un bagno (obbligatorio).
 * @author AlessandroBevilacqua.
 */
public class BarcaMedio extends Barca
{
	private Bagno bagno;
	/**
	 * Metodo costruttore di una barca di tipo medio (possiede un solo bagno e nessuna cabina).
	 * @param numeroSerie della barca da creare (stringa di 12 caratteri numerici).
	 * @param modello della barca da creare.
	 * @param bagno da aggiungere alla barca da creare.
	 */
	public BarcaMedio(String numeroSerie, String modello, Bagno bagno) 
	{
		super(numeroSerie, modello, TipologiaBarca.Medio);
		this.setBagno(bagno);
	}

	/**
	 * Restituisce il bagno della barca di tipo medio.
	 * @return bagno.
	 */
	public Bagno getBagno() 
	{
		return bagno;
	}

	/**
	 * Permette di impostare il bagno della barca di tipo medio.
	 * @param bagno.
	 */
	public void setBagno(Bagno bagno) 
	{
		this.bagno = bagno;
	}
}
