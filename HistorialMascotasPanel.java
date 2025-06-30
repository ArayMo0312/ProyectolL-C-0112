import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

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

        // Panel inferior de acciones (buscar y eliminar por ID)

        JPanel panelAcciones = new JPanel();
        campoBusqueda = new JTextField(10);
        botonBuscar = new JButton("Buscar");
        botonEliminar = new JButton("Eliminar");

        //Adicion de componentes al panel de acciones

        panelAcciones.add(new JLabel("ID:"));
        panelAcciones.add(campoBusqueda);
        panelAcciones.add(botonBuscar);
        panelAcciones.add(botonEliminar);
        add(panelAcciones, BorderLayout.SOUTH);

        // Eventos al pulssar los botones 
        botonBuscar.addActionListener((ActionEvent e) -> buscarMascota());
        botonEliminar.addActionListener((ActionEvent e) -> eliminarMascota());

        mostrarHistorial();
    }

    //Metodo para mostrar todas las Mascotas en cola y atedidas recorriendo el arbol

    private void mostrarHistorial() {
        areaHistorial.setText("");
        arbol.recorridoEnOrden(m -> areaHistorial.append(m.toString() + "\n\n"));
    }

    //Metodo para buscar una mascota por ID

    private void buscarMascota() {
        try{
            int id = Integer.parseInt(campoBusqueda.getText().trim());
            Mascota m = arbol.buscar(id);
            if (m != null) {
                JOptionPane.showMessageDialog(this, m.toString(), "Mascota encontrada", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró la mascota.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this, "El ID debe ser un numero entero.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Metodo para eliminar una mascota por ID

    private void eliminarMascota() {
        try {
            int id = Integer.parseInt(campoBusqueda.getText().trim());
            Mascota m = arbol.buscar(id);
            if (m != null) {
                arbol.eliminar(id);
                mostrarHistorial();
                JOptionPane.showMessageDialog(this, "Mascota eliminada.", "Eliminación exitosa", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Mascota no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this, "El ID debe ser un numero entero.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

