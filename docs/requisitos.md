# Especificación de Requisitos del Sistema de Nómina Orientado a Objetos

## Proyecto CIPA — Modalidad Individual
**Estudiante:** Andrés Felipe Guzmán Arrieta  
**Programa:** Ingeniería de Software  
**Institución:** Universidad de Cartagena  

---

## 1. Introducción
El presente documento contiene la especificación formal de requisitos funcionales, no funcionales, reglas de negocio y restricciones del **Sistema de Nómina Orientado a Objetos**. Este proyecto ha sido desarrollado de forma individual como parte de las actividades evaluativas del CIPA, aplicando principios de diseño orientado a objetos (POO), arquitectura en capas, código limpio y pruebas automatizadas con JUnit 5.

## 2. Planteamiento del Problema
La gestión de nómina en las organizaciones constituye uno de los procesos operativos y financieros más críticos. La complejidad radica en la coexistencia de múltiples modalidades contractuales (empleados con sueldo fijo, remuneración variable por horas laboradas, esquemas comerciales por comisión y personal con contratos a término fijo). Cada modalidad posee reglas particulares para el cómputo de devengados, bonificaciones de antigüedad o desempeño, horas extras con recargos específicos, beneficios corporativos y deducciones de ley o voluntarias.

Comúnmente, las soluciones rudimentarias sufren de:
- Acoplamiento excesivo mediante estructuras condicionales anidadas (`if-else` o `switch` sobre el tipo de empleado).
- Imprecisión aritmética por el uso inadecuado de tipos de coma flotante binaria (`double` o `float`).
- Mezcla errónea entre beneficios cubiertos por el empleador y descuentos aplicados al sueldo del trabajador.
- Falta de validaciones estrictas y pruebas automatizadas que garanticen la integridad de los cálculos.

## 3. Justificación
Desarrollar este sistema bajo el paradigma de programación orientada a objetos (POO) y los principios SOLID permite demostrar cómo una arquitectura desacoplada y polimórfica resuelve de manera elegante y extensible las variaciones de nómina. Además, al tratarse de un desarrollo individual, evidencia el dominio integral del ciclo de vida del software: desde el análisis y diseño conceptual, hasta la codificación, verificación formal con pruebas unitarias y documentación técnica.

## 4. Objetivo General
Diseñar, implementar, probar y documentar un sistema de liquidación de nómina de consola en Java 17 gestionado con Maven, que modele de forma limpia y polimórfica distintos tipos de empleados, garantizando precisión numérica con `BigDecimal`, cumplimiento estricto de las reglas de negocio y cobertura exhaustiva mediante pruebas unitarias con JUnit 5.

## 5. Objetivos Específicos
1. Modelar una jerarquía orientada a objetos clara y extensible mediante una abstracción base `Empleado` y sus especializaciones (`EmpleadoAsalariado`, `EmpleadoPorHoras`, `EmpleadoPorComision`, `EmpleadoTemporal`).
2. Segregar interfaces funcionales (`Calculable`, `Bonificable`, `ElegibleFondoAhorro`) para respetar el principio de segregación de interfaces (ISP).
3. Implementar servicios desacoplados para el cálculo de deducciones legales/voluntarias y beneficios empresariales utilizando inyección de dependencias.
4. Aplicar políticas rigurosas de validación de datos de entrada y salvaguardas de negocio (como el rechazo ante salarios netos negativos).
5. Construir y ejecutar una batería de al menos 20 pruebas unitarias con JUnit 5 que cubran casos típicos, límites contractuales y rechazo de datos inválidos.
6. Proporcionar una interfaz interactiva de consola en español que permita la administración y liquidación individual o masiva de empleados.

## 6. Alcance del Sistema
El sistema abarca:
- Registro en memoria de empleados de cuatro modalidades contractuales.
- Cálculo de devengados base, horas extras, comisiones por ventas y bonificaciones por antigüedad o rendimiento.
- Cálculo de deducciones de ley (seguridad social y pensión del 4%) y aportes voluntarios al fondo de ahorro (2%).
- Asignación de beneficios financiados por el empleador (auxilio de alimentación de $1.000.000).
- Cálculo ilustrativo y configurable de costos patronales (ARL patronal).
- Generación de desgloses de pago detallados para auditoría y comprobante de nómina.
- Ejecución masiva polimórfica y resumen consolidado de costos organizacionales.

*Límite del alcance:* La versión actual opera mediante interfaz de consola en memoria. No incluye persistencia en bases de datos relacionales ni interfaz gráfica de usuario (GUI/Web), aunque su arquitectura por capas permite incorporarlas a futuro sin modificar la lógica de negocio.

