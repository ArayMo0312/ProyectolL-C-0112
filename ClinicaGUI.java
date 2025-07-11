import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;

public class ClinicaGUI extends JFrame {
    private ColaMascotas cola;
    private Mascota mascotaActual;
    private ArbolMascotas arbol;
    private JTextArea areaCola; //cola de mascotas
    private JTextArea areaAtendiendo; // mascota siendo atendida
    private JButton botonAtender;
    private JButton botonAgregar;

    public ClinicaGUI (ColaMascotas cola, ArbolMascotas arbol, Mascota mascotaActualP) {
        this.cola = cola;
        this.arbol = arbol;
        this.mascotaActual = mascotaActualP;

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
            cola.guardarArchivoCola("cola.txt", mascotaActual);
            arbol.guardarEnArchivo("historial.txt");
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
                mascotaActual = cola.dequeue();
                if (mascotaActual  != null) {
                    mascotaActual.setVecesAtendida(mascotaActual.getVecesAtendida()+1);
                    mascotaActual.agregarHistorial("Atendida: " + mascotaActual.getVecesAtendida() + " veces.");
                    areaAtendiendo.setText(mascotaActual.toString());
                } else {
                    areaAtendiendo.setText ("No hay mascotas en espera.");
                }
                actualizarCola();
                cola.guardarArchivoCola("cola.txt", mascotaActual);
                arbol.guardarEnArchivo("historial.txt");
            }
        });

        botonAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarNuevaMascota();
                actualizarCola();
            }
        });

        JButton botonHistorial = new JButton("Ver Registro");
        botonHistorial.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                JDialog dialogo = new JDialog(ClinicaGUI.this, "Clinica Veterinaria - Registro de Mascotas", true);
                dialogo.add(new HistorialMascotasPanel(ClinicaGUI.this, arbol, cola));
                dialogo.setSize(425, 350);
                dialogo.setVisible(true);
            }
        });
        panelBotones.add(botonHistorial);
        
        if (mascotaActual != null) {
            areaAtendiendo.setText(mascotaActual.toString());
        } else {
            areaAtendiendo.setText("No hay mascota en atención");
        }
    }
    public void agregarNuevaMascota() { // ventana de input para el botón Agregar
        try {
            int id = generarIdUnico();

            String nombre = JOptionPane.showInputDialog(this, "Nombre de la mascota:");
            if (nombre == null) return;

            String especie = JOptionPane.showInputDialog(this, "Especie de la mascota:");
            if (especie == null) return;

            String dueño = JOptionPane.showInputDialog(this, "Nombre del dueño:");
            if (dueño == null) return;

            Mascota nueva = new Mascota(id, nombre, especie, dueño);
            try {
                arbol.insertar(nueva);
                arbol.guardarEnArchivo("historial.txt");
                
                
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido. Debe ser un número entero.");
        }

    }
     public void actualizarCola() {
        areaCola.setText(cola.mostrarColaComoTexto());
    }
    
    private int generarIdUnico() {
        Random random = new Random();
        int id;
        do {
            id = random.nextInt(Integer.MAX_VALUE); // Número entre 0 y el valor máximo de Integer
        } while (arbol.buscar(id) != null);
        return id;
    }
}