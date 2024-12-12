package qcm.metier;

import java.util.ArrayList;
import java.util.List;

public class QCMReponse implements Reponse
{
	private List<QCMReponseItem> 	alReponses;
	private String					txtExplication;

	public QCMReponse() {
		this.alReponses=new ArrayList<QCMReponseItem>();
	}
	
	public List<QCMReponseItem> getReponses(){
		return this.alReponses;
	}

	public void ajouterItem (QCMReponseItem item) {

		this.alReponses.add(item);

	}

	public void ajouterTexteExplication(String s) {
		this.txtExplication = s;
	}

	public String getTexteExplication() {
		return this.txtExplication;
	}
}