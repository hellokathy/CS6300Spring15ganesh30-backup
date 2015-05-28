package edu.gatech.seclass;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SentenceTest {

    private Sentence sentence;

    //
    // Initialization and Cleanup
    //
    
    @Before
    public void setUp() throws Exception {
        sentence = new Sentence();
    }

    @After
    public void tearDown() throws Exception {
        sentence = null;
    }

    //
    // Tests for the function - SentenceInterface->getWord()
    //
    
    @Test
    public void testGetWord1() throws IllegalArgumentException,
            PositionOutOfBoundsException {
        sentence.setSentence("This is a short sentence");
        String word = sentence.getWord(2);
        assertEquals("is", word);
    }

    @SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
    public void testGetWord2() throws IllegalArgumentException,
            PositionOutOfBoundsException {
        sentence.setSentence("This is a sentence");
        String word = sentence.getWord(-5);
    }
    
    @SuppressWarnings("unused")
	@Test(expected = PositionOutOfBoundsException.class)
    public void testGetWord3() throws IllegalArgumentException,
    		PositionOutOfBoundsException {
    	sentence.setSentence("This is a sentence   ");
    	String word = sentence.getWord(5);
    }
    
    //
    // Tests for the function - SentenceInterface->getWords()
    //    
    
    @Test
    public void testGetWords1() throws IllegalArgumentException,
            PositionOutOfBoundsException {
        sentence.setSentence("This assignment   allows submissions  using   the text box below only");
        String[] words = sentence.getWords(4, 8);
        
        assertEquals(5, words.length);
        assertEquals("submissions", words[0]);
        assertEquals("using", words[1]);
        assertEquals("the", words[2]);
        assertEquals("text", words[3]);
        assertEquals("box", words[4]);
    }

    @Test(expected = PositionOutOfBoundsException.class)
    public void testGetWords2() throws IllegalArgumentException,
            PositionOutOfBoundsException {
        sentence.setSentence("Another short sentence");
        sentence.getWords(3, 4);
    }
    
    @Test
    public void testGetWords3() throws IllegalArgumentException,
            PositionOutOfBoundsException {
        sentence.setSentence("This assignment   allows submissions  using   the text box below only");
        String[] words = sentence.getWords(9, 10);
        
        assertEquals(2, words.length);
        assertEquals("below", words[0]);
        assertEquals("only", words[1]);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetWords4() throws IllegalArgumentException,
            PositionOutOfBoundsException {
    	sentence.setSentence("Let's go for a walk");
    	sentence.getWords(0, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetWords5() throws IllegalArgumentException,
            PositionOutOfBoundsException {
    	sentence.setSentence("Let's run");
    	sentence.getWords(1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetWords6() throws IllegalArgumentException,
            PositionOutOfBoundsException {
    	sentence.setSentence("This is a great movie");
    	sentence.getWords(4, 3);
    }

    //
    // Tests for the function - SentenceInterface->length()
    //    
    
    @Test
    public void testGetLength1() {
        sentence.setSentence("   T-Square is a    Collaboration  and Learning    Environment      ");
        int length = sentence.length();
        assertEquals(7, length);
    }

    @Test
    public void testGetLength2() {
        sentence.setSentence("");
        int length = sentence.length();
        assertEquals(0, length);
    }
    
    @Test
    public void testGetLength3() {
        sentence.setSentence("What a wonderful weather ! But this is not the same tomorrow :-( .");
        int length = sentence.length();
        assertEquals(14, length);
    }
    
    @Test
    public void testGetLength4() {
    	//null check
        sentence.setSentence(null);
        int length = sentence.length();
        assertEquals(0, length);
    }    
    
    //
    // Tests for the function - SentenceInterface->indexOf()
    //    
    
    @Test
    public void testIndexOf1() {
        sentence.setSentence("This is a short sentence");
        assertEquals(5, sentence.indexOf("sentence"));
    }

    @Test
    public void testIndexOf2() {
        sentence.setSentence("This is a sentence");
        assertEquals(-1, sentence.indexOf("short"));
    }
    
    @Test
    public void testIndexOf3() {
    	sentence.setSentence("This is great !");
    	assertEquals(4, sentence.indexOf("!"));
    }
    
    @Test
    public void testIndexOf4() {
    	//null check
    	assertEquals(-1, sentence.indexOf(null));
    }    

    //
    // Tests for the function - SentenceInterface->reverse()
    //    
    
    @Test
    public void testReverse1() {
        sentence.setSentence("This is a short sentence");
        sentence.reverse();
        assertEquals("sentence short a is This", sentence.getSentence());
    }

    @Test
    public void testReverse2() {
        sentence.setSentence("Smallest sentencccccccce !!");
        sentence.reverse();
        assertEquals("!! sentencccccccce Smallest", sentence.getSentence());
    }
    
    @Test
    public void testReverse3() {
        sentence.setSentence("One-Word-Sentence");
        sentence.reverse();
        assertEquals("One-Word-Sentence", sentence.getSentence());
    }  
    
    @Test
    public void testReverse4() {
        sentence.setSentence("dog kicks dog");
        sentence.reverse();
        assertEquals("dog kicks dog", sentence.getSentence());
    }
        
    @Test
    public void testReverse5() {
        //null check
    	sentence.setSentence(null);
        sentence.reverse();
        assertEquals("", sentence.getSentence());    
    }      
}
