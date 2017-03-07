package crawler;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class WebCrawler {
	
	private HashSet<String> vLinks;
	private HashSet<String> dupLinks;
	private HashSet<String> errorLinks;
	private Map<String, JSONArray>pageAndLinks;
		
	/**
	 * Constructor
	 */
	public WebCrawler() {
        vLinks = new HashSet<String>();
        dupLinks = new HashSet<String>();
        errorLinks = new HashSet<String>();
        pageAndLinks = new TreeMap<String, JSONArray>();
    }
	
	/**
	 * Method used print the set elements.
	 * @param items
	 */
	private void prinSet(final HashSet<String> items){
		for(String item : items){
			System.out.println(item);
		}
	}
	
	/**
	 * Main method of the crawler
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
	private void processFile(final Map<String, JSONArray> mapLinks){
		pageAndLinks = mapLinks;	
		processLink(pageAndLinks.keySet().toArray()[0].toString());
		
		System.out.println("Success:");
		prinSet(vLinks);
		System.out.println("Skipped:");
		prinSet(dupLinks);
		System.out.println("Error:");
		prinSet(errorLinks);
		vLinks.clear();
		dupLinks.clear();
		errorLinks.clear();
	}

	/**
	 * Method used to process the each link
	 * @param URL
	 */
	private void processLink(final String URL) {
		
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
