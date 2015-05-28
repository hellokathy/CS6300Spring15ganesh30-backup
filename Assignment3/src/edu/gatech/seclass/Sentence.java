package edu.gatech.seclass;

//
// Class declaration and definition
//
public class Sentence implements SentenceInterface 
{
	//
	// Class Member variables
	//
	private String m_Sentence = "";
	private String[] m_WordsInSentence = new String[0];
	
	//
	// 'Set' method
	//
    public void setSentence(String sentence)
    {
    	if(sentence != null)
    	{
	    	m_Sentence = sentence;
	
	    	if(m_Sentence.trim().length() > 0)
	    		m_WordsInSentence = m_Sentence.trim().split("\\s+");
    	}
    }

    //
    // 'Get' method
    //
    public String getSentence()
    { 
    	return m_Sentence;
    }

    //
    // Returns the number of words in the current sentence
    //
    public int length()
    {
    	return m_WordsInSentence.length;    	
    }

    //
    // Returns the word at position "position" in the sentence, with 1 being the first position
    //
    public String getWord(int position) 
    		throws IllegalArgumentException, PositionOutOfBoundsException
    {
    	if (position <= 0)
    		throw new IllegalArgumentException("The position of a word should be greater than 0");
    
    	if (position > m_WordsInSentence.length)
    		throw new PositionOutOfBoundsException();

    	return m_WordsInSentence[position-1];
    }

    //
    // Returns the words from position "startPosition" to position "endPosition" in the sentence, with 1 being the first position
    //
    public String[] getWords(int startPosition, int endPosition)
            throws IllegalArgumentException, PositionOutOfBoundsException
    {
    	if (startPosition <= 0)
    		throw new IllegalArgumentException("Position of the First Word should NOT be less than or equal to 0");
    	if (endPosition <= 0)
    		throw new IllegalArgumentException("Position of the Last Word should NOT be less than or equal to 0");
    	if (startPosition > endPosition)
    		throw new IllegalArgumentException("'Position of the First Word' should always be greater than 'Position of the Last Word'");
    	
    	if (endPosition > m_WordsInSentence.length)
    		throw new PositionOutOfBoundsException();
    	
    	String[] words = new String[endPosition - startPosition + 1];
    	for (int posn = startPosition-1, i = 0; posn < endPosition; posn++, i++)
    		words[i] = m_WordsInSentence[posn];
    	
    	return words;
    }

    //
    // Returns the position of the first complete and case sensitive occurrence of word word in the sentence and -1 if the
    // word is not present
    public int indexOf(String word)
    {
    	if (word == null)
    		return -1;
    	
    	String wordToCompare = word.trim();
    	for (int i = 0; i < m_WordsInSentence.length; i++)
    	{
    		if (m_WordsInSentence[i].equals(wordToCompare))
    			return (i+1);
    	}
    	
    	return -1;
    }

    //
    // Reverses the positions of the words in the sentence, so that the first word becomes the last one, the second word 
    // becomes the one before the last word, and so on.
    //
    public void reverse()
    {
    	String[] reverseArr = new String[m_WordsInSentence.length];
    	StringBuilder sb = new StringBuilder();
    	
    	for (int posn = m_WordsInSentence.length-1, i = 0; posn >= 0; posn--, i++)
    	{
    		reverseArr[i] = m_WordsInSentence[posn]; 
    		sb.append(m_WordsInSentence[posn]);
    		sb.append(" ");    		
    	}
    	
    	m_Sentence = sb.toString().trim();
    	m_WordsInSentence = reverseArr;
    }
}
