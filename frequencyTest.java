import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class frequencyTest {
    @Test
    public void addWord(){
	Word w = new Word("Hello",1);
	assertEquals(1,w.count);
	assertEquals("Hello", w.word);
    }

    @Test
    public void dogEqualsDog(){
	Word w = new Word("dog",1);
	Word x = new Word("dog",1);
	boolean dogisdog = w.equals(x);
	assertEquals(true, dogisdog);
    }

    @Test
    public void catEqualsDog(){
	Word w = new Word("dog",1);
	Word x = new Word("cat",1);
	boolean catisdog = w.equals(x);
	assertEquals(false, catisdog);
    }
    
    @Test
    public void dogBeatsCat(){
	Word w = new Word("dog",2);
	Word x = new Word("cat",1);
	int dogCatAdvantage = w.compareTo(x);
	assertEquals(-1, dogCatAdvantage);
    }

    @Test
    public void incrementWorks(){
	Word w = new Word("dog",1);
	w.incrementCount();
	int dogcount = w.count;
	assertEquals(2, dogcount);
    }
    
}
