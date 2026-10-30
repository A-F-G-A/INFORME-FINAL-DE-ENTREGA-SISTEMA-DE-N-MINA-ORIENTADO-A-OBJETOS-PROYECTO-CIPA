package com.cipa.nomina.model;

import com.cipa.nomina.exception.ValidacionException;
import java.math.BigDecimal;

/**
 * Representa a un empleado que devenga un salario base más una comisión porcentual por ventas.
 * Reglas de negocio:
 * - Salario base + comisión porcentual sobre ventas.
 * - Si ventas > $20.000.000, bono adicional del 3% sobre el total de ventas.
 * - Si ventas = $20.000.000, no recibe bono adicional.
 * - Beneficio de alimentación mensual de $1.000.000 cubierto por la empresa.
 * - Tarifa de comisión configurable y validada.
 */
public class EmpleadoPorComision extends Empleado implements Bonificable {

    private final BigDecimal salarioBase;
    private final BigDecimal porcentajeComision;
    private final BigDecimal ventasRealizadas;

    public EmpleadoPorComision(String id, String nombre, String documento, int antiguedadAnios,
                               BigDecimal salarioBase, BigDecimal porcentajeComision, BigDecimal ventasRealizadas) {
        super(id, nombre, documento, antiguedadAnios, TipoEmpleado.POR_COMISION);

        if (salarioBase == null || salarioBase.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidacionException("El salario base no puede ser negativo.");
        }
        if (porcentajeComision == null || porcentajeComision.compareTo(BigDecimal.ZERO) < 0 
                || porcentajeComision.compareTo(BigDecimal.ONE) > 0) {
            throw new ValidacionException("El porcentaje de comisión debe estar entre 0.00 y 1.00 (ejemplo: 0.05 para 5%).");
        }
        if (ventasRealizadas == null || ventasRealizadas.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidacionException("El monto de ventas realizadas no puede ser negativo.");
        }

        this.salarioBase = salarioBase.setScale(ConstantesNomina.ESCALA_MONETARIA, ConstantesNomina.MODO_REDONDEO);
        this.porcentajeComision = porcentajeComision;
        this.ventasRealizadas = ventasRealizadas.setScale(ConstantesNomina.ESCALA_MONETARIA, ConstantesNomina.MODO_REDONDEO);
    }

    public BigDecimal getPorcentajeComision() {
        return porcentajeComision;
    }

    public BigDecimal getVentasRealizadas() {
        return ventasRealizadas;
    }

    /**
     * Calcula la comisión regular por ventas: ventasRealizadas * porcentajeComision.
     */
    public BigDecimal getComisionPorVentas() {
        return ventasRealizadas.multiply(porcentajeComision)
                .setScale(ConstantesNomina.ESCALA_MONETARIA, ConstantesNomina.MODO_REDONDEO);
    }

    @Override
    public BigDecimal calcularDevengadoBase() {
        // En empleado por comisión, el ingreso base devengado es salarioBase + comisión sobre ventas
        return salarioBase.add(getComisionPorVentas())
                .setScale(ConstantesNomina.ESCALA_MONETARIA, ConstantesNomina.MODO_REDONDEO);
    }

    @Override
    public BigDecimal calcularBono() {
        // Regla: Si ventas > $20.000.000, bono adicional del 3% sobre el total de ventas.
        // Si ventas == $20.000.000, no recibe el bono.
        if (ventasRealizadas.compareTo(ConstantesNomina.UMBRAL_VENTAS_COMISION) > 0) {
            return ventasRealizadas.multiply(ConstantesNomina.PORCENTAJE_BONO_VENTAS_COMISION)
                    .setScale(ConstantesNomina.ESCALA_MONETARIA, ConstantesNomina.MODO_REDONDEO);
        }
        return BigDecimal.ZERO.setScale(ConstantesNomina.ESCALA_MONETARIA, ConstantesNomina.MODO_REDONDEO);
    }

    @Override
    public boolean tieneDerechoAlimentacion() {
        return true;
    }

    @Override
    public BigDecimal getSalarioBase() {
        return salarioBase;
    }
}
