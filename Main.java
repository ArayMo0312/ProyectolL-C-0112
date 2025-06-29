import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        ColaMascotas cola = new ColaMascotas();
        ArbolMascotas arbol = new ArbolMascotas();

        SwingUtilities.invokeLater(() -> {

            ClinicaGUI clinica = new ClinicaGUI(cola, arbol);
            clinica.setVisible(true);
        });
    }
}