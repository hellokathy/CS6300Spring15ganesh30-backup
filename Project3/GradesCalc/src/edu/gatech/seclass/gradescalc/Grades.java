package edu.gatech.seclass.gradescalc;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

/**
 * This class accesses the database with the grades information and makes it available
 */
public class Grades 
{
	private String m_gradesDBURL;

	/**
	 * Class constructor
	 * 
	 * @param gradesDB
	 */
	public Grades(String gradesDB)
	{
		m_gradesDBURL = gradesDB;
	}
	
	public int getAttendance(String gtId)
	{
		List<Integer> attendance = getRowValues(CellDataType.NUMERIC, gtId, 
				Constants.ATTENDANCE_SHEET);
		
		if(attendance.size() == 1)
			return attendance.get(0);
		else
			return -1;
	}
	
	public List<String> getAssignmentNames()
	{
		return extractColumnNames(Constants.INDIVIDUAL_GRADES_SHEET);
	}
	
	/**
	 * Returns the total number of assignments
	 * 
	 * @return int
	 */
	public int getAssignmentCount()
	{
		return extractColumnNames(Constants.INDIVIDUAL_GRADES_SHEET).size();
	}
	
	public List<String> getProjectNames()
	{
		return extractColumnNames(Constants.INDIVIDUAL_CONTRIBS_SHEET);
	}
	
	/**
	 * Returns the total number of projects
	 * 
	 * @return int
	 */
	public int getProjectCount()
	{
		return extractColumnNames(Constants.INDIVIDUAL_CONTRIBS_SHEET).size();
	}
	
