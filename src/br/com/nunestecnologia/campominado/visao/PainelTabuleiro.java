package br.com.nunestecnologia.campominado.visao;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.com.nunestecnologia.campominado.modelo.Tabuleiro;

public class PainelTabuleiro extends JPanel{
	private static final long serialVersionUID = 1L;

	public PainelTabuleiro(Tabuleiro tabuleiro) {
		setLayout(new GridLayout(tabuleiro.getLinhas(),tabuleiro.getColunas()));
		
		tabuleiro.paraCadaCampo( c -> add(new BotaoCampo(c)));
		tabuleiro.registrarObservador(e -> {
			
			SwingUtilities.invokeLater(() -> {
				if( e.isGanhou()) {
					JOptionPane.showMessageDialog(this, "Parabéns você ganhou! ;)");
				}else {
					JOptionPane.showMessageDialog(this, "Infelizmente você perdeu :(");
				}
				tabuleiro.reiniciar();
			});
			
		});
		
	}

}
