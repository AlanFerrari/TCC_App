package br.com.etecia.meus_direitos.objetos;

public class Advogados {
    private int imagem;
    private String userName;
    private String cidade;
    private String estado;
    private String area_atuacao;

    public Advogados(int imagem, String userName, String cidade, String estado, String area_atuacao) {
        this.imagem = imagem;
        this.userName = userName;
        this.cidade = cidade;
        this.estado = estado;
        this.area_atuacao = area_atuacao;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getArea_atuacao() {
        return area_atuacao;
    }

    public void setArea_atuacao(String area_atuacao) {
        this.area_atuacao = area_atuacao;
    }
}
