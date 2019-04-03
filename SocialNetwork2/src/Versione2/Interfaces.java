package Versione2;

import java.util.ArrayList;

import mylib.BelleStringhe;

public class Interfaces {
	
	private static final String PART_ATT="Partecipanti attuali:";
	private static final String GIORNI=" giorni";
	private static final String ORE=" ore";
	private static final String CAMPI = "Campi:";
	
	public static String mostraEvento(Evento e) {
		String s="";
		s+=e.getNome()+"\n"+e.getDescrizione()+"\n\n";
		if(e.getValueCampoString(Evento.TITOLO)!=null) s+=Evento.TITOLO+": "+e.getValueCampoString(Evento.TITOLO)+"\n";
		s+=Evento.NUM_PART+": "+e.getValueCampoInt(Evento.NUM_PART)+"        "+PART_ATT+e.getPartAtt()+"\n";
		s+=Evento.LUOGO+": "+e.getValueCampoString(Evento.LUOGO)+"\n";
		s+=Evento.DATA+": "+mylib.Utility.DateToString(e.getValueCampoDate(Evento.DATA))+"\n";
		s+=Evento.TERM_ULT_ISCR+": "+mylib.Utility.DateToString(e.getValueCampoDate(Evento.TERM_ULT_ISCR))+"\n";
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
}
