package Metier;

public class Evalutation 
{
	private int nbQuestions;
	private boolean evaluation;

	public Evalutation(int nb, boolean eval)
	{
		this.nbQuestions = nb;
		this.evaluation  = eval;
	}

	/**
	 * Méthode permettant de savoir si l'objet est une évaluation
	 * @return vrai si c'est une évaluation, faux sinon.
	 */
	public boolean isEvaluee (){return this.evaluation;}

	/**
	 * Méthode permettant de savoir le nombre de questions de l'évaluation.
	 * @return le nombre de questions de l'évaluation.
	 */
	public int getNbQuestions(){return this.nbQuestions;}
}