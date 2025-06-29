import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaCola extends JFrame {
    private ColaMascotas cola;
    private JTextArea areaCola; //cola de mascotas
    private JTextArea areaAtendiendo; // mascota siendo atendida
    private JButton botonAtender;
    private JButton botonAgregar;

    public VentanaCola (ColaMascotas cola) {
        this.cola = cola;

        setTitle("Clínica Veterinaria - Cola de Espera");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

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
    }
    public void agregarNuevaMascota() {
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
            cola.enqueue(nueva);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Id inválido. Debe ser un número entero.");
        }
    }
     private void actualizarCola() {
        areaCola.setText(cola.mostrarColaComoTexto());
    }
}