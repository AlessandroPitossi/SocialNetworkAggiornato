package Versione5;

import java.io.File;
import java.util.ArrayList;

public class Bacheca {
	
	private static final File FILE_EVENTI=Evento.FILE_EV;
	public static final int FIRST_PAGE=0;
	
	public static ArrayList<String> mostraEventi(ArrayList<Integer> colonne) {
		ArrayList <String> result=new ArrayList<>();
		ArrayList <Evento> eventiAperti=ExcelUtility.eventiAperti();
		ArrayList<String> stati=new ArrayList<>();
		ExcelReader.takeRowFromFile(stati, FILE_EVENTI, Evento.EVENT_STATO_ROW, FIRST_PAGE);
		for(int i=0; i<eventiAperti.size(); i++) {
				colonne.add(i);
				result.add(eventiAperti.get(i).mostraEv());
		}
		return result;
	}
		
	public static void main(String[] args) {
		//mostraEventi();
	}
}
