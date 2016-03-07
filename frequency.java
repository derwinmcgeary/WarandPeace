import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
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
	String inFile="pg2600.txt";
	FileReader fr = null;
	BufferedReader br = null;
	try {
	    fr = new FileReader(inFile);
	    br = new BufferedReader(fr);
	} catch(FileNotFoundException e) {
	    System.out.printf("Argh! We can't find %s! Time to die...", inFile);
	    return;
	} 

	ArrayList<Word> ft = freqTable(br);
	for(int i=0; i <10; i++) {
	    System.out.println(ft.get(i).word + ", " + ft.get(i).count);
	}

	writeCSV(ft, "freqtable.txt");
	
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
		String[] words = currentLine.split("[^A-Za-z]");
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

	return table;
    }

    private static void writeCSV(ArrayList<Word> ft, String fn) {

	File f = new File(fn);
	PrintWriter pw = null;
	
	try(FileWriter fw = new FileWriter(f, true)) {
	    pw = new PrintWriter(fw);
	} catch(IOException e) {
	    System.out.println("Can't open a file for writing");
	}
	
	// Write out the list of frequencies
	for(Word w: ft) {
	    System.out.println(w.word + ", " + w.count);
	    pw.println(w.word + ", " + w.count);	   
	}
	
	pw.close();
    }
}
