package com.cipa.nomina.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Constantes de negocio y configuración del sistema de nómina.
 * Evita valores mágicos en el código y centraliza las tasas y umbrales.
 */
public final class ConstantesNomina {

    private ConstantesNomina() {
        // Constructor privado para clase de utilidad/constantes
    }

    /** Escala de redondeo decimal estándar para montos monetarios (2 decimales) */
    public static final int ESCALA_MONETARIA = 2;

    /** Modo de redondeo estándar: HALF_UP (redondeo simétrico al más cercano) */
    public static final RoundingMode MODO_REDONDEO = RoundingMode.HALF_UP;

    /** Porcentaje de deducción de Seguro Social y Pensión según enunciado: 4% (0.04) */
    public static final BigDecimal PORCENTAJE_SEGURO_SOCIAL_PENSION = new BigDecimal("0.04");

    /** Beneficio mensual de alimentación cubierto por la empresa: $1.000.000 */
    public static final BigDecimal BENEFICIO_ALIMENTACION_MENSUAL = new BigDecimal("1000000.00");

    /** Umbral de años para el bono de antigüedad del empleado asalariado: > 5 años */
    public static final int UMBRAL_ANIOS_BONO_ASALARIADO = 5;

    /** Porcentaje de bono de antigüedad para asalariado: 10% (0.10) */
    public static final BigDecimal PORCENTAJE_BONO_ANTIGUEDAD = new BigDecimal("0.10");

    /** Límite de horas normales para empleado por horas en el período de cálculo: 40 horas */
    public static final BigDecimal HORAS_BASE_SEMANALES = new BigDecimal("40.00");

    /** Factor multiplicador para horas extras (1.5x) */
    public static final BigDecimal FACTOR_HORA_EXTRA = new BigDecimal("1.50");

    /** Años mínimos de antigüedad para tener derecho al fondo de ahorro: > 1 año */
    public static final int UMBRAL_ANIOS_FONDO_AHORRO = 1;

    /** Porcentaje de aporte al fondo de ahorro para empleado por horas: 2% (0.02) */
    public static final BigDecimal PORCENTAJE_FONDO_AHORRO = new BigDecimal("0.02");

    /** Umbral de ventas para el bono extraordinario de comisión: > $20.000.000 */
    public static final BigDecimal UMBRAL_VENTAS_COMISION = new BigDecimal("20000000.00");

    /** Porcentaje de bono adicional sobre ventas cuando superan el umbral: 3% (0.03) */
    public static final BigDecimal PORCENTAJE_BONO_VENTAS_COMISION = new BigDecimal("0.03");

    /**
     * Tasa de ARL por defecto con fines ilustrativos (Riesgo I: 0.522% = 0.00522).
     * Según el enunciado, la ARL es asumida como costo empresarial y no se descuenta al trabajador.
     */
    public static final BigDecimal PORCENTAJE_ARL_EMPRESA_DEFAULT = new BigDecimal("0.00522");
}
