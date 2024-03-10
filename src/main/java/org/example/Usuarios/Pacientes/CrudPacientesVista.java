package org.example.Usuarios.Pacientes;

import javax.swing.*;
import java.awt.*;
import org.example.Observer;
import org.example.Usuarios.*;
import org.example.Usuarios.Doctores.EliminarDoc;

public class CrudPacientesVista extends JFrame implements Observer {
    private UsuarioService usuarioService;

    public CrudPacientesVista(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        usuarioService.registerObserver(this);
        setLayout(new FlowLayout());
        setTitle("Crud Pacientes");

        JButton registrarButton = new JButton("Registrar");
        registrarButton.addActionListener(e -> {
            vistaRegistro vistaRegistro = new vistaRegistro(usuarioService);
            vistaRegistro.setVisible(true);
            dispose();
        });

        JButton editarButton = new JButton("Editar");
        editarButton.addActionListener(e -> {
            EditarPaciente editarPaciente = new EditarPaciente(usuarioService);
            editarPaciente.setVisible(true);
            dispose();
        });

        JButton eliminarButton = new JButton("Eliminar");
        eliminarButton.addActionListener(e -> {
            EliminarPaciente eliminarPaciente = new EliminarPaciente(usuarioService);
            eliminarPaciente.setVisible(true);
            dispose();
        });



        add(registrarButton);
        add(editarButton);
        add(eliminarButton);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void update() {
    
    }
}