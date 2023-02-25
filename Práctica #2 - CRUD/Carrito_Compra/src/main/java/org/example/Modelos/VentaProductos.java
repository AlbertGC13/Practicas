package org.example.Modelos;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class VentaProductos {
    private long id;
    private Date fechaCompra;
    private String nombreCliente;
    private List<Producto> listaProductos;

    private float total;

    public VentaProductos(){}

    public VentaProductos(long id, Date fechaCompra, String nombreCliente, List<Producto> listaProductos) {
        this.id = id;
        this.fechaCompra = fechaCompra;
        this.nombreCliente = nombreCliente;
        this.listaProductos = listaProductos;
        float total = 0;
        for (int i =0; i < listaProductos.size(); i++){
            total += listaProductos.get(i).getTotal();
        }
        this.total = total;
    }

    public long getId() {
        return id;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
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
        VentaProductos that = (VentaProductos) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
