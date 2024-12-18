package qcm.metier;


import java.util.*;

public class GenerationEvals {

    private Map<Notion, Map<DifficulteQuestion, Integer>>  mapNotions;

    public GenerationEvals() {

        this.mapNotions = new HashMap<>();

    }
    
    public void resetGeneration() {

        this.mapNotions = new HashMap<>();

    }

    /**
     * Ajouter une nouvelle notion à la liste de génération
     * @param n Notion à ajouter
     * @return True si elle a été ajouté
     */
    public boolean ajouterNotion(Notion n) {

        if (this.mapNotions.containsKey(n)) return false;

        System.out.println("La notion " + n.getNom() + " a été ajouté");

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

        System.out.println("La notion " + n.getNom() + " a été supprimé !!!");

        this.mapNotions.remove(n);

        return true;

    }

    /**
     * Permets d'ajouter un nombre de questions en fonction de la difficulté et de la Notion
     * @param n Notion à ajouter la question
     * @param diff Difficulté de la notion
     * @param num Nombre de questions à ajouter
     * @return Savoir si le nombre de questions a pu être ajouté
     */
    public boolean ajouterDifficulteQuestion (Notion n, DifficulteQuestion diff, Integer num) {

        if (!this.mapNotions.containsKey(n))    return false;
        if (n.getNbQuestions(diff) < num)       return false;

        Map<DifficulteQuestion, Integer> mapNotion = this.mapNotions.get(n);

        System.out.println(num + " >= " + n.getNbQuestions(diff));

        mapNotion.put(diff, num);

        this.mapNotions.put(n, mapNotion);

        return true;
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
                System.out.println("Ajout de +" + this.mapNotions.get(n).get(d) + " pour : " + nbQuestion);
            }


        }

        return nbQuestion;

    }

    /**
     * Permets de générer une évaluation ainsi que tous les fichiers
     * @param path Lien vers la destination
     */
    public void genererEvaluation ( String path ) {

        System.out.println("Génération en cours ...");

        // Vérification du nombre de question
        if (this.getNbQuestions() < 0) return;

        // TODO : Générer les questions

    }

}
