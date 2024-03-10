package org.example.Usuarios.Doctores;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.example.Observer;
import org.example.Usuarios.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.example.Usuarios.*;

public class CrudDoctorVista extends JFrame {
    private UsuarioService usuarioService;

    public CrudDoctorVista(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        setLayout(new FlowLayout());
        setTitle("Crud Doctores");

        JButton registrarButton = new JButton("Registrar");
        registrarButton.addActionListener(e -> {
            VistaCreacionDoctor vistaCreacionDoctor = new VistaCreacionDoctor(usuarioService);
            vistaCreacionDoctor.setVisible(true);
            dispose();
        });

        JButton editarButton = new JButton("Editar");
        editarButton.addActionListener(e -> {
            EditarDoc editarDoc = new EditarDoc(usuarioService);
            editarDoc.setVisible(true);
            dispose();
        });

        JButton eliminarButton = new JButton("Eliminar");
        eliminarButton.addActionListener(e -> {
            EliminarDoc eliminarDoc = new EliminarDoc(usuarioService);
            eliminarDoc.setVisible(true);
            dispose();
        });

        JButton reporteButton = new JButton("Reporte");
        reporteButton.addActionListener(e -> {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            List<String> especialidades = usuarioService.obtenerEspecialidadesDisponibles();
            Map<String, Long> countBySpecialty = especialidades.stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            countBySpecialty.forEach((specialty, count) -> {
                dataset.addValue(count, "Cantidad", specialty);
            });

            JFreeChart barChart = ChartFactory.createBarChart(
                    "Cantidad de doctores por especialidad",
                    "Especialidad",
                    "Cantidad",
                    dataset,
                    PlotOrientation.VERTICAL,
                    false, true, false);

            barChart.getCategoryPlot().getRenderer().setSeriesPaint(0, Color.blue);

            ChartPanel chartPanel = new ChartPanel(barChart);
            chartPanel.setPreferredSize(new Dimension(560, 367));
            JFrame frame = new JFrame();
            frame.setContentPane(chartPanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setResizable(false); 
            frame.setVisible(true);
        });
        add(registrarButton);
        add(editarButton);
        add(eliminarButton);
        add(reporteButton);

        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
