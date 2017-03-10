package com.ge.test;

import org.json.simple.JSONObject;
import org.junit.Test;

import com.ge.crawler.WebCrawler;
import com.ge.crawler.utils.JSONReader;

import junit.framework.TestCase;

/**
 * 
 * This is the class used to validate the webcrawler output.
 *
 */
public class WebCrawlerTest extends TestCase {

	WebCrawler crawler = null;
	JSONReader fileReader = null;
	
	protected void setUp() throws Exception {
		super.setUp();
		crawler = new WebCrawler();
		fileReader = new JSONReader();
	}
	
	@Test
	public void testWebCrawlTest1(){
		
		JSONObject jssonExepect1FileObj = fileReader.getFile("output1.json");
		JSONObject jsonFileObj = fileReader.getFile("internet1.json");
		JSONObject jsonOutputObj = crawler.processFile(fileReader.getPageAndLinks(jsonFileObj));
		//compare output file and expected file obj
		assertEquals(jssonExepect1FileObj, jsonOutputObj);
	}
	
	@Test
	public void testWebCrawlTest12(){
		
		JSONObject jssonExepect1FileObj = fileReader.getFile("output2.json");
		JSONObject jsonFileObj = fileReader.getFile("internet2.json");
		JSONObject jsonOutputObj = crawler.processFile(fileReader.getPageAndLinks(jsonFileObj));
		
		//compare output file and expected file obj
		assertEquals(jssonExepect1FileObj, jsonOutputObj);
	}

}
