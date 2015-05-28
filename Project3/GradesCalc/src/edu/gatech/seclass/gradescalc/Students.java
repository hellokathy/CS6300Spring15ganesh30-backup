package edu.gatech.seclass.gradescalc;

import java.io.*;
import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

/**
 * This class accesses the database with the students information and makes it available
 */
public class Students 
{
	private String m_studentsDBURL;
	
	/**
	 * Class constructor
	 * 
	 * @param studentsDB
	 * @throws IOException
	 */
	public Students(String studentsDBURL) 
	{
		m_studentsDBURL = studentsDBURL;
	}
	
	/**
	 * Parse the 'Students' spreadsheet database
	 * 
	 * @param studentsList
	 * @throws IOException
	 */
	public HashSet<Student> getAllStudents()
	{
		HashSet<Student> studentsList = new HashSet<Student>();
		try 
		{
			FileInputStream file = new FileInputStream(new File(m_studentsDBURL));
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			//Read 'StudentsInfo' worksheet in the Excel DB. Skip the first row as it is the 
			//heading row
			XSSFSheet studentInfoSheet = workbook.getSheet(Constants.STUDENTS_INFO_SHEET);
			Iterator<Row> rowIterator = studentInfoSheet.iterator();
			rowIterator.next();
			while(rowIterator.hasNext())
			{
				Row row = rowIterator.next();
			
				String name = row.getCell(0).getStringCellValue();
				String gtId = Integer.toString((int)row.getCell(1).getNumericCellValue());
				String email = row.getCell(2).getStringCellValue();
				
				Student student = new Student(name, gtId);
				student.setEmail(email);
				studentsList.add(student);
			}
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return studentsList;
	}
	
	public String getTeamName(String studentName)
	{
		String teamName = "";
		
		try
		{
			FileInputStream file = new FileInputStream(new File(m_studentsDBURL));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			
			//Read 'Teams' worksheet in the Excel DB. While reading, skip the first row as it is the heading row
			XSSFSheet teamsSheet = workbook.getSheet(Constants.TEAMS_SHEET);
			Iterator<Row> rowIterator = teamsSheet.iterator();
			rowIterator.next();
			
			while(rowIterator.hasNext())
			{
				Row row = rowIterator.next();
				
				Iterator<Cell> cellIterator = row.cellIterator();
				Cell cell = cellIterator.next();	
				teamName = cell.getStringCellValue();
				
				boolean match = false;
				while(cellIterator.hasNext())
				{
					cell = cellIterator.next();
					if(cell.getStringCellValue().equals(studentName))
					{
						match = true;
						break;
					}
				}
				if (match)
					break;
			}
			
			file.close();
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}	
		
		return teamName;
	}
}
