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

    // Metodo per inserire un nuovo prodotto
    public static boolean addProduct(MenuProduct p) {
        String sql = "INSERT INTO menu_items (nome, tipologia, prezzo_vendita, costo_realizzazione, allergeni) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Riempiamo i punti interrogativi (?) con i dati
            pstmt.setString(1, p.getNome());
            pstmt.setString(2, p.getTipologia());
            pstmt.setDouble(3, p.getPrezzoVendita());
            pstmt.setDouble(4, p.getCostoRealizzazione());
            pstmt.setString(5, p.getAllergeni());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Se è > 0, ha funzionato

        } catch (SQLException e) {
            System.out.println("ERRORE INSERIMENTO: " + e.getMessage());
            return false; // Qualcosa è andato storto (es. nome duplicato)
        }
    }

    // 1. AGGIORNAMENTO (Richiede un oggetto con ID valido)
    public static boolean updateProduct(MenuProduct p) {
        String sql = "UPDATE menu_items SET nome = ?, tipologia = ?, prezzo_vendita = ?, costo_realizzazione = ?, allergeni = ? WHERE id = ?";

        try (java.sql.Connection conn = java.sql.DriverManager.getConnection(URL, USER, PASS);
             java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, p.getNome());
            pstmt.setString(2, p.getTipologia());
            pstmt.setDouble(3, p.getPrezzoVendita());
            pstmt.setDouble(4, p.getCostoRealizzazione());
            pstmt.setString(5, p.getAllergeni());
            pstmt.setInt(6, p.getId()); // FONDAMENTALE: Usa l'ID per trovare la riga

            return pstmt.executeUpdate() > 0;

        } catch (java.sql.SQLException e) {
            System.out.println("ERRORE UPDATE: " + e.getMessage());
            return false;
        }
    }

    // 2. ELIMINAZIONE
// Metodo ELIMINAZIONE con DEBUG
    public static boolean deleteProduct(int id) {
        System.out.println("--- INIZIO TENTATIVO ELIMINAZIONE ---");
        System.out.println("ID ricevuto da eliminare: " + id);

        if (id <= 0) {
            System.out.println("ERRORE: L'ID non è valido (è 0 o minore). Impossibile eliminare dal DB.");
            return false;
        }

        String sql = "DELETE FROM menu_items WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            // Eseguiamo
            int rowsAffected = pstmt.executeUpdate();

            System.out.println("Righe eliminate realmente dal DB: " + rowsAffected);

            if (rowsAffected > 0) {
                System.out.println("SUCCESSO: Prodotto eliminato.");
                return true;
            } else {
                System.out.println("FALLIMENTO: Nessuna riga trovata con questo ID. Il prodotto esiste nel DB?");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("ERRORE SQL GRAVE: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    // Metodo per ottenere la lista delle categorie esistenti
    public static List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        // DISTINCT serve a non avere duplicati (es. se ho 10 Primi, voglio la scritta "Primi" una volta sola)
        String sql = "SELECT DISTINCT tipologia FROM menu_items ORDER BY tipologia";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                categories.add(rs.getString("tipologia"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Se il DB è vuoto, restituiamo almeno delle categorie base per non lasciare l'utente smarrito
        if (categories.isEmpty()) {
            categories.add("Primi");
            categories.add("Secondi");
            categories.add("Bibite");
        }

        return categories;
    }




}




