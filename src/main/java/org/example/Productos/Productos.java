package org.example.Productos;

public class Productos {
    private int id;
    private String nombreProducto;
    private int precioProducto;
    private String descripcionProducto;
    private int cantidadProducto;


    public Productos() {
    }

    public Productos(int id, String nombreProducto, int precioProducto,String descripcionProducto, int cantidadProducto, String rol) {
        this.id = id;
        this.nombreProducto = nombreProducto;
        this.precioProducto = precioProducto;
        this.descripcionProducto = descripcionProducto;
        this.cantidadProducto = cantidadProducto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }
 

    public int getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(int precioProducto) {
        this.precioProducto = precioProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    @Override
    public String toString() {
        return "Usuario{" +
            "id=" + id +
            ", nombre='" + nombreProducto + '\'' +
            ", apellidos='" + precioProducto + '\'' +
            ", password='" + descripcionProducto + '\'' +
            ", edad=" + cantidadProducto
        ;
    }
}
