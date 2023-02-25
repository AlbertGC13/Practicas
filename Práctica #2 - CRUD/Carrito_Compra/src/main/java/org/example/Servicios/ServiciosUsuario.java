package org.example.Servicios;


import org.example.Modelos.Usuario;


import java.util.ArrayList;
import java.util.List;

public class ServiciosUsuario {

    private static ServiciosUsuario instancia;
    private List<Usuario> listaUsuarios = new ArrayList<>();

    private ServiciosUsuario(){
        listaUsuarios.add(new Usuario("admin", "admin", "admin"));
    }

    public static ServiciosUsuario getInstancia(){
        if(instancia==null){
            instancia = new ServiciosUsuario();
        }
        return instancia;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public Usuario autenticarUsuario(String usuario, String password) {
        Usuario tmp = getUsuarioPorUsuario(usuario);
        if(tmp != null) {
            if(tmp.getPassword().equals(password)){
                System.out.println("Alerta: Inicio de seccion correcto");
                return tmp;
            }
        }
        System.out.println("Alerta: Inicio de seccion incorrecto");
        return null;
    }

    // CREAR
    public Usuario crearUsuario(Usuario usuario) {
        Usuario tmp = getUsuarioPorUsuario(usuario.getUsuario());
        if(tmp!=null) {
            System.out.println("Alerta: Usuario ya registrado");
            return null;
        }
        System.out.println("Alerta: Usuario registrado correctamente");
        listaUsuarios.add(usuario);
        return usuario;
    }

    // BORRAR
    public boolean borrarUsuario(String usuario) {
        Usuario tmp = getUsuarioPorUsuario(usuario);
        if(tmp==null) {
            System.out.println("Alerta: Usuario no existe");
            return false;
        }
        System.out.println("Alerta: Usuario eliminado correctamente");
        listaUsuarios.remove(tmp);
        return true;
    }

    // ACTUALIZAR
    public Usuario actualizarUsuario(Usuario usuario) {
        Usuario tmp = getUsuarioPorUsuario(usuario.getUsuario());
        if(tmp == null) {
            System.out.println("Alerta: Usuario no existe");
            return null;
        }
        System.out.println("Alerta: Usuario actualizado correctamente");
        tmp.actualizar(usuario);
        return usuario;
    }

    // ENLISTAR
    public Usuario getUsuarioPorUsuario(String usuario){
        return listaUsuarios.stream().filter(u -> u.getUsuario().equalsIgnoreCase(usuario)).findFirst().orElse(null);
    }
    public Usuario getUsuarioPorNombre(String nombre){
        return listaUsuarios.stream().filter(u -> u.getNombre().equalsIgnoreCase(nombre)).findFirst().orElse(null);
    }

}
