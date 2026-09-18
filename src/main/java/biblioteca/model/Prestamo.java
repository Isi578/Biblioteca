package biblioteca.model;

import java.time.LocalDate;

public class Prestamo {
    private String id;
    private Libro theLibro;
    private LocalDate fechaPrestamo;
    private LocalDate fechaLimite;
    private LocalDate fechaDevolucion;
}
