package com.cipa.nomina;

import com.cipa.nomina.exception.SalarioInvalidoException;
import com.cipa.nomina.exception.ValidacionException;
import com.cipa.nomina.model.ConstantesNomina;
import com.cipa.nomina.model.DesgloseNomina;
import com.cipa.nomina.model.Empleado;
import com.cipa.nomina.model.EmpleadoAsalariado;
import com.cipa.nomina.model.EmpleadoPorComision;
import com.cipa.nomina.model.EmpleadoPorHoras;
import com.cipa.nomina.model.EmpleadoTemporal;
import com.cipa.nomina.service.CalculadoraDeduccionesService;
import com.cipa.nomina.service.GestorBeneficiosService;
import com.cipa.nomina.service.ServicioNomina;
import com.cipa.nomina.validation.ValidadorNomina;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Suite completa de pruebas unitarias con JUnit 5 para el Sistema de Nómina CIPA.
 * Verifica estrictamente los 20 escenarios mínimos de negocio requeridos.
 */
class SistemaNominaTest {

    private ServicioNomina servicioNomina;

    @BeforeEach
    void setUp() {
        servicioNomina = new ServicioNomina(
                new CalculadoraDeduccionesService(),
                new GestorBeneficiosService(),
                new ValidadorNomina()
        );
    }

    // 1. Asalariado con más de cinco años: bono del 10 %
    @Test
    @DisplayName("1. Asalariado con más de 5 años: recibe bono del 10% y auxilio de alimentación")
    void testAsalariadoConMasDeCincoAniosRecibeBono() {
        // Salario $3.000.000, 6 años de antigüedad -> Bono: 10% = $300.000
        // Salario Bruto = $3.300.000
        // Deducción Salud/Pensión (4%) = $132.000
        // Salario Neto = $3.168.000
        // Beneficio Alimentación = $1.000.000
        EmpleadoAsalariado emp = new EmpleadoAsalariado("E1", "Carlos", "123", 6, new BigDecimal("3000000.00"));
        DesgloseNomina desglose = servicioNomina.liquidarEmpleado(emp);

        assertEquals(new BigDecimal("300000.00"), desglose.getBonoAdicional());
        assertEquals(new BigDecimal("3300000.00"), desglose.getSalarioBruto());
        assertEquals(new BigDecimal("132000.00"), desglose.getDeduccionSeguroSocialPension());
        assertEquals(new BigDecimal("3168000.00"), desglose.getSalarioNeto());
        assertEquals(new BigDecimal("1000000.00"), desglose.getBeneficioAlimentacionEmpresa());
    }

    // 2. Asalariado con exactamente cinco años: sin bono
    @Test
    @DisplayName("2. Asalariado con exactamente 5 años: no recibe bono")
    void testAsalariadoConExactamenteCincoAniosSinBono() {
        // Salario $3.000.000, 5 años exactos -> Bono: $0.00
        // Salario Bruto = $3.000.000
        // Deducción Salud/Pensión (4%) = $120.000
        // Salario Neto = $2.880.000
        EmpleadoAsalariado emp = new EmpleadoAsalariado("E2", "Ana", "124", 5, new BigDecimal("3000000.00"));
        DesgloseNomina desglose = servicioNomina.liquidarEmpleado(emp);

        assertEquals(new BigDecimal("0.00"), desglose.getBonoAdicional());
        assertEquals(new BigDecimal("3000000.00"), desglose.getSalarioBruto());
        assertEquals(new BigDecimal("120000.00"), desglose.getDeduccionSeguroSocialPension());
        assertEquals(new BigDecimal("2880000.00"), desglose.getSalarioNeto());
    }

    // 3. Asalariado con menos de cinco años: sin bono
    @Test
    @DisplayName("3. Asalariado con menos de 5 años: no recibe bono")
    void testAsalariadoConMenosDeCincoAniosSinBono() {
        EmpleadoAsalariado emp = new EmpleadoAsalariado("E3", "Pedro", "125", 4, new BigDecimal("2500000.00"));
        DesgloseNomina desglose = servicioNomina.liquidarEmpleado(emp);

        assertEquals(new BigDecimal("0.00"), desglose.getBonoAdicional());
        assertEquals(new BigDecimal("2500000.00"), desglose.getSalarioBruto());
        assertEquals(new BigDecimal("100000.00"), desglose.getDeduccionSeguroSocialPension());
        assertEquals(new BigDecimal("2400000.00"), desglose.getSalarioNeto());
    }

