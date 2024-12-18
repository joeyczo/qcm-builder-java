package qcm.metier;
import java.util.*;
public class AssociationReponse implements Reponse
{
	private ArrayList<AssociationReponseItem> 	alReponses;
	private String								txtExplication;

	public AssociationReponse(){
		this.alReponses		= new ArrayList<AssociationReponseItem>();
		this.txtExplication	= null;
	}
	
	public ArrayList<AssociationReponseItem> getReponses(){ return this.alReponses;}

	public int getNbReponses () {
		return this.alReponses.size();
	}

	public AssociationReponseItem getReponse ( int indince ) {
		return this.alReponses.get(indince);
	}

	public void ajouterTexteExplication(String s) {
		this.txtExplication = s;
	}

	public String getTexteExplication() {
		return this.txtExplication;
	}

	public void ajouterDefinition(AssociationReponseItem item) {
		this.alReponses.add(item);
	}
}
