package com.cipa.nomina.ui;

import com.cipa.nomina.exception.NominaException;
import com.cipa.nomina.model.DesgloseNomina;
import com.cipa.nomina.model.Empleado;
import com.cipa.nomina.model.EmpleadoAsalariado;
import com.cipa.nomina.model.EmpleadoPorComision;
import com.cipa.nomina.model.EmpleadoPorHoras;
import com.cipa.nomina.model.EmpleadoTemporal;
import com.cipa.nomina.service.ServicioNomina;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Interfaz de usuario por consola para interactuar con el sistema de nómina CIPA.
 * Separa la lógica de presentación de la lógica de negocio (Principio de Responsabilidad Única).
 */
public class MenuConsola {

    private final ServicioNomina servicioNomina;
    private final Scanner scanner;

    public MenuConsola(ServicioNomina servicioNomina) {
        this.servicioNomina = servicioNomina;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            mostrarMenuPrincipal();
            String opcion = leerTexto("Seleccione una opción: ");
            System.out.println();
            switch (opcion) {
                case "1" -> registrarAsalariado();
                case "2" -> registrarPorHoras();
                case "3" -> registrarPorComision();
                case "4" -> registrarTemporal();
                case "5" -> listarEmpleados();
                case "6" -> liquidarUnEmpleado();
                case "7" -> liquidarTodosLosEmpleados();
                case "8" -> configurarArlPatronal();
                case "9" -> cargarDatosDePrueba();
                case "10" -> {
                    System.out.println("Gracias por utilizar el Sistema de Nómina Orientado a Objetos - CIPA.");
                    salir = true;
                }
                default -> System.out.println("[!] Opción inválida. Intente de nuevo.\n");
            }
        }
    }

    private void mostrarMenuPrincipal() {
        System.out.println("================================================================================");
        System.out.println("          SISTEMA DE NÓMINA ORIENTADO A OBJETOS — PROYECTO CIPA                ");
        System.out.println("================================================================================");
        System.out.println("1. Registrar Empleado Asalariado");
        System.out.println("2. Registrar Empleado por Horas");
        System.out.println("3. Registrar Empleado por Comisión");
        System.out.println("4. Registrar Empleado Temporal");
        System.out.println("5. Consultar Empleados Registrados");
        System.out.println("6. Liquidar Nómina de un Empleado (por ID)");
        System.out.println("7. Liquidar Nómina de Todos los Empleados (Polimorfismo)");
        System.out.println("8. Configurar Tasa Patronal de ARL");
        System.out.println("9. Cargar Empleados de Demostración Académica");
        System.out.println("10. Salir del Sistema");
        System.out.println("================================================================================");
    }

    private void registrarAsalariado() {
        System.out.println("--- REGISTRO DE EMPLEADO ASALARIADO ---");
        try {
            String id = leerTexto("ID único: ");
            String nombre = leerTexto("Nombre completo: ");
            String doc = leerTexto("Documento de identidad: ");
            int antiguedad = leerEntero("Años de antigüedad en la empresa: ");
            BigDecimal salario = leerBigDecimal("Salario mensual fijo ($): ");

            Empleado emp = new EmpleadoAsalariado(id, nombre, doc, antiguedad, salario);
            servicioNomina.registrarEmpleado(emp);
            System.out.println("\n[✓] Empleado asalariado registrado con éxito.\n");
        } catch (NominaException | IllegalArgumentException e) {
            System.out.println("\n[ERROR DE VALIDACIÓN] " + e.getMessage() + "\n");
        }
    }

    private void registrarPorHoras() {
        System.out.println("--- REGISTRO DE EMPLEADO POR HORAS ---");
        try {
            String id = leerTexto("ID único: ");
            String nombre = leerTexto("Nombre completo: ");
            String doc = leerTexto("Documento de identidad: ");
            int antiguedad = leerEntero("Años de antigüedad en la empresa: ");
            BigDecimal tarifa = leerBigDecimal("Tarifa por hora normal ($): ");
            BigDecimal horas = leerBigDecimal("Horas trabajadas en el período: ");
            boolean aceptaFondo = leerBooleano("¿Acepta participar en el fondo de ahorro voluntario? (s/n): ");

            Empleado emp = new EmpleadoPorHoras(id, nombre, doc, antiguedad, tarifa, horas, aceptaFondo);
            servicioNomina.registrarEmpleado(emp);
            System.out.println("\n[✓] Empleado por horas registrado con éxito.\n");
        } catch (NominaException | IllegalArgumentException e) {
            System.out.println("\n[ERROR DE VALIDACIÓN] " + e.getMessage() + "\n");
        }
    }

    private void registrarPorComision() {
        System.out.println("--- REGISTRO DE EMPLEADO POR COMISIÓN ---");
        try {
            String id = leerTexto("ID único: ");
            String nombre = leerTexto("Nombre completo: ");
            String doc = leerTexto("Documento de identidad: ");
            int antiguedad = leerEntero("Años de antigüedad en la empresa: ");
            BigDecimal salarioBase = leerBigDecimal("Salario base fijo ($): ");
            BigDecimal tasaComision = leerBigDecimal("Porcentaje de comisión sobre ventas (ej: 0.05 para 5%): ");
            BigDecimal ventas = leerBigDecimal("Total ventas acumuladas en el período ($): ");

            Empleado emp = new EmpleadoPorComision(id, nombre, doc, antiguedad, salarioBase, tasaComision, ventas);
            servicioNomina.registrarEmpleado(emp);
            System.out.println("\n[✓] Empleado por comisión registrado con éxito.\n");
        } catch (NominaException | IllegalArgumentException e) {
            System.out.println("\n[ERROR DE VALIDACIÓN] " + e.getMessage() + "\n");
        }
    }

    private void registrarTemporal() {
        System.out.println("--- REGISTRO DE EMPLEADO TEMPORAL ---");
        try {
            String id = leerTexto("ID único: ");
            String nombre = leerTexto("Nombre completo: ");
            String doc = leerTexto("Documento de identidad: ");
            int antiguedad = leerEntero("Años de antigüedad en la empresa: ");
            BigDecimal salario = leerBigDecimal("Salario mensual acordado ($): ");
            LocalDate fechaInicio = leerFecha("Fecha inicio contrato (YYYY-MM-DD): ");
            LocalDate fechaFin = leerFecha("Fecha fin contrato (YYYY-MM-DD): ");

            Empleado emp = new EmpleadoTemporal(id, nombre, doc, antiguedad, salario, fechaInicio, fechaFin);
            servicioNomina.registrarEmpleado(emp);
            System.out.println("\n[✓] Empleado temporal registrado con éxito.\n");
        } catch (NominaException | IllegalArgumentException e) {
            System.out.println("\n[ERROR DE VALIDACIÓN] " + e.getMessage() + "\n");
        }
    }

    private void listarEmpleados() {
        System.out.println("--- LISTADO DE EMPLEADOS REGISTRADOS ---");
        List<Empleado> lista = servicioNomina.getEmpleadosRegistrados();
        if (lista.isEmpty()) {
            System.out.println("No hay empleados registrados actualmente en el sistema.\n");
            return;
        }
        for (Empleado emp : lista) {
            System.out.println(" • " + emp);
        }
        System.out.println();
    }

    private void liquidarUnEmpleado() {
        System.out.println("--- LIQUIDAR NÓMINA INDIVIDUAL ---");
        String id = leerTexto("Ingrese el ID del empleado a liquidar: ");
        Empleado emp = servicioNomina.buscarEmpleadoPorId(id);
        if (emp == null) {
            System.out.println("\n[!] No se encontró ningún empleado con el ID: " + id + "\n");
            return;
        }

        try {
            DesgloseNomina resultado = servicioNomina.liquidarEmpleado(emp);
            System.out.println("\n" + resultado);
        } catch (NominaException e) {
            System.out.println("\n[ERROR EN LIQUIDACIÓN] " + e.getMessage() + "\n");
        }
    }

    private void liquidarTodosLosEmpleados() {
        System.out.println("--- LIQUIDACIÓN MASIVA DE NÓMINA (POLIMORFISMO POO) ---");
        List<Empleado> empleados = servicioNomina.getEmpleadosRegistrados();
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados para procesar.\n");
            return;
        }

        try {
            List<DesgloseNomina> desgloses = servicioNomina.liquidarTodos();
            BigDecimal totalNetoPagar = BigDecimal.ZERO;
            BigDecimal totalCostosEmpresa = BigDecimal.ZERO;

            for (DesgloseNomina desglose : desgloses) {
                System.out.println(desglose);
                totalNetoPagar = totalNetoPagar.add(desglose.getSalarioNeto());
                totalCostosEmpresa = totalCostosEmpresa.add(desglose.getCostoTotalEmpresa());
            }

            System.out.println("********************************************************************************");
            System.out.println(" RESUMEN CONSOLIDADO DE LA NÓMINA INSTITUCIONAL");
            System.out.println(String.format(" Cantidad de empleados procesados:  %d", desgloses.size()));
            System.out.println(String.format(" Total neto a desembolsar nómina:   $ %,15.2f", totalNetoPagar));
            System.out.println(String.format(" Costo total global para la empresa: $ %,15.2f", totalCostosEmpresa));
            System.out.println("********************************************************************************\n");
        } catch (NominaException e) {
            System.out.println("\n[ERROR EN LIQUIDACIÓN COLECTIVA] " + e.getMessage() + "\n");
        }
    }

    private void configurarArlPatronal() {
        System.out.println("--- CONFIGURACIÓN DE TASA PATRONAL DE ARL ---");
        System.out.println("Tasa actual configurada: " + 
                servicioNomina.getGestorBeneficios().getPorcentajeArlPatronal().multiply(BigDecimal.valueOf(100)) + "%");
        try {
            BigDecimal nuevaTasa = leerBigDecimal("Ingrese la nueva tasa porcentual (ej: 0.00522 para Clase I - 0.522%): ");
            servicioNomina.getGestorBeneficios().setPorcentajeArlPatronal(nuevaTasa);
            System.out.println("\n[✓] Tasa de ARL patronal actualizada exitosamente.\n");
        } catch (NominaException e) {
            System.out.println("\n[ERROR] " + e.getMessage() + "\n");
        }
    }

    public void cargarDatosDePrueba() {
        System.out.println("--- CARGANDO DATOS DE PRUEBA ACADÉMICOS ---");
        try {
            // 1. Asalariado con > 5 años (recibe bono 10% y auxilio alimentación)
            servicioNomina.registrarEmpleado(new EmpleadoAsalariado(
                    "EMP-001", "Carlos Mendoza", "CC-10111222", 6, new BigDecimal("3500000.00")));

            // 2. Asalariado con 5 años exactos (sin bono, pero con auxilio alimentación)
            servicioNomina.registrarEmpleado(new EmpleadoAsalariado(
                    "EMP-002", "Ana Sofía Gómez", "CC-10333444", 5, new BigDecimal("2800000.00")));

            // 3. Por Horas: 45 horas trabajadas (40 normal + 5 extras a 1.5x), >1 año y acepta fondo (2%)
            servicioNomina.registrarEmpleado(new EmpleadoPorHoras(
                    "EMP-003", "Jorge Ramírez", "CC-10555666", 2, new BigDecimal("25000.00"), new BigDecimal("45.00"), true));

            // 4. Por Horas: 35 horas trabajadas (< 40 h), sin extras, rechaza fondo
            servicioNomina.registrarEmpleado(new EmpleadoPorHoras(
                    "EMP-004", "Lucía Fernández", "CC-10777888", 1, new BigDecimal("20000.00"), new BigDecimal("35.00"), false));

            // 5. Por Comisión: ventas > 20M ($25.000.000), bono 3% adicional y auxilio alimentación
            servicioNomina.registrarEmpleado(new EmpleadoPorComision(
                    "EMP-005", "Marcela Torres", "CC-10999000", 3, new BigDecimal("1800000.00"), new BigDecimal("0.05"), new BigDecimal("25000000.00")));

            // 6. Por Comisión: ventas exactamente $20.000.000 (sin bono adicional del 3%)
            servicioNomina.registrarEmpleado(new EmpleadoPorComision(
                    "EMP-006", "David Morales", "CC-10222333", 4, new BigDecimal("1800000.00"), new BigDecimal("0.04"), new BigDecimal("20000000.00")));

            // 7. Temporal con contrato vigente
            servicioNomina.registrarEmpleado(new EmpleadoTemporal(
                    "EMP-007", "Elena Vargas", "CC-10444555", 0, new BigDecimal("2200000.00"), 
                    LocalDate.now().minusMonths(2), LocalDate.now().plusMonths(4)));

            System.out.println("[✓] Se cargaron 7 empleados de prueba ilustrando todos los escenarios de negocio.\n");
        } catch (Exception e) {
            System.out.println("[!] Nota: Algunos empleados ya estaban registrados: " + e.getMessage() + "\n");
        }
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String entrada = scanner.nextLine().trim();
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("[!] Debe ingresar un número entero válido.");
            }
        }
    }

    private BigDecimal leerBigDecimal(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String entrada = scanner.nextLine().trim().replace(",", ".");
                return new BigDecimal(entrada);
            } catch (Exception e) {
                System.out.println("[!] Ingrese un valor numérico decimal válido (ej: 2500000 o 0.05).");
            }
        }
    }

    private boolean leerBooleano(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim().toLowerCase();
            if (entrada.equals("s") || entrada.equals("si") || entrada.equals("sí") || entrada.equals("true")) {
                return true;
            }
            if (entrada.equals("n") || entrada.equals("no") || entrada.equals("false")) {
                return false;
            }
            System.out.println("[!] Responda 's' para Sí o 'n' para No.");
        }
    }

    private LocalDate leerFecha(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String entrada = scanner.nextLine().trim();
                return LocalDate.parse(entrada);
            } catch (DateTimeParseException e) {
                System.out.println("[!] Formato de fecha inválido. Utilice el formato AAAA-MM-DD (ej: 2026-12-31).");
            }
        }
    }
}
