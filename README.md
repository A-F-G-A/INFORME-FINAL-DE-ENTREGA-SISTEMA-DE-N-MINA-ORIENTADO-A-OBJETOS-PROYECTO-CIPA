# Sistema de Nómina Orientado a Objetos — Proyecto CIPA

[![Java 17](https://img.shields.io/badge/Java-17%20LTS-orange.svg)](https://www.oracle.com/java/)
[![Maven 3](https://img.shields.io/badge/Maven-3.9+-blue.svg)](https://maven.apache.org/)
[![JUnit 5](https://img.shields.io/badge/JUnit-5.10.2-green.svg)](https://junit.org/junit5/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Sistema profesional de liquidación y gestión de nómina organizacional desarrollado bajo el paradigma de **Programación Orientada a Objetos (POO)** en **Java 17 LTS** con **Apache Maven** y pruebas unitarias con **JUnit 5**.

Este proyecto fue desarrollado de forma individual para la actividad del **CIPA** en la **Universidad de Cartagena**.

---

## 📌 Autor
- **Estudiante:** Andrés Felipe Guzmán Arrieta
- **Programa:** Ingeniería de Software
- **Institución:** Universidad de Cartagena (Cartagena de Indias, Colombia)

---

## 🚀 Características Principales
- **Jerarquía Polimórfica:** Modelo puro orientado a objetos para 4 tipos de empleados:
  - `EmpleadoAsalariado`: Salario mensual fijo, bono del 10% si antigüedad > 5 años y auxilio de alimentación de $1.000.000 cubierto por la empresa.
  - `EmpleadoPorHoras`: Jornada estándar de 40 horas, horas extras al 1.5x (150%), y deducción voluntaria del 2% al fondo de ahorro institucional (si antigüedad > 1 año y aceptación explícita).
  - `EmpleadoPorComision`: Salario base + comisión configurable por ventas, bono adicional del 3% si ventas > $20.000.000, y auxilio de alimentación de $1.000.000 asumido por la empresa.
  - `EmpleadoTemporal`: Salario mensual fijo con control de vigencia contractual por fechas y sin bonificaciones adicionales.
- **Deducciones y Costos Patronales Claros:** Diferenciación contable estricta entre salario bruto, deducciones a cargo del empleado (salud y pensión del 4%, fondo de ahorro), salario neto a pagar y costos corporativos asumidos por el empleador (alimentación y ARL patronal configurable).
- **Precisión Monetaria Absoluta:** Todos los importes financieros utilizan `java.math.BigDecimal` con escala a 2 decimales y redondeo `RoundingMode.HALF_UP`.
- **Principios SOLID:** Arquitectura desacoplada en capas (`model`, `service`, `validation`, `exception`, `ui`) aplicando Inyección de Dependencias y Segregación de Interfaces.
- **Validaciones Rigurosas:** Prevención de horas o ventas negativas, tarifas inválidas, contratos vencidos y salvaguarda contra salarios netos negativos (`SalarioInvalidoException`).
- **Cobertura de Pruebas Unitarias al 100%:** Suite de 20 casos de prueba automatizados con JUnit 5 evaluando límites, casos nominales y escenarios de error.
- **Consola Interactiva:** Menú en español claro con opción de precarga de datos de demostración académica (`--demo`).

---

## 📁 Estructura del Proyecto

```
sistema-nomina-cipa/
├── pom.xml                               # Configuración de dependencias Maven y plugins
├── README.md                             # Documentación principal del repositorio
├── .gitignore                            # Exclusiones de Git para Java, Maven e IDEs
├── docs/                                 # Documentación técnica y académica completa
│   ├── requisitos.md                     # Especificación formal de requisitos y supuestos
│   ├── arquitectura.md                   # Diseño UML, capas y principios SOLID
│   ├── metodologia.md                    # Metodología ágil Personal Kanban individual
│   ├── casos-de-prueba.md                # Matriz detallada de las 20 pruebas JUnit 5
│   ├── informe-academico.md              # Informe universitario formal con portada editable
│   └── guion-video.md                    # Guion técnico para sustentación en YouTube (8-12 min)
└── src/
    ├── main/java/com/cipa/nomina/
    │   ├── Main.java                     # Punto de entrada de la aplicación
    │   ├── model/                        # Entidades del dominio y contratos
    │   │   ├── Empleado.java             # Clase abstracta base
    │   │   ├── EmpleadoAsalariado.java
    │   │   ├── EmpleadoPorHoras.java
    │   │   ├── EmpleadoPorComision.java
    │   │   ├── EmpleadoTemporal.java
    │   │   ├── DesgloseNomina.java       # Comprobante inmutable de nómina
    │   │   ├── Calculable.java           # Interfaz de cómputo salarial
    │   │   ├── Bonificable.java          # Interfaz de bonificaciones
    │   │   ├── ElegibleFondoAhorro.java  # Interfaz de fondo de ahorro
    │   │   ├── TipoEmpleado.java         # Enumeración de modalidades
    │   │   └── ConstantesNomina.java     # Parámetros y umbrales centralizados
    │   ├── service/                      # Servicios de negocio desacoplados
    │   │   ├── ServicioNomina.java       # Orquestador principal de nómina
    │   │   ├── CalculadoraDeducciones.java
    │   │   ├── CalculadoraDeduccionesService.java
    │   │   ├── GestorBeneficios.java
    │   │   └── GestorBeneficiosService.java
    │   ├── validation/
    │   │   └── ValidadorNomina.java      # Validaciones centralizadas
    │   ├── exception/                    # Excepciones de dominio
    │   │   ├── NominaException.java
    │   │   ├── ValidacionException.java
    │   │   └── SalarioInvalidoException.java
    │   └── ui/
    │       └── MenuConsola.java          # Menú interactivo por consola
    └── test/java/com/cipa/nomina/
        └── SistemaNominaTest.java        # 20 Pruebas unitarias con JUnit 5
```

---

## 🛠️ Requisitos Previos e Instalación

### Requisitos del Sistema:
1. **Java Development Kit (JDK):** Versión 17 LTS o superior.
2. **Apache Maven:** Versión 3.8 o superior (o utilizar el Maven integrado de IntelliJ / Eclipse / NetBeans).
3. **Git:** Para control de versiones.

### Comprobar Instalación:
```powershell
java -version
mvn -version
git --version
```

---

## ⚙️ Compilación, Pruebas y Empaquetado

1. **Clonar o ubicarse en la carpeta del proyecto:**
   ```powershell
   cd sistema-nomina-cipa
   ```

2. **Ejecutar las pruebas unitarias automatizadas (JUnit 5):**
   ```powershell
   mvn clean test
   ```
   *Debe reportar:* `Tests run: 20, Failures: 0, Errors: 0, Skipped: 0` y `BUILD SUCCESS`.

3. **Compilar y empaquetar el ejecutable JAR:**
   ```powershell
   mvn clean package
   ```
   *Genera el archivo ejecutable:* `target/sistema-nomina-cipa-1.0.0.jar`.

---

## 🖥️ Ejecución de la Aplicación

### Modo Normal (Menú Interactivo):
```powershell
java -jar target/sistema-nomina-cipa-1.0.0.jar
```

### Modo Demostración Académica Inmediata (Precarga de Empleados de Prueba):
```powershell
java -jar target/sistema-nomina-cipa-1.0.0.jar --demo
```
*Este modo carga instantáneamente 7 casos de prueba que cubren todos los escenarios de negocio (asalariados con y sin bono, empleados con horas extras, comisionistas con y sin bono del 3%, y contratos temporales vigentes).*

---

## 🧪 Resumen de Pruebas Unitarias (`SistemaNominaTest`)

| # | Escenario Evaluado | Resultado |
|---|---|---|
| 1 | Asalariado con más de 5 años: recibe bono del 10% | ✅ PASÓ |
| 2 | Asalariado con exactamente 5 años: no recibe bono | ✅ PASÓ |
| 3 | Asalariado con menos de 5 años: no recibe bono | ✅ PASÓ |
| 4 | Empleado por horas con exactamente 40 horas: sin extras | ✅ PASÓ |
| 5 | Empleado por horas con 45 horas: 40 normales + 5 extras al 1.5x | ✅ PASÓ |
| 6 | Empleado por horas con menos de 40 horas: pago proporcional | ✅ PASÓ |
| 7 | Comisionista con ventas > $20.000.000: bono adicional del 3% | ✅ PASÓ |
| 8 | Comisionista con ventas exactamente de $20.000.000: sin bono | ✅ PASÓ |
| 9 | Comisionista con ventas inferiores a $20.000.000: sin bono | ✅ PASÓ |
| 10 | Empleado temporal: salario fijo sin bonos ni auxilio | ✅ PASÓ |
| 11 | Empleado por horas > 1 año que acepta fondo: deducción del 2% | ✅ PASÓ |
| 12 | Empleado por horas que rechaza fondo: sin deducción | ✅ PASÓ |
| 13 | Deducción de seguridad social y pensión del 4% exacto | ✅ PASÓ |
| 14 | Rechazo de horas trabajadas negativas con excepción | ✅ PASÓ |
| 15 | Rechazo de ventas realizadas negativas con excepción | ✅ PASÓ |
| 16 | Rechazo de salarios, tarifas y porcentajes fuera de rango | ✅ PASÓ |
| 17 | Salvaguarda contra cálculo con salario neto negativo | ✅ PASÓ |
| 18 | Validación de contratos temporales vencidos o fechas invertidas | ✅ PASÓ |
| 19 | Redondeo monetario consistente `HALF_UP` a 2 decimales | ✅ PASÓ |
| 20 | Procesamiento masivo polimórfico de empleados heterogéneos | ✅ PASÓ |

---

## 🏛️ Principios SOLID Aplicados

- **S (Single Responsibility):** Desacoplamiento de cálculo de deducciones, beneficios corporativos, validaciones y vista de consola.
- **O (Open/Closed):** Nuevos esquemas de contratación se agregan extendiendo `Empleado` sin tocar el servicio de liquidación.
- **L (Liskov Substitution):** Cualquier empleado puede ser liquidado polimórficamente sin errores de tipo ni comportamientos anómalos.
- **I (Interface Segregation):** Interfaces atómicas `Bonificable` y `ElegibleFondoAhorro` implementadas solo por quienes las utilizan.
- **D (Dependency Inversion):** `ServicioNomina` depende de interfaces abstractas inyectadas mediante su constructor.

---

## 🌐 Publicación en Repositorio GitHub

Para publicar el repositorio local en tu cuenta de GitHub, ejecuta los siguientes comandos una vez hayas creado el repositorio vacío en GitHub:

```powershell
# Ubicarse en el proyecto
cd sistema-nomina-cipa

# Renombrar rama a main (estándar de GitHub)
git branch -M main

# Vincular con tu repositorio remoto de GitHub (reemplaza con tu URL real)
git remote add origin https://github.com/TU-USUARIO/sistema-nomina-cipa.git

# Subir el proyecto completo
git push -u origin main
```

---

## 📄 Licencia
Este proyecto es de uso académico para la Universidad de Cartagena bajo la licencia [MIT](LICENSE).
