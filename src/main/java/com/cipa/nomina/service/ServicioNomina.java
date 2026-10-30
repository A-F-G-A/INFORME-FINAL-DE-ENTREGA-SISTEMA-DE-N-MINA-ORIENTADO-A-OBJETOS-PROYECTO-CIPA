package com.cipa.nomina.service;

import com.cipa.nomina.exception.SalarioInvalidoException;
import com.cipa.nomina.model.Bonificable;
import com.cipa.nomina.model.ConstantesNomina;
import com.cipa.nomina.model.DesgloseNomina;
import com.cipa.nomina.model.Empleado;
import com.cipa.nomina.model.EmpleadoPorComision;
import com.cipa.nomina.model.EmpleadoPorHoras;
import com.cipa.nomina.model.EmpleadoTemporal;
import com.cipa.nomina.validation.ValidadorNomina;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Servicio principal de liquidación y gestión de nómina.
 * Diseñado bajo principios SOLID:
 * - Depende de abstracciones (CalculadoraDeducciones, GestorBeneficios).
 * - Utiliza polimorfismo para procesar cualquier colección de empleados sin romper la abstracción.
 */
public class ServicioNomina {

    private final List<Empleado> empleadosRegistrados;
    private final CalculadoraDeducciones calculadoraDeducciones;
    private final GestorBeneficios gestorBeneficios;
    private final ValidadorNomina validadorNomina;

    public ServicioNomina(CalculadoraDeducciones calculadoraDeducciones,
                          GestorBeneficios gestorBeneficios,
                          ValidadorNomina validadorNomina) {
        this.calculadoraDeducciones = calculadoraDeducciones != null ? calculadoraDeducciones : new CalculadoraDeduccionesService();
        this.gestorBeneficios = gestorBeneficios != null ? gestorBeneficios : new GestorBeneficiosService();
        this.validadorNomina = validadorNomina != null ? validadorNomina : new ValidadorNomina();
        this.empleadosRegistrados = new ArrayList<>();
    }

    public ServicioNomina() {
        this(new CalculadoraDeduccionesService(), new GestorBeneficiosService(), new ValidadorNomina());
    }

    /**
     * Registra un empleado en el sistema tras validar sus reglas.
     */
    public void registrarEmpleado(Empleado empleado) {
        validadorNomina.validarEmpleado(empleado);
        if (buscarEmpleadoPorId(empleado.getId()) != null) {
            throw new IllegalArgumentException("Ya existe un empleado registrado con el ID: " + empleado.getId());
        }
        empleadosRegistrados.add(empleado);
    }

    /**
     * Busca un empleado por su identificador.
     */
    public Empleado buscarEmpleadoPorId(String id) {
        if (id == null) return null;
        for (Empleado emp : empleadosRegistrados) {
            if (emp.getId().equalsIgnoreCase(id.trim())) {
                return emp;
            }
        }
        return null;
    }

    /**
     * Retorna una lista inmutable de los empleados registrados.
     */
    public List<Empleado> getEmpleadosRegistrados() {
        return Collections.unmodifiableList(empleadosRegistrados);
    }

