import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class HistorialMascotasPanel extends JPanel {
    private ArbolMascotas arbol;
    private ColaMascotas cola;
    private JTextArea areaHistorial;
    private JTextField campoBusqueda;
    private JButton botonBuscar;
    private JButton botonEliminar;
    private JButton botonEncolar;
    private ClinicaGUI ventanaPadre; 

    public HistorialMascotasPanel(ClinicaGUI ventanaPadre, ArbolMascotas arbol, ColaMascotas cola) { // Se le pasa la instancia de ClinicaGUI para poder llamar al método actualizarCola()
        this.arbol = arbol;
        this.ventanaPadre = ventanaPadre;
        this.cola = cola;
        setLayout(new BorderLayout());

        // Área principal para mostrar historial

        areaHistorial = new JTextArea();
        areaHistorial.setEditable(false);
        areaHistorial.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(areaHistorial);
        scroll.setBorder(BorderFactory.createTitledBorder("Historial completo"));
        add(scroll, BorderLayout.CENTER);

        // Panel inferior de acciones (buscar, eliminar y encolar por ID)

        JPanel panelAcciones = new JPanel();
        campoBusqueda = new JTextField(10);
        botonBuscar = new JButton("Buscar");
        botonEliminar = new JButton("Eliminar");
        botonEncolar = new JButton("Encolar");

        //Adicion de componentes al panel de acciones

        panelAcciones.add(new JLabel("ID:"));
        panelAcciones.add(campoBusqueda);
        panelAcciones.add(botonBuscar);
        panelAcciones.add(botonEliminar);
        panelAcciones.add(botonEncolar);
        add(panelAcciones, BorderLayout.SOUTH);

        // Eventos al pulssar los botones 
        botonBuscar.addActionListener((ActionEvent e) -> buscarMascota());
        botonEliminar.addActionListener((ActionEvent e) -> eliminarMascota());
        botonEncolar.addActionListener((ActionEvent e) -> encolarMascota());

        mostrarHistorial();
    }

    //Metodo para mostrar todas las Mascotas en cola y atendidas recorriendo el arbol

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
                arbol.guardarEnArchivo("historial.txt");
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

    // Método para encolar del árbol a la cola
    private void encolarMascota() {
        try {
            int id = Integer.parseInt(campoBusqueda.getText().trim());
            Mascota m = arbol.buscar(id);

            if (m == null) {
                JOptionPane.showMessageDialog(this, "Mascota no encontrada en el registro.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (cola.yaExiste(m)) {
                JOptionPane.showMessageDialog(this, "La mascota ya está en la cola.", "Duplicado", JOptionPane.ERROR_MESSAGE);
                return;
            }

            cola.enqueue(m);
            ventanaPadre.actualizarCola();
            cola.guardarArchivoCola("cola.txt");
            arbol.guardarEnArchivo("historial.txt");
            JOptionPane.showMessageDialog(this, "Mascota encolada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

