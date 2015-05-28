package edu.gatech.seclass.gradescalc;

import java.util.*;

/**
 * Utility functions for search within the object collections 
 */
public class SearchUtility 
{
	/**
	 * Searches the 'Student' hashSet collection object for the matching student names / 
	 * student Ids etc... and returns the matched 'Student' object;
	 * 
	 * @param studentList
	 * @param searchCriteria
	 * @param searchText
	 * @return Student
	 */
	public static Student searchStudent(HashSet<Student> studentList, String searchCriteria, 
			String searchText)
	{
		Student student = null;
		
		for(Iterator<Student> it = studentList.iterator(); it.hasNext(); )
		{
			Student s = it.next();
			
			String value = "";			
			switch(searchCriteria)
			{
			case Constants.GTID:
				value = s.getGtid();
				break;
			case Constants.NAME:
				value = s.getName();
				break;
			default:
				break;
			}
		
			if(value.equals(searchText))
			{
				student = s;
				break;
			}			
		}
		
		return student;
	}
}
