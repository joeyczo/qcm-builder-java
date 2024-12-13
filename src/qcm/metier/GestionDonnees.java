package qcm.metier;

import qcm.Controleur;

import java.util.ArrayList;
import java.util.List;

public class GestionDonnees {

    private List<Ressource> lstRessource;

    public GestionDonnees(Controleur ctrl) {

        this.lstRessource = new ArrayList<Ressource>();

    }

    /*  --------------------------  */
    /*	  Gestion des ressources    */
    /*  --------------------------  */

    /**
     * Permets d'ajouter une ressource dans la liste des ressources si c'est possible
     * @param ressource Ressource à ajouter
     * @return True si on peut ajouter, sinon false
     */
    public boolean ajouterRessource(Ressource ressource) {

        boolean trouve = false;

        for (Ressource r : this.lstRessource)
            if (r.getNom().equals(ressource.getNom())) {
                trouve = true;
                break;
            }

        if (trouve) return false;

        this.lstRessource.add(ressource);

        return true;

    }

    /**
     * Récupérer le nombre de ressources présente dans la liste
     * @return Nombre de ressources
     */
    public int getNbRessource () {
        return this.lstRessource.size();
    }

    /**
     * Récupérer la ressource à l'index i de la liste des ressources
     * @param indice Indince à récupérer
     * @return La ressource si elle existe, sinon null
     */
    public Ressource getRessource ( int indice ) {

        if (indice >= this.lstRessource.size()) return null;

        return this.lstRessource.get(indice);

    }

    /**
     * Permets de récupérer une ressource à partir de son nom
     * @param nom Nom de la ressource
     * @return La ressource ou null si elle n'existe pas
     */
    public Ressource getRessource ( String nom ) {

        Ressource trouves = null;

        for (Ressource r : this.lstRessource)
            if (r.getNom().equals(nom) || r.getNomCourt().equals(nom)) {
                trouves = r;
                break;
            }

        return trouves;

    }

    /*  --------------------------  */
    /*	    Gestion des notions     */
    /*  --------------------------  */

    /**
     * Permets de récupérer le nombre de notions dans une ressource
     * @param ressource Ressource
     * @return Le nombre de notion de la ressource
     */
    public int getNbNotions (Ressource ressource) {

        if (ressource == null) return -1;

        return ressource.getAlNotion().size();

    }

    /**
     * Permets
     * @param ressource
     * @param notion
     * @return
     */
    public boolean ajouterNotion(Ressource ressource, Notion notion) {

        if (ressource == null)  return false;
        if (notion    == null)  return false;

        boolean trouves = false;
        List<Notion> lstNotions = ressource.getAlNotion();

        for (Notion n : lstNotions)
            if (n.getNom().equals(notion.getNom())) {
                trouves = true;
                break;
            }

        if (trouves) return false;

        ressource.ajouterNotion(notion);

        return true;

    }

    /**
     * Permets de récupérer la notion d'une ressource à partir de son indice
     * @param ressource Ressource afin de récuéprer la notion
     * @param i Indice de la notion dans le tableau
     * @return La Notion, sinon null si elle n'existe pas
     */
    public Notion getNotion ( Ressource ressource, int i ) {

        if (ressource == null) return null;
        if (i < 0 || i > this.getNbNotions(ressource)) return null;

        List<Notion> lstNotions = ressource.getAlNotion();

        return lstNotions.get(i);

    }

    /**
     * Permets de récupérer la notion d'une ressource à partir de son nom
     * @param ressource Ressource afin de récuéprer la notion
     * @param nom Nom de la notion à récupérer
     * @return La Notion, sinon null si elle n'existe pas
     */
    public Notion getNotion ( Ressource ressource, String nom ) {

        if (ressource == null) return null;

        List<Notion> lstNotions = ressource.getAlNotion();

        Notion trouve = null;

        for (Notion n : lstNotions)
            if (n.getNom().equals(nom) || n.getNomCourt().equals(nom)) {
                trouve = n;
                break;
            }

        return trouve;

    }

}
