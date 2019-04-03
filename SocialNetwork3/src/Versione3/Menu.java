package Versione3;

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
	private static final String VOCI_HOME_PAGE[]= {"Mostra categorie", "Mostra notifiche", "Mostra Bacheca", "Proponi Evento", "Mostra eventi a cui sei iscritto", "Mostra eventi che hai proposto"};
	private static final String NOTIFICHE="Sezione Notifiche";
	private static final String EVENTI="Di quale evento vuoi creare una proposta?";
	private static final String BACHECA="Bacheca";
	private static final String SCELTA_EVENTO="\nScegli l'evento a cui iscriverti, 0 per uscire";
	private static final String SCEGLI_EVENTO="\nScegli la categoria di evento da creare >";
	private static final String SCELTA_NOTIFICA="\nScegli la notifica da vedere, 0 per uscire";
	private static final String BACHECA_VUOTA="\nNon ci sono eventi pubblicati in bacheca\n";
	private static final String NO_NOTIFICHE="\nNon hai nessuna notifica\n";
	private static final String CANCELLAZIONE="Vuoi cancellare la notifica?";
	private static final String NO_ISCRIZIONI="\nNon sei iscritto a nessun evento\n";
	private static final String ISCRIZIONI="Iscrizioni";
	private static final String CANCELLA_ISCRIZIONE="\nVuoi cancellare l'iscrizione ad uno di questi eventi?\n";
	private static final String SCELTA_CANCELLAZIONE="\nQuale iscrizione vuoi annullare?\n";
	private static final String CANCELLAZIONE_AVVENUTA="\nCancellazione effettuata con successo\n";
	private static final String NO_CANCELLAZIONE_CREATORE="\nSei il creatore dell'evento, non puoi disiscriverti\n";
	private static final String ARRIVEDERCI="Arrivederci, torna a visitare il nostro social!";
	private static final String CREAZIONI="Eventi aperti che hai proposto";
	private static final String NO_CREAZIONI="\nNon hai proposto alcun evento attualmente aperto\n";
	private static final String CANCELLA_PROPOSTA="Vuoi eliminare una delle proposte fatte?";
	private static final String SCELTA_CANCELLAZIONE_PROP="Quale proposta vuoi eliminare?";
	private static final String SUPERATO_TERM_RITIRO="\nAttenzione: superato il termine per il ritiro ";
	private static final String PROPOSTA="della proposta scelta\n";
	private static final String ISCRIZIONE="dell'iscrizione scelta\n";
	private static final String CANCELLAZIONE_ANNULLATA="\nCancellazione annullata\n";
	private static final String[] CAT_ESISTENTI= {PartitaDiCalcio.NOME};
	private static final int FIRST_PAGE=0;
	
	public static Utente menuLogIn() {
		ExcelUtility.aggiornamenti();
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
				case 5:	menuIscrizioni(u);
						break;
				case 6: menuProposteFatte(u);
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
	
	private static void menuProposteFatte(Utente u) {
		 ArrayList<Evento> eventi=ExcelUtility.getEventiUtente(u);
		 ArrayList<Evento> eventiCreati=new ArrayList<>();
		 for(int i=0; i<eventi.size(); i++) {
			 if(eventi.get(i).creatore(u)) {
				 eventiCreati.add(eventi.get(i));
			 }
		 }
		 if(eventiCreati.size()==0) System.out.println(NO_CREAZIONI);
		 else {
			ArrayList<String> eventiUtente=new ArrayList<>();
			for(int i=0; i<eventi.size(); i++) {
			eventiUtente.add(eventiCreati.get(i).mostraEv());
			}
			MyMenu menu=new MyMenu(CREAZIONI, eventiUtente);
			menu.stampaMenuSenzaEsci();
			boolean cancella=InputDati.yesOrNo(CANCELLA_PROPOSTA);
			if(cancella) {
				int i=menu.scegli(SCELTA_CANCELLAZIONE_PROP);
				if(i!=0) {
					Evento eventoScelto=eventi.get(i-1);
					if(!eventoScelto.ritirabile()) {
						System.out.println(SUPERATO_TERM_RITIRO+PROPOSTA);
						System.out.println(CANCELLAZIONE_ANNULLATA);
					}
					else {
						eventoScelto.ritira ();
					}
				}
			}
		 }
	}
	
	private static void menuIscrizioni(Utente u) {
		 ArrayList<Evento> eventi=ExcelUtility.getEventiUtente(u);
			if(eventi.size()==0) System.out.println(NO_ISCRIZIONI);
			else {
				ArrayList<String> eventiUtente=new ArrayList<>();
				for(int i=0; i<eventi.size(); i++) {
					eventiUtente.add(eventi.get(i).mostraEv());//
				}
				MyMenu menu=new MyMenu(ISCRIZIONI, eventiUtente);
				menu.stampaMenuSenzaEsci();
				boolean cancella=InputDati.yesOrNo(CANCELLA_ISCRIZIONE);
				if(cancella) {
					int i=menu.scegli(SCELTA_CANCELLAZIONE);
					if(i!=0) {
						Evento eventoScelto=eventi.get(i-1);
						if(eventoScelto.creatore(u)) {
							System.out.println(NO_CANCELLAZIONE_CREATORE);
						}
						else { 
							if(!eventoScelto.ritirabile()) System.out.println(SUPERATO_TERM_RITIRO+ISCRIZIONE);
							else{
								int j=Utility.getNomiUtenti(eventoScelto.getUtenti()).indexOf(u.getId());
								ExcelReader.removeCell(j, eventoScelto.getColumn(), Evento.FILE_UT_EV, FIRST_PAGE);
								System.out.println(CANCELLAZIONE_AVVENUTA);
								System.out.println(CANCELLAZIONE_ANNULLATA);
							}
						}
					}
				}
			}
	}
	
	public static Evento menuSceltaEvento() {
		MyMenu m=new MyMenu(EVENTI, CAT_ESISTENTI);
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
