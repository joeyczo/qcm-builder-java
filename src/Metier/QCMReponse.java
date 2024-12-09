package Metier;

import java.util.ArrayList;

public class QCMReponse implements Reponse
{
	private ArrayList<QCMReponseItem> alReponses;

	public QCMReponse(){this.alReponses=new ArrayList<QCMReponseItem>();}
	
	/**
	 * Méthode pour obtenir la liste des réponses.
	 * @return la liste des éléments de réponse de QCM.
	 */
	public ArrayList<QCMReponseItem> getReponses(){ return this.alReponses;}
}
