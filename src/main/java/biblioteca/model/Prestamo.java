package biblioteca.model;

import java.time.LocalDate;

public class Prestamo {

    private String id;
    private Libro libro;
    private LocalDate fechaPrestamo;
    private LocalDate fechaLimite;
    private LocalDate fechaDevolucion;

    public Prestamo(
            String id,
            Libro libro,
            LocalDate fechaPrestamo,
            LocalDate fechaLimite
    ) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException(
                    "El identificador del prestamo es obligatorio."
            );
        }

        if (libro == null) {
            throw new IllegalArgumentException(
                    "El libro del prestamo es obligatorio."
            );
        }

        if (fechaPrestamo == null || fechaLimite == null) {
            throw new IllegalArgumentException(
                    "Las fechas del prestamo son obligatorias."
            );
        }

        if (fechaLimite.isBefore(fechaPrestamo)) {
            throw new IllegalArgumentException(
                    "La fecha limite no puede ser anterior a la fecha de prestamo."
            );
        }

        this.id = id.trim();
        this.libro = libro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaLimite = fechaLimite;
    }

    public void registrarDevolucion(LocalDate fechaDevolucion) {
        if (!estaActivo()) {
            throw new IllegalStateException(
                    "El prestamo ya fue devuelto."
            );
        }

        if (fechaDevolucion == null
                || fechaDevolucion.isBefore(fechaPrestamo)) {
            throw new IllegalArgumentException(
                    "La fecha de devolucion no es valida."
            );
        }

        this.fechaDevolucion = fechaDevolucion;
    }

    public boolean estaActivo() {
        return fechaDevolucion == null;
    }

    public boolean fueDevueltoTarde() {
        return fechaDevolucion != null
                && fechaDevolucion.isAfter(fechaLimite);
    }

    public String getId() {
        return id;
    }

    public Libro getLibro() {
        return libro;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }
}
