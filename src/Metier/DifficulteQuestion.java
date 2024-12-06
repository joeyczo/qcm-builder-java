package Metier;

public enum DifficulteQuestion 
{
	TRESFACILE("#82CE84"),
	FACILE	  ("#737EC9"),
	MOYEN	  ("#E6415B"),
	DIFFICILE ("#827D7C");

	private String libelle;

	DifficulteQuestion(String lib){this.libelle = lib;}
}