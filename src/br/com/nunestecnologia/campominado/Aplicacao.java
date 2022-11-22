package br.com.nunestecnologia.campominado;

import br.com.nunestecnologia.campominado.modelo.Tabuleiro;
import br.com.nunestecnologia.campominado.visao.TabuleiroConsole;

public class Aplicacao {

	public static void main(String[] args) {
		Tabuleiro tabuleiro= new Tabuleiro(6, 6, 6);
		
		new TabuleiroConsole(tabuleiro);
	}
}
