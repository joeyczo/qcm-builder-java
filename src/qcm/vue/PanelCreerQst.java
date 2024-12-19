package qcm.vue;

import qcm.metier.*;
import qcm.Controleur;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PanelCreerQst extends JPanel implements ActionListener
{
    private FrameCreerQst           frameParent;
    private Controleur              ctrl;
    private DonneesCreationQuestion data;

    private JTextField              txtNbPoints;
    private JTextField              txtTempsRep;

    private JComboBox<String>       ddlstRessource;
    private JComboBox<String>       ddlstNotions;
    private JComboBox<String>       ddlstTypeQuestion;

    private ArrayList<RoundButton>  lstBtnDiff;

    private JButton                 btnValider;

    private JPanel                  pnlBoutons;

    private String                  ressourceLaPlusLongue;

    public PanelCreerQst(FrameCreerQst parent, Controleur ctrl, DonneesCreationQuestion data)
    {
        this.frameParent    = parent;
        this.ctrl           = ctrl;
        this.data           = data;

        // Initialisation des composants
        this.txtNbPoints           = new JTextField("1.0", 5); // par défaut "1.0" pour le nombre de points
        this.txtTempsRep           = new JTextField("00:30", 5); // par défaut "00:30" pour le temps
        this.ressourceLaPlusLongue = "";

        this.ddlstRessource = new JComboBox<>();
        for (int i = 0; i < this.ddlstRessource.getItemCount(); i++)
            this.ddlstTypeQuestion.selectWithKeyChar(ddlstTypeQuestion.getItemAt(i).charAt(0));


        this.ddlstRessource.addItem("-- Choisir une ressource --");

        this.ddlstNotions    = new JComboBox<>();
        for (int i = 0; i < this.ddlstNotions.getItemCount(); i++)
            this.ddlstTypeQuestion.selectWithKeyChar(ddlstTypeQuestion.getItemAt(i).charAt(0));

        this.ddlstNotions.addItem("-- Choisir une notion --");

        this.ddlstNotions.setEnabled(false);
        this.ddlstTypeQuestion = new JComboBox<>();
        for (int i = 0; i < this.ddlstTypeQuestion.getItemCount(); i++)
            this.ddlstTypeQuestion.selectWithKeyChar(ddlstTypeQuestion.getItemAt(i).charAt(0));

        this.ddlstTypeQuestion.addItem("-- Choisir un type de question --");

        for (int i = 0; i < this.ctrl.getNbRessource(); i++)
        {
            if ( this.ressourceLaPlusLongue.length() < this.ctrl.getRessource(i).getNom().length())
                this.ressourceLaPlusLongue = this.ctrl.getRessource(i).getNom();

            this.ddlstRessource.addItem(this.ctrl.getRessource(i).getNomCourt());
        }

        for (TypeQuestion type : TypeQuestion.values())
            this.ddlstTypeQuestion.addItem(type.getNomType());

        this.lstBtnDiff = new ArrayList<RoundButton>();
        for (DifficulteQuestion diff : DifficulteQuestion.values()) {

            RoundButton btn = new RoundButton(diff.getTexte(), diff.getCouleur());
            btn.setBackground(diff.getCouleur());
            btn.setPreferredSize(new Dimension(45, 45));
            btn.setFont(new Font("Arial", Font.BOLD, 8));
            btn.setForeground(Color.WHITE);
            btn.setHorizontalAlignment(SwingConstants.CENTER);

            this.lstBtnDiff.add(btn);
        }

        this.pnlBoutons = new JPanel();
        this.pnlBoutons.setLayout(new GridLayout(1,4));

        this.btnValider = new JButton("Valider");


        // Configurer le layout
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espacement entre les composants
        gbc.fill   = GridBagConstraints.HORIZONTAL; // Pour que les composants prennent toute la largeur de leur cellule


        // Ligne 1 :
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(new JLabel("Ressource :"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(this.ddlstRessource, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        this.add(new JLabel("Nombre de points :"), gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        this.add(this.txtNbPoints, gbc);

        // Ligne 2 :
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(new JLabel("Notion :"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(this.ddlstNotions, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        this.add(new JLabel("Temps de réponse (min:sec) :"), gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        this.add(this.txtTempsRep, gbc);

        // Ligne 3
        gbc.gridx = 2;
        gbc.gridy = 2;
        this.add(new JLabel("Difficulté :"), gbc);

        for(JButton btn : this.lstBtnDiff)
            this.pnlBoutons.add(btn);

        gbc.gridx = 3;
        gbc.gridy = 2;
        this.add(this.pnlBoutons, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(new JLabel("Type de questions : "), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        this.add(this.ddlstTypeQuestion, gbc);

        //Ligne 4
        gbc.gridx = 3;
        gbc.gridy = 3;
        this.add(this.btnValider, gbc);


        this.ddlstRessource.addActionListener(this);
        this.ddlstNotions   .addActionListener(this);
        this.ddlstTypeQuestion   .addActionListener(this);

        for(RoundButton btn : this.lstBtnDiff)
            btn.addActionListener(this);

        this.btnValider.addActionListener(this);
        verifierTemps();

        //this.txtTempsRep.getDocument().addDocumentListener(this);

        // Charger les données
        if (this.data != null)
            this.chargerDonnees();
    }

    public void actionPerformed(ActionEvent e)
    {
        this.revalidate();
        this.repaint();

        if (e.getSource() == this.ddlstRessource){

            this.ddlstNotions.removeAllItems();

            if (this.ddlstRessource.getSelectedIndex() != 0){

                Ressource rsc = this.ctrl.getRessource((String) this.ddlstRessource.getSelectedItem());

                if (rsc == null) return;

                this.ddlstNotions.addItem("-- Choisir une notion --");

                for (Notion n : rsc.getAlNotion())
                    this.ddlstNotions.addItem(n.getNomCourt());

                this.ddlstNotions.setEnabled(true);
            }
            else {
                this.ddlstNotions.addItem("-- Choisir une notion --");
                this.ddlstNotions.setEnabled(false);
            }
        }

        if (e.getSource() instanceof RoundButton){
            RoundButton btn = (RoundButton) e.getSource();

            for (RoundButton autreBtn : this.lstBtnDiff) {
                autreBtn.setBackground(Color.LIGHT_GRAY);
                autreBtn.setSelected(false);
            }

            btn.setBackground(btn.getCouleurBase());
            btn.setSelected(true);
        }

        if (e.getSource() == this.btnValider) {

            try {
                double nbPoints         = Double.parseDouble(this.txtNbPoints.getText());
                String tempsReponse     = this.txtTempsRep.getText();
                String diff             = "";
                String ressource        = (String) this.ddlstRessource.getSelectedItem();
                String notion           = (String) this.ddlstNotions.getSelectedItem();

                for (RoundButton btn : this.lstBtnDiff)
                    if (btn.isSelected()) diff = btn.getTexte();

                if (this.ddlstRessource.getSelectedIndex() == 0) {
                    this.afficherMessageErreur("Veuillez sélectionner une ressource");
                    return;
                }

                if (this.ddlstNotions.getSelectedIndex() == 0) {
                    this.afficherMessageErreur("Veuillez sélectionner une notion");
                    return;
                }

                if (nbPoints <= 0) {
                    this.afficherMessageErreur("Le nombre de point doît être supérieur à 0 !");
                    return;
                }

                if (diff.isEmpty()) {
                    this.afficherMessageErreur("Veuillez sélectionner une difficulté pour la question");
                    return;
                }

                if (this.ddlstTypeQuestion.getSelectedIndex() == 0) {
                    this.afficherMessageErreur("Veuillez sélectionner un type de question");
                    return;
                }

                Ressource rsc = this.ctrl.getRessource(ressource);

                if (rsc == null) {
                    this.afficherMessageErreur("Impossible de récupérer la ressource !");
                    return;
                }

                Notion    not = this.ctrl.getNotion(rsc, notion);

                DifficulteQuestion difficulte = DifficulteQuestion.TRESFACILE;

                switch (diff) {
                    case "F"    -> difficulte = DifficulteQuestion.FACILE;
                    case "M"    -> difficulte = DifficulteQuestion.MOYEN;
                    case "D"    -> difficulte = DifficulteQuestion.DIFFICILE;
                }

                TypeQuestion type = TypeQuestion.QCMSOLO;

                switch ((String) this.ddlstTypeQuestion.getSelectedItem()) {
                    case "QCM à réponse multiple"   -> type = TypeQuestion.QCMMULTI;
                    case "Association"              -> type = TypeQuestion.ASSOCIATION;
                    case "Élimination"              -> type = TypeQuestion.ELIMINATION;
                }

                DonneesCreationQuestion dataQuestions;

                // Ici on ajoute une nouvelle question
                if (this.data == null) {
                    dataQuestions = new DonneesCreationQuestion(nbPoints, verifierTemps(), rsc, not, difficulte, type, null);
                } else { // Ici on modifie une question existante
                    System.out.println("MODIFICATION");
                    dataQuestions = new DonneesCreationQuestion(nbPoints, verifierTemps(), rsc, not, difficulte, type, this.data.qst());
                }
                new FrameInfosQuestion(this.ctrl, dataQuestions);
                this.frameParent.fermerFenetre();

            } catch (NumberFormatException ne) {
                this.afficherMessageErreur("Veuillez préciser un nombre de points valide !");
                System.out.println("Erreur format points : " + ne.getMessage());
            } catch (Exception ex) {
                this.afficherMessageErreur("Une erreur inconnu vient de se produire");
                ex.printStackTrace();
                System.out.println("Autre erreur : " +ex.getMessage());
            }
        }
    }

    /**
     * Afficher un message d'erreur au client
     * @param message Message à afficher
     */
    private void afficherMessageErreur(String message)
    {
        JOptionPane.showMessageDialog(this, message, "Erreur lors de la validation des données", JOptionPane.ERROR_MESSAGE);
    }

    private String verifierTemps()
    {
        String temps = this.txtTempsRep.getText();
        int minute, seconde;
        int index = temps.indexOf(':');

        if(index != -1){
            seconde = Integer.parseInt(temps.substring(index + 1));
            minute = Integer.parseInt(temps.substring(0, index));

            while(seconde > 59){
                minute++;
                seconde -= 60;
            }
        } else{
            this.afficherMessageErreur("Erreur : le format valide pour le temps est le suivant -> mm:ss");
            return "";
        }

        return ((minute > 10) ? "" + minute : "0" + minute) + ":" + ((seconde > 10) ? "" + seconde : "0" + seconde);
    }

    /*public void insertUpdate(DocumentEvent e) {
        verifierEtMettreAJourTemps();
    }

    public void removeUpdate(DocumentEvent e) {
        verifierEtMettreAJourTemps();
    }

    public void changedUpdate(DocumentEvent e) {
        verifierEtMettreAJourTemps();
    }

    private void verifierEtMettreAJourTemps() {
        String nouveauTemps = verifierTemps();
        if (!nouveauTemps.isEmpty() && !nouveauTemps.equals(this.txtTempsRep.getText())) {
            this.txtTempsRep.setText(nouveauTemps);
        }
    }*/

    /**
     * Charger les données de la question dans le formulaire
     */
    public void chargerDonnees() {

        // Sélection de la ressource et de la notion
        this.ddlstRessource.setSelectedItem(this.data.ressource().getNom());
        this.ddlstNotions.setSelectedItem(this.data.notion().getNom());

        // Nb de points
        this.txtTempsRep.setText(this.data.tempsReponse());
        this.txtNbPoints.setText(String.valueOf(this.data.nbPoints()));

        // Sélection du type de question et bloquage pour empecher la modification
        this.ddlstTypeQuestion.setEnabled(false);
        this.ddlstTypeQuestion.setSelectedItem(this.data.type().getNomType());

        // Sélection de la difficulté
        for (RoundButton btn : this.lstBtnDiff) {
            if (btn.getTexte().equals(this.data.diff().getTexte())) {
                btn.doClick();
                break;
            }
        }

    }
}

class RoundButton extends JButton
{

    private String  texte;
    private Color   couleurBase;
    private boolean selected;

    public RoundButton(String label, Color couleur)
    {
        super(label);
        this.texte       = label;
        this.couleurBase = couleur;
        this.selected    = false;
        setContentAreaFilled(false);
    }

    public Color getCouleurBase() {
        return this.couleurBase;
    }

    public void setSelected(boolean f) {
        this.selected = f;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public String getTexte() {
        return this.texte;
    }

    protected void paintComponent(Graphics g)
    {
        if (getModel().isArmed()) { g.setColor(Color.lightGray); }
        else { g.setColor(getBackground()); }
        g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
        super.paintComponent(g);
    }


    protected void paintBorder(Graphics g)
    {
        g.setColor(getForeground());
        g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
    }


    public boolean contains(int x, int y)
    {
        int radius = getSize().width / 2;
        return (x - radius) * (x - radius) + (y - radius) * (y - radius) <= radius * radius;
    }
}