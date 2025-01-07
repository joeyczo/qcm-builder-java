package qcm.metier;

public class EliminationReponseItem {
	
	private String  texte;
	private int     ordreSuppression;
	private float   ptsSuppression;
	private	boolean bonneReponse;

	/*  --------------------------  */
	/*         CONSTRUCTEUR         */
	/*  --------------------------  */

	public EliminationReponseItem(String texte, int ordreSuppression, float ptsSuppression, boolean bonneReponse) {

		this.texte             = texte;
		this.ordreSuppression  = ordreSuppression;
		this.ptsSuppression    = ptsSuppression;
		this.bonneReponse      = bonneReponse;

	}

	/*  --------------------------  */
	/*           GETTERS            */
	/*  --------------------------  */

	public String getTexte           () { return this.texte;            }
	public int    getOrdreSuppression() { return this.ordreSuppression; }
	public float  getPtsSuppression  () { return this.ptsSuppression;   }


	/*  --------------------------  */
	/*           SETTERS            */
	/*  --------------------------  */

	public void setTexte(String texte) { this.texte = texte; }


	/*  --------------------------  */
	/*        AUTRE METHODE         */
	/*  --------------------------  */

	public boolean isBonneReponse() { return this.bonneReponse; }

}