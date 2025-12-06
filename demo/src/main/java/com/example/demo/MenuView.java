package com.example.demo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import java.util.List;
import java.util.Map;

public class MenuView {

    public static Parent getView() {
        // Layout principale: Intestazione in alto, Lista al centro
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: white;"); // Sfondo bianco come nell'immagine

        // --- A. COSTRUZIONE HEADER (Parte Alta) ---
        VBox topContainer = new VBox(5);
        topContainer.setPadding(new Insets(20, 20, 10, 20));


        // 1. Label "Manage" (Che funge da Tasto Indietro)
        Label lblManage = new Label("← Manage"); // Ho aggiunto una freccina per chiarezza
        lblManage.setStyle("-fx-text-fill: #888888; -fx-font-size: 14px; -fx-cursor: hand;"); // Cursore a manina

        // Effetto Hover (Opzionale: diventa scuro quando ci passi sopra)
        lblManage.setOnMouseEntered(e -> lblManage.setStyle("-fx-text-fill: #333333; -fx-font-size: 14px; -fx-cursor: hand; -fx-underline: true;"));
        lblManage.setOnMouseExited(e -> lblManage.setStyle("-fx-text-fill: #888888; -fx-font-size: 14px; -fx-cursor: hand; -fx-underline: false;"));

        // AZIONE: Torna alla Dashboard del Manager
        lblManage.setOnMouseClicked(e -> {
            System.out.println("Torno indietro...");
            // Sostituisce la vista corrente con quella del Manager
            lblManage.getScene().setRoot(ManagerView.getView());
        });

        // 2. Riga con "Menu" e Icone
        HBox titleRow = new HBox();
        titleRow.setAlignment(Pos.CENTER_LEFT);

        Label lblTitle = new Label("Menu");
        lblTitle.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #000;");

        // Spaziatore invisibile che spinge le icone a destra
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Icona Cerca (Lente d'ingrandimento)
        StackPane btnSearch = createIcon("M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z");

        // Icona Più (Add)
        StackPane btnAdd = createIcon("M19 13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z");
        HBox.setMargin(btnAdd, new Insets(0, 0, 0, 15)); // Un po' di margine a sinistra del +

        titleRow.getChildren().addAll(lblTitle, spacer, btnSearch, btnAdd);

        // 3. Linea separatrice sottile
        Separator sep = new Separator();
        sep.setPadding(new Insets(10, 0, 0, 0));

        topContainer.getChildren().addAll(lblManage, titleRow, sep);
        root.setTop(topContainer);


        // --- B. COSTRUZIONE LISTA (Parte Centrale) ---
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true); // La lista si adatta alla larghezza
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: white; -fx-border-color: transparent;");

        VBox listContent = new VBox();
        listContent.setPadding(new Insets(10, 20, 20, 20)); // Margini interni

        // Recuperiamo i dati dal MockDB divisi per categorie
        Map<String, List<MenuProduct>> datiMenu = MockDatabase.getMenuByCategories();

        // Ciclo per ogni Categoria (es. prima "Cocktail", poi "Bibite")
        for (String categoria : datiMenu.keySet()) {

            // 1. Titolo della sezione (Opzionale, ma richiesto dalla logica)
            Label sectionTitle = new Label(categoria);
            sectionTitle.setStyle("-fx-font-size: 12px; -fx-text-fill: #999; -fx-font-weight: bold; -fx-padding: 10 0 5 0;");
            listContent.getChildren().add(sectionTitle);

            // 2. Ciclo per ogni prodotto in quella categoria
            for (MenuProduct prodotto : datiMenu.get(categoria)) {
                // Creiamo la riga per il singolo prodotto
                HBox row = createProductRow(prodotto);
                listContent.getChildren().add(row);
            }
        }

        scrollPane.setContent(listContent);
        root.setCenter(scrollPane);

        return root;
    }

    // --- METODI DI SUPPORTO (Disegnatori) ---

    // Disegna una singola riga: [Nome + Prezzo/Qta] ......... [...]
    private static HBox createProductRow(MenuProduct p) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(15, 0, 15, 0)); // Spazio sopra e sotto ogni riga
        // Linea grigia sotto ogni elemento (bordo inferiore)
        row.setStyle("-fx-border-color: #EEE; -fx-border-width: 0 0 1 0;");

        // Colonna Sinistra: Nome e Dettagli
        VBox infoBox = new VBox(5);

        Label nameLbl = new Label(p.nome);
        nameLbl.setStyle("-fx-font-size: 16px; -fx-text-fill: #333;");

        // Calcolo quantità dal DB
        long qtaVenduta = MockDatabase.getQuantitySold(p.nome);

        Label detailsLbl = new Label(String.format("%.0f€ | Q.ta: %d", p.prezzoVendita, qtaVenduta));
        detailsLbl.setStyle("-fx-font-size: 14px; -fx-text-fill: #777;");

        infoBox.getChildren().addAll(nameLbl, detailsLbl);

        // Spaziatore centrale
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Colonna Destra: I tre puntini (...)
        Label dots = new Label("...");
        dots.setStyle("-fx-font-size: 20px; -fx-text-fill: #555; -fx-padding: 0 10 0 0;");
        dots.setOnMouseClicked(e -> System.out.println("Click opzioni su: " + p.nome));

        row.getChildren().addAll(infoBox, spacer, dots);
        return row;
    }

    // Crea le icone rotonde o semplici
    private static StackPane createIcon(String svgData) {
        SVGPath path = new SVGPath();
        path.setContent(svgData);
        path.setScaleX(1.2);
        path.setScaleY(1.2);
        path.setFill(Color.BLACK);

        StackPane container = new StackPane(path);
        container.setPrefSize(30, 30);
        container.setStyle("-fx-cursor: hand;");
        return container;
    }
}