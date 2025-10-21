package Stationnement;

import java.time.LocalDateTime;
import java.time.Duration;

public class Transaction {
    private final String codeStationnement;
    private final LocalDateTime heureDebut;
    private LocalDateTime heureFin;
    private double coutTotal;
    private String typePaiement; //"Crédit" ou "Comptant"
    private static final int MAX_MINUTES = 120;

    //constructeur
    public Transaction(String codeStationnement) {
        this.codeStationnement = codeStationnement;
        this.heureDebut = LocalDateTime.now();
        this.heureFin = heureDebut;
        this.coutTotal = 0;
        this.typePaiement = "Inconnu";
    }

    public void ajouterArgent(double centsAjoutes, double tarifHoraire) {
        //Chaque fois que l'usager insère une pièce ou ajoute du temps par carte
        //augmenter coutTotal
        //Recalculer la durée de stationnement en fonction du tarifHoraire de la zone. (Durée en minutes = (coutTotal / tarifHoraire) * 60).
        //Mettre à jour l'heureFin en ajoutant cette durée à l'heureDebut.

        //faire des getters pour les variables, la classe Stationnement.Borne en a besoin pour les coupons

        // convertir cents -> minutes ajoutées
        double heuresAjoutees = (centsAjoutes / 100.0) / tarifHoraire;
        long minutesAjoutees = Math.round(heuresAjoutees * 60.0);

        long currentMinutes = Duration.between(heureDebut, heureFin).toMinutes();
        long newMinutes = Math.min(currentMinutes + minutesAjoutees, MAX_MINUTES);

        // ajuster le coût effectif pour les minutes réelles (prévenir arrondis)
        double coutTotalHeures = (newMinutes / 60.0) * tarifHoraire;
        this.coutTotal = (int)Math.round(coutTotalHeures * 100);

        this.heureFin = heureDebut.plusMinutes(newMinutes);
    }

    // getters pour coupon
    public String getCodeStationnement(){return codeStationnement;}
    public LocalDateTime getHeureDebut(){return heureDebut;}
    public LocalDateTime getHeureFin(){return heureFin;}
    public double getCoutCents(){return coutTotal;}
    public void setTypePaiement(String t){ this.typePaiement = t; }
    public String getTypePaiement(){ return typePaiement; }
}
