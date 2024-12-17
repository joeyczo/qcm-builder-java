package qcm;

import qcm.metier.*;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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

                sRet += sc.nextLine() + "\n";

            }

            sc.close();

        } catch (Exception e) {
            System.out.println("Erreur lecture fichier : " + e.getMessage());
        }

        /*
         * Structure :
         * UID
         * Difficulte (TRESFACILE, FACILE ...)
         * Type de question (QCMSOLO,QCMMULTI,ASSOCIATION,ELIMINIATION)
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

            if (q.getTypeQuestion() == TypeQuestion.QCMSOLO || q.getTypeQuestion() == TypeQuestion.QCMMULTI) {

                QCMReponse reponse = (QCMReponse) q.getReponse();

                for (QCMReponseItem item : reponse.getReponses()) {

                    questionEnTxt += "\n{REPONSEQCM}\n";

                    questionEnTxt += item.isValide() ? "1\n" : "0\n"; // 1 si la réponse est valide. Sinon 0

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

    }


    /*  --------------------------  */
    /*                              */
    /*	  CHARGEMENT DES DONNEES    */
    /*                              */
    /*  --------------------------  */

    /**
     * Méthode permettant de charger les données dans la base de données
     * - Paramètres (Ressources et Notions)
     * - Questions (Avec les réponses)
     */
    public void chargerDonnees() {

        // Chargement des données (Ressource et Notion)
        this.chargerDonneesParametres();

        // Chargement des questions et réponses
        this.chargerDonneesQuestions();

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

            System.out.println("Erreur lors de l'ouverture du fichier des paramètres : " + e.getMessage());

        }

    }

    /**
     * Chargement des questions dans la base de données
     */
    private void chargerDonneesQuestions() {

        String pathFichierDonnees   = Paths.get("src", "data", "app", "DonneesQuestions.csv").toString();

        try {

            Scanner sc = new Scanner(new FileInputStream(pathFichierDonnees), StandardCharsets.UTF_8);

            while (sc.hasNextLine()) {

                Scanner scLigne = new Scanner(sc.nextLine()).useDelimiter(",");

                String  uidQuestion = scLigne.next();
                String  difficulte  = scLigne.next();
                String  typeQst     = scLigne.next();
                String  ressource   = scLigne.next();
                String  nomNotion   = scLigne.next();
                String  tempsRsp    = scLigne.next();
                String  ptsRsp      = scLigne.next();

                // Transformation des données

                String txtQuestion  = this.getTexteQuestion(uidQuestion);
                double nbPoints     = Double.parseDouble(ptsRsp);

                TypeQuestion typeQuestion = switch (typeQst) {
                    case "QCMMULTI"     -> TypeQuestion.QCMMULTI;
                    case "ASSOCIATION"  -> TypeQuestion.ASSOCIATION;
                    case "ELIMINATION"  -> TypeQuestion.ELIMINATION;
                    default             -> TypeQuestion.QCMSOLO;
                };

                DifficulteQuestion difficulteQuestion = switch (difficulte) {
                    case "TRESFACILE"   -> DifficulteQuestion.TRESFACILE;
                    case "FACILE"       -> DifficulteQuestion.FACILE;
                    case "MOYEN"        -> DifficulteQuestion.MOYEN;
                    default             -> DifficulteQuestion.DIFFICILE;
                };

                Ressource rsc = this.ctrl.getRessource(ressource);

                if (rsc == null)
                    return;

                Notion notion = this.ctrl.getNotion(rsc, nomNotion);

                if (notion == null)
                    return;

                System.out.println("UID " + uidQuestion);
                System.out.println(txtQuestion);

                Reponse rsp = this.getReponseFichier(uidQuestion, typeQuestion);

                Question nouvelleQuestion = new Question(txtQuestion, tempsRsp, nbPoints, typeQuestion, rsp, difficulteQuestion, notion);

                notion.ajouterQuestion(nouvelleQuestion);

            }

        } catch (Exception e) {
            System.out.println("Erreur lors de l'ouverture du fichier des questions : " + e.getMessage());
        }

    }

    /**
     * Récupérer le texte de la question en fonction de son UID
     * @param uid UID de la question pour récupérer les données dans le fichier TXT
     * @return Le texte de la question
     */
    private String getTexteQuestion (String uid) {

        String  pathFichierQuestion = Paths.get("src", "data", "app", uid+".txt").toString();
        String sRet                 = "";

        try {

            Scanner sc = new Scanner(new FileInputStream(pathFichierQuestion));

            while (sc.hasNextLine()) {

                String ligne = sc.nextLine();

                // Déterminer le type de lecture
                if (ligne.equals("{TEXTEQST}")) {

                    sRet = sc.nextLine();

                    sRet = sRet.replaceAll("\\\\n", "\n").replaceAll("\\\\t", "\t");

                }

            }

        } catch (Exception e) {
            System.out.println("Erreur lecture fichier données de la question : " + e.getMessage());
        }

        return sRet;

    }

    /**
     * Permets de récupérer la réponse à la question en fonction de son UID et de son type (Qui sera retourné)
     * @param uid UID de la question
     * @param typeQuestion Type de la question
     * @return L'objet Reponse correspondant à l'UID de la question
     */
    private Reponse getReponseFichier ( String uid, TypeQuestion typeQuestion ) {

        String  pathFichierQuestion = Paths.get("src", "data", "app", uid+".txt").toString();
        QCMReponse          qcmReponse          = new QCMReponse();
        AssociationReponse  associationReponse  = new AssociationReponse();

        
        try {

            Scanner sc = new Scanner(new FileInputStream(pathFichierQuestion));

            while (sc.hasNextLine()) {

                String ligne = sc.nextLine();

                // Association des bonnes ou mauvaises réponses pour les QCM Solo et Multiples
                if (typeQuestion == TypeQuestion.QCMSOLO || typeQuestion == TypeQuestion.QCMMULTI) {

                    if (ligne.equals("{REPONSEQCM}")) {

                        boolean estValide   = sc.nextLine().equals("1");
                        String txtQuestion  = sc.nextLine().replaceAll("\\\\n", "\n").replaceAll("\\\\t", "\t");

                        QCMReponseItem item = new QCMReponseItem(txtQuestion, estValide);

                        qcmReponse.ajouterItem(item);

                    }

                    return qcmReponse;

                } else if (typeQuestion == TypeQuestion.ASSOCIATION) { // Association pour les réponses de type associations

                    if (ligne.equals("{ASSOCIATION}")) {

                        String txtDefinition = sc.nextLine().replaceAll("\\\\n", "\n").replaceAll("\\\\t", "\t");

                        AssociationReponseItem definition = new AssociationReponseItem(txtDefinition);

                        if (sc.hasNextLine() && sc.nextLine().equals("{REPONSE}")) {

                            String txtReponse = sc.nextLine().replaceAll("\\\\n", "\n").replaceAll("\\\\t", "\t");

                            AssociationReponseItem reponse = new AssociationReponseItem(txtReponse);

                            definition.setReponse(reponse);

                            associationReponse.ajouterDefinition(definition);

                        } else {
                            System.out.println("Erreur lors de la lecture des associations !");
                        }

                    }

                }

                return null;

            }

            
        } catch (Exception e) {
            System.out.println("Erreur de lecture du fichier de données de la réponse : " + e.getMessage());
        }

        return null;

    }


}
