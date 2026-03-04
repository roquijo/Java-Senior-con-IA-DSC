package org.example;


import org.example.model.DatosContactoPersona;
import org.example.service.AgendaService;

public class Main {

    public static void main(String[] args) {
        AgendaService service = new AgendaService();

        service.agregarContacto(new DatosContactoPersona("JORGE", "318", "roqui"));
        service.agregarContacto(new DatosContactoPersona("asdasd", "318", "roqui"));
//        service.agregarContacto(new DatosContactoPersona(null, "318", "roqui"));
//        service.agregarContacto(new DatosContactoPersona("", "318", "roqui"));
//        service.agregarContacto(new DatosContactoPersona("   ", "318", "roqui"));
//        service.agregarContacto(new DatosContactoPersona("jorge", "657685", "akjshdfas"));

        service.listarContactos();
    }
}