package qcm.metier;

import java.util.ArrayList;
import java.util.List;

public class Ressource 
{
	private final int LIMITE = 35;

	private String       nom;
	private String       intitule; //Ex : r1.01
	private List<Notion> alNotion;

	public Ressource(String nom)
	{
		this.nom      = nom;
		this.alNotion = new ArrayList<Notion>();
	}


	/*---------------*/
	/*      GET      */
	/*---------------*/


	public String getNomCourt()
	{
		String nom = this.nom;

		if ( this.nom.length() >= LIMITE )
		{
			nom = nom.substring(0, LIMITE - 3);
			nom += "...";
		}

		return nom ;
	}

	public String       getNom()      { return nom; }
	public String       getIntitule() { return intitule; }
	public List<Notion> getAlNotion() { return alNotion; }


	/*---------------*/
	/*      SET      */
	/*---------------*/


	public void setNom     (String nom)      {this.nom      = nom;}
	public void setIntitule(String intitule) {this.intitule = intitule;}



	/*---------------*/
	/*     AUTRE     */
	/*---------------*/
	public void    ajouterNotion(Notion n)    { this.alNotion.add(n); }
	public boolean equals       (Ressource r) { return this.nom.equals(r.getNom()); }
}