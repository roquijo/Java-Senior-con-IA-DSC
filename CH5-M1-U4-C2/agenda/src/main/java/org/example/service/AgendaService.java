package org.example.service;

import org.example.exceptions.DatosNoValidosException;
import org.example.exceptions.LlaveException;
import org.example.model.DatosContactoPersona;

import java.util.Map;
import java.util.TreeMap;

public class AgendaService {

    private final Map<String, DatosContactoPersona> contactos;

    public AgendaService() {
        this.contactos = new TreeMap<>();
    }

    public void agregarContacto(DatosContactoPersona contacto) {
        if (validarNombre(contacto.getNombre())) {
            if (!contactos.containsKey(contacto.getNombre().toLowerCase())) {
                contactos.put(contacto.getNombre().toLowerCase(), contacto);
            } else {
                throw new LlaveException("El contacto ya existe");
            }
        } else {
            throw new DatosNoValidosException("El nombre no puede ser nulo");
        }
    }


    public void actualizarContacto(DatosContactoPersona contacto) {
        if (validarNombre(contacto.getNombre())) {
            if (contactos.containsKey(contacto.getNombre().toLowerCase())) {
                contactos.put(contacto.getNombre().toLowerCase(), contacto);
            } else {
                throw new LlaveException("El contacto no existe");
            }
        } else {
            throw new DatosNoValidosException("El nombre no puede ser nulo");
        }
    }

    public void listarContactos() {
        for (String llave : contactos.keySet()) {
            System.out.println("Foreach " + contactos.get(llave));
        }
    }

    public void eliminarContacto(String nombre) {
        if (validarNombre(nombre)) {
            contactos.remove(nombre.toLowerCase());
        }
    }

    public boolean validarNombre(String nombre) {
        return nombre != null &&
                !nombre.isBlank();
    }
}
