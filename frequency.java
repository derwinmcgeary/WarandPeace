import java.util.Collections;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.SortedSet;
import java.util.regex.*;
import java.util.Set;
import java.util.Comparator;

public class frequency {

    public static Map<String, Integer> sortByValue(Map<String, Integer> map) {
	List list = new LinkedList(map.entrySet());
	Collections.sort(list, new Comparator() {
		
		public int compare(Object o2, Object o1) {
		    return ((Comparable) ((Map.Entry) (o1)).getValue()) .compareTo(((Map.Entry) (o2)).getValue());
		}
	    });
	Map result = new LinkedHashMap();
	for (Iterator it = list.iterator(); it.hasNext();) {
	    Map.Entry entry = (Map.Entry)it.next();
	    result.put(entry.getKey(), entry.getValue());
	}
	return result;
    }

    public static void main (String[] args) {
	// Read in War and Peace
	String inFile = "pg2600.txt";
	String outFile = "tf.csv";
	String topTen = "top10.csv";
	FileReader fr = null;
	BufferedReader br = null;
	
	if (args.length>0) {
	    inFile = args[0];
	}

	if (args.length > 1) {
	    outFile = args[1];
	}

	if (args.length == 2) {
	    topTen = args[2];
	}

	if (args.length > 2) {
	    System.out.println("Usage: java frequency [INPUT_FILE] [OUTPUT_FILE] [TOP_TEN_FILE]/n/n All parameters are optional.");
	}

	try {
	    fr = new FileReader(inFile);
	    br = new BufferedReader(fr);
	} catch(FileNotFoundException e) {
	    System.out.printf("Argh! We can't find %s! Time to die...", inFile);
	    return;
	} 

	Map<String, Integer> ft = freqTable(br);

	ft = sortByValue(ft);
	
	writeCSVHashMap(ft, outFile);
	writeCSVHashMap(ft, topTen,10);
    }


    private static HashMap<String, Integer> freqTable(BufferedReader br) {
	// foreach word in War and Peace, if it is in the
	// frequency table, increment frequency, otherwise
	// add to the frequency table with frequency one

	long startTime = System.currentTimeMillis();
	
	int total=0;
	String currentLine=null;
	HashMap<String, Integer> table = new HashMap<String, Integer>(); 
	
	try {
	    while ((currentLine=br.readLine()) != null) {
		// This is our definition of a word
		// splitting the line on anything non wordy (alpha + ' and -)

		// Double dashes are used in PG to represent an M dash -- for quotation
		currentLine = currentLine.replace("--"," ");
		String[] words = currentLine.split("[^A-Za-z'-]");
		for ( String w: words ) {
		    if (w.length() == 0) {
			break;
		    }

		    w = w.toLowerCase();
		    
		    // Find w in HashMap, if there, increment count, else add it
		    if(table.get(w)==null) { // i.e. we didn't find it
			table.put(w,1);
		    } else {                 // i.e. We found it
			table.put(w, table.get(w) + 1);
		    }
		}
	    }
	} catch (IOException e) {
	    System.out.println("errrrrrror!");
	} finally {
	    try {
		if(br!=null) {
		    br.close();
		}
	    } catch (IOException e) { System.out.println("This should never happen");}
	}

	System.out.printf("Total time: %d ms\n",System.currentTimeMillis()-startTime);
	return table;
    }

    // Rewritten to use HashMap
    private static void writeCSVHashMap(Map<String, Integer> ft, String fn, int n) {
	int counter=0;
	PrintWriter pw = null;
	
	try {
	    pw = new PrintWriter(fn);
	} catch(FileNotFoundException e) {
	    System.out.println("Can't open a file for writing");
	}

	Set<String> words =  ft.keySet();
	for(String word : words) {
	    pw.println(word + ", " + ft.get(word));
	    if(++counter==n) break;
	}

	pw.flush();
	pw.close();
    }

    private static void writeCSVHashMap(Map<String, Integer> ft, String fn) {
	writeCSVHashMap(ft, fn, ft.size());
    }

    
}
