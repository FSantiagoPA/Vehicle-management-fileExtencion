package com.santiparra.filemanagment;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.santiparra.cars.dao.util.Config;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

/**
 * Clase que implementa un manejador SAX para leer archivos XML de Person.
 */
public class SAXHandler extends DefaultHandler {
	private StringBuilder currentValue = new StringBuilder();
	

	public static void listPersonsFromXML() throws Exception {
		File file = new File(Config.FILE_XML);
		if (!file.exists()) {
			System.out.println("El archivo XML no existe: " + Config.FILE_XML);
			return;
		}

		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		parser.parse(file, new SAXHandler());
	}

	@Override
	public void startDocument() {
		System.out.println("===== LISTING PERSONS FROM XML (SAX) =====");
	}

	@Override
	public void endDocument() {
		System.out.println("===== END OF DOCUMENT =====");
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		currentValue.setLength(0);
		if (qName.equalsIgnoreCase("Person")) {
			System.out.printf("Person ID : %s%n", attributes.getValue("driverId"));
			String birthYear = attributes.getValue("yearOfBirth");
			if (birthYear != null) {
				System.out.printf("Birth Year : %s%n", birthYear);
			}
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		currentValue.append(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase("Name")) {
			System.out.printf("Full Name : %s%n", currentValue.toString().trim());
		} else if (qName.equalsIgnoreCase("Address")) {
			System.out.printf("Address : %s%n", currentValue.toString().trim());
		} else if (qName.equalsIgnoreCase("Age")) {
			System.out.printf("Age : %s%n", currentValue.toString().trim());
		}
	}
}
