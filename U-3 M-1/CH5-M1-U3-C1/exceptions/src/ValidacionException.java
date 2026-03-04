import java.time.LocalDateTime;
import java.util.Date;

public class ValidacionException extends Exception {
    private LocalDateTime fecha;
    private String campo;

    public ValidacionException(String campo, LocalDateTime fecha, String mensaje) {
        super(mensaje);
        this.campo = campo;
        this.fecha = fecha;
    }

    public String getCampo() {
        return campo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return String.format("Error en campo '%s' con fecha '%s': %s",
                campo, fecha, getMessage());
    }
}