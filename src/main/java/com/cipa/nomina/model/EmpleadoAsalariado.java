package com.cipa.nomina.model;

import com.cipa.nomina.exception.ValidacionException;
import java.math.BigDecimal;

/**
 * Representa a un empleado asalariado con sueldo fijo mensual.
 * Reglas de negocio:
 * - Salario fijo mensual.
 * - Si antigüedad > 5 años, bono mensual del 10% de su salario. Si tiene 5 años exactos, no recibe bono.
 * - Beneficio de alimentación mensual de $1.000.000 cubierto por la empresa.
 */
public class EmpleadoAsalariado extends Empleado implements Bonificable {

    private final BigDecimal salarioMensual;

    public EmpleadoAsalariado(String id, String nombre, String documento, int antiguedadAnios, BigDecimal salarioMensual) {
        super(id, nombre, documento, antiguedadAnios, TipoEmpleado.ASALARIADO);
        if (salarioMensual == null || salarioMensual.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidacionException("El salario mensual del empleado asalariado debe ser mayor a cero.");
        }
        this.salarioMensual = salarioMensual.setScale(ConstantesNomina.ESCALA_MONETARIA, ConstantesNomina.MODO_REDONDEO);
    }

    @Override
    public BigDecimal calcularDevengadoBase() {
        return salarioMensual;
    }

    @Override
    public BigDecimal calcularBono() {
        // Regla: Antigüedad estrictamente mayor a 5 años (si tiene 5 años, no recibe)
        if (getAntiguedadAnios() > ConstantesNomina.UMBRAL_ANIOS_BONO_ASALARIADO) {
            return salarioMensual.multiply(ConstantesNomina.PORCENTAJE_BONO_ANTIGUEDAD)
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
        return salarioMensual;
    }
}
