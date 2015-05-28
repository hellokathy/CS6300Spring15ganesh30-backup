package edu.gatech.seclass.assignment7;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * For the MyClass.buggyMet3(..) function, the below Unit Tests achieve 
 * - 100% Branch Coverage
 * - But does not reveal the 'Division by Zero' fault
 */	
public class MyClassTestBC3 
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
    // Tests for the function - MyClass->buggyMet3()
    //
	
    @Test
    public void test1()
    {
    	int a = 5;
    	int b = 10;
   	
    	myClass.buggyMet3(a, b);
    	assertTrue(true);
    }
    
    @Test
    public void test2()
    {
    	int a = -5;
    	int b = -10;
    	
    	myClass.buggyMet3(a, b);
    	assertTrue(true);
    }
}
