package qcm;

import qcm.metier.Notion;
import qcm.metier.Ressource;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;
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

                    rsc.ajouterNotion(new Notion(scLigne.next()));

                }

                this.ctrl.ajouterRessource(rsc);

            }


        } catch (Exception e) {

            System.out.println("Erreur lors de l'ouverture du fichier : " + e.getMessage());

        }

    }


}
