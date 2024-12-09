package Metier;

public enum DifficulteQuestion 
{
	TRESFACILE("#82CE84"),
	FACILE	  ("#737EC9"),
	MOYEN	  ("#E6415B"),
	DIFFICILE ("#827D7C");

	private String couleur;

	DifficulteQuestion(String c){this.couleur = c;}

	/**
	 * Méthode pour obtenir la couleur de la difficulté.
	 * @return la couleur de la difficulté en hexadécimal.
	 */
	public String getCouleur(){return this.couleur;}
}