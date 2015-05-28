package edu.gatech.seclass.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * The main activity class for the Calculator Android Application. This class handles the UI operations of the application.
 */
public class MainActivity extends Activity 
{
	private Calculator calculator = new Calculator();
	private TextView displayText;
	
	/**
	 * Along with IDE generated code, obtain a reference to the display 'TextView' control. This is an over-riden method 
	 * from the base class.
	 * 
	 * @param savedInstanceStage Variable as defined in the base class method.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		displayText = (TextView)findViewById(R.id.textResult);
	}
	
	/**
	 * Event handler for the click of any numerical value in the calculator (i.e. digits 0 - 9). Based on the number clicked,
	 * the display is updated to show operand-1/operand-2
	 * 
	 * @param view A reference to the clicked Button object : 0-9 on the calculator
	 */
	public void operandClick(View view)
	{
		String digit = ((Button)view).getText().toString();
		
		calculator.setOperandValue(digit);
		DisplayText(calculator.getOperandToDisplay());
	}

	/**
	 * Event handler for the click of an arithmetic operator in the calculator (i.e. '+' '-' '*'). Based on the operator clicked
	 * and the stage in the calculation, the display is updated to show any error, if encountered. 
	 * 
	 * @param view A reference to the clicked Button object : '+' '-' '*' on the calculator
	 */	
	public void operatorClick(View view) 
	{
		String operator = ((Button)view).getText().toString();
		
		boolean error = calculator.setOperator(operator);
		if(error)
			DisplayText(Constants.CALCULATION_ERROR);
	}

	/**
	 * Event handler for the click of '=' button in the calculator. Based on the input numbers and the arithmetic operator, 
	 * the result/error is calculated and displayed. Post display, the calculator is reset to original state so that a new
	 * calculation can be performed.
	 * 
	 * @param view A reference to the clicked Button object : '=' on the calculator
	 */	
	public void resultClick(View view) 
	{
		DisplayText(calculator.result());
		calculator.clearAllValues();
	}

	/**
	 * Event handler for the click of 'C' button in the calculator. This clears the result and all the intermediate entered data.
	 * 
	 * @param view A reference to the clicked Button object : 'C' on the calculator
	 */	
	public void clearClick(View view) 
	{
		calculator.clearAllValues();
		DisplayText(Constants.EMPTY_STRING);
	}

	/**
	 * Display the text (operand/result/error message) on the calculator
	 * 
	 * @param input String input to display
	 */		
	private void DisplayText(String input)
	{
		displayText.setText(input);
	}
}
