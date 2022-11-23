package br.com.nunestecnologia.campominado.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TabuleiroTest {

	@Test
	void testTabuleiroSemMinas() {
		int linha=2;
		int coluna=2;
		Tabuleiro tab1 = new Tabuleiro(linha, coluna, 0);
		for (int l = 0; l < linha; l++) {
			for (int c = 0; c < coluna; c++) {
				tab1.alternarMarcacao(l, c);
			}
		}
		int camposMarcados = (int) tab1.toString().chars().filter(marked -> marked == 'x').count();
		assertTrue(camposMarcados == linha*coluna);
	}

}
