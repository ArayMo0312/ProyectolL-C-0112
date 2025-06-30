import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ClinicaGUI extends JFrame {
    private ColaMascotas cola;
    private ArbolMascotas arbol;
    private JTextArea areaCola; //cola de mascotas
    private JTextArea areaAtendiendo; // mascota siendo atendida
    private JButton botonAtender;
    private JButton botonAgregar;

    public ClinicaGUI (ColaMascotas cola, ArbolMascotas arbol) {
        this.cola = cola;
        this.arbol = arbol;

        setTitle("Clínica Veterinaria - Cola de Espera");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Menú desplegable para el botón de guardar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menuArchivo = new JMenu("Archivo");
        menuBar.add(menuArchivo);
        JMenuItem guardarItem = new JMenuItem("Guardar estado");
        menuArchivo.add(guardarItem);
        guardarItem.addActionListener(e ->{ // Agrega la acción de llamar al método guardar al botón de guardar
            cola.guardarArchivoCola("cola.txt");
            JOptionPane.showMessageDialog(this, "Estado guardado en archivo.");
        });
        
        areaAtendiendo = new JTextArea(5,40);
        areaAtendiendo.setEditable(false);
        areaAtendiendo.setBorder(BorderFactory.createTitledBorder("Mascota en Atención"));
        add(areaAtendiendo, BorderLayout.NORTH);

        areaCola = new JTextArea();
        areaCola.setEditable(false);
        areaCola.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(areaCola);
        scroll.setBorder(BorderFactory.createTitledBorder("Mascotas en Cola"));
        add(scroll, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        botonAtender = new JButton("Atender siguiente");
        botonAgregar = new JButton("Agregar nueva mascota");
        panelBotones.add(botonAgregar);
        panelBotones.add(botonAtender);
        add(panelBotones, BorderLayout.SOUTH);

        botonAtender.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Mascota atendida = cola.dequeue();
                if (atendida  != null) {
                    atendida.agregarHistorial("Atendida en turno actual.");
                    areaAtendiendo.setText(atendida.toString());
                } else {
                    areaAtendiendo.setText ("No hay mascotas en espera.");
                }
                actualizarCola();
            }
        });

        botonAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarNuevaMascota();
                actualizarCola();
            }
        });

        JButton botonHistorial = new JButton("Ver Historial");
        botonHistorial.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                JDialog dialogo = new JDialog(ClinicaGUI.this, "Clinica Veterinaria - Historial de Mascotas", true);
                dialogo.add(new HistorialMascotasPanel(arbol));
                dialogo.setSize(400, 300);
                dialogo.setVisible(true);
            }
        });
        panelBotones.add(botonHistorial);

    }
    public void agregarNuevaMascota() { // ventana de input para el botón Agregar
        try {
            String idString = JOptionPane.showInputDialog(this, "ID de la mascota:");
            if (idString == null) return;
            int id = Integer.parseInt(idString);

            String nombre = JOptionPane.showInputDialog(this, "Nombre de la mascota:");
            if (nombre == null) return;

            String especie = JOptionPane.showInputDialog(this, "Especie de la mascota:");
            if (especie == null) return;

            String dueño = JOptionPane.showInputDialog(this, "Nombre del dueño:");
            if (dueño == null) return;

            Mascota nueva = new Mascota(id, nombre, especie, dueño);
            try {
                arbol.insertar(nueva);
                cola.enqueue(nueva);
                
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Id inválido. Debe ser un número entero.");
        }

    }
     private void actualizarCola() {
        areaCola.setText(cola.mostrarColaComoTexto());
    }
}