package Metier;
import java.util.*;

public class AssociationReponse implements Reponse
{
	private ArrayList<AssociationReponseItem> alReponses;

	/**
	 * Constructeur par défaut qui initialise la liste des réponses.
	 */
	public AssociationReponse() {
		this.alReponses = new ArrayList<AssociationReponseItem>();
	}

	/**
	 * Méthode pour obtenir la liste des réponses.
	 * @return la liste des éléments de réponse d'association.
	 */
	public ArrayList<AssociationReponseItem> getReponses() {
		return this.alReponses;
	}
}
