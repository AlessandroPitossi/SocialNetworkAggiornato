package Versione3;

import mylib.BelleStringhe;

public class Campo<T>{
	private static final String OBBLIGATORIO="Campo obbligatorio";
	private static final String FACOLTATIVO="Campo facoltativo";
	private static final int NUM_CHAR_DA_STAM=60;
	private boolean obbligatorio;
	private String nome;
	private String descrizione;
	private T valore;
	
	public Campo(String nome, String descrizione, boolean obbligatorio){
		this.nome=nome;
		this.descrizione=descrizione;
		this.obbligatorio=obbligatorio;
	}
	
	public void mostra() {
		System.out.println("-" + nome);
		System.out.print(BelleStringhe.aCapo(descrizione, NUM_CHAR_DA_STAM));
		if(obbligatorio) {
			System.out.println(OBBLIGATORIO);
		}
		else System.out.println(FACOLTATIVO);
		System.out.println();
	}
	
	public boolean isObbligatorio() {
		return obbligatorio;
	}

	public void setObbligatorio(boolean obbligatorio) {
		this.obbligatorio = obbligatorio;
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

	public T getValore() {
		return valore;
	}

	public void setValore(T valore) {
		this.valore = valore;
	}
	
}