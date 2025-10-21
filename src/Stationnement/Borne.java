package Stationnement;

import java.time.DayOfWeek;
import java.util.regex.Pattern;

public class Borne {
    //    Stationnement.Borne codeG;    //horaire: 4,25$ dispo: lun à ven 8h à 23h, sam 9h à 23h, dem 13h à 18h
    //    Stationnement.Borne codeSQ;   //horaire: 2,25$ dispo: lun à ven 9h à 21h, sam 9h à 18h
    //    String nomBorne;

    private Transaction transactionEnCours = null; //null = aucune transaction
    private double totalArgentComptant = 0;
    private double totalArgentCredit = 0;
    double tarifG = 4.25;
    double tarifSQ = 2.25;
    private static final Pattern CODE_PATTERN = Pattern.compile("^(G|SQ)\\d{3}$");

//    public Stationnement.Borne(Map<String, Double> tarifs) {
//        this.tarifs = tarifs;
//    }

    public boolean validerCode(String code) {
        //regex qui vérifie que le nom de la borne est correct
        return CODE_PATTERN.matcher(code).matches();
    }

    public void demarrerTransaction(String code) {
        // appelle des fonctions pour :
        //Vérifie si le code est valide
        //Vérifie si on est dans les heures payantes pour cette zone (tu devras créer une méthode privée pour ça, ex: estDansPeriodeTarifee(String code)).
        //Si tout est bon, elle crée une nouvelle transaction : this.transactionEnCours = new Stationnement.Transaction(code);
        if (!validerCode(code)) throw new IllegalArgumentException("Code invalide");
        //if (!estDansPeriodeTarifee(code)) throw new IllegalStateException("Hors période tarifée");    !!!À FIXER LA FONCTION EN BAS!!!!
        this.transactionEnCours = new Transaction(code);
    }

    public void insererPiece(Piece p) {
        double tarif = 0;
        // Si une transaction est en cours, elle appelle transactionEnCours.ajouterArgent(...) en utilisant la valeur de la pièce et le bon tarif horaire.
        if (transactionEnCours == null) throw new IllegalStateException("Aucune transaction");
        else {
            String zone = transactionEnCours.getCodeStationnement().startsWith("SQ") ? "SQ" : "G";
            if (zone.equals("SQ")) {
                tarif = tarifSQ;
            } else {
                tarif = tarifG;
            }
            transactionEnCours.ajouterArgent(p.getCents(), tarif);
            transactionEnCours.setTypePaiement("Comptant");
        }
    }

    public String confirmerTransaction(CarteCredit carte) {
        //Ajoute le coutTotal de la transaction au bon compteur   totalArgentComptant or totalArgentCredit
        //Génère une String qui représente le coupon avec toutes les infos de la transaction. (coupon)
        //this.transactionEnCours = null

        if (transactionEnCours == null) throw new IllegalStateException("Aucune transaction");
        double montant$ = transactionEnCours.getCoutCents() / 100.0;
        if (carte.estExpiree()) throw new IllegalStateException("Carte expirée");
        if (!carte.soldeSuffisant(montant$)) throw new IllegalStateException("Solde insuffisant");
        carte.debiter(montant$);
        totalArgentCredit += transactionEnCours.getCoutCents();
        transactionEnCours.setTypePaiement("Crédit");
        return finaliserEtGenererCoupon();
    }

    /*
    * public String confirmerTransactionComptant() {
        if (transactionEnCours == null) throw new IllegalStateException("Aucune transaction");
        totalArgentComptantCents += transactionEnCours.getCoutCents();
        transactionEnCours.setTypePaiement("Comptant");
        return finaliserEtGenererCoupon();
    }
    * */

