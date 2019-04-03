package Versione1;

public class Utente {

	private static final String USER = "User";
	private static int NUM;
	private String id;
	private String password;
	private char genere;
	private int eta;
	private int column;	
	
	public Utente(String id, String password, char genere, int eta, int column) {
		this.id = id;
		this.password = password;
		this.genere = genere;
		this.eta = eta;
		this.column=column;
	}

	public Utente(char genere, int eta) {
		NUM=ExcelUtility.getUserNum();
		column=NUM;
		id=USER+""+(NUM++);
		password =Utility.generatePass();
		this.genere = genere;
		this.eta = eta;
	}
	
	public static Utente createUtente(char genere, int eta) {
		Utente u=new Utente(genere, eta);
		ExcelUtility.addUser(u);
		return u;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
		ExcelUtility.setUserId(id, column);
	}
	
	public int getColumn() {
		return column;
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
	
	public void setEta(int eta) {
		this.eta = eta;
		ExcelUtility.setUserEta(eta, column);
	}
}
