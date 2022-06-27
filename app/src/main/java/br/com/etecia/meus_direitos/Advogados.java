package br.com.etecia.meus_direitos;

public class Advogados{
    String nome;
    String estado;
    String cidade;
    String areaAtuacao;
    int imagem;

    public Advogados(String nome, String estado, String cidade, String areaAtuacao, int imagem) {
        this.nome = nome;
        this.estado = estado;
        this.cidade = cidade;
        this.areaAtuacao = areaAtuacao;
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getAreaAtuacao() {
        return areaAtuacao;
    }

    public void setAreaAtuacao(String areaAtuacao) {
        this.areaAtuacao = areaAtuacao;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }
}
