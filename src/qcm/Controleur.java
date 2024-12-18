package qcm;

import qcm.metier.*;
import qcm.utils.Uid;
import qcm.vue.*;

public class Controleur {

	private Accueil			    accueil;
	private GestionDonnees	    gestionDonnees;
    private ControleurDonnees   ctrlDonnees;
    private GenerationEvals     generationEvals;

	public Controleur() {

		this.accueil		= new Accueil(this);
		this.gestionDonnees = new GestionDonnees(this);
        this.ctrlDonnees    = new ControleurDonnees (this);
        this.generationEvals = new GenerationEvals();

        this.ctrlDonnees.chargerDonnees();

	}

    /*  -----------------------------  */
    /*	 Méthodes gestion paramètres   */
    /*  -----------------------------  */

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

    /*  ---------------------------  */
    /*	 Méthodes génération évals   */
    /*  ---------------------------  */

    public boolean ajouterNotion (Notion n) {
        return this.generationEvals.ajouterNotion(n);
    }

    public boolean supprimerNotion (Notion n) {
        return this.generationEvals.supprimerNotion(n);
    }

    public void resetGenerationEvals() {
        this.generationEvals.resetGeneration();
    }

    public int getNotionsSelected () {
        return this.generationEvals.getNotionSelected();
    }

    public boolean ajouterDifficulteQuestion (Notion n, DifficulteQuestion d, Integer i) {
        return this.generationEvals.ajouterDifficulteQuestion(n, d, i);
    }

    public int getNbQuestions() {
        return this.generationEvals.getNbQuestions();
    }

    public void generationEvaluation ( String path ) {
        this.generationEvals.genererEvaluation(path);
    }

    /*  ------------------------------  */
    /*	 Méthodes créations questions   */
    /*  ------------------------------  */

    public boolean sauvegarderQuestion ( Question q ) {
        return this.ctrlDonnees.sauvegarderQuestion(q);
    }

    public Question getQuestionUID ( Notion n, String uid ) {
        return this.gestionDonnees.getQuestionUID(n, uid);
    }


    /*  --------------------------  */
    /*	  Lancement du programme    */
    /*  --------------------------  */

	public static void main(String[] args) {
		new Controleur();
	}
}