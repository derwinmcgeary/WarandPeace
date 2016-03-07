import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.regex.*;

public class counter {
    public static void main(String[] args) {
	String searchString = "the";
	String inFile="pg2600.txt";

	if(args.length == 2) {
	    searchString = args[0];
	    inFile = args[1];
	}

	FileReader fr = null;
	// Open "pg2600.txt" (or parameter number 2)
	try {
	    fr = new FileReader(inFile);
	} catch(FileNotFoundException e) {
	    System.out.printf("Argh! We can't find %s! Time to die...", inFile);
	    return;
	} finally {
	    
	}
	BufferedReader br = new BufferedReader(fr);
	// Return count of "the" (or parameter number 1)
	System.out.printf("Total number of \"%s\" in file \"%s\" is %d\n", searchString, inFile, wc(searchString, br));
    }

    public static int wc(String searchString, BufferedReader br){
	int total=0;
	String currentLine=null;

	Pattern pattern = Pattern.compile("\\b" + searchString + "\\b", Pattern.CASE_INSENSITIVE);
	
	try {
	    while ((currentLine=br.readLine()) != null) {
		Matcher  matcher = pattern.matcher(currentLine);
		int subtotal = 0;
		while (matcher.find())
		    subtotal++;
		// count the number of searchString on the line, and increment total by that much
		total+=subtotal;
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

	return total;
    }
}
