/**
 * @author AlessandroBevilacqua.
 * @version 1.0.
 */
package noleggio;
import java.util.Comparator;

/**
 * Classe che implementa l'interfaccia Comparator.
 * Serve per comparare gli oggetti BarcaAllInformazioni.
 * Fornisce un ordinamento in base alla data di inizio noleggio (dalla meno recente alla piu' recente).
 * @author AlessandroBevilacqua.
 */
public class CompareByDate implements Comparator<BarcaAllInformazioni> 
{

	public CompareByDate() 
	{
	}

	@Override
	public int compare(BarcaAllInformazioni first, BarcaAllInformazioni second) 
	{
		return first.getDataInizioNoleggio().compareTo(second.getDataInizioNoleggio());
	}

}
