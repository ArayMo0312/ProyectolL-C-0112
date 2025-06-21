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

}