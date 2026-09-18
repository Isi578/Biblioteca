package biblioteca.configuracion;

public class ConfiguracionBiblioteca {

    private static final ConfiguracionBiblioteca INSTANCIA =
            new ConfiguracionBiblioteca();

    private String nombre;
    private String direccion;
    private double porcentajeMulta;

    private ConfiguracionBiblioteca() {
        nombre = "Biblioteca Universitaria";
        direccion = "Sin direccion registrada";
        porcentajeMulta = 0.0;
    }

    public static ConfiguracionBiblioteca getInstancia() {
        return INSTANCIA;
    }

    public synchronized void configurar(
            String nombre,
            String direccion,
            double porcentajeMulta
    ) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre de la biblioteca es obligatorio."
            );
        }

        if (direccion == null || direccion.isBlank()) {
            throw new IllegalArgumentException(
                    "La direccion de la biblioteca es obligatoria."
            );
        }

        if (porcentajeMulta < 0) {
            throw new IllegalArgumentException(
                    "El porcentaje de multa no puede ser negativo."
            );
        }

        this.nombre = nombre.trim();
        this.direccion = direccion.trim();
        this.porcentajeMulta = porcentajeMulta;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public double getPorcentajeMulta() {
        return porcentajeMulta;
    }
}
