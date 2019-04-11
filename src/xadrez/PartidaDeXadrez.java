package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Dama;
import xadrez.pecas.Peao;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {
	private Tabuleiro tabuleiro;
	
	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8,8);
		inicilizarJogo();
	}
	
	public PecaDeXadrez[][] getPecas(){
		PecaDeXadrez[][] matriz = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i=0; i<tabuleiro.getLinhas(); i++) {
			for (int j=0; j<tabuleiro.getColunas(); j++) {
				matriz[i][j] = (PecaDeXadrez)tabuleiro.peca(i,j);
			}
		}
		return matriz;
	}
	
	
	public PecaDeXadrez MovimentarPeca(XadrezPosicao posicaoDeOrigem, XadrezPosicao posicaoDestino) {
		Posicao origem = posicaoDeOrigem.toPosicao();
		Posicao destino = posicaoDestino.toPosicao();
		validarPosicaoOrigem(origem);
		Peca caputuraDePeca = realizarMovimento(origem, destino);
		return (PecaDeXadrez)caputuraDePeca;
	}
	
	private Peca realizarMovimento(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removerPeca(origem);
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(p, destino);
		return pecaCapturada;
	}
	
	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.temPacaNaPosicao(posicao)) {
			throw new XadrezException("N�o existe peca na posi��o de origem.");
		}	
		
		if (!tabuleiro.peca(posicao).existeAlgumMovimentoPossivel()){
			throw new XadrezException("N�o existe movimentos possiveis para a peca.");
		}
	}
	
	private void colocarNovaPeca(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.colocarPeca(peca, new XadrezPosicao(coluna, linha).toPosicao());
	}
	
	private void inicilizarJogo() {
		colocarNovaPeca('b', 6, new Rei(tabuleiro, Cor.Branco));
		colocarNovaPeca('c', 6, new Cavalo(tabuleiro, Cor.Branco));
		colocarNovaPeca('d', 6, new Dama(tabuleiro, Cor.Branco));
		colocarNovaPeca('e', 6, new Bispo(tabuleiro, Cor.Branco));
		colocarNovaPeca('f', 6, new Torre(tabuleiro, Cor.Branco));
		colocarNovaPeca('g', 6, new Peao(tabuleiro, Cor.Branco));
		
		colocarNovaPeca('b', 5, new Rei(tabuleiro, Cor.Preto));
		colocarNovaPeca('c', 5, new Cavalo(tabuleiro, Cor.Preto));
		colocarNovaPeca('d', 5, new Dama(tabuleiro, Cor.Preto));
		colocarNovaPeca('e', 5, new Bispo(tabuleiro, Cor.Preto));
		colocarNovaPeca('f', 5, new Torre(tabuleiro, Cor.Preto));
		colocarNovaPeca('g', 5, new Peao(tabuleiro, Cor.Preto));
		
		
	}
	

}
