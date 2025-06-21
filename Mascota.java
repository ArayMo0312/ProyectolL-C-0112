public class Mascota {
    private int id;          
    private String nombre;
    private String especie;     
    private String nombreDueño;
    private String historial;

    public Mascota(int id, String nombre, String especie, String nombreDueño) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.nombreDueño = nombreDueño;
        this.historial = ""; // Empieza sin historial
    }
    
    public void agregarHistorial(String entrada) {
        historial += entrada + "\n";
    }

    @Override
    public String toString() {
        return "ID: " + id + "\n"
             + "Nombre: " + nombre + "\n"
             + "Especie: " + especie + "\n"
             + "Dueño: " + nombreDueño + "\n"
             + "Historial:\n" + historial;
    }

    // Getters
    public int getId() {return id;}
    public String getNombre() {return nombre;}
    public String getEspecie() {return especie;}
    public String getNombreDueño() {return nombreDueño;}
    public String getHistorial() {return historial;}

    // Setters 
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setEspecie(String especie) {this.especie = especie;}
    public void setNombreDueño(String nombreDueño) {this.nombreDueño = nombreDueño;}
}