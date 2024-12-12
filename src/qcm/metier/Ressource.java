package qcm.metier;

import java.util.ArrayList;
import java.util.List;

public class Ressource 
{
	private String nom;
	private List<Notion> alNotion;
	private String intitule; //Ex : r1.01

	public Ressource(String nom)
	{
		this.nom      = nom;
		this.alNotion = new ArrayList<Notion>();
	}

	public String getNom() {return nom;}
	public String getIntitule() {return intitule;}

	public void setNom(String nom) {this.nom = nom;}
	public void setIntitule(String intitule) {this.intitule = intitule;}

	public List<Notion> getAlNotion() {return alNotion;}

	public void ajouterNotion(Notion n) {

		this.alNotion.add(n);

	}



	public boolean equals(Ressource r) {
		return this.nom.equals(r.getNom());
	}
}