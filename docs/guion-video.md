# Guion de Exposición para Video de YouTube (8 a 12 Minutos)

## Proyecto CIPA — Modalidad Individual
**Expositor (Estudiante):** Andrés Felipe Guzmán Arrieta  
**Programa:** Ingeniería de Software  
**Institución:** Universidad de Cartagena  
**Duración Total Estimada:** 10:00 a 11:30 minutos  

---

## 1. Planificación General de la Exposición

| Bloque | Sección Temática | Tiempo Estimado | Pantalla a Compartir | Archivo o Recurso a Mostrar |
|---|---|---|---|---|
| **1** | Presentación e Introducción | 01:00 min | Cámara web y Diapositiva inicial | Portada del proyecto / Datos del estudiante |
| **2** | Problema y Objetivos del CIPA | 01:00 min | Documentación | `docs/requisitos.md` |
| **3** | Reglas de Negocio y Supuestos | 01:30 min | Documentación | `docs/requisitos.md` (Tabla comparativa) |
| **4** | Arquitectura y Diseño POO | 01:30 min | IDE / Diagramas | `docs/arquitectura.md` (Diagramas Mermaid) |
| **5** | Demostración de Principios SOLID | 01:30 min | IDE (Editor de código) | `Empleado.java`, `Bonificable.java`, `ServicioNomina.java` |
| **6** | Demostración Funcional en Consola | 02:00 min | Terminal de comandos | Ejecución de `java -jar ...` con menú interactivo |
| **7** | Pruebas Unitarias Automatizadas | 01:00 min | Terminal / IDE | Ejecución en vivo de `mvn test` (20 pruebas) |
| **8** | Control de Versiones con Git | 00:45 min | Terminal | `git log --oneline` e historial de commits |
| **9** | Metodología y Conclusiones | 00:45 min | Diapositiva final | Resumen de aprendizajes y cierre |

---

## 2. Guion Detallado Paso a Paso (Parlamento del Estudiante)

### Bloque 1: Presentación e Introducción (0:00 - 1:00)
- **Pantalla:** Cámara del estudiante en primer plano o compartiendo la portada oficial del proyecto.
- **Acción:** Saludo formal, identificación institucional y presentación del objetivo.
- **Texto sugerido:**
> *"Muy buenas a todos. Mi nombre es Andrés Felipe Guzmán Arrieta, estudiante de Ingeniería de Software de la Universidad de Cartagena. En este video voy a sustentar de manera individual el desarrollo del Sistema de Nómina Orientado a Objetos correspondiente a la actividad del CIPA.  
> El propósito de este proyecto es construir un software profesional, mantenible y matemáticamente preciso en Java 17 con Maven, que liquide la nómina de diferentes modalidades contractuales aplicando polimorfismo puro, principios SOLID y pruebas unitarias con JUnit 5."*

---

### Bloque 2: Problema y Objetivos del CIPA (1:00 - 2:00)
- **Pantalla:** Archivo `docs/requisitos.md` abierto en el editor.
- **Acción:** Resaltar la complejidad de administrar múltiples tipos de contratos y la necesidad de separar deducciones de costos de la empresa.
- **Texto sugerido:**
> *"En cualquier organización coexisten diferentes figuras contractuales: asalariados fijos, personal que cobra por horas laboradas, vendedores a comisión y empleados con contratos a término definido.  
> Nuestro objetivo fue crear una arquitectura que no dependa de cadenas interminables de condicionales `if-else` para saber qué tipo de empleado estamos liquidando, garantizando a la vez que los cálculos financieros se manejen con `BigDecimal` para evitar cualquier error de redondeo monetario."*

---

### Bloque 3: Reglas de Negocio y Supuestos Académicos (2:00 - 3:30)
- **Pantalla:** Sección de reglas de negocio en `docs/requisitos.md`.
- **Acción:** Explicar rápidamente cada uno de los cuatro tipos de empleados y las decisiones tomadas.
- **Texto sugerido:**
> *"Las reglas del negocio son muy claras:
> 1. Para el empleado asalariado: sueldo fijo, bono del 10% únicamente si tiene más de 5 años de servicio, y auxilio de alimentación de $1.000.000 pagado por la empresa.
> 2. Para el empleado por horas: jornada base de 40 horas a tarifa ordinaria y horas extras al 150%. Además, si tiene más de 1 año y lo autoriza expresamente, se deduce el 2% para su fondo de ahorro.
> 3. Para el empleado por comisión: salario base, comisión sobre ventas y un bono adicional del 3% si las ventas superan estrictamente los 20 millones de pesos, más el auxilio de alimentación.
> 4. Para el empleado temporal: salario fijo con validación estricta de fechas de vigencia contractual.
> Respecto a deducciones y beneficios: se aplica la retención del 4% para salud y pensión, y la ARL se configuró como costo patronal de la empresa, sin descontarla jamás del sueldo del empleado."*

---

### Bloque 4: Arquitectura y Diseño POO (3:30 - 5:00)
- **Pantalla:** Diagrama UML en `docs/arquitectura.md` o el árbol de paquetes en el IDE.
- **Acción:** Mostrar la jerarquía de herencia y la estructura en capas (`model`, `service`, `validation`, `exception`, `ui`).
- **Texto sugerido:**
> *"Aquí en pantalla pueden observar la arquitectura del sistema. En el paquete `model` tenemos una clase abstracta base `Empleado` de la que heredan `EmpleadoAsalariado`, `EmpleadoPorHoras`, `EmpleadoPorComision` y `EmpleadoTemporal`.  
> Separamos las responsabilidades en capas: los servicios calculan deducciones y gestionan beneficios, el validador asegura que no ingresen datos negativos o cadenas vacías, y el comprobante de nómina se encapsula en una clase inmutable llamada `DesgloseNomina`."*

