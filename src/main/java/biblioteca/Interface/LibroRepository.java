package biblioteca.Interface;

import biblioteca.model.Libro;

import java.util.List;
import java.util.Optional;

public interface LibroRepository {

    void guardar(Libro libro);

    Optional<Libro> buscarPorCodigo(String codigo);

    boolean existeCodigo(String codigo);

    List<Libro> listar();
}