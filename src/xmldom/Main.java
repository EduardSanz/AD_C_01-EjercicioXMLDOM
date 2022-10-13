package xmldom;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import xmldom.manejadores.AlumnosSAXHandler;
import xmldom.modelos.Alumno;
import xmldom.modelos.Tags;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        ArrayList<Alumno> alumnosList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do{
            opcion = menu(scanner);
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
                    try {
                        cargarAlumnos(alumnosList, scanner);
                    } catch (ParserConfigurationException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (SAXException e) {
                        System.out.println("EL FICHERO ES INCOMPATIBLE");
                        ;
                    }
                    break;
                case 4:
                    try {
                        cargaAlumnosSAX(alumnosList, scanner);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (SAXException e) {
                        System.out.println("ERROR DE SAX");
                        System.out.println(e.getLocalizedMessage());
                    } catch (ParserConfigurationException e) {
                        System.out.println("ERRO EN EL PARSER");
                        System.out.println(e.getLocalizedMessage());
                    }
                    break;
                case 5:
                    System.out.println("Bye Bye");
            }
        }while (opcion != 5);

    }

    private static void cargaAlumnosSAX(ArrayList<Alumno> alumnosList, Scanner scanner) throws IOException, SAXException, ParserConfigurationException {
        System.out.println("Dime el nombre del fichero");
        String fileName = scanner.nextLine();
        File fichero = new File(fileName);

        SAXParserFactory saxPF = SAXParserFactory.newInstance();
        SAXParser saxP = saxPF.newSAXParser();
        AlumnosSAXHandler handler = new AlumnosSAXHandler(alumnosList);
        saxP.parse(fichero, handler);

        for (Alumno alumno : alumnosList) {
            System.out.println(alumno);
        }
    }

    private static void cargarAlumnos(ArrayList<Alumno> alumnosList, Scanner scanner) throws ParserConfigurationException, IOException, SAXException {

        System.out.println("Dime el nombre del fichero");
        String fileName = scanner.nextLine();
        File fichero = new File(fileName);

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc = db.parse(fichero);
        doc.getDocumentElement().normalize();

        alumnosList.clear();

        NodeList alumnosNodos = doc.getElementsByTagName(Tags.ALUMNO);
        for (int i = 0; i < alumnosNodos.getLength(); i++) {
            Node alumno = alumnosNodos.item(i);
            if (alumno.getNodeType() == Node.ELEMENT_NODE){
                Element alumnoElement = (Element) alumno;
                String id = alumnoElement.getAttribute(Tags.ID);
                String nombre = alumnoElement.getElementsByTagName(Tags.NOMBRE).item(0).getTextContent();
                String apellidos = alumnoElement.getElementsByTagName(Tags.APELLIDOS).item(0).getTextContent();
                int asignaturas = Integer.parseInt(alumnoElement.getElementsByTagName(Tags.ASIGNATURAS).item(0).getTextContent());
                alumnosList.add(new Alumno(id, nombre, apellidos, asignaturas));
            }
        }

        System.out.printf("Hemos cargado %d alumnos%n", alumnosNodos.getLength());
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
            Element alumno = doc.createElement(Tags.ALUMNO);
            raiz.appendChild(alumno);

            Attr attrId = doc.createAttribute(Tags.ID);
            attrId.setValue(kk.getId());
            alumno.setAttributeNode(attrId);

            Element nombre = doc.createElement(Tags.NOMBRE);
            nombre.setTextContent(kk.getNombre());
            alumno.appendChild(nombre);

            Element apellidos = doc.createElement(Tags.APELLIDOS);
            apellidos.setTextContent(kk.getApellido());
            alumno.appendChild(apellidos);

            Element asignaturas = doc.createElement(Tags.ASIGNATURAS);
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
            System.out.println("4. Cargar alumnos SAX");
            System.out.println("5. Salir");
            opcion = scanner.nextInt();
            scanner.nextLine();
        }while (opcion < 1 || opcion > 5);
        return opcion;
    }
}