## 7. Actores y Usuarios
Al ser una aplicación de consola para liquidación interna, interactúa un único actor:
- **Analista / Administrador de Nómina:** Usuario autorizado encargado de ingresar datos de empleados, configurar parámetros patronales, liquidar salarios individuales o colectivos y revisar comprobantes y reportes consolidados.

## 8. Requisitos Funcionales (RF)
- **RF-01 (Registro de Asalariado):** Permitir el registro de un empleado asalariado ingresando ID, nombre, documento, antigüedad en años y salario mensual fijo positivo.
- **RF-02 (Registro de Empleado por Horas):** Permitir el registro indicando tarifa por hora positiva, horas trabajadas en el período (no negativas) y consentimiento explícito para el fondo de ahorro.
- **RF-03 (Registro de Empleado por Comisión):** Permitir el registro ingresando salario base (no negativo), porcentaje de comisión sobre ventas (0.00 a 1.00) y monto total de ventas acumuladas (no negativo).
- **RF-04 (Registro de Empleado Temporal):** Permitir el registro con salario mensual acordado, fecha de inicio y fecha de finalización contractual.
- **RF-05 (Consulta de Empleados):** Listar todos los empleados registrados en el sistema mostrando sus atributos principales.
- **RF-06 (Liquidación Individual):** Calcular y desplegar el comprobante detallado de nómina para un empleado identificado por su ID único.
- **RF-07 (Liquidación Masiva Polimórfica):** Procesar la nómina de todos los empleados en una única ejecución polimórfica, totalizando el desembolso neto de la empresa y los costos patronales.
- **RF-08 (Configuración de ARL):** Permitir la consulta y modificación dinámica del porcentaje de ARL patronal sin alterar los cálculos de deducciones del trabajador.
- **RF-09 (Carga de Datos de Prueba):** Proveer una opción para cargar un lote predefinido de empleados que ilustre todos los casos y reglas de negocio para demostración académica.
- **RF-10 (Control de Errores y Validaciones):** Informar de manera clara y amigable cualquier dato inválido o violación de regla sin que la aplicación termine inesperadamente.

## 9. Requisitos No Funcionales (RNF)
- **RNF-01 (Precisión Numérica):** Todos los cálculos monetarios deben realizarse obligatoriamente mediante la clase `java.math.BigDecimal`, evitando inconsistencias de redondeo propias de `float` o `double`.
- **RNF-02 (Política de Redondeo):** Aplicar de forma uniforme una escala de 2 decimales y el modo de redondeo `RoundingMode.HALF_UP` en todos los importes calculados.
- **RNF-03 (Compatibilidad y Plataforma):** Desarrollado sobre Java 17 LTS y gestionado mediante Apache Maven (estándar `pom.xml`).
- **RNF-04 (Calidad y Principios SOLID):** El código debe aplicar los cinco principios SOLID, desacoplamiento de servicios y polimorfismo puro.
- **RNF-05 (Cobertura de Pruebas):** El sistema debe contar con pruebas automatizadas estructuradas con JUnit 5 que alcancen el 100% de los escenarios solicitados por la guía académica.
- **RNF-06 (Idioma e Interfaz):** Toda la interacción con el usuario en consola y mensajes de error debe estar redactada en idioma español claro y profesional.

## 10. Reglas de Negocio Obligatorias (RN)

### RN-01: Empleado Asalariado
- Percibe un salario mensual fijo acordado contractualmente.
- Si su antigüedad es estrictamente mayor a 5 años (`antiguedadAnios > 5`), percibe un bono mensual del 10% sobre su salario base.
- Si tiene 5 años exactos o menos, **no** recibe dicho bono.
- Tiene derecho al beneficio corporativo de alimentación de $1.000.000 mensual asumido íntegramente por la empresa.

### RN-02: Empleado por Horas
- El salario devengado se calcula multiplicando las horas laboradas por la tarifa por hora normal.
- Las primeras 40 horas del período de cálculo se liquidan a la tarifa ordinaria.
- Las horas que superen las 40 se liquidan como horas extras con un factor de recargo del 1.5x (150% de la tarifa base).
- No percibe bonos de nómina ni beneficio de alimentación.
- Si su antigüedad supera 1 año (`antiguedadAnios > 1`) y acepta formalmente participar, se le deduce mensualmente el 2% de su salario bruto con destino al fondo de ahorro institucional.
- Si rechaza el fondo o tiene 1 año o menos de antigüedad, no se efectúa descuento alguno.

