package edu.gatech.seclass.assignment7;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * For the MyClass.buggyMet2(..) function, the below Unit Tests achieve 
 * - 100% Branch Coverage but not 100% path coverage
 * - Reveal the 'Division by Zero' fault
 */	
public class MyClassTestBC2 
{
	private MyClass myClass;
	
    //
    // Initialization and Cleanup
    //
    
    @Before
    public void setUp() throws Exception {
        myClass = new MyClass();
    }

    @After
    public void tearDown() throws Exception {
        myClass = null;
    }	
	
    //
    // Tests for the function - MyClass->buggyMet2()
    //

    @Test
    public void test1()
    {
    	int a = 5;
    	int b = -10;
    	int c = 20;
    	int d = 80;
    	
    	myClass.buggyMet2(a, b, c, d);
    	assertTrue(true);
    }
    
    @Test
    public void test2()
    {
    	int a = -5;
    	int b = 10;
    	int c = 20;
    	int d = 80;

    	myClass.buggyMet2(a, b, c, d);
    	assertTrue(true);
    }
}
