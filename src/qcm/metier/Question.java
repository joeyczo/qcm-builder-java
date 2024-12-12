package qcm.metier;

import java.util.ArrayList;
import java.util.List;

public class Question
{
	private String texteQuestion;
	private String tempsReponse;

	private TypeQuestion typeQuestion;
	private Reponse reponse;
	private DifficulteQuestion difficulte;

	private List<Fichier> fichiers;
	
	public Question(String texteQuestion, String tempsReponse, TypeQuestion typeQuestion, Reponse reponse, DifficulteQuestion difficulte) {

		this.texteQuestion = texteQuestion;
		this.tempsReponse = tempsReponse;
		this.typeQuestion = typeQuestion;
		this.reponse = reponse;
		this.difficulte = difficulte;
		this.fichiers = new ArrayList<Fichier>();

	}

	/* Getters */

	public String getTexteQuestion() {
		return this.texteQuestion;
	}

	public String getTempsReponse() {
		return this.tempsReponse;
	}

	public DifficulteQuestion getDifficulte() {
		return this.difficulte;
	}

	public TypeQuestion getTypeQuestion() {
		return this.typeQuestion;
	}

	public Reponse getReponse() {
		return this.reponse;
	}

	public Fichier getFichier(int index) {
		return this.fichiers.get(index);
	}

	/* Setters */

	public void setTexteQuestion(String texteQuestion) {
		this.texteQuestion = texteQuestion;
	}

	public void setTempsReponse(String tempsReponse) {
		this.tempsReponse = tempsReponse;
	}

	public void setDifficulte(DifficulteQuestion difficulte) {
		this.difficulte = difficulte;
	}

	public void setTypeQuestion(TypeQuestion typeQuestion) {
		this.typeQuestion = typeQuestion;
	}

	public void setReponse(Reponse reponse) {
		this.reponse = reponse;
	}

	/* Methodes */

	public boolean ajouterFichier(Fichier f){
		return this.fichiers.add(f);
	}

}