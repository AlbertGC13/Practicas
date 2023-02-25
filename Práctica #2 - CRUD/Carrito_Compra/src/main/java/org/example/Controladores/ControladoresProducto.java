package org.example.Controladores;

import org.example.Modelos.CarroCompras;
import org.example.Modelos.Producto;
import org.example.Servicios.ServiciosProducto;
import org.example.Utilidades.BaseControlador;
import io.javalin.Javalin;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ControladoresProducto extends BaseControlador {
    ServiciosProducto serviciosProducto = ServiciosProducto.getInstancia();

    public ControladoresProducto(Javalin app) {
        super(app);
    }
    @Override
    public void aplicarRutas() {
        app.routes(() -> {
            path("/", () -> {
                get("/", ctx -> {
                    String query = ctx.queryParam("busqueda");
                    List<Producto> lista = null;
                    if(query != null) {
                        lista = serviciosProducto.getProductoPorBusqueda(query).stream().filter(a -> a.getCantidad() > 0).toList();
                    } else {
                        lista = serviciosProducto.getListaProductos().stream().filter(a -> a.getCantidad() > 0).toList();
                    }
                    CarroCompras carroCompras = ctx.sessionAttribute("carroCompras");
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Listado de productos disponibles");
                    modelo.put("lista", lista);
                    modelo.put("cantidad_carrito", carroCompras.getCantidadCarrito());
                    ctx.render("/templates/enlistar-producto.html", modelo);
                });
            });
        });
        app.routes(() -> {
            path("/Seguridad/Productos", () -> {


                get("/", ctx -> {
                    String query = ctx.queryParam("busqueda");
                    List<Producto> lista = null;
                    if(query != null) {
                        lista = serviciosProducto.getProductoPorBusqueda(query);
                    } else {
                        lista = serviciosProducto.getListaProductos();
                    }
                    CarroCompras carroCompras = ctx.sessionAttribute("carroCompras");
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Administrar listado de productos disponibles");
                    modelo.put("cantidad_carrito", carroCompras.getCantidadCarrito());
                    modelo.put("lista", lista);
                    ctx.render("/templates/enlistar-producto-avanzado.html", modelo);
                });
                // FORMULARIO DE ACTUALIZACION
                get("/Actualizar/{id}", ctx -> {
                    Producto producto = serviciosProducto.getProductoPorID(ctx.pathParamAsClass("id", Integer.class).get());
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Actualizar - ".concat(producto.getNombre()));
                    modelo.put("nombre", producto.getNombre());
                    modelo.put("cantidad", producto.getCantidad());
                    modelo.put("action", ("/Seguridad/Productos/Actualizar/".concat(String.valueOf(producto.getId()))));
                    modelo.put("precio", producto.getPrecio());
                    CarroCompras carroCompras = ctx.sessionAttribute("carroCompras");
                    modelo.put("cantidad_carrito", carroCompras.getCantidadCarrito());
                    ctx.render("/templates/actualizar-producto.html", modelo);
                });
                // ACTUALIZAR PRODUCTO
                post("/Actualizar/{id}", ctx -> {

                    int id = ctx.pathParamAsClass("id" , Integer.class).get();
                    String nombre = ctx.formParam("nombre");
                    int precio = ctx.formParamAsClass("precio" , Integer.class).get();
                    int cantidad = ctx.formParamAsClass("cantidad" , Integer.class).get();

                    Producto tmp = new Producto(id, nombre, new BigDecimal(precio), cantidad);
                    serviciosProducto.actualizarProducto(tmp);
                    ctx.redirect("/Seguridad/Productos");
                });
                // FORMULARIO DE CREACION
                get("/Crear/", ctx -> {
                    Map<String, Object> modelo = new HashMap<>();
                    CarroCompras carroCompras = ctx.sessionAttribute("carroCompras");
                    modelo.put("cantidad_carrito", carroCompras.getCantidadCarrito());
                    ctx.render("/templates/crear-producto.html", modelo);
                });
                // CREAR PRODUCTO
                post("/Crear/", ctx -> {
                    String nombre = ctx.formParam("nombre");
                    BigDecimal precio = new BigDecimal(ctx.formParam("precio"));
                    int cantidad = ctx.formParamAsClass("cantidad" , Integer.class).get();
                    Producto tmp = new Producto(serviciosProducto.getListaProductos().size()+1, nombre, precio, cantidad);
                    serviciosProducto.crearProducto(tmp);
                    ctx.redirect("/Seguridad/Productos");
                });
                // VER PRODUCTOS
                get(ctx -> {
                    List<Producto> lista = serviciosProducto.getListaProductos();
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Listado de Productos disponibles");
                    modelo.put("lista", lista);
                    CarroCompras carroCompras = ctx.sessionAttribute("carroCompras");
                    modelo.put("cantidad_carrito", carroCompras.getCantidadCarrito());
                    ctx.render("/templates/enlistar-producto.html", modelo);
                });
                // ELIMINAR PRODUCTO
                post("/Eliminar/{id}", ctx -> {
                    int id = ctx.pathParamAsClass("id" , Integer.class).get();
                    serviciosProducto.borrarProducto(id);
                    ctx.redirect("/Seguridad/Productos");
                });
            });
        });
    }
}
