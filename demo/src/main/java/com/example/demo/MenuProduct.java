package com.example.demo;

public class MenuProduct {

    // Attributi
    private int id;
    private String nome;
    private String tipologia;
    private double prezzoVendita;
    private double costoRealizzazione;
    private String allergeni;

    // --- COSTRUTTORE 1: COMPLETO (6 Argomenti) ---
    // Da usare in DatabaseService quando leggi i dati completi
    public MenuProduct(int id, String nome, String tipologia, double prezzo, double costo, String allergeni) {
        this.id = 0; // 0 indica che il DB deve ancora assegnare un ID vero
        this.nome = nome;
        this.tipologia = tipologia;
        this.prezzoVendita = prezzo;
        this.costoRealizzazione = costo;
        this.allergeni = allergeni;
    }

    //costruttore da 5 argomenti
    public MenuProduct(String nome, String tipologia, double prezzo, double costo, String allergeni) {
        this.id = 0; // 0 o -1 indica che è un prodotto nuovo non ancora nel DB
        this.nome = nome;
        this.tipologia = tipologia;
        this.prezzoVendita = prezzo;
        this.costoRealizzazione = costo;
        this.allergeni = allergeni;
    }

    // --- COSTRUTTORE 2: SEMPLIFICATO (4 Argomenti) ---
    // Utile se hai ancora vecchio codice (es. MockDatabase) che non usa ID e Allergeni
    public MenuProduct(String nome, String tipologia, double prezzo, double costo) {
        this.id = 0; // ID provvisorio
        this.nome = nome;
        this.tipologia = tipologia;
        this.prezzoVendita = prezzo;
        this.costoRealizzazione = costo;
        this.allergeni = ""; // Stringa vuota di default
    }

    // --- GETTER E SETTER ---
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getTipologia() { return tipologia; }
    public double getPrezzoVendita() { return prezzoVendita; }
    public double getCostoRealizzazione() { return costoRealizzazione; }
    public String getAllergeni() { return allergeni; }

    // Utile per debug (stampa l'oggetto invece dell'indirizzo di memoria)
    @Override
    public String toString() {
        return nome + " (" + tipologia + ")";
    }
}