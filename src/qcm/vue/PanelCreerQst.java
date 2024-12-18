package qcm.vue;

import qcm.metier.*;
import qcm.Controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PanelCreerQst extends JPanel implements ActionListener
{
    private FrameCreerQst           frameParent;
    private Controleur              ctrl;

    private JTextField              txtNbPoints;
    private JTextField              txtTempsRep;
    
    private JCheckBox               cbUnique;

    private JComboBox<String>       cmbRessources;
    private JComboBox<String>       cmbNotions;
    private JComboBox<String>       cmbTypeQst;

    private ArrayList<RoundButton>  lstBtnDiff;

    private JButton                 btnValider;

    private JPanel                  pnlBoutons;

    private String                  ressourceLaPlusLongue;

    public PanelCreerQst(FrameCreerQst parent, Controleur ctrl)
    {
        this.frameParent = parent;
        this.ctrl        = ctrl;

        // Initialisation des composants
        this.txtNbPoints = new JTextField("1.0", 5); // par défaut "1.0" pour le nombre de points
        this.txtTempsRep = new JTextField("00:30", 5); // par défaut "00:30" pour le temps

        this.ressourceLaPlusLongue = "";

        this.cmbRessources = new JComboBox<>();
        for (int i = 0; i < this.cmbRessources.getItemCount(); i++)
            this.cmbTypeQst.selectWithKeyChar(cmbTypeQst.getItemAt(i).charAt(0));


        this.cmbRessources.addItem("-- Choisir une ressource --");

        this.cmbNotions    = new JComboBox<>();
        for (int i = 0; i < this.cmbNotions.getItemCount(); i++)
            this.cmbTypeQst.selectWithKeyChar(cmbTypeQst.getItemAt(i).charAt(0));

        this.cmbNotions.addItem("-- Choisir une notion --");

        this.cmbNotions.setEnabled(false);
        this.cmbTypeQst = new JComboBox<>();
        for (int i = 0; i < this.cmbTypeQst.getItemCount(); i++)
            this.cmbTypeQst.selectWithKeyChar(cmbTypeQst.getItemAt(i).charAt(0));

        this.cmbTypeQst.addItem("-- Choisir un type de question --");

        for (int i = 0; i < this.ctrl.getNbRessource(); i++)
        {
            if ( this.ressourceLaPlusLongue.length() < this.ctrl.getRessource(i).getNom().length())
                this.ressourceLaPlusLongue = this.ctrl.getRessource(i).getNom();

            this.cmbRessources.addItem(this.ctrl.getRessource(i).getNomCourt());
        }

        for (TypeQuestion type : TypeQuestion.values())
            this.cmbTypeQst.addItem(type.getNomType());

        this.lstBtnDiff = new ArrayList<RoundButton>();
        for (DifficulteQuestion diff : DifficulteQuestion.values()) {

            RoundButton btn = new RoundButton(diff.getTexte(), diff.getCouleur());
            btn.setBackground(diff.getCouleur());
            btn.setPreferredSize(new Dimension(40, 40));
            btn.setFont(new Font("Arial", Font.BOLD, 8));
            btn.setForeground(Color.WHITE);
            btn.setHorizontalAlignment(SwingConstants.CENTER);

            this.lstBtnDiff.add(btn);
        }

        this.cbUnique = new JCheckBox("Choix multiple");
        this.cbUnique.setVisible(false);

        this.pnlBoutons = new JPanel();
        this.pnlBoutons.setLayout(new GridLayout(1,4));

        this.btnValider = new JButton("Valider");

        // Configurer le layout
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espacement entre les composants
        gbc.fill = GridBagConstraints.HORIZONTAL; // Pour que les composants prennent toute la largeur de leur cellule

        // Ligne 1 : Label et champs texte pour nombre de points et temps de réponse
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(new JLabel("Nombre de points :"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(txtNbPoints, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        this.add(new JLabel("Temps de réponse (min:sec) :"), gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        this.add(txtTempsRep, gbc);

        // Ligne 2 : Label et JComboBox pour Ressource
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(new JLabel("Ressource :"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(cmbRessources, gbc);

        // Ligne 3 : Label et JComboBox pour Notion
        gbc.gridx = 2;
        gbc.gridy = 0;
        this.add(new JLabel("Notion :"), gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        this.add(cmbNotions, gbc);

        // Ligne 4 : Label et boutons de niveaux
        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(new JLabel("Difficulté :"), gbc);

        for(JButton btn : lstBtnDiff)
            this.pnlBoutons.add(btn);

        gbc.gridx = 1;
        gbc.gridy = 2;
        this.add(this.pnlBoutons, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        this.add(new JLabel("Type de questions : "), gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        this.add(this.cmbTypeQst, gbc);

        //Ligne 5 : Bouton Valider
        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(this.btnValider, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        this.add(this.cbUnique, gbc);

        this.cmbRessources.addActionListener(this);
        this.cmbNotions   .addActionListener(this);
        this.cmbTypeQst   .addActionListener(this);

        for(RoundButton btn : lstBtnDiff)
            btn.addActionListener(this);

        this.btnValider.addActionListener(this);
        this.cbUnique.addActionListener(this);
        verifierTemps();
    }

    public void actionPerformed(ActionEvent e)
    {
        String typeQ = (String) this.cmbTypeQst.getSelectedItem();

        if("QCM".equals(typeQ)){
            this.cbUnique.setVisible(true);
        } else{
            this.cbUnique.setVisible(false);
        }

        revalidate();
        repaint();

        if (e.getSource() == this.cmbRessources)
        {

            if (this.cmbRessources.getSelectedIndex() != 0)
            {
                this.cmbNotions.removeAllItems();

                Ressource rsc = this.ctrl.getRessource((String) this.cmbRessources.getSelectedItem());

                if (rsc == null) return;

                this.cmbNotions.addItem("-- Choisir un type de question --");

                for (Notion n : rsc.getAlNotion())
                    this.cmbNotions.addItem(n.getNomCourt());

                this.cmbNotions.setEnabled(true);
            }

        }

        if (e.getSource() instanceof RoundButton)
        {
            RoundButton btn = (RoundButton) e.getSource();

            // On désactive tous les autres boutons
            for (RoundButton autreBtn : this.lstBtnDiff) {
                autreBtn.setBackground(Color.LIGHT_GRAY);
                autreBtn.setSelected(false);
            }

            // On active maintenant le bouton cliqué
            btn.setBackground(btn.getCouleurBase());
            btn.setSelected(true);

        }

        // Vérification et envoie des données
        if (e.getSource() == this.btnValider) {

            try {
                double nbPoints         = Double.parseDouble(this.txtNbPoints.getText());
                String tempsReponse     = this.txtTempsRep.getText();
                String diff             = "";
                String ressource        = (String) this.cmbRessources.getSelectedItem();
                String notion           = (String) this.cmbNotions.getSelectedItem();


                for (RoundButton btn : this.lstBtnDiff)
                    if (btn.isSelected()) diff = btn.getTexte();

                if (this.cmbRessources.getSelectedIndex() == 0) {
                    this.afficherMessageErreur("Veuillez sélectionner une ressource");
                    return;
                }

                if (this.cmbNotions.getSelectedIndex() == 0) {
                    this.afficherMessageErreur("Veuillez sélectionner une notion");
                    return;
                }

                if (nbPoints <= 0) {
                    this.afficherMessageErreur("Le nombre de point doît être supérieur à 0 !");
                    return;
                }

                /*
                if (!tempsReponse.contains(":")) {
                    this.afficherMessageErreur("Veuillez indiquer un temps de réponse valide (min:sec)");
                    return;
                }
                else {
                    String [] tabTempsReponse = tempsReponse.split(":");
                    int       minute          = -1;
                    int       seconde         = -1;

                    try {
                        minute  = Integer.parseInt(tabTempsReponse[0]);
                        seconde = Integer.parseInt(tabTempsReponse[1]);
                    }
                    catch (Exception exception) {
                        exception.getMessage();
                        this.afficherMessageErreur("Veuillez indiquer un temps de réponse valide (min:sec)");
                        return;
                    }

                    if ( minute == -1 || tabTempsReponse[0].length() > 2 || minute < 0 || minute > 59 ) {
                        this.afficherMessageErreur("Le temps en minute doit être entre 0 et 59");
                        return;
                    }
                    else if ( seconde == -1 || tabTempsReponse[1].length() > 2 || seconde <= 0 || seconde > 59 ) {
                        this.afficherMessageErreur("Le temps en seconde doit être entre 1 et 59");
                        return;
                    }
                    System.out.println(tabTempsReponse[0].length());
                }*/


                if (diff.isEmpty()) {
                    this.afficherMessageErreur("Veuillez sélectionner une difficulté pour la question");
                    return;
                }


                if (this.cmbTypeQst.getSelectedIndex() == 0) {
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

                switch ((String) this.cmbTypeQst.getSelectedItem()) {
                    case "QCM à réponse multiple"   -> type = TypeQuestion.QCMMULTI;
                    case "Association"              -> type = TypeQuestion.ASSOCIATION;
                    case "Élimination"              -> type = TypeQuestion.ELIMINATION;
                }

                DonneesCreationQuestion data = new DonneesCreationQuestion(nbPoints, verifierTemps(), rsc, not, difficulte, type, null);

                System.out.println("C'EST BON ?!");
                new FrameInfosQuestion(this.ctrl, data);
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