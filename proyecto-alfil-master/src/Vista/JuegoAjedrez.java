//package Vista;
//
///**
// *
// * @author Yieison
// */
//import Negocio.Tablero;
//
//
//public class JuegoAjedrez extends Application {
//
//    private Scene escenaPrincipal;
//    private ComboBox<String> selectorColorAlfil;
//    private ComboBox<Integer> selectorFilaPeon, selectorColumnaPeon, selectorFilaAlfil, selectorColumnaAlfil;
//    private Label etiquetaPeon, etiquetaAlfil;
//    private ImageView imagenPeonView, imagenAlfilView;
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    @Override
//    public void start(Stage escenarioPrincipal) {
//        escenarioPrincipal.setTitle("Ajedrez: Peón vs Alfil");
//
//        VBox layoutPrincipal = new VBox(20, crearTitulo(), crearGridPrincipal());
//        layoutPrincipal.setStyle("-fx-alignment: center;");
//
//        BorderPane layoutConTexto = new BorderPane();
//        layoutConTexto.setCenter(layoutPrincipal);
//        layoutConTexto.setBottom(crearDesarrolladores());
//        BorderPane.setMargin(crearDesarrolladores(), new Insets(10, 10, 10, 10));
//
//        escenaPrincipal = new Scene(layoutConTexto, 800, 600);
//        escenaPrincipal.getStylesheets().add(getClass().getResource("/resources/estilos.css").toExternalForm());
//
//        escenarioPrincipal.setScene(escenaPrincipal);
//        escenarioPrincipal.show();
//    }
//
//    private Label crearTitulo() {
//        Label titulo = new Label("Juego de Ajedrez: Peón vs Alfil");
//        titulo.getStyleClass().add("label-title");
//        return titulo;
//    }
//
//    private GridPane crearGridPrincipal() {
//        GridPane gridPrincipal = new GridPane();
//        gridPrincipal.setPadding(new Insets(10, 10, 10, 10));
//        gridPrincipal.setVgap(8);
//        gridPrincipal.setHgap(10);
//
//        agregarComponentesGrid(gridPrincipal);
//
//        gridPrincipal.setStyle("-fx-alignment: center;");
//        return gridPrincipal;
//    }
//
//    private void agregarComponentesGrid(GridPane gridPrincipal) {
//        // Configuración del Peón
//        Label etiquetaPosPeon = new Label("Peón");
//        etiquetaPosPeon.setStyle("-fx-font-weight: bold;");
//        GridPane.setConstraints(etiquetaPosPeon, 0, 0);
//        Label etiquetaFilaPeon = new Label("Fila:");
//        GridPane.setConstraints(etiquetaFilaPeon, 1, 0);
//        selectorFilaPeon = crearComboBox(0, 7);
//        GridPane.setConstraints(selectorFilaPeon, 2, 0);
//        Label etiquetaColumnaPeon = new Label("Columna:");
//        GridPane.setConstraints(etiquetaColumnaPeon, 3, 0);
//        selectorColumnaPeon = crearComboBox(0, 7);
//        GridPane.setConstraints(selectorColumnaPeon, 4, 0);
//        Image imagenPeon = new Image(getClass().getResourceAsStream("/resources/peon.png"));
//        imagenPeonView = new ImageView(imagenPeon);
//        imagenPeonView.setFitHeight(100);
//        imagenPeonView.setFitWidth(100);
//        etiquetaPeon = new Label("", imagenPeonView);
//        GridPane.setConstraints(etiquetaPeon, 5, 0);
//
//        // Configuración del Alfil
//        Label etiquetaPosAlfil = new Label("Alfil");
//        etiquetaPosAlfil.setStyle("-fx-font-weight: bold;");
//        GridPane.setConstraints(etiquetaPosAlfil, 0, 1);
//        Label etiquetaFilaAlfil = new Label("Fila:");
//        GridPane.setConstraints(etiquetaFilaAlfil, 1, 1);
//        selectorFilaAlfil = crearComboBox(0, 7);
//        GridPane.setConstraints(selectorFilaAlfil, 2, 1);
//        Label etiquetaColumnaAlfil = new Label("Columna:");
//        GridPane.setConstraints(etiquetaColumnaAlfil, 3, 1);
//        selectorColumnaAlfil = crearComboBox(0, 7);
//        GridPane.setConstraints(selectorColumnaAlfil, 4, 1);
//        Image imagenAlfil = new Image(getClass().getResourceAsStream("/resources/alfil.png"));
//        imagenAlfilView = new ImageView(imagenAlfil);
//        imagenAlfilView.setFitHeight(100);
//        imagenAlfilView.setFitWidth(100);
//        etiquetaAlfil = new Label("", imagenAlfilView);
//        GridPane.setConstraints(etiquetaAlfil, 5, 1);
//
//        // Configuración del Color del Alfil
//        Label etiquetaColorAlfil = new Label("Color Alfil:");
//        GridPane.setConstraints(etiquetaColorAlfil, 0, 2);
//        selectorColorAlfil = new ComboBox<>();
//        selectorColorAlfil.getItems().addAll("Blanco", "Negro");
//        GridPane.setConstraints(selectorColorAlfil, 1, 2);
//
//        // Botón de jugar
//        Button botonJugar = new Button("Jugar");
//        botonJugar.getStyleClass().add("button");
//        GridPane.setConstraints(botonJugar, 1, 3);
//
//        botonJugar.setOnAction(e -> {
//            Integer filaPeon = selectorFilaPeon.getValue();
//            Integer columnaPeon = selectorColumnaPeon.getValue();
//            Integer filaAlfil = selectorFilaAlfil.getValue();
//            Integer columnaAlfil = selectorColumnaAlfil.getValue();
//            String colorAlfil = selectorColorAlfil.getValue();
//
//            if (filaPeon != null && columnaPeon != null && filaAlfil != null && columnaAlfil != null && colorAlfil != null) {
//                if (filaPeon.equals(filaAlfil) && columnaPeon.equals(columnaAlfil)) {
//                    mostrarAlerta("Error", "El peón y el alfil no pueden ocupar la misma posición.");
//                } else {
//                    Tablero tab = new Tablero(filaAlfil, columnaAlfil, filaPeon, columnaPeon, colorAlfil);
//                    tab.jugar(filaAlfil, columnaAlfil, filaPeon, columnaPeon);
//                }
//            } else {
//                mostrarAlerta("Error", "Por favor, selecciona todas las posiciones y el color del alfil.");
//            }
//        });
//
//        gridPrincipal.getChildren().addAll(
//                etiquetaPosPeon, etiquetaFilaPeon, selectorFilaPeon, etiquetaColumnaPeon, selectorColumnaPeon, etiquetaPeon,
//                etiquetaPosAlfil, etiquetaFilaAlfil, selectorFilaAlfil, etiquetaColumnaAlfil, selectorColumnaAlfil, etiquetaAlfil,
//                etiquetaColorAlfil, selectorColorAlfil, botonJugar
//        );
//
//        // Configurar evento de selección de color
//        selectorColorAlfil.setOnAction(e -> actualizarColorPiezas());
//    }
//
//    private ComboBox<Integer> crearComboBox(int inicio, int fin) {
//        ComboBox<Integer> comboBox = new ComboBox<>();
//        for (int i = inicio; i <= fin; i++) {
//            comboBox.getItems().add(i);
//        }
//        return comboBox;
//    }
//
//    private HBox crearDesarrolladores() {
//        Image imagenIcono = new Image(getClass().getResourceAsStream("/resources/sistemas.png"));
//        ImageView iconoView = new ImageView(imagenIcono);
//        iconoView.setFitHeight(100);
//        iconoView.setFitWidth(100);
//
//        Label desarrolladoPor = new Label("Desarrollado por: \nYieison Lizarazo - 1151938 \nRoberth Caicedo - 1151996");
//        desarrolladoPor.getStyleClass().add("label-footer");
//
//        HBox hboxDesarrolladoPor = new HBox(10, iconoView, desarrolladoPor);
//        hboxDesarrolladoPor.setPadding(new Insets(10));
//        hboxDesarrolladoPor.setStyle("-fx-alignment: center;");
//        return hboxDesarrolladoPor;
//    }
//
//    private void actualizarColorPiezas() {
//        String colorAlfil = selectorColorAlfil.getValue();
//        if (colorAlfil != null) {
//            if (colorAlfil.equals("Blanco")) {
//                imagenAlfilView.setImage(new Image(getClass().getResourceAsStream("/resources/alfil_blanco.png")));
//                imagenPeonView.setImage(new Image(getClass().getResourceAsStream("/resources/peon_negro.png")));
//                etiquetaAlfil.setText("Alfil (Blanco)");
//                etiquetaPeon.setText("Peón (Negro) (↑)");
//            } else {
//                imagenAlfilView.setImage(new Image(getClass().getResourceAsStream("/resources/alfil_negro.png")));
//                imagenPeonView.setImage(new Image(getClass().getResourceAsStream("/resources/peon_blanco.png")));
//                etiquetaAlfil.setText("Alfil (Negro)");
//                etiquetaPeon.setText("Peón (Blanco) (↓)");
//            }
//        }
//    }
//
//    private void mostrarAlerta(String titulo, String mensaje) {
//        Alert alerta = new Alert(Alert.AlertType.ERROR);
//        alerta.setTitle(titulo);
//        alerta.setHeaderText(null);
//        alerta.setContentText(mensaje);
//        alerta.showAndWait();
//    }
//}
