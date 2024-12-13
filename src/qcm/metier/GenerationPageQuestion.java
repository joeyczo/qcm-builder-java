package qcm.metier;

import java.io.*;
import java.util.Scanner;

public class GenerationPageQuestion {

    private String cheminFichierDonnees;
    private String cheminFichierSortie;

    private String contenu;

    public GenerationPageQuestion(String cheminFichierDonnees, String cheminFichierSortie) {
        this.cheminFichierDonnees = cheminFichierDonnees;
        this.cheminFichierSortie = cheminFichierSortie;

        //Lire le fichier cheminFichierDonnees, en déduire le type de la question et appeller la bonne méthode en fonction de celui-ci.
        try {
            Scanner sc = new Scanner(new FileInputStream(cheminFichierDonnees));

            contenu = "";


            // HEAD

            //TODO : Scanner pour obtenir le title
            String title = "Questionnaire";
            contenu += genererHead(title);


            // BODY


            // ACCUEIL

            //TODO : Scanner pour obtenir ces 3 attributs
            String intituleRessource = "R2.01 Introduction au développement objet";
            int nbNotions = 4;
            String tempsEstime = "10mn30";

            //Accueil
            contenu += "<body>\n" +
                    "    <div class=\"accueil-card\">\n" +
                    "        <h1>" + intituleRessource + "</h1>\n" +
                    "        <p>" + nbNotions + " notions - Environ " + tempsEstime + "</p>\n" +
                    "        <button>Démarrer le questionnaire</button>\n" +
                    "    </div>\n" +
                    "</body>";



            // TODO : Scanner, tant qu'il reste des questions :

            // TODO : Obtenir le type de question
            String type= "sous le sunlight des tropiques";

            switch(type){
                case "ELIMINATION" : genererPageElimination();

                case "QCM" : genererPageQCM();

                case "LIAISON" : genererPageLiaison();
            }



            //Fin, écriture dans cheminFichierSortie
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(cheminFichierSortie))) {
                writer.write(contenu);
            } catch (Exception e) {
                System.out.println("Une erreur est survenue lors de la géneration du questionnaire : " + e.getMessage());
            }

        } catch (FileNotFoundException e) {
            System.out.println((e.getMessage()));
        }
    }


    private String genererHead(String title) {
        String head = "<!DOCTYPE html>\n" +
                "<html lang=\"fr\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>" + title + "</title>\n" +
                "    <link href=\"style.css\" rel=\"stylesheet\">\n" +
                "    <script src=\"script.js\"></script>\n" +
                "</head>";

        return head;
    }
    
    private String genererPageQCM() {
        String sRep = "";

        return sRep;
    }

    private String genererPageLiaison() {
        return "";
    }

    private String genererPageElimination() {
        return "";
    }
}