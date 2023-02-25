package org.example.Servicios;

import org.example.Modelos.Producto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class ServiciosProducto {

    private static ServiciosProducto instancia;
    private List<Producto> listaProductos = new ArrayList<>();

    private ServiciosProducto(){
        listaProductos.add(new Producto(1, "Iphone 13 ", new BigDecimal(1500), 5));
        listaProductos.add(new Producto(2, "Airpods serie 2 ", new BigDecimal(500), 15));
        listaProductos.add(new Producto(3, "Apple Watch serie 2 32mm", new BigDecimal(700), 10));
        listaProductos.add(new Producto(4, "Cargador C-C", new BigDecimal(100), 50));
    }

    public static ServiciosProducto getInstancia(){
        if(instancia==null){
            instancia = new ServiciosProducto();
        }
        return instancia;
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    // CREAR
    public Producto crearProducto(Producto producto) {
        Producto tmp = getProductoPorID(producto.getId());
        if(tmp!=null) {
            System.out.println("Alerta: Producto ya registrado");
            return null;
        }
        System.out.println("Alerta: Producto registrado correctamente");
        listaProductos.add(producto);
        return producto;
    }

    // BORRAR
    public boolean borrarProducto(int id) {
        Producto tmp = getProductoPorID(id);
        if(tmp==null) {
            System.out.println("Alerta: Producto no existe");
            return false;
        }
        System.out.println("Alerta: Producto eliminado correctamente");
        listaProductos.remove(tmp);
        return true;
    }

    // ACTUALIZAR
    public Producto actualizarProducto(Producto producto) {
        Producto tmp = getProductoPorID(producto.getId());
        if(tmp == null) {
            System.out.println("Alerta: Producto no existe");
            return null;
        }
        System.out.println("Alerta: Producto actualizado correctamente");
        tmp.actualizar(producto);
        return producto;
    }

    public void cambiarCantidad(int id, int cantidad) {
        Producto tmp = getProductoPorID(id);
        if(tmp == null) {
            System.out.println("Alerta: Producto no existe");
        } else {
            System.out.println("Alerta: Producto actualizado correctamente");
            tmp.actualizarCantidad(cantidad, tmp.getCantidad());
        }
    }

    // ENLISTAR
    public Producto getProductoPorID(int id) {
        return listaProductos.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }
    public Producto getProductoPorNombre(String nombre) {
        return listaProductos.stream().filter(p -> p.getNombre() == nombre).findFirst().orElse(null);
    }

    public List<Producto> getProductoPorBusqueda(String palabra) {
        List<Producto> lista = listaProductos.stream().filter(p -> (p.getNombre().toLowerCase()).contains(palabra.toLowerCase())).toList();
        return lista;
    }

}
