package qcm.metier;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        if (!this.mapNotions.containsKey(n)) return false;

        Map<DifficulteQuestion, Integer> newMap = new HashMap<>();

        System.out.println("Ajout de la difficulté " + diff.getTexte() + " (" + num + ") pour la Notion " + n.getNom());

        newMap.put(diff, num);

        this.mapNotions.put(n, newMap);

        return true;
    }


    /**
     * Permets de connaître le nombre de notions sélectionnées dans le tableau
     * @return Le nombre de notions sélectionnées
     */
    public int getNotionSelected () {

        return this.mapNotions.size();

    }

}
