package com.cipa.nomina.exception;

/**
 * Excepción base para errores de reglas de negocio o validaciones en el sistema de nómina.
 */
public class NominaException extends RuntimeException {
    public NominaException(String mensaje) {
        super(mensaje);
    }

    public NominaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
