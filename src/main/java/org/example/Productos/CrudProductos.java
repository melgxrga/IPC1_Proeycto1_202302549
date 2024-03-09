package org.example.Productos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.stream.Collectors;
import java.util.List;
import org.example.Observer;
import org.example.Usuarios.*;
import org.example.Usuarios.Doctores.EditarDoc;
import org.example.Usuarios.Doctores.EliminarDoc;
import org.example.Usuarios.Doctores.VistaCreacionDoctor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class CrudProductos extends JFrame {
    private ProductosService productosService;
    private UsuarioService usuarioService;

    public CrudProductos(ProductosService productosService) {
        this.productosService = productosService;
        this.productosService = productosService;
        this.usuarioService = new UsuarioService();
        setLayout(new FlowLayout());
        setTitle("Crud productos");

        JButton registrarButton = new JButton("Registrar");
        registrarButton.addActionListener(e -> {
            final ProductosService finalProductosService = (productosService != null) ? productosService
                    : ProductosService.getInstance();

            VistaCreacionProductos vistaCreacionProductos = new VistaCreacionProductos(finalProductosService);
            vistaCreacionProductos.setVisible(true);

            finalProductosService.notifyObservers();
            dispose();
        });

        JButton editarButton = new JButton("Editar");
        editarButton.addActionListener(e -> {
            final ProductosService finalProductosService = (productosService != null) ? productosService
                    : ProductosService.getInstance();
            EditarProductos editarProductos = new EditarProductos(finalProductosService);

            editarProductos.setVisible(true);

            finalProductosService.notifyObservers();
            dispose();
        });

        JButton eliminarButton = new JButton("Eliminar");
        eliminarButton.addActionListener(e -> {
            
            final ProductosService finalProductosService = (productosService != null) ? productosService
                    : ProductosService.getInstance();
            EliminarProductos eliminarProductos = new EliminarProductos(finalProductosService);

            eliminarProductos.setVisible(true);

            finalProductosService.notifyObservers();
            dispose();
        });
        JButton reporteButton = new JButton("Reporte");
        reporteButton.addActionListener(e -> {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            List<Productos> productos = productosService.getProductos();
            for (Productos producto : productos) {
                dataset.addValue(producto.getCantidadProducto(), "Cantidad", producto.getNombreProducto());
            }
            JFreeChart barChart = ChartFactory.createBarChart(
                    "Cantidad de productos",
                    "Producto",
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
