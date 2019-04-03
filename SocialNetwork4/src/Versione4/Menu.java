package Versione4;

import java.io.File;
import java.util.ArrayList;

import Versione4.Evento.Stato;
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
	private static final String CONSENSO="Confermi di voler registrarti con questo profilo?";
	private static final String BENVENUTO="Benvenuto nella home page";
	private static final String HOME_PAGE="Home Page";
	private static final String VOCI_HOME_PAGE[]= {"Mostra categorie", "Mostra notifiche", "Mostra Bacheca", "Proponi Evento", 
			"Mostra eventi a cui sei iscritto", "Mostra eventi che hai proposto", "Mostra profilo personale"};
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
	private static final int LARGHEZZA=50;
	private static final String ARRIVEDERCI="Arrivederci, torna a visitare il nostro social!";
	private static final String CREAZIONI="Eventi aperti che hai proposto";
	private static final String NO_CREAZIONI="\nNon hai proposto alcun evento attualmente aperto\n";
	private static final String CANCELLA_PROPOSTA="Vuoi eliminare una delle proposte fatte?";
	private static final String SCELTA_CANCELLAZIONE_PROP="Quale proposta vuoi eliminare?";
	private static final String SUPERATO_TERM_RITIRO="\nAttenzione: superato il termine per il ritiro ";
	private static final String PROPOSTA="della proposta scelta\n";
	private static final String ISCRIZIONE="dell'iscrizione scelta\n";
	private static final String CANCELLAZIONE_ANNULLATA="\nCancellazione annullata\n";
	private static final String	MSG_ID="Inserisci il nomignolo che vuoi (attenzione, non sarà modificabile in futuro):";
	private static final String	DOPPIONE="\nAttenzione: nomignolo già utilizzato da qualcun'altro, scegline un altro";
	private static final String CATEGORIE_INTERESSE="Seleziona le categorie che ti interessano";	
	private static final String	CONSENSO_FASCIA_ETA="Vuoi inserire la fascia d'età in cui ricadi?";
	private static final String	ERRORE_FASCIA="\nAttenzione: la fascia d'età che inserisci deve comprendere la tua età\n";
	private static final String	CONSENSO_CATEGORIE="Vuoi inserire le categorie d'interesse?";
	private static final String	SCEGLI_CATEGORIA="Quale categoria ti interessa?";
	private static final String	CONTINUA="Vuoi ripetere l'operazione?";
	private static final String PROFILO="Profilo";
	private static final String MODIFICA="Vuoi modificare una delle voci?";
	private static final String VOCE_MODIFICA="Modifica";
	private static final String[] POSSIBILI_MODIFICHE= {"Modifica età", "Modifica fascia d'età", "Aggiungi categorie d'interesse", "Rimuovi categorie d'interesse"};
	private static final String MSG_MODIFICA="Quale voce vuoi modificare?";
	private static final String NECESSITA_MODIFICA_FASCIA="L'età che hai inserito non rientra più nella fascia d'età che era impostata, è necessario modificare anche quest'ultima affinchè sia compatibile";
	private static final String MODIFICARE_ANCORA="Vuoi modificare qualcos'altro?";
	private static final String CONFERMA="Confermi?";
	private static final String NECESSITA_MODIFICA_ETA="La fascia d'età che hai inserito non è più compatibile con l'età che era impostata, è necessario modificare anche quest'ultima affinchè sia compatibile";
	private static final String ERRORE_ETA_FASCIA="\nAttenzione: l'età che inserisci deve rientrare nella fascia d'età impostata precedentemente, cioè ";
	private static final String MSG_FASCIA="Inserisci la fascia d'età";
	private static final String[] CAT_ESISTENTI= {PartitaDiCalcio.NOME};
	private static final String NO_MORE_CATEGORIES="Non ci sono ulteriori categorie da poter aggiungere";
	private static final String SCEGLI_RIMOZIONE="Quale categoria vuoi rimuovere?";
	private static final String CATEGORIE_RIMOZIONE="Seleziona le categorie che vuoi rimuovere";
	private static final String NO_CATEGORIES="Non hai alcuna categoria da poter rimuovere";
	private static final String	PARTECIPAZIONE="Vuoi partecipare a questo evento?";
	private static final String	ERRORE_EVENTO="\nCi discpiace, ma tale evento non è più presente in bacheca\n";
	private static final String	NO_INVITABILI="\nNon ci sono utenti che puoi invitare, per poter invitare qualcuno quest'ultimo deve aver partecipato, in passato, ad un evento proposto da te della stessa categoria che vuoi proporre\n";
	private static final String	INVITI="Invita qualcuno";
	private static final String	INVITO_ANCORA="Vuoi invitare qualcun'altro?";
	private static final String CHI_INVITARE="Chi vuoi invitare?";
	private static final String INVITA_TUTTI="Vuoi invitare tutti?";
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
		ArrayList<String> categorie=new ArrayList<>();
		Utente u=null;
		String name;
		char gender;
		int eta;
		String fascia=ExcelUtility.NO_FASCIA;
		boolean accettabile;
		ArrayList<String>names=new ArrayList<>();
		ExcelUtility.takeIds(names);
		do {
			name=InputDati.leggiStringaNonVuota(MSG_ID);
			accettabile=mylib.Utility.accettabile(names, name, DOPPIONE);
		}while(!accettabile);
		boolean consenso;
		gender=InputDati.leggiUpperChar(MSG_GENDER, CHAR_AMMISSIBILI);
		eta=InputDati.leggiIntero(MSG_ETA, MIN, MAX);
		consenso=InputDati.yesOrNo(CONSENSO_FASCIA_ETA);
		if(consenso) {
			fascia=mylib.Utility.inserisciFascia(eta, MIN, MAX, ERRORE_FASCIA);
		}
		consenso=InputDati.yesOrNo(CONSENSO_CATEGORIE);
		if(consenso) {
			categorie=menuCategorie(CAT_ESISTENTI, 	SCEGLI_CATEGORIA, CATEGORIE_INTERESSE);
		}
		consenso=InputDati.yesOrNo(CONSENSO);
		if(consenso) {
		u=Utente.createUtente(name, gender, eta, fascia, categorie);
		System.out.println(ID+u.getId()+" "+PASSWORD+u.getPassword());
		}
		else System.out.println(OP_ANNULLATA);
		return u;
	}
	
	public static ArrayList<String> menuCategorie(String[] categories, String msgScelta, String titoloMenu) {
		ArrayList<String> categorie=new ArrayList<>();
		ArrayList<String> voci=new ArrayList<>();
		for(int i=0; i<categories.length; i++) {
			voci.add(categories[i]);
		}
		MyMenu m=new MyMenu(titoloMenu, voci);//CATEGORIE_INTERESSE
		boolean finito=false;
		do {
			int i=m.scegli(msgScelta);//SCEGLI_CATEGORIA
			if(i==0) finito=true;
			else{
				categorie.add(voci.get(i-1));
				voci.remove(i-1);
				m=new MyMenu(titoloMenu, voci);
				if(voci.size()==0) finito=true;
				else finito=InputDati.yesOrNo(CONTINUA);
			}
		}while(!finito);
		return categorie;
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
						if(notifiche.get(j).getInvito()) {
							boolean partecipa=InputDati.yesOrNo(PARTECIPAZIONE);
							if(partecipa) {
								Evento e=new Evento(notifiche.get(j).getEventColumn());
								if(!e.getStato().toString().equals(Stato.APERTA.toString())) System.out.println(ERRORE_EVENTO);
								else partecipa(e, u);
							}
						}
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
				case 7: menuProfilo(u);
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
	
	private static void menuProfilo(Utente u) {
		System.out.println(BelleStringhe.incornicia(PROFILO));
		System.out.println(Interfaces.getProfilo(u));
		boolean ok=InputDati.yesOrNo(MODIFICA);
		if(ok) {
			boolean finito=false;
			do {
				MyMenu m=new MyMenu(VOCE_MODIFICA, POSSIBILI_MODIFICHE);
				int i=m.scegli(MSG_MODIFICA);				
				switch(i) {
					case 1:	modificaEtaMenuProfilo(u);
							break;
					case 2: modificaFasciaMenuProfilo(u);
							break;
					case 3:	addCategoriaMenuProfilo(u);
							break;
					case 4: removeCategoriaMenuProfilo(u);
							break;
					case 0: finito=true;
						    break;
				}
				finito=!InputDati.yesOrNo(MODIFICARE_ANCORA);
			}while(!finito);
		}
	}
	
	public static void invita(ArrayList<Utente> invitabili, Evento e) {
		boolean finito=false;
		ArrayList<Utente> invitati=new ArrayList<Utente>();
		ArrayList<String> nomiInvitabili=new ArrayList<>();
		for(int i=0; i<invitabili.size(); i++) {
			nomiInvitabili.add(invitabili.get(i).getId());
		}
		MyMenu m=new MyMenu(INVITI, nomiInvitabili);
		m.stampaMenuSenzaEsci();
		boolean tutti=InputDati.yesOrNo(INVITA_TUTTI);
		if(tutti) {
			invitati=invitabili;
			finito=true;
		}
		else{
			do {
				if(invitabili.size()==0) {
					System.out.println(BelleStringhe.aCapo(NO_INVITABILI, LARGHEZZA));
					finito=true;
				}
				else {
						int i=m.scegli(CHI_INVITARE);
						switch(i) {
							case 0: finito=true;
									break;
							default:invitati.add(invitabili.get(i-1));
									invitabili.remove(i-1);
									nomiInvitabili.remove(i-1);
									finito=!InputDati.yesOrNo(INVITO_ANCORA);
									break;
						}
				}
			}while(!finito);
		}
		if(invitati.size()!=0) {
			Notifica n=Interfaces.invito(e);
			ExcelUtility.writeNotify(n, invitati);
		}
	}
	
	private static void modificaEtaMenuProfilo(Utente u) {
		boolean consenso=false;
		int eta;
		String fascia="";
		do{
			eta=InputDati.leggiIntero(MSG_ETA, MIN, MAX);
			consenso=InputDati.yesOrNo(CONFERMA);
		}while(!consenso);
		u.setEta(eta);
		if(u.getFascia()!=null&&u.getFascia()!="") {
			if(!Utility.etaContenuta(u.getFascia(), eta)) {
				System.out.println(BelleStringhe.aCapo(NECESSITA_MODIFICA_FASCIA, LARGHEZZA));
				fascia=mylib.Utility.inserisciFascia(eta, MIN, MAX, ERRORE_FASCIA);
				u.setFascia(fascia);
			}
		}
	}
	
	private static void modificaFasciaMenuProfilo(Utente u) {
		boolean consenso=false;
		int eta;
		String fascia="";
		do{
			fascia=InputDati.leggiFasciaEta(MSG_FASCIA, MIN, MAX);
			consenso=InputDati.yesOrNo(CONFERMA);
		}while(!consenso);
		u.setFascia(fascia);
		if(!Utility.etaContenuta(fascia, u.getEta())) {
			System.out.println(BelleStringhe.aCapo(NECESSITA_MODIFICA_ETA, LARGHEZZA));
			do{
				eta=InputDati.leggiIntero(MSG_ETA, MIN, MAX);
				if(!Utility.etaContenuta(fascia, eta)) System.out.println(ERRORE_ETA_FASCIA+fascia);
			}while(!Utility.etaContenuta(fascia, eta));
			u.setEta(eta);
		}
	}
	
	private static void addCategoriaMenuProfilo(Utente u) {
		ArrayList<String> categorie=null;
		categorie=ExcelUtility.getCategorie(u);
		if(categorie.size()==CAT_ESISTENTI.length) {
			System.out.println(NO_MORE_CATEGORIES);
		}
		else{
			ArrayList<String> catEsistenti=new ArrayList<>();
			for(int j=0; j<CAT_ESISTENTI.length; j++) {
				catEsistenti.add(CAT_ESISTENTI[j]);
			}
			for(int j=0; j<categorie.size(); j++) {
				for(int k=0; k<catEsistenti.size(); k++) {
					if(catEsistenti.get(k).equals(categorie.get(j))) catEsistenti.remove(k);
				}
			}
			String[] useless= {};
			String[] rimanenti=catEsistenti.toArray(useless);
			catEsistenti=menuCategorie(rimanenti, SCEGLI_CATEGORIA, CATEGORIE_INTERESSE);
			for(int j=0; j<catEsistenti.size(); j++) {
				u.addCategoria(catEsistenti.get(j));
			}
		}
	}
	
	private static void removeCategoriaMenuProfilo(Utente u) {
		ArrayList<String> categorie=null;
		categorie=ExcelUtility.getCategorie(u);
		if(categorie.size()==0) {
			System.out.println(NO_CATEGORIES);
		}
		else {
			ArrayList<String> catUtente=new ArrayList<>();
			String[] useless= {};
			String[] rimanenti=categorie.toArray(useless);
			catUtente=menuCategorie(rimanenti, SCEGLI_RIMOZIONE, CATEGORIE_RIMOZIONE);
			for(int j=0; j<catUtente.size(); j++) {
				int k=u.getCategorie().indexOf(catUtente.get(j));
				u.removeCategoria(k);
			}
		}
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
						eventoScelto.ritira();
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
					eventiUtente.add(eventi.get(i).mostraEv());
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
