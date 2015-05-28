package edu.gatech.seclass.gradescalc;

import java.util.HashMap;

public class Project
{
	private String m_name;
	public String getName()
	{
		return m_name;
	}
	public void setName(String name)
	{
		m_name = name;
	}
	
	private HashMap<String, Integer> m_individualGrades;
	public HashMap<String, Integer> getIndividualGrades()
	{
		return m_individualGrades;
	}
	public void setIndividualGrades(HashMap<String, Integer> grades)
	{
		m_individualGrades = grades;
	}
}
