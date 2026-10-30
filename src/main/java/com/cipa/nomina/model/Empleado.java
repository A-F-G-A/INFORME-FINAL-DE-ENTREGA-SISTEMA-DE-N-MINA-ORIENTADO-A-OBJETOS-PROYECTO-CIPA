package com.cipa.nomina.model;

import com.cipa.nomina.exception.ValidacionException;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Clase base abstracta que representa a un empleado en la organización.
 * Aplica el principio de sustitución de Liskov (L) y Responsabilidad Única (S).
 */
public abstract class Empleado implements Calculable {

    private final String id;
    private final String nombre;
    private final String documento;
    private final int antiguedadAnios;
    private final TipoEmpleado tipoEmpleado;

    /**
     * Constructor base de Empleado.
     *
     * @param id Identificador interno del empleado (no nulo, no vacío).
     * @param nombre Nombre completo del empleado (no nulo, no vacío).
     * @param documento Documento de identidad no sensible o código institucional.
     * @param antiguedadAnios Antigüedad en años en la empresa (>= 0).
     * @param tipoEmpleado Tipo de empleado asociado.
     */
    public Empleado(String id, String nombre, String documento, int antiguedadAnios, TipoEmpleado tipoEmpleado) {
        if (id == null || id.trim().isEmpty()) {
            throw new ValidacionException("El identificador del empleado es obligatorio y no puede estar vacío.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidacionException("El nombre del empleado es obligatorio y no puede estar vacío.");
        }
        if (documento == null || documento.trim().isEmpty()) {
            throw new ValidacionException("El documento del empleado es obligatorio y no puede estar vacío.");
        }
        if (antiguedadAnios < 0) {
            throw new ValidacionException("La antigüedad no puede ser negativa: " + antiguedadAnios);
        }
        if (tipoEmpleado == null) {
            throw new ValidacionException("El tipo de empleado es obligatorio.");
        }

        this.id = id.trim();
        this.nombre = nombre.trim();
        this.documento = documento.trim();
        this.antiguedadAnios = antiguedadAnios;
        this.tipoEmpleado = tipoEmpleado;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public int getAntiguedadAnios() {
        return antiguedadAnios;
    }

    public TipoEmpleado getTipoEmpleado() {
        return tipoEmpleado;
    }

    /**
     * Indica si el empleado tiene derecho estatutario al beneficio de alimentación ($1.000.000).
     * Según el enunciado, aplica a Asalariados y Empleados por Comisión.
     *
     * @return true si tiene derecho al auxilio de alimentación cubierto por la empresa.
     */
    public abstract boolean tieneDerechoAlimentacion();

    /**
     * Retorna el salario base nominal contractual del empleado para propósitos de reporte y auditoría.
     *
     * @return BigDecimal con el salario base.
     */
    public abstract BigDecimal getSalarioBase();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empleado empleado = (Empleado) o;
        return Objects.equals(id, empleado.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s (%s, Antigüedad: %d años)", 
                id, nombre, documento, tipoEmpleado.getDescripcion(), antiguedadAnios);
    }
}
