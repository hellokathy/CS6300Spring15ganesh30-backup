package edu.gatech.seclass.calculator;

import java.math.BigInteger;

/**
 * Performs the various operations of the Calculator
 */
public class Calculator 
{
	private String firstOperand;
	private String secondOperand;
	private Operations operator;
	
	/**
	 * Initialize the 1st, 2nd operand and the arithmetic operator
	 */
	public Calculator() 
	{
		firstOperand = Constants.EMPTY_STRING;
		secondOperand = Constants.EMPTY_STRING;
		operator = null;
	}

	/**
	 * Append the newly entered digit to the current operand - operand1/operand2
	 * 
	 * @param newDigit New digit that is entered
	 */
	public void setOperandValue(String newDigit)
	{
		//if the operator is not entered, the user is still entering the first number. If not, the second number is entered
		if (operator == null)
			firstOperand = calculateOperand(firstOperand, newDigit);
		else
			secondOperand = calculateOperand(secondOperand, newDigit);
	}

	/**
	 * Append the newly entered digit to the current operand - operand1/operand2. Handles leading zeroes and also checks for 
	 * maximum 'Long' value which is a limitation set by this calculator. 
	 * 
	 * @param operand Number to which the new digit has to be appended
	 * @param newDigit New digit that is entered
	 * @return New number based on the addition of the new digit
	 */
	private String calculateOperand(String operand, String newDigit)
	{		
		String number = operand;
		
		//Handle leading zeroes
		if(number.equals("0"))
		{
			if ( !newDigit.equals("0"))
				number = newDigit;
		}
		else
		{
			//handle numbers up to 'Max Long Value' which is a limitation set by this calculator
			BigInteger newValue = new BigInteger(number.concat(newDigit));
			
			if (newValue.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) < 1)
				number = newValue.toString();
		}
		return number;
	}
	
	/**
	 * Returns the operand to be displayed on the calculator screen
	 * 
	 * @return First or Second operand based on the context
	 */
	public String getOperandToDisplay()
	{
		//if the operator is not entered, display the first number. If not, display the second number		
		if (operator == null)
			return firstOperand;
		else
			return secondOperand;
	}
	
	/**
	 * Set the arithmetic operator ('+' '-' '*') as entered from the UI.
	 * 
	 * @param arithmeticOperator Arithmetic operator passed in as string
	 * 
	 * @return Error Status True/False.
	 * Sample errors : Entering two operators in a calculation, Pressing a button other than "=" when the two operands and the 
	 * Operator have been entered. 
	 */
	public boolean setOperator(String arithmeticOperator) 
	{
		if((firstOperand.equals(Constants.EMPTY_STRING)) || (operator != null))
		{
			clearAllValues();
			return true;
		}
		
		if (arithmeticOperator.equals(Constants.ADDITION_OPERATOR))
			operator = Operations.ADD;
		else if (arithmeticOperator.equals(Constants.SUBTRACTION_OPERATOR))
			operator = Operations.SUBTRACT;
		else if (arithmeticOperator.equals(Constants.MULTIPLICATION_OPERATOR))
			operator = Operations.MULTIPLY;
		
		return false;
	}	
	
	/**
	 * Based on the selected arithmetic operator selected, perform calculation and return result. If there is an erroneous
	 * situation (example - result is bigger and 'Long'), returns error.
	 * 
	 * @return Calculated result
	 */
	public String result() 
	{
		if((firstOperand.equals(Constants.EMPTY_STRING)) || (secondOperand.equals(Constants.EMPTY_STRING)) || (operator == null))
		{
			clearAllValues();
			return Constants.CALCULATION_ERROR;
		}
		
		BigInteger num1 = BigInteger.valueOf(Long.parseLong(firstOperand));
		BigInteger num2 = BigInteger.valueOf(Long.parseLong(secondOperand));
		BigInteger result = BigInteger.ZERO;
		
		switch(operator) 
		{
			case ADD:
				result = num1.add(num2); 
				break;
				
			case SUBTRACT:
				result = num1.subtract(num2);
				break;
				
			case MULTIPLY:
				result = num1.multiply(num2);
				break;
		}

		//Check if the result is bigger than 'maximum long value' or 'minimum long value'
		if ((result.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) == 1) || (result.compareTo(BigInteger.valueOf(Long.MIN_VALUE)) == -1))
			return Constants.CALCULATION_ERROR;
		else
			return result.toString();
	}
		
	/**
	 * Clears all the operands and operator by setting them to initial state
	 */
	public void clearAllValues() 
	{
		firstOperand = Constants.EMPTY_STRING;
		secondOperand = Constants.EMPTY_STRING;
		operator = null;
	}	
}
