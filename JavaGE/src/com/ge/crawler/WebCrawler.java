package com.ge.crawler;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ge.crawler.utils.JSONReader;

/**
 * @author Chandru
 * This is the main class of WebCrawler project
 *
 */
public class WebCrawler {
	
	private static final Logger logger = Logger.getLogger(WebCrawler.class);
	private HashSet<String> vLinks;
	private HashSet<String> dupLinks;
	private HashSet<String> errorLinks;
	private Map<String, JSONArray>pageAndLinks;
		
	/**
	 * Constructor
	 */
	public WebCrawler() {
		
		logger.info("WebCrawler constructor called.");
        vLinks = new HashSet<String>();
        dupLinks = new HashSet<String>();
        errorLinks = new HashSet<String>();
        pageAndLinks = new TreeMap<String, JSONArray>();
    }
	
	/**
	 * Main method of the crawler for test the class
	 * @param args
	 */
	public static void main(String[] args) {
		
		WebCrawler crawler = new WebCrawler();
		JSONReader fileReader = new JSONReader();
		JSONObject jsonFileObj = fileReader.getFile("internet1.json");
		crawler.processFile(fileReader.getPageAndLinks(jsonFileObj));
		
		JSONObject jsonFileObj2 = fileReader.getFile("internet2.json");
		crawler.processFile(fileReader.getPageAndLinks(jsonFileObj2));
	}
	
	/**
	 * Method used to process the the input page and links.
	 * @param mapLinks
	 */
	public JSONObject processFile(final Map<String, JSONArray> mapLinks){
		
		pageAndLinks = mapLinks;	
		processLink(pageAndLinks.keySet().toArray()[0].toString());
		
		JSONObject output = new JSONObject();
		output.put("Success", JSONReader.createJSONArry(vLinks));
		output.put("Skipped", JSONReader.createJSONArry(dupLinks));
		output.put("Error", JSONReader.createJSONArry(errorLinks));
		
		logger.info("File processed and output JSON object has created.");
		
		logger.info(" Output : " + output);
		vLinks.clear();
		dupLinks.clear();
		errorLinks.clear();
		
		return output;
	}

	/**
	 * Method used to process the each link
	 * @param URL
	 */
	private void processLink(final String URL) {
		
		logger.info("Processing the URL : " + URL);
		// Check if we have already crawled the URLs
		if (vLinks.contains(URL)) {
			dupLinks.add(URL);
		} else if (null == pageAndLinks.get(URL)) {
			errorLinks.add(URL);
		} else {
			vLinks.add(URL);
			JSONArray pgLinksArry = (JSONArray) pageAndLinks.get(URL);
			Iterator<String> pgLinks = pgLinksArry.iterator();
			while (pgLinks.hasNext()) {
				String link = pgLinks.next();
				processLink(link);
			}
		}
	}
}
