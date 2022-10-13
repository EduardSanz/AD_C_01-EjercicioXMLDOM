package xmldom.manejadores;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import xmldom.modelos.Alumno;
import xmldom.modelos.Tags;

import javax.swing.text.html.HTML;
import java.util.ArrayList;

public class AlumnosSAXHandler extends DefaultHandler {


    private Alumno alumno;
    private String dato;
    private ArrayList<Alumno> alumnosList;

    public AlumnosSAXHandler(ArrayList<Alumno> alumnosList) {
        this.alumnosList = alumnosList;
        this.alumnosList.clear();
    }

    /**
     * Se dispara al encontrar etique de apertura,
     *
     * SOLO LOS TAG CON ATRIBUTOS
     * @param uri The Namespace URI, or the empty string if the
     *        element has no Namespace URI or if Namespace
     *        processing is not being performed.
     * @param localName The local name (without prefix), or the
     *        empty string if Namespace processing is not being
     *        performed.
     * @param qName The qualified name (with prefix), or the
     *        empty string if qualified names are not available.
     * @param attributes The attributes attached to the element.  If
     *        there are no attributes, it shall be an empty
     *        Attributes object.
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        // Estoy en un nodo Objeto
        if (qName.equals(Tags.ALUMNO)) {
            alumno = new Alumno();
            alumno.setId(attributes.getValue(Tags.ID));
        }
    }


    /**
     * Se dispara al encontrar una etiqueta de cierre
     *
     * CUANDO EL TAG TIENE VALOR INTERNO
     * @param uri The Namespace URI, or the empty string if the
     *        element has no Namespace URI or if Namespace
     *        processing is not being performed.
     * @param localName The local name (without prefix), or the
     *        empty string if Namespace processing is not being
     *        performed.
     * @param qName The qualified name (with prefix), or the
     *        empty string if qualified names are not available.
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        switch (qName) {
            case Tags.ALUMNO:
                alumnosList.add(alumno);
                break;
            case Tags.NOMBRE:
                alumno.setNombre(dato);
                break;
            case Tags.APELLIDOS:
                alumno.setApellido(dato);
                break;
            case Tags.ASIGNATURAS:
                alumno.setAsignaturas(Integer.parseInt(dato));
                break;
        }
    }

    /**
     * Se dispara al encontrar texto Plano
     * @param ch The characters.
     * @param start The start position in the character array.
     * @param length The number of characters to use from the
     *               character array.
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        dato = new String(ch, start, length);
    }
}
