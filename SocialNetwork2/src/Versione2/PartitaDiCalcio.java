package Versione2;

import java.util.ArrayList;
import mylib.*;

public class PartitaDiCalcio extends Evento{

	private static final boolean OBBLIGATORIO=true;
	public static final String NOME="Partita di calcio";
	private static final String DESCRIZIONE="Organizza una partita di calcio insieme ai tuoi amici";
	public static final String GENERE="Genere";
	private static final String DESCR_GENERE="Il genere (maschile o femminile) dei giocatori";
	public static final String FASCIA_ETA="Fascia d'età";
	private static final String DESCR_FASCIA_ETA="Estremo inferiore e superiore di età ammissibile dei giocatori";
	private static final String ERRORE_ETA="\nAttenzione:La fascia di età dell'evento che stai creando deve comprendere la tua età\n";
	private static final String	ERRORE_GENERE="\nAttenzione: questo evento è destinato unicamente ";
	private static final String ERRORE_ETA_UTENTE="\nAttenzione: questo evento è destinato a coloro con età ";
	private static final String UOMINI="agli uomini\n";
	private static final String DONNE="alle donne\n";
	private static final char MASCHIO='M';
	public static final int EVENT_GENERE_ROW=NUM_CAMPI+1;
	public static final int EVENT_FASCIA_ROW=NUM_CAMPI+2;
	private Campo <Character> genere=new Campo<>(GENERE, DESCR_GENERE, OBBLIGATORIO);
	private Campo <String> fasciaEta=new Campo<>(FASCIA_ETA, DESCR_FASCIA_ETA, OBBLIGATORIO);
	private int etaMin;
	private int etaMax;
	private ArrayList <Integer> eta=new ArrayList<>();
	
	public PartitaDiCalcio () {
		super(NOME, DESCRIZIONE);
		addCampo(genere);
		addCampo(fasciaEta);
	}
	
	public PartitaDiCalcio (int colonna) {
		super(colonna);
		addCampo(genere);
		addCampo(fasciaEta);
		ArrayList<String> column=ExcelReader.takeColumn(FILE_EV, colonna, FIRST_PAGE);
		setValCampo(GENERE, column.get(EVENT_GENERE_ROW).charAt(0));
		setValCampo(FASCIA_ETA, column.get(EVENT_FASCIA_ROW));
		Utility.prendiEta(fasciaEta.getValore(), eta);
		setEtaMin(eta.get(0));
		setEtaMax(eta.get(1));
	}
	
	private void calcEta() {
		String fascia= fasciaEta.getValore();
		Utility.prendiEta(fascia, eta);
		setEtaMin(eta.get(0));
		setEtaMax(eta.get(1));
	}
	
	public void aggiornaEta(String valFascia) {
		setValCampo(FASCIA_ETA, valFascia);
		calcEta();
	}

	public int getEtaMin() {
		return etaMin;
	}

	public void setEtaMin(int etaMin) {
		this.etaMin = etaMin;
	}

	public int getEtaMax() {
		return etaMax;
	}

	public void setEtaMax(int etaMax) {
		this.etaMax = etaMax;
	}
	
	public void setGenere(char c) {
		setValCampo(GENERE, c);
	}
	
	public char getGenere() {
		return genere.getValore();
	}
	
	public String getFascia() {
		return fasciaEta.getValore();
	}
	
	public void inizializzaCampo(Campo c, Utente u) {
		if(c.getNome().equals(FASCIA_ETA)) {
			boolean ok=false;
			do{
				aggiornaEta(InputDati.leggiFasciaEta(MESSAGGIO_CAMPO+c.getNome()+FINE_MESSAGGIO_CAMPO, Menu.MIN, Menu.MAX));
				if(etaMin>u.getEta()||etaMax<u.getEta()) {
					System.out.println(ERRORE_ETA);
				}
				else ok=true;
			}while(!ok);
		}
		else if (c.getNome().equals(GENERE)) setGenere(u.getGenere());
		else inizializza(c);
	}
	
	public void inizializzaEvento(Utente u) {
		for(int i=0; i<getCampi().size(); i++) {
			inizializzaCampo(getCampi().get(i), u);
		}
	}
	
	private boolean accettabile(char genere, int eta) {
		if(genere!=getGenere()) {
			System.out.print(ERRORE_GENERE);
			if(getGenere()==MASCHIO) System.out.println(UOMINI);
			else System.out.println(DONNE); 
			return false;
		}
		else {;
			if(eta<etaMin||eta>etaMax) {
				System.out.println(ERRORE_ETA_UTENTE+etaMin+"-"+etaMax);
				return false;
			}
		}
		return true;
	}
	
	public String mostraEv() {
		return Interfaces.mostraEvento(this);
	}
	
	public boolean addUtente(Utente u) {
		boolean ok=accettabile(u.getGenere(), u.getEta());
		if(ok) {
			return super.addUtente(u);
		}
		else return false;
	}
}
