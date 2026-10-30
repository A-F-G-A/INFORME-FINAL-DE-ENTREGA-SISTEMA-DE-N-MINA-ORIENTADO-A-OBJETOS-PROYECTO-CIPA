# INFORME TÉCNICO Y ACADÉMICO: SISTEMA DE NÓMINA ORIENTADO A OBJETOS

---

### PORTADA EDITABLE DEL PROYECTO

**INSTITUCIÓN:** Universidad de Cartagena  
**FACULTAD:** Facultad de Ingeniería  
**PROGRAMA ACADÉMICO:** Ingeniería de Software  
**ASIGNATURA:** [COMPLETAR]  
**DOCENTE:** [COMPLETAR]  
**ESTUDIANTE (MODALIDAD INDIVIDUAL):** Andrés Felipe Guzmán Arrieta  
**CÓDIGO ESTUDIANTIL:** [COMPLETAR]  
**CIPA:** [COMPLETAR SEGÚN EL REGISTRO ACADÉMICO]  
**CIUDAD:** Cartagena de Indias, Colombia  
**FECHA:** [COMPLETAR]  

---

## RESUMEN EJECUTIVO
El presente informe documenta el desarrollo integral del **Sistema de Nómina Orientado a Objetos**, concebido como una solución robusta, tipada y desacoplada para la liquidación salarial de organizaciones con esquemas de contratación heterogéneos. Implementado en el lenguaje **Java 17 LTS** bajo el gestor de dependencias **Apache Maven**, el sistema modela cuatro tipos contractuales: Asalariados, Por Horas, Por Comisión y Temporales. Se aplicaron rigurosamente los cinco principios **SOLID**, código limpio y precisión monetaria con `BigDecimal`. La confiabilidad de la solución está garantizada por una suite de 20 pruebas unitarias automatizadas con **JUnit 5**, obteniendo un resultado de ejecución de 100% aprobado (`BUILD SUCCESS`).

---

## 1. INTRODUCCIÓN
En el ejercicio de la ingeniería de software moderna, la gestión financiera y de nómina representa un dominio de prueba ideal para evaluar la madurez de los paradigmas de programación. Los sistemas tradicionales de nómina frecuentemente colapsan ante cambios legislativos o nuevos tipos de beneficios debido a un diseño procedimental basado en estructuras condicionales anidadas y al uso impreciso de tipos de datos flotantes. 

El presente proyecto, desarrollado de forma individual para la actividad del CIPA, demuestra cómo el paradigma orientado a objetos, el polimorfismo puro, la segregación de interfaces y la inyección de dependencias permiten crear un motor de nómina flexible, escalable y auditado matemáticamente.

---

## 2. PLANTEAMIENTO DEL PROBLEMA Y JUSTIFICACIÓN
### 2.1. Problema
Una empresa contemporánea necesita procesar periódicamente las remuneraciones de su talento humano bajo cuatro modalidades contractuales con particularidades divergentes:
- Empleados asalariados con sueldo mensual fijo y primas de antigüedad.
- Empleados remunerados por horas que exigen el cálculo de recargos extraordinarios (150%) para horas superiores a 40 y la gestión opcional de un fondo de ahorro.
- Empleados comerciales remunerados mediante salario base más comisiones porcentuales y bonos por metas de ventas.
- Empleados temporales con restricciones temporales estrictas de vigencia contractual.

A esto se suma la necesidad de distinguir de manera categórica entre:
1. El salario bruto devengado por el empleado.
2. Las deducciones aplicadas a su pago (salud, pensión y fondo de ahorro).
3. El salario neto final a depositar.
4. Los beneficios corporativos asumidos en un 100% por la empresa (auxilio de alimentación).
5. Los costos patronales que no disminuyen el sueldo del colaborador (ARL).

