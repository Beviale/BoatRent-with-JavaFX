/**
 * @author AlessandroBevilacqua.
 * @version 1.0.
 */
package noleggio;
import java.util.Comparator;

/**
 * Classe che implementa l'interfaccia Comparator.
 * Serve per comparare gli oggetti BarcaNoleggio.
 * Fornisce un ordinamento in base al tipo di barca (medio, base e poi avanzato).
 * In caso di parità di tipo, l'ordinamento si basa sulla data di inizio noleggio (dalla meno recente alla più recente).
 * @author AlessandroBevilacqua.
 */
public class CompareByTipoAndDate implements Comparator<BarcaNoleggio> 
{

	public CompareByTipoAndDate() 
	{
		
	}

	@Override
	public int compare(BarcaNoleggio first, BarcaNoleggio second) 
	{
		if(first.getTipo().equals(second.getTipo())==false)
		{
			return first.getTipo().compareTo(second.getTipo());
		}
		return first.getDataInizioNoleggio().compareTo(second.getDataInizioNoleggio());
	}
}
