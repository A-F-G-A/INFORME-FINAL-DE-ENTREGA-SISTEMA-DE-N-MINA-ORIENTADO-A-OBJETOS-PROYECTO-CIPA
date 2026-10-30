package com.cipa.nomina.model;

import com.cipa.nomina.exception.SalarioInvalidoException;
import java.math.BigDecimal;

/**
 * Modelo inmutable que almacena el desglose detallado de la liquidación de nómina de un empleado.
 * Contiene con precisión matemática y transparencia auditada:
 * - Ingresos devengados (salario base, horas extras, comisiones, bonos)
 * - Salario bruto total
 * - Deducciones a cargo del empleado (seguridad social/pensión, fondo de ahorro)
 * - Salario neto final pagado al trabajador
 * - Beneficios pagados por la empresa (auxilio de alimentación)
 * - Costos empresariales asociados (ARL patronal y beneficios)
 */
public class DesgloseNomina {

    private final Empleado empleado;
    private final BigDecimal salarioBase;
    private final BigDecimal pagoHorasNormales;
    private final BigDecimal pagoHorasExtras;
    private final BigDecimal comisionVentas;
    private final BigDecimal bonoAdicional;
    private final BigDecimal salarioBruto;
    private final BigDecimal deduccionSeguroSocialPension;
    private final BigDecimal deduccionFondoAhorro;
    private final BigDecimal totalDeducciones;
    private final BigDecimal salarioNeto;
    private final BigDecimal beneficioAlimentacionEmpresa;
    private final BigDecimal costoArlEmpresa;
    private final BigDecimal costoTotalEmpresa;

