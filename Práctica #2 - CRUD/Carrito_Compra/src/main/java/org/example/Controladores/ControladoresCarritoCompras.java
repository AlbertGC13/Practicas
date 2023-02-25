package org.example.Controladores;

import org.example.Modelos.CarroCompras;
import org.example.Modelos.Producto;
import org.example.Servicios.ServiciosProducto;
import org.example.Utilidades.BaseControlador;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ControladoresCarritoCompras extends BaseControlador {

    ServiciosProducto serviciosProducto = ServiciosProducto.getInstancia();

    public ControladoresCarritoCompras (Javalin app){
        super (app);
    }

    @Override
    public void aplicarRutas() {
        app.routes(() -> {
            path("/Carrito/", () -> {
                post("/{id}", ctx->{
                    int id = ctx.pathParamAsClass("id" , Integer.class).get();
                    int cantidad = ctx.formParamAsClass("cantidad" , Integer.class).get();
                    Producto tmp = serviciosProducto.getProductoPorID(id);
                    CarroCompras carroCompras = ctx.sessionAttribute("carroCompras");

                    carroCompras.addProducto(new Producto(id, tmp.getNombre(), tmp.getPrecio(), cantidad > tmp.getCantidad() ? tmp.getCantidad() : cantidad));
                    ctx.sessionAttribute("carroCompras", carroCompras);
                    ctx.redirect("/Carrito");
                });
                post("/Borrar/{id}", ctx->{
                    int id = ctx.pathParamAsClass("id" , Integer.class).get();
                    CarroCompras carroCompras = ctx.sessionAttribute("carroCompras");
                    carroCompras.removeProducto(serviciosProducto.getProductoPorID(id));
                    ctx.sessionAttribute("carroCompras", carroCompras);
                    ctx.redirect("/Carrito");
                });
                get(ctx -> {
                    CarroCompras carroCompras = ctx.sessionAttribute("carroCompras");
                    List<Producto> lista = carroCompras.getListaProductos();
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Carrito de productos");
                    modelo.put("lista", lista);
                    modelo.put("monto_total", carroCompras.getMontoCarrito());

                    modelo.put("cantidad_carrito", carroCompras.getCantidadCarrito());
                    ctx.render("/templates/carrito.html", modelo);
                });
            });
        });
    }

}
