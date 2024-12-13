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
    private Controleur         ctrl;


    private ArrayList<JButton>     lstBtnSupp;
    private ArrayList<JTextArea>   lstTxtReponses;
    private ArrayList<JCheckBox>   lstBtnValideReponse;
    private ArrayList<JScrollPane> lstScrollTexte;
    private ArrayList<JTextField>  lstOrdrePrioQst;
    private ArrayList<JTextField>  lstPointEnMoins;


    private JButton           btnSubmit, btnAdd, btnExplain;
    private JTextArea         txtQst;
    private JScrollPane       scrollPane;
    //private JPanel            panelHaut, panelMilieu, panelBas;
    private ButtonGroup       btg;


    private int nbReponsesActuelles = 0;

    private List<JPanel> listReponses = new ArrayList<JPanel>();


    public PanelElim( FrameInfosQuestion parent, Controleur ctrl ){

        this.frameParent = parent;
        this.ctrl        = ctrl;

        this.setLayout(new BorderLayout());

//        this.panelHaut   = new JPanel();
//        this.panelMilieu = new JPanel();
//        this.panelBas    = new JPanel();

        this.lstBtnSupp          = new ArrayList<JButton>();
        this.lstTxtReponses      = new ArrayList<JTextArea>();
        this.lstBtnValideReponse = new ArrayList<JCheckBox>();
        this.lstScrollTexte      = new ArrayList<JScrollPane>();
        this.lstOrdrePrioQst     = new ArrayList<JTextField>();
        this.lstPointEnMoins     = new ArrayList<JTextField>();


        this.txtQst     = new JTextArea  (5, 1);
        this.scrollPane = new JScrollPane(this.txtQst, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.btnSubmit  = new JButton    ("Enregistrer");
        this.btnAdd     = new JButton    (new ImageIcon("src/data/img/add.png"));
        this.btnExplain = new JButton    (new ImageIcon("src/data/img/edit.png"));
        this.btg        = new ButtonGroup();

//        this.panelHaut  .setLayout( new GridBagLayout() );
//        this.panelMilieu.setLayout( new BoxLayout(panelMilieu, BoxLayout.Y_AXIS));
//        this.panelMilieu.setBorder( BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets             = new Insets(5, 5, 5, 5);
        gbc.fill               = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(new JLabel("Question :"), gbc);

        gbc.gridy     = 1;
        gbc.gridwidth = 5;
        gbc.ipadx     = 600;
        gbc.ipady     = 50;
        this.add(this.scrollPane, gbc);

//        // Panel milieu
//        this.createAnswerPanel();
//        this.createAnswerPanel();

        for( int cpt = 0; cpt < 2; cpt ++)
        {
            this.lstBtnSupp         .add(new JButton());
            this.lstTxtReponses     .add(new JTextArea (5, 1));
            this.lstScrollTexte     .add(new JScrollPane(this.lstTxtReponses.getLast(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
            this.lstBtnValideReponse.add(new JCheckBox());

            gbc.gridwidth  = 1;
            gbc.gridheight = 2;
            gbc.ipadx      = 0;
            gbc.ipady      = 0;
            gbc.gridx      = 0;
            gbc.gridy      = 2 + cpt;
            this.lstBtnSupp.get(cpt).setOpaque(false);
            this.lstBtnSupp.get(cpt).setContentAreaFilled(false);
            this.lstBtnSupp.get(cpt).setBorderPainted(false);
            this.lstBtnSupp.get(cpt).setCursor(new Cursor(Cursor.HAND_CURSOR));
            this.lstBtnSupp.get(cpt).setIcon(new ImageIcon("src/data/img/delete.png"));
            this.add(this.lstBtnSupp.get(cpt), gbc);

            gbc.gridx     = 1;
            gbc.gridwidth = 5;
            gbc.ipadx     = 400;
            gbc.ipady     = 30;
            this.add(this.lstScrollTexte.get(cpt), gbc);

            gbc.gridheight = 1;
            gbc.gridx      = GridBagConstraints.RELATIVE;
            gbc.gridy      = GridBagConstraints.RELATIVE;
            this.add(this.lstOrdrePrioQst.get(cpt), gbc);

            gbc.gridheight = 1;
            gbc.gridx      = GridBagConstraints.RELATIVE;
            gbc.gridy      = GridBagConstraints.RELATIVE;
            this.add(this.lstPointEnMoins.get(cpt), gbc);

            gbc.gridheight = 2;
            gbc.gridx      = GridBagConstraints.RELATIVE;
            gbc.gridwidth  = GridBagConstraints.RELATIVE;
            gbc.ipadx      = 0;
            gbc.ipady      = 0;
            this.add(this.lstBtnValideReponse.get(cpt), gbc);

            // TODO JE ME SUIS ARRETER LA


//            JTextField ordrePrio = new JTextField(3);
//            ordrePrio.setHorizontalAlignment(JTextField.CENTER);
//            scorePanel.add(ordrePrio);
//
//            JRadioButton rbValide = new JRadioButton();
//            btg.add(rbValide);
//            scorePanel.add(rbValide);
//
//            JTextField noteSoustraite = new JTextField(3);
//            noteSoustraite.setHorizontalAlignment(JTextField.CENTER);
//            scorePanel.add(noteSoustraite);

        }


// TODO faire en sorte de voir quand la sourie passe sur le bouton
        this.btnAdd.setOpaque(false);
        this.btnAdd.setContentAreaFilled(false);
        this.btnAdd.setBorderPainted(false);
        this.add(btnAdd);

        this.btnExplain.setOpaque(false);
        this.btnExplain.setContentAreaFilled(false);
        this.btnExplain.setBorderPainted(false);
        this.add(btnExplain);

        this.add(btnSubmit);

//        this.add(panelHaut  , BorderLayout.NORTH);
//        this.add(panelMilieu, BorderLayout.CENTER);
//        this.add(panelBas   , BorderLayout.SOUTH);



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

    public void createAnswerPanel()
    {
            JPanel reponsePanel = new JPanel();
            reponsePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Alignement à gauche avec padding*

            // Ajout du bouton de suppression
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

            JTextField ordrePrio = new JTextField(3);
            ordrePrio.setHorizontalAlignment(JTextField.CENTER);
            scorePanel.add(ordrePrio);

            JRadioButton rbValide = new JRadioButton();
            btg.add(rbValide);
            scorePanel.add(rbValide);

            JTextField noteSoustraite = new JTextField(3);
            noteSoustraite.setHorizontalAlignment(JTextField.CENTER);
            scorePanel.add(noteSoustraite);

            reponsePanel.add(scorePanel);

            addTextFieldKeyListener(ordrePrio, noteSoustraite, rbValide);

//            this.panelMilieu.add(reponsePanel);
//            this.panelMilieu.revalidate();
//            this.panelMilieu.repaint();

            listReponses.add(reponsePanel);
            nbReponsesActuelles++;

    }


    public boolean supprimerReponse(JPanel reponsePanel) {
        if (nbReponsesActuelles > 2) {
//            this.panelMilieu.remove(reponsePanel);
//            this.panelMilieu.revalidate();
//            this.panelMilieu.repaint();
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