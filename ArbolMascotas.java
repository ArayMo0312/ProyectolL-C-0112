import java.util.function.Consumer;

public class ArbolMascotas {
    private NodoABB raiz;

    public void insertar(Mascota nuevaMascota){
        raiz = insertarRec(raiz, nuevaMascota);
    }

    private NodoABB insertarRec(NodoABB actual, Mascota nuevaMascota){
        if(actual == null)return new NodoABB(nuevaMascota);             //Si no hay un nodo anterior pone a la nueva mascota como un nuevo nodo

        if(nuevaMascota.getId() < actual.mascota.getId()){   //Ordena alfabeticamente 
            actual.izquierdo = insertarRec(actual.izquierdo, nuevaMascota);
        }
        else if(nuevaMascota.getId() > actual.mascota.getId() ){     //Ordena alfabeticamente
            actual.derecho = insertarRec(actual.derecho, nuevaMascota);
        }
        else{
            throw new IllegalArgumentException("Ya existe una mascota con el ID: " + nuevaMascota.getId());
        }

        return actual;
    }

    //Metodo para buscar por nombre

    public Mascota buscar(String nombre){
        return buscarRec(raiz, nombre);
    }

    private Mascota buscarRec(NodoABB actual, String nombre){

        if(actual == null){
            return null;
        }

        if(nombre.equalsIgnoreCase(actual.mascota.getNombre())){
            return actual.mascota;
        }

        if(nombre.compareToIgnoreCase(actual.mascota.getNombre()) < 0){
            return buscarRec(actual.izquierdo, nombre);
        }

        else{
            return buscarRec(actual.derecho, nombre);
        }
    }

    //Metodo para recorrer el arbol en orden

    public void recorridoEnOrden(Consumer<Mascota> accion){
        recorridoEnOrdenRec(raiz, accion);
    }

    private void recorridoEnOrdenRec(NodoABB actual, Consumer<Mascota> accion){
        if(actual != null){
            recorridoEnOrdenRec(actual.izquierdo, accion);
            accion.accept(actual.mascota);
            recorridoEnOrdenRec(actual.derecho, accion);
        }
    }

    //Metodo para eliminar

    public void eliminar(String nombre){
        raiz = eliminarRec(raiz, nombre);

    }

    private NodoABB eliminarRec(NodoABB actual, String nombre){

        if(actual == null){return null;}

        if(nombre.compareToIgnoreCase(actual.mascota.getNombre()) < 0) {
            actual.izquierdo = eliminarRec(actual.izquierdo, nombre);
        }
        else if(nombre.compareToIgnoreCase(actual.mascota.getNombre()) > 0) {
            actual.derecho = eliminarRec(actual.derecho, nombre);
        }
        else{

            //Caso 1: Sin hijos
            if(actual.izquierdo == null && actual.derecho == null){
                return null;
            }
            
            //Caso 2: Un hijo
            if(actual.izquierdo == null){
                return actual.derecho;
            }
            if(actual.derecho == null){
                return actual.izquierdo;
            }

            //Caso 3: Mas de un hijo
            else{
                NodoABB sucesor = encontrarMinimo(actual.derecho);
                actual.mascota = sucesor.mascota;
                actual.derecho = eliminarRec(actual.derecho, sucesor.mascota.getNombre());
            }
        }
        return actual;
    }

    //Metod auxiliar
    private NodoABB encontrarMinimo(NodoABB nodo){
        while(nodo.izquierdo != null){
            nodo = nodo.izquierdo;
        }
        return nodo;
    }
}