    /**
     * Liquida la nómina para un empleado en una fecha de corte específica.
     */
    public DesgloseNomina liquidarEmpleado(Empleado empleado, LocalDate fechaCorte) {
        validadorNomina.validarEmpleado(empleado);

        // Validación contractual si es temporal
        if (empleado instanceof EmpleadoTemporal temporal) {
            validadorNomina.validarContratoTemporal(temporal, fechaCorte);
        }

        // 1. Ingresos y Devengados Polimórficos
        BigDecimal devengadoBase = empleado.calcularDevengadoBase();
        BigDecimal bono = BigDecimal.ZERO.setScale(ConstantesNomina.ESCALA_MONETARIA, ConstantesNomina.MODO_REDONDEO);
        if (empleado instanceof Bonificable bonificable) {
            bono = bonificable.calcularBono();
        }

        BigDecimal salarioBruto = devengadoBase.add(bono)
                .setScale(ConstantesNomina.ESCALA_MONETARIA, ConstantesNomina.MODO_REDONDEO);

        // Subtotales detallados para transparencia del recibo
        BigDecimal horasNormales = BigDecimal.ZERO.setScale(ConstantesNomina.ESCALA_MONETARIA, ConstantesNomina.MODO_REDONDEO);
        BigDecimal horasExtras = BigDecimal.ZERO.setScale(ConstantesNomina.ESCALA_MONETARIA, ConstantesNomina.MODO_REDONDEO);
        BigDecimal comisionVentas = BigDecimal.ZERO.setScale(ConstantesNomina.ESCALA_MONETARIA, ConstantesNomina.MODO_REDONDEO);

        if (empleado instanceof EmpleadoPorHoras porHoras) {
            horasNormales = porHoras.getPagoHorasNormales();
            horasExtras = porHoras.getPagoHorasExtras();
        } else if (empleado instanceof EmpleadoPorComision porComision) {
            comisionVentas = porComision.getComisionPorVentas();
        }

        // 2. Deducciones a cargo del trabajador
        BigDecimal deduccionSaludPension = calculadoraDeducciones.calcularSeguroSocialYPension(salarioBruto);
        BigDecimal deduccionFondo = calculadoraDeducciones.calcularFondoAhorro(empleado, salarioBruto);
        BigDecimal totalDeducciones = deduccionSaludPension.add(deduccionFondo)
                .setScale(ConstantesNomina.ESCALA_MONETARIA, ConstantesNomina.MODO_REDONDEO);

        // 3. Salario Neto
        BigDecimal salarioNeto = salarioBruto.subtract(totalDeducciones)
                .setScale(ConstantesNomina.ESCALA_MONETARIA, ConstantesNomina.MODO_REDONDEO);

        if (salarioNeto.compareTo(BigDecimal.ZERO) < 0) {
            throw new SalarioInvalidoException("Cálculo anómalo: El salario neto es negativo ($" + salarioNeto + 
                    ") para el empleado " + empleado.getNombre() + ". Se rechaza la operación.");
        }

        // 4. Beneficios y costos asumidos por la empresa
        BigDecimal beneficioAlimentacion = gestorBeneficios.calcularBeneficioAlimentacion(empleado);
        BigDecimal costoArl = gestorBeneficios.calcularCostoArlPatronal(empleado.getSalarioBase());
        BigDecimal costoTotalEmpresa = salarioBruto.add(beneficioAlimentacion).add(costoArl)
                .setScale(ConstantesNomina.ESCALA_MONETARIA, ConstantesNomina.MODO_REDONDEO);

        return new DesgloseNomina(
                empleado,
                empleado.getSalarioBase(),
                horasNormales,
                horasExtras,
                comisionVentas,
                bono,
                salarioBruto,
                deduccionSaludPension,
                deduccionFondo,
                totalDeducciones,
                salarioNeto,
                beneficioAlimentacion,
                costoArl,
                costoTotalEmpresa
        );
    }

    /**
     * Sobrecarga de liquidación utilizando la fecha actual del sistema.
     */
    public DesgloseNomina liquidarEmpleado(Empleado empleado) {
        return liquidarEmpleado(empleado, LocalDate.now());
    }

    /**
     * Liquida la nómina polimórficamente para todos los empleados registrados.
     */
    public List<DesgloseNomina> liquidarTodos(LocalDate fechaCorte) {
        List<DesgloseNomina> desgloses = new ArrayList<>();
        for (Empleado emp : empleadosRegistrados) {
            desgloses.add(liquidarEmpleado(emp, fechaCorte));
        }
        return desgloses;
    }

    public List<DesgloseNomina> liquidarTodos() {
        return liquidarTodos(LocalDate.now());
    }

    public GestorBeneficios getGestorBeneficios() {
        return gestorBeneficios;
    }

    public CalculadoraDeducciones getCalculadoraDeducciones() {
        return calculadoraDeducciones;
    }
}
