# Matriz y Evidencia de Casos de Prueba Unitarios (JUnit 5)

## Proyecto CIPA — Modalidad Individual
**Estudiante:** Andrés Felipe Guzmán Arrieta  
**Programa:** Ingeniería de Software  
**Institución:** Universidad de Cartagena  
**Clase de Pruebas:** `com.cipa.nomina.SistemaNominaTest`  

---

## 1. Estrategia y Configuración de Pruebas
Las pruebas unitarias fueron construidas utilizando **JUnit 5 Jupiter** (`junit-jupiter-api` y `junit-jupiter-engine` versión `5.10.2`) sobre **Java 17**. Cada método de prueba está anotado con `@Test` y `@DisplayName` en español descriptivo, evaluando un escenario puntual de las reglas de negocio, límites contractuales o datos inválidos.

### Comando de Ejecución:
Para ejecutar el ciclo completo de pruebas en la terminal del proyecto, se utiliza:
```powershell
mvn test
```
o para una compilación limpia desde cero:
```powershell
mvn clean test
```

---

## 2. Matriz Detallada de los 20 Casos de Prueba

| ID | Nombre del Método de Prueba | Descripción del Escenario | Datos de Entrada (Parámetros) | Resultado Esperado | Resultado Obtenido | Estado |
|---|---|---|---|---|---|---|
| **CP-01** | `testAsalariadoConMasDeCincoAniosRecibeBono()` | Asalariado con > 5 años recibe bono del 10% y auxilio de alimentación. | Sueldo: $3.000.000, Antigüedad: 6 años | Bono: $300.000, Bruto: $3.300.000, Salud/Pens: $132.000, Neto: $3.168.000, Alim: $1.000.000 | Coincide con precisión matemática | **PASÓ** |
| **CP-02** | `testAsalariadoConExactamenteCincoAniosSinBono()` | Asalariado con exactamente 5 años: no debe recibir bono. | Sueldo: $3.000.000, Antigüedad: 5 años | Bono: $0.00, Bruto: $3.000.000, Salud/Pens: $120.000, Neto: $2.880.000 | Coincide con precisión matemática | **PASÓ** |
| **CP-03** | `testAsalariadoConMenosDeCincoAniosSinBono()` | Asalariado con < 5 años: no debe recibir bono. | Sueldo: $2.500.000, Antigüedad: 4 años | Bono: $0.00, Bruto: $2.500.000, Salud/Pens: $100.000, Neto: $2.400.000 | Coincide con precisión matemática | **PASÓ** |
| **CP-04** | `testEmpleadoPorHorasConExactamente40Horas()` | Empleado por horas con exactamente 40 horas: horas ordinarias completas, cero extras. | Tarifa: $20.000, Horas: 40.00, AceptaFondo: false | Normales: $800.000, Extras: $0.00, Bruto: $800.000, Neto: $768.000 | Coincide con precisión matemática | **PASÓ** |
| **CP-05** | `testEmpleadoPorHorasCon45Horas()` | Empleado por horas con 45 horas: 40 normales y 5 extras con recargo del 1.5x. | Tarifa: $20.000, Horas: 45.00, AceptaFondo: false | Normales: $800.000, Extras: $150.000 (5*30.000), Bruto: $950.000, Neto: $912.000 | Coincide con precisión matemática | **PASÓ** |
| **CP-06** | `testEmpleadoPorHorasConMenosDe40Horas()` | Empleado por horas con menos de 40 horas: pago proporcional exacto, cero extras. | Tarifa: $25.000, Horas: 30.00, AceptaFondo: false | Normales: $750.000, Extras: $0.00, Bruto: $750.000 | Coincide con precisión matemática | **PASÓ** |
| **CP-07** | `testEmpleadoPorComisionConVentasSuperioresA20M()` | Comisionista con ventas > $20.000.000: bono adicional del 3% sobre ventas y auxilio. | Base: $1.500.000, Tasa: 5%, Ventas: $25.000.000 | Comision: $1.250.000, Bono: $750.000, Bruto: $3.500.000, Neto: $3.360.000, Alim: $1.000.000 | Coincide con precisión matemática | **PASÓ** |
| **CP-08** | `testEmpleadoPorComisionConVentasExactamente20M()` | Comisionista con ventas exactamente de $20.000.000: sin bono adicional del 3%. | Base: $1.500.000, Tasa: 5%, Ventas: $20.000.000 | Comision: $1.000.000, Bono: $0.00, Bruto: $2.500.000 | Coincide con precisión matemática | **PASÓ** |
| **CP-09** | `testEmpleadoPorComisionConVentasInferioresA20M()` | Comisionista con ventas inferiores a $20.000.000: sin bono adicional. | Base: $1.500.000, Tasa: 5%, Ventas: $15.000.000 | Comision: $750.000, Bono: $0.00, Bruto: $2.250.000 | Coincide con precisión matemática | **PASÓ** |
| **CP-10** | `testEmpleadoTemporalSinBonosNiBeneficios()` | Temporal con contrato vigente: sueldo fijo, deducción 4%, sin bonos ni auxilio. | Sueldo: $2.000.000, Rango: [-1 mes a +5 meses] | Bruto: $2.000.000, Bono: $0.00, Alim: $0.00, Deducc: $80.00, Neto: $1.920.000 | Coincide con precisión matemática | **PASÓ** |
| **CP-11** | `testEmpleadoPorHorasCumpleAntiguedadYAceptaFondoAhorro()` | Empleado por horas con antigüedad > 1 año y que acepta fondo: deducción del 2%. | Tarifa: $25.000, Horas: 40.00, Antigüedad: 2 años, Acepta: true | Bruto: $1.000.000, Salud/Pens (4%): $40.000, Fondo (2%): $20.000, Neto: $940.000 | Coincide con precisión matemática | **PASÓ** |
| **CP-12** | `testEmpleadoPorHorasRechazaFondoAhorro()` | Empleado por horas con antigüedad > 1 año pero rechaza fondo: sin retención de ahorro. | Tarifa: $25.000, Horas: 40.00, Antigüedad: 3 años, Acepta: false | Bruto: $1.000.000, Fondo: $0.00, Total Deducc: $40.000, Neto: $960.000 | Coincide con precisión matemática | **PASÓ** |
| **CP-13** | `testDeduccionSeguroSocialYPensionDelCuatroPorCiento()` | Deducción de seguridad social y pensión del 4% exacto sobre el salario bruto. | Sueldo Bruto: $4.500.000 | Deducción: $180.000.00 exactos | Coincide con precisión matemática | **PASÓ** |
| **CP-14** | `testRechazoDeHorasNegativas()` | Validador rechaza horas trabajadas negativas con excepción. | Horas: -5.00 | Lanza `ValidacionException` con mensaje explicativo | Se capturó `ValidacionException` | **PASÓ** |
| **CP-15** | `testRechazoDeVentasNegativas()` | Validador rechaza ventas realizadas negativas. | Ventas: -$1.000.00 | Lanza `ValidacionException` con mensaje explicativo | Se capturó `ValidacionException` | **PASÓ** |
| **CP-16** | `testRechazoSalariosTarifasOPorcentajesInvalidos()` | Validador rechaza salarios <= 0, tarifas <= 0, comisiones > 1.0 y antigüedad < 0. | Diversos valores fuera de rango | Lanza `ValidacionException` en cada caso frontera | Se verificaron 4 aserciones `assertThrows` | **PASÓ** |
| **CP-17** | `testRechazoSalarioNetoNegativo()` | Salvaguarda de negocio rechaza cálculos anómalos que resulten en neto negativo. | Deducción forzada simulada del 150% | Lanza `SalarioInvalidoException` rechazando liquidar | Se capturó `SalarioInvalidoException` | **PASÓ** |
| **CP-18** | `testValidacionContratoTemporal()` | Validación de contratos temporales ante fechas invertidas o contratos ya vencidos. | Fechas invertidas / Contrato vencido vs. fecha hoy | Lanza `ValidacionException` en ambos casos | Se capturó `ValidacionException` | **PASÓ** |
| **CP-19** | `testRedondeoMonetarioConsistente()` | Verificación de precisión y redondeo HALF_UP a 2 decimales en operaciones fraccionarias. | 37.33 horas a $21.533,33 (= $803.839,2089) | Redondea a $803.839,21 con escala fija de 2 decimales | Escala = 2, Valor = 803839.21 | **PASÓ** |
| **CP-20** | `testCalculoPolimorficoColeccionEmpleados()` | Liquidación masiva homogénea de una lista mixta con los 4 tipos de empleados. | 1 Asalariado, 1 Por Horas, 1 Por Comisión, 1 Temporal | Lista de 4 desgloses con netos positivos sin condicionales de tipo | 4 liquidaciones exitosas | **PASÓ** |

