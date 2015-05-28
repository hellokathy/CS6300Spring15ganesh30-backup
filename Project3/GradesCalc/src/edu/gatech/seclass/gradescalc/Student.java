package edu.gatech.seclass.gradescalc;

/**
 * Entity class for a 'Student' that contains the attributes associate with a student 
 */
public class Student 
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
	
	private String m_gtId;
	public String getGtid() 
	{ 
		return m_gtId; 
	}	
	public void setGtId(String gtId) 
	{ 
		m_gtId = gtId; 
	}
	
	private String m_email;
	public String getEmail()
	{
		return m_email;
	}
	public void setEmail(String email)
	{
		m_email = email;
	}
	
	public Student(String name, String gtId) 
	{
		m_name = name;
		m_gtId = gtId;
	}	
}
