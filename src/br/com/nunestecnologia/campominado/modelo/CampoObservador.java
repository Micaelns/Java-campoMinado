package br.com.nunestecnologia.campominado.modelo;

@FunctionalInterface
public interface CampoObservador {

	public void eventoOcorreu(Campo c, CampoEvento evento);
}
