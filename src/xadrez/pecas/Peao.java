package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Peao extends PecaDeXadrez {
	
	public Peao(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "P";
	}
	
	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0, 0);
		
		if (getCor() == Cor.Branco) {
			p.setValores(posicao.getLinha() -1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPacaNaPosicao(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() -2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() -1, posicao.getColuna());
			
			if (getTabuleiro().posicaoExiste(p) &&
				getTabuleiro().posicaoExiste(p2) &&
			    !getTabuleiro().temPacaNaPosicao(p) &&
			    !getTabuleiro().temPacaNaPosicao(p2) &&
			    getQuantidadeMovimento() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() -1, posicao.getColuna()-1);
			if (getTabuleiro().posicaoExiste(p) && existePecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() -1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExiste(p) && existePecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
		}
		else {
			p.setValores(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPacaNaPosicao(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
			
			if (getTabuleiro().posicaoExiste(p) &&
				getTabuleiro().posicaoExiste(p2) &&
			    !getTabuleiro().temPacaNaPosicao(p) &&
			    !getTabuleiro().temPacaNaPosicao(p2) &&
			    getQuantidadeMovimento() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() + 1, posicao.getColuna()-1);
			if (getTabuleiro().posicaoExiste(p) && existePecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() +1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExiste(p) && existePecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
		}
		return mat;
    }
}