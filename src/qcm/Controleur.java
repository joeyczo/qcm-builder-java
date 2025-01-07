package qcm;

import qcm.metier.*;
import qcm.vue.*;

import java.io.File;
import java.util.ArrayList;

public class Controleur {

	private Accueil			    accueil;
	private GestionDonnees	    gestionDonnees;
    private ControleurDonnees   ctrlDonnees;
    private GenerationEvals     generationEvals;

	public Controleur() {

		this.accueil		 = new Accueil          (this);
		this.gestionDonnees  = new GestionDonnees   (this);
        this.ctrlDonnees     = new ControleurDonnees(this);
        this.generationEvals = new GenerationEvals  (this);

        this.ctrlDonnees.chargerDonnees();

	}

    /*  -----------------------------  */
    /*	 Méthodes gestion paramètres   */
    /*  -----------------------------  */

    public int       getNbRessource()                      { return this.gestionDonnees.getNbRessource();}
    public int       getNbNotion   (Ressource r)           { return this.gestionDonnees.getNbNotions(r); }
    public Ressource getRessource  (int i)                 { return this.gestionDonnees.getRessource(i); }
    public Ressource getRessource  (String s)              { return this.gestionDonnees.getRessource(s); }
    public Notion    getNotion     (Ressource r, String s) { return this.gestionDonnees.getNotion(r, s); }
    public Notion    getNotion     (Ressource r, int i)    { return this.gestionDonnees.getNotion(r, i); }

    public boolean ajouterRessource (Ressource ressource) {

        if (this.gestionDonnees.ajouterRessource(ressource)) {

            this.ctrlDonnees.sauvegarder();
            return true;

        }

        return false;

    }

    public boolean ajouterNotion(Ressource ressource, Notion notion) {

        if (this.gestionDonnees.ajouterNotion(ressource, notion)) {

            this.ctrlDonnees.sauvegarder();
            return true;

        }

        return false;

    }

    /*  ---------------------------  */
    /*	 Méthodes génération évals   */
    /*  ---------------------------  */

    public int     getNotionsSelected       ()                                          { return this.generationEvals.getNotionSelected(); }
    public int     getNbQuestions           ()                                          { return this.generationEvals.getNbQuestions();    }


    public boolean ajouterNotion            (Notion n)                                  { return this.generationEvals.ajouterNotion(n);                   }
    public Integer ajouterDifficulteQuestion(Notion n, DifficulteQuestion d, Integer i) { return this.generationEvals.ajouterDifficulteQuestion(n, d, i); }
    public boolean supprimerNotion          (Notion n)                                  { return this.generationEvals.supprimerNotion(n);                 }
    public boolean exporterEval             (Evalutation e)                             { return this.generationEvals.exporterEvaluation(e);              }
    public void    resetGenerationEvals     ()                                          {        this.generationEvals.resetGeneration();                  }
    public void    generationEvaluation     (boolean eval, String path)                 {        this.generationEvals.genererEvaluation(eval, path);      }
    public void    changerRessourceEval     (Ressource r)                               {        this.generationEvals.changerRessource(r);                }

    /*  ---------------------------------  */
    /*	 Méthodes gestions des questions   */
    /*  ---------------------------------  */

    public ArrayList<Fichier> getFichiersQuestion     ()                                                 { return this.ctrlDonnees.getFichiersQuestion();                     }
    public File               getFichierQuestion      (int nbIndex)                                      { return this.ctrlDonnees.getFichierQuestion(nbIndex);               }
    public Question           getQuestionUID          (Notion n, String uid)                             { return this.gestionDonnees.getQuestionUID(n, uid);                 }

    public void               chargerFichiersQuestion (ArrayList<Fichier> ensFichier)                    {        this.ctrlDonnees.chargerFichiersQuestion(ensFichier);       }
    public void               nouvelleQuestionFichier ()                                                 {        this.ctrlDonnees.nouvelleQuestionFichier();                 }
    public boolean            ajouterFichierQuestion  (String lienFichier, DonneesCreationQuestion data) { return this.ctrlDonnees.ajouterFichierQuestion(lienFichier, data); }
    public boolean            sauvegarderQuestion     (Question q)                                       { return this.ctrlDonnees.sauvegarderQuestion(q);                    }
    public boolean            modifierQuestion        (Question q)                                       { return this.ctrlDonnees.modifierQuestion(q);                       }
    public boolean            supprimerFichierQuestion(int nbIndex)                                      { return this.ctrlDonnees.supprimerFichierQuestion(nbIndex);         }

    public boolean supprimerQuestion (Notion n, Question q) {

        if (this.ctrlDonnees.supprimerQuestion(q))
            return this.gestionDonnees.supprimerQuestion(n ,q);

        return false;

    }


    /*  --------------------------  */
    /*	  Lancement du programme    */
    /*  --------------------------  */

    /**
     * Ouvrir la fenêtre de création de question pour la modification avec les données de la question
     * @param data Données de la question
     */
    public        void ouvrirCreerQuestion(DonneesCreationQuestion data) { new FrameCreerQst(this.accueil, this, data); }
	public static void main               (String[] args)                { new Controleur(); }
}