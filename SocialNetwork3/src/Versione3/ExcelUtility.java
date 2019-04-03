package Versione3;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import mylib.BelleStringhe;
import mylib.InputDati;

public class ExcelUtility {

	private static final int EV_ROW=0;
	public static final int USER_ROW=0;
	public static final int PASS_ROW=1;
	public static final int GENDER_ROW=2;
	public static final int AGE_ROW=3;
	public static final int FILE_ROW=4;
	public static final int RANGE_ROW=5;
	private static final int DA_LEGGERE_COLUMN=0;
	private static final int TITLE_COLUMN=1;
	private static final int DESCRIPTION_COLUMN=2;
	private static final int EVENT_COLUMN=3;
	private static final String STATO_APERTA="APERTA";
	private static final String YES="Y";
	private static final String NO="N";
	private static final String FILE_USER_PASSWORDS="d:/Progetto Zanella/ProfiliUtenti.xlsx";
	private static final String FILE_USER_PATH="d:/Progetto Zanella/FileUtenti/";
	private static final String FILE_EXTENSION=".xlsx";
	private static final String FILE_VERSIONE="d:/Progetto Zanella/FileEventi/Versione.xlsx";
	private static final String FILE_CATEGORIE="d:/Progetto Zanella/CategorieInteresse.xlsx";
	public static File FILE=new File(FILE_USER_PASSWORDS);
	public static File FILE_CAT=new File(FILE_CATEGORIE);
	private static File FILE_VERS=new File(FILE_VERSIONE);
	private static final int VERSIONE3=3;
	private static final int ROW16=16;
	private static final int ROW15=15;
	private static final int N_SHIFT=2;
	private static final String ZERO="0";
	private static final int FIRST_PAGE=0;
	private static final String MESSAGGIO_EVENTO="\nVuoi pubblicare l'evento in bacheca?";
	private static final String EVENTO_PUBBLICATO="\nEvento pubblicato\n";
	private static final String EVENTO_NON_PUBBLICATO="\nEvento non pubblicato\n";
	private static final String GIA_ISCRITTO="\nSei già iscritto a questo evento\n";
	private static final String PART_MAX_RAGGIUNTI="\nCi dispiace, ma è già stato raggiunto il numero massimo di iscritti possibili. Ti invitiamo a tenere controllata la bacheca in caso qualcuno decida di disiscriversi";
	private static final int LARGHEZZA=50;
	
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
		String filename;
		try{
			filename=ExcelReader.takeColumn(FILE, i, FIRST_PAGE).get(FILE_ROW);
		}
		catch(Exception e) {
				Utente u=new Utente(id, passwords.get(i), genders.get(i).charAt(0), ages.get(i), i);
				creaFile(u);
				return u;
		}
		return new Utente(id, passwords.get(i), genders.get(i).charAt(0), ages.get(i), i, filename);
	}

	public static ArrayList<Evento> eventiAperti(){
		ArrayList<Evento> eventi=new ArrayList<>();
		ArrayList<String> stati=new ArrayList<>();
		ExcelReader.takeRowFromFile(stati, Evento.FILE_EV, Evento.EVENT_STATO_ROW, FIRST_PAGE);
		for(int i=0; i<stati.size(); i++) {
			if(stati.get(i).equals(STATO_APERTA)){
				eventi.add(new Evento(i));
			}
		}
		return eventi;
	}
	
	public static ArrayList<Evento> getEventiUtente(Utente u) {
		ArrayList<Evento> eventiUtente=new ArrayList<>();
		ArrayList<Evento> eventiAperti=eventiAperti();
		for(int i=0; i<eventiAperti.size(); i++) {
			ArrayList<Utente> utenti=eventiAperti.get(i).getUtenti();
			ArrayList<String> nomi=Utility.getNomiUtenti(utenti);
			for(int j=0; j<nomi.size(); j++) {
				if(nomi.get(j).equals(u.getId())) {
					eventiUtente.add(eventiAperti.get(i));
					break;
				}
			}
		}
		return eventiUtente;
	}
	
	private static int getNumEventi() {
		ArrayList<String> stati=new ArrayList<>();
		ExcelReader.takeRowFromFile(stati, Evento.FILE_EV, Evento.EVENT_STATO_ROW, FIRST_PAGE);
		return stati.size();
	}
	
	public static void writeNotify(Evento e) {
		Notifica n=new Notifica(e);
		ArrayList<Utente> utenti=e.getUtenti();
		writeNotify(n, utenti);
	}
	
	public static void writeNotify(Notifica n, ArrayList<Utente> utenti) {
		for(int i=0; i<utenti.size(); i++) {
			Utente u=utenti.get(i);
			writeNotifyUser(n, u);
		}
	}
	
	private static void writeNotifyUser(Notifica n, Utente u) {
		if(u.getFileName()==null) {
			creaFile(u);
		}
		String fileName=u.getFileName();
		ExcelReader.addToColumn(YES, new File(fileName), DA_LEGGERE_COLUMN, FIRST_PAGE);
		ExcelReader.addToColumn(n.getTitolo(), new File(fileName), TITLE_COLUMN, FIRST_PAGE);
		ExcelReader.addToColumn(n.getDescrizione(), new File(fileName), DESCRIPTION_COLUMN, FIRST_PAGE);
		ExcelReader.addToColumn(n.getEventColumn(), new File(fileName), EVENT_COLUMN, FIRST_PAGE);
	}
	
	public static void creaFile(Utente u) {
		String fileName=FILE_USER_PATH+u.getId()+FILE_EXTENSION;
		u.setFileName(fileName);
		ExcelReader.creaFileXl(fileName);
		ExcelReader.addToCell(fileName, FILE, u.getColumn(), FILE_ROW, FIRST_PAGE);
	}
	
	private static int versione() {
		return Integer.parseInt(ExcelReader.takeColumn(FILE_VERS, 0, FIRST_PAGE).get(0).substring(0, 1));
	}
	
	public static void aggiornamenti() {
		shiftCampi();
	}
	
	private static void shiftCampi() {
		 if(versione()<VERSIONE3) {
			 ExcelReader.addToCell(""+VERSIONE3, FILE_VERS, 0, 0, FIRST_PAGE);
			 File f=Evento.FILE_EV;
			 Sheet sheet = ExcelReader.getSheet(f, FIRST_PAGE);
		     sheet.shiftRows(ROW15, ROW16, N_SHIFT);
		     if(sheet.getRow(ROW15)==null) sheet.createRow(ROW15);
		     if(sheet.getRow(ROW16)==null) sheet.createRow(ROW16);
		     for(int i=0; i<getNumEventi(); i++) {
		    	 String data=ExcelReader.takeColumn(f, i, FIRST_PAGE).get(Evento.EVENT_TERM_ISCR_ROW);
		    	 sheet.getRow(ROW16).createCell(i).setCellValue(ZERO);
		         sheet.getRow(ROW15).createCell(i).setCellValue(data);
		         }
		     ExcelReader.writeFile(f, sheet);
		     ExcelReader.chiudi();
		 }
	}
	
	public static ArrayList<Notifica> visualizzaElencoNotifiche(Utente u){
		ArrayList<Notifica>notifiche=new ArrayList<>();
		File fileUser=u.getFile();
		Row currentRow=null;
	    Sheet sheet = ExcelReader.getSheet(fileUser, FIRST_PAGE);
	    Iterator<Row> rowIterator = sheet.iterator();
	    while (rowIterator.hasNext()) {
	    	currentRow = rowIterator.next();
		    String titolo=currentRow.getCell(TITLE_COLUMN).getStringCellValue();
		    String descrizione=currentRow.getCell(DESCRIPTION_COLUMN).getStringCellValue();
		    boolean nuova;
		    int column=(int)currentRow.getCell(EVENT_COLUMN).getNumericCellValue();
		    if(currentRow.getCell(DA_LEGGERE_COLUMN).getStringCellValue().equals(YES)) {
		    	nuova=true;
		    }
		    else nuova=false;
		    notifiche.add(0, new Notifica(titolo, descrizione, nuova, column));
		} 
	    ExcelReader.chiudi();
		return notifiche;
	}
	
	public static void addUser(Utente u)  {
		int column=u.getColumn();
		ExcelReader.addToColumn(u.getId(), FILE, column, FIRST_PAGE);
		ExcelReader.addToColumn(u.getPassword(), FILE, column, FIRST_PAGE);
		ExcelReader.addToColumn(""+u.getGenere(), FILE, column, FIRST_PAGE);
		ExcelReader.addToColumn(u.getEta(),FILE, column, FIRST_PAGE);
	}
	
	public static void createEvento(Evento e, Utente u) {
		e.inizializzaEvento(u);
		boolean sure=InputDati.yesOrNo(MESSAGGIO_EVENTO);
		if(sure) {
			e.writeEvent();
			e.addUtente(u);
			System.out.println("\n"+EVENTO_PUBBLICATO);
		}
		else System.out.println("\n"+EVENTO_NON_PUBBLICATO);
	}
	
	private static boolean iscrAccettabile(Utente u, Evento e) {
		int column=e.getColumn();
		ArrayList<String> utentiIscr=ExcelReader.takeColumn(Evento.FILE_UT_EV, column, FIRST_PAGE);
		boolean ok=mylib.Utility.accettabile(utentiIscr, u.getId(), GIA_ISCRITTO)&&
				e.getUtenti().size()<e.getPartMax();
		if(e.getUtenti().size()==e.getPartMax()) System.out.println(BelleStringhe.aCapo(PART_MAX_RAGGIUNTI, LARGHEZZA));
		return ok;
	}
	
	public static boolean partecipa(Evento e, Utente u) {
		boolean ok=iscrAccettabile(u, e);
		if(ok) {
			e.addUtente(u);
			if(e.getUtenti().size()>=e.getNumPart()) {
				e.modificaStato();
			}
			return true;
		}
		return false;
	}
	
	public static String takeNomeEvento(int colonna) {
		return ExcelReader.takeColumn(Evento.FILE_EV, colonna, FIRST_PAGE).get(Evento.EVENT_NOME_ROW);
	}
	
	public static void modificaStatiEventi() {
		int currentColumn=0;
		ArrayList<String>  column=ExcelReader.takeColumn(Evento.FILE_EV, currentColumn, FIRST_PAGE);
		while(column.size()!=0) {
			Evento e=new Evento(currentColumn);
			e.modificaStato();
			currentColumn+=1;
			column=ExcelReader.takeColumn(Evento.FILE_EV, currentColumn, FIRST_PAGE);
		}
	}
	
	public static void NotifyRead(Notifica n, int row, Utente u) {
		n.setNuova(false);
		ExcelReader.addToCell(NO, new File(u.getFileName()), DA_LEGGERE_COLUMN, row, FIRST_PAGE);
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
	
	public static int getEventNum() {
		return getNum(Evento.FILE_EV, EV_ROW);
	}
	
	public static void setUserEta(int eta, int column) {
		ExcelReader.addToCell(eta, FILE, column, AGE_ROW, FIRST_PAGE);
	}
	
	public static void setUserId(String id, int column) {
		ExcelReader.addToCell(id, FILE, column, USER_ROW, FIRST_PAGE);
	}
}
