package com.cipa.nomina.validation;

import com.cipa.nomina.exception.ValidacionException;
import com.cipa.nomina.model.Empleado;
import com.cipa.nomina.model.EmpleadoTemporal;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Validador centralizado de datos de entrada y coherencia del sistema de nómina.
 * Aplica el principio de Responsabilidad Única (S) evitando validaciones dispersas.
 */
public class ValidadorNomina {

    public void validarTextoNoVacio(String valor, String nombreCampo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new ValidacionException("El campo '" + nombreCampo + "' es obligatorio y no puede estar vacío.");
        }
    }

    public void validarNumeroPositivo(BigDecimal valor, String nombreCampo) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidacionException("El valor del campo '" + nombreCampo + "' debe ser un número positivo mayor a cero.");
        }
    }

    public void validarNumeroNoNegativo(BigDecimal valor, String nombreCampo) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidacionException("El valor del campo '" + nombreCampo + "' no puede ser negativo.");
        }
    }

    public void validarAntiguedad(int antiguedadAnios) {
        if (antiguedadAnios < 0) {
            throw new ValidacionException("La antigüedad no puede ser negativa: " + antiguedadAnios);
        }
    }

    public void validarPorcentaje(BigDecimal porcentaje, String nombreCampo) {
        if (porcentaje == null || porcentaje.compareTo(BigDecimal.ZERO) < 0 || porcentaje.compareTo(BigDecimal.ONE) > 0) {
            throw new ValidacionException("El porcentaje para '" + nombreCampo + "' debe ubicarse en el rango de 0.00 a 1.00 (ejemplo: 0.05 para 5%).");
        }
    }

    public void validarContratoTemporal(EmpleadoTemporal temporal, LocalDate fechaCorte) {
        if (temporal == null) {
            throw new ValidacionException("La información del empleado temporal no puede ser nula.");
        }
        if (fechaCorte != null && !temporal.isContratoVigente(fechaCorte)) {
            throw new ValidacionException(String.format(
                    "El contrato del empleado temporal [%s - %s] no está vigente para la fecha de liquidación %s (Rango contrato: %s a %s).",
                    temporal.getId(), temporal.getNombre(), fechaCorte,
                    temporal.getFechaInicioContrato(), temporal.getFechaFinContrato()));
        }
    }

    public void validarEmpleado(Empleado empleado) {
        if (empleado == null) {
            throw new ValidacionException("El empleado a procesar no puede ser nulo.");
        }
    }
}
