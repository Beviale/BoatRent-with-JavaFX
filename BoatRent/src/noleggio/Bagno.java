/**
 * @author AlessandroBevilacqua
 * @version 1.0.
 */

package noleggio;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import application.Main;

/**
 * Classe che definisce un oggetto Bagno. Gli oggetti Bagno possono essere aggiunti agli oggetti Barca.
 * @author AlessandroBevilacqua
 */
public class Bagno 
{
	private int codiceBagno;
	private float area;
	private String modello;
	
	
	/**
	 * Metodo costruttore. Chiama calcolaCodiceBagno().
	 * @param area del bagno da creare.
	 * @param modello del bagno da creare.
	 */
	public Bagno(float area, String modello)
	{
		this.setArea(area);
		this.setModello(modello);
		this.codiceBagno=calcolaCodiceBagno();
	}
	
	
	
	/**
	 * Permette di impostare il codice del bagno. 
	 * @param codice da impostare.
	 */
	public void setCodiceBagno(int codiceBagno)
	{
		this.codiceBagno=codiceBagno;
	}
	
	/**
	 * Calcola in automatico il codice del bagno.
	 * Tale calcolo fa in modo che il codice sia esattamente il successivo del codiceBagno piu' grande presente nel database (parte da 1).
	 * In questo modo ogni volta che si crea un nuovo bagno mediante l'applicativo si avrà un bagno diverso da tutti gli altri.
	 * Lo scopo e' quello di avere memorizzati nel database dei bagni che siano tutti distinti tra loro e i cui codici siano in ordine crescente.
	 * @return codice del bagno.
	 */
	
	private int calcolaCodiceBagno()
	{
		String query = "Select MAX(codiceBagno) from Bagno;";
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
	 * Restituisce il codice del bagno
	 * @return codice del bagno.
	 */
	public int getCodice()
	{
		return this.codiceBagno;
	}
	
	/**
	 * Restituisce l'area del bagno.
	 * @return area.
	 */
	public float getArea() 
	{
		return area;
	}
	
	
	/**
	 * Permette di impostare l'area del bagno.
	 * @param area da impostare.
	 */
	public void setArea(float area) 
	{
		this.area = area;
	}
	
	/**
	 * Restituisce il modello del bagno.
	 * @return modello del bagno.
	 */
	public String getModello() 
	{
		return modello;
	}
	
	
	/**
	 * Permette di impostare il modello del bagno.
	 * @param modello da impostare.
	 */
	public void setModello(String modello) 
	{
		this.modello = modello;
	}
	
	

	@Override
	public int hashCode() 
	{
		return Objects.hash(codiceBagno);
	}
	
	/**
	 * Due oggetti Bagno sono uguali se e soltanto se hanno lo stesso codiceBagno.
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
		Bagno other = (Bagno) obj;
		return codiceBagno == other.codiceBagno;
	}
}
