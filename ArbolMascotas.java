public class ArbolMascotas {
    private NodoABB raiz;

    public void insertar(Mascota nuevaMascota){
        raiz = insertarRec(raiz, nuevaMascota);
    }

    private NodoABB insertarRec(NodoABB actual, Mascota nuevaMascota){
        if(actual == null)return new NodoABB(nuevaMascota);             //Si no hay un nodo anterior pone a la nueva mascota como un nuevo nodo

        if(nuevaMascota.getNombre().compareToIgnoreCase(actual.mascota.getNombre()) < 0 ){   //Ordena alfabeticamente 
            actual.izquierdo = insertarRec(actual.izquierdo, nuevaMascota);
        }
        else if(nuevaMascota.getNombre().compareToIgnoreCase(actual.mascota.getNombre()) > 0 ){     //Ordena alfabeticamente
            actual.derecho = insertarRec(actual.derecho, nuevaMascota);
        }
        else{
            System.out.println("Macota con nombre duplicado no insertada: " + nuevaMascota.getNombre());
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

    

    
}

