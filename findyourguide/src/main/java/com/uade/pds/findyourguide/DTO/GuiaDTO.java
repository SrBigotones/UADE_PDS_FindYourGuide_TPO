
import java.util.List;

public class GuiaDTO {
    private long idUsuario;
    private List<CiudadPais> ubicaciones;
    private String nombre;
    private String apellido;
    private List<Idioma> idiomas;
    private List<ServicioGuiaDTO> servicios;
    private int puntuacion;


    public GuiaDTO(long idUsuario, List<CiudadPais> ubicaciones, String nombre, String apellido,
                List<Idioma> idiomas, List<ServicioGuiaDTO> servicios, int puntuacion) {
    this.idUsuario = idUsuario;
    this.ubicaciones = ubicaciones;
    this.nombre = nombre;
    this.apellido = apellido;
    this.idiomas = idiomas;
    this.servicios = servicios;
    this.puntuacion = puntuacion;
    }

    // Getters y setters
    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public List<CiudadPais> getUbicaciones() {
        return ubicaciones;
    }

    public void setUbicaciones(List<CiudadPais> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public List<Idioma> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<Idioma> idiomas) {
        this.idiomas = idiomas;
    }

    public List<ServicioGuiaDTO> getServicios() {
        return servicios;
    }

    public void setServicios(List<ServicioGuiaDTO> servicios) {
        this.servicios = servicios;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }
}