### 2.2. Justificación
Implementar esta solución en Java 17 aplicando POO y buenas prácticas permite erradicar el código espagueti y los code smells típicos (como el "Type Checking" mediante condicionales repetitivos). Asimismo, provee a la academia un proyecto con evidencia verificable: código fuente compilable, cobertura de pruebas unitarias al 100% y documentación trazable en Git.

---

## 3. OBJETIVOS
### 3.1. Objetivo General
Diseñar, construir, probar y documentar un sistema de liquidación de nómina de consola orientado a objetos en Java 17 con Maven, que modele de forma limpia y polimórfica los diferentes tipos de empleados, garantizando exactitud matemática, respeto a los principios SOLID y alta mantenibilidad.

### 3.2. Objetivos Específicos
1. Construir un modelo de dominio con herencia y polimorfismo centrado en la abstracción `Empleado` y sus especializaciones.
2. Segregar responsabilidades mediante interfaces de capacidad (`Calculable`, `Bonificable`, `ElegibleFondoAhorro`).
3. Diseñar servicios desacoplados para el cálculo de deducciones y administración de beneficios empresariales mediante inyección de dependencias.
4. Implementar una política homogénea de precisión monetaria utilizando `BigDecimal` con escala a 2 decimales y redondeo `HALF_UP`.
5. Validar exhaustivamente las reglas de negocio con una suite de 20 pruebas unitarias con JUnit 5.
6. Estructurar una interfaz de consola interactiva con validaciones amigables y control de excepciones de dominio.

---

## 4. MARCO CONCEPTUAL
- **Programación Orientada a Objetos (POO):** Paradigma fundamentado en la encapsulación de estado y comportamiento, la herencia como mecanismo de especialización conceptual y el polimorfismo para tratar instancias heterogéneas mediante contratos comunes.
- **Principios SOLID:** Principios de diseño de software promovidos por Robert C. Martin para asegurar que los sistemas sean fáciles de mantener, probar y extender a lo largo del tiempo.
- **Precisión Decimal con BigDecimal:** En entornos contables y bancarios, los tipos nativos `float` y `double` introducen errores de precisión inaceptables al trabajar con fracciones no representables exactamente en binario. `BigDecimal` garantiza exactitud exacta de base 10.
- **JUnit 5:** Framework estándar de la industria para la construcción y ejecución automatizada de pruebas unitarias en la plataforma Java.

---

## 5. REGLAS DE NEGOCIO IMPLEMENTADAS

```
+-------------------------------------------------------------------------------------------------------+
| RESUMEN DE REGLAS DE NEGOCIO                                                                          |
+----------------------+--------------------+---------------------+--------------------+----------------+
| Tipo de Empleado     | Base Devengada     | Bonificación        | Aux. Alimentación  | Fondo Ahorro   |
+----------------------+--------------------+---------------------+--------------------+----------------+
| Asalariado           | Salario fijo       | 10% si antig > 5 a  | $1.000.000 empresa | No aplica      |
| Por Horas            | <= 40 h normales   | No aplica           | No aplica          | 2% si antig >1 |
|                      | > 40 h recargo 1.5x|                     |                    | y acepta       |
| Por Comisión         | Salario base +     | 3% s/ventas si      | $1.000.000 empresa | No aplica      |
|                      | comision s/ventas  | ventas > $20M       |                    |                |
| Temporal             | Salario fijo       | No aplica           | No aplica          | No aplica      |
|                      | (valida contrato)  |                     |                    |                |
+----------------------+--------------------+---------------------+--------------------+----------------+
```

### Ambigüedades y Supuestos Académicos Adoptados:
1. **Comisión por ventas:** Configurable individualmente por empleado en su constructor (tasa entre 0.00 y 1.00) en lugar de un valor estático quemado en código.
2. **ARL (Riesgos Laborales):** Se trata como costo 100% patronal con tasa ilustrativa por defecto de 0.522% (Clase de Riesgo I en Colombia). No se deduce al empleado.
3. **Período de 40 horas:** Se toma como la jornada estándar ordinaria del ciclo liquidado.
4. **Base de deducción de seguridad social y pensión:** Se aplica el 4% sobre el salario bruto total devengado.
5. **Auxilio de alimentación:** Beneficio corporativo financiado por el empleador; suma al costo empresarial pero no se entrega en efectivo dentro del salario neto ni se deduce.

