package qcm.metier;

public class EliminationReponseItem {
	
	private String 	texte;
	private int 	ordreSuppression;
	private float 	ptsSuppression;

	/*  --------------------------  */
	/*         CONSTRUCTEUR         */
	/*  --------------------------  */

	public EliminationReponseItem(String texte, int ordreSuppression, float ptsSuppression) {
		this.texte 				= texte;
		this.ordreSuppression 	= ordreSuppression;
		this.ptsSuppression 	= ptsSuppression;
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


	/*  --------------------------  */
	/*           SETTERS            */
	/*  --------------------------  */

	public void setOrdreSuppression(int ordreSuppression) {
		this.ordreSuppression = ordreSuppression;
	}

	public void setPtsSuppression(float ptsSuppression) {
		this.ptsSuppression = ptsSuppression;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}
}