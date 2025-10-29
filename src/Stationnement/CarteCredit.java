package Stationnement;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Random;

public class CarteCredit {
    private final String numCarte;
    private final YearMonth dateExpiration; // = "mm/aa";
    private double solde;

    public CarteCredit(String numCarte, String mmaa) {  //regex pour faire 4 champ * 4
        this.numCarte = numCarte;
        this.dateExpiration = YearMonth.now();
        this.solde = new Random().nextDouble() * 200.0;
    }

    private YearMonth parseMmAa(String mmaa) {
        String[] parts = mmaa.split("/");
        int mm = Integer.parseInt(parts[0]);
        int aa = Integer.parseInt(parts[1]);
        int fullYear = 2000 + aa;
        return YearMonth.of(fullYear, mm);
    }

    public boolean estExpiree(CarteCredit carte) { return YearMonth.now().isBefore(dateExpiration);}

    public boolean soldeSuffisant(double montant) {
        return solde >= montant;
        //si le solde est >= au montant demandé par la borne
        //si true alors on appele debiter()
    }

    public void debiter(double montant) {
        // on retire le montant de la borne au solde solde -= montant
        if (!soldeSuffisant(montant)) throw new IllegalStateException("Solde insuffisant");
        solde -= montant;
    }

    public double getSolde() { return solde; }
}
