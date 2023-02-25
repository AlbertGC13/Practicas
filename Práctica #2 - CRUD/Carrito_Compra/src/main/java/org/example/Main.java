package org.example;

import org.example.Controladores.ControladoresCarritoCompras;
import org.example.Controladores.ControladoresProducto;
import org.example.Controladores.ControladoresSeguridad;
import org.example.Controladores.ControladoresVentasProductos;
import org.example.Modelos.CarroCompras;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

import static io.javalin.apibuilder.ApiBuilder.get;

public class Main {

    public static void main(String[] args) {

        System.out.println("Aplicacion iniciada...");

        Javalin app = Javalin.create(config ->{
            config.addStaticFiles(staticFileConfig -> {
                staticFileConfig.hostedPath = "/";
                staticFileConfig.directory = "/publico";
                staticFileConfig.location = Location.CLASSPATH;
            });
        });

        new ControladoresSeguridad(app).aplicarRutas();
        new ControladoresProducto(app).aplicarRutas();
        new ControladoresCarritoCompras(app).aplicarRutas();
        new ControladoresVentasProductos(app).aplicarRutas();


        app.start(8080);

    }
}
