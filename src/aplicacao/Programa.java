package aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.XadrezException;
import xadrez.XadrezPosicao;

public class Programa {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
		List<PecaDeXadrez> capiturada = new ArrayList<>();
		
		while (!partidaDeXadrez.getCheckMate()) {
		  try {
			  UI.limparTela();
			  UI.imprimirAPartida(partidaDeXadrez, capiturada);
			  System.out.println();
			  System.out.println("Posicao: ");
			  XadrezPosicao posicao = UI.OrigemPosicaoXadrez(sc);
			  
			  boolean[][] movimentosPossiveis = partidaDeXadrez.movimentosPossiveis(posicao);
			  UI.limparTela();
			  UI.imprimirTabuleiro(partidaDeXadrez.getPecas(), movimentosPossiveis);
			  
			  
			  System.out.println();
			  System.out.println("Destino: ");
			  XadrezPosicao destino = UI.OrigemPosicaoXadrez(sc);
			  
			  PecaDeXadrez capiturarPeca = partidaDeXadrez.MovimentarPeca(posicao, destino);
			  
			  if (capiturarPeca != null) {
				  capiturada.add(capiturarPeca);
			  }
		  }
		  catch (XadrezException e) {
	 		    System.out.println(e.getMessage());
				 sc.hasNextLine();
			}
		  catch (InputMismatchException e) {
				  System.out.println(e.getMessage());
				  sc.hasNextLine();
			}
		  }
		UI.limparTela();
		UI.imprimirAPartida(partidaDeXadrez, capiturada);
		  
}
}