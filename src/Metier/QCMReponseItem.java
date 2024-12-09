package Metier;

public class QCMReponseItem 
{
	private String texte;
	private boolean valide;
	
	public QCMReponseItem(String texte, boolean valide) 
	{
		this.texte = texte;
		this.valide = valide;
	}

	/**
	 * Méthode pour obtenir le texte de la réponse.
	 * @return le texte de la réponse.
	 */
	public String  getTexte()		    { return this.texte; }

	/**
	 * Méthode pour obtenir la validité de la réponse.
	 * @return la validité de la réponse.
	 */
	public boolean isValide()		    { return this.valide;}

	/**
	 * Méthode pour définir le texte de la réponse.
	 * @param texte le texte de la réponse.
	 */
	public void    setFichier(String s ){ this.texte = s;    }

	/**
	 * Méthode pour définir la validité de la réponse.
	 * @param b la validité de la réponse.
	 */
	public void    setValide (boolean b){ this.valide = b;   }
}