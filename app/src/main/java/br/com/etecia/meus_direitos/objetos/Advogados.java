package br.com.etecia.meus_direitos.objetos;

public class Advogados {
    private int id;
    private int imagem;
    private String nomeAdvogado;
    private String cidade;
    private String estado;
    private String areaAtuacao;

    public Advogados() {
    }

    public Advogados(int id, int imagem, String nomeAdvogado, String cidade, String estado, String areaAtuacao) {
        this.id = id;
        this.imagem = imagem;
        this.nomeAdvogado = nomeAdvogado;
        this.cidade = cidade;
        this.estado = estado;
        this.areaAtuacao = areaAtuacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }

    public String getNomeAdvogado() {
        return nomeAdvogado;
    }

    public void setNomeAdvogado(String nomeAdvogado) {
        this.nomeAdvogado = nomeAdvogado;
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
        return areaAtuacao;
    }

    public void setArea_atuacao(String area_atuacao) {
        this.areaAtuacao = area_atuacao;
    }
}
