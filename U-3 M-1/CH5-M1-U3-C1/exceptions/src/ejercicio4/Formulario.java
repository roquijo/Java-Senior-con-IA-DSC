package ejercicio4;

public class Formulario {

    private String nombre;
    private String telefono;
    private String codigoPostal;

    public Formulario(String nombre, String telefono, String codigoPostal) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.codigoPostal = codigoPostal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    @Override
    public String toString() {
        return "Formulario{" +
                "nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", codigoPostal='" + codigoPostal + '\'' +
                '}';
    }
}
