package org.example.Usuarios.Doctores;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.example.Usuarios.Usuario;
import org.example.Usuarios.UsuarioService;

import java.awt.*;
import java.util.stream.Collectors;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditarDoc extends JFrame {
    private JTextField idField;
    private JTextField nombreField;
    private JTextField apellidoField;
    private JPasswordField passwordField;
    private JFormattedTextField edadField;
    private UsuarioService usuarioService;
    private JComboBox<String> generoBox;
    private JButton editarButton;
    private JTextField telefonoField;
    private JTextField especialidadField;

    public EditarDoc(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel idLabel = new JLabel("ID");
        idField = new JTextField(10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(idLabel, gbc);
        gbc.gridx = 1;
        panel.add(idField, gbc);

        JLabel nombreLabel = new JLabel("Nombre");
        nombreField = new JTextField(10);
        gbc.gridx = 0;
        gbc.gridy = 1; 
        panel.add(nombreLabel, gbc);
        gbc.gridx = 1;
        panel.add(nombreField, gbc);

        JLabel apellidoLabel = new JLabel("Apellido");
        apellidoField = new JTextField(10);
        gbc.gridx = 0;
        gbc.gridy = 2; 
        panel.add(apellidoLabel, gbc);
        gbc.gridx = 1;
        panel.add(apellidoField, gbc);

        JLabel passwordLabel = new JLabel("Password");
        passwordField = new JPasswordField(10);
        gbc.gridx = 0;
        gbc.gridy = 3; 
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        JLabel edadLabel = new JLabel("Edad");
        edadField = new JFormattedTextField();
        edadField.setColumns(10);
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(edadLabel, gbc);
        gbc.gridx = 1;
        panel.add(edadField, gbc);

        JLabel generoLabel = new JLabel("Género");
        generoBox = new JComboBox<>();
        generoBox.addItem("Masculino");
        generoBox.addItem("Femenino");
        gbc.gridx = 0;
        gbc.gridy = 5; 
        panel.add(generoLabel, gbc);
        gbc.gridx = 1;
        panel.add(generoBox, gbc);

        JLabel telefonoLabel = new JLabel("Telefono");
        telefonoField = new JTextField(10);
        gbc.gridx = 0;
        gbc.gridy = 6; 
        panel.add(telefonoLabel, gbc);
        gbc.gridx = 1;
        panel.add(telefonoField, gbc);

        JLabel especialidadLabel = new JLabel("Especialidad Doctor");
        especialidadField = new JTextField(10);
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(especialidadLabel, gbc);
        gbc.gridx = 1;
        panel.add(especialidadField, gbc);

        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton buscarButton = new JButton("Buscar");
        buttonPanel.add(buscarButton);

        add(buttonPanel, BorderLayout.SOUTH); 
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                String id = idField.getText();
                Usuario usuario = usuarioService.buscarPorId(id);
                if (usuario != null) {
                    idField.setEditable(false);  
                    nombreField.setText(usuario.getNombre());
                    apellidoField.setText(usuario.getApellidos());
                    passwordField.setText(usuario.getPassword());
                    edadField.setText(String.valueOf(usuario.getEdad()));
                    generoBox.setSelectedItem(usuario.getGenero());
                    telefonoField.setText(usuario.getNumeroDoctor());
                    especialidadField.setText(usuario.getEspecialidadDoctor());
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró un usuario con el ID proporcionado");
                }
            }
        });
        editarButton = new JButton("Editar");
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(editarButton);
        add(buttonPanel, BorderLayout.SOUTH);
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                Usuario usuario = usuarioService.buscarPorId(id);
                if (usuario != null) {
                    usuario.setNombre(nombreField.getText());
                    usuario.setApellidos(apellidoField.getText());
                    usuario.setPassword(new String(passwordField.getPassword()));
                    usuario.setEdad(Integer.parseInt(edadField.getText()));
                    usuario.setGenero((String) generoBox.getSelectedItem());
                    usuario.setNumeroDoctor(telefonoField.getText());
                    usuario.setEspecialidadDoctor(especialidadField.getText());
                    usuarioService.editarUsuario(usuario);
                    JOptionPane.showMessageDialog(null, "Usuario actualizado con éxito");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró un usuario con el ID proporcionado");
                }
            }
        });


        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
