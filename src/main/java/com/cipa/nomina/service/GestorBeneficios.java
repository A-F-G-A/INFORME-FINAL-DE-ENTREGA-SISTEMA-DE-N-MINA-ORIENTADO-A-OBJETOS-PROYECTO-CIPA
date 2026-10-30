package com.cipa.nomina.service;

import com.cipa.nomina.model.Empleado;
import java.math.BigDecimal;

/**
 * Contrato para la gestión de beneficios corporativos y costos asumidos por el empleador.
 * Principio de Inversión de Dependencias (D).
 */
public interface GestorBeneficios {

    /**
     * Calcula el beneficio de alimentación cubierto por la empresa (no deducible al empleado).
     *
     * @param empleado Empleado para verificar elegibilidad.
     * @return Monto del beneficio de alimentación.
     */
    BigDecimal calcularBeneficioAlimentacion(Empleado empleado);

    /**
     * Calcula el costo empresarial de ARL en base al salario base devengado y la tasa patronal.
     *
     * @param salarioBase Salario de referencia.
     * @return Monto del costo de ARL asumido por la empresa.
     */
    BigDecimal calcularCostoArlPatronal(BigDecimal salarioBase);

    /**
     * Obtiene el porcentaje patronal de ARL actualmente configurado.
     */
    BigDecimal getPorcentajeArlPatronal();

    /**
     * Permite ajustar la tasa de ARL de forma explícita y transparente.
     */
    void setPorcentajeArlPatronal(BigDecimal porcentajeArl);
}
