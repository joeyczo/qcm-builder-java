package Metier;

public class Fichier {
	private String lienFic;
	private String typeFic;

	public Fichier(String lien, String type)
	{
		this.lienFic = lien;
		this.typeFic = type;
	}

	/**
	 * Méthode pour obtenir le lien du fichier.
	 * @return le lien du fichier.
	 */
	public String getLienFichier(){return this.lienFic;}

	/**
	 * Méthode pour obtenir le type du fichier.
	 * @return le type du fichier.
	 */
	public String getTypeFichier(){return this.typeFic;}
}
