package org.example.Controladores;

import org.example.Modelos.CarroCompras;
import org.example.Modelos.VentaProductos;
import org.example.Servicios.ServiciosVentasProductos;
import org.example.Utilidades.BaseControlador;
import io.javalin.Javalin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ControladoresVentasProductos  extends BaseControlador {

    ServiciosVentasProductos serviciosVentasProductos = ServiciosVentasProductos.getInstancia();

    public ControladoresVentasProductos (Javalin app){
        super (app);
    }

    @Override
    public void aplicarRutas() {
        app.routes(() -> {
            path("/Ventas", () -> {
                post(ctx -> {
                    String nombre = ctx.formParam("nombre");
                    CarroCompras carroCompras = ctx.sessionAttribute("carroCompras");
                    VentaProductos ventaProductos = new VentaProductos(serviciosVentasProductos.getListaVentasProductos().size(), new Date(), nombre, carroCompras.getListaProductos());
                    serviciosVentasProductos.crearVentaProducto(ventaProductos);
                    ctx.sessionAttribute("carroCompras", new CarroCompras());
                    ctx.redirect("/");
                });
            });
        });

        app.routes(() -> {
            path("Seguridad/Pedidos", () -> {
                get(ctx -> {
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Listado de Productos disponibles");
                    modelo.put("lista", serviciosVentasProductos.getListaVentasProductos());
                    CarroCompras carroCompras = ctx.sessionAttribute("carroCompras");
                    modelo.put("cantidad_carrito", carroCompras.getCantidadCarrito());
                    ctx.render("/templates/ventas.html", modelo);
                });
            });
        });


    }

}
