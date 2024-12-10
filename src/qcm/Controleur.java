package qcm;

import qcm.metier.GestionDonnees;
import qcm.metier.Notion;
import qcm.metier.Ressource;
import qcm.metier.TypeQuestion;
import qcm.vue.*;

public class Controleur {

	private Accueil			  accueil;
	private GestionDonnees	  gestionDonnees;
    private ControleurDonnees ctrlDonnees;

	public Controleur() {

		this.accueil		= new Accueil(this);
		this.gestionDonnees = new GestionDonnees(this);
        this.ctrlDonnees    = new ControleurDonnees("src/data/DonneesParam.csv", this);
        this.ctrlDonnees.chargerDonnees();

        // TODO : A supprimer

        /*Ressource dev = new Ressource("Dev");
        dev.ajouterNotion(new Notion("Tableaux"));
        dev.ajouterNotion(new Notion("Booléens"));
        dev.ajouterNotion(new Notion("Rien à foutre"));
        dev.ajouterNotion(new Notion("Pas cool"));

        this.gestionDonnees.ajouterRessource(dev);

        this.gestionDonnees.ajouterRessource(new Ressource("Bado"));
        this.gestionDonnees.ajouterRessource(new Ressource("PLP"));

        Ressource fat = new Ressource("Fat");

        fat.ajouterNotion(new Notion("Cuisine"));
        fat.ajouterNotion(new Notion("Ménage"));
        fat.ajouterNotion(new Notion("Gentil"));

        this.gestionDonnees.ajouterRessource(fat);*/

	}

    /*  --------------------------  */
    /*	 Méthodes gestion données   */
    /*  --------------------------  */

    public boolean ajouterRessource (Ressource ressource)
    {
        if (this.gestionDonnees.ajouterRessource(ressource))
        {
            this.ctrlDonnees.sauvegarder();
            return true;
        }

        return false;
    }

    public int getNbRessource () {
        return this.gestionDonnees.getNbRessource();
    }

    public Ressource getRessource(int i) {
        return this.gestionDonnees.getRessource(i);
    }

    public Ressource getRessource(String s) {
        return this.gestionDonnees.getRessource(s);
    }

    public Notion getNotion(Ressource r, String s) {
        return this.gestionDonnees.getNotion(r, s);
    }

    public Notion getNotion(Ressource r, int i) {
        return this.gestionDonnees.getNotion(r, i);
    }

    public boolean ajouterNotion(Ressource ressource, Notion notion) {
        if (this.gestionDonnees.ajouterNotion(ressource, notion))
        {
            this.ctrlDonnees.sauvegarder();
            return true;
        }

        return false;
    }

    public int getNbNotion(Ressource r) {
        return this.gestionDonnees.getNbNotions(r);
    }


    /*  --------------------------  */
    /*	  Lancement du programme    */
    /*  --------------------------  */

	public static void main(String[] args) {
		new Controleur();
	}
}