/**
 * @author AlessandroBevilacqua
 * @version 1.0.
 */

package noleggio;


/**
 * Classe che definisce un oggetto BarcaAvanzato. 
 * Eredita dalla classe Barca e aggiunge due bagni e una cabina (obbligatori).
 * @author AlessandroBevilacqua
 */
public class BarcaAvanzato extends Barca
{
	private Bagno bagno1;
	private Bagno bagno2;
	private Cabina cabina;
	/**
	 * Costruttore di una barca di tipo avanzato (possiede due bagni e una cabina).
	 * @param numeroSerie della barca da creare (stringa di 12 caratteri numerici).
	 * @param modello della barca da creare.
	 * @param bagno1 primo bagno da aggiungere alla barca da creare.
	 * @param bagno2 secondo bagno da aggiungere alla barca da creare.
	 * @param cabina cabina da aggiungere alla barca da creare.
	 */
	public BarcaAvanzato(String numeroSerie, String modello, Bagno bagno1, Bagno bagno2, Cabina cabina) 
	{
		super(numeroSerie, modello, TipologiaBarca.Avanzato);
		this.bagno1=bagno1;
		this.bagno2=bagno2;
		this.cabina=cabina;
	}

	/**
	 * Restituisce il primo bagno della barca di tipo Avanzato.
	 * @return bagno1.
	 */
	public Bagno getBagno1() 
	{
		return bagno1;
	}

	/**
	 * Permette di impostare il primo bagno della barca di tipo Avanzato.
	 * @param bagno1.
	 */
	public void setBagno1(Bagno bagno1) 
	{
		this.bagno1 = bagno1;
	}
	
	
	/**
	 * Restituisce il secondo bagno della barca di tipo Avanzato.
	 * @return bagno2.
	 */
	public Bagno getBagno2() 
	{
		return bagno2;
	}

	/**
	 * Permette di impostare il secondo bagno della barca di tipo Avanzato.
	 * @param bagno2.
	 */
	public void setBagno2(Bagno bagno2) 
	{
		this.bagno2 = bagno2;
	}

	/**
	 * Restituisce la cabina della barca di tipo Avanzato.
	 * @return
	 */
	public Cabina getCabina() 
	{
		return cabina;
	}

	
	/**
	 * Permette di impostare la cabina della barca di tipo Avanzato.
	 * @param cabina.
	 */
	public void setCabina(Cabina cabina) 
	{
		this.cabina = cabina;
	}
}
