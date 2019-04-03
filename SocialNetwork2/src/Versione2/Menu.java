package Versione2;

import java.io.File;
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
	private static final String OP_ANNULLATA="\nOperazione annullata\n";
	public static final int MIN=14;
	public static final int MAX=130;
	private static final String CONSENSO="Confermi di voler procedere?";
	private static final String BENVENUTO="Benvenuto nella home page";
	private static final String HOME_PAGE="Home Page";
	private static final String VOCI_HOME_PAGE[]= {"Mostra categorie", "Mostra notifiche", "Mostra Bacheca", "Proponi Evento"};
	private static final String NOTIFICHE="Sezione Notifiche";
	private static final String EVENTI="Di quale evento vuoi creare una proposta?";
	private static final String VOCI_EVENTI[]= {"Partita di calcio"};
	private static final String BACHECA="Bacheca";
	private static final String SCELTA_EVENTO="\nScegli l'evento a cui iscriverti, 0 per uscire";
	private static final String SCEGLI_EVENTO="\nScegli la categoria di evento da creare >";
	private static final String SCELTA_NOTIFICA="\nScegli la notifica da vedere, 0 per uscire";
	private static final String BACHECA_VUOTA="\nNon ci sono eventi pubblicati in bacheca\n";;
	private static final String NO_NOTIFICHE="\nNon hai nessuna notifica\n";
	private static final String CANCELLAZIONE="Vuoi cancellare la notifica?";
	private static final String ARRIVEDERCI="Arrivederci, torna a visitare il nostro social!";
	private static final int FIRST_PAGE=0;
	
	public static Utente menuLogIn() {
		ExcelUtility.modificaStatiEventi();
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
	
	public static void menuBacheca(Utente u) {
		ExcelUtility.modificaStatiEventi();
		ArrayList<Integer> colonne=new ArrayList<>();
		ArrayList<String> column=new ArrayList<>();
		ArrayList<String> eventi=Bacheca.mostraEventi(colonne);
		if(eventi.size()==0) System.out.println(BACHECA_VUOTA);
		else{
			MyMenu m=new MyMenu(BACHECA, eventi);
			boolean finito=false;
			do {
				int i=m.scegli(SCELTA_EVENTO);
				switch(i) {
					case 0: finito=true;
							break;
					default:int j=colonne.get(i-1);
							column=ExcelReader.takeColumn(Evento.FILE_EV, j, FIRST_PAGE);
							String nome=column.get(Evento.EVENT_NOME_ROW);
							Evento e=Utility.getEvento(nome, j);
							finito=partecipa(e, u);
				}
			}while(!finito);
		}
	}
	
	private static boolean partecipa(Evento e, Utente u) {
		return ExcelUtility.partecipa(e, u);
	}
	
	public static void menuNotifiche(Utente u) {
		ExcelUtility.modificaStatiEventi();
		ArrayList<Notifica> notifiche=ExcelUtility.visualizzaElencoNotifiche(u);
		ArrayList<String> titoli=new ArrayList<>();
		if(notifiche.size()==0)	System.out.println(NO_NOTIFICHE);
		else {
			boolean finito=false;
			for(int i=0; i<notifiche.size(); i++) {
				titoli.add(notifiche.get(i).mostraTitolo());
			}
			do {
				MyMenu m=new MyMenu(NOTIFICHE, titoli);
				int i=m.scegli(SCELTA_NOTIFICA);
				switch(i) {
				case 0: finito=true;
						break;
				default:int j=i-1;
						notifiche.get(j).mostra();
						ExcelUtility.NotifyRead(notifiche.get(j), notifiche.size()-i, u);
						titoli.set(j, notifiche.get(j).mostraTitolo());
						boolean ok=InputDati.yesOrNo(CANCELLAZIONE);
						if(ok) {
							ExcelReader.removeRow(new File(u.getFileName()), notifiche.size()-i, FIRST_PAGE);
							notifiche.remove(j);
							titoli.remove(j);
						} 
					}				
			}while(!finito);
		}
	}
	
	public static void menuHome(Utente u) {
		MyMenu m=new MyMenu(HOME_PAGE, VOCI_HOME_PAGE);
		boolean finito=false;
		System.out.println(BelleStringhe.rigaIsolata(BelleStringhe.incornicia(BENVENUTO)));
		do {
			int i=m.scegli();
			switch(i) {
				case 1: mostraCategorie();
						break;
				case 2: menuNotifiche(u);
					    break;
				case 3: menuBacheca(u);
					    break;
				case 4:	Evento e=menuSceltaEvento();
						ExcelUtility.createEvento(e, u);
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
	
	public static Evento menuSceltaEvento() {
		MyMenu m=new MyMenu(EVENTI, VOCI_EVENTI);
		boolean finito=false;
		Evento e=null;
		do {
			int i=m.scegliSenzaEsci(SCEGLI_EVENTO);
			switch(i) {
				case 1: e=new PartitaDiCalcio();
						finito=true;
			}
		}while(!finito);
		return e;
	}
	
	public static void main(String[] args) {
	//	menuBacheca();
	}
}
