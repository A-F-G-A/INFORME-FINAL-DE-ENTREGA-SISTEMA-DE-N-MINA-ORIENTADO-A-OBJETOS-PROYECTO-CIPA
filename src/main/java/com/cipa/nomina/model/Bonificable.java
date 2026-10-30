package com.cipa.nomina.model;

import java.math.BigDecimal;

/**
 * Interfaz que define la capacidad de un empleado para percibir bonos de desempeño o antigüedad.
 * Segregación de Interfaces (I): solo la implementan aquellos empleados sujetos a bonos según las reglas.
 */
public interface Bonificable {
    /**
     * Calcula el monto del bono aplicable según las condiciones del empleado.
     * Si no cumple la condición, debe retornar BigDecimal.ZERO.
     *
     * @return BigDecimal con el valor del bono.
     */
    BigDecimal calcularBono();
}
