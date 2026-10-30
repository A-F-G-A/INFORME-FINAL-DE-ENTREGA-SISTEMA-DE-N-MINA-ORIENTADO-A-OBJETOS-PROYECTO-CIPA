# Metodología de Desarrollo Ágil Individual

## Proyecto CIPA — Modalidad Individual
**Estudiante:** Andrés Felipe Guzmán Arrieta  
**Programa:** Ingeniería de Software  
**Institución:** Universidad de Cartagena  

---

## 1. Justificación de la Metodología Seleccionada
Para el desarrollo de este proyecto, al ser ejecutado en su totalidad por un **único estudiante**, las metodologías tradicionales pensadas para equipos medianos o grandes (como Scrum con sus múltiples roles: Product Owner, Scrum Master y Development Team, o ceremonias como Daily Standups y Sprint Reviews) introducen una sobrecarga burocrática innecesaria e irreal.

Por ello, se seleccionó una adaptación rigurosa de **Personal Kanban (Kanban Individual)** complementada con prácticas técnicas de **Extreme Programming (XP)**:
- **Visualización continua del flujo de trabajo:** Permite tener claridad de qué tarea está en progreso y qué está completado.
- **Límite de trabajo en curso (WIP - Work In Progress):** Al trabajar de forma individual, el límite de tareas en progreso simultáneas se fijó en 1 o máximo 2, evitando el cambio de contexto destructivo.
- **Enfoque en calidad continua (Test-Driven Mindset):** Definición de pruebas unitarias tempranas para validar cada regla de negocio antes de dar por cerrada una funcionalidad.
- **Entregas incrementales verificadas:** Cada fase se valida directamente contra el compilador de Java y la suite de pruebas automatizadas con Maven.

---

## 2. Organización y Roles en el Desarrollo Individual
Al tratarse de un proyecto individual, el estudiante asume todas las responsabilidades del ciclo de vida del software, alternando conscientemente los siguientes roles funcionales:

1. **Analista de Requisitos:** Interpretación de la guía del CIPA, levantamiento de reglas de negocio, identificación de ambigüedades y formalización de supuestos académicos.
2. **Arquitecto de Software:** Diseño de la jerarquía de clases POO, definición de interfaces segregadas y aplicación de patrones e inyección de dependencias (SOLID).
3. **Desarrollador Java:** Implementación en código limpio, uso de `BigDecimal` y construcción del menú interactivo de consola.
4. **Ingeniero de Calidad (QA):** Diseño de la matriz de pruebas, implementación de la suite JUnit 5, ejecución de pruebas de regresión y verificación de casos límite y anómalos.
5. **Especialista en Documentación y Git:** Gestión del repositorio local, commits progresivos con convenciones semánticas, elaboración del informe monográfico y guion para sustentación en video.

---

## 3. Tablero Kanban del Proyecto

El avance del proyecto se gestionó mediante un tablero estructurado en cuatro columnas operativas:

```
+--------------------------------+---------------------------+---------------------------+---------------------------------+
|      PENDIENTE (BACKLOG)       |   EN DESARROLLO (WIP <= 2)|    EN REVISIÓN / PRUEBA   |      COMPLETADO (DONE)          |
+--------------------------------+---------------------------+---------------------------+---------------------------------+
| [ ] Grabar video de YouTube    |                           |                           | [X] Configuración Maven pom.xml |
| [ ] Publicar repositorio GitHub|                           |                           | [X] Jerarquía Empleados POO     |
|                                |                           |                           | [X] Interfaces Segregadas       |
|                                |                           |                           | [X] Servicios y Validaciones    |
|                                |                           |                           | [X] Menú de Consola Interactivo |
|                                |                           |                           | [X] 20 Pruebas JUnit 5 pasadas  |
|                                |                           |                           | [X] docs/requisitos.md          |
|                                |                           |                           | [X] docs/arquitectura.md        |
|                                |                           |                           | [X] docs/metodologia.md         |
+--------------------------------+---------------------------+---------------------------+---------------------------------+
```

---

## 4. Historias de Usuario y Criterios de Aceptación

### HU-01: Cálculo de Salario Asalariado con Bono por Antigüedad
- **Como:** Administrador de nómina.
- **Quiero:** Registrar y liquidar empleados asalariados con salario mensual fijo.
- **Para:** Asignar el 10% de bono si tienen más de 5 años de servicio y otorgar el auxilio de alimentación de $1.000.000 asumido por la empresa.
- **Criterios de Aceptación:**
  1. Si `antiguedadAnios > 5`, el bono es exactamente el 10% del salario base.
  2. Si `antiguedadAnios == 5` o menor, el bono es exactamente $0.00.
  3. El beneficio de alimentación se suma al costo patronal, sin descontarse ni sumarse en efectivo al salario neto del trabajador.

