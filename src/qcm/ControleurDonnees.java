package qcm;

import qcm.metier.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Scanner;

public class ControleurDonnees {

    private Controleur          ctrl;
    private ArrayList<Fichier>  ensFichiers;

    public ControleurDonnees(Controleur ctrl) {

        this.ctrl           = ctrl;
        this.ensFichiers    = new ArrayList<>();

    }


    /*  --------------------------  */
    /*                              */
    /*	  SAUVEGARDE DES DONNEES    */
    /*                              */
    /*  --------------------------  */


    /*  ----------------------------------------  */
    /*    SAUVEGARDE DES NOTIONS ET RESSOURCES    */
    /*  ----------------------------------------  */

    // Sauvegarde des Ressources et des Notions
    public boolean sauvegarder() { return this.sauvegarderRessources(); }

    /**
     * Nouvelle méthode permettant de sauvegarder les ressources et les notions
     * Permets également de créer un dossier avec le nom de la ressource dans les fichiers de l'application
     * (Deux tables séparées)
     */
    private boolean sauvegarderRessources () {

        String sRet = "";

        try {

            PrintWriter pw = new PrintWriter(new FileOutputStream("data/DonneesRessource.csv"));
            
            for ( int i = 0; i < this.ctrl.getNbRessource(); i++) {
                
                Ressource rsc = this.ctrl.getRessource(i);

                String  code    = (rsc.getCode() != null)        ? rsc.getCode()       .replaceAll(",", "|") : "";
                String  nom     = (rsc.getNomSansCode() != null) ? rsc.getNomSansCode().replaceAll(",", "|") : "";
                
                sRet += rsc.getUID() + "," + code + "," + nom;
                
                if (i < this.ctrl.getNbRessource() -1) sRet += "\n";

                // Création du dossier de données parents
                File dossierRsc = new File("data/app/rsc/" + code.replaceAll("/", "."));

                if (!dossierRsc.exists()) dossierRsc.mkdirs();

                this.sauvegarderNotions(rsc);

            }

            pw.println(sRet);

            pw.close();

            return true;

        } catch (Exception e) {

            System.out.println("Erreur sauvegarde fichier de données Ressource : " + e.getMessage());
            return false;

        }

    }

    /**
     * Permets de sauvegarder les notions dans la base de données
     * Les notions sont associés à la ressource par son UID (De la ressource)
     * Permets également de créer l'ensemble des données
     * @param r Ressource parent
     */
    private void sauvegarderNotions ( Ressource r ) {

        if (r.getNbNotion() == 0) return;

        String  lienFichier = "data/DonneesNotions.csv";
        String  sRet        = "";
        boolean modifie     = false;

        try {

            if (!Files.exists(Paths.get( "data", "DonneesNotions.csv")))
                Files.createFile(Paths.get( "data", "DonneesNotions.csv"));

            Scanner sc = new Scanner(new FileInputStream(lienFichier), StandardCharsets.UTF_8);

            /*  ----------------------------------  */
            /*	     Récupération des données       */
            /*  ----------------------------------  */

            while (sc.hasNextLine()) {

                String ligne = sc.nextLine();

                Scanner scLigne = new Scanner(ligne).useDelimiter(",");

                String  uid = scLigne.next();

                if (uid.equals(r.getUID())) {

                    sRet += r.getUID() + ",";

                    for ( int i = 0; i < r.getNbNotion(); i++) {

                        Notion not = r.getNotion(i);

                        sRet += not.getNom().replaceAll(",", "|") + ((i<r.getNbNotion()-1) ? "," : "");

                        // Création du dossier de données

                        String  nomRscParent = r.getCode() .replaceAll("/", ".");
                        String  nomNot       = not.getNom().replaceAll("/", ".");

                        File fichierNot = new File("data/app/rsc/" + nomRscParent + "/" + nomNot);

                        if (!fichierNot.exists()) fichierNot.mkdir();

                    }

                    sRet += "\n";

                    modifie = true;

                }
                else {

                    sRet += ligne + "\n";

                }

            }

            if (!modifie) {

                sRet += r.getUID() + ",";

                for ( int i = 0; i < r.getNbNotion(); i++) {

                    Notion not = r.getNotion(i);

                    sRet += not.getNom().replaceAll(",", "|") + ((i<r.getNbNotion()-1) ? "," : "");

                }

                sRet += "\n";

            }

            /*  ----------------------------------  */
            /*        Sauvegarde des données        */
            /*  ----------------------------------  */

            PrintWriter pw = new PrintWriter(new FileOutputStream(lienFichier));

            pw.print(sRet);

            pw.close();


        }
        catch (Exception e) {

            System.out.println("Erreur fichier de sauvegarde des données notions : " + e.getMessage());

        }

    }


