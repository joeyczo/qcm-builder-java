package qcm.metier;

import java.util.ArrayList;
import java.util.List;

public class Notion
{
    private final int LIMITE = 35;

    private String          nom;
    private List<Question>	alQuestions;
    private Ressource       ressource;

    public Notion	(String nom, Ressource r) {
        this.nom         = nom;
        this.ressource   = r;
        this.alQuestions = new ArrayList<>();
    }

    public String getNom() {
        return this.nom;
    }

    /**
     * Rétrecie le nom de la notion si il est trop long
     * @return Rétrecie le nom de la notion à 40 caractères si il est trop long
     */
    public String getNomCourt()
    {
        String nom = this.nom;

        if ( this.nom.length() >= LIMITE )
        {
            nom = nom.substring(0, LIMITE - 3);
            nom += "...";
        }

        return nom ;
    }


    /*  --------------------------  */
    /*    Gestion des Questions     */
    /*  --------------------------  */

    /**
     * Récupérer la ressource parent
     * @return La ressource parent à la Notion
     */
    public Ressource getRessource() {
        return ressource;
    }

    /**
     * Permets de récupérer le nombre de question d'une notion en fonction de sa difficulté
     * @param diff Difficulté à récupérer
     * @return Le nombre de questions
     */
    public int getNbQuestions ( DifficulteQuestion diff ) {

        int nbQst = 0;

        for (Question q : this.alQuestions)
            if (q.getDifficulte() == diff) nbQst++;

        return nbQst;

    }

    /**
     * Permets de récupérer le nombre de questions pour la Notion
     * @return Le nombre de questions disponible
     */
    public int getNbQuestions() {
        return this.alQuestions.size();
    }

    /**
     * Récupérer la question à l'indice i si elle existe
     * @param i Indice
     * @return La question de la Notion à l'indice i
     */
    public Question getQuestion ( int i ) {

        if (i < 0 || i >= this.getNbQuestions()) return null;

        return this.alQuestions.get(i);

    }

    /**
     * Ajouter une question à la Notion
     * @param q Nouvelle question
     */
    public void ajouterQuestion ( Question q ) {

        this.alQuestions.add(q);

    }

    /**
     * Modifier une question à un indice i
     * @param i Indice de la question à modifier
     * @param q Nouvelles données pour la question
     * @return True si la question a été modifié, sinon false
     */
    public boolean modifierQuestion ( int i, Question q ) {

        if (i < 0 || i >= this.getNbQuestions()) 	return false;
        if (this.alQuestions.get(i) == null)		return false;

        this.alQuestions.remove(i);
        this.alQuestions.add(i , q);

        return true;

    }

    /**
     * Supprimer une Question à l'indice i
     * @param i Indice à supprimer
     * @return True si elle a été supprimée, sinon false
     */
    public boolean supprimerQuestion ( int i ) {

        if (i < 0 || i >= this.getNbQuestions()) 	return false;
        if (this.alQuestions.get(i) == null)		return false;

        this.alQuestions.remove(i);

        return true;

    }

    /**
     * Récupérer l'indice d'une question dans la liste
     * @param q Question à récupérer
     * @return L'indince de la question dans la liste, sinon -1
     */
    public int getIndiceQuestion(Question q) {

        if (q == null) return -1;

        int indice = -1;

        for (int i = 0; i < this.alQuestions.size(); i++)
            if (this.alQuestions.get(i).equals(q)) {
                indice = i;
                break;
            }

        return indice;


    }

}
