package com.cipa.nomina.service;

import com.cipa.nomina.model.Empleado;
import java.math.BigDecimal;

/**
 * Contrato para el cálculo de deducciones salariales a cargo del trabajador.
 * Principio de Inversión de Dependencias (D) y Segregación de Interfaces (I).
 */
public interface CalculadoraDeducciones {

    /**
     * Calcula la deducción legal de Seguro Social y Pensión sobre el salario bruto devengado.
     *
     * @param salarioBruto Salario bruto devengado.
     * @return Monto a deducir por seguridad social y pensión.
     */
    BigDecimal calcularSeguroSocialYPension(BigDecimal salarioBruto);

    /**
     * Calcula el aporte voluntario al fondo de ahorro para los empleados que aplican y lo aceptaron.
     *
     * @param empleado Instancia de Empleado evaluada.
     * @param salarioBruto Salario bruto devengado.
     * @return Monto a deducir por fondo de ahorro.
     */
    BigDecimal calcularFondoAhorro(Empleado empleado, BigDecimal salarioBruto);
}
