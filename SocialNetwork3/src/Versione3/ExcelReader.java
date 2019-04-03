package Versione3;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader{
	public static final int FIRST_PAGE=0;
	public static final String NO_VALUE="/";
	private static FileInputStream excelFile=null;
	private static Workbook workbook=null;
	private static FileOutputStream outFile=null;
	
	public static Sheet getSheet(File f, int page) {
		Sheet sheet=null;
		try {
			 excelFile= new FileInputStream(f);
			 workbook = new XSSFWorkbook(excelFile);
		     sheet = workbook.getSheetAt(page);
		}
		catch (FileNotFoundException ex) {
	 		  System.out.println(ex.getMessage());
		}
		catch (IOException ex) {
	          System.out.println(ex.getMessage());
	    }	
		return sheet;
	}
	
	public static void writeFile(File f, Sheet s) {
		try {
			outFile = new FileOutputStream(f);
			s.getWorkbook().write(outFile);
	        chiudiSicuro(outFile);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void takeRowFromFile(ArrayList <String> things, File f, int row, int page) {
		 Sheet datatypeSheet = getSheet(f, page);
		 Row currentRow = datatypeSheet.getRow(row);
		 if(currentRow!=null) {
		     Iterator<Cell> cellIterator = currentRow.cellIterator();
		     while (cellIterator.hasNext()) {
			     Cell currentCell =cellIterator.next();
			     if(!Utility.isEmpty(currentCell)) things.add(currentCell.getStringCellValue());
			 }
		 }
		 chiudi();
	}
	
	public static void takeIntRowFromFile(ArrayList <Integer> things, File f, int row, int page) {
		 Sheet datatypeSheet = getSheet(f, page);
		 Row currentRow = datatypeSheet.getRow(row);
		 if(currentRow!=null) {
			 Iterator<Cell> cellIterator = currentRow.cellIterator();
			 while (cellIterator.hasNext()) {
				 Cell currentCell =cellIterator.next();
				 if(!Utility.isEmpty(currentCell)) things.add((int) currentCell.getNumericCellValue());
			 }
		 } 	 
		 chiudi();
	}
	
	public static void addToRow(String thing, File f, int row, int page)  {
		 Sheet sheet = getSheet(f, page);
	     Row currentRow = sheet.getRow(row);
	     if(currentRow==null) currentRow=sheet.createRow(row);
	     int l=currentRow.getLastCellNum();
	     if(l==-1) l=0;
	     currentRow.createCell(l).setCellValue(thing);
	     writeFile(f, sheet); 
	     chiudi();
	}

	public static void addToRow(int thing, File f, int row, int page)  {
		Sheet sheet = getSheet(f, page);
		Row currentRow = sheet.getRow(row);
	    if(currentRow==null) currentRow=sheet.createRow(row);
	    int l=currentRow.getLastCellNum();
	    if(l==-1) l=0;
	    currentRow.createCell(l).setCellValue(thing);
	    writeFile(f, sheet); 
	    chiudi();
	}
	
	public static void creaFileXl(String fileName) {
		try {
			Workbook workbook=new XSSFWorkbook();
			Sheet sheet=workbook.createSheet();
			writeFile(new File(fileName), sheet);
			chiudiSicuro(workbook);
			}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<String> takeColumn(File f, int column, int page){ 
		ArrayList<String> columndata = new ArrayList<>(); 
		Sheet sheet =getSheet(f, page); 
		Iterator<Row> rowIterator = sheet.iterator(); 
		while (rowIterator.hasNext()) { 
			Row row = rowIterator.next(); 
			Iterator<Cell> cellIterator = row.cellIterator(); 
			while (cellIterator.hasNext()) { 
				Cell cell = cellIterator.next(); 
				if(row.getRowNum() >= 0){ 
					if(cell.getColumnIndex() == column){
						try {
							if(!Utility.isEmpty(cell)) columndata.add(cell.getStringCellValue());			
						}
						catch(Exception e) {
							if(!Utility.isEmpty(cell)) columndata.add(""+cell.getNumericCellValue());			
						}
					} 
				} 
			} 
		}
		chiudi();
		return columndata; 
	}
	
	public static void addToCell(String s, File f, int column, int row, int page) {
	     Sheet sheet = getSheet(f, page);
	     Row currentRow = takeWantedRow(row, sheet);
	     currentRow.createCell(column).setCellValue(s);	   
	     writeFile(f, sheet);
	     chiudi();
	}
	
	public static void addToCell(int s, File f, int column, int row, int page) {
		 Sheet sheet = getSheet(f, page);
	     Row currentRow = takeWantedRow(row, sheet);
	     currentRow.createCell(column).setCellValue(s);	   
	     writeFile(f, sheet);
	     chiudi();
	}
	
	public static Row takeWantedRow(int row, Sheet sheet) {
		Row currentRow = null;
		Iterator<Row> rowIterator = sheet.iterator();
		int i=0;
		while (i<=row) {
	        if(rowIterator.hasNext()) currentRow = rowIterator.next(); 
	        else currentRow=sheet.createRow(i);
	        i++;
	    }
		return currentRow;
	}
	
	public static void removeCell(int row, int column, File f, int page) { 
		 Sheet sheet = getSheet(f, page);
	     Row currentRow = takeWantedRow(row, sheet);
	     Cell cell=currentRow.getCell(column);
	     currentRow.removeCell(cell);
	     writeFile(f, sheet);
	     chiudi();
	}
	
	private static Row takeNextRow(Sheet sheet, int column) {
		Row currentRow = null;
		Iterator<Row> rowIterator = sheet.iterator();
        int i=0;
        Cell cell=null;
        while (rowIterator.hasNext()) { 
			currentRow = rowIterator.next(); 
			i++;
			cell=currentRow.getCell(column);
			if(cell==null||Utility.isEmpty(cell)) break; 
			
        }
        if(!rowIterator.hasNext()&&cell!=null&&!Utility.isEmpty(cell)) currentRow=sheet.createRow(i);
        else {
        	currentRow=sheet.getRow(i-1);
        	if (currentRow==null) currentRow=sheet.createRow(i); 
        }
        return currentRow;
	}
	
	public static void addToColumn(String s, File f, int column, int page)  {
		  Sheet sheet = getSheet(f, page);
	      Row currentRow = takeNextRow(sheet, column);
	      if(s==null) currentRow.createCell(column).setCellValue(NO_VALUE);
	      else currentRow.createCell(column).setCellValue(s);	            
	      writeFile(f, sheet);
	      chiudi();
	}
	
	public static void addToColumn(int s, File f, int column, int page)  {
		 Sheet sheet =getSheet(f, page);
	     Row currentRow = takeNextRow(sheet, column);
	     currentRow.createCell(column).setCellValue(s);	            
	     writeFile(f, sheet);
	     chiudi();
	}
	
	public static void removeRow(File f, int row, int page) {
		Sheet sheet = getSheet(f, page);
	    int lastRowNum=sheet.getLastRowNum();
	    if(row>=0&&row<lastRowNum){
	      sheet.shiftRows(row+1,lastRowNum, -1);
	    }
	    if(row==lastRowNum){
	    	Row removingRow=sheet.getRow(row);
	      if(removingRow!=null){
	    	  sheet.removeRow(removingRow);
	      }
	    }
	    writeFile(f, sheet);
		chiudi();
	}
	
	private static void chiudiSicuro(OutputStream is) {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
 
	private static void chiudiSicuro(InputStream is) {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void chiudiSicuro(Workbook is) {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void chiudi() {
		chiudiSicuro(workbook);
		chiudiSicuro(excelFile);
	}
}