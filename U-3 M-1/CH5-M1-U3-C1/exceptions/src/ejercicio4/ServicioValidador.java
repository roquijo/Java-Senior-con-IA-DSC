package ejercicio4;

import java.util.ArrayList;
import java.util.List;

public class ServicioValidador {

    public List<Exception> validarFormulario(Formulario formulario) {
        List<Exception> errors = new ArrayList<>();
        try {
            ValidarNombreService.validarNombre(formulario.getNombre());
        } catch (NombreInvException e) {
            errors.add(e);
        }
        try {
            validarTelefono(formulario.getTelefono());
        } catch (TelefonoInvException e) {
            errors.add(e);
        }
        try {
            validarCodigoPostal(formulario.getCodigoPostal());
        } catch (CodigoPostalnvException e) {
            errors.add(e);
        }
        return errors;
    }

    public void validarTelefono(String telefono) throws TelefonoInvException {
        if (telefono == null) {
            throw new TelefonoInvException("El telefono no puede ser nulo");
        }
        if (telefono.isEmpty()) {
            throw new TelefonoInvException("El telefono no puede estar vacio");
        }
        if (telefono.length() != 10) {
            throw new TelefonoInvException("El telefono debe tener 10 caracteres");
        }
    }

    public void validarCodigoPostal(String code) throws CodigoPostalnvException {
        if (code == null) {
            throw new CodigoPostalnvException("El codigo no puede ser nulo");
        }
        if (code.isBlank()) {
            throw new CodigoPostalnvException("El codigo no puede estar en blanco");
        }
        if (code.isEmpty()) {
            throw new CodigoPostalnvException("El codigo no puede estar vacio");
        }
        if (code.length() != 5) {
            throw new CodigoPostalnvException("El codigo debe tener 5 caracteres");
        }
    }
}