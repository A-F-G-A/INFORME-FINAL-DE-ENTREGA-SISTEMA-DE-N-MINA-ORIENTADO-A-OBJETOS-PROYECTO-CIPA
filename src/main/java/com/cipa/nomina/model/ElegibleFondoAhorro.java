package com.cipa.nomina.model;

/**
 * Interfaz que define la elegibilidad y participación en el fondo de ahorro institucional.
 * Segregación de Interfaces (I): aplicable exclusivamente a empleados con esquema de ahorro.
 */
public interface ElegibleFondoAhorro {
    /**
     * Indica si el empleado cumple con el criterio de antigüedad para optar al fondo.
     *
     * @return true si cumple antigüedad mínima requerida (> 1 año).
     */
    boolean cumpleRequisitosFondo();

    /**
     * Indica si el empleado manifestó formalmente su aceptación de participar en el fondo.
     *
     * @return true si acepta participar.
     */
    boolean isAceptaFondoAhorro();
}
