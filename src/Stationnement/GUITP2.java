package Stationnement;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class GUITP2 {
    private JPanel panel1;
    private JLabel labelLogo;
    private JPanel panelNumeros;
    private JPanel panelOperations;
    private JPanel panelComptant;
    private JTextArea champMessage; ////à utiliser
    private JButton bouton25;
    private JButton bouton100;
    private JButton bouton200;
    private JPanel panelCredit;

    private JTextArea zoneRecu; ////à utiliser
    private JButton boutonRapport;
    private JFormattedTextField champNumeroCarte;   ////à utiliser
    private JFormattedTextField champDateExp;       ////à utiliser
    private JButton boutonValiderDateExp;
    private JPanel panelControles;
    private JButton boutonMax;
    private JButton boutonPlus;
    private JButton boutonMoins;
    private JButton boutonOK;
    private JButton boutonAnnuler;
    private JPanel panelRecu;
    private JPanel panelGauche;
    private JPanel panelDroite;
    private JPanel panelMessages;

    private EcouteurNumero ecouteurNumero;
    private EcouteurCarteCredit ecouteurCarteCredit;
    private EcouteurMonnaie ecouteurMonnaie;
    private EcouteurControles ecouteurControles;
    private EcouteurEntree ecouteurEntree;

    //// variables utiles pour vous
    String place =""; //place de stationnement choisie
    Borne borne; // borne à créer dans le constructeur

    //formatage
    public GUITP2() throws ParseException {

        labelLogo.setIcon(new ImageIcon("logo.png"));
        boutonValiderDateExp.setMargin(new Insets(10,0, 10, 0));
        //Création des écouteurs
        ecouteurNumero = new EcouteurNumero();
        ecouteurCarteCredit = new EcouteurCarteCredit();
        ecouteurMonnaie = new EcouteurMonnaie();
        ecouteurControles = new EcouteurControles();
        ecouteurEntree = new EcouteurEntree();

        // panelNumeros avec la grille
        GridBagLayout gl = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        FlowLayout f = new FlowLayout(FlowLayout.CENTER);
        panelOperations.setLayout(f);

        panelNumeros.setLayout(gl);
        c.fill = GridBagConstraints.BOTH;
        c.weightx =1;
        c.weighty=1;
        for ( int i = 0; i <15 ; i++) {
            JButton temp = new JButton();
            temp.setFont(new Font("Tw Cen MT", Font.BOLD, 16));
            temp.setForeground(new Color(0,174,239));
            temp.addActionListener(ecouteurNumero);
            if  ( i ==0 )
                temp.setText("A");
            else if ( i ==1 )
                temp.setText("B");
            else if ( i==2 )
               temp.setText("G");
            else if (i==3) {
                c.gridwidth = GridBagConstraints.REMAINDER; //end row
                temp.setText("SQ");
            }
            else if (i<=6) {
                c.weightx=1;
                c.gridwidth = 1;
                temp.setText(String.valueOf(i-4));
            } else if (i==7) {
                c.gridwidth = GridBagConstraints.REMAINDER; //end row
                temp.setText(String.valueOf(i-4));
            } else if (i<=10) {
                c.weightx=1;
                c.gridwidth = 1;
                temp.setText(String.valueOf(i-4));
            } else if (i==11) {
                c.gridwidth = GridBagConstraints.REMAINDER; //end row
                temp.setText(String.valueOf(i-4));
            } else if (i<=13) {
                c.weightx=1;
                c.gridwidth = 1;
                temp.setText(String.valueOf(i-4));
            } else {
                c.gridwidth = GridBagConstraints.REMAINDER; //end row
                temp.setText("entrée");
                temp.removeActionListener(ecouteurNumero);
                temp.addActionListener(ecouteurEntree);
            }
            gl.setConstraints(temp, c );
            panelNumeros.add( temp);
        }

        // inscrire les sources à l'écouteur
        bouton25.addActionListener(ecouteurMonnaie);
        bouton100.addActionListener(ecouteurMonnaie);
        bouton200.addActionListener(ecouteurMonnaie);

        boutonMax.addActionListener(ecouteurCarteCredit);
        boutonPlus.addActionListener(ecouteurCarteCredit);
        boutonMoins.addActionListener(ecouteurCarteCredit);
        boutonValiderDateExp.addActionListener(ecouteurCarteCredit);
        boutonOK.addActionListener(ecouteurControles);
        boutonAnnuler.addActionListener(ecouteurControles);
        boutonRapport.addActionListener(ecouteurControles);

        ////1. créer objet Borne
        this.borne = new Borne();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        try {
            champNumeroCarte = new JFormattedTextField(new MaskFormatter("#### #### #### ####"));
            champDateExp = new JFormattedTextField(new MaskFormatter("##/##"));
        }
        catch ( ParseException pe) {
            pe.printStackTrace();
        }
    }
    private void $$$setupUI$$$() {
        createUIComponents();
    }
    private class EcouteurNumero implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            //lettre ou chiffre du bouton qu'on a cliqué dessus
           String lettreChiffre = ((JButton)e.getSource()).getText();
           boutonNumeroLettre_actionPerformed( lettreChiffre);
        }
    }

    private class EcouteurEntree implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boutonEntree_actionPerformed();
        }
    }

    private class EcouteurMonnaie implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if ( e.getSource() == bouton25)
                bouton25_actionPerformed();
            else if ( e.getSource()==bouton100)
                bouton100_actionPerformed();
            else if ( e.getSource() == bouton200)
                bouton200_actionPerformed();
        }
    }

    private class EcouteurCarteCredit implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

             if ( e.getSource() == boutonPlus)
                boutonPlus_actionPerformed();
             else if ( e.getSource() == boutonMoins)
                 boutonMoins_actionPerformed();
            else if (e.getSource() == boutonMax)
                boutonMax_actionPerformed();
            else if ( e.getSource() == boutonValiderDateExp)
                boutonValiderDateExp_actionPerformed();
        }
    }

    private class EcouteurControles implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if ( e.getSource() == boutonOK)
                boutonOK_actionPerformed();
            else if ( e.getSource() == boutonRapport)
                boutonRapport_actionPerformed();
            else if ( e.getSource() == boutonAnnuler)
                boutonAnnuler_actionPerformed();
        }
    }

    /// À COMPLETER
    public void boutonNumeroLettre_actionPerformed(String lettreChiffre) {
        // 2. À compléter, afficher la place choisie dans le champMessage
        // à partir de la lettre ou du chiffre cliqué en paramètre
        place += lettreChiffre;
        champMessage.setText("Votre stationnement : " + place);
    }

    private void boutonEntree_actionPerformed() {
        champMessage.setText("");
        borne.demarrerTransaction(place); //faire afficher les message de la fonction dans champMessage.setText()
        if (!borne.validerCode(place)) {
            place = "";
            champMessage.setText("Le code est invalide");
        } else {
            champMessage.setText("Le code est valide" + "\n" + "Inserez de l'argent ou votre carte de crédit");
        }
    }

    private void bouton25_actionPerformed() {
        //4. à coder
        if (borne.getTransactionEnCours() != null) {
            Piece piece25 = new Piece(25);
            borne.insererPiece(piece25);

            champMessage.setText(borne.getTransactionEnCours().getDureePayee() + " minutes");

//            //long dureeActuelle = borne.getTransactionEnCours().getHeureFin().getMinute();
//            if (borne.getTransactionEnCours().getHeureFin().getMinute() < 120) {
//                Piece piece25 = new Piece(25);
//                borne.insererPiece(piece25);
//                champMessage.setText(borne.getTransactionEnCours().getHeureFin().getMinute() + " minutes");
//            } else {
//                champMessage.setText("Maximum de 2 heures atteint.");
//            }
        } else {
            champMessage.setText("Veuillez d'abord entrer un code de stationnement valide.");
        }

//        Piece piece = new Piece(25);
//        borne.insererPiece(piece);
//        //Duration temps = Duration.between(heuredebut, heurefin)
//        champMessage.setText(borne.getTransactionEnCours().getHeureFin().getMinute() + " minutes"); ///à rechecker
    }

    private void bouton100_actionPerformed() {
        //5. à coder
        if (borne.getTransactionEnCours() != null) {
            Piece piece100 = new Piece(100);
            borne.insererPiece(piece100);
            champMessage.setText(borne.getTransactionEnCours().getDureePayee() + " minutes");
        } else {
            champMessage.setText("Veuillez d'abord entrer un code de stationnement valide.");
        }
//        Piece piece = new Piece(100);
//        borne.insererPiece(piece);
//        //Duration temps = Duration.between(heuredebut, heurefin)
//        champMessage.setText(borne.getTransactionEnCours().getHeureFin().getMinute() + " minutes"); ///à rechecker
    }

    private void bouton200_actionPerformed() {
        //6. à coder
        if (borne.getTransactionEnCours() != null) {
            Piece piece200 = new Piece(200);
            borne.insererPiece(piece200);
            champMessage.setText(borne.getTransactionEnCours().getDureePayee() + " minutes");
        } else {
            champMessage.setText("Veuillez d'abord entrer un code de stationnement valide.");
        }
//        Piece piece = new Piece(200);
//        borne.insererPiece(piece);
//        //Duration temps = Duration.between(heuredebut, heurefin)
//        champMessage.setText(borne.getTransactionEnCours().getHeureFin().getMinute() + " minutes"); ///à rechecker
    }

    private void boutonValiderDateExp_actionPerformed(){
        //7. à coder
        System.out.println("valider date d'expiration");
    }

    private void boutonPlus_actionPerformed() {
        //8. à coder
        System.out.println("bouton +");
    }

    private void boutonMoins_actionPerformed(){
        //9. à coder
        System.out.println("bouton -");
    }

    private void boutonMax_actionPerformed() {
        //10. à coder
        System.out.println("bouton max");
    }

    private void boutonOK_actionPerformed() {
        // 11 à coder
        System.out.println("bouton check");
    }

    private void boutonAnnuler_actionPerformed() {
       //12 à coder
        System.out.println("bouton x rouge");
    }

    private void boutonRapport_actionPerformed() {
        //13 à coder
        System.out.println("bouton rapport");
    }

    public static void main(String[] args) {
        try {
            JFrame frame = new JFrame("GUITP2");
            frame.setContentPane(new GUITP2().panel1);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(930, 815);
            //frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            System.out.println ( new GregorianCalendar(2021, 11, 5).get(Calendar.DAY_OF_WEEK));
        }
        catch ( Exception ex)
        {
            ex.printStackTrace();
        }
    }


    /// rajouter par après (optionel)
    private void actualiserAffichageTransaction() {
        if (borne.getTransactionEnCours() != null) {

            long duree = borne.getTransactionEnCours().getHeureFin().getMinute();
            double cout = borne.getTransactionEnCours().getCoutTotal();
            LocalDateTime heureFin = borne.getTransactionEnCours().getHeureFin();

            java.time.format.DateTimeFormatter formatHeure = java.time.format.DateTimeFormatter.ofPattern("HH:mm");

            String message = String.format("Coût actuel: %.2f $\nDurée totale: %d minutes\nHeure de fin: %s", cout, duree, heureFin.format(formatHeure));

            champMessage.setText(message);
        }
    }
}
