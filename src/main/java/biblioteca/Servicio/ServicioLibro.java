package biblioteca.Servicio;

import biblioteca.Interface.LibroRepository;
import biblioteca.creational.LibroBuilder;
import biblioteca.model.Libro;

import java.util.List;

public class ServicioLibro {

    private final LibroRepository libroRepository;

    public ServicioLibro(LibroRepository libroRepository) {
        if (libroRepository == null) {
            throw new IllegalArgumentException(
                    "El repositorio de libros es obligatorio."
            );
        }

        this.libroRepository = libroRepository;
    }

    public Libro registrar(
            String codigo,
            String titulo,
            String autor,
            String categoria
    ) {
        Libro libro = new LibroBuilder(codigo, titulo, autor)
                .conCategoria(categoria)
                .build();

        return registrar(libro);
    }

    public Libro registrar(Libro libro) {
        if (libro == null) {
            throw new IllegalArgumentException(
                    "El libro es obligatorio."
            );
        }

        if (libroRepository.existeCodigo(libro.getCodigo())) {
            throw new IllegalArgumentException(
                    "Ya existe un libro con el codigo "
                            + libro.getCodigo() + "."
            );
        }

        libroRepository.guardar(libro);
        return libro;
    }

    public Libro clonar(String codigoOrigen, String nuevoCodigo) {
        Libro original = buscar(codigoOrigen);

        if (libroRepository.existeCodigo(nuevoCodigo)) {
            throw new IllegalArgumentException(
                    "Ya existe un libro con el codigo "
                            + nuevoCodigo + "."
            );
        }

        Libro copia = original.clonarConNuevoCodigo(nuevoCodigo);
        libroRepository.guardar(copia);

        return copia;
    }

    public Libro buscar(String codigo) {
        return libroRepository.buscarPorCodigo(codigo)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "No existe un libro con el codigo "
                                        + codigo + "."
                        )
                );
    }

    public List<Libro> listar() {
        return libroRepository.listar();
    }
}