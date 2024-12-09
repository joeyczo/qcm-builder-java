package Metier;

import java.util.ArrayList;
import java.util.List;

public class Question
{
	private String texteQuestion;
	private int    tempsReponse;

	private TypeQuestion typeQuestion;
	private Reponse reponse;
	private DifficulteQuestion difficulte;

	private List<Fichier> fichiers;
	
	public Question(String texteQuestion, int tempsReponse, TypeQuestion typeQuestion, Reponse reponse, DifficulteQuestion difficulte) {
		this.texteQuestion = texteQuestion;
		this.tempsReponse = tempsReponse;
		this.typeQuestion = typeQuestion;
		this.reponse = reponse;
		this.difficulte = difficulte;
		this.fichiers = new ArrayList<Fichier>();
	}

	/* ------------ */
	/*	 Getters 	*/
	/* ------------ */

	/**
	 * Méthode pour obtenir le texte de la question.
	 * @return le texte de la question.
	 */
	public String getTexteQuestion() {
		return this.texteQuestion;
	}

	/**
	 * Méthode pour obtenir le temps de réponse.
	 * @return le temps de réponse en secondes.
	 */
	public int getTempsReponse() {
		return this.tempsReponse;
	}

	/**
	 * Méthode pour obtenir la difficulté de la question.
	 * @return la difficulté de la question.
	 */
	public DifficulteQuestion getDifficulte() {
		return this.difficulte;
	}

	/**
	 * Méthode pour obtenir le type de la question.
	 * @return le type de la question.
	 */
	public TypeQuestion getTypeQuestion() {
		return this.typeQuestion;
	}

	/**
	 * Méthode pour obtenir la réponse à la question.
	 * @return la réponse à la question.
	 */
	public Reponse getReponse() {
		return this.reponse;
	}

	/**
	 * Méthode pour obtenir un fichier associé à la question.
	 * @param index l'index du fichier dans la liste.
	 * @return le fichier à l'index spécifié.
	 */
	public Fichier getFichier(int index) {
		return this.fichiers.get(index);
	}

	
	/* ------------ */
	/*	 Setters 	*/
	/* ------------ */

	/**
	 * Méthode pour définir le texte de la question.
	 * @param texteQuestion le nouveau texte de la question.
	 */
	public void setTexteQuestion(String texteQuestion) {
		this.texteQuestion = texteQuestion;
	}

	/**
	 * Méthode pour définir le temps de réponse.
	 * @param tempsReponse le nouveau temps de réponse en secondes.
	 */
	public void setTempsReponse(int tempsReponse) {
		this.tempsReponse = tempsReponse;
	}

	/**
	 * Méthode pour définir la difficulté de la question.
	 * @param difficulte la nouvelle difficulté de la question.
	 */
	public void setDifficulte(DifficulteQuestion difficulte) {
		this.difficulte = difficulte;
	}

	/**
	 * Méthode pour définir le type de la question.
	 * @param typeQuestion le nouveau type de la question.
	 */
	public void setTypeQuestion(TypeQuestion typeQuestion) {
		this.typeQuestion = typeQuestion;
	}

	/**
	 * Méthode pour définir la réponse à la question.
	 * @param reponse la nouvelle réponse à la question.
	 */
	public void setReponse(Reponse reponse) {
		this.reponse = reponse;
	}

	/*  -----------------  */
	/*	 Autres méthodes   */
	/*  -----------------  */

	/**
	 * Méthode pour ajouter un fichier à la liste des fichiers associés à la question.
	 * @param f le fichier à ajouter.
	 * @return true si le fichier a été ajouté avec succès.
	 */
	public boolean ajouterFichier(Fichier f) {
		return this.fichiers.add(f);
	}
}