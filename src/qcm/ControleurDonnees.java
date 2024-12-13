package qcm;

import qcm.metier.*;

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

            sRet += rsc.getNom().replaceAll(",", "|");

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
            sRet += this.ctrl.getNotion(rsc, i).getNom().replaceAll(",", "|") + (((i+1)!=nbNotion)?",":"");

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

        String sRet                 = "";
        String pathFichierDonnees   = Paths.get("src", "data", "app", "DonneesQuestions.csv").toString();

        /*  ---------------------------------  */
        /*	  Lecture du fichier de données    */
        /*  ---------------------------------  */

        try {

            Scanner sc = new Scanner(new FileInputStream(pathFichierDonnees));

            while (sc.hasNextLine()) {

                sRet += sc.nextLine();

            }

            sc.close();

        } catch (Exception e) {
            System.out.println("Erreur lecture fichier : " + e.getMessage());
        }

        sRet += (!sRet.isEmpty()) ? "\n" : "";

        /*
         * Structure :
         * UID
         * Difficulte (TRESFACILE, FACILE ...)
         * Type de question (QCM,ASSOCIATION,ELIMINIATION)
         * Nom de la ressource
         * Nom de la notion
         * Temps de réponse (mm:ss)
         * Nombre de points
         */

        String nomRessource = q.getNotion().getRessource().getNom().replaceAll(",", "|");
        String nomNotion    = q.getNotion().getNom().replaceAll(",", "|");

        sRet += q.getUID()                  + "," +
                q.getDifficulte()           + "," +
                q.getTypeQuestion()         + "," +
                nomRessource                + "," +
                nomNotion                   + "," +
                q.getTempsReponse()         + "," +
                q.getNbPoints();

        /*  ----------------------------------  */
        /*	  Écriture du fichier de données    */
        /*  ----------------------------------  */

        try {

            PrintWriter pw = new PrintWriter(new FileOutputStream(pathFichierDonnees));

            pw.print(sRet);

            pw.close();

            /*  -----------------------------------------  */
            /*	  Écriture de la question au format TXT    */
            /*  -----------------------------------------  */

            PrintWriter pwQuestion = new PrintWriter(new FileOutputStream(Paths.get("src", "data", "app", q.getUID()+".txt").toString()));

            String questionEnTxt = "{TEXTEQST}\n";

            questionEnTxt += q.getTexteQuestion();

            if (q.getReponse().getTexteExplication() != null) {
                questionEnTxt += "\n{TEXTEEXPLICATION}\n";

                questionEnTxt += q.getReponse().getTexteExplication();
            }

            /*  ---------------------------------------------  */
            /*	  Écriture des réponses dans le fichier TXT    */
            /*  ---------------------------------------------  */

            if (q.getTypeQuestion() == TypeQuestion.QCM) {

                QCMReponse reponse = (QCMReponse) q.getReponse();

                for (QCMReponseItem item : reponse.getReponses()) {

                    questionEnTxt += "\n{REPONSEQCM}\n";

                    questionEnTxt += item.getTexte();

                }

            } else if (q.getTypeQuestion() == TypeQuestion.ASSOCIATION) {

                AssociationReponse reponse = (AssociationReponse) q.getReponse();

                for (AssociationReponseItem item : reponse.getReponses()) {

                    questionEnTxt += "\n{ASSOCIATION}\n";

                    questionEnTxt += item.getTexte();

                    questionEnTxt += "\n{REPONSE}\n";

                    questionEnTxt += item.getReponse().getTexte();

                }

            }

            pwQuestion.print(questionEnTxt);

            pwQuestion.close();

            return true;

        } catch (Exception e) {
            System.out.println("Erreur de fichier : " + e.getMessage());
            return false;
        }

        // TODO : Sauvegarde des réponses dans les fichiers CSV

    }


    /*  --------------------------  */
    /*                              */
    /*	  CHARGEMENT DES DONNEES    */
    /*                              */
    /*  --------------------------  */

    public void chargerDonnees() {

        // Chargement des données (Ressource et Notion)
        this.chargerDonneesParametres();

    }

    /**
     * Chargement des données paramètres, Ressource et Notion-
     */
    private void chargerDonneesParametres() {

        try {

            Scanner sc = new Scanner(new FileInputStream(this.lienFichier));

            while (sc.hasNextLine()) {

                String ligne = sc.nextLine();

                Scanner scLigne = new Scanner(ligne).useDelimiter(",");

                String nomRessource = scLigne.next();

                Ressource rsc = new Ressource(nomRessource.replaceAll("[|]", ","));

                while (scLigne.hasNext())
                    rsc.ajouterNotion(new Notion(scLigne.next().replaceAll("[|]", ","), rsc));


                this.ctrl.ajouterRessource(rsc);

            }


        } catch (Exception e) {

            System.out.println("Erreur lors de l'ouverture du fichier : " + e.getMessage());

        }

    }


}
