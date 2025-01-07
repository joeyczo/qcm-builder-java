package qcm.metier;

public class QCMReponseItem {

	private String  texte;
	private boolean valide;
	
	public QCMReponseItem(String texte, boolean valide) {

		this.texte  = texte;
		this.valide = valide;

	}

	public String  getTexte()          { return this.texte;     }
	public boolean isValide()          { return this.valide;    }
	public void    setTexte(String s ) {        this.texte = s; }

}