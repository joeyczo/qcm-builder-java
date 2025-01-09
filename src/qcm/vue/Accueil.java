package qcm.vue;

import qcm.Controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Accueil extends JFrame implements ActionListener {

    private JPanel  pnlBtns;

    private JButton btnCreerQuestion;
    private JButton btnCreerEval;
    private JButton btnParametres;
    private JButton btnListeQst;

    private JLabel lblTitre;

    private FrameParametres  frameParametres;
    private FrameCreerQst    frameCreerQuestion;
    private FrameGenererEval frameGenererEval;
    private FrameListeQst    frameListeQst;

    private Font             fontGenerale;

    private Controleur      ctrl;


    public Accueil(Controleur ctrl) {

        this.ctrl          = ctrl;
        this.fontGenerale  = new Font("Arial", Font.BOLD, 16);


        this.setTitle("Accueil");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        ImageIcon img = new ImageIcon("../data/img/icon.png");
        this.setIconImage(img.getImage());

        this.pnlBtns           = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets             = new Insets(10, 10, 10, 10);
        gbc.fill               = GridBagConstraints.HORIZONTAL;
        gbc.gridx              = 0;
        gbc.gridy              = GridBagConstraints.RELATIVE;

        this.lblTitre         = new JLabel ("QCM Builder");
        this.btnCreerQuestion = new JButton("Créer une question");
        this.btnCreerEval     = new JButton("Générer une évaluation");
        this.btnListeQst      = new JButton("Liste des questions");
        this.btnParametres    = new JButton("Paramètres");

        this.lblTitre        .setFont(new Font("Arial", Font.BOLD, 30));
        this.btnCreerQuestion.setFont(this.fontGenerale);
        this.btnCreerEval    .setFont(this.fontGenerale);
        this.btnListeQst     .setFont(this.fontGenerale);
        this.btnParametres   .setFont(this.fontGenerale);


        Dimension btnSize = new Dimension(600, 50);
        this.btnCreerQuestion.setPreferredSize(btnSize);
        this.btnCreerEval    .setPreferredSize(btnSize);
        this.btnParametres   .setPreferredSize(btnSize);
        this.btnListeQst     .setPreferredSize(btnSize);

        this.btnCreerQuestion.addActionListener(this);
        this.btnCreerEval    .addActionListener(this);
        this.btnParametres   .addActionListener(this);
        this.btnListeQst     .addActionListener(this);

        this.pnlBtns.add(this.lblTitre);
        this.pnlBtns.add(this.btnCreerQuestion, gbc);
        this.pnlBtns.add(this.btnCreerEval    , gbc);
        this.pnlBtns.add(this.btnListeQst     , gbc);
        this.pnlBtns.add(this.btnParametres   , gbc);

        this.add(this.pnlBtns);

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == this.btnParametres)
            this.frameParametres = new FrameParametres(this, this.ctrl);

        if(e.getSource() == this.btnCreerQuestion)
            this.frameCreerQuestion = new FrameCreerQst(this, this.ctrl, null);

        if(e.getSource() == this.btnCreerEval)
            this.frameGenererEval = new FrameGenererEval(this, this.ctrl);

        if(e.getSource() == this.btnListeQst)
            this.frameListeQst = new FrameListeQst(this,this.ctrl);

    }
}