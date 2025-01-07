package qcm.metier;

import java.util.ArrayList;
import java.util.List;

public class QCMReponse implements Reponse
{
	private List<QCMReponseItem> alReponses;
	private String               txtExplication;

	public QCMReponse() {

		this.alReponses	    = new ArrayList<QCMReponseItem>();
		this.txtExplication	= null;

	}

	/**
	 * Retourner l'ensemble de la liste des réponses
	 * @return La liste complète des items des réponses
	 */
	public List<QCMReponseItem> getReponsesItem(){ return this.alReponses; }

	/**
	 * Récupérer le nombre d'items présent dans la liste des réponses
	 * @return Le nombre d'objets
	 */
	public int getNbReponse() { return this.alReponses.size(); }

	/**
	 * Récupérer un item de la liste des réponses à l'indince i
	 * @param indice Indince pour récupérer la réponse
	 * @return L'objet, sinon null si il n'existe pas
	 */
	public QCMReponseItem getReponseItem(int indice) {

		if (indice < 0 || indice >= this.getNbReponse()) return null;

		return this.alReponses.get(indice);
	}

	public void   ajouterItem            (QCMReponseItem item) {        this.alReponses.add(item); }
	public void   ajouterTexteExplication(String s)            {        this.txtExplication = s;   }
	public String getTexteExplication    ()                    { return this.txtExplication;       }

}