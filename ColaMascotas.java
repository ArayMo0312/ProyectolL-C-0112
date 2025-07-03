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

        writer.println(actual.getDato().getId()); // Escribe el ID en el archivo
        guardarRecursivo(writer, actual.getSiguiente()); //Llamado recursivo
    }

    public void cargarColaArchivo (String nombreArchivo, ArbolMascotas arbol) { // Lee los datos de cada línea del archivo, los asigna a los atributos de mascota y agrega las mascotas que estaban en la cola de nuevo a la cola
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                try {
                    int id = Integer.parseInt(linea.trim()); // Lee la línea y saca el ID quitando espacios en blanco
                    Mascota m = arbol.buscar(id);
                    if (m != null) {
                        enqueue(m);
                    } else {
                        JOptionPane.showMessageDialog(null, "Mascota con el ID: " + id + " no encontrada en el registro.", "Error al cargar cola", JOptionPane.ERROR_MESSAGE);
                }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "ID inválido en el archivo: " + linea, "Error de formato", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No se pudo cargar el archivo de la cola: " + e.getMessage(), "Error de lectura", JOptionPane.ERROR_MESSAGE);
        }
    }
}