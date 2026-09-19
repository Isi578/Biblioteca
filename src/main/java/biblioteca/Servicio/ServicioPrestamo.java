package biblioteca.Servicio;

import biblioteca.Interface.LibroRepository;
import biblioteca.Interface.PrestamoRepository;
import biblioteca.model.Libro;
import biblioteca.model.Prestamo;

import java.time.LocalDate;


public class ServicioPrestamo {

    private static int siguienteId = 1;

    private final LibroRepository libroRepository;
    private final PrestamoRepository prestamoRepository;

    public ServicioPrestamo(
            LibroRepository libroRepository,
            PrestamoRepository prestamoRepository
    ) {
        if (libroRepository == null || prestamoRepository == null) {
            throw new IllegalArgumentException(
                    "Los repositorios son obligatorios."
            );
        }

        this.libroRepository = libroRepository;
        this.prestamoRepository = prestamoRepository;
    }

    public Prestamo prestar(String codigoLibro, LocalDate fechaLimite) {
        Libro libro = libroRepository.buscarPorCodigo(codigoLibro)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "No existe un libro con el codigo "
                                        + codigoLibro + "."
                        )
                );

        if (prestamoRepository.buscarActivoPorLibro(codigoLibro)
                .isPresent()) {
            throw new IllegalStateException(
                    "El libro ya tiene un prestamo activo."
            );
        }

        LocalDate fechaPrestamo = LocalDate.now();

        if (fechaLimite == null
                || fechaLimite.isBefore(fechaPrestamo)) {
            throw new IllegalArgumentException(
                    "La fecha limite debe ser hoy o posterior."
            );
        }

        libro.prestar();

        String id = String.valueOf(siguienteId++);

        Prestamo prestamo = new Prestamo(
                id,
                libro,
                fechaPrestamo,
                fechaLimite
        );

        prestamoRepository.guardar(prestamo);
        return prestamo;
    }

    public void devolver(String idPrestamo, LocalDate fechaDevolucion) {
        Prestamo prestamo = prestamoRepository.buscarPorId(idPrestamo)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "No existe un prestamo con ese identificador."
                        )
                );

        prestamo.registrarDevolucion(fechaDevolucion);
        prestamo.getLibro().devolver();
        prestamoRepository.actualizar(prestamo);
    }
}