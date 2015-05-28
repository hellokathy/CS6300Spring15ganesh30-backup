package edu.gatech.seclass.assignment7;

public class MyClass 
{
	public int buggyMet1(String inputString)
	{
		int a = 100;
		int b = 100;

		boolean isCityNamePresent = inputString.contains("Atlanta");
		boolean isStateNamePresent = inputString.contains("GA");
		
		if (isCityNamePresent)
		{
			a = a - 80;
		}
		else
		{
			b = b - 80;
		}
		
		if (isStateNamePresent)
		{
			b = b - 20;
		}
		else
		{
			a = a - 20;
		}
		
    	int result = a / b;
    	
    	return result;
	}
	
	public int buggyMet2(int a, int b, int c, int d)
	{
		int x = 100;
		int y = 100;
		
		if (a > 0)
		{
			x = x - c;
		}
		else
		{
			y = y - c;
		}
		
		if (b > 0)
		{
			y = y - d;
		}
		else
		{
			x = x - d;
		}
		
    	int result = x / y;
			
		return result;
	}
	
	public int buggyMet3(int a, int b)
	{
		int x = 10;
		int y = 100;
		
		if (a > 0) 
		{
			y = y - a;			
		}
		
		
		if (b > 0)
		{
			y = y - b;
		}
		
    	int result = x / y;
			
		return result;	
	}
}
