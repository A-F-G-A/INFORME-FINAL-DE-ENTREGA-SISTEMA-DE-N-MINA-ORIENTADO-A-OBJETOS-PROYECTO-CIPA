package com.cipa.nomina;

import com.cipa.nomina.service.CalculadoraDeduccionesService;
import com.cipa.nomina.service.GestorBeneficiosService;
import com.cipa.nomina.service.ServicioNomina;
import com.cipa.nomina.ui.MenuConsola;
import com.cipa.nomina.validation.ValidadorNomina;

/**
 * Punto de entrada principal (Main) del Sistema de Nómina Orientado a Objetos - CIPA.
 * Configura la inyección de dependencias inicial y arranca la consola interactiva.
 */
public class Main {

    public static void main(String[] args) {
        // Inyección de dependencias (DIP): Invertimos el control suministrando las implementaciones
        CalculadoraDeduccionesService calculadoraDeducciones = new CalculadoraDeduccionesService();
        GestorBeneficiosService gestorBeneficios = new GestorBeneficiosService();
        ValidadorNomina validadorNomina = new ValidadorNomina();

        ServicioNomina servicioNomina = new ServicioNomina(
                calculadoraDeducciones,
                gestorBeneficios,
                validadorNomina
        );

        MenuConsola menu = new MenuConsola(servicioNomina);

        // Si se pasa el argumento "--demo", precarga datos para agilizar pruebas rápidas
        if (args.length > 0 && "--demo".equalsIgnoreCase(args[0])) {
            menu.cargarDatosDePrueba();
        }

        menu.iniciar();
    }
}
