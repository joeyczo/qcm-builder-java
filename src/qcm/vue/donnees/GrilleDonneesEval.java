package qcm.vue.donnees;
import qcm.Controleur;
import qcm.metier.DifficulteQuestion;
import qcm.metier.Notion;
import qcm.metier.Ressource;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class GrilleDonneesEval extends AbstractTableModel
{
    private Controleur  ctrl;

    private Ressource   rsc;

    private String[]    tabEntetes;
    private Object[][]  tabDonnees;
    private int         nbLigne;

    public GrilleDonneesEval (Controleur ctrl, Ressource r)
    {
        this.ctrl   = ctrl;
        this.rsc    = r;
        Notion notion;

        List<Notion> lstNotions = new ArrayList<Notion>();
        for(int i=0; i<this.ctrl.getNbNotion(r); i++)
            lstNotions.add(this.ctrl.getNotion(r, i));

        this.nbLigne = this.ctrl.getNbNotion(r) + 1;

        this.tabEntetes = new String[]{"Nom notion", "Select", "TF", "F", "M", "D", "∑"};
        this.tabDonnees = new Object[this.nbLigne][tabEntetes.length];

        int taille = lstNotions.size();
        for ( int lig=0; lig<taille; lig++) {
            notion = lstNotions.get(lig);

            tabDonnees[lig][0] = notion.getNom();
            tabDonnees[lig][1] = false;
            tabDonnees[lig][2] = 0;
            tabDonnees[lig][3] = 0;
            tabDonnees[lig][4] = 0;


            tabDonnees[lig][5] = 0;
            tabDonnees[lig][6] = 0;
        }


        tabDonnees[this.nbLigne-1][0] = "Nb questions";
        tabDonnees[this.nbLigne-1][1] = false;
        tabDonnees[this.nbLigne-1][2] = 0;
        tabDonnees[this.nbLigne-1][3] = 0;
        tabDonnees[this.nbLigne-1][4] = 0;
        tabDonnees[this.nbLigne-1][5] = 0;
        tabDonnees[this.nbLigne-1][6] = 0;
    }

    public int    getColumnCount()                 { return this.tabEntetes.length;          }
    public int    getRowCount   ()                 { return this.tabDonnees.length;          }
    public String getColumnName (int col)          { return this.tabEntetes[col];            }
    public Object getValueAt    (int row, int col) { return this.tabDonnees[row][col];       }
    public Class  getColumnClass(int c)            { return getValueAt(0, c).getClass();}

    public boolean isCellEditable(int row, int col)
    {
        return col != 0 && col != DifficulteQuestion.values().length+2 && row != this.nbLigne-1;
    }

    public void setValueAt(Object value, int row, int col)
    {

        if (col != 0 && col != DifficulteQuestion.values().length+2) {

            if (this.getValueAt(row, col) instanceof Boolean) {
                this.tabDonnees[row][col] = value;

                // On ajoute à la liste la notion à générer
                if (!(boolean) value) {
                    this.resetLigne(row);
                    this.ctrl.supprimerNotion(this.getNotionLigne(row));
                } else {
                    this.ctrl.ajouterNotion(this.getNotionLigne(row));
                }

                this.fireTableCellUpdated(row, col);
            }

            if (this.getValueAt(row, col) instanceof Integer) {
                if ((boolean) this.getValueAt(row, 1)) {

                    if (this.ctrl.ajouterDifficulteQuestion(this.getNotionLigne(row), this.getDifficulteColonne(col), (Integer) value)) {

                        this.tabDonnees[row][col] = value;
                        this.sommeLigne(row);
                        this.fireTableCellUpdated(row, col);
                    }

                }
            }

        }
    }

    /**
     * Récupérer la Notion associée à une ligne du tableau
     * @param lig Index de la ligne du tableau
     * @return La Notion si elle existe, sinon Null
     */
    private Notion getNotionLigne (int lig) {

        if (lig < 0 || lig >= this.nbLigne) return null;

        return this.ctrl.getNotion(this.rsc, (String) this.tabDonnees[lig][0]);

    }

    /**
     * Permets de récupérer la difficulté de la colonne sélectionnée
     * @param col Index de la colonne
     * @return La difficulté
     */
    private DifficulteQuestion getDifficulteColonne (int col) {

        int i = 1;
        DifficulteQuestion dif = DifficulteQuestion.FACILE;

        for (DifficulteQuestion d : DifficulteQuestion.values())
        {
            i++;
            dif = d;
            if (i == col) break;
        }

        return dif;

    }

    private void sommeLigne(int lig) {

        int somme = 0;

        for (int i = 2; i < DifficulteQuestion.values().length+2; i++)
            somme += (Integer) this.getValueAt(lig, i);

        this.tabDonnees[lig][DifficulteQuestion.values().length+2] = somme;
        this.fireTableCellUpdated(lig, DifficulteQuestion.values().length+2);
        this.calculerTotal();

    }

    private void resetLigne(int lig) {

        for (int i = 2; i < DifficulteQuestion.values().length+2; i++) {
            this.tabDonnees[lig][i] = 0;
            this.fireTableCellUpdated(lig, i);
        }

        this.sommeLigne(lig);

    }

    public void calculerTotal() {

        int[] sommes = new int[DifficulteQuestion.values().length];

        // Initaliser le tableau
        for (int i = 0; i < DifficulteQuestion.values().length; i++)
            sommes[i] = 0;

        // Calculer le nombre de questions en fonction de leurs difficultés
        for ( int i = 0; i < this.nbLigne-1; i++)
            for ( int j = 2; j < DifficulteQuestion.values().length+2; j++)
                sommes[j-2] += (Integer) this.tabDonnees[i][j];


        // Afficher le nombre de questions en fonction de leurs difficultés
        for (int i = 2; i < DifficulteQuestion.values().length+2; i++) {
            this.tabDonnees[this.nbLigne-1][i] = sommes[i-2];
            this.fireTableCellUpdated(this.nbLigne-1, i);
        }

        // Afficher la somme finale (Nb de questions pour l'évaluation)
        int sommesTotal = 0;
        for (int i : sommes)
            sommesTotal += i;

        this.tabDonnees[this.nbLigne-1][DifficulteQuestion.values().length+2] = sommesTotal;
        this.fireTableCellUpdated(this.nbLigne, DifficulteQuestion.values().length+2);

    }
}