package Stationnement;

import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private static final Pattern Code_Pattern = Pattern.compile("^(G|SQ)\\d{3}$");

    public Transaction getTransactionEnCours() {
        return transactionEnCours;
    }

    public boolean validerCode(String code) {
        return Code_Pattern.matcher(code).matches();
    }

    public void demarrerTransaction(String code) {
        // appelle des fonctions pour :
        //Vérifie si le code est valide
        //Vérifie si on est dans les heures payantes pour cette zone (tu devras créer une méthode privée pour ça, ex: estDansPeriodeTarifee(String code)).
        //Si tout est bon, elle crée une nouvelle transaction : this.transactionEnCours = new Stationnement.Transaction(code);
        if (!validerCode(code) && !estDansPeriodeTarifee(code)) {
            code = "";
        }
        else {
            this.transactionEnCours = new Transaction(code);
        }
    }

    public void insererPiece(Piece p) {
        double tarif = 0;
        // Si une transaction est en cours, elle appelle transactionEnCours.ajouterArgent(...) en utilisant la valeur de la pièce et le bon tarif horaire.
        String zone = transactionEnCours.getCodeStationnement().startsWith("SQ") ? "SQ" : "G";
        if (zone.equals("SQ")) {
            tarif = tarifSQ;
        } else {
            tarif = tarifG;
        }
        transactionEnCours.ajouterArgent(p.getCents(), tarif);
        transactionEnCours.setTypePaiement("Comptant");
    }

    /// fixer car inutile
    public String confirmerTransaction(CarteCredit carte) {
        //Ajoute le coutTotal de la transaction au bon compteur   totalArgentComptant or totalArgentCredit
        //Génère une String qui représente le coupon avec toutes les infos de la transaction. (coupon)
        //this.transactionEnCours = null

        if (transactionEnCours == null) throw new IllegalStateException("Aucune transaction"); ///changer ligne
        double montant$ = transactionEnCours.getCoutTotal() / 100.0;
        if (carte.estExpiree(carte)) throw new IllegalStateException("Carte expirée"); ///changer ligne
        if (!carte.soldeSuffisant(montant$)) throw new IllegalStateException("Solde insuffisant"); ///changer ligne
        carte.debiter(montant$);
        totalArgentCredit += transactionEnCours.getCoutTotal();
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
                transactionEnCours.getCoutTotal() / 100.0,
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


    //  !!À FIXER PLUS TARD !!
    private boolean estDansPeriodeTarifee(String code) {
        LocalDateTime debut = LocalDateTime.now();
        DayOfWeek jour = debut.getDayOfWeek();
        LocalTime heure = debut.toLocalTime();

        if (code.startsWith("G")) {
            if (jour == DayOfWeek.MONDAY || jour == DayOfWeek.TUESDAY || jour == DayOfWeek.WEDNESDAY
                    || jour == DayOfWeek.THURSDAY || jour == DayOfWeek.FRIDAY) {
                if (heure.isAfter(LocalTime.of(8, 0)) && heure.isBefore(LocalTime.of(23, 0))) {
                    return true;
                }
            } else if (jour == DayOfWeek.SATURDAY) {
                if (heure.isAfter(LocalTime.of(9, 0)) && heure.isBefore(LocalTime.of(23, 0))) {
                    return true;
                }
            } else if (jour == DayOfWeek.SUNDAY) {
                if (heure.isAfter(LocalTime.of(13, 0)) && heure.isBefore(LocalTime.of(18, 0))) {
                    return true;
                }
            }
        } else if (code.startsWith("SQ")) {
            if (jour == DayOfWeek.MONDAY || jour == DayOfWeek.TUESDAY || jour == DayOfWeek.WEDNESDAY
                    || jour == DayOfWeek.THURSDAY || jour == DayOfWeek.FRIDAY) {
                if (heure.isAfter(LocalTime.of(9, 0)) && heure.isBefore(LocalTime.of(21, 0))) {
                    return true;
                }
            } else if (jour == DayOfWeek.SATURDAY) {
                if (heure.isAfter(LocalTime.of(9, 0)) && heure.isBefore(LocalTime.of(18, 0))) {
                    return true;
                }
            }
        }
        //entre lundi et ven 8h a 23h - sam 9h a 23h - dim 13h a 18h
        return false;
    }
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
