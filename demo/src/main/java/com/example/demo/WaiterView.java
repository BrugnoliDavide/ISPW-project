package com.example.demo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;

public class WaiterView {

    // Placeholder Dati Utente
    private static String userName = "Sir. Robert";
    private static String userRole = "Waiter";
    private static Color profilePlaceholderColor = Color.CORNFLOWERBLUE; // Colore diverso dal Manager

    public static Parent getView() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // Applica sfondo tema
        root.getStyleClass().add("view-background");

        // --- 1. HEADER (Top) ---
        HBox header = createHeader();
        root.setTop(header);

        // --- 2. CENTER (Spazio vuoto/Sfondo) ---
        // Nell'immagine è grigio vuoto, possiamo mettere un placeholder o lasciarlo libero
        StackPane center = new StackPane();
        // center.setStyle("-fx-background-color: rgba(0,0,0,0.05); -fx-background-radius: 20;"); // Opzionale: ombra leggera
        root.setCenter(center);

        // --- 3. BOTTOM (Controlli Ordine) ---
        VBox bottomBox = createBottomControls();
        root.setBottom(bottomBox);

        // TEMA: Applica tema corrente
        //ThemeManager.applyTheme(root);

        return root;
    }

    private static HBox createHeader() {
        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);

        // Foto Profilo (Cerchio)
        Circle profileImg = new Circle(25, profilePlaceholderColor);

        // Testi (Nome e Ruolo)
        VBox texts = new VBox(2);
        Label nameLbl = new Label(userName);
        nameLbl.getStyleClass().add("text-title");
        nameLbl.setStyle("-fx-font-size: 16px;");

        Label roleLbl = new Label(userRole);
        roleLbl.getStyleClass().add("text-body");
        roleLbl.setStyle("-fx-font-size: 14px;");

        texts.getChildren().addAll(nameLbl, roleLbl);

        // Spaziatore
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Icona Orologio (Simulata da SVG)
        StackPane clockIcon = createIcon("M11.99 2C6.47 2 2 6.48 2 12s4.47 10 9.99 10C17.52 22 22 17.52 22 12S17.52 2 11.99 2zM12 20c-4.42 0-8-3.58-8-8s3.58-8 8-8 8 3.58 8 8-3.58 8-8 8zm.5-13H11v6l5.25 3.15.75-1.23-4.5-2.67z");
        // Opzionale: Aggiungi un tooltip o azione per l'orario

        header.getChildren().addAll(profileImg, texts, spacer, clockIcon);
        return header;
    }

    private static VBox createBottomControls() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20, 0, 10, 0));

        // 1. Bottone "New Order" (FISSO SCURO)
        Button btnNewOrder = new Button("new\norder");
        btnNewOrder.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        // STILE FISSO: Colore #2B2B2B, Testo Bianco
        btnNewOrder.setStyle(
                "-fx-background-color: #2B2B2B;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 15;" +
                        "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-min-width: 140;" +
                        "-fx-min-height: 60;" +
                        "-fx-cursor: hand;"
        );

        // 2. Etichetta "table"
        Label lblTable = new Label("table");
        lblTable.getStyleClass().add("text-body");
        lblTable.setStyle("-fx-font-size: 14px;");

        // 3. Input "number" (TextField libero, NON ComboBox)
        javafx.scene.control.TextField txtTable = new javafx.scene.control.TextField();
        txtTable.setPromptText("number");
        txtTable.setAlignment(Pos.CENTER);

        // Definiamo gli stili: Normale (Grigio) ed Errore (Rosso)
        String normalStyle = "-fx-background-color: white; -fx-background-radius: 10; -fx-border-color: #DDD; -fx-border-radius: 10; -fx-min-width: 200; -fx-font-size: 14px;";
        String errorStyle  = "-fx-background-color: white; -fx-background-radius: 10; -fx-border-color: red; -fx-border-radius: 10; -fx-min-width: 200; -fx-font-size: 14px;";

        txtTable.setStyle(normalStyle);

        // AZIONE
        btnNewOrder.setOnAction(e -> {
            String input = txtTable.getText().trim();

            try {
                // Tenta di convertire in numero
                int tavoloSelezionato = Integer.parseInt(input);

                if (tavoloSelezionato <= 0) throw new NumberFormatException();

                // Se è valido:
                txtTable.setStyle(normalStyle);
                System.out.println("Apro menu per tavolo: " + tavoloSelezionato);

                // QUI COLLEGHEREMO LA VISTA SUCCESSIVA (TakeOrderView)
                // btnNewOrder.getScene().setRoot(TakeOrderView.getView(tavoloSelezionato));

            } catch (NumberFormatException ex) {
                // Se non è un numero valido: Bordo Rosso
                txtTable.setStyle(errorStyle);
            }
        });

        layout.getChildren().addAll(btnNewOrder, lblTable, txtTable);
        return layout;
    }

    // Helper per icone
    private static StackPane createIcon(String svgContent) {
        SVGPath path = new SVGPath();
        path.setContent(svgContent);
        path.getStyleClass().add("icon-svg");
        path.setScaleX(1.5);
        path.setScaleY(1.5);

        StackPane p = new StackPane(path);
        p.setPrefSize(40, 40);
        return p;
    }
}