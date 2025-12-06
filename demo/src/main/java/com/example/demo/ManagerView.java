package com.example.demo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class ManagerView {

    // --- VARIABILI DATI (Placeholder) ---
    // In futuro queste verranno popolate da un database o un oggetto User
    private static String userName = "Sig.na Susan";
    private static String userRole = "Manager";
    // Per ora usiamo un colore, in futuro sarà un URL immagine
    private static Color profilePlaceholderColor = Color.LIGHTBLUE;

    public static Parent getView() {

        // 1. Layout Principale: BorderPane è perfetto per (Intestazione - Centro - Basso)
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20)); // Margine dai bordi dello schermo
        root.setStyle("-fx-background-color: #D9D9D9;"); // Sfondo grigio

        // --- PARTE SUPERIORE (Header) ---
        HBox header = createHeader();
        root.setTop(header);

        // --- PARTE CENTRALE (Welcome Message) ---
        VBox centerBox = new VBox();
        centerBox.setAlignment(Pos.CENTER);

        Label welcomeText = new Label("welcome, " + userName.toLowerCase());
        welcomeText.getStyleClass().add("welcome-text"); // Stile definito nel CSS
        Label subWelcome = new Label(userName.split(" ")[1]); // Prende solo il nome per la seconda riga (esempio)
        subWelcome.getStyleClass().add("welcome-text-bold");

        centerBox.getChildren().addAll(welcomeText, subWelcome);
        root.setCenter(centerBox);

        // --- PARTE INFERIORE (Bottoni) ---
        HBox bottomBox = new HBox(20); // 20px spazio tra i bottoni
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(0, 0, 50, 0)); // Spazio dal fondo

        Button btnMenu = createDashboardButton("Menu");
        Button btnFinance = createDashboardButton("Financial");

        // Azioni fittizie (Placeholder)
        btnMenu.setOnAction(e -> {System.out.println("Navigazione -> Menu Ristorante");
        btnMenu.getScene().setRoot(MenuView.getView());
        });
        btnFinance.setOnAction(e -> System.out.println("Navigazione -> Dashboard Finanziaria"));

        bottomBox.getChildren().addAll(btnMenu, btnFinance);
        root.setBottom(bottomBox);

        return root;
    }

    // Metodo helper per creare l'intestazione complessa
    private static HBox createHeader() {
        HBox header = new HBox(15); // Spazio orizzontale tra elementi
        header.setAlignment(Pos.CENTER_LEFT);

        // 1. Immagine Profilo (Simulata con un cerchio per ora)
        Circle profileImg = new Circle(25, profilePlaceholderColor);
        // NOTA: In futuro useremo: new ImagePattern(new Image("url..."))

        // 2. Testi Nome e Ruolo
        VBox texts = new VBox(2);
        Label nameLbl = new Label(userName);
        nameLbl.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #555;");
        Label roleLbl = new Label(userRole);
        roleLbl.setStyle("-fx-font-size: 14px; -fx-text-fill: #888;");
        texts.getChildren().addAll(nameLbl, roleLbl);

        // 3. Spaziatore (spinge l'icona notifica a destra)
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);


        SVGPath notifIcon = new SVGPath();
        //migliorare l'icona: è una merda
        notifIcon.setContent("M20 4H4c-1.1 0-1.99.9-1.99 2L2 18c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V6c0-1.1-.9-2-2-2zm0 4l-8 5-8-5V6l8 5 8-5v2z");
        notifIcon.setFill(Color.TRANSPARENT);
        notifIcon.setStroke(Color.BLACK);
        notifIcon.setStrokeWidth(2);
        notifIcon.setScaleX(1.2);
        notifIcon.setScaleY(1.2);

        // Area cliccabile per la notifica
        StackPane notifBtn = new StackPane(notifIcon);
        notifBtn.setOnMouseClicked(e -> System.out.println("Apertura Notifiche..."));
        notifBtn.setStyle("-fx-cursor: hand;");

        header.getChildren().addAll(profileImg, texts, spacer, notifBtn);
        return header;
    }

    // Metodo helper per creare i bottoni scuri uguali
    private static Button createDashboardButton(String text) {
        Button btn = new Button(text);
        btn.getStyleClass().add("dashboard-btn"); // Stile CSS
        btn.setPrefSize(140, 50); // Dimensione fissa
        return btn;
    }
}