	private List<String> extractColumnNames(String worksheet) 
	{
		List<String> columnNames = new ArrayList<String>();		
		try
		{
			FileInputStream file = new FileInputStream(new File(m_gradesDBURL));
			XSSFWorkbook workbook = new XSSFWorkbook(file);			
			XSSFSheet sheet = workbook.getSheet(worksheet);
			Iterator<Row> rowIterator = sheet.iterator();
			Row row = rowIterator.next();
			
			//first column ignored
			Iterator<Cell> cellIterator = row.cellIterator();
			cellIterator.next();
			while(cellIterator.hasNext())
			{
				Cell cell = cellIterator.next();
				columnNames.add(cell.getStringCellValue());
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
		
		return columnNames;
	}
	
	public List<Integer> getAssignmentGrades(String gtId)
	{
		return getRowValues(CellDataType.NUMERIC, gtId, Constants.INDIVIDUAL_GRADES_SHEET);
	}
	
	public List<Integer> getIndividualProjectGrades(String gtId)
	{
		return getRowValues(CellDataType.NUMERIC, gtId, Constants.INDIVIDUAL_CONTRIBS_SHEET);
	}
	
	public List<Integer> getTeamProjectGrades(String teamName)
	{
		return getRowValues(CellDataType.STRING, teamName, Constants.TEAM_GRADES_SHEET);
	}
	
	private List<Integer> getRowValues(CellDataType cellDataType, String key, String worksheet)
	{
		List<Integer> grades = new ArrayList<Integer>();		
		try
		{
			FileInputStream file = new FileInputStream(new File(m_gradesDBURL));
			XSSFWorkbook workbook = new XSSFWorkbook(file);			
			XSSFSheet teamsSheet = workbook.getSheet(worksheet);
			Iterator<Row> rowIterator = teamsSheet.iterator();
			rowIterator.next();
			
			while(rowIterator.hasNext())
			{
				Row row = rowIterator.next();				
				Iterator<Cell> cellIterator = row.cellIterator();
				Cell cell = cellIterator.next();
				
				String cellValue = "";
				if(cellDataType == CellDataType.NUMERIC)
				{
					cellValue = Integer.toString((int)cell.getNumericCellValue());
				}
				else if(cellDataType == CellDataType.STRING)
				{
					cellValue = cell.getStringCellValue();
				}
				
				if(cellValue.equals(key))
				{
					while(cellIterator.hasNext())
					{
						cell = cellIterator.next();
						grades.add((int)cell.getNumericCellValue());
					}
					break;
				}
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
		
		return grades;
	}
	
	public void updateAssignmentGrades(List<Assignment> updatedAssignments)
	{
		try
		{
			FileInputStream file = new FileInputStream(new File(m_gradesDBURL));
			XSSFWorkbook workbook = new XSSFWorkbook(file);			
			XSSFSheet sheet = workbook.getSheet(Constants.INDIVIDUAL_GRADES_SHEET);			
			Iterator<Row> rowIterator = sheet.iterator();
			Row headerRow = rowIterator.next();
				
			//parse through all assignments that need to be updated
			for(int i = 0; i < updatedAssignments.size(); i++)
			{
				Assignment a = updatedAssignments.get(i);

				//first header-column ignored
				Iterator<Cell> cellIterator = headerRow.cellIterator();
				cellIterator.next();
				int columnIndex = 0;
				
				//check the column to be updated for the assignment. Parse through header row
				while(cellIterator.hasNext())
				{
					Cell cell = cellIterator.next();
					columnIndex++;
					if(cell.getStringCellValue().equals(a.getName()))
					{
						//update the grades for the assignment. Parse through 'grades' list object
						Iterator<Entry<String, Integer>> it = a.getGrades().entrySet().iterator();
						while(it.hasNext())
						{
							Entry<String, Integer> thisEntry = (Entry<String, Integer>)it.next();							
							String gtId = thisEntry.getKey();
							int grade = thisEntry.getValue();
							
							//parse through each row in the sheet to find the right row to be updated
							Iterator<Row> rowUpdateIterator = sheet.iterator();
							Row row = rowUpdateIterator.next();
							while(rowUpdateIterator.hasNext())
							{
								row = rowUpdateIterator.next();
								Cell gtIdCell = row.getCell(0);
								if(Integer.toString((int)gtIdCell.getNumericCellValue()).equals(gtId))
								{
									if(row.getCell(columnIndex) == null)
									{
										cell = row.createCell(columnIndex);
										cell.setCellValue(grade);
									}
								}
							}
						}						
						break;
					}
				}
			}
			
			file.close();			
			FileOutputStream outFile = new FileOutputStream(new File(m_gradesDBURL));
			workbook.write(outFile);
			outFile.close();			
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}			
	}
	
	public void addNewAssignments(List<Assignment> newAssignments)
	{
		try
		{
			FileInputStream file = new FileInputStream(new File(m_gradesDBURL));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheet(Constants.INDIVIDUAL_GRADES_SHEET);	
			Iterator<Row> rowIterator = sheet.iterator();
			Row headerRow = rowIterator.next();
			
			int initialAssignmentCount = this.getAssignmentCount();
			
			for(int i = 0; i < newAssignments.size(); i++)
			{
				//Create assignment name header cell
				Assignment a = newAssignments.get(i); 
				Cell cell = headerRow.createCell(initialAssignmentCount + i + 1);
				cell.setCellValue(a.getName());
				
				if((a.getGrades() != null) && (a.getGrades().size() > 0))
				{
					//add the grades for the new assignment
					Iterator<Entry<String, Integer>> it = a.getGrades().entrySet().iterator();
					while(it.hasNext())
					{
						Entry<String, Integer> thisEntry = (Entry<String, Integer>)it.next();
						String gtId = thisEntry.getKey();
						int grade = thisEntry.getValue();
						
						//parse through each row in the sheet to find the right row to be updated
						Iterator<Row> rowUpdateIterator = sheet.iterator();
						Row row = rowUpdateIterator.next();
						while(rowUpdateIterator.hasNext())
						{
							row = rowUpdateIterator.next();
							Cell gtIdCell = row.getCell(0);
							if((Integer.toString(((int)gtIdCell.getNumericCellValue())).equals(gtId)))
							{
								cell = row.createCell(initialAssignmentCount + i + 1);
								cell.setCellValue(grade);
								break;
							}
						}
					}
				}
			}
			
			file.close();
			
			FileOutputStream outFile = new FileOutputStream(new File(m_gradesDBURL));
			workbook.write(outFile);
			outFile.close();
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void updateIndividualProjectGrades(List<Project> updatedProjects)
	{
		try
		{
			FileInputStream file = new FileInputStream(new File(m_gradesDBURL));
			XSSFWorkbook workbook = new XSSFWorkbook(file);			
			XSSFSheet sheet = workbook.getSheet(Constants.INDIVIDUAL_CONTRIBS_SHEET);			
			Iterator<Row> rowIterator = sheet.iterator();
			Row headerRow = rowIterator.next();
				
			//parse through all projects that need to be updated
			for(int i = 0; i < updatedProjects.size(); i++)
			{
				Project p = updatedProjects.get(i);

				//first header-column ignored
				Iterator<Cell> cellIterator = headerRow.cellIterator();
				cellIterator.next();
				int columnIndex = 0;
				
				//check the column to be updated for the project. Parse through header row				
				while(cellIterator.hasNext())
				{
					Cell cell = cellIterator.next();
					columnIndex++;
					if(cell.getStringCellValue().equals(p.getName()))
					{
						//update the grades for the project. Parse through 'grades' list object
						Iterator<Entry<String, Integer>> it = p.getIndividualGrades().entrySet().iterator();
						while(it.hasNext())
						{
							Entry<String, Integer> thisEntry = (Entry<String, Integer>)it.next();							
							String gtId = thisEntry.getKey();
							int grade = thisEntry.getValue();
							
							//parse through each row in the sheet to find the right row to be updated
							Iterator<Row> rowUpdateIterator = sheet.iterator();
							Row row = rowUpdateIterator.next();
							while(rowUpdateIterator.hasNext())
							{
								row = rowUpdateIterator.next();
								Cell gtIdCell = row.getCell(0);
								if((Integer.toString(((int)gtIdCell.getNumericCellValue())).equals(gtId)))
								{
									row.getCell(columnIndex).setCellValue(grade);
									break;
								}
							}
						}						
						break;
					}
				}
			}
			
			file.close();
			
			FileOutputStream outFile = new FileOutputStream(new File(m_gradesDBURL));
			workbook.write(outFile);
			outFile.close();			
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}				
	}
}
