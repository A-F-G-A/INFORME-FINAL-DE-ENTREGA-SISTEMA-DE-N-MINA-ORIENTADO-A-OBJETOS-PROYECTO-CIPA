package com.cipa.nomina.exception;

/**
 * Excepción lanzada cuando el cálculo produce un salario neto negativo o incoherente.
 */
public class SalarioInvalidoException extends NominaException {
    public SalarioInvalidoException(String mensaje) {
        super(mensaje);
    }
}
