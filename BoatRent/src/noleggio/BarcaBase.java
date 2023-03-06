/**
 * @author AlessandroBevilacqua.
 * @version 1.0.
 */
package noleggio;


/**
 * Classe che definisce un oggetto BarcaBase.
 * Eredita dalla classe astratta Barca e non aggiunge alcun metodo o attributo.
 * @author AlessandroBevilacqua.
 */
public class BarcaBase extends Barca
{
/**
 * Metodo costruttore di una barca di tipo base (non possiede ne' bagni ne' cabine).
 * @param numeroSerie della barca da creare (stringa di 12 caratteri numerici).
 * @param modello della barca da creare.
 */
	public BarcaBase(String numeroSerie, String modello) 
	{
		super(numeroSerie, modello, TipologiaBarca.Base);
	}

}
