package Versione5;

import java.io.File;
import java.util.ArrayList;

public class Utente {

	private static int NUM;
	private String id;
	private String password;
	private char genere;
	private int eta;
	private int column;
	private String fileName;
	private String fascia;
	private ArrayList<String> categorie;
	
	public Utente(String id, String password, char genere, int eta, int column, String fascia, ArrayList<String> categorie) {
		this.id = id;
		this.password = password;
		this.genere = genere;
		this.eta = eta;
		this.column = column;
		this.fascia=fascia;
		this.categorie=categorie;
	}
	
	public Utente(String id, String password, char genere, int eta, int column, String fileName, String fascia, ArrayList<String> categorie) {
		this.id = id;
		this.password = password;
		this.genere = genere;
		this.eta = eta;
		this.column = column;
		this.fileName=fileName;
		this.fascia=fascia;
		this.categorie=categorie;
	}

	public Utente(String id, char genere, int eta, String fascia, ArrayList<String> categorie) {
		NUM=ExcelUtility.getUserNum();
		column=NUM;
		this.id=id;
		password =Utility.generatePass();
		ExcelUtility.creaFile(this);
		this.genere = genere;
		this.eta = eta;
		this.fascia=fascia;
		this.categorie=categorie;
	}
	
	public static Utente createUtente(String id, char genere, int eta, String fascia, ArrayList<String> categorie) {
		Utente u=new Utente(id, genere, eta, fascia, categorie);
		ExcelUtility.addUser(u);
		return u;
	}
	
	public int getColumn() {
		return column;
	}
	
	public void setColumn(int i) {
		column=i;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String s) {
		fileName=s;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
		ExcelUtility.setUserId(id, column);
	}
	
	public String getFascia() {
		return fascia;
	}
	
	public void setFascia(String fascia) {
		this.fascia = fascia;
		ExcelUtility.setUserFascia(fascia, column);
	}
	
	public ArrayList<String> getCategorie(){
		return categorie;
	}
	
	public void addCategoria(String categoria) {
		this.categorie.add(categoria);
		ExcelUtility.addCategoria(column, categoria);		
	}
	
	public void removeCategoria(int i) {
		this.categorie.remove(i);
		ExcelUtility.removeCategoria(column, i);	
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public char getGenere() {
		return genere;
	}
	
	public void setGenere(char genere) {
		this.genere = genere;
	}
	
	public int getEta() {
		return eta;
	}
	
	public File getFile() {
		return new File(fileName);
	}
	
	public void setEta(int eta) {
		this.eta = eta;
		ExcelUtility.setUserEta(eta, column);
	}
}
