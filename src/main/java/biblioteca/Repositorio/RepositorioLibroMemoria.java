package biblioteca.Repositorio;

import biblioteca.Interface.LibroRepository;
import biblioteca.model.Libro;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RepositorioLibroMemoria implements LibroRepository {

    private final Map<String, Libro> libros = new LinkedHashMap<>();

    @Override
    public void guardar(Libro libro) {
        if (libro == null) {
            throw new IllegalArgumentException("El libro es obligatorio.");
        }

        if (libros.containsKey(libro.getCodigo())) {
            throw new IllegalArgumentException(
                    "Ya existe un libro con ese codigo."
            );
        }

        libros.put(libro.getCodigo(), libro);
    }

    @Override
    public Optional<Libro> buscarPorCodigo(String codigo) {
        return Optional.ofNullable(libros.get(codigo));
    }

    @Override
    public boolean existeCodigo(String codigo) {
        return libros.containsKey(codigo);
    }

    @Override
    public List<Libro> listar() {
        return new ArrayList<>(libros.values());
    }
}
