package com.cipa.nomina.model;

import com.cipa.nomina.exception.ValidacionException;
import java.math.BigDecimal;

/**
 * Representa a un empleado remunerado por horas trabajadas.
 * Reglas de negocio:
 * - Primeras 40 horas a tarifa normal.
 * - Horas que superen las 40 se pagan a 1.5 veces la tarifa normal.
 * - No recibe bonos de nómina.
 * - Si antigüedad > 1 año y acepta participar, 2% de deducción para fondo de ahorro.
 * - No tiene auxilio de alimentación.
 */
public class EmpleadoPorHoras extends Empleado implements ElegibleFondoAhorro {

    private final BigDecimal tarifaPorHora;
    private final BigDecimal horasTrabajadas;
    private final boolean aceptaFondoAhorro;

    public EmpleadoPorHoras(String id, String nombre, String documento, int antiguedadAnios,
                            BigDecimal tarifaPorHora, BigDecimal horasTrabajadas, boolean aceptaFondoAhorro) {
        super(id, nombre, documento, antiguedadAnios, TipoEmpleado.POR_HORAS);

        if (tarifaPorHora == null || tarifaPorHora.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidacionException("La tarifa por hora debe ser un valor positivo estrictamente mayor a cero.");
        }
        if (horasTrabajadas == null || horasTrabajadas.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidacionException("Las horas trabajadas no pueden ser negativas.");
        }

        this.tarifaPorHora = tarifaPorHora.setScale(ConstantesNomina.ESCALA_MONETARIA, ConstantesNomina.MODO_REDONDEO);
        this.horasTrabajadas = horasTrabajadas.setScale(2, ConstantesNomina.MODO_REDONDEO);
        this.aceptaFondoAhorro = aceptaFondoAhorro;
    }

    public BigDecimal getTarifaPorHora() {
        return tarifaPorHora;
    }

    public BigDecimal getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public BigDecimal getHorasNormales() {
        if (horasTrabajadas.compareTo(ConstantesNomina.HORAS_BASE_SEMANALES) <= 0) {
            return horasTrabajadas;
        }
        return ConstantesNomina.HORAS_BASE_SEMANALES;
    }

    public BigDecimal getHorasExtras() {
        if (horasTrabajadas.compareTo(ConstantesNomina.HORAS_BASE_SEMANALES) > 0) {
            return horasTrabajadas.subtract(ConstantesNomina.HORAS_BASE_SEMANALES);
        }
        return BigDecimal.ZERO.setScale(2, ConstantesNomina.MODO_REDONDEO);
    }

    public BigDecimal getPagoHorasNormales() {
        return getHorasNormales().multiply(tarifaPorHora)
                .setScale(ConstantesNomina.ESCALA_MONETARIA, ConstantesNomina.MODO_REDONDEO);
    }

    public BigDecimal getPagoHorasExtras() {
        BigDecimal horasExtras = getHorasExtras();
        if (horasExtras.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO.setScale(ConstantesNomina.ESCALA_MONETARIA, ConstantesNomina.MODO_REDONDEO);
        }
        BigDecimal tarifaExtra = tarifaPorHora.multiply(ConstantesNomina.FACTOR_HORA_EXTRA);
        return horasExtras.multiply(tarifaExtra)
                .setScale(ConstantesNomina.ESCALA_MONETARIA, ConstantesNomina.MODO_REDONDEO);
    }

    @Override
    public BigDecimal calcularDevengadoBase() {
        return getPagoHorasNormales().add(getPagoHorasExtras())
                .setScale(ConstantesNomina.ESCALA_MONETARIA, ConstantesNomina.MODO_REDONDEO);
    }

    @Override
    public boolean cumpleRequisitosFondo() {
        return getAntiguedadAnios() > ConstantesNomina.UMBRAL_ANIOS_FONDO_AHORRO;
    }

    @Override
    public boolean isAceptaFondoAhorro() {
        return aceptaFondoAhorro;
    }

    @Override
    public boolean tieneDerechoAlimentacion() {
        return false;
    }

    @Override
    public BigDecimal getSalarioBase() {
        // En empleado por horas, la base contractual de referencia es el pago por jornada base (40 horas)
        return ConstantesNomina.HORAS_BASE_SEMANALES.multiply(tarifaPorHora)
                .setScale(ConstantesNomina.ESCALA_MONETARIA, ConstantesNomina.MODO_REDONDEO);
    }
}
