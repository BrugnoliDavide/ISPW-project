package com.example.demo;

import java.time.LocalDateTime;

// 1. IL PRODOTTO DEL LISTINO (Rappresenta la tabella 'Catalogo')
// Questo è ciò che vedi nella schermata Menu: prezzi e costi attuali.
class MenuProduct {
    String nome;      // Chiave primaria
    String tipologia; // La categoria (es. "Cocktail", "Bibite")
    String allergeni;
    double prezzoVendita;
    double costoRealizzazione;

    public MenuProduct(String nome, String tipologia, double prezzo, double costo) {
        this.nome = nome;
        this.tipologia = tipologia;
        this.prezzoVendita = prezzo;
        this.costoRealizzazione = costo;
        this.allergeni = ""; // Default vuoto
    }
}

// 2. LA VENDITA REGISTRATA (Rappresenta la tabella 'Storico Ordini')
// Questo serve SOLO per calcolare la "Q.ta: 127" che vedi nell'immagine.
class SaleRecord {
    String nomeArticolo;
    LocalDateTime dataOra;
    double prezzoVenditaSnapshot; // Prezzo congelato al momento della vendita
    double costoRealizzazioneSnapshot;

    public SaleRecord(String nome, double prezzo, double costo) {
        this.nomeArticolo = nome;
        this.dataOra = LocalDateTime.now();
        this.prezzoVenditaSnapshot = prezzo;
        this.costoRealizzazioneSnapshot = costo;
    }
}