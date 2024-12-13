package qcm.metier;
import java.util.*;
public class AssociationReponse implements Reponse
{
	private ArrayList<AssociationReponseItem> 	alReponses;
	private String								txtExplication;

	public AssociationReponse(){this.alReponses=new ArrayList<AssociationReponseItem>();}
	
	public ArrayList<AssociationReponseItem> getReponses(){ return this.alReponses;}

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
