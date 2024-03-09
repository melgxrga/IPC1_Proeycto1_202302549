package org.example.Productos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.example.Observer;

public class ProductosService {
    private List<Productos> productos;
    private static Random random = new Random();
    private List<Observer> observers = new ArrayList<>();
    private static ProductosService instance;

    private ProductosService() {
        // constructor privado
        this.productos = new ArrayList<>();
    }

    public static ProductosService getInstance() {
        if (instance == null) {
            instance = new ProductosService();
        }
        return instance;
    }
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public Productos buscarPorId(String id) {
        List<Productos> productos = getProductos();
        for (Productos producto : productos) {
            if (productos != null && String.valueOf(producto.getId()).equals(id)) {
                return producto;
            }
        }
        return null;
    }

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public static Productos generaProductos(String nombreProducto, int precioProducto, String descripcionProducto,
            int cantidadProducto) {
        Productos producto = new Productos();
        producto.setId(generarId());
        producto.setNombreProducto(nombreProducto);
        producto.setPrecioProducto(precioProducto);
        producto.setDescripcionProducto(descripcionProducto);
        producto.setCantidadProducto(cantidadProducto);
        return producto;
    }

    public void addProducto(Productos producto) {
        productos.add(producto);
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public List<Productos> getProductos() {
        // Verifica si productos es null y, de ser así, lo inicializa
        if (productos == null) {
            productos = new ArrayList<>();
        }
    
        for (Productos producto : productos) {
            System.out.println(
                    "Id  " + producto.getId() +
                            "Nombre: " + producto.getNombreProducto() +
                            ", Precio: " + producto.getPrecioProducto() +
                            ", Descripcion: " + producto.getDescripcionProducto() +
                            ", Cantidad: " + producto.getCantidadProducto());
        }
        return productos;
    }
    public void editarProducto(Productos productoActualizado) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getId() == (productoActualizado.getId())) {
                productos.set(i, productoActualizado);
                break;
            }
        }
    }
    
    public void eliminarProducto(Productos producto) {
        productos.removeIf(p -> p.getId() ==(producto.getId()));
        // notifyObservers();
    }
    public static int generarId() {
            return 1 + random.nextInt(999);
    }
}
