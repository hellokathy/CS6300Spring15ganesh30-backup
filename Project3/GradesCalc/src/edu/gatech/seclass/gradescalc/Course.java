package edu.gatech.seclass.gradescalc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * This class accesses both the students and grades data through the 'Student' and 'Grades'
 * classes. The Course class shall be the interface between the GradesCalc tool and the 
 * underlying databases.
 */
public class Course 
{	
	private Students m_studentsDBReader;
	private Grades m_gradesDBReader;
	private HashSet<Student> m_studentsSet;
	private List<Assignment> m_addedAssignments;
	private List<Project> m_updatedProjects;

	//ATT - Attendance
	//AAG - Average assignments grade
	//APG - Average projects grade
	//Operators - + - * /
	private String m_formula = "ATT * 0.2 + AAG * 0.4 + APG * 0.4";
	
	public String getFormula()
	{
		return m_formula;
	}
	
	public void setFormula(String formula)
	{
		m_formula = formula;
	}
	
	/**
	 * Getter for m_studentsSet
	 * 
	 * @return HashSet<Student>
	 */
	public HashSet<Student> getStudents()
	{
		return m_studentsSet;
	}
	
	/**
	 * Class constructor
	 * 
	 * @param students
	 * @param grades
	 * @throws IOException
	 */
	public Course(Students students, Grades grades)
	{
		m_studentsDBReader = students;
		m_gradesDBReader = grades;
		m_studentsSet = m_studentsDBReader.getAllStudents();
		m_addedAssignments = new ArrayList<Assignment>();
		m_updatedProjects = new ArrayList<Project>();
	}
	
	/**
	 * Returns the total number of students 
	 * 
	 * @return int
	 */
	public int getNumStudents()
	{
		return m_studentsSet.size();
	}
	
	/**
	 * Returns the 'Student' object that matches 'studentName'
	 * 
	 * @param studentName
	 * @return Student
	 */
	public Student getStudentByName(String studentName)
	{
		if((studentName == null) || studentName.equals(""))
			return null;
		else
			return SearchUtility.searchStudent(m_studentsSet, Constants.NAME, studentName);
	}
	
	/**
	 * Returns the 'Student' object that matches 'studentId'
	 * 
	 * @param studentId
	 * @return Student
	 */
	public Student getStudentByID(String studentId)
	{
		if((studentId == null) || studentId.equals(""))
			return null;
		else
			return SearchUtility.searchStudent(m_studentsSet, Constants.GTID, studentId);
	}
	
	public String getEmail(Student student)
	{
		String key = getStudentKey(student);
		String searchCriteria;
		String searchText;
		
		if(key.equals(Constants.GTID))
		{
			searchCriteria = Constants.GTID;
			searchText = student.getGtid();
		}
		else if(key.equals(Constants.NAME))
		{
			searchCriteria = Constants.NAME;
			searchText = student.getName();
		}
		else
		{
			return "";
		}
		
		Student s = SearchUtility.searchStudent(m_studentsSet, searchCriteria, searchText);
		return s.getEmail();
	}
	
	public int getAttendance(Student student)
	{
		String key = getStudentKey(student);
		String searchCriteria;
		String searchText;
		if(key.equals(Constants.GTID))
		{
			searchCriteria = Constants.GTID;
			searchText = student.getGtid();
		}
		else if(key.equals(Constants.NAME))
		{
			searchCriteria = Constants.NAME;
			searchText = student.getName();
		}
		else
		{
			return -1;
		}
		
		Student s = SearchUtility.searchStudent(m_studentsSet, searchCriteria, searchText);
		return m_gradesDBReader.getAttendance(s.getGtid());
	}
	
	public String getTeam(Student student)
	{
		String key = getStudentKey(student);
		String studentName = "";
		if(key.equals(Constants.GTID))
		{
			Student s = SearchUtility.searchStudent(m_studentsSet, Constants.GTID, student.getGtid());
			studentName = s.getName();
		}
		else if(key.equals(Constants.NAME))
		{
			studentName = student.getName();
		}
		else
		{
			return "";
		}
		
		return m_studentsDBReader.getTeamName(studentName);
	}	

	/**
	 * Returns the total number of assignments
	 * 
	 * @return int
	 */
	public int getNumAssignments()
	{
		return m_gradesDBReader.getAssignmentCount();
	}
	
	/**
	 * Returns the total number of projects
	 * 
	 * @return int
	 */
	public int getNumProjects()
	{
		return m_gradesDBReader.getProjectCount();
	}	
	
