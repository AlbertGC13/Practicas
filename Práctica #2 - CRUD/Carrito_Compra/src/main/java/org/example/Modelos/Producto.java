package org.example.Modelos;
import java.math.BigDecimal;
import java.util.Objects;

public class Producto {

    private int id;
    private String nombre;
    private BigDecimal precio;

    private int cantidad;

    public Producto() {
    }

    public Producto(int id, String nombre, BigDecimal precio, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.precio=precio;
        this.cantidad=cantidad;
    }

    public void actualizar(Producto tmp){
        nombre = tmp.getNombre();
        precio = tmp.getPrecio();
        cantidad = tmp.getCantidad();
    }

    public void actualizarCantidad(int tmp, int limite) {
        if(tmp > limite) {
            cantidad = limite;
            return;
        }
        cantidad += tmp;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return id == producto.id;
    }

    public float getTotal() {
        return precio.floatValue()*cantidad;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
