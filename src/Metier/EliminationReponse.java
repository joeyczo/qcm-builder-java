package Metier;

import java.util.ArrayList;

public class EliminationReponse implements Reponse {

	private ArrayList<EliminationReponseItem> reponses;

	/**
	 * Méthode pour obtenir la liste des réponses.
	 * @return la liste des éléments de réponse d'élimination.
	 */
	public ArrayList<EliminationReponseItem> getReponses() {
		return reponses;
	}
}