	public int getAverageAssignmentsGrade(Student student)
	{
		String key = getStudentKey(student);
		String searchCriteria;
		String searchText;
		if(key.equals(Constants.GTID))
		{
			searchCriteria = Constants.GTID;
			searchText = student.getGtid();
		}
		else if(key.equals(Constants.NAME))
		{
			searchCriteria = Constants.NAME;
			searchText = student.getName();
		}
		else
		{
			return -1;
		}		
		
		Student s = SearchUtility.searchStudent(m_studentsSet, searchCriteria, searchText);
		List<Integer> grades = m_gradesDBReader.getAssignmentGrades(s.getGtid());
		
		double average = 0.0;
		for(int i = 0; i < grades.size(); i++)
		{
			average += grades.get(i);
		}
		average /= grades.size();
		return (int)Math.round(average);
	}
	
	public int getAverageProjectsGrade(Student student)
	{
		String key = getStudentKey(student);
		String searchCriteria;
		String searchText;
		if(key.equals(Constants.GTID))
		{
			searchCriteria = Constants.GTID;
			searchText = student.getGtid();
		}
		else if(key.equals(Constants.NAME))
		{
			searchCriteria = Constants.NAME;
			searchText = student.getName();
		}
		else
		{
			return -1;
		}
		
		Student s = SearchUtility.searchStudent(m_studentsSet, searchCriteria, searchText);
		
		List<Integer> individualProjectGrades = m_gradesDBReader.getIndividualProjectGrades(s.getGtid());
		
		String teamName = m_studentsDBReader.getTeamName(s.getName());
		List<Integer> teamProjectGrades = m_gradesDBReader.getTeamProjectGrades(teamName);
		
		if(individualProjectGrades.size() != teamProjectGrades.size())
			return -1;
		
		//The average projects grade for a student S in team T should be computed by first multiplying, for 
		//each project P, team T’s project grade by the individual contribution grade of S in P, treated as a 
		//percentage, and then averaging the resulting grades.
		double average = 0.0;
		for(int i = 0; i < individualProjectGrades.size(); i++)
		{
			average += (individualProjectGrades.get(i) / 100.0) * teamProjectGrades.get(i);
		}
		average /= individualProjectGrades.size();
		
		return (int)Math.round(average);
	}	
	
	public int getOverallGrade(Student student)
	{
		//ATT - Attendance
		//AAG - Average assignments grade
		//APG - Average projects grade

		int attendance = getAttendance(student);
		int avgAssignments = getAverageAssignmentsGrade(student);
		int avgProjects = getAverageProjectsGrade(student);
		double result = 0.0;
		
		if((attendance == -1) || (avgAssignments == -1) || (avgProjects == -1))
			return -1;
					
		if((getFormula() == null) || getFormula().equals(""))
			return -1;
		
		try 
		{
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("js");
			
			String function = "function calculateGrade (ATT, AAG, APG) {c = "+ getFormula() + "; return c; }";
			engine.eval(function);
			
			Invocable jsInvoke = (Invocable)engine;
		    Object obj = jsInvoke.invokeFunction("calculateGrade", new Object[] { (double)attendance, (double)avgAssignments, 
		    		(double)avgProjects });
			
		    result = (double)obj;
		} 
		catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		}
		catch (ScriptException e) 
		{
			throw new GradeFormulaException("Wrong forumula entered! Please correct it");
		}
		
