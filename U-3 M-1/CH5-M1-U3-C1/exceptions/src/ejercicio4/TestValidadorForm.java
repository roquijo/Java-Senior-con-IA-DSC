package ejercicio4;

import java.util.List;

public class TestValidadorForm {

    public static void main(String[] args) {
        Formulario formulario = new Formulario(null, "12", "12");
        ServicioValidador servicioValidador = new ServicioValidador();
        List<Exception> errors = servicioValidador.validarFormulario(formulario);
        errors.forEach(err -> System.out.println(err.getMessage()));
    }
}