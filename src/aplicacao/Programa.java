package aplicacao;

import xadrez.PartidaDeXadrez;

public class Programa {

	public static void main(String[] args) {
		
		PartidaDeXadrez partidaDeXadres = new PartidaDeXadrez();
		UI.imprimirTabuleiro(partidaDeXadres.getPecas());

}
}