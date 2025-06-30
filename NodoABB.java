public class NodoABB {
    
    Mascota mascota;
    NodoABB izquierdo, derecho;

    public NodoABB(Mascota mascota){    //Constructor basico para NodoABB
        this.mascota = mascota;
        this.izquierdo = null;
        this.derecho = null;
    }
}
