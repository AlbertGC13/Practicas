package org.example.Servicios;


import org.example.Modelos.VentaProductos;

import java.util.ArrayList;
import java.util.List;

public class ServiciosVentasProductos {

    private static ServiciosVentasProductos instancia;
    private static ServiciosProducto serviciosProducto = ServiciosProducto.getInstancia();
    private List<VentaProductos> listaVentasProductos = new ArrayList<>();

    private ServiciosVentasProductos() {}

    public static ServiciosVentasProductos getInstancia(){
        if(instancia==null){
            instancia = new ServiciosVentasProductos();
        }
        return instancia;
    }

    public List<VentaProductos> getListaVentasProductos() {
        return listaVentasProductos;
    }

    public void setListaVentasProductos(List<VentaProductos> listaVentasProductos) {
        this.listaVentasProductos = listaVentasProductos;
    }


    // CREAR
    public VentaProductos crearVentaProducto(VentaProductos ventaProductos) {
        VentaProductos tmp = getVentaProductoPorID(ventaProductos.getId());
        if(tmp!=null) {
            System.out.println("Alerta: Venta de productos ya registrado");
            return null;
        }
        System.out.println("Alerta: Venta de productos registrado correctamente");
        listaVentasProductos.add(ventaProductos);
        for(int i=0; i<ventaProductos.getListaProductos().size(); i++){
            serviciosProducto.cambiarCantidad(ventaProductos.getListaProductos().get(i).getId(), ventaProductos.getListaProductos().get(i).getCantidad()*-1);
        }
        return ventaProductos;
    }

    // ENLISTAR
    public VentaProductos getVentaProductoPorID(long id){
        return listaVentasProductos.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }
}
