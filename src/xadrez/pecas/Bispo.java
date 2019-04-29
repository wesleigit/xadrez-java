package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Bispo extends PecaDeXadrez {
	
	public Bispo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "B";
	}
	
	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0, 0);
		
		//Noroeste
		p.setValores(posicao.getLinha() -1, posicao.getColuna() -1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPacaNaPosicao(p)){
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() -1, p.getColuna() -1);
		}
		
		if (getTabuleiro().posicaoExiste(p) && existePecaAdversaria(p)){
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//Nordeste
		p.setValores(posicao.getLinha() +1, posicao.getColuna() +1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPacaNaPosicao(p)){
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() +1, p.getColuna() +1);
		}
		
		if (getTabuleiro().posicaoExiste(p) && existePecaAdversaria(p)){
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//Suldeste
		p.setValores(posicao.getLinha() -1, posicao.getColuna() +1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPacaNaPosicao(p)){
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() -1, p.getColuna() +1);
			}
		
		if (getTabuleiro().posicaoExiste(p) && existePecaAdversaria(p)){
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//Sul do Oeste
		p.setValores(posicao.getLinha() +1, posicao.getColuna() -1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPacaNaPosicao(p)){
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() +1, p.getColuna() -1);
			}
		
		if (getTabuleiro().posicaoExiste(p) && existePecaAdversaria(p)){
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		
		return mat;
	}
}