		return (int)Math.round(result);		
	}
	
	public void addAssignment(String assignmentName)
	{
		if((assignmentName == null) || assignmentName.equals(""))
			return;
		
		Assignment assignment = new Assignment();
		assignment.setName(assignmentName);		
		m_addedAssignments.add(assignment);
	}	
	
	public void addGradesForAssignment(String assignmentName, HashMap<Student, Integer> studentGrades)
	{
		if((assignmentName == null) || assignmentName.equals(""))
			return;
		
		if((studentGrades == null) || (studentGrades.size() == 0))
			return;

		//Parse the 'studentGrades' hashmap and extract the GT-IDs and the corresponding grades
		HashMap<String, Integer> grades = new HashMap<String, Integer>();
		Iterator<Entry<Student, Integer>> it = studentGrades.entrySet().iterator();
		
		while(it.hasNext())
		{
			Entry<Student, Integer> thisEntry = (Entry<Student, Integer>)it.next();
			Student s = (Student)thisEntry.getKey();
			int grade = thisEntry.getValue();
			
			String key = getStudentKey(s);
			String gtId = "";
			if(key.equals(Constants.GTID))
			{
				gtId = s.getGtid();
			}
			else if(key.equals(Constants.NAME))
			{				
				gtId = SearchUtility.searchStudent(m_studentsSet, Constants.NAME, s.getName()).getGtid();
			}
			else
			{
				System.err.println("Wrong Student object passed. Both name & gt-id are missing from the Student object");
				System.err.println(s);
			}
			
			if((gtId != null) && (!gtId.equals("")))
				grades.put(gtId, grade);
		}
		
		//check if this assignment is newly added
		int index = -1;
		boolean isPresent = false;
						
		for(int i = 0; i < m_addedAssignments.size(); i++)
		{
			if(assignmentName.equals(m_addedAssignments.get(i).getName()))
			{
				isPresent = true;
				index = i;
				break;
			}
		}		
		
		if(isPresent)
		{
			m_addedAssignments.get(index).setGrades(grades);
		}
		else
		{
			Assignment a = new Assignment();
			a.setName(assignmentName);
			a.setGrades(grades);
			m_addedAssignments.add(a);
		}		
	}	
	
	public void addIndividualContributions(String projectName, HashMap<Student, Integer> contributions)
	{
		if((projectName == null) || projectName.equals(""))
			return;
		
		if((contributions == null) || (contributions.size() == 0))
			return;
		
		List<String> projectNames = m_gradesDBReader.getProjectNames();
		if (!projectNames.contains(projectName))
		{
			System.err.println("Wrong Project Name passed. Please check");
			System.err.println(projectName);
		}
		else
		{
			//Parse the 'contributions' hashmap and extract the GT-IDs and the corresponding grades
			HashMap<String, Integer> grades = new HashMap<String, Integer>();
			Iterator<Entry<Student, Integer>> it = contributions.entrySet().iterator();
			while(it.hasNext())
			{
				Entry<Student, Integer> thisEntry = (Entry<Student, Integer>)it.next();
				Student s = (Student)thisEntry.getKey();
				int grade = thisEntry.getValue();
				
				String key = getStudentKey(s);
				String gtId = "";
				if(key.equals(Constants.GTID))
				{
					gtId = s.getGtid();
				}
				else if(key.equals(Constants.NAME))
				{				
					gtId = SearchUtility.searchStudent(m_studentsSet, Constants.NAME, s.getName()).getGtid();
				}
				else
				{
					System.err.println("Wrong Student object passed. Both name & gt-id are missing from the Student object");
					System.err.println(s);
				}
				
				if((gtId != null) && (!gtId.equals("")))
					grades.put(gtId, grade);
			}
			Project p = new Project();
			p.setName(projectName);
			p.setIndividualGrades(grades);
			m_updatedProjects.add(p);
		}
	}
	
	public void updateGrades(Grades gradesDB)
	{
		updateIndividualGradesSheet(gradesDB);
		updateIndividualContribsSheet(gradesDB);
		clearUpdatedData();
	}
	
	private void updateIndividualGradesSheet(Grades gradesDB)
	{
		List<Assignment> newAssignments = new ArrayList<Assignment>();
		List<Assignment> updatedAssignments = new ArrayList<Assignment>();
		
		for(int i = 0; i < m_addedAssignments.size(); i++)
		{
			Assignment assignment = m_addedAssignments.get(i);
			if(m_gradesDBReader.getAssignmentNames().contains(assignment.getName()))
			{
				if((assignment.getGrades() != null) && (assignment.getGrades().size() > 0))
				{
					updatedAssignments.add(assignment);
				}
			}
			else
			{
				newAssignments.add(assignment);
			}
		}

		if(updatedAssignments.size() > 0)
			gradesDB.updateAssignmentGrades(updatedAssignments);
		
		if(newAssignments.size() > 0)
			gradesDB.addNewAssignments(newAssignments);
	}
	
	private void updateIndividualContribsSheet(Grades gradesDB)
	{
		if(m_updatedProjects.size() > 0)
			gradesDB.updateIndividualProjectGrades(m_updatedProjects);
	}
	
	private void clearUpdatedData()
	{
		m_addedAssignments.clear();
		m_updatedProjects.clear();
	}
	
	public String getStudentKey(Student student)
	{
		if(student == null)			
			return "";
		
		if((student.getGtid() == null) && (student.getName() == null))
			return "";
				
		if((student.getGtid() != null) && !student.getGtid().equals("")) 
			return Constants.GTID;
		
		if((student.getName() != null) && !student.getName().equals("")) 
			return Constants.NAME;
		
		return "";
	}
	
    /////////////////////////////////////////////////
    //Logic will be implemented in future versions //
    /////////////////////////////////////////////////
	
	public Student addNewStudent(Student student)
	{
		return null;
	}
	
	public void associateStudentToTeam(Student student, String teamName)
	{		
	}
	
	public void updateStudents(Students studentsDB)
	{
	}
	
	public void addAttendance(Student student, int attendance)
	{
	}
	
	public void addAssignmentGradesForNewStudent(Student student, HashMap<String, Integer> assignmentGrades)
	{
	}
	
	public void addProjectGradesForNewStudent(Student student, HashMap<String, Integer> individualProjectGrades)
	{
	}
	
	public void addNewProject(String projectName)
	{
	}
	
	public void addTeamGrades(String teamName, String projectName, int teamGrade)
	{
	}
	
	public void addIndividualGradesForNewProject(Student student, String projectName, int individualGrade)
	{
	}
		
    //////////////////////////////////////////////////////
    //Logic will be implemented in future versions ENDS //
    //////////////////////////////////////////////////////
}
