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

	public boolean isEvaluee (){return this.evaluation;}
	public int getNbQuestions(){return this.nbQuestions;}
}