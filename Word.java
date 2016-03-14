public class Word implements Comparable<Word> {
    String word;
    int count;
    
    public Word(String w, int c) {
	word=w;
	count=c;
    }
    
    public boolean equals(Word w) {
	return word.equals(w.word);
    }
    
    public int incrementCount() {
	return ++this.count;
    }
    
    @Override
    public int compareTo(Word w) {
	return w.count - count; 
    }
}
