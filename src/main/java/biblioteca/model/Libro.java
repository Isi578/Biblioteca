package biblioteca.model;

public class Libro {

    private String codigo;
    private String titulo;
    private String autor;
    private String categoria;
    private EstadoLibro estado;

    public Libro(String codigo, String titulo, String autor, String categoria) {
        this.codigo = validarTexto(codigo, "El codigo es obligatorio.");
        this.titulo = validarTexto(titulo, "El titulo es obligatorio.");
        this.autor = validarTexto(autor, "El autor es obligatorio.");
        this.categoria = categoria == null || categoria.isBlank()
                ? "General"
                : categoria.trim();
        this.estado = EstadoLibro.DISPONIBLE;
    }

    public void prestar() {
        if (estado != EstadoLibro.DISPONIBLE) {
            throw new IllegalStateException(
                    "El libro no esta disponible para prestamo."
            );
        }

        estado = EstadoLibro.PRESTADO;
    }

    public void devolver() {
        if (estado != EstadoLibro.PRESTADO) {
            throw new IllegalStateException(
                    "El libro no tiene un prestamo activo."
            );
        }

        estado = EstadoLibro.DISPONIBLE;
    }

    // Patron Prototype
    public Libro clonarConNuevoCodigo(String nuevoCodigo) {
        return new Libro(nuevoCodigo, titulo, autor, categoria);
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public EstadoLibro getEstado() {
        return estado;
    }

    private static String validarTexto(String valor, String mensaje) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(mensaje);
        }

        return valor.trim();
    }
}