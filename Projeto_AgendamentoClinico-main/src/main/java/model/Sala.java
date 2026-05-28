package model;

public class Sala {

    private int numero;

    public Sala(int numero) {
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    @Override
    public String toString() {
        return "Sala " + numero;
    }
}