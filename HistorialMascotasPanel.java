import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class HistorialMascotasPanel extends JPanel {
    private ArbolMascotas arbol;
    private JTextArea areaHistorial;
    private JTextField campoBusqueda;
    private JButton botonBuscar;
    private JButton botonEliminar;

    public HistorialMascotasPanel(ArbolMascotas arbol) {
        this.arbol = arbol;
        setLayout(new BorderLayout());

        // Área principal para mostrar historial
        areaHistorial = new JTextArea();
        areaHistorial.setEditable(false);
        areaHistorial.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(areaHistorial);
        scroll.setBorder(BorderFactory.createTitledBorder("Historial completo"));
        add(scroll, BorderLayout.CENTER);

        // Panel de acciones
        JPanel panelAcciones = new JPanel();
        campoBusqueda = new JTextField(10);
        botonBuscar = new JButton("Buscar");
        botonEliminar = new JButton("Eliminar");

        panelAcciones.add(new JLabel("Nombre:"));
        panelAcciones.add(campoBusqueda);
        panelAcciones.add(botonBuscar);
        panelAcciones.add(botonEliminar);
        add(panelAcciones, BorderLayout.SOUTH);

        // Eventos
        botonBuscar.addActionListener((ActionEvent e) -> buscarMascota());
        botonEliminar.addActionListener((ActionEvent e) -> eliminarMascota());

        mostrarHistorial();
    }

    private void mostrarHistorial() {
        areaHistorial.setText("");
        arbol.recorridoEnOrden(m -> areaHistorial.append(m.toString() + "\n\n"));
    }

    private void buscarMascota() {
        String nombre = campoBusqueda.getText().trim();
        Mascota m = arbol.buscar(nombre);
        if (m != null) {
            JOptionPane.showMessageDialog(this, m.toString(), "Mascota encontrada", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró la mascota.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarMascota() {
        String nombre = campoBusqueda.getText().trim();
        Mascota m = arbol.buscar(nombre);
        if (m != null) {
            arbol.eliminar(nombre);
            mostrarHistorial();
            JOptionPane.showMessageDialog(this, "Mascota eliminada.", "Eliminación exitosa", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Mascota no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
