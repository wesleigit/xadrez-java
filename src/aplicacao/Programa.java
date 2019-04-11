package aplicacao;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.XadrezException;
import xadrez.XadrezPosicao;

public class Programa {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
		
		while (true) {
		  try {
			  UI.limparTela();
			  UI.imprimirTabuleiro(partidaDeXadrez.getPecas());
			  System.out.println();
			  System.out.println("Posicao: ");
			  XadrezPosicao posicao = UI.OrigemPosicaoXadrez(sc);
			  
			  System.out.println();
			  System.out.println("Destino: ");
			  XadrezPosicao destino = UI.OrigemPosicaoXadrez(sc);
			  
			  PecaDeXadrez capiturarPeca = partidaDeXadrez.MovimentarPeca(posicao, destino);
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
}
}