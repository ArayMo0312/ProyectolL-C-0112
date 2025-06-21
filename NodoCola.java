public class NodoCola {
    private NodoCola siguiente;
    private Mascota dato;

    public NodoCola (Mascota dato) { // El nodo guarda objetos de tipo Mascota
        this.dato = dato;
        this.siguiente = null;
    }

    public Mascota getDato() {return dato;}
    public NodoCola getSiguiente() {return siguiente;}
    public void setSiguiente (NodoCola siguiente) {this.siguiente = siguiente;}
}