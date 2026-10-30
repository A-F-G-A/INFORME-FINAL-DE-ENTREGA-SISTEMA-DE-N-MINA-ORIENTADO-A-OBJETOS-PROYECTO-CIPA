package com.cipa.nomina.exception;

/**
 * Excepción lanzada cuando los datos de entrada o estado de un empleado incumplen las restricciones del sistema.
 */
public class ValidacionException extends NominaException {
    public ValidacionException(String mensaje) {
        super(mensaje);
    }
}
