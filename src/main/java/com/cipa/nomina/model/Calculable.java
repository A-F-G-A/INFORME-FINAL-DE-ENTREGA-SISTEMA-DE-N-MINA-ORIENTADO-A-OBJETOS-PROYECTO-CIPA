package com.cipa.nomina.model;

import java.math.BigDecimal;

/**
 * Interfaz que define la capacidad de un empleado para generar ingresos sujetos a nómina.
 * Principio de Segregación de Interfaces (I): Contrato esencial de cálculo salarial.
 */
public interface Calculable {
    /**
     * Calcula y retorna el salario devengado (o base antes de deducciones/bonos de nómina)
     * de acuerdo a la naturaleza contractual del empleado.
     *
     * @return BigDecimal con el valor devengado base.
     */
    BigDecimal calcularDevengadoBase();
}
