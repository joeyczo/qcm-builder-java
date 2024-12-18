package qcm.metier;

public class EliminationReponseItem {
	
	private String 	texte;
	private int 	ordreSuppression;
	private float 	ptsSuppression;
	private	boolean	bonneReponse;

	/*  --------------------------  */
	/*         CONSTRUCTEUR         */
	/*  --------------------------  */

	public EliminationReponseItem(String texte, int ordreSuppression, float ptsSuppression, boolean bonneReponse) {
		this.texte 				= texte;
		this.ordreSuppression 	= ordreSuppression;
		this.ptsSuppression 	= ptsSuppression;
		this.bonneReponse		= bonneReponse;
	}

	/*  --------------------------  */
	/*           GETTERS            */
	/*  --------------------------  */

	public String getTexte() {
		return texte;
	}

	public int getOrdreSuppression() {
		return ordreSuppression;
	}

	public float getPtsSuppression() {
		return ptsSuppression;
	}

	public boolean isBonneReponse() {
		return this.bonneReponse;
	}


	/*  --------------------------  */
	/*           SETTERS            */
	/*  --------------------------  */

	public void setOrdreSuppression(int ordreSuppression) {
		this.ordreSuppression = ordreSuppression;
	}

	public void setPtsSuppression(float ptsSuppression) {
		this.ptsSuppression = ptsSuppression;
	}

	public void setBonneReponse ( boolean bonneReponse ) {
		this.bonneReponse = bonneReponse;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}
}