package qcm.metier;


import qcm.Controleur;
import qcm.vue.FrameVisuEval;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class GenerationEvals {

    private Map<Notion, Map<DifficulteQuestion, Integer>>   mapNotions;
    private Controleur                                      ctrl;
    private Ressource                                       ressource;

    public GenerationEvals(Controleur ctrl) {

        this.mapNotions = new HashMap<>();
        this.ctrl       = ctrl;
        this.ressource  = null;

    }
    
    public void resetGeneration() {

        this.mapNotions = new HashMap<>();
        this.ressource  = null;

    }

    /**
     * Modifier la ressource parent de l'évaluation
     * @param ressource Nouvelle ressource
     */
    public void changerRessource ( Ressource ressource ) {
        this.ressource = ressource;
    }

    /**
     * Ajouter une nouvelle notion à la liste de génération
     * @param n Notion à ajouter
     * @return True si elle a été ajouté
     */
    public boolean ajouterNotion(Notion n) {

        if (this.mapNotions.containsKey(n)) return false;

        //System.out.println("La notion " + n.getNom() + " a été ajouté");

        this.mapNotions.put(n, new HashMap<>());

        return true;

    }

    /**
     * Supprimer une notion dans la liste de génération
     * @param n Notion à supprimer
     * @return True si elle a été supprimé
     */
    public boolean supprimerNotion(Notion n) {

        if (!this.mapNotions.containsKey(n)) return false;

        // System.out.println("La notion " + n.getNom() + " a été supprimé !!!");

        this.mapNotions.remove(n);

        return true;

    }

    /**
     * Permets d'ajouter un nombre de questions en fonction de la difficulté et de la Notion
     * @param n Notion à ajouter la question
     * @param diff Difficulté de la notion
     * @param num Nombre de questions à ajouter
     * @return Le plus grand nombre de question qu'il est possible d'ajouter
     */
    public Integer ajouterDifficulteQuestion (Notion n, DifficulteQuestion diff, Integer num) {

        if (!this.mapNotions.containsKey(n))    return 0;


        if (n.getNbQuestions(diff) < num) {

            int maxQst = n.getNbQuestions(diff);

            return this.ajouterDifficulteQuestion(n, diff, maxQst);

        }

        Map<DifficulteQuestion, Integer> mapNotion = this.mapNotions.get(n);

        //System.out.println(num + " >= " + n.getNbQuestions(diff));

        mapNotion.put(diff, num);

        this.mapNotions.put(n, mapNotion);

        return num;
    }


    /**
     * Permets de connaître le nombre de notions sélectionnées dans le tableau
     * @return Le nombre de notions sélectionnées
     */
    public int getNotionSelected () {

        return this.mapNotions.size();

    }

    /**
     * Permets de connaître le nombre de questions qui seront générés
     * @return Le nombre de questions généré dans l'évaluation, -1 si aucune
     */
    public int getNbQuestions() {

        Integer nbQuestion = 0;

        if (this.mapNotions.isEmpty()) return -1;

        Set<Notion> ensNotions  = this.mapNotions.keySet();

        for (Notion n : ensNotions) {

            Set<DifficulteQuestion> ensDiff = this.mapNotions.get(n).keySet();

            for (DifficulteQuestion d : ensDiff) {
                nbQuestion += this.mapNotions.get(n).get(d);
                // System.out.println("Ajout de +" + this.mapNotions.get(n).get(d) + " pour : " + nbQuestion);
            }


        }

        return nbQuestion;

    }

    /**
     * Permets de générer une évaluation ainsi que tous les fichiers
     * @param evalue Indique si le questionnaire est évalué ou non
     * @param path Lien vers la destination
     */
    public void genererEvaluation ( boolean evalue, String path ) {

        System.out.println(this.ressource);
        System.out.println("NOM ::");
        System.out.println(this.ressource.getNom());

        System.out.println("Génération en cours ...");

        // Vérification du nombre de question
        if (this.getNbQuestions() < 0) return;

        // Listes qui contient toutes les notions
        ArrayList<Question> alQuestion = new ArrayList<>();

        for (Notion n : this.mapNotions.keySet()) {

            // Toutes les Questions de la notion
            ArrayList<Question> ensQuestion = new ArrayList<>();

            for ( int i = 0; i < n.getNbQuestions(); i++)
                ensQuestion.add(n.getQuestion(i));

            // On mélange la liste de toutes les questions
            Collections.shuffle(ensQuestion);

            for (DifficulteQuestion diff : this.mapNotions.get(n).keySet()) {

                int nbQuestionsDiff = this.mapNotions.get(n).get(diff);
                int nbQuestionGen   = 0;

                System.out.println(nbQuestionsDiff + " questions pour la diff " + diff + " et la notion " + n.getNom());

                for ( Question q : ensQuestion) {

                    if (q.getDifficulte() == diff && nbQuestionGen < nbQuestionsDiff) {
                        alQuestion.add(q);
                        nbQuestionGen++;
                    }

                }

            }

        }

        // On mélange une dernière fois la liste pour mélanger l'ordre des questions et les difficultés
        Collections.shuffle(alQuestion);


        Evalutation eval = new Evalutation(this.getNbQuestions(), evalue, this.ressource, alQuestion, path);

        new FrameVisuEval(this.ctrl, eval);

    }

    /**
     * Exporter l'évaluation vers le fichier après la visualisation
     * @param e Données de l'évaluation
     */
    public boolean exporterEvaluation( Evalutation e ) {

        if (e == null) return false;

        Path cheminGeneration   = Paths.get(e.pathExp());
        Path cheminGenJS        = Paths.get(e.pathExp(), "script.js");
        Path cheminGenHTML      = Paths.get(e.pathExp(), "index.html");
        Path cheminGenCSS       = Paths.get(e.pathExp(), "style.css");
        Path cheminGenImg       = Paths.get(e.pathExp(), "assets");

        try {


            // Génération du JavaScript

            String sJs = "";

            sJs += "const ressource = `" + e.ressource().getNom() + "`;\n";

            sJs += "const exam      = " + e.evaluation() + ";\n";

            sJs += "const questions = " + GenererJSON.genererJson(e) + ";\n";

            Scanner sc = new Scanner(new FileInputStream("src/data/web/script.js"), StandardCharsets.UTF_8);

            while (sc.hasNextLine())
                sJs += sc.nextLine() + "\n";

            Files.createDirectory(cheminGeneration);

            PrintWriter pw = new PrintWriter(new FileOutputStream(cheminGenJS.toString()));

            pw.print(sJs);

            pw.close();

            // Copie des fichiers HTML et CSS

            Files.copy(Paths.get("src","data", "web", "index.html"), cheminGenHTML, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Paths.get("src", "data", "web", "style.css"), cheminGenCSS, StandardCopyOption.REPLACE_EXISTING);

            // Copie des assets

            copyDir(Paths.get("src", "data", "web", "assets"), cheminGenImg);


            return true;


        } catch (Exception ex) {
            System.out.println("Erreur génération des fichiers de l'évaluation" + ex.getMessage());
            return false;
        }

    }

    public static void copyDir(Path source, Path target) throws IOException {
        Files.walkFileTree(source, new SimpleFileVisitor<Path>() {

            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path targetDir = target.resolve(source.relativize(dir));
                if (!Files.exists(targetDir)) {
                    Files.createDirectory(targetDir);
                }
                return FileVisitResult.CONTINUE;
            }

            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.copy(file, target.resolve(source.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });
    }

}
