package edu.gatech.seclass.gradescalc;

import java.util.HashMap;

public class Assignment
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
	
	private HashMap<String, Integer> m_grades;
	public HashMap<String, Integer> getGrades()
	{
		return m_grades;
	}
	public void setGrades(HashMap<String, Integer> grades)
	{
		m_grades = grades;
	}	
}
