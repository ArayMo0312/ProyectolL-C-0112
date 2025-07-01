import java.io.*;
import javax.swing.JOptionPane;

public class ColaMascotas {
    private NodoCola head;
    private NodoCola tail;
    private int tamaño;

    public ColaMascotas () { // La cola inicia sin mascotas
        this.head = null;
        this.tail = null;
        this.tamaño = 0;
    }

    public boolean estaVacia() {
        return (this.tamaño == 0);
    }

    public void enqueue (Mascota dato) { // Agrega objetos de tipo Mascota a ColaMascotas
        if (yaExiste(dato)) {
            throw new IllegalArgumentException("Mascota ya está en la cola.");
        }
        NodoCola nuevo = new NodoCola(dato);

        if (estaVacia()) {
            this.head = nuevo;
            this.tail = nuevo;
        } else {
            this.tail.setSiguiente(nuevo);
            this.tail = nuevo;
        }
        tamaño++;
    }

    public Mascota dequeue() { // Saca la mascota que está primera en la fila para ser atendida
        if (estaVacia()) {
            return null;
        }
        Mascota dato = this.head.getDato();
        this.head = this.head.getSiguiente();
        this.tamaño--;

        if (this.head == null) {
            this.tail = null;
        }
        return dato;
    }

    public boolean yaExiste (Mascota dato) { // Llama al método recursivo
        return yaExisteRecursivo(dato, this.head);
    }

    private boolean yaExisteRecursivo (Mascota dato, NodoCola actual) { // Compara el dato que entra con todos los nodos que existen para saber si ya existe
        if (actual == null) {
            return false;
        }
        if (actual.getDato().equals(dato)) {
            return true;
        }
        return yaExisteRecursivo(dato, actual.getSiguiente());
    }

    public String mostrarColaComoTexto() { // Llama al método recursivo
        return mostrarColaRecursivo(this.head);
    }

    private String mostrarColaRecursivo(NodoCola actual) { // Usa el toString para ver todas las mascotas de la cola en forma de texto
        if (actual == null) {
            return "";
        }
        return actual.getDato().toString() + " \n----------------------\n " + mostrarColaRecursivo(actual.getSiguiente());

    }

    public void guardarArchivoCola(String archivo) { // Guarda el archivo utilizando el método recursivo para imprimir todas las mascotas de la cola
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            guardarRecursivo(writer, head);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardarRecursivo(PrintWriter writer, NodoCola actual) {
        if (actual == null) return;

        Mascota m =actual.getDato();
        String linea = m.getId() + "|" + m.getNombre() + "|" + m.getEspecie() + "|" + m.getNombreDueño() + "|" + m.getHistorial().replace("\n", "\\n") + "|" + m.getVecesAtendida(); // Cada mascota será guardada en una línea, separando los datos por "|" y borrando saltos de línea del historial
        writer.println(linea); // Escribe la línea en el archivo
        guardarRecursivo(writer, actual.getSiguiente()); //Llamado recursivo
    }

    public void cargarColaArchivo (String nombreArchivo) { // Lee los datos de cada línea del archivo, los asigna a los atributos de mascota y agrega las mascotas que estaban en la cola de nuevo a la cola
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split("\\|"); // Divide el string en partes de un array utilizando "|" como separador
                if (partes.length >= 6) {
                    int id = Integer.parseInt(partes[0]); // Ya que viene de un String, hay que hacer un parse para que el ID vuelva a ser int
                    String nombre = partes[1];
                    String especie = partes[2];
                    String dueño = partes[3];
                    String historial = partes[4].replace("\\n", "\n"); //Vuelve a agregar los saltos de línea
                    int veces = Integer.parseInt(partes[5]);

                    Mascota mascota = new Mascota(id, nombre, especie, dueño);
                    mascota.agregarHistorial(historial);
                    mascota.setVecesAtendida(veces);
                    enqueue(mascota);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error de formato en los datos del archivo.", "Error de datos", JOptionPane.ERROR_MESSAGE);
        }
    }
}