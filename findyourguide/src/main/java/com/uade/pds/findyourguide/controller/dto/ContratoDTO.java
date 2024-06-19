
import java.util.Date;

public class ContratoDTO {
    private long idContrato;
    private Date fechaIni;
    private Date fechaFin;
    private double importe;
    private EstadoContrato estado;
    private ServicioGuiaDTO servicio;
    private UsuarioDTO usuarioContratante;
    private UsuarioDTO usuarioGuia;

    public ContratoDTO(long idContrato, Date fechaIni, Date fechaFin, double importe, EstadoContrato estado,
                       ServicioGuiaDTO servicio, UsuarioDTO usuarioContratante, UsuarioDTO usuarioGuia) {
        this.idContrato = idContrato;
        this.fechaIni = fechaIni;
        this.fechaFin = fechaFin;
        this.importe = importe;
        this.estado = estado;
        this.servicio = servicio;
        this.usuarioContratante = usuarioContratante;
        this.usuarioGuia = usuarioGuia;
    }

    // Getters y Setters
    public long getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(long idContrato) {
        this.idContrato = idContrato;
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public EstadoContrato getEstado() {
        return estado;
    }

    public void setEstado(EstadoContrato estado) {
        this.estado = estado;
    }

    public ServicioGuiaDTO getServicio() {
        return servicio;
    }

    public void setServicio(ServicioGuiaDTO servicio) {
        this.servicio = servicio;
    }

    public UsuarioDTO getUsuarioContratante() {
        return usuarioContratante;
    }

    public void setUsuarioContratante(UsuarioDTO usuarioContratante) {
        this.usuarioContratante = usuarioContratante;
    }

    public UsuarioDTO getUsuarioGuia() {
        return usuarioGuia;
    }

    public void setUsuarioGuia(UsuarioDTO usuarioGuia) {
        this.usuarioGuia = usuarioGuia;
    }
}
