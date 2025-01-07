package qcm.metier;

import java.awt.*;

public enum DifficulteQuestion {

	TRESFACILE(new Color(130, 206, 132), "TF", "rgb(130,206,132)"),
	FACILE    (new Color(115, 126, 201),  "F", "rgb(115,126,201)"),
	MOYEN     (new Color(230,  65,  91),  "M", "rgb(230,65,91)"  ),
	DIFFICILE (new Color(130, 125, 125),  "D", "rgb(130,125,125)");

	private Color  couleur;
	private String texte;
	private String couleurHtml;

	DifficulteQuestion (Color c, String t, String couleurHtml) {

		this.couleur     = c;
		this.texte       = t;
		this.couleurHtml = couleurHtml;

	}

	public Color  getCouleur    () { return this.couleur;     }
	public String getTexte      () { return this.texte;       }
	public String getCouleurHtml() { return this.couleurHtml; }
}