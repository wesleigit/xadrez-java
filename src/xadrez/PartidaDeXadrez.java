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
	
	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	
	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8,8);
		turno = 1;
		jogadorAtual = Cor.Branco;
		inicilizarJogo();
	}
	
	public int getTurno(){
		return turno;
	}
	
	public Cor getJogadorAtual() {
		return jogadorAtual;
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
	
	public boolean[][] movimentosPossiveis(XadrezPosicao posicaoOrigem){
		Posicao posicao = posicaoOrigem.toPosicao();
		validarPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
	}
	
	
	public PecaDeXadrez MovimentarPeca(XadrezPosicao posicaoDeOrigem, XadrezPosicao posicaoDestino) {
		Posicao origem = posicaoDeOrigem.toPosicao();
		Posicao destino = posicaoDestino.toPosicao();
		validarPosicaoOrigem(origem);
		validarPosicaoDestino(origem, destino);
		Peca caputuraDePeca = realizarMovimento(origem, destino);
		proximoTurno();
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
			throw new XadrezException("Não existe peca na posição de origem.");
		}	
		
		if (jogadorAtual != ((PecaDeXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezException("A peça escolhida não é sua");
		}
		
		if (!tabuleiro.peca(posicao).existeAlgumMovimentoPossivel()){
			throw new XadrezException("Não existe movimentos possiveis para a peca.");
		}
	}
	
	private void validarPosicaoDestino(Posicao origem,Posicao destino) {
		if (!tabuleiro.peca(origem).movimentoPossivel(destino)) {
			throw new XadrezException("A peca de origem não pode se mexer para a posicão de destino.");
		}
	}
	
	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.Branco)? Cor.Preto : Cor.Branco;
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
		
		colocarNovaPeca('b', 1, new Rei(tabuleiro, Cor.Preto));
		colocarNovaPeca('c', 1, new Cavalo(tabuleiro, Cor.Preto));
		colocarNovaPeca('d', 1, new Dama(tabuleiro, Cor.Preto));
		colocarNovaPeca('e', 1, new Bispo(tabuleiro, Cor.Preto));
		colocarNovaPeca('f', 1, new Torre(tabuleiro, Cor.Preto));
		colocarNovaPeca('g', 1, new Peao(tabuleiro, Cor.Preto));
		
		
	}
	

}
