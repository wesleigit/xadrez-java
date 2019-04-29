package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	private boolean check;
	private boolean checkMate;
	
	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapituradas = new ArrayList<>();
	
	
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
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
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
		
		if (testarCheck(jogadorAtual)) {
			desfazerMovimento(origem, destino, caputuraDePeca);
			throw new XadrezException("Nao pode colocar o seu REI em check.");
		}
		
		check = (testarCheck(oponente(jogadorAtual))) ? true : false;
		
		if (testarCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		}
		else {
			proximoTurno();	
		}
		
		return (PecaDeXadrez)caputuraDePeca;
	}
	
	private Peca realizarMovimento(Posicao origem, Posicao destino) {
		PecaDeXadrez p = (PecaDeXadrez)tabuleiro.removerPeca(origem);
		p.incrementarQuantidadeMovimento();
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(p, destino);
		
		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapituradas.add(pecaCapturada);
		}
		
		return pecaCapturada;
	}
	
	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapiturada) {
		PecaDeXadrez p = (PecaDeXadrez)tabuleiro.removerPeca(destino);
		p.decrementarQantidadeMovimento();
		tabuleiro.colocarPeca(p, origem);
		
		if (pecaCapiturada != null) {
			tabuleiro.colocarPeca(pecaCapiturada, destino);
			pecasCapituradas.remove(pecaCapiturada);
			pecasNoTabuleiro.add(pecaCapiturada);
		}
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
	
	
	private Cor oponente(Cor cor) {
		return (cor == Cor.Branco) ? Cor.Preto : Cor.Branco;
	}
	
	private PecaDeXadrez rei(Cor cor) {
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCor() == cor).collect(Collectors.toList());
		for (Peca p: lista) {
			if (p instanceof Rei) {
				return (PecaDeXadrez)p;
			}
		}
		
		throw new IllegalStateException("Nao existe o rei " + cor + " no tabuleiro");
	}
	
	private boolean testarCheck(Cor cor) {
		Posicao posicaoRei = rei(cor).getPosicaoXadrez().toPosicao();
		List<Peca> pecasOponente = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
		for (Peca p: pecasOponente) {
			boolean[][] mat = p.movimentosPossiveis();
			if (mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean testarCheckMate(Cor cor) {
		if (testarCheck(cor)) {
			return false;
		}
		
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
		
		for (Peca p: list) {
			boolean[][] mat = p.movimentosPossiveis();
			for (int i=0; i<tabuleiro.getLinhas(); i++) {
				for (int j=0; j<tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Posicao origem = ((PecaDeXadrez)p).getPosicaoXadrez().toPosicao();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapiturada = realizarMovimento(origem, destino);
						boolean testeCheck = testarCheck(cor);
						desfazerMovimento(origem, destino, pecaCapiturada);
						if (!testeCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	
	private void colocarNovaPeca(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.colocarPeca(peca, new XadrezPosicao(coluna, linha).toPosicao());
		pecasNoTabuleiro.add(peca);
	}
	
	private void inicilizarJogo() {
		// Peças Brancas
		colocarNovaPeca('a', 1, new Torre(tabuleiro, Cor.Branco));
		colocarNovaPeca('h', 1, new Torre(tabuleiro, Cor.Branco));
		colocarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.Branco));
		colocarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.Branco));
		colocarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.Branco));
		colocarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.Branco));
		colocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.Branco));
		colocarNovaPeca('a', 2, new Peao(tabuleiro, Cor.Branco));
		colocarNovaPeca('b', 2, new Peao(tabuleiro, Cor.Branco));
		colocarNovaPeca('c', 2, new Peao(tabuleiro, Cor.Branco));
		colocarNovaPeca('d', 2, new Peao(tabuleiro, Cor.Branco));
		colocarNovaPeca('e', 2, new Peao(tabuleiro, Cor.Branco));
		colocarNovaPeca('f', 2, new Peao(tabuleiro, Cor.Branco));
		colocarNovaPeca('g', 2, new Peao(tabuleiro, Cor.Branco));
		colocarNovaPeca('h', 2, new Peao(tabuleiro, Cor.Branco));
		
		// Peças Pretas
		colocarNovaPeca('a', 8, new Torre(tabuleiro, Cor.Preto));
		colocarNovaPeca('h', 8, new Torre(tabuleiro, Cor.Preto));
		colocarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.Preto));
		colocarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.Preto));
		colocarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.Preto));
		colocarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.Preto));
		colocarNovaPeca('e', 8, new Rei(tabuleiro, Cor.Preto));
		colocarNovaPeca('a', 7, new Peao(tabuleiro, Cor.Preto));
		colocarNovaPeca('b', 7, new Peao(tabuleiro, Cor.Preto));
		colocarNovaPeca('c', 7, new Peao(tabuleiro, Cor.Preto));
		colocarNovaPeca('d', 7, new Peao(tabuleiro, Cor.Preto));
		colocarNovaPeca('e', 7, new Peao(tabuleiro, Cor.Preto));
		colocarNovaPeca('f', 7, new Peao(tabuleiro, Cor.Preto));
		colocarNovaPeca('g', 7, new Peao(tabuleiro, Cor.Preto));
		colocarNovaPeca('h', 7, new Peao(tabuleiro, Cor.Preto));
		
		
		
		/*colocarNovaPeca('b', 6, new Rei(tabuleiro, Cor.Branco));
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
		*/
		
	}
	

}
