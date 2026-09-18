package biblioteca.Repositorio;

import biblioteca.Interface.PrestamoRepository;
import biblioteca.model.Prestamo;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class RepositorioPrestamoMemoria implements PrestamoRepository {

    private final Map<String, Prestamo> prestamos = new LinkedHashMap<>();

    @Override
    public void guardar(Prestamo prestamo) {
        if (prestamo == null) {
            throw new IllegalArgumentException(
                    "El prestamo es obligatorio."
            );
        }

        if (prestamos.containsKey(prestamo.getId())) {
            throw new IllegalArgumentException(
                    "Ya existe un prestamo con ese identificador."
            );
        }

        prestamos.put(prestamo.getId(), prestamo);
    }

    @Override
    public void actualizar(Prestamo prestamo) {
        if (prestamo == null
                || !prestamos.containsKey(prestamo.getId())) {
            throw new IllegalArgumentException(
                    "El prestamo no existe."
            );
        }

        prestamos.put(prestamo.getId(), prestamo);
    }

    @Override
    public Optional<Prestamo> buscarPorId(String id) {
        return Optional.ofNullable(prestamos.get(id));
    }

    @Override
    public Optional<Prestamo> buscarActivoPorLibro(String codigoLibro) {
        return prestamos.values().stream()
                .filter(prestamo ->
                        prestamo.getLibro()
                                .getCodigo()
                                .equals(codigoLibro)
                )
                .filter(Prestamo::estaActivo)
                .findFirst();
    }
}