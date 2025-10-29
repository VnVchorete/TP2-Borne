package Stationnement;

public class Piece {
    //private double valeur;
    public static final int CENT_25 = 25;
    public static final int DOLLAR = 100;
    public static final int DOLLAR_2 = 200;
    private int cents;

    //constructeur
    public Piece(int cents) {
        this.cents = cents;
    }

    public int getCents() { return cents; }

    //public double getValeur() { return cents / 100.0; }
}
