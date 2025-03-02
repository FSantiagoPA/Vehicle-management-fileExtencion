package com.santiparra.filemanagment;

import com.santiparra.cars.dao.util.Config;
import com.santiparra.cars.entities.Person;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.*;

/**
 * Clase que maneja la conversión de archivos CSV a XML y la manipulación de archivos XML.
 */
public class DOMHandler {
    /**
     * Convierte una lista de personas a formato XML.
     * 
     * @param persons Lista de personas a convertir.
     */
    public static void convertCSVToXML(List<Person> persons) {
        try {
            if (persons.isEmpty()) {
                System.out.println("No hay datos para convertir.");
                return;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element root = doc.createElement("Persons");
            doc.appendChild(root);

            for (Person p : persons) {
                Element personElement = doc.createElement("Person");
                personElement.setAttribute("driverId", p.getDriverId());

                Element name = doc.createElement("Name");
                name.setTextContent(p.getName());
                personElement.appendChild(name);

                Element address = doc.createElement("Address");
                address.setTextContent(p.getAddress());
                personElement.appendChild(address);

                Element age = doc.createElement("Age");
                age.setTextContent(String.valueOf(p.getAge()));
                personElement.appendChild(age);

                root.appendChild(personElement);
            }

            saveXML(doc);
        } catch (Exception e) {
            System.out.println("Error procesando XML: " + e.getMessage());
        }
    }

    /**
     * Agrega el año de nacimiento a los elementos "Person" dentro del XML.
     */
    public static void addYearOfBirthToXML() {
        try {
            File file = new File(Config.FILE_XML);
            if (!file.exists()) {
                System.out.println("El archivo XML no existe: " + Config.FILE_XML);
                return;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            NodeList persons = doc.getElementsByTagName("Person");
            for (int i = 0; i < persons.getLength(); i++) {
                Element person = (Element) persons.item(i);
                int age = Integer.parseInt(person.getElementsByTagName("Age").item(0).getTextContent());
                int birthYear = java.time.Year.now().getValue() - age;
                person.setAttribute("yearOfBirth", String.valueOf(birthYear));
            }

            saveXML(doc);
        } catch (Exception e) {
            System.out.println("Error procesando XML: " + e.getMessage());
        }
    }

    /**
     * Guarda el documento XML en un archivo.
     * 
     * @param doc Documento XML a guardar.
     * @throws TransformerException si hay un error en la transformación.
     */
    private static void saveXML(Document doc) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(Config.FILE_XML));
        transformer.transform(source, result);
        System.out.println("Archivo XML actualizado: " + Config.FILE_XML);
    }
}
