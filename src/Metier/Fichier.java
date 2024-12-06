package Metier;

public class Fichier {
	private String lienFic;
	private String typeFic;

	public Fichier(String lien, String type)
	{
		this.lienFic = lien;
		this.typeFic = type;
	}

	public String getLienFichier(){return this.lienFic;}
	public String getTypeFichier(){return this.typeFic;}
}
