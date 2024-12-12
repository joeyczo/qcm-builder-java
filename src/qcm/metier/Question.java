package qcm.metier;

import qcm.utils.Uid;

import java.util.ArrayList;
import java.util.List;

public class Question
{
	private String 				texteQuestion;
	private String 				tempsReponse;
	private String				UID;

	private TypeQuestion 		typeQuestion;
	private Reponse 			reponse;
	private DifficulteQuestion 	difficulte;
	private Notion				notion;

	private List<Fichier> 		fichiers;
	
	public Question(String texteQuestion, String tempsReponse, TypeQuestion typeQuestion, Reponse reponse, DifficulteQuestion difficulte, Notion notion) {

		this.UID			= Uid.generateUid(10);
		this.texteQuestion 	= texteQuestion;
		this.tempsReponse 	= tempsReponse;
		this.typeQuestion 	= typeQuestion;
		this.reponse 		= reponse;
		this.difficulte 	= difficulte;
		this.fichiers 		= new ArrayList<Fichier>();
		this.notion			= notion;

	}

	public Question(String uid, String texteQuestion, String tempsReponse, TypeQuestion typeQuestion, Reponse reponse, DifficulteQuestion difficulte, Notion notion) {

		this.UID			= uid;
		this.texteQuestion 	= texteQuestion;
		this.tempsReponse 	= tempsReponse;
		this.typeQuestion 	= typeQuestion;
		this.reponse 		= reponse;
		this.difficulte 	= difficulte;
		this.fichiers 		= new ArrayList<Fichier>();
		this.notion			= notion;

	}

	/* ------------ */
	/*	 Getters 	*/
	/* ------------ */

	public String getUID() { return this.UID;	}

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

	public Notion	getNotion() {
		return this.notion;
	}

	/* ------------ */
	/*	 Setters 	*/
	/* ------------ */

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