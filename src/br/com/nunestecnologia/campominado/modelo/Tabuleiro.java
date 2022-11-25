package br.com.nunestecnologia.campominado.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Tabuleiro implements CampoObservador {
	
	private final int linhas;
	private final int colunas;
	private final int minas;
	
	private final List<Campo> campos = new ArrayList<>();
	private final List<Consumer<ResultadoEvento>> observadores = new ArrayList<>();

	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		
		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}
	
	public void paraCadaCampo(Consumer<Campo> funcao) {
		campos.forEach(funcao);
	}
	
	public void registrarObservador(Consumer<ResultadoEvento> observador) {
		observadores.add(observador);
	}
	
	@Override
	public void eventoOcorreu(Campo c, CampoEvento evento) {
		if(evento == CampoEvento.EXPLODIR) {
			mostrarMinas();
			notificarObsercadores(false);
		}else if( objetivoAlcancado()) {
			notificarObsercadores(true);
		}
	}
	public void abrir(int linha, int coluna) {
		campos.parallelStream()
						.filter(c -> c.getColuna() == coluna && c.getLinha() == linha)
						.findFirst()
						.ifPresent(c -> c.abrir());
	}

	public void alternarMarcacao(int linha, int coluna) {
		campos.parallelStream()
			.filter(c -> c.getColuna() == coluna && c.getLinha() == linha)
			.findFirst()
			.ifPresent(c -> c.alternarMarcacao());
	}
	
	public boolean objetivoAlcancado() {
		return campos.stream().allMatch( c -> c.objetivoAlcancado());
	}
	
	public void reiniciar() {
		campos.stream().forEach( c -> c.reiniciar());
		sortearMinas();
	}
	
	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	private void notificarObsercadores(boolean resultado) {
		observadores.stream()
		 	.forEach( o -> o.accept(new ResultadoEvento(resultado)));
	}

	private void sortearMinas() {
		long minasArmadas = 0;
		Predicate<Campo> minado = c -> c.isMinado();
		
		 while(minasArmadas < minas){
			int aleatorio = (int) (Math.random() * campos.size());
			campos.get(aleatorio).minar();
			minasArmadas = campos.stream().filter(minado).count();
		}
	}
	
	private void associarVizinhos() {
		for (Campo c1: campos) {
			for (Campo c2: campos) {
				c1.adicionarVizinho(c2);
			}
		}
	}

	private void gerarCampos() {
		for (int l = 0; l < linhas; l++) {
			for (int c = 0; c < colunas; c++) {
				Campo campo = new Campo(l,c);
				campo.registrarObservador(this);
				campos.add(campo);
			}
		}
	}

	private void mostrarMinas() {
		campos.stream()
			.filter( c -> c.isMinado() )
			.filter( c -> !c.isMarcado() )
			.forEach( c ->  c.setAberto(true));
	}
}
