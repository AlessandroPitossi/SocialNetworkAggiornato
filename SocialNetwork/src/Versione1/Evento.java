package Versione1;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class Evento {

	private static final boolean FACOLTATIVO=false;
	private static final boolean OBBLIGATORIO=true;
	public static final int FIRST_PAGE=0;
	public static final String TITOLO="Titolo";
	private static final String DESCR_TITOLO="Il titolo che vuoi dare al tuo evento";
	public static final String NUM_PART="Numero di partecipanti";
	private static final String DESCR_NUM_PART="Il numero di persone che possono partecipare all'evento";
	public static final String TERM_ULT_ISCR="Termine ultimo di iscrizione";
	private static final String DESCR_TERM_ULT_ISCR="Ultimo giorno utile per iscriversi all'evento";
	public static final String LUOGO="Luogo";
	private static final String DESCR_LUOGO="Indirizzo del luogo che ospiterà l'evento oppure, se l'evento è itinerante, il luogo di ritrovo";
	public static final String DATA="Data";
	private static final String DESCR_DATA="Data in cui si svolge l'evento o, nel caso l'evento duri più giorni, la data di inizio";
	public static final String ORA="Ora";
	private static final String DESCR_ORA="Orario di ritrovo";
	public static final String DURATA="Durata";
	private static final String DESCR_DURATA="Durata approssimativa in ore e minuti, per gli eventi che si esauriscono in un solo giorno, o in termini di numero esatto di giorni, per gli eventi che occupani più giorni";
	public static final String QUOTA="Quota individuale";
	private static final String DESCR_QUOTA="La spesa che ogni partecipante all'iniziativa dovrà sostenere";
	public static final String COMPRESO_QUOTA="Compreso nella quota";
	private static final String DESCR_COMPRESO_QUOTA="Tutte le voci di spesa che sono comprese nella \"Quota individuale\"";
	public static final String DATA_FIN="Data conclusiva";
	private static final String DESCR_DATA_FIN="Data in cui l'evento si conclude";
	public static final String ORA_FIN="Ora conclusiva";
	private static final String DESCR_ORA_FIN="Orario di conclusione dell'evento";
	public static final String NOTE="Note";
	private static final String DESCR_NOTE="Informazioni aggiuntive circa l'evento";
	private Campo <String> titolo=new Campo<>(TITOLO, DESCR_TITOLO, FACOLTATIVO);
	private Campo <Integer> numPartecipanti=new Campo<>(NUM_PART, DESCR_NUM_PART, OBBLIGATORIO);
	private Campo <Date> termIscr=new Campo<>(TERM_ULT_ISCR, DESCR_TERM_ULT_ISCR, OBBLIGATORIO);
	private Campo <String> luogo=new Campo<>(LUOGO, DESCR_LUOGO, OBBLIGATORIO);
	private Campo <Date> dataIn=new Campo<>(DATA, DESCR_DATA, OBBLIGATORIO);
	private Campo <Time> oraIn=new Campo<>(ORA, DESCR_ORA, OBBLIGATORIO);
	private Campo durata=new Campo<>(DURATA, DESCR_DURATA, FACOLTATIVO);
	private Campo <Double> quota=new Campo<>(QUOTA, DESCR_QUOTA, OBBLIGATORIO);
	private Campo <String> compresoQuota=new Campo<>(COMPRESO_QUOTA, DESCR_COMPRESO_QUOTA, FACOLTATIVO);
	private Campo <Date> dataFin=new Campo<>(DATA_FIN, DESCR_DATA_FIN, FACOLTATIVO);
	private Campo <Time> oraFin=new Campo<>(ORA_FIN, DESCR_ORA_FIN, FACOLTATIVO);
	private Campo <String> note=new Campo<>(NOTE, DESCR_NOTE, FACOLTATIVO);
	
	private String nome; 
	private String descrizione;
	private ArrayList<Campo> campi=new ArrayList<>();
	
	public Evento(String nome, String descrizione) {
		this.nome = nome;
		this.descrizione = descrizione;
		addCampi();
	}
	
	private void addCampi() {
		addCampo(titolo);
		addCampo(numPartecipanti);
		addCampo(termIscr);
		addCampo(luogo);
		addCampo(dataIn);
		addCampo(oraIn);
		addCampo(durata);
		addCampo(quota);
		addCampo(compresoQuota);
		addCampo(dataFin);
		addCampo(oraFin);
		addCampo(note);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public ArrayList<Campo> getCampi(){
		return campi;
	}
	/*
	 * Precondizione: il nome del campo passato deve essere il nome di un campo esistente
	 */
	public Campo getCampo(String nome) {
		for(int i=0; i<campi.size(); i++) {
			if(campi.get(i).getNome().equals(nome)) {
				return campi.get(i);
			}
		}
		System.out.println("Campo inesistente");
		return null;
	}
	
	public void addCampo(Campo c) {
		campi.add(c);
	}
	
	/*
	 * Precondizioni: il campo passato deve essere un campo esistente
	 *                il valore passato deve essere del tipo corretto rispetto al campo passato
	 */
	public <T> void setValCampo(Campo c, T valore) {
		c.setValore(valore);
	}
}
