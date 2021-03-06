package aplicacao;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import xadrez.Cor;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.XadrezPosicao;

public class UI {
	
	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	public static void limparTela() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static XadrezPosicao OrigemPosicaoXadrez(Scanner sc) {
		try {
		String s = sc.nextLine();
		char coluna = s.charAt(0);
		int linha = Integer.parseInt(s.substring(1));
		return new XadrezPosicao(coluna, linha);
		}
		catch (RuntimeException e) {
			throw new InputMismatchException("Erro ao ler as posi��es de entrada. Valores validos de A1 a H8.");
		}
	}
	
	public static void imprimirAPartida(PartidaDeXadrez partidaDeXadrez, List<PecaDeXadrez> capiturada) {
		imprimirTabuleiro(partidaDeXadrez.getPecas());
		System.out.println();
		mprimirPecasCapituradas(capiturada);
		System.out.println();
		System.out.println("Turno: " + partidaDeXadrez.getTurno());
		
		if (partidaDeXadrez.getCheckMate()) {
			System.out.println("CHECKMATE!");
			System.out.println("Vencedor: " + partidaDeXadrez.getJogadorAtual());
		}
		else {
			System.out.println("Aguardando o jogador da  cor " + partidaDeXadrez.getJogadorAtual());
			
			if (partidaDeXadrez.getCheck()) {
				System.out.println("CHECK!");		
		    }
		}
	}
	
	public static void imprimirTabuleiro(PecaDeXadrez[][] pecas) {
		for (int i=0; i<pecas.length; i++) {
			System.out.print(ANSI_GREEN  + (8 - i)  + " " + ANSI_RESET);
			for(int j=0; j<pecas.length;j++) {
				imprimirPeca(pecas[i][j], false);
			}
			System.out.println();
		}
		 System.out.println(ANSI_GREEN  + "  a b c d e f g h" + ANSI_RESET);
	}
	
	public static void imprimirTabuleiro(PecaDeXadrez[][] pecas, boolean[][] movimentosPossiveis) {
		for (int i=0; i<pecas.length; i++) {
			System.out.print(ANSI_GREEN  + (8 - i) + " " +  ANSI_RESET);
			for(int j=0; j<pecas.length;j++) {
				imprimirPeca(pecas[i][j], movimentosPossiveis[i][j]);
			}
			System.out.println();
		}
		 System.out.println(ANSI_GREEN  + "  a b c d e f g h" + ANSI_RESET);
	}
	
	
	private static void imprimirPeca(PecaDeXadrez peca, boolean fundoTela) {
		if (fundoTela) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
	    
		if (peca == null) {
	      System.out.print("-" + ANSI_RESET);
	    }
	    else {
	      if (peca.getCor() == Cor.Branco) {
	        System.out.print(ANSI_WHITE + peca + ANSI_RESET);
	      }
	      else {
	        System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
	      }
	    }
	   System.out.print(" ");
	}
	
	private static void mprimirPecasCapituradas(List<PecaDeXadrez> capiturado) {
		List<PecaDeXadrez> branco = capiturado.stream().filter(x -> x.getCor() == Cor.Branco).collect(Collectors.toList());
		List<PecaDeXadrez> preto = capiturado.stream().filter(x -> x.getCor() == Cor.Preto).collect(Collectors.toList());
		System.out.println("Pecas capturadas: ");
		System.out.print("Branco: ");
		System.out.print(ANSI_WHITE);
		System.out.println(Arrays.toString(branco.toArray()));
		System.out.print(ANSI_RESET);
		
		System.out.print("Preto:  ");
		System.out.print(ANSI_YELLOW);
		System.out.println(Arrays.toString(preto.toArray()));
		System.out.print(ANSI_RESET);
		
	}
}
