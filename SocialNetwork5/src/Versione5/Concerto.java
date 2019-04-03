package Versione5;

import java.io.File;
import java.util.ArrayList;

import mylib.InputDati;

public class Concerto extends Evento{

	private static final boolean OBBLIGATORIO=true;
	private static final boolean FACOLTATIVO=false;
	public static final String NOME="Concerto";
	private static final String DESCRIZIONE="Organizza insieme ad altri utenti una trasferta per assistere un concerto";
	public static final String ARTISTA="Artista";
	private static final String DESCR_ARTISTA="L'artista che si esibisce al concerto";
	public static final String TSHIRT="T-Shirt";
	private static final String DESCR_TSHIRT="Il prezzo dell'eventuale t-shirt acquistabile";
	public static final String PASS_BACKSTAGE="Pass Backstage";
	private static final String DESCR_PASS_BACKSTAGE="Il prezzo dell'eventuale pass backstage acquistabile";
	public static final int EVENT_ARTISTA_ROW=NUM_CAMPI+1;
	public static final int EVENT_TSHIRT_ROW=NUM_CAMPI+2;
	public static final int EVENT_PASS_BACKSTAGE_ROW=NUM_CAMPI+3;
	private static final int MIN_COSTO=0;
	private static final int FIRST_ROW=0;
	private static final int SECOND_PAGE=1;
	private static final String MSG_ARTISTA="Inserisci l'artista che si esibirà al concerto";
	private static final String MSG_COSTO="Inserisci il prezzo del campo "; 
	private Campo <String> artista=new Campo<>(ARTISTA, DESCR_ARTISTA, OBBLIGATORIO);
	private Campo <Double> tshirt=new Campo<>(TSHIRT, DESCR_TSHIRT, FACOLTATIVO);
	private Campo <Double> pass_backstage=new Campo<>(PASS_BACKSTAGE, DESCR_PASS_BACKSTAGE, FACOLTATIVO);
	
	public Concerto() {
		super(NOME, DESCRIZIONE);
		addCampiConcerto();
	}
	
	public Concerto(int column) {
		super(column);
		addCampiConcerto();
		inizializzaCampiDaFileConcerto();
	}
	
	public void inizializzaCampiDaFileConcerto() {
		ArrayList<String> colonna=ExcelReader.takeColumn(FILE_EV, getColumn(), FIRST_PAGE);
		setValCampo(ARTISTA, colonna.get(EVENT_ARTISTA_ROW));
		if(!colonna.get(EVENT_TSHIRT_ROW).equals(ExcelReader.NO_VALUE)) setValCampo(TSHIRT, Double.parseDouble(colonna.get(EVENT_TSHIRT_ROW)));
		else setValCampo(TSHIRT, (double)MIN_COSTO);
		if(!colonna.get(EVENT_PASS_BACKSTAGE_ROW).equals(ExcelReader.NO_VALUE)) setValCampo(PASS_BACKSTAGE, Double.parseDouble(colonna.get(EVENT_PASS_BACKSTAGE_ROW)));
		else setValCampo(PASS_BACKSTAGE, (double)MIN_COSTO);
	}
	
	public void addCampiConcerto() {
		addCampo(artista);
		addCampo(tshirt);
		addCampo(pass_backstage);
	}
	
	private void setArtista(String s) {
		setValCampo(ARTISTA, s);
	}
	
	public void setTshirt(double s) {
		setValCampo(TSHIRT, s);
	}
	
	public void setPass(double s) {
		setValCampo(PASS_BACKSTAGE, s);
	}
	
	private void setPagabile(String nome, double val) {
		if(nome.equals(TSHIRT)) setTshirt(val);
		else if(nome.equals(PASS_BACKSTAGE)) setPass(val);
	}
	
	public double getTshirt() {
		return tshirt.getValore();
	}
	
	public double getPass() {
		return pass_backstage.getValore();
	}
	
	public String getArtista() {
		return artista.getValore();
	}
	
	public String mostraEv() {
		return Interfaces.mostraEvento(this);
	}
	
	public ArrayList<Campo<Double>> pagabili(){
		ArrayList<Campo<Double>> pagabili=new ArrayList<>();
		pagabili.add(tshirt);
		pagabili.add(pass_backstage);
		return pagabili;
	}
	
	public void inizializza(Campo c) {
		boolean pagabile=false;
		ArrayList<Campo<Double>> pagabili=pagabili();
		if(c.getNome().equals(ARTISTA)) {
			setArtista(InputDati.leggiStringaNonVuota(MSG_ARTISTA));
		}
		else {
			for(int i=0; i<pagabili.size(); i++) {
				if(c.getNome().equals(pagabili.get(i).getNome())) {
					pagabile=true;
				}
			}
			if(pagabile) {
				boolean ok=InputDati.yesOrNo(MESSAGGIO_FACOLTATIVO+c.getNome()+"?");
				if(ok) {
					setPagabile(c.getNome(), InputDati.leggiDoubleConMinimo(MSG_COSTO+c.getNome(), MIN_COSTO));
				}
			}
			else super.inizializza(c);
		}
	}
	
	public void inizializzaEvento(Utente u) {
		super.inizializzaEvento(u);
	}
	
	public boolean addUtente(Utente u) {
		String prezzi=Utility.consensi(pagabili());
		ExcelReader.addToColumn(prezzi, Evento.FILE_UT_EV, getColumn(), SECOND_PAGE);
		return super.addUtente(u);
	}
}
