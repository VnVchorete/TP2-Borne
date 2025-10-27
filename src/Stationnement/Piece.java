package Stationnement;

public class Piece {
    //private double valeur;
    public static final int CENT_25 = 25;
    public static final int DOLLAR = 100;
    public static final int DOLLAR_2 = 200;
    private final int cents;

    //constructeur
    public Piece(int cents) {
//        if (cents != CENT_25 && cents != DOLLAR && cents != DOLLAR_2) {
//            throw new IllegalArgumentException("Pièce non acceptée"); ///enlever plus tard
//        }
        this.cents = cents;
    }

    public int getCents() { return cents; }

    public double getValeur() { return cents / 100.0; }
}