    private String finaliserEtGenererCoupon() {
        // construire le texte du coupon avec heures, coût, type, code
        String coupon = String.format("Paiement: %s\nCoût: %.2f$\nDébut: %s\nFin: %s\nPlace: %s\n",
                transactionEnCours.getTypePaiement(),
                transactionEnCours.getCoutCents() / 100.0,
                transactionEnCours.getHeureDebut(),
                transactionEnCours.getHeureFin(),
                transactionEnCours.getCodeStationnement());
        transactionEnCours = null;
        return coupon;
    }

//    public String genererRecu() {
//        //totalArgentComptant + totalArgentCredit
//        //Crée une String avec ce montant
//        //Remet les deux compteurs à zéro
//        //Retourne la String du rapport.
//        double total = totalArgentComptant + totalArgentCredit;
//        String report = String.format("Rapport total: %.2f$", total / 100.0);
//        totalArgentComptant = 0;
//        totalArgentCredit = 0;
//        return report;
//    }

    /*
                //  !!À FIXER PLUS TARD !!
    private boolean estDansPeriodeTarifee(String code) {
        // À implémenter selon les heures que tu trouves pour G et SQ dans la carte/énoncé.
        // Exemple placeholder : always true for dev.

        //verifier code, jours de la semaine, heure,
        if (transactionEnCours.getCodeStationnement().startsWith("SQ")) {
            if (transactionEnCours.getHeureDebut().getDayOfWeek() == DayOfWeek.MONDAY
                    || transactionEnCours.getHeureDebut().getDayOfWeek() == DayOfWeek.TUESDAY
                    || transactionEnCours.getHeureDebut().getDayOfWeek() == DayOfWeek.TUESDAY
                    || transactionEnCours.getHeureDebut().getDayOfWeek() == DayOfWeek.WEDNESDAY
                    || transactionEnCours.getHeureDebut().getDayOfWeek() == DayOfWeek.THURSDAY
                    || transactionEnCours.getHeureDebut().getDayOfWeek() == DayOfWeek.FRIDAY) {
                if (transactionEnCours.getHeureDebut().isAfter(8) && transactionEnCours.getHeureDebut().isBefore(23)) { //si cest entre 8 et 23h
                    //mettre que ca commence a charger par heure
                }
            } else if (transactionEnCours.getHeureDebut().getDayOfWeek() == DayOfWeek.SATURDAY) {
                if (transactionEnCours.getHeureDebut().isAfter(9) && transactionEnCours.getHeureDebut().isBefore(23)) { //entre 9 et 23h
                    //commence a chargé par heure
                }
            } else if (transactionEnCours.getHeureDebut().getDayOfWeek() == DayOfWeek.SUNDAY) {
                if (transactionEnCours.getHeureDebut().isAfter(13) && transactionEnCours.getHeureDebut().isBefore(18)) { //entre 9 et 23h//commence a chargé par heure

                }
            }
        } else {
            //meme affaire mais pour "B"
        }

        //entre lundi et ven 8h a 23h - sam 9h a 23h - dim 13h a 18h
        // transactionEnCours.getHeureDebut().getDayOfWeek().
        return true;
    }   */
}


/*
* Comment tout ça fonctionne ensemble ?
Imagine que tu es devant l'interface graphique (GUI).

Tu tapes "G123" et "entrée".

Le code de ton Stationnement.Main (ou du GUI) appelle maBorne.demarrerTransaction("G123");.

La borne est maintenant en mode transaction. Tu insères 1$.

Le code crée une pièce (Stationnement.Piece unDollar = new Stationnement.Piece(1.00);) et appelle maBorne.insererPiece(unDollar);.

La borne demande à sa transactionEnCours de se mettre à jour avec ce montant. La transaction calcule que pour 1$ à 4.25$/h, tu as droit à environ 14 minutes et met à jour son heureFin.

Tu appuies sur "Confirmer".

Le code appelle String coupon = maBorne.confirmerTransaction();.

La borne finalise, met à jour son total d'argent comptant, génère le texte du coupon et se prépare pour le prochain client.
* */
