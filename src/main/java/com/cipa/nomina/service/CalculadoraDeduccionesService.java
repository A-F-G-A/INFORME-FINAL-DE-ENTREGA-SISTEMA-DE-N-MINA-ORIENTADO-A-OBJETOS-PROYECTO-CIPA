package com.cipa.nomina.service;

import com.cipa.nomina.model.ConstantesNomina;
import com.cipa.nomina.model.ElegibleFondoAhorro;
import com.cipa.nomina.model.Empleado;
import java.math.BigDecimal;

/**
 * Servicio encargado de calcular las deducciones de nómina que corresponden al trabajador.
 * Principio de Responsabilidad Única (S) e Inversión de Dependencias (D).
 */
public class CalculadoraDeduccionesService implements CalculadoraDeducciones {

    @Override
    public BigDecimal calcularSeguroSocialYPension(BigDecimal salarioBruto) {
        if (salarioBruto == null || salarioBruto.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO.setScale(ConstantesNomina.ESCALA_MONETARIA, ConstantesNomina.MODO_REDONDEO);
        }
        return salarioBruto.multiply(ConstantesNomina.PORCENTAJE_SEGURO_SOCIAL_PENSION)
                .setScale(ConstantesNomina.ESCALA_MONETARIA, ConstantesNomina.MODO_REDONDEO);
    }

    @Override
    public BigDecimal calcularFondoAhorro(Empleado empleado, BigDecimal salarioBruto) {
        if (empleado instanceof ElegibleFondoAhorro elegible) {
            if (elegible.cumpleRequisitosFondo() && elegible.isAceptaFondoAhorro()) {
                if (salarioBruto != null && salarioBruto.compareTo(BigDecimal.ZERO) > 0) {
                    return salarioBruto.multiply(ConstantesNomina.PORCENTAJE_FONDO_AHORRO)
                            .setScale(ConstantesNomina.ESCALA_MONETARIA, ConstantesNomina.MODO_REDONDEO);
                }
            }
        }
        return BigDecimal.ZERO.setScale(ConstantesNomina.ESCALA_MONETARIA, ConstantesNomina.MODO_REDONDEO);
    }
}
