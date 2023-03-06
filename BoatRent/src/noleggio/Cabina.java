/**
 * @author AlessandroBevilacqua.
 * @version 1.0.
 */

package noleggio;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import application.Main;

/**
 * Classe che definisce un oggetto Cabina.
 * Gli oggetti Cabina possono essere aggiunti agli oggetti Barca.
 * @author AlessandroBevilacqua.
 */
public class Cabina 
{
	private int codiceCabina;
	private float area;
	private String modello;
	/**
	 * Metodo costruttore. Chiama calcolaCodiceCabina().
	 * @param area della cabina da creare.
	 * @param modello della cabina da creare.
	 */
	public Cabina(float area, String modello)
	{
		this.area=area;
		this.setModello(modello);
		this.codiceCabina=calcolaCodiceCabina();
	}
	
	/**
	 * Permette di impostare il codice della cabina.
	 * @param codiceCabina.
	 */
	public void setCodiceCabina(int codiceCabina)
	{
		this.codiceCabina=codiceCabina;
	}
	
	/**
	 * Calcola in automatico il codice della cabina.
	 * Tale calcolo fa in modo che il codice sia esattamente il successivo del codiceCabina piu' grande presente nel database (si parte da 1).
	 * In questo modo ogni volta che si crea una nuova cabina mediante l'applicativo si avrà una cabina diversa da tutte le altre.
	 * Lo scopo e' quello di avere memorizzate nel database delle cabine che siano tutte distinte tra loro e i cui codici siano in ordine crescente.
	 * @return codice della cabina.
	 */
	private int calcolaCodiceCabina()
	{
		String query = "Select MAX(codiceCabina) from Cabina;";
		int codice=0;
		try 
		{
			PreparedStatement p = Main.c.prepareStatement(query);
			ResultSet r = p.executeQuery();
			r.next();
			codice = r.getInt(1);
			p.close();
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		codice++;
		return codice;
	}
	
	
	/**
	 * Restituisce il codice della cabina.
	 * @return codice.
	 */
	public int getCodiceCabina()
	{
		return this.codiceCabina;
	}
	
	/**
	 * Restituisce l'area della cabina.
	 * @return area.
	 */
	public float getArea() 
	{
		return area;
	}
	
	/**
	 * Permette di impostare l'area della cabina.
	 * @param area.
	 */
	public void setArea(float area) 
	{
		this.area = area;
	}
	
	
	
	@Override
	public int hashCode() 
	{
		return Objects.hash(codiceCabina);
	}
	
	/**
	 * Due oggetti Cabina sono uguali se e soltanto se hanno lo stesso codiceCabina.
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
		Cabina other = (Cabina) obj;
		return codiceCabina == other.codiceCabina;
	}

	/**
	 * Restituisce il modello della cabina.
	 * @return modello.
	 */
	public String getModello() 
	{
		return modello;
	}

	/**
	 * Permette di impostare il modello della cabina.
	 * @param modello.
	 */
	public void setModello(String modello) 
	{
		this.modello = modello;
	}
}
