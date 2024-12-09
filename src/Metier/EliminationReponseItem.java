package Metier;

public class EliminationReponseItem {
	
	private String texte;
	private int ordreSuppression;
	private float ptsSuppression;

	/**
	 * Méthode pour obtenir le texte de la réponse.
	 * @return le texte de la réponse.
	 */
	public String getTexte() {
		return texte;
	}

	/**
	 * Méthode pour obtenir l'ordre de suppression de la réponse.
	 * @return l'ordre de suppression de la réponse.
	 */
	public int getOrdre() {
		return ordreSuppression;
	}

	/**
	 * Méthode pour obtenir le nombre de points de suppression de la réponse.
	 * @return le nombre de points de suppression de la réponse.
	 */
	public float getPtsSuppression() {
		return ptsSuppression;
	}

	/**
	 * Méthode pour définir le texte de la réponse.
	 * @param texte le texte de la réponse.
	 */
	public void setTexte(String texte) {
		this.texte = texte;
	}

	/**
	 * Méthode pour définir l'ordre de suppression de la réponse.
	 * @param ordre l'ordre de suppression de la réponse.
	 */
	public void setOrdre(int ordre) {
		this.ordreSuppression = ordre;
	}

	/**
	 * Méthode pour définir le nombre de points de suppression de la réponse.
	 * @param ptsSuppression le nombre de points de suppression de la réponse.
	 */
	public void setPtsSuppression(float ptsSuppression) {
		this.ptsSuppression = ptsSuppression;
	}
}