    public DesgloseNomina(Empleado empleado,
                          BigDecimal salarioBase,
                          BigDecimal pagoHorasNormales,
                          BigDecimal pagoHorasExtras,
                          BigDecimal comisionVentas,
                          BigDecimal bonoAdicional,
                          BigDecimal salarioBruto,
                          BigDecimal deduccionSeguroSocialPension,
                          BigDecimal deduccionFondoAhorro,
                          BigDecimal totalDeducciones,
                          BigDecimal salarioNeto,
                          BigDecimal beneficioAlimentacionEmpresa,
                          BigDecimal costoArlEmpresa,
                          BigDecimal costoTotalEmpresa) {

        if (salarioNeto == null || salarioNeto.compareTo(BigDecimal.ZERO) < 0) {
            throw new SalarioInvalidoException(
                    "El salario neto calculado no puede ser negativo: $" + salarioNeto + 
                    " para el empleado " + (empleado != null ? empleado.getNombre() : "desconocido"));
        }

        this.empleado = empleado;
        this.salarioBase = salarioBase;
        this.pagoHorasNormales = pagoHorasNormales;
        this.pagoHorasExtras = pagoHorasExtras;
        this.comisionVentas = comisionVentas;
        this.bonoAdicional = bonoAdicional;
        this.salarioBruto = salarioBruto;
        this.deduccionSeguroSocialPension = deduccionSeguroSocialPension;
        this.deduccionFondoAhorro = deduccionFondoAhorro;
        this.totalDeducciones = totalDeducciones;
        this.salarioNeto = salarioNeto;
        this.beneficioAlimentacionEmpresa = beneficioAlimentacionEmpresa;
        this.costoArlEmpresa = costoArlEmpresa;
        this.costoTotalEmpresa = costoTotalEmpresa;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public BigDecimal getSalarioBase() {
        return salarioBase;
    }

    public BigDecimal getPagoHorasNormales() {
        return pagoHorasNormales;
    }

    public BigDecimal getPagoHorasExtras() {
        return pagoHorasExtras;
    }

    public BigDecimal getComisionVentas() {
        return comisionVentas;
    }

    public BigDecimal getBonoAdicional() {
        return bonoAdicional;
    }

    public BigDecimal getSalarioBruto() {
        return salarioBruto;
    }

    public BigDecimal getDeduccionSeguroSocialPension() {
        return deduccionSeguroSocialPension;
    }

    public BigDecimal getDeduccionFondoAhorro() {
        return deduccionFondoAhorro;
    }

    public BigDecimal getTotalDeducciones() {
        return totalDeducciones;
    }

    public BigDecimal getSalarioNeto() {
        return salarioNeto;
    }

    public BigDecimal getBeneficioAlimentacionEmpresa() {
        return beneficioAlimentacionEmpresa;
    }

    public BigDecimal getCostoArlEmpresa() {
        return costoArlEmpresa;
    }

    public BigDecimal getCostoTotalEmpresa() {
        return costoTotalEmpresa;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("================================================================================\n");
        sb.append(String.format(" COMPROBANTE DE PAGO DE NÓMINA | %s\n", empleado.getTipoEmpleado().getDescripcion().toUpperCase()));
        sb.append("================================================================================\n");
        sb.append(String.format(" ID Empleado:       %-25s | Nombre:     %s\n", empleado.getId(), empleado.getNombre()));
        sb.append(String.format(" Documento:         %-25s | Antigüedad: %d años\n", empleado.getDocumento(), empleado.getAntiguedadAnios()));
        sb.append("--------------------------------------------------------------------------------\n");
        sb.append(" 1. INGRESOS Y DEVENGADOS (DEL TRABAJADOR)\n");
        sb.append(String.format("   - Salario Base Contractual:              $ %,15.2f\n", salarioBase));
        if (pagoHorasNormales.compareTo(BigDecimal.ZERO) > 0 || pagoHorasExtras.compareTo(BigDecimal.ZERO) > 0) {
            sb.append(String.format("   - Pago Horas Ordinarias:                 $ %,15.2f\n", pagoHorasNormales));
            sb.append(String.format("   - Pago Horas Extras (recargo 1.5x):      $ %,15.2f\n", pagoHorasExtras));
        }
        if (comisionVentas.compareTo(BigDecimal.ZERO) > 0) {
            sb.append(String.format("   - Comisión por Ventas:                   $ %,15.2f\n", comisionVentas));
        }
        if (bonoAdicional.compareTo(BigDecimal.ZERO) > 0) {
            sb.append(String.format("   - Bonificación (Antigüedad / Ventas):    $ %,15.2f\n", bonoAdicional));
        }
        sb.append(String.format("   TOTAL SALARIO BRUTO DEVENGADO:           $ %,15.2f\n", salarioBruto));
        sb.append("--------------------------------------------------------------------------------\n");
        sb.append(" 2. DEDUCCIONES (A CARGO DEL TRABAJADOR)\n");
        sb.append(String.format("   - Seguro Social y Pensión (4%%):          $ %,15.2f\n", deduccionSeguroSocialPension));
        if (deduccionFondoAhorro.compareTo(BigDecimal.ZERO) > 0) {
            sb.append(String.format("   - Fondo de Ahorro Voluntario (2%%):       $ %,15.2f\n", deduccionFondoAhorro));
        }
        sb.append(String.format("   TOTAL DEDUCCIONES:                       $ %,15.2f\n", totalDeducciones));
        sb.append("--------------------------------------------------------------------------------\n");
        sb.append(String.format(" >>> SALARIO NETO A PAGAR:                  $ %,15.2f <<<\n", salarioNeto));
        sb.append("================================================================================\n");
        sb.append(" 3. BENEFICIOS Y COSTOS FINANCIADOS POR LA EMPRESA (NO DESCONTADOS AL EMPLEADO)\n");
        sb.append(String.format("   - Beneficio de Alimentación mensual:     $ %,15.2f\n", beneficioAlimentacionEmpresa));
        sb.append(String.format("   - Costo ARL (Riesgo configurado):        $ %,15.2f\n", costoArlEmpresa));
        sb.append(String.format("   COSTO TOTAL ASUMIDO POR LA EMPRESA:      $ %,15.2f\n", costoTotalEmpresa));
        sb.append("================================================================================\n");
        return sb.toString();
    }
}
