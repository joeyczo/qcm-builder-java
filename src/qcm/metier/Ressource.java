package qcm.metier;

import java.util.ArrayList;
import java.util.List;

public class Ressource 
{
	private String nom;
	private List<Notion> alNotion;

	public Ressource(String nom)
	{
		this.nom      = nom;
		this.alNotion = new ArrayList<Notion>();
	}

	public String getNom() {return nom;}

	public void setNom(String nom) {this.nom = nom;}

	public List<Notion> getAlNotion() {return alNotion;}

	public void ajouterNotion(Notion n) {

		this.alNotion.add(n);

	}

}