    // 4. Empleado por horas con exactamente 40 horas
    @Test
    @DisplayName("4. Empleado por horas con exactamente 40 horas: pago normal, cero extras")
    void testEmpleadoPorHorasConExactamente40Horas() {
        // 40 horas a $20.000 = $800.000. Extras = $0.00. No fondo.
        // Salud/Pensión (4%) = $32.000 -> Neto = $768.000
        EmpleadoPorHoras emp = new EmpleadoPorHoras("E4", "Luis", "126", 1, new BigDecimal("20000.00"), new BigDecimal("40.00"), false);
        DesgloseNomina desglose = servicioNomina.liquidarEmpleado(emp);

        assertEquals(new BigDecimal("800000.00"), desglose.getPagoHorasNormales());
        assertEquals(new BigDecimal("0.00"), desglose.getPagoHorasExtras());
        assertEquals(new BigDecimal("800000.00"), desglose.getSalarioBruto());
        assertEquals(new BigDecimal("768000.00"), desglose.getSalarioNeto());
    }

    // 5. Empleado por horas con 45 horas (40 normales + 5 extras a 1.5x)
    @Test
    @DisplayName("5. Empleado por horas con 45 horas: 40 normales y 5 extras con recargo 1.5x")
    void testEmpleadoPorHorasCon45Horas() {
        // Tarifa $20.000/h.
        // Horas normales: 40 * $20.000 = $800.000
        // Horas extras: 5 * ($20.000 * 1.5 = $30.000) = $150.000
        // Salario bruto = $950.000
        // Salud/Pensión (4%) = $38.000 -> Neto (sin fondo) = $912.000
        EmpleadoPorHoras emp = new EmpleadoPorHoras("E5", "Marta", "127", 1, new BigDecimal("20000.00"), new BigDecimal("45.00"), false);
        DesgloseNomina desglose = servicioNomina.liquidarEmpleado(emp);

        assertEquals(new BigDecimal("800000.00"), desglose.getPagoHorasNormales());
        assertEquals(new BigDecimal("150000.00"), desglose.getPagoHorasExtras());
        assertEquals(new BigDecimal("950000.00"), desglose.getSalarioBruto());
        assertEquals(new BigDecimal("912000.00"), desglose.getSalarioNeto());
    }

    // 6. Empleado por horas con una cantidad inferior a 40 horas
    @Test
    @DisplayName("6. Empleado por horas con menos de 40 horas: pago exacto proporcional, cero extras")
    void testEmpleadoPorHorasConMenosDe40Horas() {
        // 30 horas a $25.000 = $750.000. Extras = $0.00
        EmpleadoPorHoras emp = new EmpleadoPorHoras("E6", "Sofia", "128", 2, new BigDecimal("25000.00"), new BigDecimal("30.00"), false);
        DesgloseNomina desglose = servicioNomina.liquidarEmpleado(emp);

        assertEquals(new BigDecimal("750000.00"), desglose.getPagoHorasNormales());
        assertEquals(new BigDecimal("0.00"), desglose.getPagoHorasExtras());
        assertEquals(new BigDecimal("750000.00"), desglose.getSalarioBruto());
    }

    // 7. Empleado por comisión con ventas superiores a $20.000.000
    @Test
    @DisplayName("7. Empleado por comisión con ventas > $20.000.000: bono adicional del 3% sobre ventas")
    void testEmpleadoPorComisionConVentasSuperioresA20M() {
        // Base $1.500.000, Comisión 5% sobre ventas $25.000.000 = $1.250.000
        // Bono adicional (ventas > 20M): 3% sobre $25.000.000 = $750.000
        // Salario bruto = 1.500.000 + 1.250.000 + 750.000 = $3.500.000
        // Deducción Salud/Pensión (4%) = $140.000 -> Neto = $3.360.000
        // Beneficio alimentación = $1.000.000
        EmpleadoPorComision emp = new EmpleadoPorComision(
                "E7", "Andres", "129", 2,
                new BigDecimal("1500000.00"), new BigDecimal("0.05"), new BigDecimal("25000000.00"));
        DesgloseNomina desglose = servicioNomina.liquidarEmpleado(emp);

        assertEquals(new BigDecimal("1250000.00"), desglose.getComisionVentas());
        assertEquals(new BigDecimal("750000.00"), desglose.getBonoAdicional());
        assertEquals(new BigDecimal("3500000.00"), desglose.getSalarioBruto());
        assertEquals(new BigDecimal("3360000.00"), desglose.getSalarioNeto());
        assertEquals(new BigDecimal("1000000.00"), desglose.getBeneficioAlimentacionEmpresa());
    }

