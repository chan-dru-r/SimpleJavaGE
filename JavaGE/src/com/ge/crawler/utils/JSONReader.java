package com.ge.crawler.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.ge.crawler.WebCrawler;

public class JSONReader {
	private static final Logger logger = Logger.getLogger(WebCrawler.class);
	private static final String PAGES = "pages";
	private static final String LINKS = "links";
	private static final String ADDRESS = "address";

	/** 
	 * Method used to get the JSONfile object.
	 * @param filename
	 * @return JSONObject
	 */
	public JSONObject getFile(final String filename) {
		
		logger.info("Reading a file for parse : " + filename);
		JSONParser parser = new JSONParser();
		JSONObject jsonFileObj = null;
		try {
			// Get file from input folder
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource(filename).getFile());
			Object obj = parser.parse(new FileReader(file));

			jsonFileObj = (JSONObject) obj;
			
		} catch (FileNotFoundException e) {
			logger.error("Input file is not found in the place specified: " + e );
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("File reading error : " + e );
			e.printStackTrace();
		} catch (ParseException e) {
			logger.error("File Parsing error : " + e );
			e.printStackTrace();
		}
		return jsonFileObj;
	}

	/**
	 * Method used to get the page and links under the page.
	 * @param jsonFileObj
	 * @return Map<String, JSONArray>
	 */
	public Map<String, JSONArray> getPageAndLinks(final JSONObject jsonFileObj) {

		Map<String, JSONArray> pageAndLinks = new TreeMap<String, JSONArray>();
		// loop array
		JSONArray jsonPgArray = (JSONArray) jsonFileObj.get(PAGES);
		Iterator<JSONObject> iterator = jsonPgArray.iterator();
		while (iterator.hasNext()) {
			JSONObject page = iterator.next();
			JSONArray pgLinksArry = (JSONArray) page.get(LINKS);
			pageAndLinks.put(page.get(ADDRESS).toString(), pgLinksArry);
		}
		return pageAndLinks;
	}
	
	/**
	 * This is the method used to create json array for given set
	 * @param items
	 * @return
	 */
	public static JSONArray createJSONArry(final HashSet<String> items){
		
		JSONArray jsonArry = new JSONArray();
		for(String item : items)
			jsonArry.add(item);
		
		return jsonArry;
	}

} // End of class
