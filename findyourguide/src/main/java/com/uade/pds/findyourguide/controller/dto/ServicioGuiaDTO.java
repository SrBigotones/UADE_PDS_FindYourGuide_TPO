
public class ServicioGuiaDTO {
    private double precio;
    private String nombre;
    private String descripcion;
    private TipoServicio tipoServicio;

    public ServicioGuiaDTO(double precio, String nombre, String descripcion, TipoServicio tipoServicio) {
        this.precio = precio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipoServicio = tipoServicio;
    }

    // Getters y Setters
    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }
}

