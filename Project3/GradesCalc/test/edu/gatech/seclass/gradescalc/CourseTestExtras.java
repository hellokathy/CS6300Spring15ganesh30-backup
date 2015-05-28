package edu.gatech.seclass.gradescalc;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CourseTestExtras 
{
    Students students = null;
    Grades grades = null;
    Course course = null;
    static final String GRADES_DB = "DB" + File.separator + "GradesDatabase6300-grades.xlsx";
    static final String GRADES_DB_GOLDEN = "DB" + File.separator + "GradesDatabase6300-grades-goldenversion.xlsx";
    static final String STUDENTS_DB = "DB" + File.separator + "GradesDatabase6300-students.xlsx";
    static final String STUDENTS_DB_GOLDEN = "DB" + File.separator + "GradesDatabase6300-students-goldenversion.xlsx";

    @Before
    public void setUp() throws Exception {
        FileSystem fs = FileSystems.getDefault();
        Path gradesdbfilegolden = fs.getPath(GRADES_DB_GOLDEN);
        Path gradesdbfile = fs.getPath(GRADES_DB);
        Files.copy(gradesdbfilegolden, gradesdbfile, REPLACE_EXISTING);
        Path studentsdbfilegolden = fs.getPath(STUDENTS_DB_GOLDEN);
        Path studentsdbfile = fs.getPath(STUDENTS_DB);
        Files.copy(studentsdbfilegolden, studentsdbfile, REPLACE_EXISTING);    	
    	students = new Students(STUDENTS_DB);
        grades = new Grades(GRADES_DB);
        course = new Course(students, grades);
    }

    @After
    public void tearDown() throws Exception {
        students = null;
        grades = null;
        course = null;
    }
    
    @Test
    public void testAddStudent() {
    	//add a new student
    	Student student1 = new Student("George Bailey", "901234517");
    	student1.setEmail("gb1@gatech.edu");
    	course.addNewStudent(student1);
    	
    	course.updateStudents(new Students(GRADES_DB));
    	
    	Student s = course.getStudentByID("901234517");
    	assertEquals("George Bailey", s.getName());
    	assertEquals("gb1@gatech.edu", s.getEmail());
    	assertEquals("901234517", s.getGtid());
    	
    	//$$ - check for overwrite of student values
    }
    
    @Test
    public void testAddStudentToTeam() {
    	//associate a new student to a team
    	
    	Student student1 = new Student("Richie Benaud", "901234518");
    	student1.setEmail("richben@gatech.edu");
    	course.addNewStudent(student1);    	    	
    	course.associateStudentToTeam(student1, "Team2");
    	
    	course.updateStudents(new Students(GRADES_DB));
    	
    	Student s = course.getStudentByID("901234518");
    	assertEquals("Team2", course.getTeam(s));    	
    }
    
    @Test
    public void testAddAttendance() {
    	//Add or update attendance information
    	
    	Student student = new Student("Alan Turner", "901234519");
    	student.setEmail("aturner@gatech.edu");
    	course.addNewStudent(student);    	    	
    	course.addAttendance(student, 85);
    	
    	course.updateGrades(new Grades(GRADES_DB));
    	
    	Student s = course.getStudentByID("901234519");
    	assertEquals(85, course.getAttendance(s));
    	
    	s = course.getStudentByName("Freddie Catlay");
    	course.addAttendance(s, 95);
    	course.updateGrades(new Grades(GRADES_DB));
    	s = course.getStudentByName("Freddie Catlay");
    	assertEquals(95, course.getAttendance(s));
    }
    
    @Test
    public void testAddAssignmentGradesForNewStudent() {
    	//Add a new row in 'Individual Grades' sheet for a new student and add relevant grades
    	Student student = new Student("William Smith", "901234520");
    	student.setEmail("wsmith@gatech.edu");
    	course.addNewStudent(student);
    	
    	HashMap<String, Integer> assignmentGrades = new HashMap<String, Integer>();
    	assignmentGrades.put("Assignment1", 81);
    	assignmentGrades.put("Assignment2", 82);
    	assignmentGrades.put("Assignment3", 83);
    	course.addAssignmentGradesForNewStudent(student, assignmentGrades);
    	
    	course.updateGrades(new Grades(GRADES_DB));
    	
    	Student s = course.getStudentByID("901234520");
    	assertEquals(82, course.getAverageAssignmentsGrade(s));
    }
    
    @Test
    public void testAddIndividualContribForNewStudent() {    	
    	//Add a new row in 'Individual Contribs' sheet for a new student and add relevant grades
    	Student student = new Student("Jack Russel", "901234521");
    	student.setEmail("jrussel@gatech.edu");
    	course.addNewStudent(student);
    	
    	HashMap<String, Integer> individualProjectGrades = new HashMap<String, Integer>();
    	individualProjectGrades.put("Project1", 91);
    	individualProjectGrades.put("Project2", 92);
    	individualProjectGrades.put("Project3", 93);
    	course.addProjectGradesForNewStudent(student, individualProjectGrades);
    	
    	course.updateGrades(new Grades(GRADES_DB));
    	
    	Student s = course.getStudentByID("901234521");
    	assertEquals(92, course.getAverageProjectsGrade(s));
    }
    
    @Test
    public void testAddNewProject() {
    	course.addNewProject("Project 4");    	
    	course.updateGrades(new Grades(GRADES_DB));
    	
    	int projectCount = course.getNumProjects();
    	assertEquals(4, projectCount);
    }
    
    @Test
    public void testAddProjectGrades() {
    	//Add project grades for a new project or update it if already existing
    	course.addNewProject("Project 5");    	
    	course.updateGrades(new Grades(GRADES_DB));
    	
    	//add team grade
    	course.addTeamGrades("Team 1", "Project 5", 85);
    	course.addTeamGrades("Team 2", "Project 5", 86);
    	course.addTeamGrades("Team 3", "Project 5", 87);
    	course.addTeamGrades("Team 4", "Project 5", 88);
    	
    	//add individual contribution grades
    	Student student1 = course.getStudentByName("Shevon Wise");
    	course.addIndividualGradesForNewProject(student1, "Project 5", 91);
    	Student student2 = course.getStudentByName("Tendai Charpentier");
    	course.addIndividualGradesForNewProject(student2, "Project 5", 92);
    	
    	course.updateGrades(new Grades(GRADES_DB));
    }	
}
