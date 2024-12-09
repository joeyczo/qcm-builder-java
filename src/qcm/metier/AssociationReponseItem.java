package qcm.metier;

public class AssociationReponseItem 
{
	private AssociationReponseItem item;
	private String texte;
	private Fichier fichier;
	
	public AssociationReponseItem(AssociationReponseItem item, String texte, Fichier fichier) 
	{
		this.item    = item;
		this.texte   = texte;
		this.fichier = fichier;
	}

	public String  getTexte()			   	  { return this.texte;    }
	public Fichier getfichier()			   	  { return this.fichier;  }
	public void    setFichier(Fichier newFic) { this.fichier = newFic;}
	public void    setFichier(String s) 	  { this.texte   = s;       }
}