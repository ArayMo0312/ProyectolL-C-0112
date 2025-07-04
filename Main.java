import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        ColaMascotas cola = new ColaMascotas();
        ArbolMascotas arbol = new ArbolMascotas();
        arbol.cargarDesdeArchivo("historial.txt");
        Mascota mascotaActual = cola.cargarColaArchivo("cola.txt", arbol); // Llama siempre al método de carga antes de mostrar la interfaz

        System.out.println("Ruta actual: " + new java.io.File(".").getAbsolutePath());
        SwingUtilities.invokeLater(() -> {

            ClinicaGUI clinica = new ClinicaGUI(cola, arbol, mascotaActual);
            clinica.setVisible(true);
            clinica.actualizarCola();
        });
    }
}