---

### Bloque 5: Demostración de Principios SOLID (5:00 - 6:30)
- **Pantalla:** Código fuente en el IDE (`Bonificable.java`, `ServicioNomina.java`, `CalculadoraDeduccionesService.java`).
- **Acción:** Explicar brevemente cómo se ven reflejados los principios.
- **Texto sugerido:**
> *"Aplicamos los principios SOLID de forma real:
> - **Responsabilidad Única (S):** La clase `CalculadoraDeduccionesService` solo se encarga de deducciones; no interfiere con la interfaz de usuario ni con los beneficios.
> - **Abierto/Cerrado (O):** Si la empresa crea un nuevo tipo de contrato, como pasantes o contratistas, simplemente extendemos `Empleado` sin modificar el motor de liquidación masiva.
> - **Sustitución de Liskov (L):** Cualquier empleado concreto puede ser procesado por el servicio de nómina respetando el contrato de la clase padre.
> - **Segregación de Interfaces (I):** En lugar de recargar a `Empleado`, creamos interfaces pequeñas como `Bonificable` o `ElegibleFondoAhorro`, implementadas únicamente por quienes realmente poseen esas características.
> - **Inversión de Dependencias (D):** `ServicioNomina` depende de interfaces abstractas que se inyectan en su constructor, lo cual facilitó aislar las pruebas unitarias."*

---

### Bloque 6: Demostración Funcional en Consola (6:30 - 8:30)
- **Pantalla:** Terminal abierta con la aplicación en ejecución.
- **Acción:** Ejecutar el comando `java -jar target/sistema-nomina-cipa-1.0.0.jar --demo`. Mostrar la carga de empleados, consultar la lista y liquidar un empleado individual y luego todos colectivamente.
- **Texto sugerido:**
> *"Vamos a ver el sistema en acción. Ejecutamos la aplicación con el parámetro `--demo` para cargar automáticamente un conjunto representativo de empleados.  
> Seleccionamos la opción 5 y vemos que tenemos registrados asalariados con más y menos de 5 años, empleados con horas extras y comisionistas con diferentes metas de ventas.  
> Si seleccionamos la opción 6 para liquidar al empleado EMP-003, vemos el recibo detallado: 40 horas normales a $25.000, 5 horas extras al 1.5x, retención de seguridad social del 4%, deducción del fondo de ahorro del 2% y el salario neto exacto.  
> Ahora ejecutamos la opción 7: liquidación colectiva polimórfica. El sistema itera sobre la lista heterogénea de empleados sin condicionales de tipo y nos entrega el consolidado general con el total que la empresa debe desembolsar."*

---

### Bloque 7: Pruebas Unitarias Automatizadas con JUnit 5 (8:30 - 9:30)
- **Pantalla:** Terminal en el proyecto ejecutando `mvn test`.
- **Acción:** Mostrar el resultado de Maven Surefire en pantalla con 20 pruebas aprobadas.
- **Texto sugerido:**
> *"Para garantizar que cada regla de negocio y cada caso límite se cumplan sin margen de error, construimos una suite completa con JUnit 5 en `SistemaNominaTest`.  
> Ejecutamos en vivo `mvn test`. Como pueden ver en la salida de consola, se ejecutaron las 20 pruebas unitarias: validaciones de horas negativas, ventas negativas, límites de 5 años de antigüedad, liquidación polimórfica y redondeo con `BigDecimal`. Obtenemos 0 fallos, 0 errores y el mensaje final BUILD SUCCESS."*

---

### Bloque 8: Control de Versiones con Git (9:30 - 10:15)
- **Pantalla:** Terminal ejecutando `git log --oneline`.
- **Acción:** Mostrar el historial de commits semánticos que evidencian el desarrollo progresivo individual.
- **Texto sugerido:**
> *"El desarrollo del proyecto siguió un control de versiones riguroso con Git. En el historial de commits pueden apreciar una evolución limpia y progresiva: configuración inicial de Maven, modelo de entidades, servicios, interfaz de consola, suite de pruebas unitarias y documentación técnica completa."*

---

### Bloque 9: Metodología y Conclusiones (10:15 - 11:00)
- **Pantalla:** Diapositiva final con conclusiones y datos institucionales.
- **Acción:** Reflexión final y despedida cordial.
- **Texto sugerido:**
> *"Como conclusión, este proyecto desarrollado individualmente bajo la metodología Personal Kanban demostró que la programación orientada a objetos y los principios SOLID proporcionan una base arquitectónica sólida y profesional para resolver problemas de lógica de negocio complejos.  
> Muchas gracias por su atención y quedo atento a cualquier inquietud o sugerencia."*

---

## 3. Lista de Comprobación Previa a la Grabación

- [ ] Micrófono verificado con audio claro y sin eco.
- [ ] Resolución de pantalla configurada en 1080p (Full HD) y tamaño de fuente ampliado en la terminal y el IDE para máxima legibilidad.
- [ ] Compilación previa ejecutada exitosamente (`mvn clean package`).
- [ ] Terminal lista en la ruta del proyecto `sistema-nomina-cipa`.
- [ ] Diapositiva de portada abierta con el nombre completo: Andrés Felipe Guzmán Arrieta y datos de la Universidad de Cartagena.
- [ ] Cerrar notificaciones de correo o aplicaciones de mensajería durante la grabación.
