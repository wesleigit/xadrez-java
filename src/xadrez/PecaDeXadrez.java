package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class PecaDeXadrez extends Peca {
	private Cor cor;
	private int quantidadeMovimento;
	
	public Cor getCor() {
		return cor;
	}

	public void setCor(Cor cor) {
		this.cor = cor;
	}
	
	public int getQuantidadeMovimento() {
		return quantidadeMovimento;
	}
	
	public void incrementarQuantidadeMovimento() {
		quantidadeMovimento++;
	}
	
	public void decrementarQantidadeMovimento() {
		quantidadeMovimento--;
	}
	
	
	
	public XadrezPosicao getPosicaoXadrez() {
		return XadrezPosicao.fromPosicao(posicao);
	}

	public PecaDeXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}
	
	protected boolean existePecaAdversaria(Posicao posicao){
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao);
		return p != null && p.getCor() != cor;
		
	}

}
