package org.example.Modelos;

import java.util.Objects;

public class Usuario {
    private String usuario;
    private String nombre;
    private String password;

    public Usuario() {
    }

    public Usuario(String usuario, String nombre, String password) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.password = password;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void actualizar(Usuario tmp){
        usuario = tmp.getUsuario();
        nombre = tmp.getNombre();
        password = tmp.getPassword();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(usuario, usuario.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario);
    }
}
