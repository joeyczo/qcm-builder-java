package qcm.metier;

import java.util.ArrayList;

public class EliminationReponse implements Reponse {

	private ArrayList<EliminationReponseItem> 	reponses;
	private String								txtExplication;

	public ArrayList<EliminationReponseItem> getReponses() {
		return reponses;
	}

	public void ajouterTexteExplication(String s) {
		this.txtExplication = s;
	}

	public String getTexteExplication() {
		return this.txtExplication;
	}
}