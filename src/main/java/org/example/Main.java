package org.example;

import javax.swing.*;

import org.example.Productos.ProductosService;
import org.example.Usuarios.UsuarioService;
import org.example.Usuarios.VistaAdministrador;
import org.example.login.VistaLogin;

import java.awt.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                VistaLogin loginFrame = new VistaLogin();
                loginFrame.setVisible(true);
                UsuarioService usuarioService = new UsuarioService();
                ProductosService productosService = ProductosService.getInstance();
                VistaAdministrador vistaAdministrador = new VistaAdministrador(usuarioService, productosService);
                usuarioService.addObserver(vistaAdministrador.new TableObserver());

                System.out.println(usuarioService.getUsuarios());

                System.out.println(usuarioService.getUsuarios());
            }
        });
    }
}