### RN-03: Empleado por Comisión
- Devenga un salario base fijo más una comisión porcentual convenida sobre el total de ventas del período.
- Si el monto de ventas realizadas supera estrictamente los $20.000.000 (`ventas > 20000000.00`), recibe una bonificación adicional extraordinaria del 3% calculada sobre el total de sus ventas.
- Si las ventas son exactamente iguales a $20.000.000 o inferiores, no se otorga este bono adicional.
- Tiene derecho al beneficio corporativo de alimentación de $1.000.000 mensual cubierto por la empresa.

### RN-04: Empleado Temporal
- Percibe un salario mensual fijo convenido.
- Posee fechas obligatorias de inicio y finalización contractual.
- No percibe bonos ni beneficios corporativos adicionales.
- Solo se puede liquidar si a la fecha de corte de la nómina el contrato se encuentra plenamente vigente.

### RN-05: Deducciones y Beneficios
- **Deducción de Seguridad Social y Pensión:** Descuento obligatorio del 4% sobre el salario bruto total devengado por el trabajador.
- **Fondo de Ahorro:** Deducción del 2% del salario bruto exclusivamente para empleados por horas elegibles que hayan aceptado.
- **Beneficio de Alimentación:** $1.000.000 mensuales pagados por el empleador (no se resta del sueldo del empleado) para Asalariados y Comisionistas.
- **ARL (Administradora de Riesgos Laborales):** Costo a cargo de la empresa (patronal), configurable y jamás deducible del salario neto del empleado.

## 11. Restricciones y Validaciones Obligatorias
1. Identificadores, nombres y documentos no pueden ser nulos ni contener cadenas en blanco.
2. La antigüedad no puede ser negativa (`antiguedad >= 0`).
3. Horas trabajadas y tarifas no pueden ser negativas. Las tarifas deben ser estrictamente positivas (> 0).
4. El monto de ventas realizadas no puede ser negativo (`ventas >= 0`).
5. El porcentaje de comisión debe situarse en el intervalo $[0.00, 1.00]$.
6. La fecha de finalización del contrato temporal no puede ser anterior a la fecha de inicio.
7. Salvaguarda de Salario Neto: Si tras deducir los conceptos aplicables el salario neto resulta negativo, el sistema debe abortar la liquidación lanzando `SalarioInvalidoException` en lugar de alterar silenciosamente el resultado.

## 12. Ambigüedades y Supuestos Académicos Formales

| Concepto | Ambigüedad en la Guía | Supuesto Académico Adoptado | Justificación Técnica |
|---|---|---|---|
| **Tarifa de Comisión** | El enunciado no fija una tasa única obligatoria para todos los comisionistas. | Se diseñó como un parámetro configurable por empleado en su constructor (ej. 0.05 para 5%), validado entre 0.00 y 1.00. | En el ámbito empresarial real las comisiones varían según categoría y metas; fijar un valor quemado en código violaría la extensibilidad. |
| **Tarifa y Asunción de ARL** | El enunciado pide contemplar la ARL pero no especifica tarifa ni sujeto pasivo del pago. | Se modela como un **costo patronal** con tasa por defecto de 0.522% (Riesgo I en Colombia), configurable mediante `GestorBeneficios`. **No se descuenta al trabajador**. | En la legislación laboral colombiana la ARL es asumida en su totalidad por la empresa empleadora. Tratarla como deducción al empleado sería un error normativo y financiero. |
| **Período de las 40 Horas** | El enunciado indica que después de 40 horas hay recargo del 1.5x, sin aclarar si el ciclo es semanal, quincenal o mensual. | Se asume que las 40 horas representan la jornada estándar ordinaria del ciclo o corte que se está liquidando en el ejercicio. | Permite validar directamente la fórmula del enunciado sin requerir conversiones calendáricas arbitrarias. |
| **Base de las Deducciones** | No se especifica si las deducciones se calculan sobre el salario base o el salario bruto total (incluyendo bonos y comisiones). | Se liquidan sobre el **salario bruto total devengado** (base + horas extras + comisiones + bonificaciones). | Garantiza congruencia contable y evita que bonos salariales queden libres de retención de seguridad social según el espíritu del ejercicio. |
| **Tratamiento del Auxilio de Alimentación** | Diferenciación entre concepto devengado y beneficio en especie asumido por la empresa. | Se registra como beneficio cubierto por la empresa. Suma al costo empresarial pero **no se entrega en efectivo dentro del salario neto ni se deduce**. | El enunciado establece textualmente: *"cubierto por la empresa"*, impidiendo que sea cargado negativamente al bolsillo del empleado. |
