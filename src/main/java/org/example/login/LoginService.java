package org.example.login;

import org.example.Productos.ProductosService;
import org.example.Usuarios.Usuario;
import org.example.Usuarios.UsuarioService;
import org.example.Usuarios.VistaAdministrador;
import org.example.Usuarios.Pacientes.VistaUsuario;
import org.example.Usuarios.Doctores.DoctorHorarios;
import org.example.Usuarios.Doctores.VistaDoctor;

class LoginService {
    private UsuarioService usuarioService;
    private ProductosService productosService;
    private DoctorHorarios doctorLogueado;
    public LoginService(UsuarioService usuarioService, ProductosService productosService, DoctorHorarios doctorLogueado) {
        this.usuarioService = usuarioService;
        this.productosService =  productosService;
        this.doctorLogueado = doctorLogueado;

    }

    public Usuario login(int id, String password) {
        for (Usuario usuario : usuarioService.getUsuarios()) {
            if (usuario.getId() == id && usuario.getPassword().equals(password)) {
                if (usuario.getRol() != null && usuario.getRol().equals("Usuario")) {
                    UsuarioService usuarioService = new UsuarioService();
                    usuarioService.cargarUsuarios();  // Carga los usuarios antes de crear la VistaUsuario
                    System.out.println(usuarioService.getUsuarios());  // Imprime los usuarios cargados
                    VistaUsuario vistaUsuario = new VistaUsuario(usuarioService, productosService); // Pasa productosService a VistaUsuario
                    vistaUsuario.setVisible(true);
                } else if( usuario.getRol() != null && usuario.getRol().equals("ADMIN")){
                    VistaAdministrador vistaAdministrador = new VistaAdministrador(usuarioService, productosService); // Pasa productosService a VistaAdministrador
                    vistaAdministrador.setVisible(true);
                }    else if( usuario.getRol() != null && usuario.getRol().equals("Doctor")){
                    VistaDoctor vistaDoctor = new VistaDoctor(usuarioService, productosService); 
                    vistaDoctor.setVisible(true);
                }

                UsuarioService.setUsuarioLogueado(usuario);

                System.out.println("El usuario " + usuario.getNombre() + " ha iniciado sesión");

                return usuario;
            }   
        }
        return null;
    }
}
