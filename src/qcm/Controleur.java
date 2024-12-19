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
        this.generationEvals = new GenerationEvals(this);

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

    public void generationEvaluation ( boolean eval, String path ) {
        this.generationEvals.genererEvaluation(eval, path);
    }

    public void changerRessourceEval ( Ressource r ) {
        this.generationEvals.changerRessource(r);
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

    public boolean modifierQuestion ( Question q ) {
        return this.ctrlDonnees.modifierQuestion(q);
    }


    /*  --------------------------  */
    /*	  Lancement du programme    */
    /*  --------------------------  */

    /**
     * Ouvrir la fenêtre de création de question pour la modification avec les données de la question
     * @param data Données de la question
     */
    public void ouvrirCreerQuestion(DonneesCreationQuestion data) {
        new FrameCreerQst(this.accueil, this, data);
    }

	public static void main(String[] args) {
		new Controleur();
	}
}