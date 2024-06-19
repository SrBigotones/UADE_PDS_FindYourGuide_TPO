
public class RegistroDTO {
    private String email;
    private String password;
    private String apiServicio;
    private TipoUsuario tipoUsuario;
    private String acreditacion;
    private String imgCredencial;
    private String nombre;
    private String apellido;
    private String sexo;
    private String dni;
    private String numTelefono;
    private EstrategiaRegistro tipoRegistro;

    
    public RegistroDTO(String email, String password, String apiServicio, TipoUsuario tipoUsuario,
                       String acreditacion, String imgCredencial, String nombre, String apellido,
                       String sexo, String dni, String numTelefono, EstrategiaRegistro tipoRegistro) {
        this.email = email;
        this.password = password;
        this.apiServicio = apiServicio;
        this.tipoUsuario = tipoUsuario;
        this.acreditacion = acreditacion;
        this.imgCredencial = imgCredencial;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.dni = dni;
        this.numTelefono = numTelefono;
        this.tipoRegistro = tipoRegistro;
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getApiServicio() {
        return apiServicio;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public String getAcreditacion() {
        return acreditacion;
    }

    public String getImgCredencial() {
        return imgCredencial;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getSexo() {
        return sexo;
    }

    public String getDni() {
        return dni;
    }

    public String getNumTelefono() {
        return numTelefono;
    }

    public EstrategiaRegistro getTipoRegistro() {
        return tipoRegistro;
    }

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setApiServicio(String apiServicio) {
        this.apiServicio = apiServicio;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public void setAcreditacion(String acreditacion) {
        this.acreditacion = acreditacion;
    }

    public void setImgCredencial(String imgCredencial) {
        this.imgCredencial = imgCredencial;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setNumTelefono(String numTelefono) {
        this.numTelefono = numTelefono;
    }

    public void setTipoRegistro(EstrategiaRegistro tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }
}
