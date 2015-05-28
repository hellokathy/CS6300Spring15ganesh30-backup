package edu.gatech.seclass.assignment7;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * For the MyClass.buggyMet1(..) function, the below Unit Tests achieve 
 * - 100% Branch Coverage
 * - But do not reveal the 'Division by Zero' fault
 */	
public class MyClassTestBC1 
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
    // Tests for the function - MyClass->buggyMet1()
    //    
    
    @Test
    public void test1()
    {
    	myClass.buggyMet1("Atlanta, GA");
    	assertTrue(true);
    }
    
    @Test
    public void test2()
    {
    	myClass.buggyMet1("Houston, TX");
    	assertTrue(true);
    }
}
