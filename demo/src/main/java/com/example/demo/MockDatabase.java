package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


//ciò è puramente fittizzio solo per scopi di sviluppo
public class MockDatabase {

    // TABELLA 1: Listino Attuale (Nome, Tipologia, Prezzi Attuali)
    public static List<MenuProduct> catalogo = new ArrayList<>();

    // TABELLA 2: Storico Vendite (Cronologia per calcolare le quantità)
    public static List<SaleRecord> storicoVendite = new ArrayList<>();

    // Blocco statico: viene eseguito appena avvii l'app per riempire i dati finti
    static {
        // --- Popoliamo il Catalogo (Come nell'immagine) ---
        catalogo.add(new MenuProduct("Negroni", "Cocktail", 7.00, 2.50));
        catalogo.add(new MenuProduct("Long Island", "Cocktail", 8.00, 3.00));
        catalogo.add(new MenuProduct("Gin Tonic", "Cocktail", 7.00, 2.00));

        catalogo.add(new MenuProduct("Acqua", "Bibite", 2.00, 0.20));
        catalogo.add(new MenuProduct("Coca Cola", "Bibite", 4.00, 0.50));

        // --- Simuliamo lo Storico (Per vedere numeri diversi in Q.ta) ---
        // Aggiungiamo 3 Negroni
        storicoVendite.add(new SaleRecord("Negroni", 7.00, 2.50));
        storicoVendite.add(new SaleRecord("Negroni", 7.00, 2.50));
        storicoVendite.add(new SaleRecord("Negroni", 6.50, 2.40)); // Uno vecchio con prezzo diverso

        // Aggiungiamo 1 Gin Tonic
        storicoVendite.add(new SaleRecord("Gin Tonic", 7.00, 2.00));

        // Acqua e Coca Cola per ora 0 vendite...
    }

    // --- LOGICHE DI RECUPERO DATI ---

    // 1. Ritorna il catalogo diviso per Tipologia (es. Cocktail -> Lista, Bibite -> Lista)
    public static Map<String, List<MenuProduct>> getMenuByCategories() {
        return catalogo.stream()
                .collect(Collectors.groupingBy(p -> p.tipologia));
    }

    // 2. Calcola la quantità venduta scorrendo la tabella Storico
    public static long getQuantitySold(String nomeProdotto) {
        return storicoVendite.stream()
                .filter(sale -> sale.nomeArticolo.equals(nomeProdotto))
                .count();
    }
}