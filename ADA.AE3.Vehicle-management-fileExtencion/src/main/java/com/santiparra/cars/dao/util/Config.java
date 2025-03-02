package com.santiparra.cars.dao.util;

import java.io.File;

public class Config {
	private static final String BASE_DIRECTORY = "target";

	public static final String FILE_CSV = BASE_DIRECTORY + File.separator + "csvfiles" + File.separator + "persons.csv";
	public static final String FILE_XML = BASE_DIRECTORY + File.separator + "xmlfiles" + File.separator + "persons.xml";
	public static final String File_XSL_ALL = BASE_DIRECTORY + File.separator + "xslfiles" + File.separator
			+ "persons_all.xsl";
	public static final String File_XSL_OLDER = BASE_DIRECTORY + File.separator + "xslfiles" + File.separator
			+ "persons_adult.xsl";
	public static final String File_XSL_MINOR = BASE_DIRECTORY + File.separator + "xslfiles" + File.separator
			+ "persons_minor.xsl";
	public static final String FILE_HTML_ALL = BASE_DIRECTORY + File.separator + "htmlfiles" + File.separator
			+ "persons_all.html";
	public static final String FILE_HTML_OLDER = BASE_DIRECTORY + File.separator + "htmlfiles" + File.separator
			+ "persons_adult.html";
	public static final String FILE_HTML_MINOR = BASE_DIRECTORY + File.separator + "htmlfiles" + File.separator
			+ "persons_minor.html";
}
