package Metier;
import java.util.*;
public class AssociationReponse implements Reponse
{
	private ArrayList<AssociationReponseItem> alReponses;

	public AssociationReponse(){this.alReponses=new ArrayList<AssociationReponseItem>();}
	
	public ArrayList<AssociationReponseItem> getReponses(){ return this.alReponses;}
}
