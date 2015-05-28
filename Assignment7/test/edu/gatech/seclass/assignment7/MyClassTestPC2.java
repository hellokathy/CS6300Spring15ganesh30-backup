package edu.gatech.seclass.assignment7;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * For the MyClass.buggyMet2(..) function, the below Unit Tests achieve 
 * - 100% Path Coverage
 * - But do not reveal the 'Division by Zero' fault
 */	
public class MyClassTestPC2 
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
    	int b = 10;
    	int c = 30;
    	int d = 40;
    	
    	myClass.buggyMet2(a, b, c, d);
    	assertTrue(true);
    }
    
    @Test
    public void Test2()
    {
    	int a = -5;
    	int b = 10;
    	int c = 30;
    	int d = 40;
    	
    	myClass.buggyMet2(a, b, c, d);
    	assertTrue(true);    	
    }
    
    
    @Test
    public void Test3()
    {
    	int a = 5;
    	int b = -10;
    	int c = 30;
    	int d = 40;
    	
    	myClass.buggyMet2(a, b, c, d);
    	assertTrue(true);   	
    }
    
    @Test
    public void Test4()
    {
    	int a = -5;
    	int b = -10;
    	int c = 30;
    	int d = 40;
    	
    	myClass.buggyMet2(a, b, c, d);
    	assertTrue(true);    	
    }    
}
