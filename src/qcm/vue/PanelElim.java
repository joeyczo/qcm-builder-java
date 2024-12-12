package qcm.vue;
import qcm.Controleur;
import qcm.metier.Notion;
import qcm.metier.Ressource;
import qcm.vue.donnees.GrilleDonneesEval;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.util.*;
import java.util.List;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PanelElim extends JPanel implements ActionListener
{
    private FrameInfosQuestion frameParent;

    private JButton           btnSubmit, btnAdd, btnDelete, btnExplain;
    private JTextArea         txtQst;
    private JScrollPane       scrollPane;
    private JPanel            panelHaut, panelMilieu, panelBas;
    private ButtonGroup       btg;

    private Controleur ctrl;

    private final int nbReponsesMax  = 4;
    private int nbReponsesActuelles = 0;

    private List<JPanel> listReponses = new ArrayList<JPanel>();


    public PanelElim( FrameInfosQuestion parent, Controleur ctrl ){
        this.frameParent = parent;
        this.ctrl = ctrl;

        this.setLayout(new BorderLayout());

        this.panelHaut   = new JPanel();
        this.panelMilieu = new JPanel();
        this.panelBas    = new JPanel();

        this.txtQst     = new JTextArea  (7, 50);
        this.scrollPane = new JScrollPane(txtQst, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.btnSubmit  = new JButton    ("Sauvegarder");
        this.btnAdd     = new JButton    (new ImageIcon("src/data/img/add.png"));
        this.btnDelete  = new JButton    (new ImageIcon("src/data/img/delete.png"));
        this.btnExplain = new JButton    (new ImageIcon("src/data/img/edit.png"));
        this.btg        = new ButtonGroup();

        this.panelHaut  .setLayout( new GridBagLayout() );
        this.panelMilieu.setLayout( new BoxLayout(panelMilieu, BoxLayout.Y_AXIS));
        this.panelMilieu.setBorder( BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets             = new Insets(5, 5, 5, 5);
        gbc.fill               = GridBagConstraints.HORIZONTAL;

        // Panel haut
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.panelHaut.add(new JLabel("Question :"), gbc);

        gbc.gridy     = 1;
        gbc.gridwidth = 10;
        gbc.ipadx     = 50;
        gbc.ipady     = 50;
        this.panelHaut.add(scrollPane, gbc);

        // Panel milieu
        this.createAnswerPanel();
        this.createAnswerPanel();

        // Panel bas
        this.panelBas.add(btnAdd);
        this.panelBas.add(btnExplain);
        this.panelBas.add(btnSubmit);

        this.add(panelHaut  , BorderLayout.NORTH);
        this.add(panelMilieu, BorderLayout.CENTER);
        this.add(panelBas   , BorderLayout.SOUTH);

        this.btnSubmit .addActionListener(this);
        this.btnAdd    .addActionListener(this);
        this.btnExplain.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            createAnswerPanel();
        }
        if(e.getSource() == btnExplain){
            JTextArea   zoneTexte  = new JTextArea(10, 30);
            JScrollPane scrollPane = new JScrollPane(zoneTexte);

            int result = JOptionPane.showConfirmDialog(
                    this,
                    scrollPane,
                    "Ajouter explication",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION)
            {
                String explication = zoneTexte.getText();
                System.out.println("Explication entrée : " + explication);
            }
            return;
        }
    }

    public boolean createAnswerPanel() {
        if (this.nbReponsesActuelles < this.nbReponsesMax) {
            JPanel reponsePanel = new JPanel();
            reponsePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Alignement à gauche avec padding*

            // Ajout du bouto*n de suppression
            JButton btnDelete = new JButton(new ImageIcon("src/data/img/delete.png"));
            btnDelete.addActionListener(e -> supprimerReponse(reponsePanel));


            reponsePanel.add(btnDelete);

            // Création du texte principal
            JTextArea txtRep = new JTextArea(3, 40);
            txtRep.setLineWrap(true);
            txtRep.setWrapStyleWord(true);
            reponsePanel.add(txtRep);

            JScrollPane scrollTxtRep = new JScrollPane(txtRep);
            reponsePanel.add(scrollTxtRep);

            JPanel scorePanel = new JPanel();
            scorePanel.setLayout(new GridLayout(2, 1, 5, 5));

            JTextField ordrePrio = new JTextField(2);
            ordrePrio.setHorizontalAlignment(JTextField.CENTER);
            scorePanel.add(ordrePrio);

            JRadioButton rbValide = new JRadioButton();
            btg.add(rbValide);
            scorePanel.add(rbValide);

            JTextField noteSoustraite = new JTextField(2);
            noteSoustraite.setHorizontalAlignment(JTextField.CENTER);
            scorePanel.add(noteSoustraite);

            reponsePanel.add(scorePanel);

            addTextFieldKeyListener(ordrePrio, noteSoustraite, rbValide);

            this.panelMilieu.add(reponsePanel);
            this.panelMilieu.revalidate();
            this.panelMilieu.repaint();

            listReponses.add(reponsePanel);
            nbReponsesActuelles++;
            return true;
        }
        return false;
    }


    public boolean supprimerReponse(JPanel reponsePanel) {
        if (nbReponsesActuelles > 2) {
            this.panelMilieu.remove(reponsePanel);
            this.panelMilieu.revalidate();
            this.panelMilieu.repaint();
            this.listReponses.remove(reponsePanel);
            nbReponsesActuelles--;
            return true;
        }
        return false;
    }

    public List<JPanel> getReponses() {
        return listReponses;
    }

    public String getQuestion(){
        return txtQst.getText();
    }

    private void addTextFieldKeyListener(JTextField textField1, JTextField textField2, JRadioButton radioButton) {
        KeyAdapter keyListener = new KeyAdapter()
        {
            public void keyReleased(KeyEvent e)
            {
                radioButton.setEnabled(textField1.getText().isEmpty() && textField2.getText().isEmpty());
                if(!radioButton.isEnabled()) radioButton.setSelected(false);
            }
        };
        textField1.addKeyListener(keyListener);
        textField2.addKeyListener(keyListener);
    }

    //Return true s'il n'y a pas d'erreur
    private boolean verifierErreurs() {
        boolean uneCaseCochee = false;
        boolean plusieursCasesCochees = false;

        // Parcours des panneaux de réponses
        for (JPanel reponsePanel : listReponses) {
            boolean caseCocheeDansCePanel = false;
            JTextField ordrePrioField = null;
            JTextField noteSoustraiteField = null;

            // Parcours des composants du panneau actuel
            for (Component comp : reponsePanel.getComponents()) {
                if (comp instanceof JPanel) {
                    JPanel innerPanel = (JPanel) comp;
                    for (Component innerComp : innerPanel.getComponents()) {
                        // Vérifie si un des composants est un JRadioButton sélectionné
                        if (innerComp instanceof JRadioButton) {
                            JRadioButton radioButton = (JRadioButton) innerComp;
                            if (radioButton.isSelected()) {
                                if (caseCocheeDansCePanel) {
                                    plusieursCasesCochees = true;
                                } else {
                                    caseCocheeDansCePanel = true;
                                    uneCaseCochee = true;
                                }
                            }
                        } else if (innerComp instanceof JTextField) {
                            JTextField textField = (JTextField) innerComp;
                            if (ordrePrioField == null) {
                                ordrePrioField = textField;
                            } else if (noteSoustraiteField == null) {
                                noteSoustraiteField = textField;
                            }
                        }
                    }
                }
            }

            // Vérifie la cohérence entre ordrePrio et noteSoustraite
            if (ordrePrioField != null && noteSoustraiteField != null) {
                boolean ordrePrioVide = ordrePrioField.getText().trim().isEmpty();
                boolean noteSoustraiteVide = noteSoustraiteField.getText().trim().isEmpty();
                if (ordrePrioVide != noteSoustraiteVide) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Erreur : Les champs 'ordre de priorité' et 'note soustraite' doivent soit être tous les deux remplis, soit tous les deux vides.",
                            "Erreur de validation",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return false;
                }
            }

            if (plusieursCasesCochees) {
                JOptionPane.showMessageDialog(
                        this,
                        "Erreur : Plusieurs cases sont cochées dans une réponse.",
                        "Erreur de validation",
                        JOptionPane.ERROR_MESSAGE
                );
                return false;
            }
        }

        if (!uneCaseCochee) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erreur : Au moins une case doit être cochée.",
                    "Erreur de validation",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }

        if (txtQst.getText().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erreur : Entrez une question.",
                    "Erreur de validation",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        return true;
    }


}