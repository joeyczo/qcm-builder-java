package qcm.metier;

import java.util.ArrayList;
import java.util.List;

public class EliminationReponse implements Reponse {

	private List<EliminationReponseItem> 		alReponses;
	private String								txtExplication;

	public EliminationReponse() {
		this.txtExplication = null;
		this.alReponses 	= new ArrayList<>();
	}


	/**
	 * Ajouter un nouvel item de réponse à la liste des réponses
	 * @param e L'objet à ajouter
	 */
	public void ajouterReponseItem (EliminationReponseItem e) {
		this.alReponses.add(e);
	}
	

	/**
	 * Récupérer le nombre d'item présent dans la liste
	 * @return Le nombre de réponses disponibles
	 */
	public int getNbReponse () {
		return this.alReponses.size();
	}

	/**
	 * Récuérer la liste entière des items de réponses
	 * @return La liste complète avec toutes les réponses
	 */
	public List<EliminationReponseItem> getReponsesItems() {
		return this.alReponses;
	}

	/**
	 * Récupérer un item de réponse à l'idnice i
	 * @param indice Indice afin de récupérer l'item
	 * @return L'objet EliminationReponseItem, sinon null s'il n'existe pas
	 */
	public EliminationReponseItem getReponseItem ( int indice ) {

		if (indice < 0 || indice >= this.getNbReponse()) return null;

		return this.alReponses.get(indice);

	}

	public void ajouterTexteExplication(String s) {
		this.txtExplication = s;
	}

	public String getTexteExplication() {
		return this.txtExplication;
	}
}