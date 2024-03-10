package org.example.Usuarios.Doctores;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;
import java.util.List;
import org.example.Usuarios.Usuario;
import org.example.Usuarios.UsuarioService;
import org.example.Usuarios.Pacientes.EditarPropioPerfil;
import org.example.Productos.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        JButton editarDoctorButton = new JButton("Editar Doctor");
        editarDoctorButton.addActionListener(e -> {
            this.doctorLogueado = UsuarioService.getUsuarioLogueado();
            if (doctorLogueado != null) {
                EditarPropioPerfil editarPropioPerfil = new EditarPropioPerfil(usuarioService);
                editarPropioPerfil.setNombreField(doctorLogueado.getNombre());
                editarPropioPerfil.setApellidoField(doctorLogueado.getApellidos());
                editarPropioPerfil.setPasswordField(doctorLogueado.getPassword());
                editarPropioPerfil.setEdadField(doctorLogueado.getEdad());
                editarPropioPerfil.setGeneroBox(doctorLogueado.getGenero());
                editarPropioPerfil.setVisible(true);
            } else {
            }
        });
        JButton closeButton = new JButton("X");
        closeButton.addActionListener(e -> dispose());
    
        JPanel titleBar = new JPanel();
    
        titleBar.add(editarDoctorButton);
        titleBar.add(closeButton);
    
        setUndecorated(true);
        getContentPane().add(titleBar, BorderLayout.NORTH);

        MouseAdapter mouseAdapter = new MouseAdapter() {
            private Point origin;
    
            @Override
            public void mousePressed(MouseEvent e) {
                origin = new Point(e.getPoint());
            }
    
            @Override
            public void mouseDragged(MouseEvent e) {
                if (origin != null) {
                    Point p = getLocation();
                    setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
                }
            }
    
            @Override
            public void mouseReleased(MouseEvent e) {
                origin = null;
            }
        };
        titleBar.addMouseListener(mouseAdapter);
        titleBar.addMouseMotionListener(mouseAdapter);
        titleBar.addMouseListener(mouseAdapter);
        titleBar.addMouseMotionListener(mouseAdapter);
    
        JPanel solicitarCitaPanel = new JPanel();
        solicitarCitaPanel.setLayout(new GridBagLayout());
    
        JLabel motivoCitaLabel = new JLabel("Motivo de la cita:");
    
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.weightx = 1.0;
        c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 0);
        solicitarCitaPanel.add(motivoCitaLabel, c);
    
        // asignarHorarioButton.addActionListener(e -> {
        //     doctorLogueado = UsuarioService.getUsuarioLogueado();
        //     if (doctorLogueado == null) {
        //         JOptionPane.showMessageDialog(null, "No hay ningún doctor logueado", "Error",
        //                 JOptionPane.ERROR_MESSAGE);
        //         return;
        //     }
    
        //     Date horario = (Date) horarioSpinner.getValue();
        //     doctorLogueado.horarios.asignarHorario(horario);
        //     tableModel.addRow(new Object[] { tableModel.getRowCount() + 1, horario });
        //     String message = "El horario " + horario + " ha sido asignado al doctor " +this.doctorLogueado.getNombre();
        //     System.out.println(message);
        //     JOptionPane.showMessageDialog(null, message);
        // });
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
            System.out.println("Lista de horarios después de asignar el nuevo horario: " + doctorLogueado.getHorarios());
            usuarioService.guardarUsuario(doctorLogueado);
            usuarioService.guardarUsuarios();
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