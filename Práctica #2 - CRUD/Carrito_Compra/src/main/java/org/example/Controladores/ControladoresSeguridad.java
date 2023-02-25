package org.example.Controladores;



import org.example.Modelos.CarroCompras;
import org.example.Modelos.Usuario;
import org.example.Servicios.ServiciosUsuario;
import org.example.Utilidades.BaseControlador;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ControladoresSeguridad extends BaseControlador {

    ServiciosUsuario serviciosUsuario = ServiciosUsuario.getInstancia();

    public ControladoresSeguridad(Javalin app) {
        super(app);
    }
    @Override
    public void aplicarRutas() {

        app.routes(() -> {
            path("/", () -> {
                before(ctx -> {
                    CarroCompras carroCompras = ctx.sessionAttribute("carroCompras");
                    if(carroCompras == null) {
                        ctx.sessionAttribute("carroCompras", new CarroCompras());
                    }
                    System.out.println(String.format("Alerta: Ingresando a la ruta: %s, Metodo: %s", ctx.req.getPathInfo(),  ctx.req.getMethod()));
                });
            });
            path("/Login", () -> {
                get(ctx -> {
                    Usuario usuario = ctx.sessionAttribute("usuario");
                    if(usuario != null){
                        ctx.redirect("/Seguridad/Productos");
                    }// usuario no existe
                    Map<String, Object> modelo = new HashMap<>();
                    CarroCompras carroCompras = ctx.sessionAttribute("carroCompras");
                    modelo.put("cantidad_carrito", carroCompras.getCantidadCarrito());
                    ctx.render("/templates/login.html");
                });
            });
        });
        app.routes(() -> {
            path("/Seguridad/", () -> {
                before(ctx -> {
                    Usuario usuario = ctx.sessionAttribute("usuario");
                    if(usuario == null){// usuario no existe
                        ctx.redirect("/Login");
                    }
                });
                post(ctx -> {
                    String nombreUsuario = ctx.formParam("usuario");
                    String password = ctx.formParam("password");
                    Usuario usuario = serviciosUsuario.getInstancia().autenticarUsuario(nombreUsuario, password);
                    if(usuario != null) {
                        ctx.sessionAttribute("usuario", usuario);
                        ctx.redirect("/Seguridad/Productos");
                    } else {
                        ctx.redirect("/Login");
                    }
                });
            });
        });
    }
}
