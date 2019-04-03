package Versione4;

import java.sql.Time;
import java.util.Date;

import Versione4.Evento.Stato;

public class Notifica {

	private String titolo;
	private String descrizione;
	private boolean nuova;
	private boolean invito;
	private int column;
	private static final String EVENTO_FALLITO="L'evento non si svolgerà a causa del non raggiungimento della quota minima dei partecipanti";
	private static final String EVENTO_CHIUSO="L'evento si svolgerà in data ";
	private static final String EVENTO_RITIRATO="L'evento non si svolgerà perchè la proposta è stata ritirata dall'utente che l'ha creata ";
	private static final String LUOGO_EVENTO=",\nil luogo di ritrovo è ";
	private static final String ORA_EVENTO=", alle ore ";
	private static final String QUOTA_NULLA="\nLa quota di partecipazione è nulla";
	private static final String QUOTA_EVENTO="\nLa quota di partecipazione è ";
	private static final String STESSO_GIORNO="\nL'evento si concluderà nell'arco della stessa giornata";
	private static final String CONCLUSIONE_EVENTO="\nL'evento si concluderà il giorno ";
	private static final String ORA_FINE="\nL'ora finale stabilita è: ";
	private static final String NOTE="\nNote: ";
	private static final String DA_LEGGERE="[NEW] ";
	
	public Notifica(String titolo, String descrizione, boolean nuova, int column, boolean invito) {
		this.titolo=titolo;
		this.descrizione=descrizione;
		this.nuova=nuova;
		this.column=column;
		this.invito=invito;
	}
	
	public Notifica(Evento e) {
		titolo="";
		Date dataIn=(Date)e.getCampo(Evento.DATA).getValore();
		Date dataFin=(Date)e.getCampo(Evento.DATA_FIN).getValore();
		String note=(String)e.getCampo(Evento.NOTE).getValore();
		Time oraIn=(Time)e.getCampo(Evento.ORA).getValore();
		Time oraFin=(Time)e.getCampo(Evento.ORA_FIN).getValore();
		String titoloEv=(String)e.getCampo(Evento.TITOLO).getValore();
		String luogo=(String)e.getCampo(Evento.LUOGO).getValore();
		if(titoloEv!=null) titolo+=titoloEv.toUpperCase()+":";
		titolo+=e.getNome()+" del "+mylib.Utility.DateToString(dataIn);
		if(e.getStato().equals(Stato.FALLITA)) {
			descrizione=EVENTO_FALLITO;
		}
		else {
			if(e.getStato().equals(Stato.RITIRATA)) descrizione=EVENTO_RITIRATO;
			else {
				descrizione=EVENTO_CHIUSO+mylib.Utility.DateToString(dataIn)+LUOGO_EVENTO+luogo+ORA_EVENTO+oraIn+".";
				if(dataFin!=null) {
					if(dataFin.compareTo(dataIn)==0) {
						descrizione+=STESSO_GIORNO;
					}
					else descrizione+=CONCLUSIONE_EVENTO+mylib.Utility.DateToString(dataFin);
				}
				if(oraFin!=null) {
					descrizione+=ORA_FINE+oraFin.toString();
				}
				if(note!=null) {
					descrizione+=NOTE+note;
				}
				double quota=(double) e.getCampo(Evento.QUOTA).getValore();
				if (quota==0){
					descrizione+=QUOTA_NULLA;
				}
				else {
					descrizione+=QUOTA_EVENTO+e.getCampo(Evento.QUOTA).getValore()+" euro";
				}
			}
		}
		nuova=true;
		invito=false;
		column=e.getColumn();
	}
	
	public String mostraTitolo() {
		if(nuova) return DA_LEGGERE+titolo;
		else return titolo;
	}
	
	public void mostraDescrizione() {
		System.out.println(descrizione);
	}
	
	public void mostra() {
		System.out.println("\n"+titolo+"\n");
		System.out.println(descrizione+"\n");
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public void setNuova(boolean nuova) {
		this.nuova=nuova;
	}
	
	public boolean getInvito() {
		return invito;
	}
	
	public int getEventColumn() {
		return column;
	}
}