---

## 3. Evidencia Real de Ejecución en Consola (Maven Surefire)

A continuación se transcribe el resultado textual obtenido al ejecutar `mvn test` directamente en la terminal del entorno de desarrollo:

```
[INFO] Scanning for projects...
[INFO] 
[INFO] ----------------< com.cipa.nomina:sistema-nomina-cipa >-----------------
[INFO] Building Sistema de Nomina Orientado a Objetos - CIPA 1.0.0
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ sistema-nomina-cipa ---
[INFO] skip non existing resourceDirectory ...\src\main\resources
[INFO] 
[INFO] --- compiler:3.11.0:compile (default-compile) @ sistema-nomina-cipa ---
[INFO] Nothing to compile - all classes are up to date
[INFO] 
[INFO] --- resources:3.3.1:testResources (default-testResources) @ sistema-nomina-cipa ---
[INFO] skip non existing resourceDirectory ...\src\test\resources
[INFO] 
[INFO] --- compiler:3.11.0:testCompile (default-testCompile) @ sistema-nomina-cipa ---
[INFO] Nothing to compile - all classes are up to date
[INFO] 
[INFO] --- surefire:3.2.5:test (default-test) @ sistema-nomina-cipa ---
[INFO] Using auto detected provider org.apache.maven.surefire.junitplatform.JUnitPlatformProvider
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.cipa.nomina.SistemaNominaTest
[INFO] Tests run: 20, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.199 s -- in com.cipa.nomina.SistemaNominaTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 20, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

### Interpretación de los Resultados:
- **`Tests run: 20`:** Se identificaron y ejecutaron de manera íntegra los 20 métodos de prueba unitarios.
- **`Failures: 0`:** Ninguna aserción lógica falló. Todos los cálculos matemáticos, políticas de redondeo y comprobaciones de excepciones respondieron exactamente como fue previsto en la especificación de negocio.
- **`Errors: 0`:** No se presentaron excepciones imprevistas en tiempo de ejecución (`NullPointerException`, divisiones entre cero, etc.).
- **`Skipped: 0`:** No se omitió ninguna prueba de la suite.
- **`BUILD SUCCESS`:** El motor Maven certificó formalmente la estabilidad y corrección del proyecto.