    /*  ----------------------------  */
    /*    SAUVEGARDE DES QUESTIONS    */
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
        String pathFichierDonnees   = Paths.get( "data", "app", "DonneesQuestions.csv").toString();

        /*  ---------------------------------  */
        /*    Lecture du fichier de données    */
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
        String nomNotion    = q.getNotion()               .getNom().replaceAll(",", "|");

        sRet += q.getUID()                  + "," +
                q.getDifficulte()           + "," +
                q.getTypeQuestion()         + "," +
                nomRessource                + "," +
                nomNotion                   + "," +
                q.getTempsReponse()         + "," +
                q.getNbPoints();

        /*  ----------------------------------  */
        /*    Écriture du fichier de données    */
        /*  ----------------------------------  */

        try {

            PrintWriter pw = new PrintWriter(new FileOutputStream(pathFichierDonnees));

            pw.print(sRet);

            pw.close();

            return this.sauvegarderDonneesQuestion(q);

        } catch (Exception e) {

            System.out.println("Erreur de fichier : " + e.getMessage());
            return false;

        }

    }

    /**
     * Sauvegarder les données de la question dans le fichier TXT
     * Contient également les réponses et données des réponses
     * @param q L'objet question afin de récupérer les données
     * @return True si le fichier a été sauvegardé, sinon false
     */
    private boolean sauvegarderDonneesQuestion ( Question q ) {

        try {

            /*  -----------------------------------------  */
            /*    Écriture de la question au format TXT    */
            /*  -----------------------------------------  */

            PrintWriter pwQuestion = new PrintWriter(new FileOutputStream(Paths.get("data", "app", q.getUID()+".txt").toString()));
            String questionEnTxt   = "{TEXTEQST}\n";

            questionEnTxt += q.getTexteQuestion();

            if (q.getReponse().getTexteExplication() != null) {

                questionEnTxt += "\n{TEXTEEXPLICATION}\n";
                questionEnTxt += q.getReponse().getTexteExplication();

            }

            // Écriture des fichiers utilisés dans la question
            if (!q.getEnsembleFichier().isEmpty()) {

                questionEnTxt += "\n{FICHIERS}\n";

                for ( int i = 0; i < q.getEnsembleFichier().size(); i++) {

                    Fichier f = q.getFichier(i);

                    questionEnTxt += f.nomFichier() + "," + f.lienFic() + ((i < (q.getEnsembleFichier().size()-1)) ? "\n" : "");

                }


            }


            /*  ---------------------------------------------  */
            /*    Écriture des réponses dans le fichier TXT    */
            /*  ---------------------------------------------  */

            if (q.getTypeQuestion() == TypeQuestion.QCMSOLO || q.getTypeQuestion() == TypeQuestion.QCMMULTI) {

                QCMReponse reponse = (QCMReponse) q.getReponse();

                for (QCMReponseItem item : reponse.getReponsesItem()) {

                    questionEnTxt += "\n{REPONSEQCM}\n";
                    questionEnTxt += item.isValide() ? "1\n" : "0\n"; // 1 si la réponse est valide. Sinon 0
                    questionEnTxt += item.getTexte();

                }


            } else if (q.getTypeQuestion() == TypeQuestion.ASSOCIATION) {

                AssociationReponse reponse = (AssociationReponse) q.getReponse();

                for (AssociationReponseItem item : reponse.getReponsesItem()) {

                    questionEnTxt += "\n{ASSOCIATION}\n";
                    questionEnTxt += item.getTexte();
                    questionEnTxt += "\n{REPONSE}\n";
                    questionEnTxt += item.getReponse().getTexte();

                }

            } else if (q.getTypeQuestion() == TypeQuestion.ELIMINATION) {

                EliminationReponse reponse = (EliminationReponse) q.getReponse();

                for (EliminationReponseItem item : reponse.getReponsesItems()) {

                    questionEnTxt += "\n{ELIMINIATION}\n";
                    // Structure : ordre suppression, pts suppression, bonne réponse (1, sinon 0)
                    questionEnTxt += item.getOrdreSuppression() + "," + item.getPtsSuppression() + "," + (item.isBonneReponse() ? "1" : "0");
                    questionEnTxt += "\n";
                    questionEnTxt += item.getTexte();

                }

            }

            pwQuestion.print(questionEnTxt);

            pwQuestion.close();

            return true;

        } catch (Exception e) {

            System.out.println("Erreur" + e.getMessage());
            return false;

        }

    }

    /**
     * Modifier une question dans la base de données
     * @param q Question à modifier (L'UID doit correspondre à une question existante)
     * @return True si elle a pu être modifiée, sinon false
     */
    public boolean modifierQuestion ( Question q ) {

        String sRet = "";

        try {

            Scanner sc = new Scanner(new FileInputStream("data/app/DonneesQuestions.csv"), StandardCharsets.UTF_8);

            while (sc.hasNextLine()) {

                String ligne = sc.nextLine();

                Scanner scLigne = new Scanner(ligne).useDelimiter(",");
                
                if (scLigne.next().equals(q.getUID())) {

                    String nomRessource = q.getNotion().getRessource().getNom().replaceAll(",", "|");
                    String nomNotion    = q.getNotion().getNom().replaceAll(",", "|");

                    sRet += q.getUID()                  + "," +
                            q.getDifficulte()           + "," +
                            q.getTypeQuestion()         + "," +
                            nomRessource                + "," +
                            nomNotion                   + "," +
                            q.getTempsReponse()         + "," +
                            q.getNbPoints();

                    if (sc.hasNextLine()) sRet += "\n";

                } else {

                    sRet += ligne + "\n";

                }

            }

            PrintWriter pw = new PrintWriter(new FileOutputStream("data/app/DonneesQuestions.csv"));

            pw.print(sRet);

            pw.close();

            return this.sauvegarderDonneesQuestion(q);

        } catch (Exception e) {

            System.out.println("Erreur modification fichier question : " + e.getMessage());
            return false;

        }

    }


    public boolean supprimerQuestion ( Question q ) {

        String sRet = "";

        try {

            Scanner sc = new Scanner(new FileInputStream("data/app/DonneesQuestions.csv"), StandardCharsets.UTF_8);

            while (sc.hasNextLine()) {

                String ligne = sc.nextLine();

                Scanner scLigne = new Scanner(ligne).useDelimiter(",");
                
                if (!scLigne.next().equals(q.getUID())) 
                    sRet += ligne + "\n";

            }

            Files.delete(Paths.get("data", "app", q.getUID()+".txt"));

            PrintWriter pw = new PrintWriter(new FileOutputStream("data/app/DonneesQuestions.csv"));

            pw.print(sRet);

            pw.close();

            return true;

        } catch (Exception e) {

            System.out.println("Erreur suppression Question : " +e.getMessage());
            return false;

        }

    }




    /*  --------------------------  */
    /*                              */
    /*    CHARGEMENT DES DONNEES    */
    /*                              */
    /*  --------------------------  */



    /**
     * Méthode permettant de charger les données dans la base de données
     * - Paramètres (Ressources et Notions)
     * - Questions (Avec les réponses)
     */
    public void chargerDonnees() {

        // Création du dossier et des fichiers de données
        try {

            File dossierDonnes    = new File ("data");
            File dossierDonnesQst = new File ("data", "app");
            Path pathRessource    = Paths.get("data", "DonneesRessource.csv");
            Path pathNotions      = Paths.get("data", "DonneesNotions.csv");
            Path pathQuestions    = Paths.get("data", "app", "DonneesQuestions.csv");

            if (!dossierDonnes.exists())
                dossierDonnes.mkdir();

            if (!dossierDonnesQst.exists())
                dossierDonnesQst.mkdir();

            if (!Files.exists(pathRessource))
                Files.createFile(pathRessource);

            if (!Files.exists(pathNotions))
                Files.createFile(pathNotions);

            if (!Files.exists(pathQuestions))
                Files.createFile(pathQuestions);


        } catch (Exception e) {

            System.out.println("Erreur création fichiers : " + e.getMessage());

        }

        // Chargement des données (Ressource et Notion)
        this.chargerDonneesParametres();

        // Chargement des questions et réponses
        this.chargerDonneesQuestions();

    }

    /**
     * Chargement des données paramètres, Ressource et Notion
     */
    private void chargerDonneesParametres() {

        try {

            Scanner sc = new Scanner(new FileInputStream("data/DonneesRessource.csv"), StandardCharsets.UTF_8);

            while (sc.hasNextLine()) {

                String  ligne   = sc.nextLine();
                Scanner scLigne = new Scanner(ligne).useDelimiter(",");

                String  uidRessource  = scLigne.next();
                String  codeRessource = scLigne.next().replaceAll("[|]", ",");
                String  nomRessource  = scLigne.next().replaceAll("[|]", ",");

                Ressource rsc = new Ressource(nomRessource, codeRessource, uidRessource);

                this.chargerNotionsRessource(rsc);

                this.ctrl.ajouterRessource(rsc);

            }

        } catch (Exception e) {

            System.out.println("Erreur lors de l'ouverture du fichier des paramètres : " + e.getMessage());

        }

    }

    /**
     * Initialise les nouvelles notions dans la ressource parent
     * @param rsc Ressource parent
     */
    private void chargerNotionsRessource (Ressource rsc) {

        try {

            Scanner sc = new Scanner(new FileInputStream("data/DonneesNotions.csv"), StandardCharsets.UTF_8);

            while (sc.hasNextLine()) {

                String ligne = sc.nextLine();

                Scanner scLigne = new Scanner(ligne).useDelimiter(",");

                if (scLigne.hasNext() && scLigne.next().equals(rsc.getUID())) {

                    while (scLigne.hasNext()) {

                        String nomNotion = scLigne.next().replaceAll("[|]", ",");
                        Notion not       = new Notion(nomNotion, rsc);

                        rsc.ajouterNotion(not);

                    }

                }

            }
        } catch (Exception e) {

            System.out.println("Erreur lecture fichier notions (Récupération) : " + e.getMessage());

        }


    }

    /**
     * Chargement des questions dans la base de données
     */
    private void chargerDonneesQuestions() {

        String pathFichierDonnees   = Paths.get( "data", "app", "DonneesQuestions.csv").toString();

        try {

            Scanner sc = new Scanner(new FileInputStream(pathFichierDonnees), StandardCharsets.UTF_8);

            while (sc.hasNextLine()) {

                Scanner scLigne = new Scanner(sc.nextLine()).useDelimiter(",");

                String  uidQuestion = scLigne.next();
                String  difficulte  = scLigne.next();
                String  typeQst     = scLigne.next();
                String  ressource   = scLigne.next().replaceAll("[|]", ",");
                String  nomNotion   = scLigne.next().replaceAll("[|]", ",");
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

                Reponse             rsp         = this.getReponseFichier(uidQuestion, typeQuestion);
                ArrayList<Fichier>  ensFichier  = this.getFichiersTexte(uidQuestion);

                Question nouvelleQuestion = new Question(uidQuestion, txtQuestion, tempsRsp, nbPoints, typeQuestion, rsp, difficulteQuestion, notion, ensFichier);

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

        String  pathFichierQuestion = Paths.get( "data", "app", uid+".txt").toString();
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
     * Récupérer la liste avec l'ensemble des fichiers utilisés pour la question
     * @param uid UID de la question pour récupérer les données dans le fichier TXT
     * @return L'ensemble des fichiers
     */
    private ArrayList<Fichier> getFichiersTexte ( String uid ) {

        String              pathFichierQuestion = Paths.get( "data", "app", uid+".txt").toString();
        ArrayList<Fichier>  ensFichier = new ArrayList<>();
        boolean             lectureFichier = false;

        try {

            Scanner sc = new Scanner(new FileInputStream(pathFichierQuestion));
            
            while (sc.hasNextLine()) {
                
                String ligne = sc.nextLine();
                
                if (ligne.equals("{FICHIERS}")) {
                    lectureFichier = true;
                    continue;
                }

                if (ligne.startsWith("{"))
                    lectureFichier = false;
                
                if (lectureFichier) {
                    
                    Scanner scLigne = new Scanner(ligne).useDelimiter(",");

                    String nomFichier  = scLigne.next();
                    String lienFichier = scLigne.next();

                    Fichier fichier = new Fichier(lienFichier, nomFichier);
                    ensFichier.add(fichier);
                    
                }
            }

            return ensFichier;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lecture fichiers de données question : " + e.getMessage());
            return ensFichier;
        }

    }

    /**
     * Permets de récupérer la réponse à la question en fonction de son UID et de son type (Qui sera retourné)
     * @param uid UID de la question
     * @param typeQuestion Type de la question
     * @return L'objet Reponse correspondant à l'UID de la question
     */
    private Reponse getReponseFichier ( String uid, TypeQuestion typeQuestion ) {

        String              pathFichierQuestion = Paths.get("data", "app", uid+".txt").toString();
        QCMReponse          qcmReponse          = new QCMReponse();
        AssociationReponse  associationReponse  = new AssociationReponse();
        EliminationReponse  eliminationReponse  = new EliminationReponse();
        
        try {

            Scanner sc = new Scanner(new FileInputStream(pathFichierQuestion));

            while (sc.hasNextLine()) {

                String ligne = sc.nextLine();

                // Association des bonnes ou mauvaises réponses pour les QCM Solo et Multiples
                if (typeQuestion == TypeQuestion.QCMSOLO || typeQuestion == TypeQuestion.QCMMULTI) {

                    if (ligne.equals("{TEXTEEXPLICATION}"))
                        qcmReponse.ajouterTexteExplication(sc.nextLine().replaceAll("\\\\n", "\n").replaceAll("\\\\t", "\t"));

                    if (ligne.equals("{REPONSEQCM}")) {

                        boolean estValide   = sc.nextLine().equals("1");
                        String txtQuestion  = sc.nextLine().replaceAll("\\\\n", "\n").replaceAll("\\\\t", "\t");
                        QCMReponseItem item = new QCMReponseItem(txtQuestion, estValide);

                        qcmReponse.ajouterItem(item);

                    }

                } else if (typeQuestion == TypeQuestion.ASSOCIATION) { // Association pour les réponses de type associations

                    if (ligne.equals("{TEXTEEXPLICATION}"))
                        associationReponse.ajouterTexteExplication(sc.nextLine().replaceAll("\\\\n", "\n").replaceAll("\\\\t", "\t"));

                    if (ligne.equals("{ASSOCIATION}")) {

                        String txtDefinition = sc.nextLine().replaceAll("\\\\n", "\n").replaceAll("\\\\t", "\t");

                        AssociationReponseItem definition = new AssociationReponseItem(txtDefinition);

                        if (sc.hasNextLine() && sc.nextLine().equals("{REPONSE}")) {

                            String txtReponse              = sc.nextLine().replaceAll("\\\\n", "\n").replaceAll("\\\\t", "\t");
                            AssociationReponseItem reponse = new AssociationReponseItem(txtReponse);

                            definition        .setReponse(reponse);
                            associationReponse.ajouterDefinition(definition);

                        } else {
                            
                            System.out.println("Erreur lors de la lecture des associations !");
                            
                        }

                    }

                } else if (typeQuestion == TypeQuestion.ELIMINATION) {

                    if (ligne.equals("{TEXTEEXPLICATION}"))
                        eliminationReponse.ajouterTexteExplication(sc.nextLine().replaceAll("\\\\n", "\n").replaceAll("\\\\t", "\t"));

                    if (ligne.equals("{ELIMINIATION}")) {

                        Scanner donneesElimination  = new Scanner(sc.nextLine()).useDelimiter(",");
                        int     ordreSuppression    = Integer.parseInt(donneesElimination.next());
                        float   ptsSuppression      = Float.parseFloat(donneesElimination.next());
                        boolean reponse             = donneesElimination.next().equals("1");
                        String  txtQuestion         = sc.nextLine().replaceAll("\\\\n", "\n").replaceAll("\\\\t", "\t");
                        EliminationReponseItem item = new EliminationReponseItem(txtQuestion, ordreSuppression, ptsSuppression, reponse);

                        eliminationReponse.ajouterReponseItem(item);

                    }

                }
                
            }

            if (typeQuestion == TypeQuestion.QCMSOLO || typeQuestion == TypeQuestion.QCMMULTI)
                return qcmReponse;
            else if (typeQuestion == TypeQuestion.ASSOCIATION)
                return associationReponse;
            else if (typeQuestion == TypeQuestion.ELIMINATION)
                return eliminationReponse;
            else
                return null;

        } catch (Exception e) {
            
            System.out.println("Erreur de lecture du fichier de données de la réponse : " + e.getMessage());
            return null;
            
        }

    }




    /*  -------------------------------------------  */
    /*                                               */
    /*    GESTION DES FICHIERS DANS LES QUESTIONS    */
    /*                                               */
    /*  -------------------------------------------  */

    /**
     * Permets de remettre la liste des fichiers pour la question à vide
     */
    public void nouvelleQuestionFichier () { this.ensFichiers = new ArrayList<>(); }

    /**
     * Ajouter un nouveau fichier à la question en cours d'édition
     * @param lienFichier Lien du fichier ouvert
     * @param data Données de la question (Ressource et Notion)
     * @return True si le fichier a été ajoutée à l'appliation et à la liste
     */
    public boolean ajouterFichierQuestion (String lienFichier, DonneesCreationQuestion data) {

        try {

            Path fichierOrig         = Paths.get(lienFichier);
            String nomRessource      = data.ressource().getCode().replaceAll("/", ".");
            String nomNot            = data.notion()   .getNom() .replaceAll("/", ".");
            File dossierDesti        = new File("data/app/rsc/" + nomRessource + "/" + nomNot);
            int nbFichiers           = dossierDesti.listFiles().length;
            String nouveauNomFichier = "fic" + String.format("%05d", nbFichiers) + this.getFileExtension(lienFichier);
            Path fichierDesti        = Paths.get("data", "app", "rsc", nomRessource, nomNot, nouveauNomFichier);

            // Copie du fichier
            Files.copy(fichierOrig, fichierDesti, StandardCopyOption.REPLACE_EXISTING);

            Fichier nouveauFichier = new Fichier(fichierDesti.toString(), fichierOrig.getFileName().toString());

            this.ensFichiers.add(nouveauFichier);

            return true;

        } catch( Exception e ) {

            System.out.println("Impossible d'ajouter le fichier : " + e.getMessage());
            return false;

        }

    }

    /**
     * Récupérer l'extension du fichier
     * @param nom Nom du fichier complet (Avec le chemin)
     * @return Le nom de l'extension (png, pdf,...)
     */
    private String getFileExtension(String nom) {

        int lastIndexOf = nom.lastIndexOf(".");

        if (lastIndexOf == -1)
            return "";

        return nom.substring(lastIndexOf);
    }

    /**
     * Récupérer tous les fichiers ajoutées pour la question
     * @return L'ensemble des fichiers
     */
    public ArrayList<Fichier> getFichiersQuestion() { return this.ensFichiers; }

    /**
     * Récupérer le fichier (de java) en fonction de son nom
     * @param nbIndex Index dans la liste
     * @return Le fichier, sinon null
     */
    public File getFichierQuestion(int nbIndex) {

        if (nbIndex < 0 || nbIndex >= this.ensFichiers.size()) return null;

        return new File(this.ensFichiers.get(nbIndex).lienFic());

    }

    /**
     * Supprimer un fichier dans la question en cours de modification, et dans le système de fichier
     * @param nbIndex Index dans la liste
     * @return True si le fichier a été supprimée, sinon false
     */
    public boolean supprimerFichierQuestion (int nbIndex) {

        if (nbIndex < 0 || nbIndex >= this.ensFichiers.size()) return false;

        if (this.ensFichiers.get(nbIndex) == null) return false;

        try {

            // Suppression du fichier

            File fichier = new File(this.ensFichiers.get(nbIndex).lienFic());

            if (!fichier.exists()) return false;

            Files.delete(Paths.get(this.ensFichiers.get(nbIndex).lienFic()));
            this.ensFichiers.remove(nbIndex);

            return true;

        } catch (Exception e) {

            System.out.println("Erreur de suppression du fichier : " + e.getMessage());
            return false;

        }

    }

    /**
     * Permet de charger les fichiers de la question
     * Intervient lorsqu'il y a une modification d'une question avec des fichiers déjà existant
     * @param ensFichiers Liste de l'ensemble des fichiers
     */
    public void chargerFichiersQuestion (ArrayList<Fichier> ensFichiers) { this.ensFichiers = ensFichiers; }


}
