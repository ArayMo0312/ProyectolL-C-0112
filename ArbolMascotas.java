import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.Consumer;

public class ArbolMascotas {
    private NodoABB raiz;

    //Metodos para insertar al arbol

    public void insertar(Mascota nuevaMascota){
        raiz = insertarRec(raiz, nuevaMascota);
    }

    private NodoABB insertarRec(NodoABB actual, Mascota nuevaMascota){  
        if(actual == null)return new NodoABB(nuevaMascota);             //Si no hay un nodo anterior pone a la nueva mascota como un nuevo nodo

        if(nuevaMascota.getId() < actual.mascota.getId()){              //Si el id es menor ordena a la izquierda
            actual.izquierdo = insertarRec(actual.izquierdo, nuevaMascota);
        }
        else if(nuevaMascota.getId() > actual.mascota.getId() ){        //Si el id esmayor ordena a la derecha
            actual.derecho = insertarRec(actual.derecho, nuevaMascota);
        }
        else{                                                           //Si ya existe con ese id tira error
            throw new IllegalArgumentException("Ya existe una mascota con el ID: " + nuevaMascota.getId());
        }

        return actual;
    }

    //Metodos para buscar por nombre

    public Mascota buscar(int id){
        return buscarRec(raiz, id);
    }

    private Mascota buscarRec(NodoABB actual, int id){

        if(actual == null){                 //Si no encuentra devuelve null
            return null;
        }

        if(id == actual.mascota.getId()){   //Si lo encuentra lo devuelve
            return actual.mascota;
        }

        if(id < actual.mascota.getId()){    //Si es menor busca a la izquierda      
            return buscarRec(actual.izquierdo, id);
        }

        else{                               //Si es mayor busca a la derecha
            return buscarRec(actual.derecho, id);
        }
    }

    //Metodos para recorrer el arbol en orden

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

    //Metodos para eliminar

    public void eliminar(int id){
        raiz = eliminarRec(raiz, id);

    }

    private NodoABB eliminarRec(NodoABB actual, int id){

        if(actual == null){return null;}

        if(id < actual.mascota.getId()) {
            actual.izquierdo = eliminarRec(actual.izquierdo, id);
        }
        else if(id > actual.mascota.getId()) {
            actual.derecho = eliminarRec(actual.derecho, id);
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
                actual.derecho = eliminarRec(actual.derecho, sucesor.mascota.getId());
            }
        }
        return actual;
    }

    //Metodo auxiliar

    private NodoABB encontrarMinimo(NodoABB nodo){  //Encuentra el Nodo con el ID mas bajo (el de mas a la izquierda)
        while(nodo.izquierdo != null){
            nodo = nodo.izquierdo;
        }
        return nodo;
    }

    public void guardarEnArchivo(String nombreArchivo){
        try(PrintWriter writter =  new PrintWriter(new FileWriter(nombreArchivo))){
            recorridoEnOrden(mascota -> writter.println(mascota.getId() + ";" + mascota.getNombre() + ";" + mascota.getEspecie() + ";" + mascota.getNombreDueño() + ";" + mascota.getHistorial().replace("\n", "\\n") + ";" + mascota.getVecesAtendida()));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void cargarDesdeArchivo(String nombreArchivo){
        File archivo = new File(nombreArchivo);
        if(!archivo.exists()){
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))){
            String linea;
            while((linea = reader.readLine()) != null){
                String[] partes = linea.split(";");
                if (partes.length >= 6){
                    int id = Integer.parseInt(partes[0]);
                    String nombre = partes[1];
                    String especie = partes[2];
                    String dueño =partes [3];
                    String historial = partes[4].replace("\\n", "\n");
                    int veces = Integer.parseInt(partes[5]);
                    Mascota m = new Mascota(id, nombre, especie, dueño);
                    m.setHistorial(historial);
                    m.setVecesAtendida(veces);
                    insertar(m);
                }

            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}

