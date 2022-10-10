package xmldom;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import xmldom.modelos.Alumno;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        ArrayList<Alumno> alumnosList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int opcion = menu(scanner);

        switch (opcion) {
            case 1:
                crearAlumno(alumnosList, scanner);
                break;
            case 2:
                try {
                    guardarAlumnos(alumnosList, scanner);
                } catch (ParserConfigurationException e) {
                    throw new RuntimeException(e);
                } catch (TransformerException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 3:
                alumnosList = cargarAlumnos();
                break;
            case 4:
                System.out.println("Bye Bye baby");
        }

    }

    private static void guardarAlumnos(ArrayList<Alumno> alumnosList, Scanner scanner) throws ParserConfigurationException, TransformerException {
        System.out.println("Dime el nombre del fichero");
        String nombreFichero = scanner.nextLine()+".xml";

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.newDocument();

        // PARA LA RAIZ DEL DOCUMENTO
        Element raiz = doc.createElement("alumnos");
        doc.appendChild(raiz);

        for (Alumno kk : alumnosList) {
            Element alumno = doc.createElement("alumno");
            raiz.appendChild(alumno);

            Attr attrId = doc.createAttribute("id_estudiante");
            attrId.setValue(kk.getId());
            alumno.setAttributeNode(attrId);

            Element nombre = doc.createElement("nombre");
            nombre.setTextContent(kk.getNombre());
            alumno.appendChild(nombre);

            Element apellidos = doc.createElement("apellidos");
            apellidos.setTextContent(kk.getApellido());
            alumno.appendChild(apellidos);

            Element asignaturas = doc.createElement("asignaturas_matriculadas");
            asignaturas.setTextContent(String.valueOf(kk.getAsignaturas()));
            alumno.appendChild(asignaturas);
        }

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();
        DOMSource ds = new DOMSource(doc);

        t.setOutputProperty(OutputKeys.INDENT, "yes");

        StreamResult sr = new StreamResult(new File(nombreFichero));
        t.transform(ds, sr);

    }

    private static void crearAlumno(ArrayList<Alumno> alumnosList, Scanner scanner) {
        System.out.println("Dime el id del Alumno");
        String id  = scanner.nextLine();
        System.out.println("Dime el NOMBRE del Alumno");
        String nombre  = scanner.nextLine();
        System.out.println("Dime los APELLIDOS del Alumno");
        String apellidos  = scanner.nextLine();
        System.out.println("Dime el numero de asignaturas del Alumno");
        int asignaturas  = scanner.nextInt();
        scanner.nextLine();
        alumnosList.add(new Alumno(id, nombre, apellidos, asignaturas));
    }

    private static int menu(Scanner scanner) {
        int opcion;
        do{
            System.out.println("1. Introducir Alumno");
            System.out.println("2. Guardar Alumnos");
            System.out.println("3. Cargar Alumnos");
            System.out.println("4. salir");
            opcion = scanner.nextInt();
            scanner.nextLine();
        }while (opcion < 1 || opcion > 4);
        return opcion;
    }
}