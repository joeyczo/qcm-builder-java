package qcm.metier;

import java.awt.*;

public enum DifficulteQuestion
{
	TRESFACILE(new Color(130, 206, 132), "TF"),
	FACILE	  (new Color(115, 126, 201), "F"),
	MOYEN	  (new Color(230, 65, 91), "M"),
	DIFFICILE (new Color(130, 125, 125), "D");

	private Color couleur;
	private String texte;

	DifficulteQuestion (Color c, String t) {
		this.couleur 	= c;
		this.texte 		= t;
	}

	public Color getCouleur()	{	return this.couleur;	}
	public String getTexte  ()	{	return this.texte;  	}
}