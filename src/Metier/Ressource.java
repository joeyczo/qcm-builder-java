package Metier;

import java.util.ArrayList;

public class Ressource 
{
	private String nom;
	private ArrayList<Notion> alNotion;

	public Ressource(String nom)
	{
		this.nom      = nom;
		this.alNotion = new ArrayList<Notion>();
	}

	public String getNom() {return nom;}

	public void setNom(String nom) {this.nom = nom;}

	public ArrayList<Notion> getAlNotion() {return alNotion;}
}