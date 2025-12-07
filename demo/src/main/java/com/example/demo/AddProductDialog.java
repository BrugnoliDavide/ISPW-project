package com.example.demo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddProductDialog {

    public static void display() {
        Stage window = new Stage();

        // 1. Configurazione Finestra
        window.initModality(Modality.APPLICATION_MODAL); // Blocca la finestra sotto finché non chiudi questa
        window.setTitle("Aggiungi Nuovo Prodotto");
        window.setMinWidth(300);

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER_LEFT);
        // Stile simile al resto dell'app
        layout.setStyle("-fx-background-color: white; -fx-border-color: #DDD; -fx-border-width: 2;");

        // --- 2. I Campi di Input ---

        // Nome
        Label lblName = new Label("Nome Prodotto:");
        TextField txtName = new TextField();
        txtName.setPromptText("Es. Tiramisù");

        // Tipologia (Usiamo una ComboBox per evitare errori di battitura)
        Label lblType = new Label("Tipologia:");
        ComboBox<String> cmbType = new ComboBox<>();
        cmbType.getItems().addAll("Primi", "Secondi", "Contorni", "Bibite", "Cocktail", "Dolci");
        cmbType.setValue("Primi"); // Valore default

        // Prezzo Vendita
        Label lblPrice = new Label("Prezzo Vendita (€):");
        TextField txtPrice = new TextField();
        txtPrice.setPromptText("Es. 12.50");

        // Costo Realizzazione
        Label lblCost = new Label("Costo Realizzazione (€):");
        TextField txtCost = new TextField();
        txtCost.setPromptText("Es. 4.00");

        // Allergeni
        Label lblAllergens = new Label("Allergeni (Opzionale):");
        TextField txtAllergens = new TextField();
        txtAllergens.setPromptText("Es. Latte, Uova");

        // --- 3. Bottone Salva ---
        Button btnSave = new Button("Salva nel Menu");
        btnSave.setStyle("-fx-background-color: #2B2B2B; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        btnSave.setMaxWidth(Double.MAX_VALUE); // Largo quanto la finestra

        // Azione del bottone
        btnSave.setOnAction(e -> {
            if (isValid(txtName, txtPrice, txtCost)) {

                // Creiamo l'oggetto temporaneo (ID è 0 perché lo decide il DB)
                MenuProduct newProduct = new MenuProduct(
                        txtName.getText(),
                        cmbType.getValue(),
                        Double.parseDouble(txtPrice.getText().replace(",", ".")), // Gestiamo sia la virgola che il punto
                        Double.parseDouble(txtCost.getText().replace(",", ".")),
                        txtAllergens.getText() // Usa il nuovo costruttore completo (o settalo dopo)
                );

                // Chiamata al Database
                boolean success = DatabaseService.addProduct(newProduct);

                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Successo", "Prodotto aggiunto correttamente!");
                    window.close(); // Chiudi la finestra
                    // Opzionale: Ricaricare la lista del menu sotto (lo vedremo dopo)
                } else {
                    showAlert(Alert.AlertType.ERROR, "Errore", "Impossibile salvare.\nForse il nome esiste già?");
                }
            }
        });

        layout.getChildren().addAll(lblName, txtName, lblType, cmbType, lblPrice, txtPrice, lblCost, txtCost, lblAllergens, txtAllergens, btnSave);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait(); // Aspetta che venga chiusa prima di tornare al codice principale
    }

    // Validazione base
    private static boolean isValid(TextField name, TextField price, TextField cost) {
        if (name.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Dati Mancanti", "Inserisci il nome del prodotto.");
            return false;
        }
        try {
            Double.parseDouble(price.getText().replace(",", "."));
            Double.parseDouble(cost.getText().replace(",", "."));
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Formato Errato", "Prezzo e Costo devono essere numeri.");
            return false;
        }
        return true;
    }

    // Helper per mostrare messaggi
    private static void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}