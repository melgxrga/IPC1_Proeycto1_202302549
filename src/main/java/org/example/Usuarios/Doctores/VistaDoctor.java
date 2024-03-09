package org.example.Usuarios.Doctores;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;

import org.example.Usuarios.Usuario;
import org.example.Usuarios.UsuarioService;
import org.example.Productos.*;

public class VistaDoctor extends JFrame {
    private Usuario doctorLogueado;
    private JTable table;
    private DefaultTableModel tableModel;
    private UsuarioService usuarioService;
    private ProductosService productosService;

    public VistaDoctor(UsuarioService usuarioService, ProductosService productosService) {
        this.doctorLogueado = UsuarioService.getUsuarioLogueado();
        this.usuarioService = usuarioService;
        this.productosService = productosService;

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel citasPanel = new JPanel();
        citasPanel.setLayout(new BorderLayout());

        DefaultTableModel citasTableModel = new DefaultTableModel();
        JTable citasTable = new JTable(citasTableModel);
        citasTableModel.addColumn("Número de Cita");
        citasTableModel.addColumn("Fecha");
        citasPanel.add(new JScrollPane(citasTable), BorderLayout.CENTER);

        tabbedPane.addTab("Citas", citasPanel);
        JPanel asignarHorarioPanel = new JPanel();
        asignarHorarioPanel.setLayout(new BorderLayout());

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        tableModel.addColumn("Número de Horario");
        tableModel.addColumn("Fecha");
        asignarHorarioPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        JPanel fechaPanel = new JPanel();
        JSpinner horarioSpinner = new JSpinner(new SpinnerDateModel());
        JButton asignarHorarioButton = new JButton("Asignar Horario");

        asignarHorarioButton.addActionListener(e -> {
            doctorLogueado = UsuarioService.getUsuarioLogueado();
            if (doctorLogueado == null) {
                JOptionPane.showMessageDialog(null, "No hay ningún doctor logueado", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Date horario = (Date) horarioSpinner.getValue();
            doctorLogueado.horarios.asignarHorario(horario);
            tableModel.addRow(new Object[] { tableModel.getRowCount() + 1, horario });
            String message = "El horario " + horario + " ha sido asignado al doctor " + this.doctorLogueado.getNombre();
            System.out.println(message);
            JOptionPane.showMessageDialog(null, message);
        });

        fechaPanel.add(horarioSpinner);
        fechaPanel.add(asignarHorarioButton);
        asignarHorarioPanel.add(fechaPanel, BorderLayout.NORTH);

        tabbedPane.addTab("Asignar Horario", asignarHorarioPanel);

        add(tabbedPane);
        setPreferredSize(new Dimension(1000, 600));
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}