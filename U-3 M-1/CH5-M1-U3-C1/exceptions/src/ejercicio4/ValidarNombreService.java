package ejercicio4;

public class ValidarNombreService {

    public static void validarNombre(String nombre) throws NombreInvException {
//        if (nombre == null) {
//            throw new NombreInvException("El nombre no puede ser nulo");
//        }
        if (nombre.isEmpty()) {
            throw new NombreInvException("El nombre no puede estar vacio");
        }
        if (nombre.length() < 3) {
            throw new NombreInvException("El nombre no puede tener menos de 3 caracteres");
        }
    }
}
