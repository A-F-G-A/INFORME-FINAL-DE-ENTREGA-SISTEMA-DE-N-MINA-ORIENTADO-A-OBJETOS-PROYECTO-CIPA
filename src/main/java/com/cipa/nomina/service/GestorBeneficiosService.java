package com.cipa.nomina.service;

import com.cipa.nomina.exception.ValidacionException;
import com.cipa.nomina.model.ConstantesNomina;
import com.cipa.nomina.model.Empleado;
import java.math.BigDecimal;

/**
 * Implementación del gestor de beneficios corporativos y costos de nómina patronales.
 */
public class GestorBeneficiosService implements GestorBeneficios {

    private BigDecimal porcentajeArlPatronal;

    public GestorBeneficiosService() {
        this.porcentajeArlPatronal = ConstantesNomina.PORCENTAJE_ARL_EMPRESA_DEFAULT;
    }

    public GestorBeneficiosService(BigDecimal porcentajeArlPatronal) {
        setPorcentajeArlPatronal(porcentajeArlPatronal);
    }

    @Override
    public BigDecimal calcularBeneficioAlimentacion(Empleado empleado) {
        if (empleado != null && empleado.tieneDerechoAlimentacion()) {
            return ConstantesNomina.BENEFICIO_ALIMENTACION_MENSUAL
                    .setScale(ConstantesNomina.ESCALA_MONETARIA, ConstantesNomina.MODO_REDONDEO);
        }
        return BigDecimal.ZERO.setScale(ConstantesNomina.ESCALA_MONETARIA, ConstantesNomina.MODO_REDONDEO);
    }

    @Override
    public BigDecimal calcularCostoArlPatronal(BigDecimal salarioBase) {
        if (salarioBase == null || salarioBase.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO.setScale(ConstantesNomina.ESCALA_MONETARIA, ConstantesNomina.MODO_REDONDEO);
        }
        return salarioBase.multiply(porcentajeArlPatronal)
                .setScale(ConstantesNomina.ESCALA_MONETARIA, ConstantesNomina.MODO_REDONDEO);
    }

    @Override
    public BigDecimal getPorcentajeArlPatronal() {
        return porcentajeArlPatronal;
    }

    @Override
    public void setPorcentajeArlPatronal(BigDecimal porcentajeArl) {
        if (porcentajeArl == null || porcentajeArl.compareTo(BigDecimal.ZERO) < 0 || porcentajeArl.compareTo(BigDecimal.ONE) > 0) {
            throw new ValidacionException("La tasa patronal de ARL debe estar entre 0.00 y 1.00 (ejemplo: 0.00522).");
        }
        this.porcentajeArlPatronal = porcentajeArl;
    }
}
