package com.taller;

import com.taller.enums.Categoria;
import com.taller.model.Articulo;
import com.taller.service.ArticuloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Punto de entrada. Implementar en clase: modelo, excepciones, logging,
 * estructuras de datos y Streams según el enunciado del taller.
 */
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("Aplicación iniciada - implementar lógica según el taller");
        ArticuloService service = new ArticuloService();

        try {
            service.crearArticulo(new Articulo(1, "Celular", Categoria.TECNOLOGIA, 200.0));
            service.crearArticulo(new Articulo(2, "Celphone", Categoria.HOGAR, 200.0));
            service.crearArticulo(new Articulo(3, "Escritorio", Categoria.HOGAR, 200.0));

//            log.info(service.getArticulos().toString());
//
//
//            Articulo articuloBuscadoPorId = service.buscarArticuloPorId(1);
//            log.info(articuloBuscadoPorId.toString());
//
//
//            log.info(service.buscarArticuloPorNombreExacto("prueba").toString());
//            log.info(service.buscarArticuloPorNombre("prueba").toString());
            log.info(service.buscarArticuloPorCategoria(Categoria.TECNOLOGIA).toString());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.info("Aplicación finalizada");
    }
}
