package Stationnement;

import java.time.LocalDateTime;
import java.util.Random;

public class CarteCredit {
    private final String numCarte;
    private final LocalDateTime dateExpiration; // = "mm/aa";
    private double solde;

    //solde random
    public CarteCredit(String numCarte, String mmaa) {  //regex pour faire 4 champ * 4
        this.numCarte = numCarte;
        this.dateExpiration = LocalDateTime.parse(mmaa);
        this.solde = new Random().nextDouble() * 200.0;
    }

//    private YearMonth parseMmAa(String mmAa) {
//        String[] parts = mmAa.split("/");
//        int mm = Integer.parseInt(parts[0]);
//        int aa = Integer.parseInt(parts[1]);
//        int fullYear = 2000 + aa;
//        return YearMonth.of(fullYear, mm);
//    }

    public boolean estExpiree() {   //pas fini
        return LocalDateTime.now().isAfter(dateExpiration);
    }

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
