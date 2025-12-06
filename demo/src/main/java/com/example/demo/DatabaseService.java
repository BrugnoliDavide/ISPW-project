package com.example.demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DatabaseService {

    // DATI DI CONNESSIONE (Quelli messi nel docker-compose)
    private static final String URL = "jdbc:postgresql://localhost:5432/restaurant_db";
    private static final String USER = "admin";
    private static final String PASS = "password123";

    // 1. Metodo per scaricare TUTTI i prodotti dal DB vero
    public static List<MenuProduct> getAllProducts() {
        List<MenuProduct> prodotti = new ArrayList<>();

        // Query SQL
        String sql = "SELECT id, nome, tipologia, prezzo_vendita, costo_realizzazione, allergeni FROM menu_items";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Scorriamo le righe della tabella risultante
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String tipologia = rs.getString("tipologia");
                double prezzo = rs.getDouble("prezzo_vendita");
                double costo = rs.getDouble("costo_realizzazione");
                String allergeni = rs.getString("allergeni");

                // Creiamo l'oggetto Java e lo aggiungiamo alla lista
                prodotti.add(new MenuProduct(id, nome, tipologia, prezzo, costo, allergeni));
            }

        } catch (SQLException e) {
            System.out.println("ERRORE DATABASE: " + e.getMessage());
            e.printStackTrace();
        }

        return prodotti;
    }

    // 2. Metodo per raggruppare per categorie (Logica Java)
    public static Map<String, List<MenuProduct>> getMenuByCategories() {
        List<MenuProduct> tuttiIProdotti = getAllProducts();
        return tuttiIProdotti.stream()
                .collect(Collectors.groupingBy(MenuProduct::getTipologia));
    }

    // 3. Metodo Quantità (PER ORA FITTIZIO)
    // Dato che non abbiamo ancora creato la tabella 'vendite' su Docker,
    // restituiamo 0 per evitare errori. Lo implementeremo nel prossimo step.
    public static long getQuantitySold(String nomeProdotto) {
        return 0; // Placeholder
    }
}