    // 8. Empleado por comisión con ventas exactamente iguales a $20.000.000
    @Test
    @DisplayName("8. Empleado por comisión con ventas exactamente de $20.000.000: sin bono adicional")
    void testEmpleadoPorComisionConVentasExactamente20M() {
        // Base $1.500.000, Comisión 5% sobre $20.000.000 = $1.000.000
        // Bono adicional = $0.00 (requiere estrictamente > 20M)
        // Salario bruto = $2.500.000
        EmpleadoPorComision emp = new EmpleadoPorComision(
                "E8", "Camila", "130", 3,
                new BigDecimal("1500000.00"), new BigDecimal("0.05"), new BigDecimal("20000000.00"));
        DesgloseNomina desglose = servicioNomina.liquidarEmpleado(emp);

        assertEquals(new BigDecimal("1000000.00"), desglose.getComisionVentas());
        assertEquals(new BigDecimal("0.00"), desglose.getBonoAdicional());
        assertEquals(new BigDecimal("2500000.00"), desglose.getSalarioBruto());
    }

    // 9. Empleado por comisión con ventas inferiores a $20.000.000
    @Test
    @DisplayName("9. Empleado por comisión con ventas inferiores a $20.000.000: sin bono adicional")
    void testEmpleadoPorComisionConVentasInferioresA20M() {
        // Base $1.500.000, Comisión 5% sobre $15.000.000 = $750.000 -> Bono = $0.00 -> Bruto = $2.250.000
        EmpleadoPorComision emp = new EmpleadoPorComision(
                "E9", "Julian", "131", 1,
                new BigDecimal("1500000.00"), new BigDecimal("0.05"), new BigDecimal("15000000.00"));
        DesgloseNomina desglose = servicioNomina.liquidarEmpleado(emp);

        assertEquals(new BigDecimal("750000.00"), desglose.getComisionVentas());
        assertEquals(new BigDecimal("0.00"), desglose.getBonoAdicional());
        assertEquals(new BigDecimal("2250000.00"), desglose.getSalarioBruto());
    }

    // 10. Empleado temporal sin bonos ni beneficios
    @Test
    @DisplayName("10. Empleado temporal: salario fijo sin bonos ni auxilio de alimentación")
    void testEmpleadoTemporalSinBonosNiBeneficios() {
        EmpleadoTemporal emp = new EmpleadoTemporal(
                "E10", "Valeria", "132", 0, new BigDecimal("2000000.00"),
                LocalDate.now().minusMonths(1), LocalDate.now().plusMonths(5));
        DesgloseNomina desglose = servicioNomina.liquidarEmpleado(emp);

        assertEquals(new BigDecimal("2000000.00"), desglose.getSalarioBruto());
        assertEquals(new BigDecimal("0.00"), desglose.getBonoAdicional());
        assertEquals(new BigDecimal("0.00"), desglose.getBeneficioAlimentacionEmpresa());
        assertEquals(new BigDecimal("80000.00"), desglose.getDeduccionSeguroSocialPension());
        assertEquals(new BigDecimal("1920000.00"), desglose.getSalarioNeto());
    }

    // 11. Empleado por horas que cumple antigüedad (>1 año) y acepta el fondo de ahorro
    @Test
    @DisplayName("11. Empleado por horas > 1 año y acepta fondo: deducción del 2% aplicada")
    void testEmpleadoPorHorasCumpleAntiguedadYAceptaFondoAhorro() {
        // 40 horas a $25.000 = $1.000.000 bruto. Antigüedad: 2 años (>1). Acepta: true.
        // Seguro social/Pensión (4%): $40.000
        // Fondo de ahorro (2%): $20.000
        // Total deducciones: $60.000
        // Salario neto: $940.000
        EmpleadoPorHoras emp = new EmpleadoPorHoras(
                "E11", "Diego", "133", 2, new BigDecimal("25000.00"), new BigDecimal("40.00"), true);
        DesgloseNomina desglose = servicioNomina.liquidarEmpleado(emp);

        assertEquals(new BigDecimal("1000000.00"), desglose.getSalarioBruto());
        assertEquals(new BigDecimal("40000.00"), desglose.getDeduccionSeguroSocialPension());
        assertEquals(new BigDecimal("20000.00"), desglose.getDeduccionFondoAhorro());
        assertEquals(new BigDecimal("60000.00"), desglose.getTotalDeducciones());
        assertEquals(new BigDecimal("940000.00"), desglose.getSalarioNeto());
    }

