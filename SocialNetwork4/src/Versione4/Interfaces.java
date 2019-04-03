package Versione4;

import java.util.ArrayList;

import mylib.BelleStringhe;

public class Interfaces {
	
	private static final String ISCRITTI_MAX="Iscritti massimi: ";
	private static final String PART_ATT="Partecipanti attuali:";
	private static final String GIORNI=" giorni";
	private static final String ORE=" ore";
	private static final String ID="Nomignolo: ";
	private static final String ETA="Età: ";
	private static final String FASCIA="Fascia età: ";
	private static final String GENERE="Genere: ";
	private static final String CATEGORIE="Categorie d'interesse: ";
	private static final String NO_VALUE="/";
	private static final String CAMPI = "Campi:";
	private static final String TITOLO1="E' stato creato un evento ";
	private static final String TITOLO2=" da parte dell'utente ";
	private static final String	DESCRIZIONE1="Corri ad iscriverti all'evento ";
	private static final String	DESCRIZIONE2=" del ";
	private static final String	DESCRIZIONE3=" prima che i posti si esauriscano!";
	private static final boolean NUOVA=true;
	private static final boolean INVITO=true;
	private static final String TITOLO_INVITO="Invito a partecipare ad un evento da parte dell'utente ";
	private static final String DESCRIZIONE_INVITO1="L'utente ";
	private static final String DESCRIZIONE_INVITO2=" ti ha invitato a partecipare all'evento da lui creato ";
	private static final String DESCRIZIONE_INVITO3="\nL'evento in questione è:\n\n";

	public static String mostraEvento(Evento e) {
		int iscrMax=e.getValueCampoInt(Evento.NUM_PART)+e.getValueCampoInt(Evento.TOLLERANZA_PARTECIPANTI);
		String s="";
		s+=e.getNome()+"\n"+e.getDescrizione()+"\n\n";
		if(e.getValueCampoString(Evento.TITOLO)!=null) s+=Evento.TITOLO+": "+e.getValueCampoString(Evento.TITOLO)+"\n";
		s+=Evento.NUM_PART+": "+e.getValueCampoInt(Evento.NUM_PART)+"        "+ISCRITTI_MAX+iscrMax+"        "+PART_ATT+e.getPartAtt()+"\n";
		s+=Evento.LUOGO+": "+e.getValueCampoString(Evento.LUOGO)+"\n";
		s+=Evento.DATA+": "+mylib.Utility.DateToString(e.getValueCampoDate(Evento.DATA))+"\n";
		s+=Evento.TERM_ULT_ISCR+": "+mylib.Utility.DateToString(e.getValueCampoDate(Evento.TERM_ULT_ISCR))+"\n";
		s+=Evento.TERMINE_RITIRO_ISCR+":"+mylib.Utility.DateToString(e.getValueCampoDate(Evento.TERMINE_RITIRO_ISCR))+"\n";
		if(e.getCampo(Evento.DURATA).getValore()!=null) {
			s+=Evento.DURATA+": "+e.getCampo(Evento.DURATA).getValore().toString();
			if(e.getCampo(Evento.DURATA).getValore().toString().indexOf(":")==-1) s+=GIORNI+"\n";
			else s+=ORE+"\n";
		}
		if(e.getValueCampoDate(Evento.DATA_FIN)!=null) {
			s+=Evento.DATA_FIN+": "+mylib.Utility.DateToString(e.getValueCampoDate(Evento.DATA_FIN))+"\n";
		}
		s+=Evento.ORA+": "+e.getValueCampoTime(Evento.ORA).toString()+"\n";
		if(e.getValueCampoTime(Evento.ORA_FIN)!=null) {
			s+=Evento.ORA_FIN+": "+e.getValueCampoTime(Evento.ORA_FIN).toString()+"\n";
		}
		s+=Evento.QUOTA+": "+e.getValueCampoDouble(Evento.QUOTA)+"\n";
		if(e.getValueCampoString(Evento.COMPRESO_QUOTA)!=null) {
			s+=Evento.COMPRESO_QUOTA+": "+e.getValueCampoString(Evento.COMPRESO_QUOTA)+"\n";
		}
		if(e.getValueCampoString(Evento.NOTE)!=null) {
			s+=Evento.NOTE+": "+e.getValueCampoString(Evento.NOTE)+"\n";
		}
		return s;
	}
	
	public static void mostra(Evento e) {
		System.out.println();
		System.out.println(e.getNome().toUpperCase());
		System.out.println(e.getDescrizione()+"\n");
		System.out.println(BelleStringhe.incornicia(CAMPI));
		ArrayList<Campo> campi=e.getCampi();
		for(int i=0; i<campi.size(); i++) {
			campi.get(i).mostra();
		}
		System.out.println();
	}
	
	public static String mostraEvento(PartitaDiCalcio e) {
		String s=mostraEvento((Evento)e);
		s+=PartitaDiCalcio.GENERE+": "+e.getGenere()+"\n";
		s+=PartitaDiCalcio.FASCIA_ETA+": "+e.getFascia()+"\n";
		return s;
	}
	
	public static String getProfilo(Utente u) {
		String s="";
		s+=ID+u.getId();
		s+="\n"+ETA+u.getEta()+"         "+FASCIA;
		if(u.getFascia()!=null&&u.getFascia()!="") s+=u.getFascia();
		else s+=NO_VALUE;
		s+="\n"+GENERE+u.getGenere();
		s+="\n"+CATEGORIE;
		if(u.getCategorie()!=null&&u.getCategorie().size()!=0) {
			for(int i=0; i<u.getCategorie().size(); i++) {
				if(i>0) s+=", ";
				s+=u.getCategorie().get(i);
			}
		}
		else s+=NO_VALUE;
		return s;
	}
	
	public static Notifica notificaCategoriaInteresse(Evento e) {
		String titolo=TITOLO1+ e.getNome() +TITOLO2+ e.getCreatore().getId();
		String descrizione=DESCRIZIONE1+ e.getNome() +DESCRIZIONE2 +mylib.Utility.DateToString(e.getValueCampoDate(Evento.DATA)) +DESCRIZIONE3;
		return new Notifica(titolo, descrizione, NUOVA, e.getColumn(), !INVITO);
	}
	
	public static Notifica invito(Evento e) {
		Utente u=e.getCreatore();
		String titolo=TITOLO_INVITO+u.getId();
		String descrizione=DESCRIZIONE_INVITO1+u.getId()+DESCRIZIONE_INVITO2+e.getNome()+DESCRIZIONE2+
				mylib.Utility.DateToString(e.getValueCampoDate(Evento.DATA))+DESCRIZIONE_INVITO3+mostraEvento(e);
		return new Notifica(titolo, descrizione, NUOVA, e.getColumn(), INVITO);
	}
}
