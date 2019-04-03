package Versione1;

import java.util.ArrayList;

import mylib.BelleStringhe;

public class Interfaces {
	
	private static final String CAMPI = "Campi:";
	
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
}
