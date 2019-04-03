package Versione5;

import java.util.ArrayList;
import java.util.Random;

import org.apache.poi.ss.usermodel.*;

import mylib.InputDati;

public class Utility {
	
	 private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	 private static Random rnd = new Random(System.currentTimeMillis());
	 private static final int LENGHT = 8;
	 private static final String CONSENSO="Vuoi acquistare anche l'aggiunta ";
	 private static final String COSTO=" del costo di ";
	 private static final String EURO=" euro";
	 public static final String SEPARATORE="-";
	 private static final int MIN_COSTO=0;
	 
	/*
	 * Precondizioni: fascia deve essere una stringa rappresentata come "int-int" (es:9-18)
	 *                eta deve essere un arrayList vuoto 
	 */
	public static void prendiEta (String fascia, ArrayList<Integer> eta) {
		int i=fascia.indexOf('-');
		int etaMin=Integer.parseInt(fascia.substring(0, i).trim());
		int etaMax=Integer.parseInt(fascia.substring(i+1).trim());
		eta.add(0, etaMin);
		eta.add(1, etaMax);
	}
	
	public static boolean etaContenuta(String fascia, int eta) {
		ArrayList<Integer> estremi=new ArrayList<>();
		prendiEta(fascia, estremi);
		if(eta<estremi.get(0)||eta>estremi.get(1)) return false;
		return true;
	}
	 
	public static boolean contenuto(ArrayList<String> stringhe, String nome){
		for(int i=0; i<stringhe.size(); i++){
			String ispezionato=stringhe.get(i);
			if(nome.equals(ispezionato)) {
				return true;
			}
		}
		return false;
	}
	
	public static ArrayList<String> getNomiUtenti(ArrayList<Utente> utenti){
		ArrayList<String> nomi=new ArrayList<>();
		for(int i=0; i<utenti.size(); i++) {
			nomi.add(utenti.get(i).getId());
		}
		return nomi;
	}
	
	public static boolean contenuto(String[] stringhe, String nome){
		for(int i=0; i<stringhe.length; i++){
			String ispezionato=stringhe[i];
			if(nome.equals(ispezionato)) {
				return true;
			}
		}
		return false;
	}
	
	public static ArrayList<Evento> mostraCategorie(Evento p) {
		ArrayList<Evento> categorie=new ArrayList<>();
		categorie.add(p);
		for(int i=0; i<categorie.size(); i++) {
			Interfaces.mostra(categorie.get(i));
		}
		return categorie;
	}
	
	public static String consensi(ArrayList<Campo<Double>> pagabili) {
		String prezzi="";
		for(int i=0; i<pagabili.size(); i++) {
			try {
			prezzi+=consenso(pagabili.get(i).getValore(), pagabili.get(i).getNome());
			}
			catch(NullPointerException e) {
				prezzi+=consenso(MIN_COSTO, pagabili.get(i).getNome());
			}
		}
		return prezzi.substring(0, prezzi.length()-1);
	}
	
	private static String consenso(double prezzo, String nome) {
		boolean consenso;
		String prezzi="";
		if(prezzo!=0) {
			consenso=InputDati.yesOrNo(CONSENSO+nome+COSTO+prezzo+EURO);
			if(consenso) {
				prezzi=""+prezzo+SEPARATORE;
			}
			else prezzi=""+MIN_COSTO+SEPARATORE;
		}
		else prezzi=""+MIN_COSTO+SEPARATORE;
		return prezzi;
	}
	
	public static boolean isEmpty(Cell c) {
		try {
			if(c.getStringCellValue().equals("")) return true;
		} catch(Exception e) {
			if((""+c.getNumericCellValue()).equals("")) return true;
		}
		return false;
	}
	
	public static Evento getEvento(String nome, int column) {
		if(nome.equals(PartitaDiCalcio.NOME)) return new PartitaDiCalcio(column);
		else if(nome.equals(Concerto.NOME)) return new Concerto(column);
		return null;
	}

	public static String generatePass() {
	        StringBuilder sb = new StringBuilder(LENGHT);
	        for (int i = 0; i < LENGHT; i++) {
	            sb.append(ALPHABET.charAt(rnd.nextInt(ALPHABET.length())));
	        }
	        return sb.toString();
	    }
}
