package qcm.metier;

public class QCMReponseItem 
{
	private String texte;
	private boolean valide;
	
	public QCMReponseItem(String texte, boolean valide) 
	{
		this.texte = texte;
		this.valide = valide;
	}

	public String  getTexte()		    { return this.texte; }
	public boolean isValide()		    { return this.valide;}
	public void    setFichier(String s ){ this.texte = s;    }
	public void    setValide (boolean b){ this.valide = b;   }
}