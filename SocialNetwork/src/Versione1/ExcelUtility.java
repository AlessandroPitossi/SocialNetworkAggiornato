package Versione1;

import java.io.File;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ExcelUtility {
	
	private static final String FILE_USER_PASSWORDS="d:/Progetto Zanella/prova.xml.xlsx";
	public static File FILE=new File(FILE_USER_PASSWORDS);
	private static final int FIRST_PAGE=0;
	public static final int USER_ROW=0;
	public static final int PASS_ROW=1;
	public static final int GENDER_ROW=2;
	public static final int AGE_ROW=3;

	public static void takeIds(ArrayList <String> ids) {
		ExcelReader.takeRowFromFile(ids, FILE, USER_ROW, FIRST_PAGE);
	}
	
	public static void takePasswords(ArrayList <String> passwords) {
		ExcelReader.takeRowFromFile(passwords, FILE, PASS_ROW, FIRST_PAGE);
	}
	
	public static void takeGenders(ArrayList <String> genders) {
		ExcelReader.takeRowFromFile(genders, FILE, GENDER_ROW, FIRST_PAGE);
	}
	
	public static void takeAges(ArrayList <Integer> ages) {
		ExcelReader.takeIntRowFromFile(ages, FILE, AGE_ROW, FIRST_PAGE);
	}
	
	public static Utente takeUser(String id) {
		ArrayList<String> ids=new ArrayList<>();
		ArrayList<String> passwords=new ArrayList<>();
		ArrayList<String> genders=new ArrayList<>();
		ArrayList<Integer> ages=new ArrayList<>();
		takeIds(ids);
		takePasswords(passwords);
		takeGenders(genders);
		takeAges(ages);
		int i=ids.indexOf(id);
		return new Utente(id, passwords.get(i), genders.get(i).charAt(0), ages.get(i), i);
	}
	
	public static void addUser(Utente u)  {
		int column=u.getColumn();
		ExcelReader.addToColumn(u.getId(), FILE, column, FIRST_PAGE);
		ExcelReader.addToColumn(u.getPassword(), FILE, column, FIRST_PAGE);
		ExcelReader.addToColumn(""+u.getGenere(), FILE, column, FIRST_PAGE);
		ExcelReader.addToColumn(u.getEta(),FILE, column, FIRST_PAGE);
	}
	
	private static int getNum(File f, int row) {
		int l=-1;
		Sheet sheet = ExcelReader.getSheet(f, FIRST_PAGE);
	    Row currentRow = ExcelReader.takeWantedRow(row, sheet);
	    if(currentRow==null) currentRow=sheet.createRow(row);
	    l=currentRow.getLastCellNum();
	    if(l==-1) l=0;
	    ExcelReader.chiudi();
		return l;
		}
	
	public static int getUserNum() {
		return getNum(FILE, USER_ROW);
	}
	
	public static void setUserEta(int eta, int column) {
		ExcelReader.addToCell(eta, FILE, column, AGE_ROW, FIRST_PAGE);
	}
	
	public static void setUserId(String id, int column) {
		ExcelReader.addToCell(id, FILE, column, USER_ROW, FIRST_PAGE);
	}
}