---

## 6. DISEÑO ORIENTADO A OBJETOS Y ARQUITECTURA
El proyecto se estructuró en cinco paquetes claramente delimitados:
1. `com.cipa.nomina.model`: Entidades del dominio y contratos polimórficos (`Empleado`, `EmpleadoAsalariado`, `EmpleadoPorHoras`, `EmpleadoPorComision`, `EmpleadoTemporal`, `DesgloseNomina`, etc.).
2. `com.cipa.nomina.service`: Orquestación y lógica de liquidación (`ServicioNomina`, `CalculadoraDeduccionesService`, `GestorBeneficiosService`).
3. `com.cipa.nomina.validation`: Validación centralizada de datos y consistencia contractual (`ValidadorNomina`).
4. `com.cipa.nomina.exception`: Excepciones de dominio personalizadas (`NominaException`, `ValidacionException`, `SalarioInvalidoException`).
5. `com.cipa.nomina.ui`: Interfaz de usuario de consola interactiva (`MenuConsola`, `Main`).

---

## 7. APLICACIÓN DE LOS PRINCIPIOS SOLID

1. **S (Single Responsibility):** Cada clase atiende una única función. `ValidadorNomina` solo valida datos, `CalculadoraDeduccionesService` solo liquida descuentos, `GestorBeneficiosService` solo administra costos patronales y `MenuConsola` solo interactúa con el usuario.
2. **O (Open/Closed):** Para agregar un nuevo tipo contractual (por ejemplo, `EmpleadoFreelance`), se crea una nueva clase que extienda de `Empleado`, sin tocar el código de `ServicioNomina`.
3. **L (Liskov Substitution):** Cualquier clase derivada de `Empleado` puede sustituir a la clase padre en la colección masiva sin provocar excepciones imprevistas ni violar contratos.
4. **I (Interface Segregation):** Las interfaces son atómicas: `Bonificable` para quienes reciben primas y `ElegibleFondoAhorro` exclusivamente para empleados por horas.
5. **D (Dependency Inversion):** `ServicioNomina` depende de interfaces abstractas (`CalculadoraDeducciones` y `GestorBeneficios`) inyectadas a través del constructor, lo que permitió simular deducciones anómalas en las pruebas sin alterar la implementación de producción.

---

## 8. CÓDIGO LIMPIO Y REFACTORIZACIÓN
- **Eliminación de valores mágicos:** Creación de `ConstantesNomina` donde se centralizan tasas (0.04, 0.02, 0.03, 0.10), umbrales (5 años, 1 año, 40 horas, $20M) y redondeos.
- **Encapsulación estricta:** Atributos finales inmutables (`private final`), accesibles mediante métodos de lectura controlados.
- **Separación de responsabilidades:** La interfaz de consola no realiza cálculos salariales; simplemente invoca a `ServicioNomina`.
- **Refactorización demostrada:** Durante las pruebas, se refactorizó la escala monetaria de los subtotales en `ServicioNomina` para garantizar que todo valor monetario intermedio mantenga exactamente dos decimales (`setScale(2, HALF_UP)`), eliminando discrepancias de redondeo en aserciones de JUnit.

---

## 9. METODOLOGÍA DE DESARROLLO INDIVIDUAL
Se adoptó **Personal Kanban**, priorizando la visibilidad de tareas y el trabajo secuencial con un límite de trabajo en curso (WIP = 1). Las fases cubiertas fueron:
1. Análisis de requisitos y especificación formal.
2. Diseño UML de interfaces y entidades POO.
3. Codificación de entidades del modelo y constantes.
4. Desarrollo de validaciones y servicios desacoplados.
5. Construcción de interfaz de consola y datos de demostración.
6. Construcción y ejecución de la suite de pruebas unitarias JUnit 5.
7. Elaboración de documentación técnica y control de versiones con Git.

