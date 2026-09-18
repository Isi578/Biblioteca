package biblioteca.Interface;

import biblioteca.model.Prestamo;

import java.util.Optional;

public interface PrestamoRepository {

    void guardar(Prestamo prestamo);

    void actualizar(Prestamo prestamo);

    Optional<Prestamo> buscarPorId(String id);

    Optional<Prestamo> buscarActivoPorLibro(String codigoLibro);
}