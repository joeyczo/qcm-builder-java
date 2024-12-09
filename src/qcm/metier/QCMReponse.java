package qcm.metier;

import java.util.ArrayList;

public class QCMReponse implements Reponse
{
	private ArrayList<QCMReponseItem> alReponses;

	public QCMReponse(){this.alReponses=new ArrayList<QCMReponseItem>();}
	
	public ArrayList<QCMReponseItem> getReponses(){ return this.alReponses;}
}
