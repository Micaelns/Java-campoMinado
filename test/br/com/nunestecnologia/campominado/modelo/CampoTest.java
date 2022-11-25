package br.com.nunestecnologia.campominado.modelo;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.nunestecnologia.campominado.execao.ExplosaoException;

class CampoTest {

	private Campo campo;
	
	@BeforeEach
	void iniciarCampo() {
		campo= new Campo(3,3);
	}
	
	@Test
	void TesteVizinhoDistancia1() {
		Campo vizinho = new Campo(3,2);
		
		boolean resultado = campo.adicionarVizinho(vizinho);
		
		assertTrue(resultado);
	}
	
	@Test
	void TesteVizinhoDistancia2() {
		Campo vizinho = new Campo(3,4);
		
		boolean resultado = campo.adicionarVizinho(vizinho);
		
		assertTrue(resultado);
	}
	
	@Test
	void TesteVizinhoDistancia3() {
		Campo vizinho = new Campo(2,3);
		
		boolean resultado = campo.adicionarVizinho(vizinho);
		
		assertTrue(resultado);
	}
	
	@Test
	void TesteVizinhoDistancia4() {
		Campo vizinho = new Campo(4,3);
		
		boolean resultado = campo.adicionarVizinho(vizinho);
		
		assertTrue(resultado);
	}
	
	@Test
	void TesteVizinhoDistancia5() {
		Campo vizinho = new Campo(2,2);
		
		boolean resultado = campo.adicionarVizinho(vizinho);
		
		assertTrue(resultado);
	}
	
	@Test
	void TesteVizinhoDistanciaInvalido() {
		Campo vizinho = new Campo(1,1);
		
		boolean resultado = campo.adicionarVizinho(vizinho);
		
		assertFalse(resultado);
	}
	
	@Test
	void TesteValorPadraoAtributoMarcado() {
		assertFalse(campo.isMarcado());
	}
	
	@Test
	void TesteIsMinadoMinarRetesta() {
		assertFalse(campo.isMinado());
		campo.minar();
		assertTrue(campo.isMinado());
	}
	
	
	@Test
	void TesteAlternarMarcacao() {
		campo.alternarMarcacao();
		assertTrue(campo.isMarcado());
	}
	
	@Test
	void TesteAlternarMarcacao2Chamadas() {
		campo.alternarMarcacao();
		campo.alternarMarcacao();
		assertFalse(campo.isMarcado());
	}

	@Test
	void TesteAbrirNaoMinadoNaoMarcado() {
		assertTrue(campo.abrir());
	}
	
	@Test
	void TesteAbrirNaoMinadoMarcado() {
		campo.alternarMarcacao();
		assertFalse(campo.abrir());
	}
	
	@Test
	void TesteAbrirMinadoMarcado() {
		campo.alternarMarcacao();
		campo.minar();
		assertFalse(campo.abrir());
	}
	
	@Test
	void TesteLinhaCorretaAtual() {
		Campo ctest = new Campo(3,2);
		assertTrue(ctest.getLinha() == 3);
	}
	
	@Test
	void TesteColunaCorretaCampo() {
		Campo ctest = new Campo(3,2);
		assertTrue(ctest.getColuna()==2);
	}
	
	@Test
	void TesteAbrirMinadoNaoMarcado() {
		
		campo.registrarObservador( (campo, evento) -> {
			if(evento == CampoEvento.EXPLODIR) {
				assertTrue(true);
			}else {
				assertFalse(true);
			}
		});
		campo.minar();
		assertTrue(campo.abrir());
	}
	
	@Test
	void TesteAbrirComVizinhos() {
		
		Campo campo11 = new Campo(1,1);
		Campo campo22 = new Campo(2,2);
		campo22.adicionarVizinho(campo11);
		
		campo.adicionarVizinho(campo22);
		campo.abrir();
		
		assertTrue(campo22.isAberto() && campo11.isAberto());
	}
	
	@Test
	void TesteAbrirComVizinhos2() {
		
		Campo campo11 = new Campo(1,1);
		Campo campo12 = new Campo(1,2);
		campo12.minar();
		Campo campo22 = new Campo(2,2);
		campo22.adicionarVizinho(campo11);
		campo22.adicionarVizinho(campo12);
		
		campo.adicionarVizinho(campo22);
		campo.abrir();
		
		assertTrue(campo22.isAberto() && !campo11.isAberto());
	}
	
	@Test
	void TesteVerificaObjetivoCampoFechado() {
		
		Campo campo1 = new Campo(1,1);
		assertFalse(campo1.objetivoAlcancado());
	}

	@Test
	void TesteVerificaObjetivoCampoAbertoNaoMinado() {
		
		Campo campo1 = new Campo(1,1);
		campo1.abrir();
		assertTrue(campo1.objetivoAlcancado());
	}

	@Test
	void TesteVerificaObjetivoCampoMinadoProtegido() {
		
		Campo campo1 = new Campo(1,1);
		campo1.minar();
		campo1.alternarMarcacao();
		assertTrue(campo1.objetivoAlcancado());
	}
	
	@Test
	void TesteVerificaMinasNasVizinhacas() {
		
		Campo campo32 = new Campo(3,2);
		Campo campo44 = new Campo(4,4);
		Campo campo22 = new Campo(2,2);
		Campo campo23 = new Campo(2,3);
		campo44.minar();
		campo22.minar();
		
		campo.adicionarVizinho(campo32);
		campo.adicionarVizinho(campo44);
		campo.adicionarVizinho(campo22);
		campo.adicionarVizinho(campo23);
		
		assertTrue(campo.minasNaVizinhanca()==2);
	}
	
	@Test
	void TesteVerificaSetAbertoTrue() {
		campo.setAberto(true);
		assertTrue(campo.isAberto());
	}
	
	@Test
	void TesteVerificaSetAbertoFalse() {
		assertFalse(campo.isAberto());
	}
	
	@Test
	void TesteReiniciarCampoMarcado() {
		campo.alternarMarcacao();
		campo.reiniciar();
		assertFalse(campo.isMarcado());
	}
	
	@Test
	void TesteReiniciarCampoAberto() {
		campo.abrir();
		campo.reiniciar();
		assertFalse(campo.isAberto());
	}

	@Test
	void TesteReiniciarCampoMinado() {
		campo.minar();
		campo.reiniciar();
		assertFalse(campo.isMinado());
	}
	
}
