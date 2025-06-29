import javax.swing.*;
public class Main {
    public static void main(String[] args) {
        ColaMascotas cola = new ColaMascotas();
        VentanaCola ventana = new VentanaCola(cola);
        SwingUtilities.invokeLater(() -> new VentanaCola(cola));
        ventana.setVisible(true);
    }
}