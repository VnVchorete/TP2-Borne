package Stationnement;

import java.time.LocalDateTime;

public class Transaction {
    private final String codeStationnement;
    private final LocalDateTime heureDebut;
    private LocalDateTime heureFin;
    private double coutTotal;
    private String typePaiement; //"Crédit" ou "Comptant"
    private long dureeEnMinutes;
    private long dureePayee;
    private static final int MAX_MINUTES = 120;

    //constructeur
    public Transaction(String codeStationnement) {
        this.codeStationnement = codeStationnement;
        this.heureDebut = LocalDateTime.now();
        this.heureFin = heureDebut;     /// créer variable de classe de type long qui garde le total de minutes
        this.coutTotal = 0.0;
        this.typePaiement = "Inconnu";
        this.dureePayee = 0;
    }

    public void ajouterArgent(double montantAjouteEnCents, double tarifHoraire) {
        this.typePaiement = "Comptant";
        montantAjouteEnCents /= 100;
        //long dureeActuelle = this.dureeEnMinutes;
        //double heuresAjoutees = montantAjouteEnCents / tarifHoraire;
        this.dureeEnMinutes = Math.round((montantAjouteEnCents / tarifHoraire) * 60);
        //long minutesAjoutees = Math.round(heuresAjoutees * 60.0);
        //this.dureeEnMinutes = Math.round(heuresAjoutees * 60.0);

        //this.dureeEnMinutes = Math.min(dureeActuelle + minutesAjoutees, MAX_MINUTES);
//        if (this.dureeEnMinutes > MAX_MINUTES) {
//            this.dureeEnMinutes = MAX_MINUTES;
//        }
        //double heuresTotales = this.dureeEnMinutes / 60.0;
        //this.coutTotal = this.dureeEnMinutes * tarifHoraire;
        if (this.dureeEnMinutes >= MAX_MINUTES || this.dureePayee >= MAX_MINUTES) { /// simplifier ou pas jsp
            this.dureePayee = MAX_MINUTES;
            this.heureFin = this.heureDebut.plusMinutes(MAX_MINUTES);
        } else {
            this.heureFin = this.heureDebut.plusMinutes(this.dureeEnMinutes);
            this.dureePayee += java.time.Duration.between(heureDebut, heureFin).toMinutes();    ///pas sur sur
        }

//        this.coutTotal += montantAjouteEnDollars;
//        this.typePaiement = "Comptant";
//        double totalHeures = this.coutTotal / tarifHoraire;
//        long totalMinutesCalculees = (long) (totalHeures * 60.0);
//        this.dureeEnMinutes = Math.min(totalMinutesCalculees, MAX_MINUTES);
//        this.heureFin = this.heureDebut.plusMinutes(this.dureeEnMinutes);
//
//        this.dureePayee = java.time.Duration.between(getHeureDebut(), getHeureFin()).toMinutes();

        //long currentMinutes = Duration.between(heureDebut, heureFin).toMinutes();


//        double heuresAjoutees = (centsAjoutes / 100.0) / tarifHoraire;
//        long minutesAjoutees = Math.round(heuresAjoutees * 60.0);
//        long currentMinutes = Duration.between(heureDebut, heureFin).toMinutes();
//        long newMinutes = Math.min(currentMinutes + minutesAjoutees, MAX_MINUTES);
//        // ajuster le coût effectif pour les minutes réelles (prévenir arrondis)
//        double coutTotalHeures = (newMinutes / 60.0) * tarifHoraire;
//        this.coutTotal = (int)Math.round(coutTotalHeures * 100);
//        this.heureFin = heureFin.plusMinutes(newMinutes);
    }

    // getters pour coupon
    public double getCoutTotal() { return coutTotal; }
    public long getDureeEnMinutes() { return dureeEnMinutes; }
    public void setTypePaiement(String t) { this.typePaiement = t; }
    public String getTypePaiement() { return typePaiement; }

    public String getCodeStationnement() { return codeStationnement; }
    public LocalDateTime getHeureDebut() { return heureDebut; }
    public LocalDateTime getHeureFin() { return heureFin; }
    public long getDureePayee() { return dureePayee; }  ///pas sur

   // public double getCoutCents(){return coutTotal;}

//    public void setTypePaiement(String t){ this.typePaiement = t; }
//    public String getTypePaiement(){ return typePaiement; }
}
