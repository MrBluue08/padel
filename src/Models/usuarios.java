package Models;

public class usuarios {
    String dni;
    String mail;
    String nombre;
    String apellidos;
    String passwd;

    public usuarios(String dni) {
        this.dni = dni;
    }

    public usuarios(String dni, String mail, String nombre, String apellidos, String passwd) {
        this.dni = dni;
        this.mail = mail;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.passwd = passwd;
    }

    public usuarios(String dni, String mail, String nombre, String apellidos) {
        this.dni = dni;
        this.mail = mail;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public usuarios(){}

    @Override
    public String toString() {
        return "dni: " + dni;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