    // 12. Empleado por horas que rechaza el fondo de ahorro
    @Test
    @DisplayName("12. Empleado por horas > 1 año que rechaza fondo: sin deducción de ahorro")
    void testEmpleadoPorHorasRechazaFondoAhorro() {
        // 40 horas a $25.000 = $1.000.000 bruto. Antigüedad: 3 años. Acepta: false.
        EmpleadoPorHoras emp = new EmpleadoPorHoras(
                "E12", "Esteban", "134", 3, new BigDecimal("25000.00"), new BigDecimal("40.00"), false);
        DesgloseNomina desglose = servicioNomina.liquidarEmpleado(emp);

        assertEquals(new BigDecimal("0.00"), desglose.getDeduccionFondoAhorro());
        assertEquals(new BigDecimal("40000.00"), desglose.getTotalDeducciones());
        assertEquals(new BigDecimal("960000.00"), desglose.getSalarioNeto());
    }

    // 13. Deducción de seguro social y pensión del 4 %
    @Test
    @DisplayName("13. Deducción de seguro social y pensión del 4% sobre salario bruto exacto")
    void testDeduccionSeguroSocialYPensionDelCuatroPorCiento() {
        EmpleadoAsalariado emp = new EmpleadoAsalariado("E13", "Paula", "135", 2, new BigDecimal("4500000.00"));
        DesgloseNomina desglose = servicioNomina.liquidarEmpleado(emp);

        // 4.500.000 * 0.04 = 180.000.00
        assertEquals(new BigDecimal("180000.00"), desglose.getDeduccionSeguroSocialPension());
    }

    // 14. Rechazo de horas negativas
    @Test
    @DisplayName("14. Rechazo de horas negativas: lanza ValidacionException")
    void testRechazoDeHorasNegativas() {
        assertThrows(ValidacionException.class, () -> new EmpleadoPorHoras(
                "E14", "Felipe", "136", 1, new BigDecimal("20000.00"), new BigDecimal("-5.00"), true));
    }

    // 15. Rechazo de ventas negativas
    @Test
    @DisplayName("15. Rechazo de ventas negativas: lanza ValidacionException")
    void testRechazoDeVentasNegativas() {
        assertThrows(ValidacionException.class, () -> new EmpleadoPorComision(
                "E15", "Monica", "137", 2, new BigDecimal("1500000.00"), new BigDecimal("0.05"), new BigDecimal("-1000.00")));
    }

    // 16. Rechazo de salarios, tarifas o porcentajes inválidos
    @Test
    @DisplayName("16. Rechazo de salarios, tarifas y porcentajes inválidos")
    void testRechazoSalariosTarifasOPorcentajesInvalidos() {
        // Salario asalariado <= 0
        assertThrows(ValidacionException.class, () -> new EmpleadoAsalariado(
                "E16A", "A", "1", 1, BigDecimal.ZERO));

        // Tarifa por horas <= 0
        assertThrows(ValidacionException.class, () -> new EmpleadoPorHoras(
                "E16B", "B", "2", 1, new BigDecimal("-100.00"), new BigDecimal("40.00"), false));

        // Porcentaje comisión > 1.00
        assertThrows(ValidacionException.class, () -> new EmpleadoPorComision(
                "E16C", "C", "3", 1, new BigDecimal("1000.00"), new BigDecimal("1.50"), new BigDecimal("10000.00")));

        // Antigüedad negativa
        assertThrows(ValidacionException.class, () -> new EmpleadoAsalariado(
                "E16D", "D", "4", -2, new BigDecimal("1000.00")));
    }

