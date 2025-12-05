package com.example.demo; // Assicurati che il package sia corretto

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Scene mainScene; // Manteniamo un riferimento alla scena principale

    @Override
    public void start(Stage primaryStage) {

        // --- Costruzione Schermata Login (Uguale a prima) ---
        StackPane root = new StackPane();
        root.getStyleClass().add("root-pane");

        VBox loginCard = new VBox(15);
        loginCard.getStyleClass().add("login-card");
        loginCard.setMaxWidth(300);
        loginCard.setMaxHeight(350);
        loginCard.setAlignment(Pos.CENTER_LEFT);

        Label userLabel = new Label("Username");
        userLabel.getStyleClass().add("input-label");
        TextField userField = new TextField();
        userField.setPromptText("Value");
        userField.getStyleClass().add("text-input-field");

        Label passLabel = new Label("Password");
        passLabel.getStyleClass().add("input-label");
        PasswordField passField = new PasswordField();
        passField.setPromptText("Value");
        passField.getStyleClass().add("text-input-field");

        Button signInBtn = new Button("Sign In");
        signInBtn.getStyleClass().add("sign-in-btn");
        VBox.setMargin(signInBtn, new Insets(20, 0, 0, 0));
        signInBtn.setMaxWidth(Double.MAX_VALUE);

        // --- QUI C'È LA LOGICA DI NAVIGAZIONE ---
        signInBtn.setOnAction(e -> {
            String role = userField.getText().trim().toLowerCase(); // Rimuove spazi e converte in minuscolo

            switch (role) {
                case "cameriere":
                    System.out.println("Navigazione verso: CAMERIERE");
                    changeScreen("Interfaccia Cameriere (Placeholder)");
                    break;

                case "manager":
                    System.out.println("Navigazione verso: MANAGER");
                    changeScreen("Interfaccia Manager (Placeholder)");
                    break;

                case "cucina":
                    System.out.println("Navigazione verso: CUCINA");
                    changeScreen("Interfaccia Cucina (Placeholder)");
                    break;

                default:
                    // Se non è nessuno dei tre, stampiamo errore in console (o potremmo mostrare un alert)
                    System.out.println("Ruolo non riconosciuto: " + role);
                    // Qui potresti aggiungere un effetto visivo di errore (es. bordo rosso)
                    userField.setStyle("-fx-border-color: red;");
            }
        });

        loginCard.getChildren().addAll(userLabel, userField, passLabel, passField, signInBtn);
        root.getChildren().add(loginCard);

        mainScene = new Scene(root, 450, 550);

        // Caricamento CSS (Ricorda lo slash!)
        mainScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        primaryStage.setTitle("Ristorante App");
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Metodo helper per cambiare schermata simulando le nuove pagine.
     * In futuro, questo metodo caricherà i veri layout.
     */
    private void changeScreen(String screenName) {
        // Creiamo un layout semplice per la nuova pagina
        StackPane newPageRoot = new StackPane();
        newPageRoot.setStyle("-fx-background-color: #EFEFEF;"); // Un colore diverso per capire che è cambiato

        Label titleLabel = new Label(screenName);
        titleLabel.setFont(new Font("Arial", 24));

        Button backBtn = new Button("Torna al Login (Per Test)");
        backBtn.setOnAction(e -> restartApp()); // Per comodità di test

        VBox content = new VBox(20, titleLabel, backBtn);
        content.setAlignment(Pos.CENTER);

        newPageRoot.getChildren().add(content);

        // --- IL TRUCCO: Sostituiamo la radice della scena corrente ---
        mainScene.setRoot(newPageRoot);
    }

    // Solo per test: ricarica la schermata iniziale
    private void restartApp() {
        start( (Stage) mainScene.getWindow() );
    }

    public static void main(String[] args) {
        launch(args);
    }
}