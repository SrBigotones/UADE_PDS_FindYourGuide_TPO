public class UsuarioDTO {
    private long id;
    private String nombre;
    private String apellido;
    private String sexo;
    private String dni;
    private String email;
    private String numTelefono;
    private String imgPerfil;

    // Constructor que inicializa todos los campos
    public UsuarioDTO(long id, String nombre, String apellido, String sexo, String dni, 
                      String email, String numTelefono, String imgPerfil) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.dni = dni;
        this.email = email;
        this.numTelefono = numTelefono;
        this.imgPerfil = imgPerfil;
    }

    // Getters
    public long getId() {
        return id;
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

    public String getEmail() {
        return email;
    }

    public String getNumTelefono() {
        return numTelefono;
    }

    public String getImgPerfil() {
        return imgPerfil;
    }

    // Setters
    public void setId(long id) {
        this.id = id;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumTelefono(String numTelefono) {
        this.numTelefono = numTelefono;
    }

    public void setImgPerfil(String imgPerfil) {
        this.imgPerfil = imgPerfil;
    }
}
