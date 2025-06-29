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
            System.out.println("La mascota ya está en la cola");
            return;
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
}