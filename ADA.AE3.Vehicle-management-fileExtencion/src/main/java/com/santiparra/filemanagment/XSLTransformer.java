package com.santiparra.filemanagment;

import java.io.File;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import com.santiparra.cars.dao.util.Config;

/**
 * Clase que maneja la transformación de XML a HTML usando XSLT.
 */
public class XSLTransformer {
	/**
	 * Transforma un archivo XML en un archivo HTML usando una hoja de estilo XSL.
	 * 
	 * @param xmlFile    Archivo XML de entrada.
	 * @param xslFile    Archivo XSL para la transformación.
	 * @param outputHtml Archivo HTML de salida.
	 * @throws TransformerException
	 */
	public static void transformXMLToHTML(String xmlFile, String xslFile, String outputHtml)
			throws TransformerException {
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(new StreamSource(xslFile));
		transformer.transform(new StreamSource(xmlFile), new StreamResult(new File(outputHtml)));
		System.out.println("Archivo HTML generado correctamente: " + outputHtml);
	}
	
	public static void generateAllPersonInHTML(String xmlFile) throws TransformerException {
		transformXMLToHTML(xmlFile,Config.File_XSL_ALL,Config.FILE_HTML_ALL);
	}
	public static void generateOlderPersonInHTML(String xmlFile) throws TransformerException {
		transformXMLToHTML(xmlFile,Config.File_XSL_OLDER,Config.FILE_HTML_OLDER);
	}
	public static void generateMinorPersonInHTML(String xmlFile) throws TransformerException {
		transformXMLToHTML(xmlFile,Config.File_XSL_MINOR,Config.FILE_HTML_MINOR);
	}
}
