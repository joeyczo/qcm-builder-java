package qcm.metier;

import java.io.*;
import java.util.Scanner;

public class GenerationPageQuestion {

    private String cheminFichierDonnees;
    private String cheminFichierSortie;

    public GenerationPageQuestion(String cheminFichierDonnees, String cheminFichierSortie) {
        this.cheminFichierDonnees = cheminFichierDonnees;
        this.cheminFichierSortie = cheminFichierSortie;

        //Lire le fichier cheminFichierDonnees, en déduire le type de la question et appeller la bonne méthode en fonction de celui-ci.
        try {
            Scanner sc = new Scanner(new FileInputStream(cheminFichierDonnees));

            // TODO : Obtenir le type de question

            String type= "sous le sunlight des tropiques";
            switch(type){
                case "ELIMINATION" : genererPageElimination();

                case "QCM" : genererPageQCM();

                case "LIAISON" : genererPageLiaison();
            }


        } catch (FileNotFoundException e) {
            System.out.println((e.getMessage()));
        }
    }
    
    private void genererPageQCM() {
        try (Scanner sc = new Scanner(new FileInputStream(cheminFichierDonnees));
             FileWriter writer = new FileWriter(cheminFichierSortie)) {
            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append("<!DOCTYPE html>\n");
            htmlContent.append("<html lang=\"fr\">\n");
            htmlContent.append("<head>\n");
            htmlContent.append("    <meta charset=\"UTF-8\">\n");
            htmlContent.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
            htmlContent.append("    <title>QCM</title>\n");
            htmlContent.append("    <link rel=\"stylesheet\" href=\"styles.css\">\n");
            htmlContent.append("</head>\n");
            htmlContent.append("<body>\n");
            htmlContent.append("<h1>Évaluation QCM</h1>\n");

            String line;
            int questionNumber = 1;

            while ((line = sc.nextLine()) != null) {
                String[] parts = line.split(";", 2);
                if (parts.length < 2) {
                    continue; // Skip malformed lines
                }

                String question = parts[0];
                String[] options = parts[1].split(";");

                htmlContent.append("<div class=\"question\">\n");
                htmlContent.append("    <p>Question ").append(questionNumber++).append(": ").append(question).append("</p>\n");
                htmlContent.append("    <ul>\n");

                for (int i = 0; i < options.length; i++) {
                    htmlContent.append("        <li>\n");
                    htmlContent.append("            <input type=\"radio\" name=\"q").append(questionNumber).append("\" id=\"q").append(questionNumber).append("-option").append(i).append("\">\n");
                    htmlContent.append("            <label for=\"q").append(questionNumber).append("-option").append(i).append("\">").append(options[i]).append("</label>\n");
                    htmlContent.append("        </li>\n");
                }

                htmlContent.append("    </ul>\n");
                htmlContent.append("</div>\n");
            }

            htmlContent.append("<button>Soumettre</button>\n");
            htmlContent.append("</body>\n");
            htmlContent.append("</html>\n");

            writer.write(htmlContent.toString());
        } catch (IOException e) {
            System.err.println("Erreur lors de la génération de la page HTML : " + e.getMessage());
        }
    }

    private void genererPageLiaison() {
        // TODO
    }

    private void genererPageElimination() {
        // TODO
    }
}