    // 17. Rechazo de resultados de nómina con salario neto negativo
    @Test
    @DisplayName("17. Rechazo de salario neto negativo con SalarioInvalidoException")
    void testRechazoSalarioNetoNegativo() {
        // Creamos un servicio con una calculadora que produzca una deducción superior al 100% para verificar la salvaguarda
        ServicioNomina servicioAnomalo = new ServicioNomina(
                new CalculadoraDeduccionesService() {
                    @Override
                    public BigDecimal calcularSeguroSocialYPension(BigDecimal salarioBruto) {
                        return salarioBruto.multiply(new BigDecimal("1.50")); // Deduce 150%
                    }
                },
                new GestorBeneficiosService(),
                new ValidadorNomina()
        );

        EmpleadoAsalariado emp = new EmpleadoAsalariado("E17", "ErrorNeto", "999", 1, new BigDecimal("1000000.00"));
        assertThrows(SalarioInvalidoException.class, () -> servicioAnomalo.liquidarEmpleado(emp));
    }

    // 18. Validación del contrato temporal (fechas invertidas o contrato vencido)
    @Test
    @DisplayName("18. Validación de contrato temporal: rechaza fechas incoherentes o contratos vencidos")
    void testValidacionContratoTemporal() {
        LocalDate hoy = LocalDate.now();

        // Fechas invertidas en la creación
        assertThrows(ValidacionException.class, () -> new EmpleadoTemporal(
                "E18A", "TemporalInvertido", "888", 0, new BigDecimal("1500000.00"),
                hoy, hoy.minusDays(10)));

        // Contrato vencido para la fecha de liquidación
        EmpleadoTemporal vencido = new EmpleadoTemporal(
                "E18B", "TemporalVencido", "889", 0, new BigDecimal("1500000.00"),
                hoy.minusMonths(6), hoy.minusMonths(1));

        assertThrows(ValidacionException.class, () -> servicioNomina.liquidarEmpleado(vencido, hoy));
    }

    // 19. Aplicación consistente del redondeo monetario (HALF_UP a 2 decimales)
    @Test
    @DisplayName("19. Redondeo monetario consistente: HALF_UP a 2 decimales en todos los cálculos")
    void testRedondeoMonetarioConsistente() {
        // Empleado por horas con tarifa y horas fraccionarias
        // 37.33 horas a $21533.33 = 803839.2089 -> Redondea HALF_UP a $803.839,21
        EmpleadoPorHoras emp = new EmpleadoPorHoras(
                "E19", "Fraccionario", "777", 0, new BigDecimal("21533.33"), new BigDecimal("37.33"), false);
        DesgloseNomina desglose = servicioNomina.liquidarEmpleado(emp);

        assertEquals(new BigDecimal("803839.21"), desglose.getSalarioBruto());
        assertEquals(2, desglose.getSalarioNeto().scale());
        assertEquals(2, desglose.getSalarioBruto().scale());
        assertEquals(2, desglose.getDeduccionSeguroSocialPension().scale());
    }

    // 20. Cálculo de nóminas para diferentes tipos de empleados en una misma ejecución (Polimorfismo)
    @Test
    @DisplayName("20. Procesamiento colectivo polimórfico de nómina para todos los tipos de empleados")
    void testCalculoPolimorficoColeccionEmpleados() {
        servicioNomina.registrarEmpleado(new EmpleadoAsalariado("P1", "Asalariado", "C1", 6, new BigDecimal("3000000.00")));
        servicioNomina.registrarEmpleado(new EmpleadoPorHoras("P2", "Por Horas", "C2", 2, new BigDecimal("25000.00"), new BigDecimal("42.00"), true));
        servicioNomina.registrarEmpleado(new EmpleadoPorComision("P3", "Por Comisión", "C3", 3, new BigDecimal("1800000.00"), new BigDecimal("0.05"), new BigDecimal("22000000.00")));
        servicioNomina.registrarEmpleado(new EmpleadoTemporal("P4", "Temporal", "C4", 0, new BigDecimal("2000000.00"), LocalDate.now().minusMonths(1), LocalDate.now().plusMonths(3)));

        List<DesgloseNomina> desgloses = servicioNomina.liquidarTodos();

        assertEquals(4, desgloses.size());
        for (DesgloseNomina desglose : desgloses) {
            assertNotNull(desglose.getSalarioNeto());
            assertTrue(desglose.getSalarioNeto().compareTo(BigDecimal.ZERO) > 0);
            assertNotNull(desglose.getEmpleado());
        }
    }
}