### HU-02: Liquidación de Empleados por Horas y Horas Extras
- **Como:** Administrador de nómina.
- **Quiero:** Calcular el pago de empleados por horas diferenciando horas ordinarias y extras.
- **Para:** Cumplir con la jornada estándar de 40 horas y pagar con recargo del 1.5x las horas excedentes.
- **Criterios de Aceptación:**
  1. Las primeras 40 horas se pagan a tarifa normal (`tarifa * horas`).
  2. Las horas superiores a 40 se pagan a `tarifa * 1.5 * horasExtras`.
  3. No perciben bonos de antigüedad ni auxilio de alimentación.

### HU-03: Participación en el Fondo de Ahorro Voluntario
- **Como:** Empleado por horas con más de 1 año de antigüedad.
- **Quiero:** Decidir voluntariamente si aporto el 2% de mi salario bruto al fondo de ahorro.
- **Para:** Construir un ahorro institucional descontado por nómina.
- **Criterios de Aceptación:**
  1. Solo aplica si `antiguedadAnios > 1` y el empleado marcó `aceptaFondoAhorro == true`.
  2. Si tiene 1 año exacto o menos, la deducción es $0.00 aunque haya manifestado interés.
  3. Si manifiesta rechazo (`false`), la deducción es $0.00.

### HU-04: Liquidación por Comisión y Meta de Ventas
- **Como:** Administrador de nómina.
- **Quiero:** Liquidar empleados de ventas con salario base y comisión porcentual configurable.
- **Para:** Premiar ventas superiores a $20.000.000 con un 3% de bono adicional sobre ventas.
- **Criterios de Aceptación:**
  1. La comisión regular se calcula sobre el total de ventas realizadas.
  2. Si `ventas > 20000000.00`, se adiciona un bono del 3% sobre el total de ventas.
  3. Si `ventas <= 20000000.00`, el bono adicional es $0.00.
  4. Recibe auxilio de alimentación de $1.000.000 asumido por la empresa.

### HU-05: Control de Contratos Temporales
- **Como:** Analista de talento humano.
- **Quiero:** Registrar empleados temporales con fechas explícitas de inicio y fin.
- **Para:** Garantizar que no se liquide ningún contrato temporal vencido a la fecha de corte.
- **Criterios de Aceptación:**
  1. La fecha de fin no puede ser anterior a la de inicio.
  2. Si la fecha de corte de nómina está fuera del rango del contrato, el sistema rechaza la liquidación con un mensaje claro.
  3. No perciben bonificaciones ni beneficios adicionales.

### HU-06: Procesamiento Masivo Polimórfico
- **Como:** Administrador de nómina.
- **Quiero:** Liquidar la nómina de todos los empleados registrados con un solo comando.
- **Para:** Obtener el total consolidado a pagar por nómina y el costo empresarial total sin condicionales de tipo.
- **Criterios de Aceptación:**
  1. La lista se procesa polimórficamente mediante la abstracción `Empleado`.
  2. Se totaliza el salario neto y el costo empresarial en el consolidado.

---

## 5. Estrategia de Pruebas y Aseguramiento de Calidad
1. **Pruebas Unitarias Automatizadas:** Implementación de la clase `SistemaNominaTest` con JUnit 5 conteniendo 20 casos de prueba independientes.
2. **Pruebas de Límites y Valores Frontera:**
   - Asalariado con 5 años exactos vs. 6 años.
   - Empleado por horas con 40 horas exactas vs. 41 horas.
   - Ventas por comisión de $20.000.000 exactos vs. $20.000.001.
3. **Pruebas de Datos Inválidos:** Comprobación del lanzamiento de `ValidacionException` ante salarios negativos, tarifas cero, horas negativas o fechas invertidas.
4. **Prueba de Salvaguarda Financiera:** Simulación de deducciones extremas para verificar el disparo de `SalarioInvalidoException`.
5. **Comando de Verificación Continua:** Ejecución reiterada de `mvn test` y `mvn clean package` durante cada etapa de desarrollo.

---

## 6. Gestión de Configuración y Control de Versiones con Git
- **Estrategia de Ramas:** Se utilizó la rama principal `master` con commits atómicos y descriptivos que representan incrementos funcionales terminados.
- **Formato de Mensajes de Commit (Conventional Commits):**
  - `chore:` Configuración de entorno y archivos base.
  - `feat:` Nuevas funcionalidades de negocio o capas del sistema.
  - `test:` Construcción y afinamiento de pruebas unitarias.
  - `docs:` Elaboración y ampliación de la documentación académica y técnica.
- **Verificabilidad Individual:** Todos los commits registran la autoría de Andrés Felipe Guzmán Arrieta, asegurando trazabilidad académica transparente.

---

## 7. Limitaciones Reconocidas del Trabajo Individual
1. **Falta de revisión cruzada por pares (Peer Review):** Al no contar con otros integrantes de equipo, la revisión de código dependió exclusivamente de la inspección rigurosa personal y de las aserciones automáticas de JUnit.
2. **Carga multifuncional simultánea:** El estudiante debió gestionar paralelamente la arquitectura, la sintaxis en Java, el diseño de la suite de pruebas y la redacción de informes extensos.
3. **Mitigación aplicada:** Se implementó una suite exhaustiva de pruebas unitarias que actuó como mecanismo objetivo de verificación y prevención de regresiones.
