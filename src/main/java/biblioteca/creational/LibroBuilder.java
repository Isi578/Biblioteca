package biblioteca.creational;

import biblioteca.model.Libro;

public class LibroBuilder {

    private final String codigo;
    private final String titulo;
    private final String autor;
    private String categoria = "General";

    public LibroBuilder(String codigo, String titulo, String autor) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
    }

    public LibroBuilder conCategoria(String categoria) {
        this.categoria = categoria;
        return this;
    }

    public Libro build() {
        return new Libro(codigo, titulo, autor, categoria);
    }
}

