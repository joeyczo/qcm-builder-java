package qcm;

import qcm.metier.Notion;
import qcm.metier.Question;
import qcm.metier.Ressource;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class ControleurDonnees
{
    private String lienFichier;
    private Controleur ctrl;

    public ControleurDonnees(String lienFichier, Controleur ctrl)
    {
        this.lienFichier = lienFichier;
        this.ctrl = ctrl;
    }


    /*  --------------------------  */
    /*                              */
    /*	  SAUVEGARDE DES DONNEES    */
    /*                              */
    /*  --------------------------  */

    /*  ----------------------------------------  */
    /*	  SAUVEGARDE DES NOTIONS ET RESSOURCES    */
    /*  ----------------------------------------  */

    public boolean sauvegarder() {

        String sRet     = "";
        boolean sauves  = false;

        /*  -----------------------------  */
        /*	  Sauvegarde des ressources    */
        /*  -----------------------------  */

        for (int i = 0; i < this.ctrl.getNbRessource(); i++) {
            Ressource rsc = this.ctrl.getRessource(i);

            sRet += rsc.getNom();

            String donnesNotions = this.sauvegardeNotion(rsc);

            if (!donnesNotions.isEmpty())
                sRet += "," + donnesNotions;

            sRet += "\n";
        }


        /*  ----------------------------------  */
        /*	  Écriture du fichier de données    */
        /*  ----------------------------------  */

        try {

            PrintWriter pw = new PrintWriter(new FileOutputStream(this.lienFichier));

            pw.println(sRet);

            pw.close();

            sauves = true;


        } catch (Exception e) {
            System.out.println("Problème lors de la création du fichier :" + e.getMessage());
        }

        return sauves;

    }

    private String sauvegardeNotion(Ressource rsc) {

        String sRet = "";

        if (rsc == null) return "";

        int nbNotion = this.ctrl.getNbNotion(rsc);

        for (int i = 0; i < nbNotion; i++)
            sRet += this.ctrl.getNotion(rsc, i).getNom() + (((i+1)!=nbNotion)?",":"");

        return sRet;

    }


    /*  ----------------------------  */
    /*	  SAUVEGARDE DES QUESTIONS    */
    /*  ----------------------------  */

    /**
     * Permets de sauvegarder la question dans la base de données
     * Un fichier texte sera créer avec le texte de la question
     * @param q Question à sauvegarder dans la base de données
     * @return True si la question a été sauvegardé, sinon false
     */
    public boolean sauvegarderQuestion ( Question q ) {

        if (q == null) return false;

        String sRet = "";

        /*  ---------------------------------  */
        /*	  Lecture du fichier de données    */
        /*  ---------------------------------  */

        try {

            // TODO : Lecture du fichier

        } catch (Exception e) {
            System.out.println("Erreur lecture fichier : " + e.getMessage());
        }

        sRet += q.getUID() + "," + q.getDifficulte() + "," + q.getTypeQuestion() + "," +  q.getNotion().getRessource().getNom() + "," +q.getNotion().getNom() + "," + q.getReponse().getTexteExplication();

        /*  ----------------------------------  */
        /*	  Écriture du fichier de données    */
        /*  ----------------------------------  */

        try {

            PrintWriter pw = new PrintWriter(new FileOutputStream(Paths.get("src", "data", "app", "DonneesQuestions.csv").toString()));

            pw.println(sRet);

            pw.close();

            /*  -----------------------------------------  */
            /*	  Écriture de la question au format TXT    */
            /*  -----------------------------------------  */

            /*PrintWriter pwQuestion = new PrintWriter(new FileOutputStream(Paths.get("src", "data", "app", q.getUID()+".txt").toString()));

            pwQuestion.println(q.getTexteQuestion());

            pwQuestion.close();*/

            return true;

        } catch (Exception e) {
            System.out.println("Erreur de fichier : " + e.getMessage());
            return false;
        }

    }


    /*  --------------------------  */
    /*                              */
    /*	  CHARGEMENT DES DONNEES    */
    /*                              */
    /*  --------------------------  */

    public void chargerDonnees() {

        try {

            Scanner sc = new Scanner(new FileInputStream(this.lienFichier));

            while (sc.hasNextLine()) {

                String ligne = sc.nextLine();

                Scanner scLigne = new Scanner(ligne).useDelimiter(",");

                Ressource rsc = new Ressource(scLigne.next());

                while (scLigne.hasNext()) {

                    rsc.ajouterNotion(new Notion(scLigne.next(), rsc));

                }

                this.ctrl.ajouterRessource(rsc);

            }


        } catch (Exception e) {

            System.out.println("Erreur lors de l'ouverture du fichier : " + e.getMessage());

        }

    }


}
