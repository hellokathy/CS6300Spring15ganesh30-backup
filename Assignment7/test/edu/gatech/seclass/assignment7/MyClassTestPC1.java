package edu.gatech.seclass.assignment7;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * For the MyClass.buggyMet1(..) function, the below Unit Tests achieve 
 * - 100% Path Coverage
 * - And also reveal the 'Division by Zero' fault (test-4 reveals the error)
 */	
public class MyClassTestPC1 
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
    
    @Test
    public void test3()
    {
    	myClass.buggyMet1("Atlanta, TX");
    	assertTrue(true);
    } 
    
    @Test
    public void test4()
    {
    	myClass.buggyMet1("Houston, GA");
    	assertTrue(true);
    }     
}
