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

    public void guardarArchivoCola(String archivo, Mascota mascotaActual) { // Guarda el archivo utilizando el método recursivo para imprimir todas las mascotas de la cola
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            if (mascotaActual != null) { // Guarda la mascota que está siendo atendida 
                writer.println("ATENDIENDO|" + mascotaActual.getId());
            } else {
                writer.println("ATENDIENDO|null");
            }
            guardarRecursivo(writer, head); // Guarda todas las mascotas de la cola
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void guardarArchivoCola(String archivo) { // Para otras clases donde no existe mascotaActual
        guardarArchivoCola(archivo, null);
    }

    private void guardarRecursivo(PrintWriter writer, NodoCola actual) {
        if (actual == null) return;

        writer.println(actual.getDato().getId()); // Escribe el ID en el archivo
        guardarRecursivo(writer, actual.getSiguiente()); //Llamado recursivo
    }

    public Mascota cargarColaArchivo (String nombreArchivo, ArbolMascotas arbol) { // Lee los datos de cada línea del archivo, los asigna a los atributos de mascota y agrega las mascotas que estaban en la cola de nuevo a la cola
        Mascota mascotaEnAtencion = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                linea = linea.trim();

                if (linea.startsWith("ATENDIENDO|")) { // Si la línea empieza con atendiendo, lo agrega al area de atendiendo, si no, asume que es ID de mascota en la cola y la agrega ahí
                    String[] partes = linea.split("\\|");
                    if (partes.length == 2 && !partes[1].equals("null")) {
                        try {
                            System.out.println(partes[1]);
                            int idAtendiendo = Integer.parseInt(partes[1].trim());
                            mascotaEnAtencion = arbol.buscar(idAtendiendo);
                            if (mascotaEnAtencion == null) {
                                JOptionPane.showMessageDialog(null, "Mascota atendiendo con ID " + idAtendiendo + " no encontrada en el árbol.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "ID de mascota atendida inválido: " + partes[1], "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
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
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No se pudo cargar el archivo de la cola: " + e.getMessage(), "Error de lectura", JOptionPane.ERROR_MESSAGE);
        }
        
        return mascotaEnAtencion;
    }
}