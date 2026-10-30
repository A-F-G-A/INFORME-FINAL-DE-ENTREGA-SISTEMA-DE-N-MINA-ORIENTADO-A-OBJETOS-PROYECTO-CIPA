package com.cipa.nomina.model;

/**
 * Enumeración que identifica los tipos de empleados administrados por el sistema.
 */
public enum TipoEmpleado {
    ASALARIADO("Empleado Asalariado"),
    POR_HORAS("Empleado por Horas"),
    POR_COMISION("Empleado por Comisión"),
    TEMPORAL("Empleado Temporal");

    private final String descripcion;

    TipoEmpleado(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
