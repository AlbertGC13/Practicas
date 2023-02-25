package org.example.Modelos;

import org.example.Servicios.ServiciosProducto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CarroCompras {
    private long id;
    private List<Producto> listaProductos = new ArrayList<>();

    public CarroCompras() {}

    public CarroCompras(long id, List<Producto> listaProductos) {
        this.id = id;
        this.listaProductos = listaProductos;
    }

    public void addProducto(Producto producto) {
        if(listaProductos != null) {
            for (int i=0; i< listaProductos.size(); i++) {
                if(listaProductos.get(i).getId() == producto.getId()){
                    listaProductos.get(i).actualizarCantidad(listaProductos.get(i).getCantidad() + producto.getCantidad(),
                            ServiciosProducto.getInstancia().getProductoPorID(producto.getId()).getCantidad());
                    return;
                }
            }
        }
        listaProductos.add(producto);
    }

    public void removeProducto(Producto producto) {
        listaProductos.remove(producto);
    }

    public float geTotalCarrito() {
        float total = 0;
        for (int i = 0; i < listaProductos.size(); i++) {
            total += (listaProductos.get(i).getPrecio()).floatValue();
        }
        return total;
    }
    public float getMontoCarrito() {
        float total1=0,precio=0,cantidad=0,total2=0;
        for (int i = 0; i < listaProductos.size(); i++) {
            precio = (listaProductos.get(i).getPrecio()).floatValue();
            cantidad = (listaProductos.get(i).getCantidad());
            total1 += precio * cantidad;
        }


        return total1;
    }

    public int getCantidadCarrito() {
        int total = 0;
        for (int i = 0; i < listaProductos.size(); i++) {
            total += listaProductos.get(i).getCantidad();
        }
        return total;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarroCompras that = (CarroCompras) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