---

## 10. PLAN Y RESULTADOS DE PRUEBAS UNITARIAS
La suite `SistemaNominaTest` ejecutó 20 pruebas unitarias con JUnit 5 cubriendo:
- Asalariados con >5 años, =5 años y <5 años.
- Empleados por horas con 40 h exactas, 45 h (horas extras) y menos de 40 h.
- Comisionistas con ventas > $20M, = $20M y < $20M.
- Contratos temporales vigentes e inválidos.
- Aportes al fondo de ahorro aceptados y rechazados.
- Deducción legal del 4% y rechazo de salario neto negativo.
- Rechazo de horas negativas, ventas negativas y tarifas inválidas.
- Redondeo consistente con `BigDecimal` y procesamiento masivo polimórfico.

**Resultado oficial de Maven Surefire:**
- Pruebas ejecutadas: **20**
- Fallos: **0**
- Errores: **0**
- Omitidas: **0**
- Estado: **BUILD SUCCESS** (0.199 segundos de tiempo de ejecución de pruebas).

---

## 11. INSTRUCCIONES DE INSTALACIÓN Y EJECUCIÓN
### Requisitos Previos:
- Java JDK 17 o superior instalado y configurado en el `PATH`.
- Apache Maven 3.8+ instalado o NetBeans/IntelliJ con Maven integrado.

### Comandos de Compilación y Pruebas:
```powershell
# Ubicarse en el directorio del proyecto
cd sistema-nomina-cipa

# Compilar y ejecutar pruebas unitarias
mvn clean test

# Empaquetar el archivo JAR ejecutable
mvn clean package

# Ejecutar la aplicación en consola
java -jar target/sistema-nomina-cipa-1.0.0.jar

# Ejecutar con datos de prueba precargados para demostración académica inmediata
java -jar target/sistema-nomina-cipa-1.0.0.jar --demo
```

---

## 12. REPOSITORIO OFICIAL EN GITHUB
El código fuente completo, el historial de versiones con Git y la documentación técnica y académica se encuentran publicados oficialmente en:

**Enlace del repositorio:**  
https://github.com/A-F-G-A/INFORME-FINAL-DE-ENTREGA-SISTEMA-DE-N-MINA-ORIENTADO-A-OBJETOS-PROYECTO-CIPA

El proyecto cuenta con 20 pruebas unitarias aprobadas mediante JUnit 5 y empaquetado verificado con Maven.

---

## 13. CONCLUSIONES
1. Se demostró que el polimorfismo y la programación orientada a objetos resuelven la liquidación de nómina eliminando por completo los condicionales dependientes de tipos concretos.
2. La aplicación de los principios SOLID dota al software de una alta capacidad de extensión frente a cambios legales o contractuales futuros.
3. El uso estricto de `BigDecimal` es imperativo en aplicaciones financieras para evitar pérdidas de precisión y asegurar la confiabilidad contable.
4. La metodología Personal Kanban y las pruebas automatizadas con JUnit 5 permitieron a un único estudiante entregar una solución robusta, completa y libre de defectos.

---

## 14. REFERENCIAS BIBLIOGRÁFICAS
- Bloch, J. (2018). *Effective Java* (3rd ed.). Addison-Wesley Professional.
- Martin, R. C. (2008). *Clean Code: A Handbook of Agile Software Craftsmanship*. Prentice Hall.
- Martin, R. C. (2017). *Clean Architecture: A Craftsman's Guide to Software Structure and Design*. Prentice Hall.
- Oracle Corporation. (2023). *Class BigDecimal - Java 17 Documentation*. Oracle Java Platform, Standard Edition.
- JUnit Team. (2024). *JUnit 5 User Guide*. https://junit.org/junit5/docs/current/user-guide/
