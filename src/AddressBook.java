import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AddressBook {
    private Map<String, String> contacts;
    private String fileName;

    public AddressBook(String fileName) {
        this.fileName = fileName;
        this.contacts = new HashMap<>();
    }

    public void load() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String phoneNumber = parts[0].trim();
                    String name = parts[1].trim();
                    contacts.put(phoneNumber, name);
                }
            }
            System.out.println("Contactos cargados correctamente desde el archivo.");
        } catch (IOException e) {
            System.out.println("Error al cargar los contactos desde el archivo: " + e.getMessage());
        }
    }

    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                String phoneNumber = entry.getKey();
                String name = entry.getValue();
                writer.write(phoneNumber + "," + name);
                writer.newLine();
            }
            System.out.println("Contactos guardados correctamente en el archivo.");
        } catch (IOException e) {
            System.out.println("Error al guardar los contactos en el archivo: " + e.getMessage());
        }
    }

    public void list() {
        System.out.println("Contactos:");
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            String phoneNumber = entry.getKey();
            String name = entry.getValue();
            System.out.println(phoneNumber + " : " + name);
        }
    }

    public void create() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el número telefónico: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Ingrese el nombre: ");
        String name = scanner.nextLine();
        contacts.put(phoneNumber, name);
        System.out.println("Contacto creado exitosamente.");
    }

    public void delete() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el número telefónico a eliminar: ");
        String phoneNumber = scanner.nextLine();
        String name = contacts.remove(phoneNumber);
        if (name != null) {
            System.out.println("Contacto eliminado: " + phoneNumber + " : " + name);
        } else {
            System.out.println("No se encontró ningún contacto con ese número telefónico.");
        }
    }

    public static void main(String[] args) {
        String fileName = "C:\\Users\\fabia\\IdeaProjects\\Agenda\\MisContactos.csv";
        AddressBook addressBook = new AddressBook(fileName);
        addressBook.load();

        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        while (choice != 0) {
            System.out.println("\nMenú:");
            System.out.println("1. Listar contactos");
            System.out.println("2. Crear contacto");
            System.out.println("3. Eliminar contacto");
            System.out.println("0. Salir");
            System.out.print("Ingrese su opción: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea después de leer la opción numérica

            switch (choice) {
                case 1:
                    addressBook.list();
                    break;
                case 2:
                    addressBook.create();
                    break;
                case 3:
                    addressBook.delete();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
                    break;
            }
        }

        addressBook.save();
    }
}
