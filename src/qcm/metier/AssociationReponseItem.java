package qcm.metier;

public class AssociationReponseItem 
{
	private AssociationReponseItem 	item;
	private String 					texte;
	private Fichier 				fichier;

    /**
     * Initialiser la définition avec le lien vers la question
     * @param item Item réponse
     * @param texte Texte de la définition
     */
	public AssociationReponseItem(AssociationReponseItem item, String texte)
	{
		this.item    = item;
		this.texte   = texte;
		this.fichier = null;
	}

    /**
     * Initialiser la réponse à la définition (Sans Item)
     * @param texte Le texte de la réponse
     */
    public AssociationReponseItem(String texte)
    {
        this.item    = null;
        this.texte   = texte;
        this.fichier = null;
    }

	public String  getTexte()			   	  { return this.texte;    	}
	public Fichier getfichier()			   	  { return this.fichier;  	}
	public void    setFichier(Fichier newFic) { this.fichier = newFic;	}
	public void    setTexte(String s) 	 	  { this.texte   = s;       }

    /**
     * Associer la réponse à la définition
     * @param r Nouvelle réponse
     */
    public void setReponse(AssociationReponseItem r) {
        this.item = r;
    }

    /**
     * Récupérer la réponse de la défintion
     * @return La réponse de la définition si elle existe
     */
    public AssociationReponseItem getReponse() {
        return this.item;
    }
}