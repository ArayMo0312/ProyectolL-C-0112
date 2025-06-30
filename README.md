# Proyecto 2 - Clínica Veterinaria (Estructuras Dinámicas con GUI)

Este proyecto de Programación I simula el funcionamiento de una clínica veterinaria utilizando estructuras de datos dinámicas implementadas desde cero en Java. El sistema permite registrar mascotas, atenderlas en orden de llegada, y consultar el historial de atención mediante una interfaz gráfica desarrollada con Java Swing.

---

## Ejecución del programa

1. Abra el proyecto en una IDE (BlueJ, IntelliJ, NetBeans, etc).
2. Ejecute la clase `Main.java`.
3. Se abrirá la ventana principal `ClinicaGUI` con las siguientes opciones:
    - Registrar nueva mascota
    - Atender siguiente en la cola
    - Ver historial de mascotas

---

## Funcionalidades principales

### Registro de mascotas
- El usuario ingresa ID, nombre, especie y dueño.
- Si el ID ya existe, se muestra un mensaje de error.
- La mascota se guarda en la cola y en el árbol binario.

### Atención en orden de llegada
- Se utiliza una lista enlazada para representar la cola de espera (FIFO).
- Al atender una mascota, se actualiza el área correspondiente en pantalla.

### Historial de mascotas
- Se accede a través de un botón en la GUI principal.
- Se muestra un recorrido inorden del árbol binario (ordenado por ID).
- Se puede buscar y eliminar mascotas mediante su ID.

---

## Clases del proyecto

- `Main.java` – Inicia el programa y la interfaz gráfica.
- `ClinicaGUI.java` – Ventana principal con cola, registro y botón de historial.
- `HistorialMascotasPanel.java` – Panel reutilizable para ver, buscar y eliminar mascotas desde el árbol.
- `Mascota.java` – Define el objeto Mascota con sus atributos y métodos.
- `ColaMascotas.java` – Implementa una lista enlazada para la cola de atención.
- `NodoCola.java` – Nodo de la lista enlazada de la cola.
- `ArbolMascotas.java` – Árbol binario de búsqueda ordenado por ID.
- `NodoABB.java` – Nodo del árbol binario.
- `README.md` – Este archivo explicativo.
- `Decisiones.txt` – Documento donde se explica la división del trabajo, diseño, etc.

---

## Requisitos

- Java 8 o superior.
- No se permite entrada o salida por consola.
- GUI obligatoria con Java Swing.
- Estructuras de datos implementadas desde cero (no se usan ArrayList ni TreeMap).

---

## Créditos

Desarrollado por:
- David Araya Montero – C4C553
- Jarek Morales Prado – C4H722

Para el curso de **Programación I**, grupo C0112, ciclo 2025-1.
