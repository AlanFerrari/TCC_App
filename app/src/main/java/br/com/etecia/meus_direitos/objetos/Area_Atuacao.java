package br.com.etecia.meus_direitos.objetos;

public class Area_Atuacao {
    private String nome;
    private int imagem;

    public Area_Atuacao(String nome, int imagem) {
        this.nome = nome;
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }
}
