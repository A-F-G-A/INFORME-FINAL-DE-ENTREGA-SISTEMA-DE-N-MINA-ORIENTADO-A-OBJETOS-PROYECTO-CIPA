package com.cipa.nomina.model;

import com.cipa.nomina.exception.ValidacionException;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Representa a un empleado vinculado mediante contrato temporal con fecha de vencimiento definida.
 * Reglas de negocio:
 * - Salario fijo mensual acordado.
 * - Requiere fecha de finalización contractual válida y futura respecto al inicio del contrato.
 * - No recibe bonos de nómina ni beneficios adicionales.
 * - Si el contrato está vencido a la fecha de liquidación, se valida y documenta explícitamente.
 */
public class EmpleadoTemporal extends Empleado {

    private final BigDecimal salarioMensual;
    private final LocalDate fechaInicioContrato;
    private final LocalDate fechaFinContrato;

    public EmpleadoTemporal(String id, String nombre, String documento, int antiguedadAnios,
                            BigDecimal salarioMensual, LocalDate fechaInicioContrato, LocalDate fechaFinContrato) {
        super(id, nombre, documento, antiguedadAnios, TipoEmpleado.TEMPORAL);

        if (salarioMensual == null || salarioMensual.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidacionException("El salario mensual del empleado temporal debe ser mayor a cero.");
        }
        if (fechaInicioContrato == null) {
            throw new ValidacionException("La fecha de inicio de contrato es obligatoria para el empleado temporal.");
        }
        if (fechaFinContrato == null) {
            throw new ValidacionException("La fecha de finalización de contrato es obligatoria para el empleado temporal.");
        }
        if (fechaFinContrato.isBefore(fechaInicioContrato)) {
            throw new ValidacionException("La fecha de finalización contractual (" + fechaFinContrato + 
                    ") no puede ser anterior a la fecha de inicio (" + fechaInicioContrato + ").");
        }

        this.salarioMensual = salarioMensual.setScale(ConstantesNomina.ESCALA_MONETARIA, ConstantesNomina.MODO_REDONDEO);
        this.fechaInicioContrato = fechaInicioContrato;
        this.fechaFinContrato = fechaFinContrato;
    }

    public LocalDate getFechaInicioContrato() {
        return fechaInicioContrato;
    }

    public LocalDate getFechaFinContrato() {
        return fechaFinContrato;
    }

    /**
     * Valida si el contrato se encuentra vigente para una fecha de corte o liquidación dada.
     */
    public boolean isContratoVigente(LocalDate fechaCorte) {
        if (fechaCorte == null) {
            return false;
        }
        return !fechaCorte.isBefore(fechaInicioContrato) && !fechaCorte.isAfter(fechaFinContrato);
    }

    @Override
    public BigDecimal calcularDevengadoBase() {
        return salarioMensual;
    }

    @Override
    public boolean tieneDerechoAlimentacion() {
        // Enunciado: No recibe beneficios adicionales
        return false;
    }

    @Override
    public BigDecimal getSalarioBase() {
        return salarioMensual;
    }
}
