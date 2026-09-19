package biblioteca.controller;

import biblioteca.Interface.LibroRepository;
import biblioteca.Interface.PrestamoRepository;
import biblioteca.Repositorio.RepositorioLibroMemoria;
import biblioteca.Repositorio.RepositorioPrestamoMemoria;
import biblioteca.Servicio.ServicioLibro;
import biblioteca.Servicio.ServicioPrestamo;
import biblioteca.configuracion.ConfiguracionBiblioteca;
import biblioteca.model.Libro;
import biblioteca.model.Prestamo;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class BibliotecaController {

    private final LibroRepository libroRepository =
            new RepositorioLibroMemoria();

    private final PrestamoRepository prestamoRepository =
            new RepositorioPrestamoMemoria();

    private final ServicioLibro servicioLibro =
            new ServicioLibro(libroRepository);

    private final ServicioPrestamo servicioPrestamo =
            new ServicioPrestamo(
                    libroRepository,
                    prestamoRepository
            );

    private final ConfiguracionBiblioteca configuracion =
            ConfiguracionBiblioteca.getInstancia();


    // =========================
    // REGISTRO DE LIBROS
    // =========================

    @FXML
    private TextField codigoLibro;

    @FXML
    private TextField tituloLibro;

    @FXML
    private TextField autorLibro;

    @FXML
    private TextField categoriaLibro;


    // =========================
    // CLONAR LIBRO
    // =========================

    @FXML
    private TextField codigoLibroOriginal;

    @FXML
    private TextField nuevoCodigoLibro;


    // =========================
    // TABLA DE LIBROS
    // =========================

    @FXML
    private TableView<Libro> tablaLibros;

    @FXML
    private TableColumn<Libro, String> columnaCodigo;

    @FXML
    private TableColumn<Libro, String> columnaTitulo;

    @FXML
    private TableColumn<Libro, String> columnaAutor;

    @FXML
    private TableColumn<Libro, String> columnaCategoria;

    @FXML
    private TableColumn<Libro, String> columnaEstado;


    // =========================
    // PRÉSTAMOS
    // =========================

    @FXML
    private TextField codigoLibroPrestamo;

    @FXML
    private DatePicker fechaLimitePrestamo;

    @FXML
    private TextField idPrestamoDevolucion;

    @FXML
    private DatePicker fechaDevolucion;


    // =========================
    // CONFIGURACIÓN
    // =========================

    @FXML
    private TextField nombreBiblioteca;

    @FXML
    private TextField direccionBiblioteca;

    @FXML
    private TextField porcentajeMulta;

    @FXML
    private Label configuracionActual;


    @FXML
    public void initialize() {

        configurarColumnas();

        actualizarTablaLibros();

        fechaLimitePrestamo.setValue(
                LocalDate.now().plusDays(7)
        );

        fechaDevolucion.setValue(
                LocalDate.now()
        );

        cargarConfiguracion();
    }


    // =========================
    // CONFIGURAR TABLA
    // =========================

    private void configurarColumnas() {

        columnaCodigo.setCellValueFactory(
                celda -> new ReadOnlyStringWrapper(
                        celda.getValue().getCodigo()
                )
        );

        columnaTitulo.setCellValueFactory(
                celda -> new ReadOnlyStringWrapper(
                        celda.getValue().getTitulo()
                )
        );

        columnaAutor.setCellValueFactory(
                celda -> new ReadOnlyStringWrapper(
                        celda.getValue().getAutor()
                )
        );

        columnaCategoria.setCellValueFactory(
                celda -> new ReadOnlyStringWrapper(
                        celda.getValue().getCategoria()
                )
        );

        columnaEstado.setCellValueFactory(
                celda -> new ReadOnlyStringWrapper(
                        celda.getValue().getEstado().toString()
                )
        );
    }


    // =========================
    // REGISTRAR LIBRO
    // =========================

    @FXML
    public void registrarLibro() {

        try {

            servicioLibro.registrar(
                    codigoLibro.getText(),
                    tituloLibro.getText(),
                    autorLibro.getText(),
                    categoriaLibro.getText()
            );

            mostrarInformacion(
                    "Libro registrado",
                    "El libro se registró correctamente."
            );

            limpiarCamposLibro();
            actualizarTablaLibros();

        } catch (RuntimeException e) {

            mostrarError(e.getMessage());
        }
    }


    // =========================
    // CLONAR LIBRO
    // =========================

    @FXML
    public void clonarLibro() {

        try {

            servicioLibro.clonar(
                    codigoLibroOriginal.getText(),
                    nuevoCodigoLibro.getText()
            );

            mostrarInformacion(
                    "Libro clonado",
                    "El libro fue clonado correctamente."
            );

            codigoLibroOriginal.clear();
            nuevoCodigoLibro.clear();

            actualizarTablaLibros();

        } catch (RuntimeException e) {

            mostrarError(e.getMessage());
        }
    }


    // =========================
    // ACTUALIZAR TABLA
    // =========================

    @FXML
    public void actualizarTablaLibros() {

        tablaLibros
                .getItems()
                .setAll(servicioLibro.listar());
    }


    // =========================
    // REALIZAR PRÉSTAMO
    // =========================

    @FXML
    public void prestarLibro() {

        try {

            Prestamo prestamo =
                    servicioPrestamo.prestar(
                            codigoLibroPrestamo.getText(),
                            fechaLimitePrestamo.getValue()
                    );

            mostrarInformacion(
                    "Préstamo realizado",
                    "Préstamo registrado correctamente.\n\n"
                            + "ID del préstamo:\n"
                            + prestamo.getId()
            );

            codigoLibroPrestamo.clear();

            actualizarTablaLibros();

        } catch (RuntimeException e) {

            mostrarError(e.getMessage());
        }
    }


    // =========================
    // DEVOLVER LIBRO
    // =========================

    @FXML
    public void devolverLibro() {

        try {

            String id = idPrestamoDevolucion.getText();

            Prestamo prestamo =
                    prestamoRepository.buscarPorId(id)
                            .orElseThrow(() ->
                                    new IllegalArgumentException(
                                            "No existe un préstamo con ese identificador."
                                    )
                            );

            servicioPrestamo.devolver(
                    id,
                    fechaDevolucion.getValue()
            );

            String mensaje;

            if (prestamo.fueDevueltoTarde()) {

                mensaje =
                        "Devolución registrada.\n\n"
                                + "El préstamo fue devuelto después de la fecha límite.";

            } else {

                mensaje =
                        "Devolución registrada correctamente.";
            }

            mostrarInformacion(
                    "Devolución",
                    mensaje
            );

            idPrestamoDevolucion.clear();

            actualizarTablaLibros();

        } catch (RuntimeException e) {

            mostrarError(e.getMessage());
        }
    }


    // =========================
    // CONFIGURACIÓN
    // =========================

    @FXML
    public void guardarConfiguracion() {

        try {

            double porcentaje =
                    Double.parseDouble(
                            porcentajeMulta.getText()
                                    .replace(",", ".")
                    );

            configuracion.configurar(
                    nombreBiblioteca.getText(),
                    direccionBiblioteca.getText(),
                    porcentaje
            );

            cargarConfiguracion();

            mostrarInformacion(
                    "Configuración",
                    "La configuración fue actualizada correctamente."
            );

        } catch (NumberFormatException e) {

            mostrarError(
                    "El porcentaje de multa debe ser un número."
            );

        } catch (RuntimeException e) {

            mostrarError(e.getMessage());
        }
    }


    // =========================
    // CARGAR CONFIGURACIÓN
    // =========================

    private void cargarConfiguracion() {

        nombreBiblioteca.setText(
                configuracion.getNombre()
        );

        direccionBiblioteca.setText(
                configuracion.getDireccion()
        );

        porcentajeMulta.setText(
                String.valueOf(
                        configuracion.getPorcentajeMulta()
                )
        );

        configuracionActual.setText(
                "Biblioteca: "
                        + configuracion.getNombre()
                        + "\nDirección: "
                        + configuracion.getDireccion()
                        + "\nMulta: "
                        + configuracion.getPorcentajeMulta()
                        + "%"
        );
    }


    // =========================
    // LIMPIAR CAMPOS
    // =========================

    private void limpiarCamposLibro() {

        codigoLibro.clear();
        tituloLibro.clear();
        autorLibro.clear();
        categoriaLibro.clear();
    }


    // =========================
    // ALERTAS
    // =========================

    private void mostrarInformacion(
            String titulo,
            String mensaje
    ) {

        Alert alert =
                new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        alert.showAndWait();
    }


    private void mostrarError(String mensaje) {

        Alert alert =
                new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        alert.showAndWait();
    }
}