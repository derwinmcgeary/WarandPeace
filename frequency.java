import java.util.Collections;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.regex.*;

public class frequency {

    private static class Word implements Comparable<Word> {
	String word;
	int count;

	public Word(String w, int c) {
	    word=w;
	    count=c;
	}
	
	public boolean equals(Word w) {
	    return word.equals(w);
	}

	public int incrementCount() {
	    return ++this.count;
	}

	@Override
	public int compareTo(Word w) {
	    return w.count - count; 
	}
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

	ArrayList<Word> ft = freqTable(br);

	writeCSV(ft, outFile, ft.size());
	writeCSV(ft, topTen,10);
    }


    private static ArrayList<Word> freqTable(BufferedReader br) {
	// foreach word in War and Peace, if it is in the
	// frequency table, increment frequency, otherwise
	// add to the frequency table with frequency one
	int total=0;
	String currentLine=null;
	ArrayList<Word> table = new ArrayList<Word>(); 
	
	try {
	    while ((currentLine=br.readLine()) != null) {
		// This is our definition of a word
		// splitting the line on anything non
		currentLine = currentLine.replace("--"," ");
		String[] words = currentLine.split("[^A-Za-z'-]");
		for ( String w: words ) {
		    if (w.length() == 0) {
			break;
		    }

		    w = w.toLowerCase();
		    
		    int found=0;
		    // find w in table
		    // if found, incrementCount
		    for ( Word s: table) {
			if(s.word.equals(w)) {
			    s.incrementCount();
			    found = 1;
			    break;
			}
		    }
		    
		    if(found==0) {
			// else, new Word(w,1) and add to table
			table.add(new Word(w,1));
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

	Collections.sort(table);
	return table;
    }

    private static void writeCSV(ArrayList<Word> ft, String fn, int lines) {

	PrintWriter pw = null;
	
	try {
	    pw = new PrintWriter(fn);
	} catch(FileNotFoundException e) {
	    System.out.println("Can't open a file for writing");
	}
	
	// Write out the list of frequencies
	System.out.printf("Writing to %s ... ",fn);
	for(int i = 0; i < lines; i++) {

	    Word w = ft.get(i);
	    pw.println(w.word + ", " + w.count);	   
	}

	pw.flush();
	pw.close();
	System.out.println("done!");
    }

    // Convenience function writes everything if we don't specify N
    private static void writeCSV(ArrayList<Word> ft, String fn) {
	writeCSV(ft,fn,ft.size());
    }

    
}
