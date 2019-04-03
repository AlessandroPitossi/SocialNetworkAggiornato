package Versione1;

import java.util.ArrayList;

import mylib.BelleStringhe;
import mylib.InputDati;
import mylib.MyMenu;

public class Menu {
	private static final String LOG_IN="Log In";
	private static final String VOCI_LOG_IN[]= {"Accedi", "Registrati"};
	private static final String MSG_USER="Inserisci il tuo id utente: ";
	private static final String MSG_PASSWORD="Inserisci la tua password: ";
	private static final String MSG_ERR_LOG_IN="Utente o password errati";
	private static final String MSG_GENDER="Sei maschio (M) o femmina (F)?";
	private static final String CHAR_AMMISSIBILI="MF";
	private static final String MSG_ETA="Inserisci la tua età (minimo 14 anni, massimo 130)";
	private static final String ID="Id:";
	private static final String PASSWORD="Password:";
	private static final String OP_ANNULLATA="Operazione annullata";
	private static final int MIN=14;
	private static final int MAX=130;
	private static final String CONSENSO="Confermi di voler procedere?";
	private static final String BENVENUTO="Benvenuto nella home page";
	private static final String HOME_PAGE="Home Page";
	private static final String VOCI_HOME_PAGE[]= {"Mostra categorie"};
	private static final String ARRIVEDERCI="Arrivederci, torna a visitare il nostro social!";
	
	public static Utente menuLogIn() {
		MyMenu m=new MyMenu(LOG_IN, VOCI_LOG_IN);
		boolean finito=false;
		Utente u=null;
		do {
			int i=m.scegliSenzaEsci();
			switch(i) {
				case 1: u=accediMenuLogIn();
						break;
				case 2: u=registratiMenuLogIn();
						break;
			}				
			finito=(u!=null);
		}while(!finito);
		return u;
	}
	
	private static Utente accediMenuLogIn() {
		Utente u=null;
		ArrayList<String> ids=new ArrayList<>();
		ArrayList<String> passwords=new ArrayList<>();
		String id=InputDati.leggiStringaNonVuota(MSG_USER);
		String password=InputDati.leggiStringaNonVuota(MSG_PASSWORD);
		ExcelUtility.takeIds(ids);
		ExcelUtility.takePasswords(passwords);
		if (Utility.contenuto(ids, id) && Utility.contenuto(passwords, password)) {
			u=ExcelUtility.takeUser(id);
		}
		else System.out.println(BelleStringhe.rigaIsolata(MSG_ERR_LOG_IN));
		return u;
	}
	
	private static Utente registratiMenuLogIn() {
		Utente u=null;
		char gender=InputDati.leggiUpperChar(MSG_GENDER, CHAR_AMMISSIBILI);
		int eta=InputDati.leggiIntero(MSG_ETA, MIN, MAX);
		boolean consenso=InputDati.yesOrNo(CONSENSO);
		if(consenso) {
		u=Utente.createUtente(gender, eta);
		System.out.println(ID+u.getId()+" "+PASSWORD+u.getPassword());
		}
		else System.out.println(OP_ANNULLATA);
		return u;
	}
	
	public static void menuHome() {
		MyMenu m=new MyMenu(HOME_PAGE, VOCI_HOME_PAGE);
		boolean finito=false;
		System.out.println(BelleStringhe.rigaIsolata(BelleStringhe.incornicia(BENVENUTO)));
		do {
			int i=m.scegli();
			switch(i) {
				case 1: mostraCategorie();;
						break;
				case 0: System.out.println(BelleStringhe.rigaIsolata(BelleStringhe.incornicia(ARRIVEDERCI)));
					    finito=true;
						break;
			}				
		}while(!finito);
	}
	
	private static void mostraCategorie() {
		PartitaDiCalcio p=new PartitaDiCalcio();
		Utility.mostraCategorie(p);
	}
}
