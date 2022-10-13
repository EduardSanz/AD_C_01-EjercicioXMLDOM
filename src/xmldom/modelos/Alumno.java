package xmldom.modelos;

public class Alumno {
    private String id;
    private String nombre;
    private String apellido;
    private int asignaturas;

    public Alumno(String id, String nombre, String apellido, int asignaturas) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.asignaturas = asignaturas;
    }

    public Alumno() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(int asignaturas) {
        this.asignaturas = asignaturas;
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", asignaturas=" + asignaturas +
                '}';
    }
}
